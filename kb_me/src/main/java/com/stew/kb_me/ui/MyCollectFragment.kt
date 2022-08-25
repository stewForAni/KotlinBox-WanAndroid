package com.stew.kb_me.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_me.R
import com.stew.kb_me.databinding.FragmentCollectBinding
import com.stew.kb_me.viewmodel.MeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 8/9/22.
 * mail: stewforani@gmail.com
 */
class MyCollectFragment:BaseVMFragment<FragmentCollectBinding>() {

    private val meViewModel: MeViewModel by viewModel()
    lateinit var homeRVAdapter: HomeRVAdapter
    lateinit var lm: LinearLayoutManager
    var isLoadMore = false
    var list: MutableList<Article.ArticleDetail> = arrayListOf()
    var collectPosition: Int = 0

    override fun getLayoutID(): Int {
        return R.layout.fragment_collect
    }

    override fun observe() {

    }

    override fun init() {

    }
}