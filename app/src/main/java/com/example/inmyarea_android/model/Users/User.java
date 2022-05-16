package com.example.inmyarea_android.model.Users;

import java.util.HashMap;

public class User {

    String email;
    String passWord;
    String name;
    String phoneNumber;

    public User() {}

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassWord() { return passWord; }

    public void setPassWord(String passWord) { this.passWord = passWord; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}