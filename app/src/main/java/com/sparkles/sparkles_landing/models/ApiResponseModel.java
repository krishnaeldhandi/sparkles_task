package com.sparkles.sparkles_landing.models;

import java.util.List;

public class ApiResponseModel {
    private String status;
    private String message;
    private List<HomeModel> data;

    // Add getters for 'status', 'message', and 'data'
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<HomeModel> getData() {
        return data;
    }
}
