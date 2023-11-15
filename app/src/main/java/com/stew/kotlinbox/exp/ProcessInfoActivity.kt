package com.stew.kotlinbox.exp

import android.os.Process
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kotlinbox.R
import com.stew.kotlinbox.databinding.ActivityPiBinding
import okio.Utf8
import java.io.BufferedReader
import java.io.File
import java.io.FileFilter
import java.io.FileInputStream
import java.io.FileReader
import java.io.IOException
import java.nio.charset.StandardCharsets


/**
 * Created by stew on 2023/10/26.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_PI)
class ProcessInfoActivity : BaseActivity<ActivityPiBinding>() {

    private var cpu_name = ""
    private var cpu_freq = ""
    private var cpu_last_executed_render = ""
    private var cpu_last_executed_main = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_pi
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }

        mBind.txPid.text = buildString {
            append("Pid: ")
            append(Process.myPid().toString())
        }

        mBind.txRid.text = buildString {
            append("Render id: ")
            append(getRenderThreadTid().toString())
            append(" / last executed: cpu")
            append(cpu_last_executed_render)
        }

        mBind.txCpuCount.text = buildString {
            append("Cpu number: ")
            append(getNumberOfCPUCores().toString())
            append("\n")
            append("detail: ")
            append(cpu_name)
        }

        mBind.txBigCore.text = buildString {
            append("Max core: ")
            append(getMaxFreqCPU().toString())
            append("\n")
            append("detail: ")
            append(cpu_freq)
        }

    }

    private fun getRenderThreadTid(): Int {
        val taskParent = File("/proc/" + android.os.Process.myPid() + "/task/")
        if (taskParent.isDirectory) {
            val taskFiles = taskParent.listFiles()
            if (taskFiles != null) {
                for (taskFile in taskFiles) {
                    //读线程名
                    var br: BufferedReader? = null
                    var cpuRate = ""
                    try {
                        br = BufferedReader(FileReader(taskFile.path + "/stat"), 100)
                        cpuRate = br.readLine()
                    } catch (throwable: Throwable) {
                        //ignore
                    } finally {
                        br?.close()
                    }
                    if (!cpuRate.isEmpty()) {
                        val param = cpuRate.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                        if (param.size < 2) {
                            continue
                        }
                        val threadName = param[1]

                        if (threadName.contains("kotlinbox")) {
                            cpu_last_executed_main = param[38]

                            runOnUiThread {
                                mBind.txPid.text = buildString {
                                    append(mBind.txPid.text)
                                    append(" / last executed: cpu")
                                    append(cpu_last_executed_main)
                                }
                            }
                        }

                        //找到name为RenderThread的线程，则返回第0个数据就是 tid
                        if (threadName == "(RenderThread)") {
                            cpu_last_executed_render = param[38]
                            return param[0].toInt()
                        }
                    }
                }
            }
        }
        return -1
    }

    private fun getNumberOfCPUCores(): Int? {
        val file = File("/sys/devices/system/cpu")
        val count = file.listFiles(FileFilter {
            val path = it.name
            if (path.startsWith("cpu")) {
                for (i in 3 until path.length) {
                    if (path[i] < '0' || path[i] > '9') {
                        return@FileFilter false
                    }
                }
                cpu_name = cpu_name + it.name + "/"
                return@FileFilter true
            }
            return@FileFilter false
        })?.size
        return count
    }

    private fun getMaxFreqCPU(): Int {
        var maxFreq = -1
        try {
            for (i in 0 until getNumberOfCPUCores()!!) {
                val filename = "/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_max_freq"
                val cpuInfoMaxFreqFile = File(filename)

//                Log.d(TAG, "getMaxFreqCPU: ----"+i)

                if (cpuInfoMaxFreqFile.exists()) {
                    val buffer = ByteArray(128)
                    val stream = FileInputStream(cpuInfoMaxFreqFile)
                    try {
                        stream.read(buffer)

                        var endIndex = 0

                        //如果看成是字符串，就查ASCII码
                        //这里为了截取有效数据，只截取前面的数字部分
                        while (buffer[endIndex] >= '0'.code.toByte() &&
                            buffer[endIndex] <= '9'.code.toByte() &&
                            endIndex < buffer.size
                        ) {
                            endIndex++
                        }

                        val str = String(buffer, 0, endIndex)

                        cpu_freq += "CPU$i:$str/ "

                        val freqBound = str.toInt()
                        if (freqBound > maxFreq) maxFreq = freqBound

                    } catch (_: NumberFormatException) {
                    } finally {
                        stream.close()
                    }
                }
            }
        } catch (e: IOException) {
        }
        return maxFreq
    }


}