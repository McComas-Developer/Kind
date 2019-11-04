package com.dangerfield.kind.model

/**
 * OUTGOING: Used for posts that are being sent to the server
 */
data class Post_api(
            var UUID: String = "",
            var timeStamp: String = "",
            var tags: List<String> = listOf(),
            var hearts:  List<String> = listOf(),
            var images: List<String> = listOf(),
            var text: String = "",
            var posterUUID: String = "",
            var posterUserName : String = ""
    )
