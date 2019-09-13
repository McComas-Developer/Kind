package com.dangerfield.kind.api

import com.dangerfield.kind.model.Post
import com.dangerfield.kind.model.User
import com.google.firebase.firestore.FirebaseFirestore

class Repository(private val db: FirebaseFirestore, user: User) : RepositoryInterface {
    override fun post(withPost: Post) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPopularPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPostsWithTags(tags: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchPosts(term: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setProfilePicture(file: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}