package com.dangerfield.kind.util

import com.dangerfield.kind.api.Endpoints
import com.dangerfield.kind.model.Post_api
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FakeDataCreator(val db: FirebaseFirestore) {

    lateinit var tags: ArrayList<String>
    lateinit var images: ArrayList<String>
    lateinit var text: ArrayList<String>
    lateinit var users: Map<String, String>

    fun run() {
        build()
        writeDogPosts()
    }

    fun build() {

        users = mapOf(
                "CJtjhhwkZjP1fSEHy9MKrv1LsdF3" to "timmy123",
                "OGm0NsidOwMKjQx4b6eYYvhrhjk2" to "uncle_bob",
                "PJ5KyopAihZqpckovFIU9iGRHCi2" to "pstar",
                "biC1DHWtIQPfAvefOkL7nqavLCC3" to "John_Smith",
                "jJ2T8R231OMZqGGurWqog9AdaoJ3" to "jimmy123"
                )

        tags = arrayListOf("dogs","doggo","kindess","funny","cats","baby","food")
        text = arrayListOf(
                "This is test data to show that we can work with fire base and such",
                "Welcome To Kind, this is some more test data, I know how much you said you liked it",
                "Dear diary, I met a squirrel today. I told him my name was Jack. That lie will haunt me forever",
                "This is an example of a longer post that takes up more space to show that we can use to more or less button Correctly"
        + "This post needs to be longer so ima just keep on typing until I am unsure of what else to add. I think im there. Bye",
                "Hi everyone!"
                )

        images = arrayListOf("https://firebasestorage.googleapis.com/v0/b/kind-af233.appspot.com/o/posts_images_test%2Fadorable-cavalier-king-charles-spaniel-puppy-royalty-free-image-523255012-1565106446.jpg?alt=media&token=e0bba667-d372-4eb4-82cf-ec3da09dd23f",
                "https://firebasestorage.googleapis.com/v0/b/kind-af233.appspot.com/o/posts_images_test%2Fimages.jpeg?alt=media&token=e7d0cd44-7e78-486c-9c2a-b7517747f1ac",
                "https://firebasestorage.googleapis.com/v0/b/kind-af233.appspot.com/o/posts_images_test%2Fbarbra-streisand-throat-cancer-never-sing-again-pp.jpg?alt=media&token=17264ba8-983b-435d-8e02-ddd6b8ebbb13",
                "https://firebasestorage.googleapis.com/v0/b/kind-af233.appspot.com/o/posts_images_test%2FImage-2-Alternatives-to-stock-photography-Thinkstock.jpg?alt=media&token=4bfa703a-8c01-4b57-8370-ff6017474fdd")
    }



    fun writeDogPosts() {
        var posts = ArrayList<Post_api>()

        for(i in 1..99) {
            val randUID = users.keys.random()
            val randUsername = users[randUID]

            posts.add(Post_api(
                    posterUUID = randUID,
                    posterUserName = randUsername!!,
                    UUID = UUID.randomUUID().toString(),
                    tags = (tags.shuffled().drop((0..8).shuffled().first())) ,
                    text = text.random(),
                    timeStamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Calendar.getInstance().time),
                    images = (images.shuffled().drop((0..5).shuffled().first())) ,
                    hearts = (users.keys.shuffled().drop((0..4).shuffled().first()))
            ))
        }

        posts.forEach {
            db.collection(Endpoints.POPULAR_POSTS).document(it.UUID).set(it)
        }
    }
}