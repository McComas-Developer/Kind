package com.dangerfield.kind.api

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.model.Post_api
import com.dangerfield.kind.model.User
import com.dangerfield.kind.util.Action
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import java.util.function.Function

object CurrentUser : UserRepository {

    private var auth = FirebaseAuth.getInstance()
    private var authStatus = MutableLiveData<Resource<Boolean>>()
    val uid: String? get() {return auth.currentUser?.uid}
    val isAuthenticated: Boolean get() { return auth.currentUser != null }

    override fun getLikedPosts() {
    }

    override fun getRecentSearches() {
    }

    override fun signUp(db: FirebaseFirestore,
                        store: FirebaseStorage,
                        profilePicture: Uri?,
                        username: String,
                        email: String,
                        pass: String,
                        confirmPass: String)
            : LiveData<Resource<Boolean>> {


        authStatus.value = Resource.Loading()

        checkUserNameTaken(db, username).addOnSuccessListener {
            if(it.documents.isNullOrEmpty()) createFirebaseAccount(store, profilePicture!!, db, username, email, pass)
            else authStatus.value = Resource.Error(message ="Sorry that username is taken :(")
        }.addOnFailureListener {
            authStatus.value = Resource.Error(message ="FAILED CONNECTION")
        }

        return authStatus

    }

    fun getUsername(db : FirebaseFirestore, onComplete: (text: String) -> Unit) {
        db.collection(Endpoints.USERS).document(this.uid!!).get().addOnSuccessListener {
            it.data?.get("username")?.toString()?.let {text ->
                onComplete(text)
            }
        }
    }

    private fun checkUserNameTaken(db: FirebaseFirestore, username: String): Task<QuerySnapshot> {
       return db.collection(Endpoints.USERS).whereEqualTo("username", username).get()
    }


    fun createFirebaseAccount(store: FirebaseStorage,profilePicture: Uri, db: FirebaseFirestore, username: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                authStatus.value = Resource.Success(true)
                store.getReference("/user_profile_test/${uid!!}").putFile(profilePicture)
                db.collection(Endpoints.USERS).document(uid.toString()).set(User(username, listOf()))
            }else{
                authStatus.value = Resource.Error(message =it.exception?.localizedMessage ?: "Unknown Error")
            }
        }
    }


    override fun signIn(email: String, pass: String): MutableLiveData<Resource<Boolean>> {
        val status : MutableLiveData<Resource<Boolean>> = MutableLiveData(Resource.Loading())

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if(it.isSuccessful) status.value = Resource.Success(true)
            else status.value =  Resource.Error(message =it.exception?.localizedMessage ?: "Unknown Error")
        }
        return status
    }


    override fun updateUserName(newName: String) {

    }

    override fun signOut(context: Context?){
        auth.signOut()
    }

    override fun setProfilePicture(store: FirebaseStorage, profilePicture: Uri): MutableLiveData<Resource<Boolean>> {
        val status : MutableLiveData<Resource<Boolean>> = MutableLiveData(Resource.Loading())
        store.getReference("/user_profile_test/${uid!!}").putFile(profilePicture).addOnSuccessListener {
            status.value = Resource.Success(true)
        }.addOnFailureListener {
            status.value =  Resource.Error(message =it.localizedMessage ?: "Unknown Error")
        }
        return status
    }

    fun getProfilePic(store: FirebaseStorage): MutableLiveData<Resource<Uri>> {
        val uri = MutableLiveData<Resource<Uri>>()
        store.getReference("/user_profile_test/${uid!!}").downloadUrl.addOnSuccessListener {
            uri.value = Resource.Success(data = it)
        }.addOnFailureListener {
            uri.value = Resource.Error(message = it.localizedMessage ?: "Unkown Error")
        }

        return uri
    }

    override fun createPost(post: Post_api, db: FirebaseFirestore) : MutableLiveData<Resource<Boolean>> {
        val postRequest : MutableLiveData<Resource<Boolean>> = MutableLiveData(Resource.Loading())

        getUsername(db) {
            post.posterUserName = it
            submitPost(db, post, postRequest)
        }

        return postRequest
    }

    fun submitPost(db: FirebaseFirestore, post: Post_api, postRequest : MutableLiveData<Resource<Boolean>>) {
        db.collection(Endpoints.POPULAR_POSTS).document(post.UUID).set(post).addOnSuccessListener {

            db.collection(Endpoints.USERS)
                    .document(post.posterUUID)
                    .update("posts", FieldValue.arrayUnion(post.UUID)).addOnSuccessListener {
                        postRequest.value = Resource.Success(true)
                    }.addOnFailureListener {
                        postRequest.value = Resource.Error(message = it.localizedMessage ?: "Unknown Error")
                    }
        }.addOnFailureListener {
            postRequest.value = Resource.Error(message = it.localizedMessage ?: "Unknown Error")
        }
    }
}
