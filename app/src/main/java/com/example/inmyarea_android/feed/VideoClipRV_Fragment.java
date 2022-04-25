package com.example.inmyarea_android.feed;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.VideoItem;

import java.util.ArrayList;
import java.util.List;


public class VideoClipRV_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video_clip_r_v_, container, false);

        final ViewPager2 videosViewPager= view.findViewById(R.id.videoViewPagger);
        List<VideoItem> videoItemList=new ArrayList<>();
        VideoItem videoItem =new VideoItem();
        videoItem.videoURL="https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
        videoItem.videoTitle="Business Title 1";
        videoItem.videoDesc="Business description....";
        videoItemList.add(videoItem);

        VideoItem videoItem2 =new VideoItem();
        videoItem2.videoURL="https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
        videoItem2.videoTitle="Business Title 2";
        videoItem2.videoDesc="Business description....";
        videoItemList.add(videoItem2);

        videosViewPager.setAdapter(new Videodapter(videoItemList));

        return view;
    }

}