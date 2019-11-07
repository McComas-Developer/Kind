package com.dangerfield.kind.ui.feed

import android.content.Context
import android.net.Uri
import com.dangerfield.kind.model.LikedState
import com.dangerfield.kind.model.Post

interface PostViewModel {

    fun getPosterProfilePic(uid: String, onComplete: (uri: Uri) -> Unit)

    fun likePost(withUUID: String, context: Context)

    fun unlikePost(withUUID: String, context: Context)

    fun getLikedStatus (ofPost: Post, context: Context): LikedState
}
