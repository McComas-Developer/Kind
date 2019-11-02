package com.dangerfield.kind.ui.profile.onboard;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.dangerfield.kind.api.CurrentUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.annotation.Nullable;

public class SignUpViewModel extends ViewModel {

  //  private Result<LiveData<Status>, ErrorMessage> authResult;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage store = FirebaseStorage.getInstance();

//    public Result<LiveData<Status>, ErrorMessage> createUser(
//            @Nullable Uri photo,
//            String username,
//            String email,
//            String password,
//            String confirmPassword
//
//            ) {
//        authResult = CurrentUser.INSTANCE.signUp(
//                db,
//                store,
//                photo,
//                username,
//                email,
//                password,
//                confirmPassword
//        );
//
//        return authResult;
//    }
}
