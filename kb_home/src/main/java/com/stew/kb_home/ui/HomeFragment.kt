package com.stew.kb_home.ui

import android.util.Log
import android.view.View
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_home.R
import com.stew.kb_home.adapter.BannerAdapter
import com.stew.kb_home.databinding.FragmentHomeBinding
import com.stew.kb_home.viewmodel.HomeViewModel
import com.zhpan.bannerview.constants.PageStyle
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun init() {
        mBind.banner.apply {
            setAdapter(BannerAdapter())
            setLifecycleRegistry(lifecycle)
            setScrollDuration(600)
            setInterval(5000)
            setPageStyle(PageStyle.MULTI_PAGE_SCALE)
            setRevealWidth(80)
            //setPageMargin(20)
            setIndicatorVisibility(View.INVISIBLE)
            setAutoPlay(false)
        }.create()

        homeViewModel.getBanner()
        homeViewModel.getArticle(currentPage)
    }

    override fun observe() {

        homeViewModel.bannerList.observe(this, {
            Log.d(TAG, "bannerList: " + it.size)
            mBind.banner.refreshData(it)
        })

        homeViewModel.articleList.observe(this, {
            Log.d(TAG, "articleList: " + it.size)
        })

    }

}