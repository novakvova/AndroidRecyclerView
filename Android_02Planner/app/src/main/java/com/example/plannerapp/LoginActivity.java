package com.example.plannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.example.plannerapp.constants.Urls;
import com.example.plannerapp.dto.LoginBadRequest;
import com.example.plannerapp.dto.LoginDTO;
import com.example.plannerapp.dto.LoginResultDTO;
import com.example.plannerapp.network.ImageRequester;
import com.example.plannerapp.network.services.AccountService;
import com.example.plannerapp.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String url = Urls.BASE + "/images/1.jpeg";

        //"https://png.pngtree.com/element_our/png/20180912/coffee-time-png_91570.jpg";

        imageRequester = ImageRequester.getInstance();
        myImage = findViewById(R.id.myimg);
        imageRequester.setImageFromUrl(myImage, url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_settings).setTitle("Реєстрація");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit_settings:
                finishAffinity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnClickBtn(View view) {
        final TextInputEditText email = findViewById(R.id.textInputEmail);
        final TextInputLayout emailLayout = findViewById(R.id.textFieldEmail);
        final TextInputEditText password = findViewById(R.id.textInputPassword);
        final TextInputLayout passwordLayout = findViewById(R.id.textFieldPassword);
        LoginDTO dto = new LoginDTO(email.getText().toString(), password.getText().toString());
//        if (dto.getEmail().isEmpty()) {
//            emailLayout.setError("Введіть пошту!");
//            return;
//        } else
//            emailLayout.setError("");
//
//        if (dto.getPassword().isEmpty()) {
//            passwordLayout.setError("Введіть пароль!");
//            return;
//        } else
//            passwordLayout.setError("");
        CommonUtils.showLoading(this);
        AccountService.getInstance()
                .getJSONApi()
                .Login(dto)
                .enqueue(new Callback<LoginResultDTO>() {
                    @Override
                    public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                        CommonUtils.hideLoading();
                        if (response.isSuccessful()) {
                            Log.d("server", "Good");
                            Log.d("Token:", response.body().getToken());
                        } else {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
                                LoginBadRequest result = gson.fromJson(json, LoginBadRequest.class);
                                emailLayout.setError(result.getEmail());
                                passwordLayout.setError(result.getPassword());
                            } catch (Exception ex) {
                                email.setText(ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResultDTO> call, Throwable t) {
                        CommonUtils.hideLoading();
                        Log.e("server", "Bad");
                    }
                });
        Log.d("Click me", email.getText().toString());
    }
}