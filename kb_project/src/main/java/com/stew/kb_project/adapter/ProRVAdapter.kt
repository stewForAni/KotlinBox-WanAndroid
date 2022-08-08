package com.stew.kb_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.makeramen.roundedimageview.RoundedImageView
import com.stew.kb_project.R
import com.stew.kb_project.bean.p
import java.lang.StringBuilder


class ProRVAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var diff: AsyncListDiffer<p>
    private val NORMAL: Int = 0
    private val FOOT: Int = 1

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NORMAL) {
            MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_pro_rv, parent, false)
            )
        } else {
            MyFootHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.foot_pro_rv, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == (itemCount - 1)) {
            FOOT
        } else {
            NORMAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == NORMAL) {
            val data = diff.currentList[position]
            (holder as MyViewHolder).rimg.load(data.envelopePic)
            holder.desc.text = data.desc
            holder.title.text = data.title
            holder.name.text = data.author
            holder.time.text = data.niceDate
        }

    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    fun setData(list: List<p>) {
        diff.submitList(list)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title: TextView = item.findViewById(R.id.title)
        var desc: TextView = item.findViewById(R.id.desc)
        var name: TextView = item.findViewById(R.id.name)
        var time: TextView = item.findViewById(R.id.time)
        var rimg: RoundedImageView = item.findViewById(R.id.rimg)
    }

    class MyFootHolder(item: View) : RecyclerView.ViewHolder(item)

    class MyCallback : DiffUtil.ItemCallback<p>() {
        override fun areItemsTheSame(
            oldItem: p,
            newItem: p
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: p,
            newItem: p
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}