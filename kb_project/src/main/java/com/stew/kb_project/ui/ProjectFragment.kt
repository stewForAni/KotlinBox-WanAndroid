package com.stew.kb_project.ui

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_project.R
import com.stew.kb_project.adapter.ProVPAdapter
import com.stew.kb_project.bean.ProjectType
import com.stew.kb_project.databinding.FragmentProjectBinding
import com.stew.kb_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class ProjectFragment : BaseVMFragment<FragmentProjectBinding>() {

    private val projectViewModel: ProjectViewModel by viewModel()
    private var l: MutableList<String> = arrayListOf()
    private var f: MutableList<Fragment> = arrayListOf()

    override fun getLayoutID(): Int {
        return R.layout.fragment_project
    }

    override fun init() {
        projectViewModel.getProTypeList()
    }

    override fun observe() {
        projectViewModel.proTypeList.observe(this, {
            initTab(it)
        })
    }

    private fun initTab(list: List<ProjectType>) {
        for (i in 1..5) {
            l.add(list[i].name)
            f.add(ProjectChildFragment.newInstance(list[i].id))
        }
        mBind.viewPager.adapter = ProVPAdapter(this, f)
        TabLayoutMediator(mBind.tabLayout, mBind.viewPager) { tab, position ->
            tab.text = l[position]
            Log.d(TAG, "initTab: $position")
        }.attach()
    }

}