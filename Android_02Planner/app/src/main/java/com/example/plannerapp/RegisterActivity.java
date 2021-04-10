package com.example.plannerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.plannerapp.constants.Urls;
import com.example.plannerapp.dto.LoginBadRequest;
import com.example.plannerapp.dto.LoginDTO;
import com.example.plannerapp.dto.LoginResultDTO;
import com.example.plannerapp.dto.RegisterDTO;
import com.example.plannerapp.dto.RegisterResultDTO;
import com.example.plannerapp.network.services.AccountService;
import com.example.plannerapp.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_settings).setTitle("Вхід");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit_settings:
                finishAffinity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnClickBtn(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        final TextInputEditText email = findViewById(R.id.textInputEmail);
        final TextInputLayout emailLayout = findViewById(R.id.textFieldEmail);
        final TextInputEditText userName = findViewById(R.id.textInputUserName);
        final TextInputLayout userNameLayout = findViewById(R.id.textFieldUserName);
        final TextInputEditText password = findViewById(R.id.textInputPassword);
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);
        final TextInputEditText password2 = findViewById(R.id.textInputPassword2);
        final TextInputLayout passwordLayout2 = findViewById(R.id.textFieldPassword2);
        RegisterDTO dto = new RegisterDTO(email.getText().toString(), password.getText().toString(),
                userName.getText().toString(), email.getText().toString());
        if (!dto.getPassword().equals(password2.getText().toString())) {
            passwordLayout2.setError("Паролі не співпадають!");
            return;
        }
        CommonUtils.showLoading(this);
        AccountService.getInstance()
                .getJSONApi()
                .Registration(dto)
                .enqueue(new Callback<RegisterResultDTO>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<RegisterResultDTO> call, Response<RegisterResultDTO> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            String token = response.body().getToken();
                            intent.putExtra("token", token);
                            startActivity(intent);
                            Log.d("Server: ", "Good");

                        } else {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
//                                LoginBadRequest result = gson.fromJson(json, LoginBadRequest.class);
//                                emailLayout.setError(result.getEmail());
//                                passwordLayout.setError(result.getPassword());
                            } catch (Exception ex) {
                                email.setText(ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResultDTO> call, Throwable t) {
                        CommonUtils.hideLoading();
                        Log.e("server", "Bad");
                    }
                });
    }
}