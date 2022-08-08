package com.stew.kb_navigation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class NaviVPAdapter(context: Fragment, private val f: MutableList<Fragment>) :
    FragmentStateAdapter(context) {

    override fun getItemCount(): Int {
        return f.size
    }

    override fun createFragment(position: Int): Fragment {
        return f[position]
    }
}