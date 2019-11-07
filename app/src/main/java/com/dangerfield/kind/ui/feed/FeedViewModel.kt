package com.dangerfield.kind.ui.feed

import android.content.Context
import android.util.Log
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.CurrentUser
import com.dangerfield.kind.api.Repository
import com.dangerfield.kind.api.Resource
import com.dangerfield.kind.model.LikedState
import com.dangerfield.kind.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FeedViewModel : ViewModel(), PostViewModel {
    private var repository: Repository = Repository(FirebaseFirestore.getInstance())
    private var currentUser: CurrentUser? = null
    private var tagPosts = MutableLiveData<Resource<List<Post>>>()

    fun getFeed(): LiveData<Resource<List<Post>>> {
        if(tagPosts.value?.data.isNullOrEmpty()){
            tagPosts = repository.getPopularPosts()
        }
        return tagPosts
    }

    fun refreshFeed() { tagPosts = repository.getPopularPosts(refreshing = true) }

    override fun getPosterProfilePic(uid: String, onComplete: (uri: Uri) -> Unit) {
        repository.getProfilePicture(FirebaseStorage.getInstance(), uid) {
            onComplete.invoke(it)
        }
    }

    override fun likePost(withUUID: String, context: Context) {
        if (currentUser != null) {
            if (currentUser!!.isAuthenticated) {
                currentUser!!.likePost(withUUID, context)
            }
        }
    }

    override fun unlikePost(withUUID: String, context: Context) {
        if (currentUser != null) {
            if (currentUser!!.isAuthenticated) {
                currentUser!!.unlikePost(withUUID, context)
            }
        }
    }

    override fun getLikedStatus(ofPost: Post, context: Context): LikedState {
        if (currentUser != null) {
            if (currentUser!!.isAuthenticated) {
                return currentUser!!.getLikedStatus(ofPost, context)
            }
        }
        return LikedState.UNLIKED
    }

    fun setCurrentUser(withCurrentUser: CurrentUser) {
        currentUser = withCurrentUser
    }

}
