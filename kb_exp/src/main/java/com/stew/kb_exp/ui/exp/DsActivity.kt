package com.stew.kb_exp.ui.exp

import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityDsBinding

/**
 * Created by stew on 2024/1/1.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_DS)
class DsActivity : BaseActivity<ActivityDsBinding>() {
    override fun getLayoutID(): Int {
        return R.layout.activity_ds
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }


    }
}