package com.stew.kotlinbox.asyncthird

import com.stew.kotlinbox.KBApplication
import com.tencent.mmkv.MMKV
import com.xj.anchortask.library.AnchorTask

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class Task3 : AnchorTask(ATConstants.TASK3) {

    override fun run() {
        MMKV.initialize(KBApplication.instance)
    }
}