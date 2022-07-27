package com.stew.kotlinbox

import com.stew.kb_common.base.BaseActivity
import com.stew.kotlinbox.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        mBind?.bnv?.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.f1 -> return@setOnNavigationItemSelectedListener true
                R.id.f2 -> return@setOnNavigationItemSelectedListener true
                R.id.f3 -> return@setOnNavigationItemSelectedListener true
            }
            false
        }
    }

}
