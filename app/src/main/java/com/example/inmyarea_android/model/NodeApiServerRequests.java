package com.example.inmyarea_android.model;
import com.example.inmyarea_android.model.ResponseMessages.BsnssByCategoryRespMsg;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Users.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NodeApiServerRequests {

    @GET("logIn={email}&{passWord}&{type}")
    Call<MainResponseMessage> logIn(@Path("email")String email, @Path("passWord")String passWord, @Path("type")String type);

    @GET("logOut={email}&{type}")
    Call<MainResponseMessage> logOut(@Path("email")String email, @Path("type")String type);

    @POST("createAccount={email}&{type}")
    Call<MainResponseMessage> createAccount(@Path("email")String email, @Path("type")String type, @Body HashMap<String, Object> json);

    @GET("getAccount={email}&{type}")
    Call<GetAccountResponseMessage> getAccountByEmail(@Path("email")String email, @Path("type")String type);

    @POST("updateAccount={email}&{type}")
    Call<MainResponseMessage> updateAccountDetails(@Path("email")String email, @Path("type")String type, @Body HashMap<String, Object> json);

    @GET("getAccountsByCategory={category}")
    Call<BsnssByCategoryRespMsg> getBusinessesByCategory(@Path("category")String category);
}