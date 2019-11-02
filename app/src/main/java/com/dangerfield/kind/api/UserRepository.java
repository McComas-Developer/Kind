package com.dangerfield.kind.api;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;


import com.dangerfield.kind.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.annotation.Nullable;

public interface UserRepository {

    void getLikedPosts();

    void getRecentSearches();

//    Result<LiveData<Status>, ErrorMessage> signUp(
//                            FirebaseFirestore db,
//                            FirebaseStorage store,
//                            @Nullable Uri profilePicture,
//                            String username,
//                            String email,
//                            String pass,
//                            String confirmPass);
//
//    Result<LiveData<Status>, ErrorMessage > signIn(String email, String pass);

    void signOut(Context context);

    void setProfilePicture();

    void createPost(Post post);

    void updateUserName(String newName);

}
