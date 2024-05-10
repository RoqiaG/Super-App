package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class sendMoney extends AppCompatActivity {
Button sendMoney ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        sendMoney = findViewById(R.id.sendbtn);
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            CustomDialogSuccessSending successSending = new CustomDialogSuccessSending(sendMoney.getContext());
            successSending.show();
            }
        });
    }
    // Create an instance of your custom message dialog
   // CustomMessageDialog customMessageDialog = new CustomMessageDialog(sendMoney.this);
    // Show the dialog
       //         customMessageDialog.show();
}