package com.stew.kotlinbox

import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bytedance.android.bytehook.ByteHook
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kotlinbox.databinding.ActivityExpBinding

/**
 * Created by stew on 2023/10/18.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_EXP)
class ExpActivity : BaseActivity<ActivityExpBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.activity_exp
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }

        mBind.txNh.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_NH)
                .navigation()
        }

        mBind.txPi.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_PI)
                .navigation()
        }

        mBind.txDp.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_DP)
                .navigation()
        }

    }

}