package com.example.inmyarea_android.model;
import com.example.inmyarea_android.model.ResponseMessages.GetBusinessesRespMsg;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.GetAppointmentsRespMsg;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<GetBusinessesRespMsg> getBusinessesByCategory(@Path("category")String category);

    @Multipart
    @POST("uploadVideo={email}")
    Call<MainResponseMessage> uploadVideo(@Part MultipartBody.Part part, @Part("video") RequestBody requestBody, @Path("email")String email);

    @POST("addAppointment")
    Call<MainResponseMessage> addAppointment( @Body Appointment appointment);

    @GET("getAppointmentsByDate={email}&{date}")
    Call<GetAppointmentsRespMsg> getAppointmentsByDate(@Path("email")String email, @Path("date")String date);

    @GET("getAppointmentByUser={email}")
    Call<GetAppointmentsRespMsg> getAppointmentByUser(@Path("email")String email);

    @GET("getAccountsByLocation={long}&{lat}")
    Call<GetBusinessesRespMsg> getAccountsByLocation(@Path("long")Double longitude,@Path("lat")Double latitude);

    @POST("editAppointment={userEmail}&{businessEmail}&{date}&{time}")
    Call<MainResponseMessage> editAppointment(@Path("userEmail")String userEmail, @Path("businessEmail")String businessEmail,
                                              @Path("date")String date, @Path("time")String time, @Body Appointment updatedAppointment);
}