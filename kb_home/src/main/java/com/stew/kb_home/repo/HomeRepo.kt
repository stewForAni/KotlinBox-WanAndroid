package com.stew.kb_home.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stew.kb_common.base.BaseRepository
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.network.BaseResp
import com.stew.kb_common.network.RespStateData
import com.stew.kb_home.api.HomeApi
import com.stew.kb_home.bean.Article
import com.stew.kb_home.bean.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeRepo(private val api: HomeApi) : BaseRepository() {

    suspend fun getBanner(data: RespStateData<List<Banner>>) {
        Log.d("TestSus", "rp1")
        dealResp({ api.getBanner() }, data)
        Log.d("TestSus", "rp2")
    }

    suspend fun getArticle(currentPage: Int, data: RespStateData<Article>) = dealResp(
        { api.getArticleList(currentPage, 10) }, data
    )

    suspend fun collect(id: Int, data: RespStateData<String>) = dealResp(
        { api.collect(id) }, data
    )

    suspend fun unCollect(id: Int, data: RespStateData<String>) = dealResp(
        { api.unCollect(id) }, data
    )

    //flow test
    fun getBannerByFlow():Flow<BaseResp<List<Banner>>>{
        Log.d("HomeViewModel", "repo1")
        return flow {
            Log.d("HomeViewModel", "repo2")
            val resp = api.getBanner()
            Log.d("HomeViewModel", "repo3")
            emit(resp)
        }.flowOn(Dispatchers.IO)
    }

}