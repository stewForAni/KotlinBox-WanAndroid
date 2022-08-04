package com.stew.kotlinbox

import android.content.Intent
import com.stew.kb_common.base.BaseActivity
import com.stew.kotlinbox.databinding.ActivitySplashBinding

/**
 * Created by stew on 4/21/22.
 * mail: stewforani@gmail.com
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutID(): Int {
        return R.layout.activity_splash
    }

    override fun init() {
//        Thread.sleep(1000)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}