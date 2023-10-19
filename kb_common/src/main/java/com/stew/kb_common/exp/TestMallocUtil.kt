package com.stew.kb_common.exp

import com.bytedance.android.bytehook.ByteHook

/**
 * Created by stew on 2023/10/19.
 * mail: stewforani@gmail.com
 */
object TestMallocUtil {

    fun startHook() {
        StartMalloc()
    }

    fun testMalloc() {
        TestMalloc()
    }

    private external fun TestMalloc();
    private external fun StartMalloc();
}