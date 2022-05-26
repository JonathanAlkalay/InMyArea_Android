package com.example.inmyarea_android.model;

import java.util.HashSet;

public class Appointment {

    String userId,service,date,userName,phone,time;

    public Appointment( String userId, String service, String date, String userName, String time,String phone) {
        this.userId = userId;
        this.service = service;
        this.date = date;
        this.userName = userName;
        this.time = time;
        this.phone=phone;
    }
}
