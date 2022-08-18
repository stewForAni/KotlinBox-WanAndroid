package com.stew.kb_project.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_project.R
import com.stew.kb_project.adapter.ProRVAdapter
import com.stew.kb_project.bean.Project
import com.stew.kb_project.bean.p
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
        private const val INDEX: String = "index"

        fun newInstance(cid: Int, index: Int): ProjectChildFragment {
            val f = ProjectChildFragment()
            val bundle = Bundle()
            bundle.putInt(C_ID, cid)
            bundle.putInt(INDEX, index)
            f.arguments = bundle
            return f
        }
    }

    private var currentID: Int = 0
    private var currentIndex: Int = 0

    private val projectViewModel: ProjectViewModel by viewModel()
    lateinit var proRVAdapter: ProRVAdapter
    lateinit var lm: LinearLayoutManager
    var isLoadMore = false
    var list: MutableList<Project.ProjectDetail> = arrayListOf()

    override fun getLayoutID(): Int {
        return R.layout.fragment_project_child
    }

    override fun observe() {
        projectViewModel.proList.observe(this, {
            //AsyncListDiffer需要一个新数据，不然添加无效
            val newList: MutableList<p> = arrayListOf()

            isLoadMore = false
            list.addAll(it)
            newList.addAll(list)

            proRVAdapter.setData(newList)

            Log.d(TAG, "observe: update data $currentIndex")
        })
    }

    override fun init() {
        val a = arguments
        if (a != null) {
            currentID = a.getInt(C_ID)
            currentIndex = a.getInt(INDEX)
        }

        lm = LinearLayoutManager(activity)
        mBind.rvPro.layoutManager = lm
        proRVAdapter = ProRVAdapter{
            Log.d(TAG, "init: $it")
        }
        mBind.rvPro.adapter = proRVAdapter

        mBind.rvPro.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                    (lm.findLastVisibleItemPosition() + 1 == proRVAdapter.itemCount &&
                            !isLoadMore)
                ) {
                    Log.d(TAG, "onScrollStateChanged: last-----")
                    isLoadMore = true
                    currentPage++
                    lazyLoad()
                }
            }
        })

    }


    override fun lazyLoad() {
        projectViewModel.getProList(currentPage+1, currentID)
    }

    //----------------------------------------------------------------

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.d("pcf", "onViewCreated: F$currentIndex")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d("pcf", "onStart: F$currentIndex")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d("pcf", "onResume: F$currentIndex")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("pcf", "onPause: F$currentIndex")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("pcf", "onStop: F$currentIndex")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.d("pcf", "onDestroyView: F$currentIndex")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("pcf", "onDestroy: F$currentIndex")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.d("pcf", "onDetach: F$currentIndex")
//    }

}