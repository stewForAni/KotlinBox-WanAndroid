package com.stew.kotlinbox.asyncthird

import com.stew.kb_common.util.AppLogUtil
import com.stew.kotlinbox.KBApplication
import com.xj.anchortask.library.AnchorTask

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class Task4 : AnchorTask(ATConstants.TASK4) {

    override fun run() {
        AppLogUtil.init(KBApplication.instance!!)
    }
}