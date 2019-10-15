package com.dangerfield.kind.ui.profile.onboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.dangerfield.kind.api.CurrentUser;
import com.dangerfield.kind.api.ErrorMessage;
import com.dangerfield.kind.api.Result;
import com.dangerfield.kind.api.Status;

public class LoginViewModel extends ViewModel {
    private Result<LiveData<Status>, ErrorMessage> result;

    public Result<LiveData<Status>, ErrorMessage> login(String email, String password) {
        result = CurrentUser.INSTANCE.signIn(email, password);
        return result;
    }
}
