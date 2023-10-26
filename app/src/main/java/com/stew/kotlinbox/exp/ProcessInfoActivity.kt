package com.stew.kotlinbox.exp

import android.os.Process
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kotlinbox.R
import com.stew.kotlinbox.databinding.ActivityPiBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


/**
 * Created by stew on 2023/10/26.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_PI)
class ProcessInfoActivity : BaseActivity<ActivityPiBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.activity_pi
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }

        mBind.txPid.text = buildString {
        append("pid: ")
        append(Process.myPid().toString())
    }
        mBind.txRid.text = buildString {
        append("rid: ")
        append(getRenderThreadTid().toString())
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
                        //找到name为RenderThread的线程，则返回第0个数据就是 tid
                        if (threadName == "(RenderThread)") {
                            return param[0].toInt()
                        }
                    }
                }
            }
        }
        return -1
    }

}