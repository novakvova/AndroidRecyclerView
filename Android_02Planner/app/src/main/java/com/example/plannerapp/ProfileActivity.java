package com.example.plannerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.plannerapp.constants.Urls;
import com.example.plannerapp.dto.LoginBadRequest;
import com.example.plannerapp.dto.RegisterResultDTO;
import com.example.plannerapp.network.ImageRequester;
import com.example.plannerapp.network.services.AccountService;
import com.example.plannerapp.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private String token;
    private ImageRequester imageRequester;
    private NetworkImageView myImage;
    SharedPreferences mSettings;
    private static int RESULT_LOAD_IMAGE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSettings = getSharedPreferences("planner_settings", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (mSettings.contains("token")) {
            Log.d("token", mSettings.getString("token", ""));
        }
        token = getIntent().getStringExtra("token");

        mSettings.edit().putString("token", token).apply();

        final TextView username = findViewById(R.id.UserName);
        AccountService.getInstance()
                .getJSONApi()
                .Profile("Bearer " + token)
                .enqueue(new Callback<RegisterResultDTO>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<RegisterResultDTO> call, Response<RegisterResultDTO> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            username.setText(response.body().getUserName());
                            String url = Urls.BASE + response.body().getImage();
                            imageRequester = ImageRequester.getInstance();
                            myImage = findViewById(R.id.userAvatar);
                            imageRequester.setImageFromUrl(myImage, url);
                            Log.d("Server: ", "Good");
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResultDTO> call, Throwable t) {
                        CommonUtils.hideLoading();
                        Log.e("server", "Bad");
                    }
                });
    }

    void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            final ImageView im = (ImageView) findViewById(R.id.userAvatar);
            im.setImageURI(imageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            DoWork();
        }
    }

    public void DoWork() {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, "name" + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);
        MultipartBody.Part file1 = body;

        AccountService.getInstance().getJSONApi().UploadFile(file1)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful())
                            Log.d("Server", "good");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    public void OnClickBtn(View view) {
        imageChooser();
    }
}