package com.example.inmyarea_android.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.inmyarea_android.R;

import java.io.File;


public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        String emailUseridId = ProfileFragmentArgs.fromBundle(getArguments()).getUseremailId();
        String profileEmailId = ProfileFragmentArgs.fromBundle(getArguments()).getProfileEmailId();
        String type = ProfileFragmentArgs.fromBundle(getArguments()).getType();

        TextView userName= view.findViewById(R.id.profile_nameTV);
        TextView desc= view.findViewById(R.id.profile_descTV);
        TextView calendar= view.findViewById(R.id.calendar_clickTV);
        TextView serOrHis= view.findViewById(R.id.profile_serorhisTV);
        Button editBt= view.findViewById(R.id.profile_editBT);
        Button apoBt= view.findViewById(R.id.profile_apointmentBT);

        if(type.equals("business")) {
            if (emailUseridId.equals(profileEmailId)) {
                apoBt.setVisibility(View.GONE);
            } else {
                editBt.setVisibility(View.GONE);
            }

        }else{
            apoBt.setVisibility(View.GONE);
        }


        editBt.setOnClickListener(v -> {
            if(type.equals("user"))
            Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToEditClientFragment(emailUseridId));
        });





        return view;
    }
}