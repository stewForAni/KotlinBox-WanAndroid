package com.stew.kotlinbox

import android.app.Application
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class MyApp:Application() {

    private val modules = mutableListOf(homeModule)

    override fun onCreate() {
        super.onCreate()
        ToastUtil.init(this)
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(modules)
        }
    }
}