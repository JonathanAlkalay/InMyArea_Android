package com.example.inmyarea_android.model.Users;

public class Business extends User {

    String description;
    String location;
    String category;

    public Business(){super();}

    public Business(String email,String password,String name,
                    String phoneNumber, String description, String category) {

        this.email=email;
        this.passWord =password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.category = category;
    }



    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

}
