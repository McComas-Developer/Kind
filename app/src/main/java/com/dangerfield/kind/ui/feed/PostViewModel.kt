package com.dangerfield.kind.ui.feed

import android.net.Uri

interface PostViewModel {
    fun getPosterProfilePic(uid: String, onComplete: (uri: Uri) -> Unit)
}
