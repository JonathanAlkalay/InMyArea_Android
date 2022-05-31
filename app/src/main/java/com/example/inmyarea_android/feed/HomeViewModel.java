package com.example.inmyarea_android.feed;

import androidx.lifecycle.ViewModel;

import com.example.inmyarea_android.model.Users.Business;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {


    List<Business> businesses=new ArrayList<>();

    public HomeViewModel(List<Business> businesses) {

    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }
}