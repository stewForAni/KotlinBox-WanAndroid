package com.stew.kotlinbox.exp

import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kotlinbox.R
import com.stew.kotlinbox.databinding.ActivityDpBinding
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
        println("MyInterface:" + MyInterface::class.java.classLoader?.toString())
        println("Thread:" + Thread.currentThread().contextClassLoader?.toString())
        val a = MyObj()
        val p = Proxy.newProxyInstance(
            MyInterface::class.java.classLoader,
            arrayOf(MyInterface::class.java),
            MyHandler(a)
        ) as MyInterface
        p.func1()
    }

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

