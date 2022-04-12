package com.example.inmyarea_android.model;
import com.example.inmyarea_android.model.ResponseMessage;
import com.example.inmyarea_android.model.Users.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NodeApiServerRequests {

    @GET("logIn={email}&{passWord}&{type}")
    Call<ResponseMessage> logIn(@Path("email")String email, @Path("passWord")String passWord, @Path("type")String type);

    @GET("logOut={email}&{type}")
    Call<ResponseMessage> logOut(@Path("email")String email, @Path("type")String type);

    @POST("createAccount={email}&{type}")
    Call<ResponseMessage> createAccount(@Path("email")String email, @Path("type")String type, @Body User user);
}
