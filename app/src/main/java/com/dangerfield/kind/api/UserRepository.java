package com.dangerfield.kind.api;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;


import com.dangerfield.kind.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.annotation.Nullable;

import kotlin.Unit;

public interface UserRepository {

    void getLikedPosts();

    void getRecentSearches();

    LiveData<Resource<Unit>> signUp(
                            FirebaseFirestore db,
                            FirebaseStorage store,
                            @Nullable Uri profilePicture,
                            String username,
                            String email,
                            String pass,
                            String confirmPass);

    LiveData<Resource<Boolean>>signIn(String email, String pass);

    void signOut(Context context);

    void setProfilePicture();

    void createPost(Post post);

    void updateUserName(String newName);

}
