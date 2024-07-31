package com.stew.kb_exp.ui.exp

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityTpBinding
import com.stew.kb_exp.util.AssetsUtil
import com.stew.kb_exp.util.PluginLoadUtil
import java.io.File

/**
 * Created by stew on 2024/7/30.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_TP)
class TestPluginApkActivity : BaseActivity<ActivityTpBinding>() {



    override fun getLayoutID(): Int {
        return R.layout.activity_tp
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }

        mBind.btn1.setOnClickListener {
            PluginLoadUtil.init(this)
        }

        mBind.btn2.setOnClickListener {
            if (!Constants.isStartDP) {
                Log.d(TAG, "hook start")
                //PluginActvity 替换成 ProxyActivity（binder传递消息到AMS之前）
                //请注意，这个方法会影响整个app的跳转逻辑
                PluginLoadUtil.hookIActivityTaskManager()
                //ProxyActivity 还原成 PluginActvity（handler处理消息之前，关键点：intent初始化于LaunchActivityItem中）
                PluginLoadUtil.hookActivityThreadH()
            }
        }

        mBind.btn3.setOnClickListener {
            val c = PluginLoadUtil.pluginDexClassLoader.loadClass("com.stew.pluginapp.ui.PluginTestActivity")
            val intent = Intent(this, c)
            startActivity(intent)

//            非activity的类是可以成功的！！！！
//            val c = PluginLoadUtil.pluginDexClassLoader.loadClass("com.stew.pluginapp.utils.PluginString")
//            val m = c.getMethod("getMsg")
//            m.isAccessible = true
//            ToastUtil.showMsg(m.invoke(c.newInstance())!!.toString())

        }
    }
}