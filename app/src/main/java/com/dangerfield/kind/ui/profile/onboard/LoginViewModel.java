package com.dangerfield.kind.ui.profile.onboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.dangerfield.kind.api.CurrentUser;
import com.dangerfield.kind.api.Resource;


public class LoginViewModel extends ViewModel {

    public Resource<LiveData<Resource<Boolean>>> login(String email, String password) {
        if(email.isEmpty() || password.isEmpty()){
            return new Resource.Error<>(null,"Please fill out all fields");
        }else{
            return new Resource.Success<>(CurrentUser.INSTANCE.signIn(email, password));
        }
    }
}
