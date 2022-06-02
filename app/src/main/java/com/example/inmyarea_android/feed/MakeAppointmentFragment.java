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
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MakeAppointmentFragment extends Fragment {

    int yearNow,monthNow,dayNow,timeNow;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_make_appointment, container, false);
        String userId=MakeAppointmentFragmentArgs.fromBundle(getArguments()).getUserid();
        String businessId=MakeAppointmentFragmentArgs.fromBundle(getArguments()).getBusinessid();
        Spinner spinnerService = view.findViewById(R.id.spinner_aposelectservice);
        Spinner spinnerTime = view.findViewById(R.id.spinner_aposelecttime);
        TextView dateSelect= view.findViewById(R.id.newapo_dateselectTV);
        Button save=view.findViewById(R.id.apo_makeapoBT);
        ProgressBar progressBar=view.findViewById(R.id.progressBar_makeapo);
        progressBar.setVisibility(View.GONE);


        dateSelect.setOnClickListener(v -> {
            Calendar cal=Calendar.getInstance();
            yearNow=cal.get(Calendar.YEAR);
            monthNow=cal.get(Calendar.MONTH);
            dayNow=cal.get(Calendar.DAY_OF_MONTH);
            timeNow= cal.getTime().getHours();

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
        });

        String[] items2 = new String[]{"Select Time","8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
        spinnerTime.setAdapter(adapter2);

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
            }else if(time.equals("Select Time")){
                Toast.makeText(getActivity(), "please select a time ", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                save.setEnabled(true);
                return;
            }else if(timeNow>Character.getNumericValue(time.charAt(0))){
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
                    Listeners.instance.addAppointment(appointment, data1 -> {
                        Toast.makeText(getActivity(), "Appointment was scheduled", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).popBackStack();
                    });
                });
            }
        });



        return view;
    }

    public void dateError(){Toast.makeText(getActivity(), "please choose a correct date", Toast.LENGTH_LONG).show();}
}