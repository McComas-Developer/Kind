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
//
//    override fun signUp(db: FirebaseFirestore,
//                        store: FirebaseStorage,
//                        profilePicture: Uri?,
//                        username: String,
//                        email: String,
//                        pass: String,
//                        confirmPass: String)
//            : Result<LiveData<Status>, ErrorMessage> {
//
//        if(email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty())
//            return Error(ErrorMessage("Please fill out all fields"))
//        if(username.contains(" "))
//            return Error(ErrorMessage("Please remove spaces from username"))
//        if(pass != confirmPass)
//            return Error(ErrorMessage("Passwords do not match, please try again"))
//        if(profilePicture == null)
//            return Error(ErrorMessage("Please Select Profile Picture"))
//
//        authStatus.value = Status.LOADING
//
//        checkUserNameTaken(db, username).addOnSuccessListener {
//            if(it.documents.isNullOrEmpty()) createFirebaseAccount(store, profilePicture, db, username, email, pass)
//            else authStatus.value = Status.FAILURE("Sorry that username is taken :(")
//        }.addOnFailureListener {
//            authStatus.value = Status.FAILURE("FAILED CONNECTION")
//        }
//
//        return Success(authStatus)
//    }
//
//    private fun checkUserNameTaken(db: FirebaseFirestore, username: String): Task<QuerySnapshot> {
//       return db.collection("Users_test").whereEqualTo("username", username).get()
//    }
//
//
//    fun createFirebaseAccount(store: FirebaseStorage,profilePicture: Uri, db: FirebaseFirestore, username: String, email: String, password: String) {
//
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
//            if(it.isSuccessful){
//                authStatus.value = Status.SUCCESS
//                store.getReference("/user_profile_test/${uid!!}").putFile(profilePicture)
//                db.collection("Users_test").add(User(username, listOf()))
//            }else{
//                authStatus.value = Status.FAILURE(it.exception?.localizedMessage ?: "Unknown Error")
//            }
//        }
//    }
//
//
    override fun signIn(email: String, pass: String): MutableLiveData<Resource<Unit>> {

        if(email.isEmpty() || pass.isEmpty()) authStatus.value = Resource.Error("Please fill out all fields")


        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if(it.isSuccessful) authStatus.value = Resource.Success(Unit)
            else authStatus.value =  Resource.Error(it.exception?.localizedMessage ?: "Unknown Error")
        }
        return authStatus
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