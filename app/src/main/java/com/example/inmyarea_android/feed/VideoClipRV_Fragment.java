package com.example.inmyarea_android.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

        String emailuseridId = VideoClipRV_FragmentArgs.fromBundle(getArguments()).getUseremailId();

        final ViewPager2 videosViewPager= view.findViewById(R.id.videoViewPagger);
        List<VideoItem> videoItemList=new ArrayList<>();
        VideoItem videoItem =new VideoItem();
        videoItem.videoURL="https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
        videoItem.videoTitle="Business Title 1";
        videoItem.videoDesc="Business description....";
        videoItem.owmerId="luay@email.com";
        videoItemList.add(videoItem);

        VideoItem videoItem2 =new VideoItem();
        videoItem2.videoURL="https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";
        videoItem2.videoTitle="Business Title 2";
        videoItem2.videoDesc="Business description....";
        videoItem2.owmerId="liraz@email.com";
        videoItemList.add(videoItem2);

        VideoAdapter videoAdapter = new VideoAdapter(videoItemList);
        videosViewPager.setAdapter(videoAdapter);

        videoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = videoItemList.get(position).owmerId;
                Navigation.findNavController(view).navigate(VideoClipRV_FragmentDirections.actionVideoClipRVFragmentToProfileFragment(emailuseridId,id,"business"));

            }
        });


        return view;
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }

}