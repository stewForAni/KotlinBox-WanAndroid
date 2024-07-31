package com.stew.kotlinbox

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.util.AppCommonUitl
import com.stew.kb_common.util.AppLogUtil
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.KVUtil
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.di.homeModule
import com.stew.kb_me.di.meModule
import com.stew.kb_navigation.di.naviModule
import com.stew.kb_project.di.ProjectModule
import com.stew.kb_user.di.userModule
import com.stew.kotlinbox.asyncthird.ATConstants
import com.stew.kotlinbox.asyncthird.ApplicationAnchorTaskCreator
import com.stew.kb_exp.util.PluginLoadUtil
import com.tencent.mmkv.MMKV
import com.xj.anchortask.library.AnchorProject
import com.xj.anchortask.library.OnProjectExecuteListener
import com.xj.anchortask.library.log.LogUtils
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

    companion object{
        var instance: KBApplication? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //HookClassLoader.hook(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ToastUtil.init(instance!!)

        val oldTime = System.currentTimeMillis()
        initKoin()
        initARouter()
        initMMKV()
        AppLogUtil.init(instance!!)
        AppCommonUitl.init(instance!!)
        initUserDarkMode()
        registerActivityLifecycle()
        Log.d(TAG, "Third party cost time : ${System.currentTimeMillis() - oldTime}")
        //test async third party
        //initAsyncThirdParty()
    }

    private fun initAsyncThirdParty() {
        val project =
            AnchorProject.Builder().setContext(instance!!).setLogLevel(LogUtils.LogLevel.DEBUG)
                .setAnchorTaskCreator(ApplicationAnchorTaskCreator())
                .addTask(ATConstants.TASK1)
                .addTask(ATConstants.TASK2)
                .addTask(ATConstants.TASK3)
                .addTask(ATConstants.TASK4)
                .addTask(ATConstants.TASK5)
                .addTask(ATConstants.TASK6).afterTask(ATConstants.TASK3)
                .addTask(ATConstants.TASK7)
                .build()

        project.addListener(object : OnProjectExecuteListener {

            var oldT: Long? = null
            // project 开始执行的时候
            override fun onProjectStart() {
                oldT = System.currentTimeMillis()
                LogUtils.i(TAG, "onProjectStart")
            }

            // project 执行一个 task 完成的时候
            override fun onTaskFinish(taskName: String) {
                LogUtils.i(TAG, "onTaskFinish, taskName is $taskName")
            }

            // project 执行完成的时候
            override fun onProjectFinish() {
                LogUtils.i(TAG, "onProjectFinish "+(System.currentTimeMillis()- oldT!!))
            }

        })

        project.start().await()
    }

    private fun registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d(TAG, "onActivityCreated: " + activity.localClassName)
            }

            override fun onActivityStarted(activity: Activity) {
                Log.d(TAG, "onActivityStarted: " + activity.localClassName)
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d(TAG, "onActivityResumed: " + activity.localClassName)
            }

            override fun onActivityPaused(activity: Activity) {
                Log.d(TAG, "onActivityPaused: " + activity.localClassName)
            }

            override fun onActivityStopped(activity: Activity) {
                Log.d(TAG, "onActivityStopped: " + activity.localClassName)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d(TAG, "onActivitySaveInstanceState: " + activity.localClassName)
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d(TAG, "onActivityDestroyed: " + activity.localClassName)
            }

        })
    }

    private fun initUserDarkMode() {
        val userDarkMode = KVUtil.getInt(Constants.USER_DARK_MODE, 999)
        Log.d(TAG, "userDarkMode: $userDarkMode")
        when (userDarkMode) {
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