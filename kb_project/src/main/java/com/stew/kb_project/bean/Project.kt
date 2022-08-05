package com.stew.kb_project.bean

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
data class Project(
    val datas: List<ProjectDetail>
) {
    data class ProjectDetail(
        val id: Int,
        val author: String,
        val link: String,
        val niceDate: String,
        val title: String,
    )
}
