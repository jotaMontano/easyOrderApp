package com.moonshine.easyorder.login.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


import com.moonshine.easyorder.Models.Client;
import com.moonshine.easyorder.Models.JWTToken;
import com.moonshine.easyorder.Models.User;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.httpUtils.httpUtil;
import com.moonshine.easyorder.login.presenter.loginPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;
import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;
import static com.moonshine.easyorder.login.view.loginActivity.createSharePreference;

public class loginRepositoryImpl implements loginRepository {

    loginPresenter presenter;
    private Activity activity;
    private UserTaskServiceListener listener;
    private Context context;
    private JWTToken jwtToken;
    User user = new User();

    public loginRepositoryImpl(loginPresenter presenter, loginRepositoryImpl.UserTaskServiceListener listener, Context context) {
        this.presenter = presenter;
        this.listener = listener;
        this.context = context;
    }
    public loginRepositoryImpl(UserTaskServiceListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }
    @Override
    public void logIn(String username, String password, Activity activity) {

        user.setUsername(username);
        user.setPassword(password);
        createTask(user);
        this.activity = activity;
       // getAccount();
    }
    @Override
    public Call createTask(User user) {
        loginRepository apiService = httpUtil.createService(loginRepository.class);

        Call<JWTToken> call = apiService.createTask(user);

        call.enqueue(new Callback<JWTToken>() {
            @Override
            public void onResponse(Call<JWTToken> call, Response<JWTToken> response) {
                if (response.isSuccessful()) { // OK
                    // listener = response.body();

                    jwtToken = response.body();
                    TOKEN = jwtToken.getId_token();
                    createSharePreference("Token",TOKEN,context);

                    getAccount();

                    Log.e(TAG,"response: " + response.code() + "- token: " + jwtToken.getId_token() );
                } else {
                    listener.OnGetTaskByUserError("Credenciales invalidas");
                }
            }

            @Override
            public void onFailure(Call<JWTToken> call, Throwable t) {
                Log.e(TAG,"SAD" + t.getMessage());
                listener.OnGetTaskByUserError("Credenciales invalidas" + t.getMessage());

            }

        });
        return null;
    }

    @Override
    public Call<User> getAccount() {


        loginRepository apiService = httpUtil.createService(loginRepository.class, TOKEN);

        Call<User> call = apiService.getAccount();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user = response.body();
                    getUserById(user.getId());
                } else {
                    listener.OnGetTaskByUserError("Credenciales invalidas");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.OnGetTaskByUserError("Something went wrong!");
            }
        });
        return null;
    }


    @Override
    public Call<Client> getUserById(long id) {
        loginRepository apiService = httpUtil.createService(loginRepository.class, TOKEN);

        Call<Client> call = apiService.getUserById(id);

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.isSuccessful()){
                   listener.OnGetTaskByClientSuccess(response.body());
                } else {
                    listener.OnGetTaskByUserError("Credenciales invalidas");
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                listener.OnGetTaskByUserError("Something went wrong!");
            }
        });
        return null;
    }

    public interface UserTaskServiceListener {
        void OnGetTaskByClientSuccess(Client client);
        void OnGetTaskByUserError(String error);
    }
}
