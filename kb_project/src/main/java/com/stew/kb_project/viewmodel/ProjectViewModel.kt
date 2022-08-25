package com.stew.kb_project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_project.bean.Project
import com.stew.kb_project.bean.ProjectType
import com.stew.kb_project.repo.ProjectRepo

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
class ProjectViewModel(private var repo: ProjectRepo) : BaseViewModel() {

    var proTypeList = MutableLiveData<List<ProjectType>>()
    var proList = MutableLiveData<Project>()
    var collectData = MutableLiveData<String>()

    fun getProTypeList() = launch(
        block = { repo.getProTypeList(proTypeList) }
    )

    fun getProList(currentPage: Int, cid: Int) = launch(
        block = { repo.getProList(currentPage, cid, proList) }
    )

    fun collect(id: Int) {
        launch(
            block = { repo.collect(id, collectData) }
        )
    }

    fun unCollect(id: Int) {
        launch(
            block = { repo.unCollect(id, collectData) }
        )
    }
}