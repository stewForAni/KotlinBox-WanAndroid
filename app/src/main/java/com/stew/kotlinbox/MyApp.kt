package com.stew.kotlinbox

import android.app.Application
import com.stew.kb_common.util.ToastUtil

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        ToastUtil.init(this)
    }
}