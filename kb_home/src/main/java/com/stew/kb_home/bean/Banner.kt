package com.stew.kb_home.bean

/**
 * Created by stew on 7/30/22.
 * mail: stewforani@gmail.com
 */
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)
