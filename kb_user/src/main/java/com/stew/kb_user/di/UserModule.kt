package com.stew.kb_user.di

import com.stew.kb_common.network.RetrofitManager
import com.stew.kb_user.api.UserApi
import com.stew.kb_user.repo.LoginRepo
import com.stew.kb_user.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by stew on 8/21/22.
 * mail: stewforani@gmail.com
 */
val userModule = module {
    single { RetrofitManager.getService(UserApi::class.java) }
    single { LoginRepo(get()) }
    viewModel { LoginViewModel(get()) }
}