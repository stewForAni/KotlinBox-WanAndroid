package com.stew.kb_home.di

import com.stew.kb_common.network.RetrofitManager
import com.stew.kb_home.api.HomeApi
import com.stew.kb_home.repo.HomeRepo
import com.stew.kb_home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by stew on 8/1/22.
 * mail: stewforani@gmail.com
 */
val homeModule = module {
    single { RetrofitManager.getService(HomeApi::class.java) }
    single { HomeRepo(get()) }
    viewModel { HomeViewModel(get()) }
}