package com.dangerfield.kind.ui.create

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.kind.api.CurrentUser
import com.dangerfield.kind.api.Resource
import com.google.firebase.storage.FirebaseStorage

class CreatePostViewModel : ViewModel() {
    private var profilePic = MutableLiveData<Resource<Uri>>()

    fun getProfilePic(): MutableLiveData<Resource<Uri>> {
        profilePic =  CurrentUser.getProfilePic(FirebaseStorage.getInstance())
        return  profilePic
    }
}