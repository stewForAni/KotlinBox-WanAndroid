package com.stew.kb_navigation.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_common.network.RespStateData
import com.stew.kb_navigation.api.NaviApi
import com.stew.kb_navigation.bean.Navi
import com.stew.kb_navigation.bean.Sys


/**
 * Created by stew on 8/7/22.
 * mail: stewforani@gmail.com
 */
class NaviRepo(private val api: NaviApi) : BaseRepository() {
    suspend fun getSys(data: RespStateData<List<Sys>>) = dealResp({ api.getSys() }, data)
    suspend fun getNavi(data: RespStateData<List<Navi>>) = dealResp({ api.getNavi() }, data)
}