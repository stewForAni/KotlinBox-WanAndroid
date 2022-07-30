package com.stew.kb_home.api

import com.stew.kb_common.network.BaseResp
import com.stew.kb_home.bean.Banner
import retrofit2.http.GET

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
interface HomeApi {
    //首页banner
    @GET("banner/json")
    suspend fun getBanner(): BaseResp<List<Banner>>
}