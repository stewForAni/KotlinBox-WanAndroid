package com.stew.kb_exp.ui.exp

import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.AppCommonUitl
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityAppBinding

/**
 * Created by stew on 2024/1/16.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_APP)
class RestartAppActivity : BaseActivity<ActivityAppBinding>() {
    override fun getLayoutID(): Int {
        return R.layout.activity_app
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }
        mBind.tx1.setOnClickListener {
            AppCommonUitl.reStartAppByKillProcess()
        }
        mBind.tx2.setOnClickListener {
            AppCommonUitl.reStartAppByClearTop()
        }
        mBind.tx3.setOnClickListener {

        }
    }
}