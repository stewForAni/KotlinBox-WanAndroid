package com.stew.kb_me.di

import com.stew.kb_common.network.RetrofitManager
import com.stew.kb_me.api.MeApi
import com.stew.kb_me.repo.MeRepo
import com.stew.kb_me.viewmodel.MeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by stew on 8/25/22.
 * mail: stewforani@gmail.com
 */
val meModule = module {
    single { RetrofitManager.getService(MeApi::class.java) }
    single { MeRepo(get()) }
    viewModel { MeViewModel(get()) }
}