package com.moonshine.easyorder.login.repository;

import android.app.Activity;

import com.moonshine.easyorder.Models.Client;
import com.moonshine.easyorder.Models.JWTToken;
import com.moonshine.easyorder.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface loginRepository {
    void logIn(String username, String password, Activity activity);
    @GET("account")
    Call<User>getAccount();

    @POST("authenticate")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<JWTToken> createTask(@Body User user );

    @GET("clients/findByUserId/{id}")
    Call<Client>getUserById(@Path("id") long id);
}
