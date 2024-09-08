package com.example.plp_app.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardsResponse {
    @SerializedName("data")
    private Data data;
    @SerializedName("additionalData")
    private Object additionalData;
    @SerializedName("message")
    private String message;
    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("code")
    private String code;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Object getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Object additionalData) {
        this.additionalData = additionalData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class Data {

    }



}
