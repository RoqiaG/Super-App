package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends AppCompatActivity {

    ImageView cardrepay,pay,savings,collect,pocket,exchange,invest,topup,utilities,cityservices,rewards,familycenter,health,more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        cardrepay = (ImageView) findViewById(R.id.cardrepay);
        pay = (ImageView) findViewById(R.id.pay);
        savings = (ImageView) findViewById(R.id.saving);
        collect = (ImageView) findViewById(R.id.collect);
        pocket = (ImageView) findViewById(R.id.pocket);
        exchange = (ImageView) findViewById(R.id.exchange);
        invest = (ImageView) findViewById(R.id.invest);
        topup = (ImageView) findViewById(R.id.topup);
        utilities = (ImageView) findViewById(R.id.utilities);
        cityservices = (ImageView) findViewById(R.id.cityservice);
        rewards = (ImageView) findViewById(R.id.rewards);
        familycenter = (ImageView) findViewById(R.id.familycenter);
        health = (ImageView) findViewById(R.id.health);
        more = (ImageView) findViewById(R.id.more);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay = new Intent(Home.this,Payment.class);
                startActivity(pay);
            }
        });
    }
}