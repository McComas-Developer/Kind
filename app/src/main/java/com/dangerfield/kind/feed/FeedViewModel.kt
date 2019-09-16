package com.dangerfield.kind.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.KindRepository
import com.dangerfield.kind.model.Post

class FeedViewModel(private val repository: KindRepository): ViewModel() {
    private var tagPosts = MutableLiveData<List<Post>>()

    fun getPostWithTag(tag: String): MutableLiveData<List<Post>> {
        tagPosts = repository.getPostsWithTag(tag)
        return tagPosts
    }
}