package com.example.inmyarea_android.model.ResponseMessages;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class GetAppointmentsRespMsg {


    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("appointments")
    private List<HashMap<String, Object>> appointments;

    public String getStatus() { return status; }

    public String getMessage() { return message; }

    public List<HashMap<String, Object>> getAppointments() { return appointments; }
}
