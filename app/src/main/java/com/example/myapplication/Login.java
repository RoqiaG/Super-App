package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button login_btn ;
    TextView register ;

    String Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.registertext);
        email = (EditText) findViewById(R.id.log_email);
        password = (EditText) findViewById(R.id.log_pass);


        Email = email.getText().toString();
        Password = password.getText().toString();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Login.this,Home.class);
                startActivity(home);
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
}