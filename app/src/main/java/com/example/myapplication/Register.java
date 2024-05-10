package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText username,email,password;

    Button register_btn;
    TextView login;

    String Name,Email,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = (Button) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.logintext);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_pass);


        Name = username.getText().toString();
        Email = email.getText().toString();
        Password = password.getText().toString();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_page = new Intent(Register.this,IdentifyPage.class);
                startActivity(login_page);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_page = new Intent(Register.this,Login.class);
                startActivity(reg_page);
            }
        });
    }
}