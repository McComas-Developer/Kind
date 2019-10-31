package com.dangerfield.kind.ui.find.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.KindRepository
import com.dangerfield.kind.api.Repository
import com.dangerfield.kind.model.Post
import com.google.firebase.firestore.FirebaseFirestore

class SearchViewModel : ViewModel() {

    private var repository: KindRepository = Repository(FirebaseFirestore.getInstance())
    private var tagPosts = MutableLiveData<List<Post>>()

    fun getPostWithTag(tag: String): MutableLiveData<List<Post>> {
            tagPosts = repository.getPostsWithTag(tag)
        return tagPosts
    }
}