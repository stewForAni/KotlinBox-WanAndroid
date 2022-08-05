package com.stew.kb_project.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by stew on 8/5/22.
 * mail: stewforani@gmail.com
 */
class ProVPAdapter(context: Fragment, private val f: MutableList<Fragment>) :
    FragmentStateAdapter(context) {

    override fun getItemCount(): Int {
        return f.size
    }

    override fun createFragment(position: Int): Fragment {
        return f[position]
    }
}