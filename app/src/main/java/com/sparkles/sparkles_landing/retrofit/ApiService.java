package com.sparkles.sparkles_landing.retrofit;

import com.sparkles.sparkles_landing.models.ApiResponseModel;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("servicesNames")
    Call<ApiResponseModel> getData();
}
