package com.example.inmyarea_android.model;

import com.example.inmyarea_android.model.ResponseMessages.GetBusinessesRespMsg;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.GetAppointmentsRespMsg;
import com.example.inmyarea_android.model.ResponseMessages.GetVideoPathResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Users.Business;
import com.example.inmyarea_android.model.Users.Client;
import com.example.inmyarea_android.model.Users.User;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model_RetroFit {

    //make sure to use local computers actual ip address....check on google, there is a command in terminal
    //example "http://10.100.102.7:8080/"

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http:/10.0.0.32:8080/")
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

        HashMap<String, Object> json;

        if (type.equals("user")) {
            json = ((Client) user).toJson();
        }else {
            json = ((Business) user).toJson();
        }

        Call<MainResponseMessage> call = nodeApiServer.createAccount(email, type, json);
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

                try {
                    listener.onComplete(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetAccountResponseMessage> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void updateBasicAccountDetails(String email, String type, User updatedUser, Listeners.updateAccountDetailsListener listener) {

        HashMap<String, Object> json;

        if (type.equals("user")) {
            json = ((Client) updatedUser).toJson();
        }else {
            json = ((Business) updatedUser).toJson();
        }

        Call<MainResponseMessage> call = nodeApiServer.updateAccountDetails(email, type, json);
        call.enqueue(new Callback<MainResponseMessage>() {
            @Override
            public void onResponse(Call<MainResponseMessage> call, Response<MainResponseMessage> response) {
                try {
                    listener.onComplete(response.body());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MainResponseMessage> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void getBusinessesByCategory(String category, Listeners.getBusinessesByCategoryListener listener) {

        Call<GetBusinessesRespMsg> call = nodeApiServer.getBusinessesByCategory(category);
        call.enqueue(new Callback<GetBusinessesRespMsg>() {
            @Override
            public void onResponse(Call<GetBusinessesRespMsg> call, Response<GetBusinessesRespMsg> response) {

                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<GetBusinessesRespMsg> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void uploadVideo(File file, String email, Listeners.uploadVideoListener listener){


        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("video", file.getName(), requestBody);

        Call<MainResponseMessage> call = nodeApiServer.uploadVideo(parts, requestBody, email);
        call.enqueue(new Callback<MainResponseMessage>() {
            @Override
            public void onResponse(Call<MainResponseMessage> call, Response<MainResponseMessage> response) {

                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<MainResponseMessage> call, Throwable t) {
                System.out.print("failed");
            }
        });
    }


    public void addAppointment( Appointment appointment, Listeners.addAppointmentListener listener) {


        Call<MainResponseMessage> call = nodeApiServer.addAppointment(appointment);
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

    public void getAppointmentsByDate(String email, String date, Listeners.getAppointmentsByDateListener listener) {
        Call<GetAppointmentsRespMsg> call = nodeApiServer.getAppointmentsByDate(email,date);
        call.enqueue(new Callback<GetAppointmentsRespMsg>() {
            @Override
            public void onResponse(Call<GetAppointmentsRespMsg> call, Response<GetAppointmentsRespMsg> response) {

                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<GetAppointmentsRespMsg> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void getAppointmentsByUser(String email, Listeners.getAppointmentsByUserListener listener) {
        Call<GetAppointmentsRespMsg> call = nodeApiServer.getAppointmentByUser(email);
        call.enqueue(new Callback<GetAppointmentsRespMsg>() {
            @Override
            public void onResponse(Call<GetAppointmentsRespMsg> call, Response<GetAppointmentsRespMsg> response) {

                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<GetAppointmentsRespMsg> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void getAccountsByLocation(Double longitude, Double latitude, Listeners.getAccountsByLocationListener listener) {
        Call<GetBusinessesRespMsg> call = nodeApiServer.getAccountsByLocation(longitude,latitude);
        call.enqueue(new Callback<GetBusinessesRespMsg>() {
            @Override
            public void onResponse(Call<GetBusinessesRespMsg> call, Response<GetBusinessesRespMsg> response) {

                try {
                    listener.onComplete(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetBusinessesRespMsg> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }

    public void updateAppointment(String userEmail, String businessEmail, String date, String time, Appointment appointment, Listeners.updateAppointmentListener listener) {
        HashMap<String,Object> json= appointment==null ? new HashMap<>(): appointment.toJson();
        Call<MainResponseMessage> call = nodeApiServer.editAppointment(userEmail, businessEmail, date, time, json);
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

    public void addVideoPath(String email, String path, Listeners.addVideoPathListener listener) {
        Call<MainResponseMessage> call = nodeApiServer.addVideoPath(email, path);
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

    public void getVideoPath(String email, Listeners.getVideoPathListener listener) {
        Call<GetVideoPathResponseMessage> call = nodeApiServer.getVideoPath(email);
        call.enqueue(new Callback<GetVideoPathResponseMessage>() {
            @Override
            public void onResponse(Call<GetVideoPathResponseMessage> call, Response<GetVideoPathResponseMessage> response) {

                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<GetVideoPathResponseMessage> call, Throwable t) {
                try { throw t; }
                catch (Throwable throwable) { throwable.printStackTrace(); }
            }
        });
    }


    public void updateVideoPath(String email, String updatedPath, Listeners.updateVideoPathListener listener) {

        Call<MainResponseMessage> call = nodeApiServer.updateVideoPath(email, updatedPath);
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