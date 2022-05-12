package com.example.inmyarea_android.model.ResponseMessages;

import com.google.gson.annotations.SerializedName;

public class MainResponseMessage {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;


    public String getStatus() { return status; }

    public String getMessage() { return message; }
}
