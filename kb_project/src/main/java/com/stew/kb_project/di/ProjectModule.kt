package com.stew.kb_project.di

import com.stew.kb_common.network.RetrofitManager
import com.stew.kb_project.api.ProjectApi
import com.stew.kb_project.repo.ProjectRepo
import com.stew.kb_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
val ProjectModule = module {
    single { RetrofitManager.getService(ProjectApi::class.java) }
    single { ProjectRepo(get()) }
    viewModel { ProjectViewModel(get()) }
}