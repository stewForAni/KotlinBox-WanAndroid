package com.stew.kb_me.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_common.network.RespStateData
import com.stew.kb_me.api.MeApi
import com.stew.kb_me.bean.MyCollect

/**
 * Created by stew on 8/25/22.
 * mail: stewforani@gmail.com
 */
class MeRepo(private val api: MeApi) : BaseRepository() {

    suspend fun getCollectList(currentPage: Int, data: RespStateData<MyCollect>) =
        dealResp({ api.getCollectList(currentPage, 10) }, data)

}