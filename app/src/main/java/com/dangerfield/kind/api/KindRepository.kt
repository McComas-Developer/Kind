package com.dangerfield.kind.api

import androidx.lifecycle.MutableLiveData
import com.dangerfield.kind.model.Post

interface KindRepository {

    fun getPopularPosts(refreshing: Boolean = false) : MutableLiveData<Resource<List<Post>>>

    fun getPostsWithTag(tag: String, refreshing: Boolean = false) : MutableLiveData<Resource<List<Post>>>

    fun searchPosts(term: String)
}