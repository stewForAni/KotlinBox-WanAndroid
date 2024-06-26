package com.stew.kotlinbox.asyncthird

import com.stew.kb_home.di.homeModule
import com.stew.kb_me.di.meModule
import com.stew.kb_navigation.di.naviModule
import com.stew.kb_project.di.ProjectModule
import com.stew.kb_user.di.userModule
import com.stew.kotlinbox.KBApplication
import com.xj.anchortask.library.AnchorTask
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by stew on 2024/6/26.
 * mail: stewforani@gmail.com
 */
class Task1 : AnchorTask(ATConstants.TASK1) {

    private val modules = mutableListOf(homeModule, ProjectModule, naviModule, meModule, userModule)

    override fun run() {
        startKoin {
            androidLogger()
            androidContext(KBApplication.instance!!)
            modules(modules)
        }
    }
}