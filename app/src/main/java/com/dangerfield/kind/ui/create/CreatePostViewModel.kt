package com.dangerfield.kind.ui.create

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.CurrentUser
import com.dangerfield.kind.api.Resource
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.model.Post_api
import com.dangerfield.kind.util.FakeDataCreator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class CreatePostViewModel : ViewModel() {
    private var profilePic = MutableLiveData<Resource<Uri>>()

    fun getProfilePic(): MutableLiveData<Resource<Uri>> {
        profilePic =  CurrentUser.getProfilePic(FirebaseStorage.getInstance())
        return  profilePic
    }

    fun createFakeData() {
        val gen = FakeDataCreator(FirebaseFirestore.getInstance(), "timmy123")
        gen.run()
    }

    fun post(text: String): MutableLiveData<Resource<Boolean>> {
        val post = Post_api(
                UUID = UUID.randomUUID().toString(),
                timeStamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Calendar.getInstance().time),
                text = text,
                images = listOf(),
                posterUUID = CurrentUser.uid!!
                )

        return CurrentUser.createPost(post, FirebaseFirestore.getInstance())
    }
}