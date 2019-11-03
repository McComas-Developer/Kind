package com.dangerfield.kind.api

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage

object CurrentUser : UserRepository {

    private var auth = FirebaseAuth.getInstance()
    private var authStatus = MutableLiveData<Resource<Unit>>()
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
            : LiveData<Resource<Unit>> {

        if(email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty())
            authStatus.value = Resource.Error(message = "Please fill out all fields")
        if(username.contains(" "))
            authStatus.value = Resource.Error(message = "Please remove spaces from username")
        if(pass != confirmPass)
            authStatus.value = Resource.Error(message ="Passwords do not match, please try again")
        if(profilePicture == null)
            authStatus.value = Resource.Error(message ="Please Select Profile Picture")

        authStatus.value = Resource.Loading()

        checkUserNameTaken(db, username).addOnSuccessListener {
            if(it.documents.isNullOrEmpty()) createFirebaseAccount(store, profilePicture!!, db, username, email, pass)
            else authStatus.value = Resource.Error(message ="Sorry that username is taken :(")
        }.addOnFailureListener {
            authStatus.value = Resource.Error(message ="FAILED CONNECTION")
        }

        return authStatus

    }

    private fun checkUserNameTaken(db: FirebaseFirestore, username: String): Task<QuerySnapshot> {
       return db.collection("Users_test").whereEqualTo("username", username).get()
    }


    fun createFirebaseAccount(store: FirebaseStorage,profilePicture: Uri, db: FirebaseFirestore, username: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                authStatus.value = Resource.Success(Unit)
                store.getReference("/user_profile_test/${uid!!}").putFile(profilePicture)
                db.collection("Users_test").add(User(username, listOf()))
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

    override fun setProfilePicture() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPost(post: Post?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}