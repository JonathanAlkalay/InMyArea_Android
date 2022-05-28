package com.example.inmyarea_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;

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
        TextView phone=view.findViewById(R.id.profilePhone_TV);
        Button editBt= view.findViewById(R.id.profile_editBT);
        Button apoBt= view.findViewById(R.id.profile_apointmentBT);
        ImageButton back = view.findViewById(R.id.backProflie_IB);
        calendar.setVisibility(View.GONE);

        if(type.equals("business")) {
            apoBt.setVisibility(View.GONE);
            if (emailUseridId.equals(profileEmailId)){
                editBt.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
                calendar.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToCalendarFragment(profileEmailId));
                });
            }
            serOrHis.setText("Services");
            Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                userName.setText((String)data.getAccount().get("name"));
                desc.setText((String)data.getAccount().get("description"));
                phone.setText((String)data.getAccount().get("phoneNumber"));
            });


        }else{
            if (emailUseridId.equals(profileEmailId)) {
                apoBt.setVisibility(View.GONE);
                editBt.setVisibility(View.VISIBLE);
                Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                    userName.setText((String)data.getAccount().get("name"));
                    desc.setText((String)data.getAccount().get("email"));
                    phone.setText((String)data.getAccount().get("phoneNumber"));
                });

            }else{
                apoBt.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToMakeAppointmentFragment(profileEmailId,emailUseridId));
                });
                Listeners.instance.getAccountByEmail(profileEmailId, "business", data -> {
                    userName.setText((String)data.getAccount().get("name"));
                    desc.setText((String)data.getAccount().get("email"));
                    phone.setText((String)data.getAccount().get("phoneNumber"));
                });
            }
            serOrHis.setText("History");

        }


        editBt.setOnClickListener(v -> {
            if(type.equals("user"))
            Navigation.findNavController(view).navigate((NavDirections) ProfileFragmentDirections.actionProfileFragmentToEditClientFragment(profileEmailId));
            else Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToEditBusineesFragment(profileEmailId));
        });

        //need to fix
        back.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_global_homeFragment);
        });





        return view;
    }
}