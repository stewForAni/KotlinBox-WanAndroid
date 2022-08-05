package com.stew.kb_project.ui

import android.os.Bundle
import android.util.Log
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_project.R
import com.stew.kb_project.databinding.FragmentProjectChildBinding
import com.stew.kb_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
class ProjectChildFragment : BaseVMFragment<FragmentProjectChildBinding>() {

    companion object {
        private const val C_ID: String = "cid"
        fun newInstance(cid: Int): ProjectChildFragment {
            val f = ProjectChildFragment()
            val bundle = Bundle()
            bundle.putInt(C_ID, cid)
            f.arguments = bundle
            return f
        }
    }

    private val projectViewModel: ProjectViewModel by viewModel()

    override fun getLayoutID(): Int {
        return R.layout.fragment_project_child
    }

    override fun init() {
        val id = arguments?.getInt(C_ID)
        if (id != null) {
            projectViewModel.getProList(currentPage, id)
        }
    }

    override fun observe() {
        projectViewModel.proList.observe(this, {
            Log.d(TAG, "observe: ${it.size}")
        })
    }

}