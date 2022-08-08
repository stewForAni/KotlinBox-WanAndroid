package com.stew.kb_navigation.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_navigation.api.NaviApi


/**
 * Created by stew on 8/7/22.
 * mail: stewforani@gmail.com
 */
class NaviRepo(private val api: NaviApi) : BaseRepository() {
    suspend fun getSys() = api.getSys()
}