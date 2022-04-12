package com.example.inmyarea_android.model;

import com.example.inmyarea_android.model.Users.User;

public class Listeners {

    public static final Listeners instance = new Listeners();
    Model_RetroFit retroFit = new Model_RetroFit();


    public interface logInListener{
        void onComplete(ResponseMessage data);
    }
    public ResponseMessage logIn(String userName, String passWord, String type,logInListener listener){
        retroFit.logIn(userName, passWord, type, listener);
        return null;
    }

    public interface createAccountListener {
        void onComplete(ResponseMessage data);
    }
    public ResponseMessage createAccount(String email, String type, User user, createAccountListener listener) {
        retroFit.createAccount(email, type, user, listener);
        return null;
    }
}
