package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class SignInResponse {

    @SerializedName("success")
    private boolean success;

    public SignInResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    // Other response fields can be added here as needed

}
