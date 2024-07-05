package com.example.myapplication;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    private static Retrofit getRetrofit() {

        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)  // Optional: Increase timeout settings
                    .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)     // Optional: Increase timeout settings
                    .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // Optional: Increase timeout settings
                    .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://super-app-backend.onrender.com/")
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static UserService getUserService() {
        UserService userService = getRetrofit().create(UserService.class);
        return  userService;
    }
}
