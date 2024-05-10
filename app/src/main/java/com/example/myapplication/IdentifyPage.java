package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IdentifyPage extends AppCompatActivity {
    Button identity_page_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_page);
        identity_page_btn = findViewById(R.id.identitybtn);
        identity_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_page = new Intent(IdentifyPage.this,cameraPage.class);
                startActivity(login_page);
            }
        });
        
    }
}