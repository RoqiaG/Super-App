package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;

public class CustomDialogSuccessSending extends Dialog {

    public CustomDialogSuccessSending(Context context) {
        super(context);
        setContentView(R.layout.success_sending); // Set the custom layout
    }
}