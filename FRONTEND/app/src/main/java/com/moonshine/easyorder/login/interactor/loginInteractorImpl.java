package com.moonshine.easyorder.login.interactor;

import android.app.Activity;
import android.content.Context;

import com.moonshine.easyorder.login.presenter.loginPresenter;
import com.moonshine.easyorder.login.repository.loginRepository;
import com.moonshine.easyorder.login.repository.loginRepositoryImpl;

public class loginInteractorImpl implements loginInteractor  {
    private loginPresenter presenter;
    private loginRepository repository;

    public loginInteractorImpl(loginPresenter presenter, loginRepositoryImpl.UserTaskServiceListener listener, Context context) {
        this.presenter = presenter;
        repository = new loginRepositoryImpl(presenter, listener, context);
    }

    @Override
    public void login(String username, String password, Activity activity) {
        repository.logIn(username, password,activity);
    }
}
