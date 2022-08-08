package com.stew.kb_navigation.di

import com.stew.kb_common.network.RetrofitManager
import com.stew.kb_navigation.api.NaviApi
import com.stew.kb_navigation.repo.NaviRepo
import com.stew.kb_navigation.viewmodel.NaviViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by stew on 8/7/22.
 * mail: stewforani@gmail.com
 */
val naviModule = module {
    single { RetrofitManager.getService(NaviApi::class.java) }
    single { NaviRepo(get()) }
    viewModel { NaviViewModel(get()) }
}