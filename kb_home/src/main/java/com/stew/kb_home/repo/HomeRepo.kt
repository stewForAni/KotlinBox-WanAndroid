package com.stew.kb_home.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_home.api.HomeApi

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeRepo(private val api: HomeApi) : BaseRepository() {
    suspend fun getBanner() = api.getBanner()
    suspend fun getArticle(currentPage: Int) = api.getArticleList(currentPage, 10)
}