package com.stew.kb_project.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_project.api.ProjectApi

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
class ProjectRepo(private val api: ProjectApi) : BaseRepository() {
    suspend fun getProTypeList() = api.getProType()
    suspend fun getProList(currentPage: Int, cid: Int) = api.getProList(currentPage, 10, cid)
}