package com.stew.kb_me.api

import com.stew.kb_common.network.BaseResp
import com.stew.kb_me.bean.MyCollect
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by stew on 8/25/22.
 * mail: stewforani@gmail.com
 */
interface MeApi {
    //首页文章列表
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(
        @Path("page") page: Int,
        @Query("page_size") page_size: Int
    ): BaseResp<MyCollect>
}