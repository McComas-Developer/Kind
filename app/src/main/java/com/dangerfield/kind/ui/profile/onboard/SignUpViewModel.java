package com.dangerfield.kind.ui.profile.onboard;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.dangerfield.kind.api.CurrentUser;

import com.dangerfield.kind.api.Resource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.annotation.Nullable;

public class SignUpViewModel extends ViewModel {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage store = FirebaseStorage.getInstance();

    public Resource<LiveData<Resource<Boolean>>> createUser(
            @Nullable Uri photo,
            String username,
            String email,
            String password,
            String confirmPassword

            ) {

      if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        return  new Resource.Error<>(null, "Please fill out all fields");
      if(username.contains(" "))
        return  new Resource.Error<>(null,   "Please remove spaces from username");
      if(!password.equals(confirmPassword))
        return  new Resource.Error<>(null,  "Passwords do not match, please try again");
      if(photo == null)
        return  new Resource.Error<>( null, "Please Select Profile Picture");
      else return  new Resource.Success<>(CurrentUser.INSTANCE.signUp(
                db,
                store,
                photo,
                username,
                email,
                password,
                confirmPassword));
    }
}
