package com.example.inmyarea_android.feed;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Appointment;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;

import java.util.ArrayList;
import java.util.Calendar;


public class EditAppointmentFragment extends Fragment {


    String userId,businessId,timep,datep,servicep;
    int yearNow,monthNow,dayNow,timeNow;
    String dateNow;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_appointment, container, false);
        userId = EditAppointmentFragmentArgs.fromBundle(getArguments()).getUserid();
        businessId = EditAppointmentFragmentArgs.fromBundle(getArguments()).getBusinessid();
        timep=EditAppointmentFragmentArgs.fromBundle(getArguments()).getTime();
        datep=EditAppointmentFragmentArgs.fromBundle(getArguments()).getDate();
        servicep=EditAppointmentFragmentArgs.fromBundle(getArguments()).getService();

        Spinner spinnerService = view.findViewById(R.id.spinner_editaposelectservice);
        Spinner spinnerTime = view.findViewById(R.id.spinner_editaposelecttime);
        TextView dateSelect= view.findViewById(R.id.editapo_dateselectTV);
        Button save=view.findViewById(R.id.apoedit_saveBT);
        ProgressBar progressBar=view.findViewById(R.id.progressBar_editapo);
        progressBar.setVisibility(View.GONE);
        dateSelect.setText(datep);


        dateSelect.setOnClickListener(v -> {
            Calendar cal=Calendar.getInstance();
            yearNow=cal.get(Calendar.YEAR);
            monthNow=cal.get(Calendar.MONTH);
            dayNow=cal.get(Calendar.DAY_OF_MONTH);
            timeNow= cal.getTime().getHours();
            dateNow=(monthNow+1)+"/"+dayNow+"/"+yearNow;

            DatePickerDialog dialog=new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,
                    mDateSetListener,yearNow,monthNow,dayNow);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String date=(month+1)+"/"+dayOfMonth+"/"+year;
                if(year==yearNow){
                    if(month==monthNow){
                        if(dayOfMonth<dayNow){
                            dateError();
                            return;
                        }else{
                            dateSelect.setText(date);
                        }
                    }else if(month<monthNow){
                        dateError();
                        return;
                    }else{
                        dateSelect.setText(date);
                    }
                }else if(year<yearNow){
                    dateError();
                    return;
                }else {
                    dateSelect.setText(date);
                }

            }
        };

        Listeners.instance.getAccountByEmail(businessId, "business", data -> {
            ArrayList<String> arr=(ArrayList<String>)data.getAccount().get("services");
            arr.add(0,"Select Service");
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr);
            spinnerService.setAdapter(adapter1);
            spinnerService.setSelection(adapter1.getPosition(servicep));
        });

        String[] items2 = new String[]{"Select Time","8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
        spinnerTime.setAdapter(adapter2);
        spinnerTime.setSelection(adapter2.getPosition(timep));

        save.setOnClickListener(v -> {
            save.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            String service=spinnerService.getItemAtPosition(spinnerService.getSelectedItemPosition()).toString();
            String time=spinnerTime.getItemAtPosition(spinnerTime.getSelectedItemPosition()).toString();
            String date=dateSelect.getText().toString().trim();
            if(service.equals("Select Service")){
                Toast.makeText(getActivity(), "please select a service ", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                save.setEnabled(true);
                return;
            }else if(time.equals("Select Time")) {
                Toast.makeText(getActivity(), "please select a time ", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                save.setEnabled(true);
                return;
            }else if(date.equals("Select Date")){
                    Toast.makeText(getActivity(), "please select a date ", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    save.setEnabled(true);
                    return;
            }else if(dateNow.equals(date)&&timeNow>=Character.getNumericValue(time.charAt(0))){
                    Toast.makeText(getActivity(), "please select a correct time ", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    save.setEnabled(true);
                    return;
            }else {
                Listeners.instance.getAccountByEmail(userId, "user", data -> {
                    String name = (String) data.getAccount().get("name");
                    String phone = (String) data.getAccount().get("phoneNumber");
                    ;
                    Appointment appointment = new Appointment(businessId, userId, service, date, name, time, phone);
                    Listeners.instance.updateAppointment(userId, businessId, datep, timep, appointment, data1 -> {save.setEnabled(true);
                        if(data1.getStatus().equals("false"))
                        {
                            Toast.makeText(getActivity(), data1.getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            save.setEnabled(true);
                            return;
                        }else {
                            Toast.makeText(getActivity(), "Appointment was edited", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).popBackStack();
                        }
                    });

                });
            }
        });

        return view;
    }


    public void dateError(){
        Toast.makeText(getActivity(), "please choose a correct date", Toast.LENGTH_LONG).show();}
}