package com.stew.kb_exp.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Handler
import android.util.Log
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.ui.exp.ProxyActivity
import java.lang.reflect.Field
import java.lang.reflect.Proxy


/**
 * Created by stew on 2024/7/28.
 * mail: stewforani@gmail.com
 */
object  PluginLoadUtil {

    const val TAG = "PluginLoadUtil"

    fun loadPluginRes(context: Context, resPath: String?): Resources? {
        return try {
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPathMethod =
                AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPathMethod.isAccessible = true
            addAssetPathMethod.invoke(assetManager, resPath)
            Resources(
                assetManager,
                context.resources.displayMetrics,
                context.resources.configuration
            )
        } catch (e: Exception) {
            null
        }
    }
    
    //ActivityTaskManager.getService().startActivity(...intent...)
    fun hookIActivityTaskManager() {

        Log.d(TAG, "hookIActivityTaskManager: start")
        //开启动态代理，activity退出后关闭
        Constants.isStartDP = true

        //获取IActivityTaskManagerSingleton实例
        val field1 = Class.forName("android.app.ActivityTaskManager")
            .getDeclaredField("IActivityTaskManagerSingleton")
        field1.isAccessible = true
        val obj1 = field1.get(null)


        //获取IActivityTaskManager实例
        val field2 = Class.forName("android.util.Singleton")
            .getDeclaredField("mInstance")
        field2.isAccessible = true
        val iatm = field2.get(obj1)


        val proxyObj = Proxy.newProxyInstance(
            Thread.currentThread().contextClassLoader,
            arrayOf(Class.forName("android.app.IActivityTaskManager"))
        ) { _, method, args ->

            if (method.name.equals("startActivity")) {
                for (i in args.indices) {
                    if (args[i] is Intent) {
                        val pluginIntent = args[i] as Intent //plugin activity intent
                        Log.d(TAG,"----current activity：" + pluginIntent.component?.className)
                        if(pluginIntent.component!!.className.contains("Plugin")){
                            val newIntent = Intent()
                            //这里需要注意是包名，不是包路径
                            newIntent.component =
                                ComponentName("com.stew.kotlinbox", ProxyActivity::class.java.name)
                            newIntent.putExtra("DPTEST", pluginIntent)
                            args[i] = newIntent
                            break
                        }

                    }
                }
            }
            val a = args ?: emptyArray()
            val r = method?.invoke(iatm, *(a))
            return@newProxyInstance r

        }

        field2.set(obj1, proxyObj)
    }

    fun hookActivityThreadH() {
        val atField =
            Class.forName("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread")
        atField.isAccessible = true
        val at = atField.get(null)

        val hField = Class.forName("android.app.ActivityThread").getDeclaredField("mH")
        hField.isAccessible = true
        val handler: Handler = hField.get(at) as Handler

        val callbackField = Class.forName("android.os.Handler").getDeclaredField("mCallback")
        callbackField.isAccessible = true

        val myCallBack = Handler.Callback {
            when (it.what) {
                159 -> {
                    println("----------------159 msg")
                    val mActivityCallbacksField: Field =
                        it.obj.javaClass.getDeclaredField("mActivityCallbacks")
                    mActivityCallbacksField.isAccessible = true
                    val mActivityCallbacks: List<Any> =
                        mActivityCallbacksField.get(it.obj) as List<Any>
                    for (i in mActivityCallbacks.indices) {
                        if (mActivityCallbacks[i].javaClass.name.equals("android.app.servertransaction.LaunchActivityItem")) {
                            val launchItem = mActivityCallbacks[i]
                            val intentFiled = launchItem.javaClass.getDeclaredField("mIntent")
                            intentFiled.isAccessible = true
                            val intent: Intent = intentFiled.get(launchItem) as Intent
                            val pluginIntent: Intent? = intent.getParcelableExtra("DPTEST")
                            Log.d(TAG, "hookActivityThreadH: 1")
                            if (pluginIntent != null) {
                                Log.d(TAG, "hookActivityThreadH: 2")
                                intentFiled.set(launchItem, pluginIntent)
                            }
                            break
                        }
                    }
                }
            }
            return@Callback false
        }

        callbackField.set(handler, myCallBack)
    }

    fun releaseActivityThreadH(){
        val atField =
            Class.forName("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread")
        atField.isAccessible = true
        val at = atField.get(null)

        val hField = Class.forName("android.app.ActivityThread").getDeclaredField("mH")
        hField.isAccessible = true
        val handler: Handler = hField.get(at) as Handler

        val callbackField = Class.forName("android.os.Handler").getDeclaredField("mCallback")
        callbackField.isAccessible = true

        callbackField.set(handler, null)
    }

}