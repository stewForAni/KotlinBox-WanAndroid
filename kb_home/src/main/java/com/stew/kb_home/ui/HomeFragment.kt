package com.stew.kb_home.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_common.network.BaseStateObserver
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_home.R
import com.stew.kb_home.adapter.BannerAdapter
import com.stew.kb_home.adapter.HomeItemClickListener
import com.stew.kb_home.adapter.HomeRVAdapter
import com.stew.kb_home.bean.Article
import com.stew.kb_home.bean.Banner
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

        homeViewModel.bannerList.observe(this, object : BaseStateObserver<List<Banner>>(null) {
            override fun getRespDataSuccess(it: List<Banner>) {
                Log.d(TAG, "observe bannerList: " + it.size)
                if (it.isEmpty()) {
                    return
                }
                mBind.topView.refreshData(it)
            }

            override fun getRespDataEnd() {
                resetUI()
            }
        })

        homeViewModel.article.observe(this, object : BaseStateObserver<Article>(null) {
            override fun getRespDataSuccess(it: Article) {

                resetUI()

                currentPage = it.curPage - 1

                //下拉刷新
                if (currentPage == 0) {
                    list.clear()
                }

                //最后一页
                if (it.over) {
                    homeRVAdapter.isLastPage = true
                }

                //list添加数据
                list.addAll(it.datas)

                //处理数据
                if (currentPage == 0) {
                    homeRVAdapter.setData(null)
                    homeRVAdapter.setData(list)
                    lm.scrollToPosition(0)
                } else {
                    homeRVAdapter.setData(list)
                }

                Log.d(TAG, "observe articleList: " + list.size)

            }

            override fun getRespDataEnd() {
                resetUI()
            }
        })

        homeViewModel.collectData.observe(this, object : BaseStateObserver<String>(null) {
            override fun getRespDataStart() {
                showLoadingDialog()
            }

            override fun getRespDataEnd() {
                dismissLoadingDialog()
            }

            override fun getRespSuccess() {
                dismissLoadingDialog()
                if (list[collectPosition].collect) {
                    ToastUtil.showMsg("取消收藏！")
                    list[collectPosition].collect = false
                } else {
                    ToastUtil.showMsg("收藏成功！")
                    list[collectPosition].collect = true
                }
                homeRVAdapter.notifyItemChanged(collectPosition)
            }

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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && homeRVAdapter.itemCount != 0 &&
                    (lm.findLastVisibleItemPosition() + 1) == homeRVAdapter.itemCount && !isLoadMore && !homeRVAdapter.isLastPage
                ) {
                    Log.d(TAG, "onScrollStateChanged: last-----")
                    isLoadMore = true
                    homeViewModel.getArticle(currentPage + 1)
                }
            }
        })


        mBind.srlHome.setColorSchemeResources(R.color.theme_color)
        mBind.srlHome.setOnRefreshListener {
            homeRVAdapter.isLastPage = false
            getHomeData()
        }

        getHomeData()
    }

    private fun getHomeData() {
        homeViewModel.getBanner()
        homeViewModel.getArticle(0)
    }

    private fun resetUI() {
        isLoadMore = false//加载更多完成，重置false
        if (mBind.srlHome.isRefreshing) {
            mBind.srlHome.isRefreshing = false
        }
    }

}