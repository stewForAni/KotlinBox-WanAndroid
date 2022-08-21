package com.stew.kb_user.repo

import com.stew.kb_common.base.BaseRepository
import com.stew.kb_user.api.UserApi

/**
 * Created by stew on 8/21/22.
 * mail: stewforani@gmail.com
 */
class LoginRepo(private val api: UserApi) : BaseRepository() {
    suspend fun login(username: String, password: String) = api.login(username, password)
}