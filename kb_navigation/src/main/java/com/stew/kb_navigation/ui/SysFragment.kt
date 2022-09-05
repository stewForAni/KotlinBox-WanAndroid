package com.stew.kb_navigation.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.stew.kb_common.base.BaseVMFragment
import com.stew.kb_common.network.BaseStateObserver

import com.stew.kb_navigation.R
import com.stew.kb_navigation.adapter.SysRVAdapter
import com.stew.kb_navigation.bean.Sys
import com.stew.kb_navigation.databinding.FragmentSysBinding

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class SysFragment : BaseVMFragment<FragmentSysBinding>() {

    private val txList = arrayListOf<View>()
    private var oldPosition = -1
    lateinit var sysAdapter: SysRVAdapter
    lateinit var lm: LinearLayoutManager
    private val myData = arrayListOf<Sys>()
    private lateinit var f: MainFragment

    override fun getLayoutID(): Int {
        return R.layout.fragment_sys
    }

    override fun observe() {
        f = parentFragment as MainFragment
        f.naviViewModel.sysList.observe(this, object : BaseStateObserver<List<Sys>>(null) {
            override fun getRespDataSuccess(it: List<Sys>) {
                for (i in 0..19) {
                    myData.add(it[i])
                }
                addView(myData)
                sysAdapter.setData(myData)
                Log.d(TAG, "observe: update data")
            }
        })
    }

    override fun init() {
        lm = LinearLayoutManager(activity)
        mBind.rvSys.layoutManager = lm
        sysAdapter = SysRVAdapter()
        mBind.rvSys.adapter = sysAdapter


        f.naviViewModel.getSys()
    }

    private fun addView(list: List<Sys>) {

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


//    private fun setFlow(data: List<s>) {
//        mBind.flexLayout.removeAllViews()
//        for (item in data) {
//            val layout = LayoutInflater.from(activity).inflate(R.layout.item_sys_flow, null, false)
//            val t = layout.findViewById<TextView>(R.id.tx_sys_flow)
//            t.text = item.name
//            mBind.flexLayout.addView(layout)
//            Log.d(TAG, "setFlow: ")
//        }
//    }
}