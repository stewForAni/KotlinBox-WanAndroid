package com.stew.kb_navigation.bean

import java.lang.StringBuilder

/**
 * Created by stew on 8/7/22.
 * mail: stewforani@gmail.com
 */
typealias s = Sys.SysChild

data class Sys(
    val id: Int,
    val name: String,
    val children: List<SysChild>
) {
    data class SysChild(
        val id: Int,
        val courseId: Int,
        val name: String,

    )
}
