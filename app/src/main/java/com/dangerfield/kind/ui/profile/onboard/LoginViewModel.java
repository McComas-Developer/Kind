package com.dangerfield.kind.ui.profile.onboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.dangerfield.kind.api.CurrentUser;
import com.dangerfield.kind.api.Resource;

import kotlin.Unit;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<Resource<Unit>> loginStatus;

    public MutableLiveData<Resource<Unit>> getLoginStatus() {
        return loginStatus;
    };

    public void login(String email, String password) {
        loginStatus = CurrentUser.INSTANCE.signIn(email, password);
    }
}
