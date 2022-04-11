package com.example.inmyarea_android.model;

public class Client extends User {


    String name;
    String phoneNumber;



    public Client(String email,String password,String name,String phoneNumber) {
        this.email=email;
        this.password=password;
        this.name=name;
        this.phoneNumber=phoneNumber;
    }
}
