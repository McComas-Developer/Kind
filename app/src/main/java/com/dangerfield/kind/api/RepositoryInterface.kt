package com.dangerfield.kind.api

import com.dangerfield.kind.model.Post

interface RepositoryInterface {

    fun post(withPost: Post)

    fun getPopularPosts()

    fun getPostsWithTags(tags: List<String>)

    fun searchPosts(term: String)

    fun createUser()

    fun setProfilePicture(file: String)
}