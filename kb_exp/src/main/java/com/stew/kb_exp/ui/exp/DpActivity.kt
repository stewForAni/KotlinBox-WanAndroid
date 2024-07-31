package com.stew.kb_exp.ui.exp

import android.content.Intent
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityDpBinding
import com.stew.kb_exp.util.AssetsUtil
import com.stew.kb_exp.util.PluginLoadUtil
import org.apache.commons.io.FileUtils
import org.koin.core.component.getScopeId
import java.io.File
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

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
        mBind.t.text = "MyInterface:" + MyInterface::class.java.classLoader.getScopeId()
        mBind.t0.text = "Thread:" + Thread.currentThread().contextClassLoader.getScopeId()

//        val a = MyObj()
//        val p = Proxy.newProxyInstance(
//            MyInterface::class.java.classLoader,
//            arrayOf(MyInterface::class.java),
//            MyHandler(a)
//        ) as MyInterface
//        p.func1()


        //------------------------------------------------------------------------------------

        if (!Constants.isStartDP) {
            //PluginActvity 替换成 ProxyActivity（binder传递消息到AMS之前）
            //请注意，这个方法会影响整个app的跳转逻辑，因为
            PluginLoadUtil.hookIActivityTaskManager()
            //ProxyActivity 还原成 PluginActvity（handler处理消息之前，关键点：intent初始化于LaunchActivityItem中）
            PluginLoadUtil.hookActivityThreadH()
        }

        mBind.t4.text = "hook iatm and activityThread-H"
        mBind.btn.setOnClickListener {
            startActivity(Intent(this, PluginActivity::class.java))
        }

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

