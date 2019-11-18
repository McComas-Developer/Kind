package com.dangerfield.kind.ui.feed

import android.net.Uri
import com.dangerfield.kind.model.LikedState
import com.dangerfield.kind.model.Post

interface PostViewModel {

    fun getPosterProfilePic(uid: String, onComplete: (uri: Uri) -> Unit)

    fun likePost(withUUID: String)

    fun unlikePost(withUUID: String)
}
