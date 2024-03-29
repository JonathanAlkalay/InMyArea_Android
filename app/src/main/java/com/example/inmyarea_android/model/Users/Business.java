package com.example.inmyarea_android.model.Users;

import com.example.inmyarea_android.model.Appointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Business extends User {

    String description;
    String location;
    String category;
    ArrayList<String> services;



    //HashMap<String , HashSet<Integer>> businessHours;



    Double longitude,latitude;

    public Business() {
        super();
    }

    public Business(String email, String password, String name,
                    String phoneNumber, String description, String category) {

        this.email = email;
        this.passWord = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.category = category;
    }


    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

//    public HashMap<String, HashSet<Integer>> getBusinessHours() {
//        return businessHours;
//    }
//
//    public void setBusinessHours(HashMap<String, HashSet<Integer>> businessHours) {
//        this.businessHours = businessHours;
//    }

    public HashMap<String, Object> toJson() {

        HashMap<String, Object> json = new HashMap<>();

        json.put("email", this.email);
        json.put("passWord", this.passWord);
        json.put("name", this.name);
        json.put("phoneNumber", this.phoneNumber);
        json.put("description", this.description);
        json.put("location", this.location);
        json.put("category", this.category);
        json.put("services", this.services);
        json.put("longitude",this.longitude);
        json.put("latitude",this.latitude);
        //json.put("businessHours",this.businessHours);


        return json;
    }

    public Business fromJson(HashMap<String, Object> json) {

        Business business = new Business((String) json.get("email"), (String) json.get("passWord"), (String) json.get("name"),
                (String) json.get("phoneNumber"), (String) json.get("description"),
                (String) json.get("category"));

        business.setLocation((String) json.get("location"));
        business.setServices((ArrayList<String>)json.get("services"));
        business.setLongitude((Double)json.get("longitude") );
        business.setLatitude((Double)json.get("latitude") );
        //business.setBusinessHours((HashMap<String, HashSet<Integer>>) json.get("businessHours"));

        return business;
    }
}