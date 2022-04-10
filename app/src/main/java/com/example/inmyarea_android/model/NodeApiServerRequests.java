package com.example.inmyarea_android.model;
import com.example.inmyarea_android.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface NodeApiServerRequests {

    @GET("logIn={userName}&{passWord}&{type}")
    Call<ResponseMessage> logIn(@Path("userName")String userName, @Path("passWord")String passWord, @Path("type")String type);

    @GET("logOut={userName}&{type}")
    Call<ResponseMessage> logOut(@Path("userName")String userName, @Path("type")String type);

    @GET("createAccount={userName}&{passWord}&{type}")
    Call<ResponseMessage> createAccount(@Path("userName")String userName, @Path("passWord")String passWord, @Path("type")String type);
}
