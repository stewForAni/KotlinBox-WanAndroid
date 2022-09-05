package com.stew.kb_me.viewmodel

import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.network.RespStateData
import com.stew.kb_me.bean.MyCollect
import com.stew.kb_me.repo.MeRepo

/**
 * Created by stew on 8/25/22.
 * mail: stewforani@gmail.com
 */
class MeViewModel(private val repo: MeRepo) : BaseViewModel() {

    val collectList = RespStateData<MyCollect>()

    fun getCollectList(currentPage: Int) = launch { repo.getCollectList(currentPage, collectList) }
}