package com.stew.kb_me.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.network.BaseStateObserver
import com.stew.kb_common.util.Constants
import com.stew.kb_me.R
import com.stew.kb_me.adapter.CollectRVAdapter
import com.stew.kb_me.bean.MyCollect
import com.stew.kb_me.bean.c
import com.stew.kb_me.databinding.FragmentCollectBinding
import com.stew.kb_me.viewmodel.MeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 8/9/22.
 * mail: stewforani@gmail.com
 */
class MyCollectFragment : BaseVMFragment<FragmentCollectBinding>() {

    private val meViewModel: MeViewModel by viewModel()
    lateinit var collectRVAdapter: CollectRVAdapter
    lateinit var lm: LinearLayoutManager
    var isLoadMore = false

    var list: MutableList<c> = arrayListOf()

    override fun getLayoutID(): Int {
        return R.layout.fragment_collect
    }

    override fun observe() {
        meViewModel.collectList.observe(this, object : BaseStateObserver<MyCollect>(null) {
            override fun getRespDataSuccess(it: MyCollect) {

                resetUI()

                currentPage = it.curPage - 1

                if (currentPage == 0) {
                    list.clear()
                }

                if (it.over) {
                    collectRVAdapter.isLastPage = true
                }

                list.addAll(it.datas)

                if (currentPage == 0) {
                    collectRVAdapter.setData(null)
                    collectRVAdapter.setData(list)
                    lm.scrollToPosition(0)
                } else {
                    collectRVAdapter.setData(list)
                }
                Log.d(TAG, "observe collectList: " + list.size)
            }

            override fun getRespDataEnd() {
                resetUI()
            }
        })
    }

    override fun init() {
        lm = LinearLayoutManager(activity)
        mBind.rvCollect.layoutManager = lm

        collectRVAdapter = CollectRVAdapter {
            val data = list[it]
            ARouter.getInstance()
                .build(Constants.PATH_WEB)
                .withString(Constants.WEB_LINK, data.link)
                .withString(Constants.WEB_TITLE, data.title)
                .navigation()
        }

        mBind.rvCollect.adapter = collectRVAdapter

        mBind.rvCollect.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && collectRVAdapter.itemCount != 0 &&
                    (lm.findLastVisibleItemPosition() + 1) == collectRVAdapter.itemCount &&
                    !isLoadMore && !collectRVAdapter.isLastPage
                ) {
                    Log.d(TAG, "onScrollStateChanged: last-----")
                    isLoadMore = true
                    getCollectList(currentPage + 1)
                }
            }
        })

        mBind.srlCollect.setColorSchemeResources(R.color.theme_color)
        mBind.srlCollect.setOnRefreshListener {
            collectRVAdapter.isLastPage = false
            getCollectList(0)
        }

        getCollectList(0)
    }

    fun getCollectList(index: Int) {
        meViewModel.getCollectList(index)
    }

    private fun resetUI() {
        isLoadMore = false
        if (mBind.srlCollect.isRefreshing) {
            mBind.srlCollect.isRefreshing = false
        }
    }

}