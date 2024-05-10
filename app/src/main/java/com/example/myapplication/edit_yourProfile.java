package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class edit_yourProfile extends AppCompatActivity {
   Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_profile);
        ImageView arrow=(ImageView) findViewById(R.id.imageView12);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit = findViewById(R.id.buttonSubmit);
                Intent intent = new Intent(getApplicationContext(), congratulationPage.class);
                startActivity(intent);
            }
        });
    }
}