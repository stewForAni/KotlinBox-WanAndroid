package com.stew.kb_navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_navigation.bean.Sys
import com.stew.kb_navigation.repo.NaviRepo

/**
 * Created by stew on 8/7/22.
 * mail: stewforani@gmail.com
 */
class NaviViewModel(private val repo: NaviRepo) : BaseViewModel() {

    var sysList = MutableLiveData<List<Sys>>()

    fun getSys() = launch(
        block = { sysList.value = repo.getSys().data }
    )
}