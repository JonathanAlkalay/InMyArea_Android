package com.example.inmyarea_android.model;

import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
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



    public void logIn(String userName, String passWord, String type, Listeners.logInListener listener) {

        Call<MainResponseMessage> call = nodeApiServer.logIn(userName,passWord,type);
        call.enqueue(new Callback<MainResponseMessage>() {
            @Override
            public void onResponse(Call<MainResponseMessage> call, Response<MainResponseMessage> response) {

                listener.onComplete(response.body());
            }
            @Override
            public void onFailure(Call<MainResponseMessage> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void createAccount(String email, String type, User user, Listeners.createAccountListener listener) {

        Call<MainResponseMessage> call = nodeApiServer.createAccount(email, type, user);
        call.enqueue(new Callback<MainResponseMessage>() {
            @Override
            public void onResponse(Call<MainResponseMessage> call, Response<MainResponseMessage> response) {

                listener.onComplete(response.body());
            }
            @Override
            public void onFailure(Call<MainResponseMessage> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void getAccountByEmail(String email, String type, Listeners.getAccountByEmailListener listener) {
        Call<GetAccountResponseMessage> call = nodeApiServer.getAccountByEmail(email, type);
        call.enqueue(new Callback<GetAccountResponseMessage>() {
            @Override
            public void onResponse(Call<GetAccountResponseMessage> call, Response<GetAccountResponseMessage> response) {

                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<GetAccountResponseMessage> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void updateBasicAccountDetails(String email, String type, User updatedUser, Listeners.updateAccountDetailsListener listener) {
        Call<MainResponseMessage> call = nodeApiServer.updateAccountDetails(email, type, updatedUser);
        call.enqueue(new Callback<MainResponseMessage>() {
            @Override
            public void onResponse(Call<MainResponseMessage> call, Response<MainResponseMessage> response) {
                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<MainResponseMessage> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }
}
