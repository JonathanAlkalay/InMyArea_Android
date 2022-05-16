package com.example.inmyarea_android.model.Users;

import java.util.HashMap;
import java.util.Map;

public class Client extends User {

    public Client(){super();}

    public Client(String email,String password,String name,String phoneNumber) {

        this.email=email;
        this.passWord =password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public HashMap<String, Object> toJson(){

        HashMap<String, Object> json = new HashMap<>();

        json.put("email", this.email);
        json.put("passWord", this.passWord);
        json.put("name", this.name);
        json.put("phoneNumber", this.phoneNumber);

        return json;
    }

    public Client fromJson(HashMap<String, Object> json){

        return new Client((String)json.get("email"), (String)json.get("passWord"), (String)json.get("name"),
                (String)json.get("phoneNumber"));
    }
}