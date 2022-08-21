package com.stew.kb_user.bean

/**
 * Created by stew on 8/21/22.
 * mail: stewforani@gmail.com
 */
data class LoginBean(
    var admin: Boolean,
    var chapterTops: List<Any>,
    var coinCount: Int,
    var collectIds: List<Int>,
    var email: String,
    var icon: String,
    var id: Int,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String
)
