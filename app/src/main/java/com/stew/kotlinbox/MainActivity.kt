package com.stew.kotlinbox

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.os.Trace
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.KVUtil
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.ui.HomeFragment
import com.stew.kb_me.ui.MyCollectFragment
import com.stew.kb_navigation.ui.MainFragment
import com.stew.kb_project.ui.ProjectFragment
import com.stew.kotlinbox.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var fragmentList: MutableList<Fragment>
    var oldFragmentIndex: Int = 0

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun init() {

        mBind.imgDraw.setOnClickListener {
            mBind.dl.open()
        }

        mBind.imgExp.setOnClickListener {
            ARouter.getInstance()
                .build(Constants.PATH_EXP)
                .navigation()
        }

        mBind.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.f1 -> {
//                    Log.d("mem_info_test", Runtime.getRuntime().totalMemory().toString())
//                    Log.d("mem_info_test", Runtime.getRuntime().freeMemory().toString())
//                    Log.d("mem_info_test", Debug.getNativeHeapSize().toString())
//                    Log.d("mem_info_test", Debug.getNativeHeapFreeSize().toString())
                    switchFragment(0)
                    mBind.fName = "首页"
                    return@setOnItemSelectedListener true
                }
                R.id.f2 -> {
                    switchFragment(1)
                    mBind.fName = "项目"
                    return@setOnItemSelectedListener true
                }
                R.id.f3 -> {
                    switchFragment(2)
                    mBind.fName = "导航"
                    return@setOnItemSelectedListener true
                }
                R.id.f4 -> {
                    switchFragment(3)
                    mBind.fName = "收藏"
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        fragmentList = mutableListOf(
            HomeFragment(),
            ProjectFragment(),
            MainFragment(),
            MyCollectFragment()
        )

        switchFragment(0)
        mBind.fName = "首页"

        resources
    }

    private fun switchFragment(position: Int) {

        val targetFragment = fragmentList[position]
        val oldFragment = fragmentList[oldFragmentIndex]
        oldFragmentIndex = position
        val ft = supportFragmentManager.beginTransaction()

        if (oldFragment.isAdded) {
            ft.hide(oldFragment)
        }

        if (!targetFragment.isAdded) {
            ft.add(R.id.f_content, targetFragment)
        }

        ft.show(targetFragment).commitAllowingStateLoss()
    }


    override fun onResume() {
        super.onResume()
        val name = KVUtil.getString(Constants.USER_NAME)
        if (name != null) {
            findViewById<TextView>(R.id.tx_info).text = name
        }
    }

}
