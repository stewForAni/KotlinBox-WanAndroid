package com.stew.kb_home.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_home.api.HomeApi

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeRepo(private val homeApi: HomeApi) : BaseRepository() {
    suspend fun getBanner() = homeApi.getBanner()
}