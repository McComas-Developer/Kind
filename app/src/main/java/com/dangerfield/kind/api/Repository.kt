package com.dangerfield.kind.api

import androidx.lifecycle.MutableLiveData
import com.dangerfield.kind.model.Post

interface Repository {

    fun post(withPost: Post)

    fun getPopularPosts()

    fun getPostsWithTag(tag: String) : MutableLiveData<List<Post>>

    fun searchPosts(term: String)

    fun createUser()

    fun setProfilePicture(file: String)
}