package com.stew.kb_me.bean

/**
 * Created by stew on 8/25/22.
 * mail: stewforani@gmail.com
 */
typealias c = MyCollect.MyCollectDetail

data class MyCollect(
    val datas: List<MyCollectDetail>,
    val over:Boolean,
    val curPage: Int
) {
    data class MyCollectDetail(
        val author: String,
        val chapterName: String,
        val niceDate: String,
        val title: String,
        val link: String,
        val id: Int
    )
}
