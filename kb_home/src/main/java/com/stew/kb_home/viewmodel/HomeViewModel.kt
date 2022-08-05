package com.stew.kb_home.viewmodel

import android.util.Log
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
    var articleList = MutableLiveData<List<Article.ArticleDetail>>()

    fun getBanner() {
        launch(
            block = { bannerList.value = repo.getBanner().data }
        )
    }

    fun getArticle(currentPage: Int) {
        launch(
            block = { articleList.value = repo.getArticle(currentPage).data?.datas }
        )
    }

}