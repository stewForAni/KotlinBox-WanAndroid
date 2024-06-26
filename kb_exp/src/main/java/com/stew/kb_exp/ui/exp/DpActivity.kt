package com.stew.kb_exp.ui.exp

import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.Handler.Callback
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityDpBinding
import org.koin.core.component.getScopeId
import java.lang.reflect.Field
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by stew on 2023/11/15.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_DP)
class DpActivity : BaseActivity<ActivityDpBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.activity_dp
    }

    override fun onDestroy() {
        super.onDestroy()
        Constants.isStartDP = false
    }

    override fun init() {

        mBind.imgBack.setOnClickListener { finish() }
        mBind.t.text = "MyInterface:" + MyInterface::class.java.classLoader.getScopeId()
        mBind.t0.text = "Thread:" + Thread.currentThread().contextClassLoader.getScopeId()

        val a = MyObj()
        val p = Proxy.newProxyInstance(
            MyInterface::class.java.classLoader,
            arrayOf(MyInterface::class.java),
            MyHandler(a)
        ) as MyInterface
        p.func1()


        //------------------------------------------------------------------------------------


        //PluginActvity 替换成 ProxyActivity（binder传递消息到AMS之前）
        //请注意，这个方法会影响整个app的跳转逻辑，因为
        hookIActivityTaskManager()
        //ProxyActivity 还原成 PluginActvity（handler处理消息之前，关键点：intent初始化于LaunchActivityItem中）
        hookActivityThreadH()

        mBind.t4.text = "hook iatm and activityThread-H"
        mBind.btn.setOnClickListener {
            startActivity(Intent(this, PluginActivity::class.java))
        }

    }

    //ActivityTaskManager.getService().startActivity(...intent...)
    private fun hookIActivityTaskManager() {

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

            if (method.name.equals("startActivity") && Constants.isStartDP) {
                for (i in args.indices) {
                    if (args[i] is Intent) {
                        val pluginIntent = args[i] as Intent //plugin activity intent
                        println("----current activity：" + pluginIntent.component?.className)
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

            val a = args ?: emptyArray()

            val r = method?.invoke(iatm, *(a))

            if (method.name.equals("startActivity")) {
                println("----$method.name / $r")
            }
            return@newProxyInstance r
        }

        field2.set(obj1, proxyObj)
    }

    private fun hookActivityThreadH() {
        val atField =
            Class.forName("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread")
        atField.isAccessible = true
        val at = atField.get(null)

        val hField = Class.forName("android.app.ActivityThread").getDeclaredField("mH")
        hField.isAccessible = true
        val handler: Handler = hField.get(at) as Handler

        val callbackField = Class.forName("android.os.Handler").getDeclaredField("mCallback")
        callbackField.isAccessible = true

        val myCallBack = Callback {
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
                            if (pluginIntent != null) {
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


    //------------------------------------------------------------------------------------


    inner class MyHandler(private val realObject: MyInterface) : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            mBind.t1.text = System.currentTimeMillis().toString() + "：before real fun"
            val a = args ?: emptyArray()
            val result = method?.invoke(realObject, *a)
            mBind.t3.text = System.currentTimeMillis().toString() + "：after real fun"
            return result
        }
    }

    interface MyInterface {
        fun func1()
    }

    inner class MyObj : MyInterface {
        override fun func1() {
            mBind.t2.text = System.currentTimeMillis().toString() + "：execute real fun"
        }

    }
}

