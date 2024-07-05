package com.example.myapplication;

import com.example.myapplication.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {


    @POST("users/signIn")
    Call<LoginResponse> userlogin(@Body LoginRequest loginRequest);

    // Define other endpoints here if available

    @POST("users/checkUserByNationalID")
    Call<checkNILoginResponse> NIcheck(@Body checkNILoginRequest checkniloginrequest);

}
