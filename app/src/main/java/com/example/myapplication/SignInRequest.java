package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class SignInRequest {


    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPassword")
    private String userPassword;

    public SignInRequest(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

}
