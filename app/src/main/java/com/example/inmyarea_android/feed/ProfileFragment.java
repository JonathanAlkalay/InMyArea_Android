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

        if(type.equals("business")) {
            if (emailUseridId.equals(profileEmailId)) {
                apoBt.setVisibility(View.GONE);
            } else {
                editBt.setVisibility(View.GONE);
            }
            serOrHis.setText("Services");
            Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                userName.setText((String)data.getAccount().get("name"));
                desc.setText((String)data.getAccount().get("description"));
                phone.setText((String)data.getAccount().get("phoneNumber"));
            });

            apoBt.setOnClickListener(v -> {

                Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToMakeAppointmentFragment(profileEmailId,emailUseridId));
                //google calendar
//                Intent intent = new Intent(Intent.ACTION_INSERT);
//                intent.setData(CalendarContract.Events.CONTENT_URI);
//                startActivity(intent);
            });
        }else{
            apoBt.setVisibility(View.GONE);
            serOrHis.setText("History");
            Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                userName.setText((String)data.getAccount().get("name"));
                desc.setText((String)data.getAccount().get("email"));
                phone.setText((String)data.getAccount().get("phoneNumber"));
            });
        }


        editBt.setOnClickListener(v -> {
            if(type.equals("user"))
            Navigation.findNavController(view).navigate((NavDirections) ProfileFragmentDirections.actionProfileFragmentToEditClientFragment(emailUseridId));
        });

        back.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_global_homeFragment);
        });





        return view;
    }
}