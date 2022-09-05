package com.stew.kb_navigation.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_common.network.BaseStateObserver
import com.stew.kb_common.util.Constants
import com.stew.kb_navigation.R
import com.stew.kb_navigation.adapter.NaviRVAdapter
import com.stew.kb_navigation.bean.Navi
import com.stew.kb_navigation.databinding.FragmentSysBinding

/**
 * Created by stew on 8/8/22.
 * mail: stewforani@gmail.com
 */
class NaviFragment : BaseVMFragment<FragmentSysBinding>() {

    private val txList = arrayListOf<View>()
    private var oldPosition = -1
    lateinit var naviAdapter: NaviRVAdapter
    lateinit var lm: LinearLayoutManager
    private val myData = arrayListOf<Navi>()
    private lateinit var f: MainFragment

    override fun getLayoutID(): Int {
        return R.layout.fragment_sys
    }

    override fun observe() {
        f = parentFragment as MainFragment
        f.naviViewModel.naviList.observe(this, object : BaseStateObserver<List<Navi>>(null) {
            override fun getRespDataSuccess(it: List<Navi>) {
                for (i in 0..19) {
                    myData.add(it[i])
                }
                addView(myData)
                naviAdapter.setData(myData)
                Log.d(TAG, "observe: update data")
            }
        })
    }

    override fun init() {
        lm = LinearLayoutManager(activity)
        mBind.rvSys.layoutManager = lm
        naviAdapter = NaviRVAdapter {
            val data = myData[it.p1].articles[it.p2]
            ARouter.getInstance()
                .build(Constants.PATH_WEB)
                .withString(Constants.WEB_LINK, data.link)
                .withString(Constants.WEB_TITLE, data.title)
                .navigation()
        }
        mBind.rvSys.adapter = naviAdapter
        f.naviViewModel.getNavi()
    }

    private fun addView(list: List<Navi>) {

        for (i in list.indices) {
            val layout = LayoutInflater.from(activity).inflate(R.layout.item_sys, null, false)
            layout.tag = i
            layout.setOnClickListener {
                setColor(it.tag as Int)
                lm.scrollToPositionWithOffset(it.tag as Int, 0)
            }

            val t = layout.findViewById<TextView>(R.id.tx_sys)
            txList.add(t)
            t.text = list[i].name

            if (i == 0) {
                setColor(i)
            }

            mBind.llSys.addView(layout)
        }

    }


    private fun setColor(position: Int) {
        if (oldPosition == position) return
        if (oldPosition != -1) {
            txList[oldPosition].background =
                ResourcesCompat.getDrawable(resources, R.drawable.shape_10dp_white, null)
        }

        oldPosition = position
        txList[oldPosition].background =
            ResourcesCompat.getDrawable(resources, R.drawable.shape_10dp_grey, null)
    }
}