package com.dangerfield.kind.api

import com.dangerfield.kind.model.Post
import com.dangerfield.kind.model.User
import com.google.firebase.firestore.FirebaseFirestore

class Repository(private val db: FirebaseFirestore, user: User) : RepositoryInterface {

    override fun getPostsWithTag(tag: String) {
        db.collection("Posts_test").whereArrayContains("tags",tag)
                .get().addOnCompleteListener {
            //TODO DO something with result
                    //likely set to some live data
        }
    }

    override fun post(withPost: Post) {
        db.collection("Posts_test").document(withPost.UUID).set(withPost).addOnCompleteListener {
            //TODO let user know its been posted
        }
    }

    override fun getPopularPosts() {
        /**
         * @We can either use serverless function to create popular collection every hour
         * or get all of the posts and order them by hearts
         */
    }



    override fun searchPosts(term: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //maybe combine tags results with text.contains result ? it would be cool to one day use machine learning
        //to tag photos
    }

    override fun createUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //set up auth and create user. assign user variable to that. Ideally
        //we can have some singlton to represent the user. THere should only ever be one user signed in at any give time
    }

    override fun setProfilePicture(file: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //get the users UUID, unser user_profiles_test/UUID/ replace the current image of them
    }
}