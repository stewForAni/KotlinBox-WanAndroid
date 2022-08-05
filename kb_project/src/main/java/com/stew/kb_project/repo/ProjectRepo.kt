package com.stew.kb_project.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_project.api.ProjectApi

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
class ProjectRepo(private val projectApi: ProjectApi) : BaseRepository() {
    suspend fun getProTypeList() = projectApi.getProType()
    suspend fun getProList(currentPage: Int) = projectApi.getProList(currentPage, 10)
}