package com.stew.kb_navigation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.stew.kb_common.base.BaseFragment
import com.stew.kb_navigation.R
import com.stew.kb_navigation.adapter.NaviVPAdapter
import com.stew.kb_navigation.databinding.FragmentMainBinding
import com.stew.kb_navigation.viewmodel.NaviViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 8/8/22.
 * mail: stewforani@gmail.com
 */
class MainFragment:BaseFragment<FragmentMainBinding>() {

    val naviViewModel: NaviViewModel by viewModel()
    private lateinit var l: MutableList<String>
    private lateinit var f: MutableList<Fragment>

    override fun getLayoutID(): Int {
        return R.layout.fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        l = arrayListOf("导航","体系")
        f = arrayListOf(NaviFragment(),SysFragment())

        mBind.viewPager.adapter = NaviVPAdapter(this, f)
        TabLayoutMediator(mBind.tabLayout, mBind.viewPager) { tab, position ->
            tab.text = l[position]
        }.attach()
    }
}