package com.dangerfield.kind.model

import android.util.Log

data class Post(
        var UUID: String = "",
        var timeStamp: String = "",
        var tags: List<String> = listOf(),
        //holds the UUID of all of the likers
        var hearts:  List<String> = listOf(),
        //holds the https link to the image
        var images: List<String> = listOf(),
        var text: String = "",
        var posterUUID: String = "",
        var expandedState: ExpandedState = ExpandedState.NONE
)

enum class ExpandedState {
    EXPANDED,
    COLLAPSED,
    NONE
}
