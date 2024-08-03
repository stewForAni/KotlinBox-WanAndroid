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
import dalvik.system.DexClassLoader
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
            PluginLoadUtil.init(this, filesDir.absolutePath + File.separator + "app-debug.apk")
        }

        mBind.btn3.setOnClickListener {
            val c = PluginLoadUtil.pd.loadClass("com.stew.pluginapp.ui.PluginMainActivity")
            val intent = Intent(this, c)
            startActivity(intent)

//            非activity的类是可以成功的！！！！
//            val c1 = PluginLoadUtil.pd.loadClass("com.stew.pluginapp.utils.PluginString")
//            val m = c1.getMethod("getMsg")
//            m.isAccessible = true
//            ToastUtil.showMsg(m.invoke(c1.newInstance())!!.toString())
        }

    }


}