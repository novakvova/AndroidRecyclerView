package com.example.plannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.example.plannerapp.constants.Urls;
import com.example.plannerapp.network.ImageRequester;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}