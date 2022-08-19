package com.stew.kb_home.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_common.util.Constants
import com.stew.kb_home.R
import com.stew.kb_home.adapter.BannerAdapter
import com.stew.kb_home.adapter.HomeItemClickListener
import com.stew.kb_home.adapter.HomeRVAdapter
import com.stew.kb_home.bean.Article
import com.stew.kb_home.bean.a
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
    lateinit var homeRVAdapter: HomeRVAdapter
    lateinit var lm: LinearLayoutManager
    var isLoadMore = false
    var list: MutableList<Article.ArticleDetail> = arrayListOf()

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun observe() {

        homeViewModel.bannerList.observe(this, {
            mBind.topView.refreshData(it)
        })

        homeViewModel.articleList.observe(this, {
            //AsyncListDiffer需要一个新数据，不然添加无效

            val newList: MutableList<a> = arrayListOf()
            isLoadMore = false
            list.addAll(it)
            newList.addAll(list)
            Log.d(TAG, "observe: " + newList.size)
            homeRVAdapter.setData(newList)

            if (mBind.srl.isRefreshing) {
                mBind.srl.isRefreshing = false
            }
        })

    }

    override fun init() {
        mBind.srl.setColorSchemeResources(R.color.theme_color)
        mBind.srl.setOnRefreshListener {
            isLoadMore = false
            list.clear()
            currentPage = 0
            homeViewModel.getArticle(currentPage)
        }

        mBind.topView.apply {
            setAdapter(BannerAdapter())
            setLifecycleRegistry(lifecycle)
            setScrollDuration(600)
            setInterval(5000)
            setAutoPlay(false)
            //setPageStyle(PageStyle.MULTI_PAGE_SCALE)
            //setRevealWidth(80)
            //setPageMargin(20)
            //setIndicatorVisibility(View.INVISIBLE)
        }.create()

        lm = LinearLayoutManager(activity)
        mBind.bottomView.layoutManager = lm

        homeRVAdapter = HomeRVAdapter {
            val data = list[it]
            Log.d(TAG, "t: " + data.title)
            Log.d(TAG, "l: " + data.link)
            ARouter.getInstance()
                .build(Constants.PATH_WEB)
                .withString(Constants.WEB_LINK, data.link)
                .withString(Constants.WEB_TITLE, data.title)
                .navigation()
        }

        mBind.bottomView.adapter = homeRVAdapter

        mBind.bottomView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                    (lm.findLastVisibleItemPosition() + 1) == homeRVAdapter.itemCount && !isLoadMore
                ) {
                    Log.d(TAG, "onScrollStateChanged: last-----")
                    isLoadMore = true
                    currentPage++
                    homeViewModel.getArticle(currentPage)
                }
            }
        })

        homeViewModel.getBanner()
        homeViewModel.getArticle(currentPage)
    }

}