package com.example.inmyarea_android.model;

public class Business extends User {

    String name;
    String ownerName;
    String phoneNumber;
    String description;
    String location;
    String category;

    public Business(String email,String password,String name, String ownerName, String phoneNumber, String description, String location, String category) {
        this.email=email;
        this.password=password;
        this.name = name;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.location = location;
        this.category = category;
    }
}
