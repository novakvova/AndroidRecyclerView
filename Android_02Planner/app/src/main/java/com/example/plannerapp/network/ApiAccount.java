package com.example.plannerapp.network;

import com.example.plannerapp.dto.LoginDTO;
import com.example.plannerapp.dto.LoginResultDTO;
import com.example.plannerapp.dto.RegisterDTO;
import com.example.plannerapp.dto.RegisterResultDTO;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiAccount {
    @POST("/api/account/login")
    public Call<LoginResultDTO> Login(@Body LoginDTO loginDTO);

    @POST("/api/account/registration")
    public Call<RegisterResultDTO> Registration(@Body RegisterDTO registerDTO);

    @GET("/api/user/profile")
    public Call<RegisterResultDTO> Profile();

    @Multipart
    @POST("/api/user/upload")
    public Call<String> UploadFile(@Part MultipartBody.Part file);
}
