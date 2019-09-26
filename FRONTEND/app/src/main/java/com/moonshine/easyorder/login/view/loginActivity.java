package com.moonshine.easyorder.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.moonshine.easyorder.MainActivity;
import com.moonshine.easyorder.Models.Client;
import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.Products.repository.ProductRepository;
import com.moonshine.easyorder.Products.repository.ProductRepositoryImpl;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.login.presenter.loginPresenter;
import com.moonshine.easyorder.login.presenter.loginPresenterImpl;
import com.moonshine.easyorder.login.repository.loginRepository;
import com.moonshine.easyorder.login.repository.loginRepositoryImpl;
import com.moonshine.easyorder.top.TopActivity;

import java.util.ArrayList;
import java.util.List;

import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;


public class loginActivity extends AppCompatActivity implements loginView, loginRepositoryImpl.UserTaskServiceListener, ProductRepositoryImpl.ProductServiceListener{

    private Button login;
    private TextInputEditText username, password;
    private loginPresenter presenter;
    private SharedPreferences sharedPref;
    private String token;
    private Client client;
    private List<Product> productsTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        presenter = new loginPresenterImpl(this,this, this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().equals("") || password.getText().toString().equals("")){
                    if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
                        validationUserPassError();
                    } else if (username.getText().toString().equals("")) {
                        validationUserError();
                    } else {
                        validationPassError();
                    }
                } else {
                    login(username.getText().toString(), password.getText().toString());
                }
            }
        });
    }



    public void login(String username, String password) {

        presenter.login(username, password, this);



    }

    @Override
    public void goHome() {

        Intent intent = new Intent(this, productsTop.size() >= 1 ? TopActivity.class : MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, getString(R.string.login_error_login_v) + error, Toast.LENGTH_SHORT).show();
    }

    public void validationUserError() {
        Toast.makeText(this, getString(R.string.user_validation_login), Toast.LENGTH_SHORT).show();
    }

    public void validationPassError() {
        Toast.makeText(this, getString(R.string.password_validation_login), Toast.LENGTH_SHORT).show();
    }

    public void validationUserPassError() {
        Toast.makeText(this, getString(R.string.user_password_validation_login), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        token = getSharePreferences(this);
        if(token != null){
            TOKEN = token;

            if (this.client == null) {
                loginRepository LogRepository = new loginRepositoryImpl(this,this);
                LogRepository.getAccount();
            }else{
                getTopProducts();
            }

        }
    }

    @Override
    public void OnGetTaskByClientSuccess(Client client) {
        this.client = client;
        token = getSharePreferences(this);
        if(token != null){
            getTopProducts();
        }
    }

    @Override
    public void OnGetTaskByUserError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
    public static String getSharePreferences(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("Token", null);
    }
    public static void removeSharePreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove("Token").commit();
        //preferences.edit().clear().commit();
    }
    public static void createSharePreference(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void OnGetProductByCategorySuccess(ArrayList<Product> products, Long id, String nameCategory) {

    }

    @Override
    public void OnProductByCategoryError(String error) {

    }

    public void getTopProducts() {
        ProductRepository repository = new ProductRepositoryImpl(this,this);
        repository.getProductsTop(this.client.getId());

    }

    @Override
    public void OnGetProductBySuccess(ArrayList<Product> listProducts) {
        productsTop = listProducts;
        goHome();
    }

    @Override
    public void OnProductByError(String error) {
        productsTop = new ArrayList<>();
        goHome();
    }
}
