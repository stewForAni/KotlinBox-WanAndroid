package com.stew.kotlinbox.asyncthird

import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kotlinbox.BuildConfig
import com.stew.kotlinbox.KBApplication
import com.xj.anchortask.library.AnchorTask

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class Task2 : AnchorTask(ATConstants.TASK2) {

    override fun run() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(KBApplication.instance)
    }
}