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
import androidx.room.Room
import com.bumptech.glide.Glide
import com.dangerfield.kind.R
import com.dangerfield.kind.db.LikeIDDatabase
import com.dangerfield.kind.model.ExpandedState
import com.dangerfield.kind.model.LikeID
import com.dangerfield.kind.model.LikedState
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.util.toReadableDate
import kotlinx.android.synthetic.main.item_feed_post.view.*
import java.security.AccessController.getContext

class PostAdapter(context: Context, private val viewModel: PostViewModel) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

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
        val timeStamp : TextView = view.tv_post_date
        val username : TextView = view.tv_post_username
        val heartsCount : TextView = view.tv_hearts_count

        init {
            heartButton.setOnClickListener { toggleHeart(adapterPosition) }
            moreButton.setOnClickListener {
                toggleText(adapterPosition)
            }
        }
    }

    private fun toggleHeart(position: Int) {
        posts[position].likedState = when(posts[position].likedState) {
                LikedState.LIKED -> {
                    viewModel.unlikePost(posts[position].UUID)
                    LikedState.UNLIKED
                }
                LikedState.UNLIKED -> {
                    viewModel.likePost(posts[position].UUID)
                    LikedState.LIKED
                }
        }

        notifyDataSetChanged()
    }

    private fun toggleText(position: Int) {
        posts[position].expandedState =
                if(posts[position].expandedState == ExpandedState.EXPANDED) ExpandedState.COLLAPSED
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
    }

    private fun setPostLiked(post: Post, holder: ViewHolder) {
        post.likedState = viewModel.getLikedStatus(post)
        holder.heartButton.background =
                if(post.likedState == LikedState.LIKED)
                    context.resources.getDrawable(R.drawable.ic_heart_filled,null)
                else
                    context.resources.getDrawable(R.drawable.ic_heart,null)
    }

    private fun setPostProfilePic(post: Post, holder: ViewHolder) {
        viewModel.getPosterProfilePic(post.posterUUID) {
            Glide.with(holder.posterImage)
                    .load(it)
                    .centerCrop()
                    .into(holder.posterImage)
        }
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
        holder.heartsCount.text = getCountText(post.hearts.size)
        holder.timeStamp.text = post.timeStamp.toReadableDate()
        holder.username.text = post.posterUserName
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

    private fun getCountText(size: Int): String {
        return if(size > 0) {
            "${size} hearts"
        }else{
            "Be the first to like"
        }
    }

    override fun getItemCount() = this.posts.size
}
