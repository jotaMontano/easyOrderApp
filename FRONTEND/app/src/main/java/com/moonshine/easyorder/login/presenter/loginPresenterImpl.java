package com.moonshine.easyorder.login.presenter;

import android.app.Activity;
import android.content.Context;

import com.moonshine.easyorder.login.interactor.loginInteractor;
import com.moonshine.easyorder.login.interactor.loginInteractorImpl;
import com.moonshine.easyorder.login.repository.loginRepositoryImpl;
import com.moonshine.easyorder.login.view.loginActivity;
import com.moonshine.easyorder.login.view.loginView;

public class loginPresenterImpl implements loginPresenter{
    private com.moonshine.easyorder.login.view.loginView loginView;
    private loginInteractor interactor;

    public loginPresenterImpl(loginActivity loginActivity, loginRepositoryImpl.UserTaskServiceListener listener, Context context) {
        this.loginView = loginActivity;
        interactor = new loginInteractorImpl(this,listener,context);
    }

    @Override
    public void login(String username, String password, Activity activity) {
        interactor.login(username, password, activity);
    }

    @Override
    public void loginSuccess() {
        loginView.goHome();
    }

    @Override
    public void loginError(String error) {
        loginView.loginError(error);
    }
}
