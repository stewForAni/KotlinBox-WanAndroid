package com.stew.kb_navigation.bean

/**
 * Created by stew on 8/8/22.
 * mail: stewforani@gmail.com
 */
data class Navi(
    val cid: Int,
    val name: String,
    val articles: List<NaviChild>
) {
    data class NaviChild(
        val id: Int,
        val link: String,
        val title: String,
    )
}
