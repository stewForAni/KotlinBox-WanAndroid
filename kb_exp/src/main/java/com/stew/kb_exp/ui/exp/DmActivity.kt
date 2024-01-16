package com.stew.kb_exp.ui.exp

import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.facade.annotation.Route
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.AppCommonUitl
import com.stew.kb_common.util.Constants
import com.stew.kb_exp.R
import com.stew.kb_exp.databinding.ActivityDmBinding

/**
 * Created by stew on 2024/1/14.
 * mail: stewforani@gmail.com
 */
@Route(path = Constants.PATH_DM)
class DmActivity : BaseActivity<ActivityDmBinding>() {
    override fun getLayoutID(): Int {
        return R.layout.activity_dm
    }

    override fun init() {
        mBind.imgBack.setOnClickListener { finish() }
        mBind.txLight.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        mBind.txDark.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        mBind.txSystem.setOnClickListener {
            AppCommonUitl.setFollowSystemTheme()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged: ")
    }
}