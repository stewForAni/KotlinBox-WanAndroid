package com.stew.kotlinbox.exp

import android.content.ComponentName
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kotlinbox.R
import com.stew.kotlinbox.databinding.ActivityDpBinding
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
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

    override fun init() {

        mBind.imgBack.setOnClickListener { finish() }

        println("MyInterface:" + MyInterface::class.java.classLoader.getScopeId())
        println("Thread:" + Thread.currentThread().contextClassLoader.getScopeId())

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

        //PluginActvity 替换成 ProxyActivity
        hookIActivityTaskManager()
        //ProxyActivity 还原成 PluginActvity
        hookActivityThreadH()
        mBind.t4.text = "hook iatm and activityThread-H"
        mBind.btn.setOnClickListener {
            finish()
        }

    }

    //ActivityTaskManager.getService().startActivity(...intent...)
    private fun hookIActivityTaskManager() {

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
        ) { proxy, method, args ->
            println("method:" + method?.name)
            if (method.name.equals("startActivity")) {
                for (i in args.indices) {
                    if (args[i] is Intent) {
                        val pluginIntent = args[i] as Intent //plugin activity intent
                        val newIntent = Intent()
                        newIntent.component = ComponentName("com.stew.kotlinbox.exp",ProxyActivity::javaClass.name)
                        newIntent.putExtra("",pluginIntent)
                        args[i] = newIntent
                        break
                    }
                }

            }
            method?.invoke(iatm, *(args ?: emptyArray()))
        }

        field2.set(obj1, proxyObj)
    }

    private fun hookActivityThreadH() {

    }







    //------------------------------------------------------------------------------------



    inner class MyHandler(private val realObject: MyInterface) : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            println("----------------proxy 1----------------")
            mBind.t1.text = System.currentTimeMillis().toString() + "：before real fun"
            val a = args ?: emptyArray()
            val result = method?.invoke(realObject, *a)
            println("----------------proxy 2----------------")
            mBind.t3.text = System.currentTimeMillis().toString() + "：after real fun"
            return result
        }
    }

    interface MyInterface {
        fun func1()
    }

    inner class MyObj : MyInterface {
        override fun func1() {
            println("----------------MyObj----------------")
            mBind.t2.text = System.currentTimeMillis().toString() + "：execute real fun"
        }

    }
}

