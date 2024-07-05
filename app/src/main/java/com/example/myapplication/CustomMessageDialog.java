package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;

public class CustomMessageDialog extends Dialog {

    public CustomMessageDialog(Context context) {
        super(context);
        setContentView(R.layout.success_payment); // Set the custom layout
    }
}
