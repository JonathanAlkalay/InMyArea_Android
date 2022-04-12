package com.example.inmyarea_android.model.Users;

public class Business extends User {

    String ownerName;
    String description;
    String location;
    String category;

    public Business(){super();}

    public Business(String email,String password,String name, String ownerName,
                    String phoneNumber, String description, String location, String category) {

        this.email=email;
        this.passWord =password;
        this.name = name;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.location = location;
        this.category = category;
    }

    public String getOwnerName() { return ownerName; }

    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

}
