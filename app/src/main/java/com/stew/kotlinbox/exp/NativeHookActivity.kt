package com.stew.kotlinbox.exp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.exp.TestMallocUtil
import com.stew.kb_common.util.Constants
import com.stew.kotlinbox.R
import com.stew.kotlinbox.databinding.ActivityNhBinding

/**
 * Created by stew on 2023/10/19.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_NH)
class NativeHookActivity : BaseActivity<ActivityNhBinding>() {


    override fun getLayoutID(): Int {
        return R.layout.activity_nh
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }

        mBind.txStart.setOnClickListener {
            TestMallocUtil.startHook()
        }

        mBind.txTest.setOnClickListener {
            TestMallocUtil.testMalloc()
        }
    }
}