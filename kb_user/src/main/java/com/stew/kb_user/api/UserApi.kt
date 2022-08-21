package com.stew.kb_user.api

import com.stew.kb_common.network.BaseResp
import com.stew.kb_user.bean.LoginBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by stew on 8/21/22.
 * mail: stewforani@gmail.com
 */
interface UserApi {
    //登录
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResp<LoginBean>
}