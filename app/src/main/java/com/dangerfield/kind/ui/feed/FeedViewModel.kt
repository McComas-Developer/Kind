package com.dangerfield.kind.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.Repository
import com.dangerfield.kind.api.Resource
import com.dangerfield.kind.model.Post
import com.google.firebase.firestore.FirebaseFirestore

class FeedViewModel : ViewModel() {
    //TODO: for now we will justmake it but we need to find a way to inject this
    private var repository: Repository = Repository(FirebaseFirestore.getInstance())
    private var tagPosts = MutableLiveData<Resource<List<Post>>>()

    fun getPostWithTag(tag: String): LiveData<Resource<List<Post>>> {
        if(tagPosts.value?.data.isNullOrEmpty()){
            Log.d("api","GOT NEW DATA")
            tagPosts = repository.getPostsWithTag(tag)
        }
        return tagPosts
    }
}

