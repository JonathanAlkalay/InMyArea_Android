package com.example.inmyarea_android.model;

import com.example.inmyarea_android.model.Users.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model_RetroFit {

    //make sure to use local computers actual ip address....check on google, there is a command in terminal
    //example "http://10.100.102.7:8080/"

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.0.26:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private NodeApiServerRequests nodeApiServer = retrofit.create(NodeApiServerRequests.class);



    public void logIn(String userName, String passWord, String type,Listeners.logInListener listener) {

        Call<ResponseMessage> call = nodeApiServer.logIn(userName,passWord,type);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                listener.onComplete(response.body());
            }
            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) { }
        });
    }

    public void createAccount(String email, String type, User user, Listeners.createAccountListener listener) {

        Call<ResponseMessage> call = nodeApiServer.createAccount(email, type, user);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                listener.onComplete(response.body());
            }
            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) { }
        });

    }
}
