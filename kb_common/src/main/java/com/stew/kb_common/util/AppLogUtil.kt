package com.stew.kb_common.util

import android.app.Application
import android.util.Log
import okio.BufferedSink
import okio.Sink
import okio.appendingSink
import okio.buffer
import java.io.File
import java.util.concurrent.Executors

/**
 * Created by stew on 2023/5/16.
 * mail: stewforani@gmail.com
 */
object AppLogUtil {
    private var mContext: Application? = null
    private var logFile: File? = null
    private var sink: Sink? = null
    private var bufferedSink: BufferedSink? = null
    private val logThread = Executors.newFixedThreadPool(1)

    private fun runOnWorkerThread(runnable: Runnable?) {
        logThread.execute(runnable)
    }

    fun init(context: Application?) {
        mContext = context
    }

    fun addLifeLog(data: String?) {
        runOnWorkerThread(Runnable {
            try {
                Log.d("addLifeLog", "addLifeLog: 1")
                val path = mContext!!.getExternalFilesDir(null).toString() + "/logs/"
                if (logFile == null) {
                    logFile = File(path, "lifelog.txt")
                }
                if (!logFile!!.exists()) {
                    Log.d("addLifeLog", "addLifeLog: 2")
                    val dir = File(path)
                    if (dir.mkdirs()) {
                        if (logFile!!.createNewFile()) {
                            Log.d("addLifeLog", "addLifeLog: 3")
                        }
                    }
                }
                Log.d("addLifeLog", "addLifeLog: 4")
                sink = logFile!!.appendingSink()
                bufferedSink = sink!!.buffer()
                bufferedSink!!.writeUtf8(System.currentTimeMillis().toString())
                bufferedSink!!.writeUtf8(" ----- ")
                bufferedSink!!.writeUtf8(data!!)
                bufferedSink!!.writeUtf8("\n")
                bufferedSink!!.flush()
                sink!!.close()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        })
    }
}