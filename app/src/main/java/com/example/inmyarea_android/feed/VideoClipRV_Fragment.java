package com.example.inmyarea_android.feed;

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
        videoItem.videoURL="https://cdn.videvo.net/videvo_files/video/free/2021-04/large_watermarked/210329_06B_Bali_1080p_005_preview.mp4";
        videoItem.videoTitle="let's see if works";
        videoItem.videoDesc="pleasseeeeeeeeeee work, because we are need a good great score+ I love Luay ";
        videoItemList.add(videoItem);

        VideoItem videoItem2 =new VideoItem();
        videoItem2.videoURL="https://cdn.videvo.net/videvo_files/video/free/2021-04/large_watermarked/210329_06B_Bali_1080p_005_preview.mp4";
        videoItem2.videoTitle="let's see if works2";
        videoItem2.videoDesc="pleasseeeeeeeeeee work2, because we are need a good great score+ I love Luay ";
        videoItemList.add(videoItem2);

        videosViewPager.setAdapter(new Videodapter(videoItemList));

        return view;
    }

}