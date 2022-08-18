package com.stew.kb_home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stew.kb_common.util.Constants
import com.stew.kb_home.R
import com.stew.kb_home.bean.a
import java.util.*

/**
 * Created by stew on 8/3/22.
 * mail: stewforani@gmail.com
 */
class HomeRVAdapter(var listener: HomeItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private var lastClickTime: Long = 0
    private var diff: AsyncListDiffer<a>
    private val NORMAL: Int = 0
    private val FOOT: Int = 1

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NORMAL) {
            MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv, parent, false)
            )
        } else {
            MyFootHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.foot_home_rv, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            FOOT
        } else {
            NORMAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("HomeRVAdapter", "onBindViewHolder: position = $position")
        if (getItemViewType(position) == NORMAL) {
            val data = diff.currentList[position]
            (holder as MyViewHolder).title.text = data.title
            holder.time.text = data.niceDate
            holder.type.text = data.superChapterName
            holder.tag1.visibility = if (data.fresh) View.VISIBLE else View.GONE
            holder.tag2.visibility = if (data.superChapterId == 408) View.VISIBLE else View.GONE
            holder.name.text = if (data.author.isEmpty()) data.shareUser else data.author
            holder.itemView.tag = position
            holder.itemView.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return if (diff.currentList.size == 0) 0 else diff.currentList.size + 1
    }

    fun setData(list: List<a>) {
        diff.submitList(list)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title: TextView = item.findViewById(R.id.title)
        var name: TextView = item.findViewById(R.id.name)
        var time: TextView = item.findViewById(R.id.time)
        var type: TextView = item.findViewById(R.id.type)
        var tag1: TextView = item.findViewById(R.id.tag1)
        var tag2: TextView = item.findViewById(R.id.tag2)
    }

    class MyFootHolder(item: View) : RecyclerView.ViewHolder(item)

    class MyCallback : DiffUtil.ItemCallback<a>() {
        override fun areItemsTheSame(
            oldItem: a,
            newItem: a
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: a,
            newItem: a
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.niceDate == newItem.niceDate
        }
    }


    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > Constants.MIN_CLICK_DELAY_TIME && v != null) {
            lastClickTime = currentTime
            listener.onClick(v.tag as Int)
        }
    }
}