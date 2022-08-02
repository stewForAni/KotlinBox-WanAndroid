package com.stew.kb_home.api

import com.stew.kb_common.network.BaseResp
import com.stew.kb_home.bean.Article
import com.stew.kb_home.bean.Banner
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
interface HomeApi {

    //首页banner
    @GET("banner/json")
    suspend fun getBanner(): BaseResp<List<Banner>>

    //首页文章列表
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseResp<Article>

}