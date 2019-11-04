package com.dangerfield.kind.api

import android.net.Uri
import android.view.View
import com.dangerfield.kind.api.Resource.*
import androidx.lifecycle.MutableLiveData
import com.dangerfield.kind.model.ExpandedState
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage

class Repository(private val db: FirebaseFirestore) : KindRepository {

    private val tagPosts = MutableLiveData<Resource<List<Post>>>()
    private val feedPosts = MutableLiveData<Resource<List<Post>>>()


    override fun getPostsWithTag(tag: String, refreshing : Boolean):  MutableLiveData<Resource<List<Post>>> {

        tagPosts.value = if (refreshing) Loading(refreshing = true) else Loading()

        db.collection(Endpoints.POPULAR_POSTS).whereArrayContains("tags",tag)
                .get().addOnCompleteListener {data ->
                    val list = data.result?.toObjects(Post::class.java) as List<Post>
                    list?.map { if(it.text.length > 150) it.expandedState = ExpandedState.COLLAPSED }
                    tagPosts.value = Success(list)
                }.addOnFailureListener {
                    tagPosts.value = Error(message =it.localizedMessage ?: "Unknown Error")
                }
        return tagPosts
    }

    override fun getPopularPosts(refreshing: Boolean) : MutableLiveData<Resource<List<Post>>>  {
        feedPosts.value = if (refreshing) Loading(refreshing = true) else Loading()

        db.collection(Endpoints.POPULAR_POSTS)
                .orderBy("timeStamp", Query.Direction.DESCENDING).limit(50)
                .get()
                .addOnCompleteListener {data ->
                    val list = data.result?.toObjects(Post::class.java) as List<Post>
                    list?.map { if(it.text.length > 150) it.expandedState = ExpandedState.COLLAPSED }
                    feedPosts.value = Success(list)
                }.addOnFailureListener {
                    feedPosts.value = Error(message =it.localizedMessage ?: "Unknown Error")
                }

        return feedPosts
    }

    override fun searchPosts(term: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //maybe combine tags results with text.contains result ? it would be cool to one day use machine learning
        //to tag photos
    }

    fun getProfilePicture(store: FirebaseStorage, uid: String, onComplete: (uri: Uri) -> Unit) {
        store.getReference("/user_profile_test/" + uid).downloadUrl.addOnSuccessListener {
            onComplete.invoke(it)
        }
    }
}
