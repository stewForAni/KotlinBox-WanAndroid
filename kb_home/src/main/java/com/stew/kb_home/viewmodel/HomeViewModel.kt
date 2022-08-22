package com.stew.kb_home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_home.bean.Article
import com.stew.kb_home.bean.Banner
import com.stew.kb_home.repo.HomeRepo

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {

    var bannerList = MutableLiveData<List<Banner>>()
    var article = MutableLiveData<Article>()
    var collectData = MutableLiveData<String>()

    fun getBanner() {
        launch(
            block = { repo.getBanner(bannerList) }
        )
    }

    fun getArticle(currentPage: Int) {
        launch(
            block = { repo.getArticle(currentPage, article) }
        )
    }

    fun collect(id: Int) {
        launch(
            block = { repo.collect(id, collectData) }
        )
    }
}