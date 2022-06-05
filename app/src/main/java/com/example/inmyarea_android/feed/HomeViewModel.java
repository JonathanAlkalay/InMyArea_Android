package com.example.inmyarea_android.feed;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetVideoPathResponseMessage;
import com.example.inmyarea_android.model.Users.Business;
import com.example.inmyarea_android.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {


    public List<VideoItem> getPathsPedi() {
        return pathsPedi.getValue();
    }

    public List<VideoItem> getPathsCos() {
        return pathsCos.getValue();
    }

    public List<VideoItem> getPathsHair() {
        return pathsHair.getValue();
    }

    public List<VideoItem> getPathsLei() {
        return pathsLei.getValue();
    }

    private MutableLiveData<List<Business>> businesses=new MutableLiveData<>();
    private MutableLiveData<List<VideoItem>> pathsPedi=new MutableLiveData<>();
    private MutableLiveData<List<VideoItem>> pathsCos=new MutableLiveData<>();
    private MutableLiveData<List<VideoItem>> pathsHair=new MutableLiveData<>();
    private MutableLiveData<List<VideoItem>> pathsLei=new MutableLiveData<>();

    public MutableLiveData<List<Business>> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses.setValue(businesses);
        List<VideoItem> tempPathsPedi =new ArrayList<>();
        List<VideoItem> tempPathsCos =new ArrayList<>();
        List<VideoItem> tempPathsHair =new ArrayList<>();
        List<VideoItem> tempPathsLei =new ArrayList<>();
        for(int i=0;i<businesses.size();i++){
            VideoItem videoItem =new VideoItem();
            videoItem.videoTitle=businesses.get(i).getName();
            videoItem.videoDesc=businesses.get(i).getDescription();
            videoItem.owmerId=businesses.get(i).getEmail();
            String cat=businesses.get(i).getCategory();
            Listeners.instance.getVideoPath(businesses.get(i).getEmail(), data -> {
                videoItem.videoURL=data.getPath();
                switch (cat){
                    case "Pedi&Medi":
                        tempPathsPedi.add(videoItem);
                        this.pathsPedi.setValue(tempPathsPedi);
                        break;
                    case "Cosmetics":
                        tempPathsCos.add(videoItem);
                        this.pathsCos.setValue(tempPathsCos);
                        break;
                    case "Hair Styling":
                        tempPathsHair.add(videoItem);
                        this.pathsHair.setValue(tempPathsHair);
                        break;
                    case "Leisure":
                        tempPathsLei.add(videoItem);
                        this.pathsHair.setValue(tempPathsLei);
                        break;
                }
            });
        }


    }

    public List<Business> getBusinessByCategory(String category){
        List<Business> list=new ArrayList<>();
        if(businesses.getValue()!=null){
        List<Business> busi=businesses.getValue();
        for(int i=0;i<busi.size();i++) {
            if (busi.get(i).getCategory().equals(category)) {
                list.add(busi.get(i));
            }
        }
        }
        return list;
    }








}