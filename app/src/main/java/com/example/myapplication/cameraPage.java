package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;


import static androidx.core.content.ContextCompat.startActivity;

public class cameraPage extends AppCompatActivity {
    Button scanButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);

        TextureView textureView = findViewById(R.id.textureView);
         scanButton = findViewById(R.id.scanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the scanning activity
                Intent intent = new Intent(view.getContext(),verifyID.class);
                startActivity(intent);
            }
        });
    }
}