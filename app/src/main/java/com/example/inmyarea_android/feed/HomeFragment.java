package com.example.inmyarea_android.feed;



import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.login.LoginFragmentDirections;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    String email,type;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment, container, false);
        email = getArguments().getString("useremail_id");
        type = getArguments().getString("type");
//        if(type.equals("business")){
//            Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment(email,email,type));
//        }


        TextView homeTV=view.findViewById(R.id.home_TV);
        homeTV.setText(email);

        Button bt = view.findViewById(R.id.catagory1_BT);
        Button profile = view.findViewById(R.id.toProflie_BT);
        profile.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate((NavDirections) HomeFragmentDirections.actionHomeFragmentToProfileFragment(email,email,type));
        });

        bt.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate((NavDirections) HomeFragmentDirections.actionHomeFragmentToVideoClipRVFragment(email));
        });

        return view;

    }


}