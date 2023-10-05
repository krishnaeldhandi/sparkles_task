package com.sparkles.sparkles_landing.retrofit;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TAG_NAME = "RetrofitClient";
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://richlabz.com/sparkles_app/api/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d(TAG_NAME, "Retrofit client created with base URL: " + BASE_URL);
        }
        return retrofit;
    }
}
