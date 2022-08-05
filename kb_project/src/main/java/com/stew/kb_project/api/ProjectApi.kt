package com.stew.kb_project.api

import com.stew.kb_common.network.BaseResp
import com.stew.kb_project.bean.Project
import com.stew.kb_project.bean.ProjectType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
interface ProjectApi {

    //获取项目分类
    @GET("project/tree/json")
    suspend fun getProType(): BaseResp<List<ProjectType>>

    //获取项目列表
    @GET("project/list/{page}/json")
    suspend fun getProList(
        @Path("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("cid") cid: Int
    ): BaseResp<Project>

}