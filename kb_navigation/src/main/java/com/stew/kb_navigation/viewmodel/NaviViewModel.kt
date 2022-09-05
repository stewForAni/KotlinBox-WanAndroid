package com.stew.kb_navigation.viewmodel


import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.network.RespStateData
import com.stew.kb_navigation.bean.Navi
import com.stew.kb_navigation.bean.Sys
import com.stew.kb_navigation.repo.NaviRepo

/**
 * Created by stew on 8/7/22.
 * mail: stewforani@gmail.com
 */
class NaviViewModel(private val repo: NaviRepo) : BaseViewModel() {

    var naviList = RespStateData<List<Navi>>()
    var sysList = RespStateData<List<Sys>>()

    fun getSys() = launch { repo.getSys(sysList) }
    fun getNavi() { launch { repo.getNavi(naviList) }}
}