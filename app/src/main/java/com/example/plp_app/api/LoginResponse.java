package com.example.plp_app.api;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
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
    // Getters và setters
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
        @SerializedName("tokenResponse")
        private TokenResponse tokenResponse;

        @SerializedName("role")
        private String role;

        @SerializedName("token")
        private String token;

        // Getters và setters
        public TokenResponse getTokenResponse() {
            return tokenResponse;
        }

        public void setTokenResponse(TokenResponse tokenResponse) {
            this.tokenResponse = tokenResponse;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class TokenResponse {
        @SerializedName("accessToken")
        private String accessToken;

        @SerializedName("refreshToken")
        private String refreshToken;

        @SerializedName("user")
        private User user;

        // Getters và setters
        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class User {
        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("email")
        private String email;
        @SerializedName("fullName")
        private String fullName;
        @SerializedName("phoneNumber")
        private String phoneNumber;
        @SerializedName("role")
        private String role;
        @SerializedName("createdTime")
        private String createdTime;

        // Getters và setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }
    }
}
