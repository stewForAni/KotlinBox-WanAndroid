package com.stew.kb_home.viewmodel

import android.util.Log
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.network.BaseResp
import com.stew.kb_common.network.RespStateData
import com.stew.kb_home.bean.Article
import com.stew.kb_home.bean.Banner
import com.stew.kb_home.repo.HomeRepo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {

    var bannerList = RespStateData<List<Banner>>()
    var article = RespStateData<Article>()
    var collectData = RespStateData<String>()

    fun getBanner() = launch {
        Log.d("TestSus", "vm1")
        repo.getBanner(bannerList)
        Log.d("TestSus", "vm2")
    }
    fun getArticle(currentPage: Int) = launch { repo.getArticle(currentPage, article) }
    fun collect(id: Int) = launch { repo.collect(id, collectData) }
    fun unCollect(id: Int) = launch { repo.unCollect(id, collectData) }


    //flow test
    fun getBannerByFlow() = launch {
        repo.getBannerByFlow().onStart {
            Log.d("HomeViewModel", "getBannerByFlow start")
        }.onCompletion {
            Log.d("HomeViewModel", "getBannerByFlow end")
        }.collectLatest {

            it.responseState = BaseResp.ResponseState.REQUEST_SUCCESS
            bannerList.value = it
            Log.d("HomeViewModel", "getBannerByFlow success")
        }
    }
}