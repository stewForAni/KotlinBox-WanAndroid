package com.stew.kb_me.bean

/**
 * Created by stew on 8/25/22.
 * mail: stewforani@gmail.com
 */
typealias c = MyCollect.MyCollectDetail
data class MyCollect(
    val datas: List<MyCollectDetail>
) {
    data class MyCollectDetail(
        val author: String,
        val niceDate: String,
        val title: String,
        val link: String,
        val id: Int
    )
}
