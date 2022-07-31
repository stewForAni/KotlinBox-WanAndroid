package com.stew.kb_home.viewmodel

import android.util.Log
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_home.repo.HomeRepo

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeViewModel(private val homeRepo: HomeRepo) : BaseViewModel() {

    fun getBanner() {
        Log.d("HomeViewModel", "homeRepo: $homeRepo")
        homeRepo.test()
        Log.d("HomeViewModel", "homeRepo: $homeRepo")
    }

    fun getHomeList() {

    }
}