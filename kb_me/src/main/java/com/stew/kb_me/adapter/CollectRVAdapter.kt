package com.stew.kb_me.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stew.kb_common.util.Constants
import com.stew.kb_me.R
import com.stew.kb_me.bean.c
import java.util.*

/**
 * Created by stew on 8/3/22.
 * mail: stewforani@gmail.com
 */
class CollectRVAdapter(var listener: CollectItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private var diff: AsyncListDiffer<c>
    private val NORMAL: Int = 0
    private val FOOT: Int = 1
    private val LAST: Int = 2
    var isLastPage = false

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NORMAL -> {
                MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_collect_rv, parent, false)
                )
            }
            FOOT -> {
                MyFootHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.foot_rv, parent, false)
                )
            }
            else -> {
                MyLastHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.last_rv, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            if (isLastPage) {
                LAST
            } else {
                FOOT
            }
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
            if (data.author.isNotEmpty()) {
                holder.name.text = data.author
            } else {
                holder.name.text = data.chapterName
            }

            holder.itemView.tag = position
            holder.itemView.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return if (diff.currentList.size == 0) 1 else diff.currentList.size + 1
    }

    fun setData(list: List<c>?) {
        //AsyncListDiffer需要一个新数据，不然添加无效
        diff.submitList(if (list != null) ArrayList(list) else null)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title: TextView = item.findViewById(R.id.title)
        var name: TextView = item.findViewById(R.id.name)
        var time: TextView = item.findViewById(R.id.time)
    }

    class MyFootHolder(item: View) : RecyclerView.ViewHolder(item)

    class MyLastHolder(item: View) : RecyclerView.ViewHolder(item)

    class MyCallback : DiffUtil.ItemCallback<c>() {
        override fun areItemsTheSame(
            oldItem: c,
            newItem: c
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: c,
            newItem: c
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.niceDate == newItem.niceDate
        }
    }

    private var lastClickTime: Long = 0
    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > Constants.MIN_CLICK_DELAY_TIME && v != null) {
            lastClickTime = currentTime
            listener.onClick(v.tag as Int)

        }
    }
}