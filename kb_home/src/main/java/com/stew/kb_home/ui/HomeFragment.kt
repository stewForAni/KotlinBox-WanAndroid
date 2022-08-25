package com.stew.kb_home.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.R
import com.stew.kb_home.adapter.BannerAdapter
import com.stew.kb_home.adapter.HomeItemClickListener
import com.stew.kb_home.adapter.HomeRVAdapter
import com.stew.kb_home.bean.Article
import com.stew.kb_home.databinding.FragmentHomeBinding
import com.stew.kb_home.viewmodel.HomeViewModel
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
    var collectPosition: Int = 0

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun observe() {

        homeViewModel.bannerList.observe(this, {
            Log.d(TAG, "observe bannerList: " + it.size)
            mBind.topView.refreshData(it)
        })

        homeViewModel.article.observe(this, {
            isLoadMore = false
            list.addAll(it.datas)

            if (it.datas.size < 10) {
                homeRVAdapter.isLastPage = true
            }

            Log.d(TAG, "observe articleList: " + list.size)

            if (currentPage == 0) {
                homeRVAdapter.setData(null)
                homeRVAdapter.setData(list)
                lm.scrollToPosition(0)
            } else {
                homeRVAdapter.setData(list)
            }

            if (mBind.srlHome.isRefreshing) {
                mBind.srlHome.isRefreshing = false
            }

        })

        homeViewModel.collectData.observe(this, {
            dismissLoadingDialog()
            if (list[collectPosition].collect) {
                ToastUtil.showMsg("取消收藏！")
                list[collectPosition].collect = false
            } else {
                ToastUtil.showMsg("收藏成功！")
                list[collectPosition].collect = true
            }
            homeRVAdapter.notifyItemChanged(collectPosition)
        })

    }

    override fun init() {

        mBind.topView.apply {
            setAdapter(BannerAdapter())
            setLifecycleRegistry(lifecycle)
            setScrollDuration(600)
            setInterval(5000)
            setAutoPlay(false)
        }.create()

        lm = LinearLayoutManager(activity)
        mBind.bottomView.layoutManager = lm

        homeRVAdapter = HomeRVAdapter(object : HomeItemClickListener {
            override fun onItemClick(position: Int) {
                val data = list[position]
                ARouter.getInstance()
                    .build(Constants.PATH_WEB)
                    .withString(Constants.WEB_LINK, data.link)
                    .withString(Constants.WEB_TITLE, data.title)
                    .navigation()
            }

            override fun onCollectClick(position: Int) {
                showLoadingDialog()//暂时处理，应该设计到框架内
                collectPosition = position
                if (list[collectPosition].collect) {
                    homeViewModel.unCollect(list[collectPosition].id)
                } else {
                    homeViewModel.collect(list[collectPosition].id)
                }
            }

        })

        mBind.bottomView.adapter = homeRVAdapter

        mBind.bottomView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                    (lm.findLastVisibleItemPosition() + 1) == homeRVAdapter.itemCount && !isLoadMore && !homeRVAdapter.isLastPage
                ) {
                    Log.d(TAG, "onScrollStateChanged: last-----")
                    isLoadMore = true
                    currentPage++
                    homeViewModel.getArticle(currentPage)
                }
            }
        })


        mBind.srlHome.setColorSchemeResources(R.color.theme_color)
        mBind.srlHome.setOnRefreshListener {
            isLoadMore = false
            homeRVAdapter.isLastPage = false
            list.clear()
            currentPage = 0
            homeViewModel.getArticle(currentPage)
        }

        homeViewModel.getBanner()
        homeViewModel.getArticle(currentPage)
    }

}