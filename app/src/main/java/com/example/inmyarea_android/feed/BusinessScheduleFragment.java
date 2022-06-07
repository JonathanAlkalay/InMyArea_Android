package com.example.inmyarea_android.feed;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.inmyarea_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class BusinessScheduleFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_business_schedule, container, false);
        //String emailid=BusinessScheduleFragmentArgs.fromBundle(getArguments()).getEmailid();
        Button save=view.findViewById(R.id.register_schedule);
        Button openSun=view.findViewById(R.id.opentimesun_scheduleBT),
                openMon=view.findViewById(R.id.opentimemon_scheduleBT),
                openTues=view.findViewById(R.id.opentimetues_scheduleBT),
                openWed=view.findViewById(R.id.opentimewednes_scheduleBT),
                openThurs=view.findViewById(R.id.opentimethurs_scheduleBT),
                openFri=view.findViewById(R.id.opentimefri_scheduleBT),
                openSatu=view.findViewById(R.id.opentimesatur_scheduleBT),
                closeSun=view.findViewById(R.id.closetimesun_scheduleBT),
                closeMon=view.findViewById(R.id.closetimemon_scheduleBT),
                closeTues=view.findViewById(R.id.closetimetues_scheduleBT),
                closeWed=view.findViewById(R.id.closetimewednes_scheduleBT),
                closeThurs=view.findViewById(R.id.closetimethurs_scheduleBT),
                closeFri=view.findViewById(R.id.closetimefri_scheduleBT),
                closeSatu=view.findViewById(R.id.closetimesatur_scheduleBT);
        Switch sunSwitch=view.findViewById(R.id.schedule_sundayswitch),
                monSwitch=view.findViewById(R.id.schedule_mondayswitch),
                tuesSwitch=view.findViewById(R.id.schedule_tuesdayswitch),
                wedSwitch=view.findViewById(R.id.schedule_wednesdayswitch),
                thursSwitch=view.findViewById(R.id.schedule_thursdayswitch),
                friSwitch=view.findViewById(R.id.schedule_fridayswitch),
                satuSwitch=view.findViewById(R.id.schedule_saturdayswitch);

//        List<Button> btns= new ArrayList<>(Arrays.asList(openSun, openMon, openTues, openWed, openThurs, openFri, openSatu));
//        for (Button b: btns){
//            b.setOnClickListener(v -> {
//                final Calendar c = Calendar.getInstance();
//                int hour = c.get(Calendar.HOUR_OF_DAY);
//                int minute = c.get(Calendar.MINUTE);
//
//                TimePickerDialog dialog=new TimePickerDialog()
//                DatePickerDialog dialog=new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,
//                        mDateSetListener,yearNow,monthNow,dayNow);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            });
//        }



        return view;
    }


}