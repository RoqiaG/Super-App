package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Payment extends AppCompatActivity {

    Button reqtopay_btn;
    Button sendMoney_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        reqtopay_btn = (Button) findViewById(R.id.reqbtn);
        reqtopay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent req = new Intent(Payment.this,RequestToPay.class);
                startActivity(req);
            }
        });

        sendMoney_btn = (Button) findViewById(R.id.sendmoney);
        sendMoney_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send_req = new Intent(Payment.this,sendMoney.class);
                startActivity(send_req);
            }
        });
    }
}