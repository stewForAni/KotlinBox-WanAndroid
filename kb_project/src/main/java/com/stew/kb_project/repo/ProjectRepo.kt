package com.stew.kb_project.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_common.network.RespStateData
import com.stew.kb_project.api.ProjectApi
import com.stew.kb_project.bean.Project
import com.stew.kb_project.bean.ProjectType

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
class ProjectRepo(private val api: ProjectApi) : BaseRepository() {
    suspend fun getProTypeList(data: RespStateData<List<ProjectType>>) =
        dealResp({ api.getProType() }, data)

    suspend fun getProList(
        currentPage: Int,
        cid: Int,
        data: RespStateData<Project>
    ) = dealResp({ api.getProList(currentPage, 10, cid) }, data)

    suspend fun collect(id: Int, data: RespStateData<String>) = dealResp(
        { api.collect(id) }, data
    )

    suspend fun unCollect(id: Int, data: RespStateData<String>) = dealResp(
        { api.unCollect(id) }, data
    )
}