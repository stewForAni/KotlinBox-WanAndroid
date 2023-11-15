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
        val a = MyObj()
        val p = Proxy.newProxyInstance(
            MyInterface::class.java.classLoader,
            arrayOf(MyInterface::class.java),
            MyHandler(a)
        ) as MyInterface
        p.func1()
    }
}

class MyHandler(private val realObject: MyInterface) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        println("----------------proxy 1----------------")
        val a = args ?: emptyArray()
        val result = method?.invoke(realObject, *a)
        println("----------------proxy 2----------------")
        return result
    }
}

interface MyInterface {
    fun func1()
}

class MyObj : MyInterface {
    override fun func1() {
        println("----------------MyObj----------------")
    }

}