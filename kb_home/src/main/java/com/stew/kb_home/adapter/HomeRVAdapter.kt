package com.stew.kb_home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stew.kb_home.R
import com.stew.kb_home.bean.Article

/**
 * Created by stew on 8/3/22.
 * mail: stewforani@gmail.com
 */
class HomeRVAdapter :
    RecyclerView.Adapter<HomeRVAdapter.MyViewHolder>() {

    private val diff: AsyncListDiffer<Article.ArticleDetail>
    private val list: MutableList<Article.ArticleDetail> = mutableListOf()

    init {
        diff = AsyncListDiffer(this, MyCallback())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("RecyclerAdapter", "onBindViewHolder: $position")
        var data = diff.currentList[position]
        holder.name?.text = data.name
        holder.image?.setImageResource(data.image)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    fun setData(list: MutableList<Article.ArticleDetail>) {
        diff.submitList(list)
    }

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var name: TextView? = null
        var image: ImageView? = null

        init {
            name = item.findViewById(R.id.textView)
            image = item.findViewById(R.id.imageView)
        }
    }

    class MyCallback : DiffUtil.ItemCallback<Article.ArticleDetail>() {
        override fun areItemsTheSame(oldItem: Article.ArticleDetail, newItem: Article.ArticleDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article.ArticleDetail, newItem: Article.ArticleDetail): Boolean {
            return oldItem.title == newItem.title
        }

    }
}