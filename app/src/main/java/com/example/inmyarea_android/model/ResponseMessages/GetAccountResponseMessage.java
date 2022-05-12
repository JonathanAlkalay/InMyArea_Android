package com.example.inmyarea_android.model.ResponseMessages;

import com.example.inmyarea_android.model.Users.User;
import com.google.gson.annotations.SerializedName;

public class GetAccountResponseMessage {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("account")
    private User account;


    public String getStatus() { return status; }

    public String getMessage() { return message; }

    public User getAccount() { return account;}
}
