package com.example.plp_app.api;

import com.google.gson.annotations.SerializedName;

public class QAProductResponse {
    @SerializedName("data")
    private LoginResponse.Data data;

    @SerializedName("additionalData")
    private Object additionalData;

    @SerializedName("message")
    private String message;

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("code")
    private String code;

    // Getters và setters
    public LoginResponse.Data getData() {
        return data;
    }

    public void setData(LoginResponse.Data data) {
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
        @SerializedName("quantityGood")
        private int quantityGood;
        @SerializedName("quantityBad")
        private int quantityBad;
        @SerializedName("quantityQA")
        private int quantityQA;

        public int getQuantityGood() {
            return quantityGood;
        }

        public void setQuantityGood(int quantityGood) {
            this.quantityGood = quantityGood;
        }

        public int getQuantityBad() {
            return quantityBad;
        }

        public void setQuantityBad(int quantityBad) {
            this.quantityBad = quantityBad;
        }

        public int getQuantityQA() {
            return quantityQA;
        }

        public void setQuantityQA(int quantityQA) {
            this.quantityQA = quantityQA;
        }
    }
}
