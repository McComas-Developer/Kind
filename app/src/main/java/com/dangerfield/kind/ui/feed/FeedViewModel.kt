package com.dangerfield.kind.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.KindRepository
import com.dangerfield.kind.api.Repository
import com.dangerfield.kind.model.Post
import com.google.firebase.firestore.FirebaseFirestore

class FeedViewModel : ViewModel() {
    //TODO: for now we will just make it but we need to find a way to inject this
    private var repository: KindRepository = Repository(FirebaseFirestore.getInstance())
    private var posts = MutableLiveData<List<Post>>()

    public fun getPosts(): MutableLiveData<List<Post>> {
        if (posts.value.isNullOrEmpty()) {
            posts = repository.getPostsWithTag("doggo")
        }
        return posts
    }

}

