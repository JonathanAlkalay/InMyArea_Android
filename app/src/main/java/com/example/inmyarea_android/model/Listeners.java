package com.example.inmyarea_android.model;

import com.example.inmyarea_android.model.Users.User;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class Listeners {

    public static final Listeners instance = new Listeners();
    Model_RetroFit retroFit = new Model_RetroFit();


    public interface logInListener{
        void onComplete(ResponseMessage data);
    }
    public void logIn(String userName, String passWord, String type,logInListener listener){
        retroFit.logIn(userName, passWord, type, listener);
    }

    public interface createAccountListener{
        void onComplete(ResponseMessage data);
    }
    public void createAccount(String email, String type, User user, createAccountListener listener) {
        retroFit.createAccount(email, type, user, listener);
    }

    public interface uploadVideoListener{
        void onComplete(ResponseMessage data);
    }
    public void uploadVideo(File file, String email, String type, uploadVideoListener listener){
        retroFit.uploadVideo(file, email, type, listener);
    }
}
