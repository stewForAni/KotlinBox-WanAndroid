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
    var proList = MutableLiveData<List<Project.ProjectDetail>>()

    fun getProTypeList() = launch(
        block = { proTypeList.value = repo.getProTypeList().data }
    )

    fun getProList(currentPage: Int, cid: Int) = launch(
        block = { proList.value = repo.getProList(currentPage, cid).data?.datas }
    )
}