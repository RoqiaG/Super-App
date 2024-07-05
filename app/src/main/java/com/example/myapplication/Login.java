package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText Email,Password;
    Button login_btn ;
    TextView register ;

    String email,password;
//    private UserService userService;
//    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.registertext);
        Email = (EditText) findViewById(R.id.log_email);
        Password = (EditText) findViewById(R.id.log_pass);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (TextUtils.isEmpty(Email.getText().toString()) || TextUtils.isEmpty(Password.getText().toString())) {
                    Toast.makeText(Login.this,"Username / Password Required" , Toast.LENGTH_LONG).show();
                }else {
                    login();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login.this,Register.class);
                startActivity(register);
            }
        });
    }

    public void login() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(Email.getText().toString());
        loginRequest.setUserPassword(Password.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userlogin(loginRequest);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()){

                    Toast.makeText(Login.this,"login Successfully" , Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (loginResponse.getUser().getPermissionId().isMerchant() == true) {

                                Intent home = new Intent(Login.this,QR.class);
                                startActivity(home);
                            }
                            else if (loginResponse.getUser().getPermissionId().isConsumer() == true){
//                                Toast.makeText(Login.this,"this is not a merchant",Toast.LENGTH_LONG).show();
                                Intent payment = new Intent(Login.this, Home.class);
                                startActivity(payment);
                            }

                        }
                    },100);

                } else {
                    Toast.makeText(Login.this,"login Failed" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this,"Throwable" + t.getLocalizedMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
}