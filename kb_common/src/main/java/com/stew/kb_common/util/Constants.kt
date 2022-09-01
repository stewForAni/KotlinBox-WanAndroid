package com.stew.kb_common.util

/**
 * Created by stew on 8/18/22.
 * mail: stewforani@gmail.com
 */
class Constants {
    companion object {
        const val WEB_TITLE: String = "web_title"
        const val WEB_LINK: String = "web_link"
        const val MIN_CLICK_DELAY_TIME: Int = 1000
        const val PATH_WEB: String = "/kb_common/ui/WebActivity"
        const val PATH_LOGIN: String = "/kb_user/ui/loginActivity"

        //kv
        const val USER_NAME: String = "user_name"
        const val USER_COOKIE: String = "user_cookie"

        //http
        const val HTTP_SUCCESS = 0
        const val HTTP_AUTH_INVALID = -1001

    }
}