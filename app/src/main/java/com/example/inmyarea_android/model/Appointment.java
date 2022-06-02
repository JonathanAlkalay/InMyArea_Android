package com.example.inmyarea_android.model;

import com.example.inmyarea_android.databinding.ApointmetRowBinding;
import com.example.inmyarea_android.model.Users.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Appointment {




    String businessId,userId,service,date,userName,phone,time;

    public Appointment() {
    }

    public Appointment(String businessId, String userId, String service, String date, String userName, String time, String phone) {
        this.businessId=businessId;
        this.userId = userId;
        this.service = service;
        this.date = date;
        this.userName = userName;
        this.time = time;
        this.phone=phone;
    }
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public HashMap<String, Object> toJson() {

        HashMap<String, Object> json = new HashMap<>();

        json.put("businessId", this.businessId);
        json.put("userId", this.userId);
        json.put("service", this.service);
        json.put("date", this.date);
        json.put("userName", this.userName);
        json.put("phone", this.phone);
        json.put("time", this.time);

        return json;
    }

    public Appointment fromJson(HashMap<String, Object> json) {

        Appointment appointment = new Appointment((String) json.get("businessId"), (String) json.get("userId"), (String) json.get("service"),
                (String) json.get("date"), (String) json.get("userName"),
                (String) json.get("time"),(String) json.get("phone"));

        return appointment;
    }
}
