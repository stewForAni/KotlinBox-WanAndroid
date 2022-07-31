package com.stew.kb_home.viewmodel

import android.util.Log
import com.stew.kb_common.base.BLOCK
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.base.ERROR
import com.stew.kb_home.repo.HomeRepo

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeViewModel(private val homeRepo: HomeRepo) : BaseViewModel() {

    //private val homeRepo: HomeRepo by lazy { HomeRepo() }

    fun getBanner() {
        Log.d("HomeViewModel", "homeRepo: $homeRepo")
        homeRepo.test()
        Log.d("HomeViewModel", "homeRepo: $homeRepo")
    }

    fun getHomeList() {

    }
}