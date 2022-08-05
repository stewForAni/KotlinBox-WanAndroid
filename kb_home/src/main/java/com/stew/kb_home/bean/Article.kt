package com.stew.kb_home.bean

/**
 * Created by stew on 8/2/22.
 * mail: stewforani@gmail.com
 */
data class Article(
    val datas: List<ArticleDetail>,
) {
    data class ArticleDetail(
        val author: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val shareUser: String,
        val title: String,
        val superChapterId: Int,
        val superChapterName: String,
    )
}

