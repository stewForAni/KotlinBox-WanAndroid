package com.stew.kb_home.ui

import android.util.Log
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_home.R
import com.stew.kb_home.databinding.FragmentHomeBinding
import com.stew.kb_home.viewmodel.HomeViewModel

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun init() {
        var homeViewModel = HomeViewModel()
    }

    override fun observe() {

    }

}