package com.dangerfield.kind.feed

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dangerfield.kind.R
import com.dangerfield.kind.model.Post
import kotlinx.android.synthetic.main.item_feed_post.view.*


class FeedAdapter(context: Context, posts: List<Post>) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var posts = listOf<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var context: Context


    init {
        this.posts = posts
        this.context = context
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_post

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feed_post, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.title.text = post.text
    }

    override fun getItemCount(): Int {
        return this.posts.size
    }

}