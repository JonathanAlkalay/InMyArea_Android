package com.example.inmyarea_android.model;

import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Users.User;

public class Listeners {

    public static final Listeners instance = new Listeners();
    Model_RetroFit retroFit = new Model_RetroFit();


    public interface logInListener{
        void onComplete(MainResponseMessage data);
    }
    public void logIn(String userName, String passWord, String type, logInListener listener){
        retroFit.logIn(userName, passWord, type, listener);
    }

    public interface createAccountListener {
        void onComplete(MainResponseMessage data);
    }
    public void createAccount(String email, String type, User user, createAccountListener listener) {
        retroFit.createAccount(email, type, user, listener);
    }

    public interface getAccountByEmailListener{
        void onComplete(GetAccountResponseMessage data);
    }
    public void getAccountByEmail(String email, String type, getAccountByEmailListener listener){
        retroFit.getAccountByEmail(email, type, listener);
    }

    public interface updateAccountDetailsListener{
        void onComplete(MainResponseMessage data);
    }
    public void updateAccountDetails(String email, String type, User updatedUser, updateAccountDetailsListener listener){
        retroFit.updateBasicAccountDetails(email, type, updatedUser, listener);
    }
}
