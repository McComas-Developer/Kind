package com.dangerfield.kind.api

import androidx.lifecycle.MutableLiveData
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.ui.find.PopularCategory

interface KindRepository {

    fun getPopularPosts(refreshing: Boolean = false) : MutableLiveData<Resource<List<Post>>>

    fun getPostsWithTag(tag: String, refreshing: Boolean = false) : MutableLiveData<Resource<List<Post>>>

    fun searchPosts(term: String)

    fun getPopularCategories() : MutableLiveData<Resource<List<PopularCategory>>>

    fun addLike(userID: String, withUUID: String)

    fun removeLike(userID: String, withUUID: String)
}
