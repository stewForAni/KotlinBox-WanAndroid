package com.stew.kotlinbox.asyncthird

import com.stew.kb_common.util.AppCommonUitl
import com.stew.kotlinbox.KBApplication
import com.xj.anchortask.library.AnchorTask

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class Task5 : AnchorTask(ATConstants.TASK5) {
    override fun run() {
        AppCommonUitl.init(KBApplication.instance!!)
    }
}