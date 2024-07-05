package com.example.myapplication;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @GET("ocr/getOcrUrl")
    Call<ResponseModel> getYourData();

//    @Multipart
//    @POST("/login_id")
//    Call<NIResponseLogin> uploadImage(@Part("file")RequestBody file);
    @Multipart
    @POST("/login_id")
    Call<NIResponseLogin> uploadImage(@Part MultipartBody.Part file);


//    Call<ResponseBody> uploadImage(MultipartBody.Part body);
}
