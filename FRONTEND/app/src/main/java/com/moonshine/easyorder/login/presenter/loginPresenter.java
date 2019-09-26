package com.moonshine.easyorder.login.presenter;

import android.app.Activity;

public interface loginPresenter {

    void login(String username, String password, Activity activity);
    void loginSuccess();
    void loginError(String error);
}
