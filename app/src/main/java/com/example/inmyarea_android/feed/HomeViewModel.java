package com.example.inmyarea_android.feed;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmyarea_android.model.Users.Business;
import com.example.inmyarea_android.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {



    private MutableLiveData<List<Business>> businesses=new MutableLiveData<>();

    public MutableLiveData<List<Business>> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses.setValue(businesses);
    }

    public List<Business> getBusinessByCategory(String category){
        List<Business> list=new ArrayList<>();
        List<Business> busi=businesses.getValue();
        for(int i=0;i<busi.size();i++) {
            if (busi.get(i).getCategory().equals(category))
                list.add(busi.get(i));
        }
        return list;
    }

    public List<VideoItem> createVideoList(List<Business> businesses){
        List<VideoItem> videoItemList=new ArrayList<>();
        for(int i=0;i<businesses.size();i++){
            VideoItem videoItem =new VideoItem();
            videoItem.videoURL="https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
            videoItem.videoTitle=businesses.get(i).getName();
            videoItem.videoDesc=businesses.get(i).getDescription();
            videoItem.owmerId=businesses.get(i).getEmail();
            videoItemList.add(videoItem);

        }
        return videoItemList;
    }







}