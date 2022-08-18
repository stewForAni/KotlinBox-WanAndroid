package com.stew.kb_common.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.R
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.databinding.ActivityWebBinding
import com.stew.kb_common.util.Constants

/**
 * Created by stew on 8/18/22.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_WEB)
class WebActivity:BaseActivity<ActivityWebBinding>() {
    override fun getLayoutID(): Int {
        return R.layout.activity_web
    }

    override fun init() {
        mBind.web.loadUrl("https://www.baidu.com/")
    }
}