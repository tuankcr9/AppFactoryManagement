package com.example.plp_app.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private SharedPreferences sharedPreferences;

    public AuthInterceptor(Context context) {
        this.sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = sharedPreferences.getString("accessToken", null);

        Request request = chain.request();
        if (token != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            Log.d("ACCESS TOKEN", "ACCESS TOKEN: " + token);
        }
        return chain.proceed(request);
    }
}
