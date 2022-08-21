package com.stew.kotlinbox

import androidx.fragment.app.Fragment
import com.stew.kb_common.base.BaseActivity
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.ui.HomeFragment
import com.stew.kb_me.ui.MeFragment
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

        mBind.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.f1 -> {
                    switchFragment(0)
                    mBind.fName = "首页"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.f2 -> {
                    switchFragment(1)
                    mBind.fName = "项目"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.f3 -> {
                    switchFragment(2)
                    mBind.fName = "导航"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.f4 -> {
                    switchFragment(3)
                    mBind.fName = "收藏"
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        fragmentList = mutableListOf(
            HomeFragment(),
            ProjectFragment(),
            MainFragment(),
            MeFragment()
        )

        switchFragment(0)
        mBind.fName = "首页"
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
        ft.show(targetFragment).commitAllowingStateLoss()
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
