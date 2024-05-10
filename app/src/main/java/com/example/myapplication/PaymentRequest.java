package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentRequest extends AppCompatActivity {
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);
             confirm = findViewById(R.id.button7);
             confirm.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     CustomMessageDialog msg = new CustomMessageDialog(PaymentRequest.this);
                     msg.show();
                 }
             });
    }
}