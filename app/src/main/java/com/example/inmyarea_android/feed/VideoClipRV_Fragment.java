package com.example.inmyarea_android.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Users.Business;
import com.example.inmyarea_android.model.VideoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


public class VideoClipRV_Fragment extends Fragment {
    private HomeViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video_clip_r_v_, container, false);

        String emailuseridId = VideoClipRV_FragmentArgs.fromBundle(getArguments()).getUseremailId();
        String categoryId = VideoClipRV_FragmentArgs.fromBundle(getArguments()).getCategoryid();
        mViewModel = ViewModelProviders.of(this.getActivity()).get(HomeViewModel.class);
//        List<Business>catList= mViewModel.getBusinessByCategory(categoryId);



        final ViewPager2 videosViewPager= view.findViewById(R.id.videoViewPagger);
        List<VideoItem> videoItemList=new ArrayList<>();
//        videoItemList=mViewModel.createVideoList(catList);

        switch (categoryId){
            case "Pedi&Medi":
                videoItemList=mViewModel.getPathsPedi();
                break;
            case "Cosmetics":
                videoItemList=mViewModel.getPathsCos();
                break;
            case "Hair Styling":
                videoItemList=mViewModel.getPathsHair();
                break;
            case "Leisure":
                videoItemList=mViewModel.getPathsLei();
                break;
        }

        VideoAdapter videoAdapter = new VideoAdapter(videoItemList);
        videosViewPager.setOffscreenPageLimit(2);
        videosViewPager.setAdapter(videoAdapter);

        List<VideoItem> finalVideoItemList = videoItemList;
        videoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = finalVideoItemList.get(position).owmerId;
                Navigation.findNavController(view).navigate((NavDirections) VideoClipRV_FragmentDirections.actionVideoClipRVFragmentToProfileFragment(emailuseridId,id,"user"));

            }
        });


        return view;
    }



    interface OnItemClickListener{
        void onItemClick(int position);
    }

}