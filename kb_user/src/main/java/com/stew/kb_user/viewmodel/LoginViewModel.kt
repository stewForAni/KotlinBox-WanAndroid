package com.stew.kb_user.viewmodel

import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.network.RespStateData
import com.stew.kb_user.bean.LoginBean
import com.stew.kb_user.repo.LoginRepo

/**
 * Created by stew on 8/21/22.
 * mail: stewforani@gmail.com
 */
class LoginViewModel(private val repo: LoginRepo) : BaseViewModel() {

    var loginData = RespStateData<LoginBean>()

    fun login(username: String, password: String) {
        launch(
            block = {
                repo.login(username, password,loginData)
            }
        )
    }
}