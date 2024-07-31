package com.stew.kb_exp.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Handler
import android.util.ArrayMap
import android.util.Log
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_exp.ui.exp.ProxyActivity
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy


/**
 * Created by stew on 2024/7/28.
 * mail: stewforani@gmail.com
 */
object PluginLoadUtil {

    const val TAG = "PluginLoadUtil"
    var isLoadApk = false

    lateinit var pluginDexClassLoader: DexClassLoader
    lateinit var pluginResources: Resources

    fun init(context: Context) {

        if (isLoadApk) {
            ToastUtil.showMsg("already load apk")
            return
        }
        isLoadApk = true

        val desPath = context.filesDir.absolutePath + File.separator + "plugintest.apk"
        val desFile = File(desPath)

        Log.d(TAG, "init: 1")
        if (!desFile.exists()) {
            Log.d(TAG, "init: 2")
            AssetsUtil.copyAssetsToDes(context, "plugintest.apk", desPath)
        }

        if (desFile.exists()) {
            Log.d(TAG, "init: 3")
            createPluginDex(context, desFile.absolutePath)
            createPluginRes(context, desFile.absolutePath)
            replaceLoadedApkClassLoader(context, pluginDexClassLoader)
        }
    }

    private fun createPluginDex(context: Context, dexPath: String) {
        Log.d(TAG, "getPluginDex: ")
        pluginDexClassLoader = DexClassLoader(
            dexPath,
            context.getDir("dexOpt", Context.MODE_PRIVATE).absolutePath,
            null,
            context.classLoader
        )
    }

    private fun createPluginRes(context: Context, resPath: String) {
        Log.d(TAG, "getPluginRes: ")
        try {
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPathMethod =
                AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPathMethod.isAccessible = true
            addAssetPathMethod.invoke(assetManager, resPath)
            pluginResources = Resources(
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

        //开启动态代理，App退出后关闭
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
                        if (pluginIntent.component!!.className.contains("Plugin")) {
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

    fun releaseActivityThreadH() {
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

    /**
     * 替换 LoadedApk 中的 类加载器 ClassLoader
     *
     *  @param context
     *  @param loader 动态加载dex的ClassLoader
     */
    fun replaceLoadedApkClassLoader(context: Context, loader: DexClassLoader) {
        // I. 获取 ActivityThread 实例对象
        // 获取 ActivityThread 字节码类 , 这里可以使用自定义的类加载器加载
        // 原因是 基于 双亲委派机制 , 自定义的 DexClassLoader 无法加载 , 但是其父类可以加载
        // 即使父类不可加载 , 父类的父类也可以加载
        var activityThreadClass: Class<*>? = null
        try {
            activityThreadClass = loader.loadClass("android.app.ActivityThread")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        // 获取 ActivityThread 中的 sCurrentActivityThread 成员
        // 获取的字段如下 :
        // private static volatile ActivityThread sCurrentActivityThread;
        // 获取字段的方法如下 :
        // public static ActivityThread currentActivityThread() {return sCurrentActivityThread;}
        var currentActivityThreadMethod: Method? = null
        try {
            currentActivityThreadMethod =
                activityThreadClass?.getDeclaredMethod("currentActivityThread")
            // 设置可访问性 , 所有的 方法 , 字段 反射 , 都要设置可访问性
            currentActivityThreadMethod?.isAccessible = true
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        // 执行 ActivityThread 的 currentActivityThread() 方法 , 传入参数 null
        var activityThreadObject: Any? = null
        try {
            activityThreadObject = currentActivityThreadMethod?.invoke(null)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        // II. 获取 LoadedApk 实例对象
        // 获取 ActivityThread 实例对象的 mPackages 成员
        // final ArrayMap<String, WeakReference<LoadedApk>> mPackages = new ArrayMap<>();
        var mPackagesField: Field? = null
        try {
            mPackagesField = activityThreadClass?.getDeclaredField("mPackages")
            // 设置可访问性 , 所有的 方法 , 字段 反射 , 都要设置可访问性
            mPackagesField?.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        // 从 ActivityThread 实例对象 activityThreadObject 中
        // 获取 mPackages 成员
        var mPackagesObject: ArrayMap<String, WeakReference<Any>>? = null
        try {
            mPackagesObject =
                mPackagesField?.get(activityThreadObject) as ArrayMap<String, WeakReference<Any>>?
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        // 获取 WeakReference<LoadedApk> 弱引用对象
        val weakReference: WeakReference<Any>? = mPackagesObject?.get(context.packageName)
        // 获取 LoadedApk 实例对象
        val loadedApkObject = weakReference?.get()


        // III. 替换 LoadedApk 实例对象中的 mClassLoader 类加载器
        // 加载 android.app.LoadedApk 类
        var loadedApkClass: Class<*>? = null
        try {
            loadedApkClass = loader.loadClass("android.app.LoadedApk")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        // 通过反射获取 private ClassLoader mClassLoader; 类加载器对象
        var mClassLoaderField: Field? = null
        try {
            mClassLoaderField = loadedApkClass?.getDeclaredField("mClassLoader")
            // 设置可访问性
            mClassLoaderField?.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        // 替换 mClassLoader 成员
        try {
            mClassLoaderField?.set(loadedApkObject, loader)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}