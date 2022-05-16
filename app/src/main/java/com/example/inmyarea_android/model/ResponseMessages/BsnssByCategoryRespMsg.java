package com.example.inmyarea_android.model.ResponseMessages;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class BsnssByCategoryRespMsg {

   @SerializedName("status")
   private String status;

   @SerializedName("message")
   private String message;

   @SerializedName("accounts")
   private List<HashMap<String, Object>> accounts;

   public String getStatus() { return status; }

   public String getMessage() { return message; }

   public List<HashMap<String, Object>> getAccounts() { return accounts; }

}
