package com.stew.kotlinbox

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.util.AppLogUtil
import com.stew.kb_common.util.AppCommonUitl
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.KVUtil
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.di.homeModule
import com.stew.kb_me.di.meModule
import com.stew.kb_navigation.di.naviModule
import com.stew.kb_project.di.ProjectModule
import com.stew.kb_user.di.userModule
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class KBApplication : Application() {

    private val modules = mutableListOf(homeModule, ProjectModule, naviModule, meModule, userModule)
    private val TAG = KBApplication::class.java.name

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Log.d(TAG, "attachBaseContext: $this")
        //HookClassLoader.hook(this)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: $this")
        ToastUtil.init(this)
        initKoin()
        initARouter()
        initMMKV()
        AppLogUtil.init(this)
        AppCommonUitl.init(this)
        initUserDarkMode()
    }

    private fun initUserDarkMode() {
        val userDarkMode = KVUtil.getInt(Constants.USER_DARK_MODE, 999)
        Log.d(TAG, "userDarkMode: $userDarkMode")
        when(userDarkMode){
            999 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1000 -> AppCommonUitl.setFollowSystemTheme()
            else -> AppCompatDelegate.setDefaultNightMode(userDarkMode)
        }

    }

    private fun initMMKV() {
        MMKV.initialize(this)
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@KBApplication)
            modules(modules)
        }
    }
}