package com.stew.kb_home.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_common.network.RespStateData
import com.stew.kb_home.api.HomeApi
import com.stew.kb_home.bean.Article
import com.stew.kb_home.bean.Banner

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeRepo(private val api: HomeApi) : BaseRepository() {

    suspend fun getBanner(data: RespStateData<List<Banner>>) = dealResp(
        { api.getBanner() }, data
    )

    suspend fun getArticle(currentPage: Int, data: RespStateData<Article>) = dealResp(
        { api.getArticleList(currentPage, 10) }, data
    )

    suspend fun collect(id: Int, data: RespStateData<String>) =
        dealResp({ api.collect(id) }, data)

    suspend fun unCollect(id: Int, data: RespStateData<String>) = dealResp(
        { api.unCollect(id) }, data
    )

}