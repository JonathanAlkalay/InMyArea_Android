package com.example.inmyarea_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.Model_RetroFit;
import com.example.inmyarea_android.model.NodeApiServerRequests;
import com.example.inmyarea_android.model.ResponseMessage;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Listeners.instance.logIn("yona", "123", "user", new Listeners.logInListener() {
            @Override
            public void onComplete(ResponseMessage data) {

                String s = data.getStatus();
                String m = data.getMessage();
            }
        });
    }
}