package com.dangerfield.kind.api;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;


import com.dangerfield.kind.model.LikedState;
import com.dangerfield.kind.model.Post;
import com.dangerfield.kind.model.Post_api;
import com.dangerfield.kind.util.ExtensionsKt.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import kotlin.Unit;

public interface UserRepository {

    List<String> getLikedPosts(Context context);

    void likePost(String withUUID, Context context);

    void unlikePost(String withUUID, Context context);

    LikedState getLikedStatus(Post ofPost, Context context);

    void getRecentSearches();

    LiveData<Resource<Boolean>> signUp(
                            FirebaseFirestore db,
                            FirebaseStorage store,
                            @Nullable Uri profilePicture,
                            String username,
                            String email,
                            String pass,
                            String confirmPass);

    LiveData<Resource<Boolean>>signIn(String email, String pass);

    void signOut(Context context);

    LiveData<Resource<Boolean>> setProfilePicture(FirebaseStorage store, Uri profilePicture);

    LiveData<Resource<Boolean>>  createPost(Post_api post, FirebaseFirestore db) ;

    void updateUserName(String newName);

}
