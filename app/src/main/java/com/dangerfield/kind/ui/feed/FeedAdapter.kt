package com.dangerfield.kind.ui.feed

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dangerfield.kind.R
import com.dangerfield.kind.model.ExpandedState
import com.dangerfield.kind.model.LikedState
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
        val title: TextView = view.tv_post_text
        val image: ImageView = view.iv_post
        val posterImage: ImageView = view.iv_post_profile_pic
        val moreButton: TextView = view.tv_more
        val heartButton: ImageButton = view.btn_heart

        init {
            heartButton.setOnClickListener {
                toggleHeart( adapterPosition)
            }
        }
    }

    private fun toggleHeart(position: Int) {
        posts[position].likedState = when(posts[position].likedState) {
                LikedState.LIKED -> LikedState.UNLIKED
                LikedState.UNLIKED -> LikedState.LIKED
            }

        notifyDataSetChanged()
    }

    private fun toggleText(it: Post) {
        it.expandedState =
                if(it.expandedState == ExpandedState.EXPANDED) ExpandedState.COLLAPSED
                else ExpandedState.EXPANDED

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
         ViewHolder(LayoutInflater
                 .from(context)
                 .inflate(R.layout.item_feed_post, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        setPostText(post, holder)
        setPostProfilePic(post, holder)
        setPostImage(post, holder)
        setPostLiked(post, holder)

        holder.moreButton.setOnClickListener {
            toggleText(post)
        }
    }

    private fun setPostLiked(post: Post, holder: ViewHolder) {
        holder.heartButton.background =
                if(post.likedState == LikedState.LIKED)
                    context.resources.getDrawable(R.drawable.ic_heart_filled,null)
                else
                    context.resources.getDrawable(R.drawable.ic_heart,null)
    }

    private fun setPostProfilePic(post: Post, holder: ViewHolder) {
        Glide.with(holder.posterImage.context)
                .load("https://firebasestorage.googleapis.com/v0/b/kind-af233.appspot.com/o/user_profiles_test%2F636050436856130987-GTY-580014564-83556690.jpeg?alt=media&token=26a644f6-42dc-449e-8bc7-a660fe055383")
                .placeholder(R.color.colorPrimary)
                .centerCrop()
                .into(holder.posterImage)
    }

    private fun setPostImage(post: Post, holder: ViewHolder) {
        if(post.images.isNotEmpty()){
            holder.image.visibility = View.VISIBLE
            Glide.with(holder.image.context)
                    .load(post.images[0])
                    .placeholder(R.color.colorPrimary)
                    .centerCrop()
                    .into(holder.image)
        }else{
            holder.image.visibility = View.GONE
        }
    }

    private fun setPostText(post: Post, holder: ViewHolder) {
        holder.title.text = when (post.expandedState) {
            ExpandedState.COLLAPSED -> {
                Log.d("expanded", "GOT HERE")
                holder.moreButton.visibility = View.VISIBLE
                holder.moreButton.text = "more"
                post.text.substring(0, 150) + "..."
            }
            ExpandedState.EXPANDED -> {
                holder.moreButton.visibility = View.VISIBLE
                holder.moreButton.text = "less"
                post.text
            }
            else -> {
                holder.moreButton.visibility = View.INVISIBLE
                post.text
            }
        }
    }

    override fun getItemCount() = this.posts.size

}