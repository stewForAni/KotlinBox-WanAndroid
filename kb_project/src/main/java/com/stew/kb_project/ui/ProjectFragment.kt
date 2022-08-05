package com.stew.kb_project.ui

import android.util.Log
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_project.R
import com.stew.kb_project.databinding.FragmentProjectBinding
import com.stew.kb_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class ProjectFragment : BaseVMFragment<FragmentProjectBinding>() {

    private val projectViewModel: ProjectViewModel by viewModel()

    override fun getLayoutID(): Int {
        return R.layout.fragment_project
    }

    override fun init() {
        projectViewModel.getProTypeList()
        projectViewModel.getProList(currentPage)
    }

    override fun observe() {
        projectViewModel.proTypeList.observe(this, {
            Log.d(TAG, "observe1: ${it.size}")
        })
        projectViewModel.proList.observe(this, {
            Log.d(TAG, "observe2: ${it.size}")
        })
    }
}