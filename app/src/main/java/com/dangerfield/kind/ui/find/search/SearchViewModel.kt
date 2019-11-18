package com.dangerfield.kind.ui.find.search

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.CurrentUser
import com.dangerfield.kind.api.KindRepository
import com.dangerfield.kind.api.Repository
import com.dangerfield.kind.model.LikedState
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.ui.feed.PostViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class SearchViewModel : ViewModel(), PostViewModel {

    private var repository: Repository = Repository(FirebaseFirestore.getInstance())

    var currentSearchTerm = MutableLiveData<String>()

    var searchResult = Transformations.switchMap(currentSearchTerm) {
        repository.getPostsWithTag(it)
    }

    override fun getPosterProfilePic(uid: String, onComplete: (uri: Uri) -> Unit) {
        repository.getProfilePicture(FirebaseStorage.getInstance(), uid) {
            onComplete.invoke(it)
        }
    }

    override fun likePost(withUUID: String) {
        if (CurrentUser.isAuthenticated) {
            CurrentUser.likePost(FirebaseFirestore.getInstance(), withUUID)
        }
    }

    override fun unlikePost(withUUID: String) {
        if (CurrentUser.isAuthenticated) {
            CurrentUser.unlikePost(FirebaseFirestore.getInstance(), withUUID)
        }
    }
}
