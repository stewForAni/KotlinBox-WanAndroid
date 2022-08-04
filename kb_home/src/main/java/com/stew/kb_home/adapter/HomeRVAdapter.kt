package com.stew.kb_home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stew.kb_home.R
import com.stew.kb_home.bean.Article
import java.lang.StringBuilder

/**
 * Created by stew on 8/3/22.
 * mail: stewforani@gmail.com
 */
class HomeRVAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var diff: AsyncListDiffer<Article.ArticleDetail>

    private val NORMAL: Int = 0
    private val FOOT: Int = 1

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == NORMAL){
            MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv, parent, false)
            )
        }else{
            MyFootHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.foot_home_rv, parent, false)
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

        if(getItemViewType(position) == FOOT){

        }else{
            val data = diff.currentList[position]
            (holder as MyViewHolder).title.text = data.title
            holder.time.text = data.niceDate
            holder.type.text = data.superChapterName

            holder.tag.visibility = if (data.fresh) View.VISIBLE else View.GONE

            if (data.author.isEmpty()) {
                holder.nameT.text = "分享："
                holder.name.text = data.shareUser
            } else {
                holder.nameT.text = "作者："
                holder.name.text = data.author
            }
        }


    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    fun setData(list: List<Article.ArticleDetail>) {
        diff.submitList(list)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title: TextView = item.findViewById(R.id.title)
        var nameT: TextView = item.findViewById(R.id.t1)
        var name: TextView = item.findViewById(R.id.name)
        var time: TextView = item.findViewById(R.id.time)
        var type: TextView = item.findViewById(R.id.type)
        var tag: TextView = item.findViewById(R.id.tag)
    }

    class MyFootHolder(item: View) : RecyclerView.ViewHolder(item) {

    }

    class MyCallback : DiffUtil.ItemCallback<Article.ArticleDetail>() {
        override fun areItemsTheSame(
            oldItem: Article.ArticleDetail,
            newItem: Article.ArticleDetail
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Article.ArticleDetail,
            newItem: Article.ArticleDetail
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}