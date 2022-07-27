package com.stew.kotlinbox

import android.util.Log
import androidx.fragment.app.Fragment
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.ui.HomeFragment
import com.stew.kb_me.ui.MeFragment
import com.stew.kb_project.ui.ProjectFragment
import com.stew.kotlinbox.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var fragmentList: MutableList<Fragment>
    var oldFragmentIndex: Int = 0

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun init() {

        mBind.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.f1 -> {
                    Log.d(TAG, "bnv 0")
                    switchFragment(0)
                    mBind.fName = "Home"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.f2 -> {
                    Log.d(TAG, "bnv 1")
                    switchFragment(1)
                    mBind.fName = "Project"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.f3 -> {
                    Log.d(TAG, "bnv 2")
                    switchFragment(2)
                    mBind.fName = "Me"
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        fragmentList = mutableListOf(HomeFragment(), ProjectFragment(), MeFragment())

        switchFragment(0)
        mBind.fName = "Home"
    }

    private fun switchFragment(position: Int) {
        val targetFragment = fragmentList.get(position)
        val oldFragment = fragmentList.get(oldFragmentIndex)
        oldFragmentIndex = position
        val ft = supportFragmentManager.beginTransaction()
        ft.hide(oldFragment)
        if (!targetFragment.isAdded) {
            ft.add(R.id.f_content, targetFragment)
        }
        ft.show(targetFragment)
        ft.commitAllowingStateLoss()
    }

    private var exitTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showMsg("再按一次退出")
            exitTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }

}
