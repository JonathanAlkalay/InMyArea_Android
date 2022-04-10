package com.example.inmyarea_android.model;

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

}
