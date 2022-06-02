package com.example.inmyarea_android.model.ResponseMessages;

import com.google.gson.annotations.SerializedName;

public class GetVideoPathResponseMessage {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;


    @SerializedName("path")
    private String path;


    public String getStatus() { return status; }

    public String getMessage() { return message; }
    public String getPath() {
        return path;
    }

}
