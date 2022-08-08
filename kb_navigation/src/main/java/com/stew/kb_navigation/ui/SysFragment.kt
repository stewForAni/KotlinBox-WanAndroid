package com.stew.kb_navigation.ui

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.stew.kb_common.base.BaseVMFragment

import com.stew.kb_navigation.R
import com.stew.kb_navigation.bean.Sys
import com.stew.kb_navigation.databinding.FragmentSysBinding
import com.stew.kb_navigation.viewmodel.NaviViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by stew on 7/27/22.
 * mail: stewforani@gmail.com
 */
class SysFragment : BaseVMFragment<FragmentSysBinding>() {

    private val naviViewModel: NaviViewModel by viewModel()
    private val txList = arrayListOf<View>()
    private var oldPosition = -1

    override fun getLayoutID(): Int {
        return R.layout.fragment_sys
    }

    override fun observe() {
        naviViewModel.sysList.observe(this, {
            addView(it)
        })
    }

    override fun init() {
        naviViewModel.getSys()
    }

    private fun addView(list: List<Sys>) {

        for (i in 0..19) {
            val layout = LayoutInflater.from(activity).inflate(R.layout.item_sys, null, false)
            layout.tag = i
            layout.setOnClickListener {
                setColor(it.tag as Int)
            }

            val t = layout.findViewById<TextView>(R.id.tx_sys)
            txList.add(t)
            t.text = list[i].name

            if (i == 0) {
                setColor(i)
            }

            mBind.ll.addView(layout)
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