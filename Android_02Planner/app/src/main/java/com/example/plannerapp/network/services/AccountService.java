package com.example.plannerapp.network.services;

import com.example.plannerapp.constants.Urls;
import com.example.plannerapp.network.ApiAccount;
import com.example.plannerapp.network.interceptors.JWTInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountService {
    private static AccountService instance;
    private static final String BASE_URL = Urls.BASE;
    private Retrofit retrofit;

    public AccountService() {
        OkHttpClient.Builder client = new OkHttpClient
                .Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new JWTInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static AccountService getInstance() {
        if (instance == null)
            instance = new AccountService();
        return instance;
    }

    public ApiAccount getJSONApi()
    {
        return retrofit.create(ApiAccount.class);
    }
}
