package com.stew.kb_project.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_common.base.BaseViewModel
import com.stew.kb_common.network.BaseStateObserver
import com.stew.kb_common.util.Constants
import com.stew.kb_common.util.LoadingViewUtil
import com.stew.kb_common.util.ToastUtil
import com.stew.kb_project.R
import com.stew.kb_project.adapter.ProItemClickListener
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

    var collectPosition: Int = 0

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
        projectViewModel.proList.observe(this, object : BaseStateObserver<Project>(null) {

            override fun getRespDataSuccess(it: Project) {

                resetUI()

                currentPage = it.curPage

                if (currentPage == 1) {
                    list.clear()
                }

                if (it.over) {
                    proRVAdapter.isLastPage = true
                }

                list.addAll(it.datas)

                if (currentPage == 1) {
                    proRVAdapter.setData(null)
                    proRVAdapter.setData(list)
                    lm.scrollToPosition(0)
                } else {
                    proRVAdapter.setData(list)
                }

                Log.d(TAG, "observe articleList: " + list.size)

            }

            override fun getRespDataEnd() {
                resetUI()
            }
        })


        projectViewModel.collectData.observe(this, object : BaseStateObserver<String>(null) {
            override fun getRespDataStart() {
                LoadingViewUtil.showLoadingDialog(requireContext(), true)
            }

            override fun getRespDataEnd() {
                LoadingViewUtil.dismissLoadingDialog()
            }

            override fun getRespSuccess() {
                LoadingViewUtil.dismissLoadingDialog()
                if (list[collectPosition].collect) {
                    ToastUtil.showMsg("取消收藏！")
                    list[collectPosition].collect = false
                } else {
                    ToastUtil.showMsg("收藏成功！")
                    list[collectPosition].collect = true
                }
                proRVAdapter.notifyItemChanged(collectPosition)
            }
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
        proRVAdapter = ProRVAdapter(object : ProItemClickListener {
            override fun onItemClick(position: Int) {
                val data = list[position]
                ARouter.getInstance()
                    .build(Constants.PATH_WEB)
                    .withString(Constants.WEB_LINK, data.link)
                    .withString(Constants.WEB_TITLE, data.title)
                    .navigation()
            }

            override fun onCollectClick(position: Int) {
                collectPosition = position
                if (list[collectPosition].collect) {
                    projectViewModel.unCollect(list[collectPosition].id)
                } else {
                    projectViewModel.collect(list[collectPosition].id)
                }
            }
        })

        mBind.rvPro.adapter = proRVAdapter

        mBind.rvPro.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && proRVAdapter.itemCount != 0 &&
                    (lm.findLastVisibleItemPosition() + 1 == proRVAdapter.itemCount &&
                            !isLoadMore && !proRVAdapter.isLastPage)
                ) {
                    Log.d(TAG, "onScrollStateChanged: last-----")
                    isLoadMore = true
                    lazyLoad()
                }
            }
        })


        mBind.srlPro.setColorSchemeResources(R.color.theme_color)
        mBind.srlPro.setOnRefreshListener {
            proRVAdapter.isLastPage = false
            projectViewModel.getProList(1, currentID)
        }

    }


    override fun lazyLoad() {
        projectViewModel.getProList(currentPage + 1, currentID)
    }

    private fun resetUI() {
        isLoadMore = false//加载更多完成，重置false
        if (mBind.srlPro.isRefreshing) {
            mBind.srlPro.isRefreshing = false
        }
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