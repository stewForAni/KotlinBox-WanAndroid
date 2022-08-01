package com.stew.kb_home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_home.bean.Banner
import com.stew.kb_home.repo.HomeRepo

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeViewModel(private val homeRepo: HomeRepo) : BaseViewModel() {

    var bannerList = MutableLiveData<List<Banner>>()
    fun getBanner() {
        launch(
            block = { bannerList.value = homeRepo.getBanner().data }
        )
    }

}