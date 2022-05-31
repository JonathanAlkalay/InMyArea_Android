package com.example.inmyarea_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Appointment;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.GetAppointmentsRespMsg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {

    MyAdapter adapter;
    boolean serOrApo;
    List<Appointment> apoArr=new ArrayList<>();
    List<String> serviceArr=new ArrayList<>();
    String emailUseridId,type,profileEmailId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        emailUseridId = getArguments().getString("useremail_id");
        type = getArguments().getString("type");
        if(!getArguments().get("profile_email_id").equals("null")) {
            profileEmailId = ProfileFragmentArgs.fromBundle(getArguments()).getProfileEmailId();
        }else profileEmailId=emailUseridId;

        TextView userName= view.findViewById(R.id.profile_nameTV);
        TextView desc= view.findViewById(R.id.profile_descTV);
        TextView calendar= view.findViewById(R.id.calendar_clickTV);
        TextView serOrApoTV= view.findViewById(R.id.profile_serorhisTV);
        TextView phone=view.findViewById(R.id.profilePhone_TV);
        Button editBt= view.findViewById(R.id.profile_editBT);
        Button apoBt= view.findViewById(R.id.profile_apointmentBT);
        RecyclerView list=view.findViewById(R.id.serorapo_RV);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new ProfileFragment.MyAdapter();
        list.setAdapter(adapter);

        calendar.setVisibility(View.GONE);

        if(type.equals("business")) {
            apoBt.setVisibility(View.GONE);
            if (emailUseridId.equals(profileEmailId)){
                serOrApo=false;
                editBt.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
                calendar.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToCalendarFragment(profileEmailId));
                });
            }
            serOrApoTV.setText("Services");
            Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                userName.setText((String)data.getAccount().get("name"));
                desc.setText((String)data.getAccount().get("description"));
                phone.setText((String)data.getAccount().get("phoneNumber"));
                serviceArr=(List<String>) data.getAccount().get("services");
                refresh();
            });


        }else{
            if (emailUseridId.equals(profileEmailId)) {
                serOrApo=true;
                apoBt.setVisibility(View.GONE);
                editBt.setVisibility(View.VISIBLE);
                serOrApoTV.setText("Appointments");
                Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                    userName.setText((String)data.getAccount().get("name"));
                    desc.setText((String)data.getAccount().get("email"));
                    phone.setText((String)data.getAccount().get("phoneNumber"));
                });
                Listeners.instance.AppointmentsByUser(profileEmailId, data -> {
                    for (HashMap<String,Object> map:data.getAppointments()){
                        Appointment appointment=new Appointment();
                        apoArr.add(appointment.fromJson(map));
                    }
                    refresh();
                });

            }else{
                serOrApo=false;
                serOrApoTV.setText("Services");
                apoBt.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToMakeAppointmentFragment(profileEmailId,emailUseridId));
                });
                Listeners.instance.getAccountByEmail(profileEmailId, "business", data -> {
                    userName.setText((String)data.getAccount().get("name"));
                    desc.setText((String)data.getAccount().get("email"));
                    phone.setText((String)data.getAccount().get("phoneNumber"));
                    serviceArr=(List<String>) data.getAccount().get("services");
                    refresh();
                });
            }


        }


        editBt.setOnClickListener(v -> {
            if(type.equals("user"))
            Navigation.findNavController(view).navigate((NavDirections) ProfileFragmentDirections.actionProfileFragmentToEditClientFragment(profileEmailId));
            else Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToEditBusineesFragment(profileEmailId));
        });




        return view;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,service,phone,time,date,service_Bus;


        public MyViewHolder(@NonNull View itemView, ProfileFragment.OnItemClickListener listener) {
            super(itemView);
            if(serOrApo) {
                name = itemView.findViewById(R.id.aporow_name);
                service = itemView.findViewById(R.id.aporow_service);
                phone = itemView.findViewById(R.id.aporow_phone);
                time = itemView.findViewById(R.id.aporow_time);
                date = itemView.findViewById(R.id.aporow_date);
            }else {
                service_Bus=itemView.findViewById(R.id.name_servicerowTV);
            }


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    listener.onItemClick(pos);
//                }
//            });
        }



    }

    private void refresh(){
        adapter.notifyDataSetChanged();

    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<ProfileFragment.MyViewHolder> {

        ProfileFragment.OnItemClickListener listener;

//        public void setOnItemClickListener(ProfileFragment.OnItemClickListener listener) {
//            this.listener = listener;
//        }

        @NonNull
        @Override
        public ProfileFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (serOrApo) {
                view = getLayoutInflater().inflate(R.layout.apointmet_row, parent, false);
            } else view = getLayoutInflater().inflate(R.layout.service_row, parent, false);

            ProfileFragment.MyViewHolder holder = new ProfileFragment.MyViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileFragment.MyViewHolder holder, int position) {
            //set  data
            if (serOrApo) {
                Appointment apo = apoArr.get(position);
                holder.name.setText(apo.getUserName());
                holder.date.setText(apo.getDate());
                holder.time.setText(apo.getTime());
                holder.service.setText(apo.getService());
                holder.phone.setText(apo.getPhone());
            } else {
                holder.service_Bus.setText(serviceArr.get(position));
            }
        }


        //return size
        @Override
        public int getItemCount() {
            if (serOrApo) {
                if (apoArr != null)
                    return apoArr.size();
            } else if (serviceArr != null) {
                return serviceArr.size();
            }
            return 0;
        }
    }
}