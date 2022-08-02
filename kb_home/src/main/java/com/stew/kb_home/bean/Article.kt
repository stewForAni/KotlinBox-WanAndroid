package com.stew.kb_home.bean

/**
 * Created by stew on 8/2/22.
 * mail: stewforani@gmail.com
 */
data class Article(
    val curPage: Int,
    val datas: List<ArticleDetail>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) {
    data class ArticleDetail(
        val author: String,
        var collect: Boolean,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val shareUser: String,
        val title: String,
    )
}

