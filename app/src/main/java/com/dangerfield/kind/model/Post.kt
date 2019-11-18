package com.dangerfield.kind.model


data class Post(
        var UUID: String = "",
        var timeStamp: String = "",
        var tags: List<String> = listOf(),
        var hearts:  List<String> = listOf(),
        var images: List<String> = listOf(),
        var text: String = "",
        var posterUUID: String = "",
        var posterUserName : String = "",
        var expandedState: ExpandedState = ExpandedState.NONE,
        var likedState: LikedState = LikedState.UNLIKED
)

enum class ExpandedState {
    EXPANDED,
    COLLAPSED,
    NONE
}

enum class LikedState {
    LIKED,
    UNLIKED
}
