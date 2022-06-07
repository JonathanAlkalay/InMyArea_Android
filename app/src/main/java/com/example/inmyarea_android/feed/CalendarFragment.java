package com.example.inmyarea_android.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.databinding.ApointmetRowBinding;
import com.example.inmyarea_android.model.Appointment;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAppointmentsRespMsg;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class CalendarFragment extends Fragment {

    List<Appointment> apoArr=new ArrayList<>();
    MyAdapter adapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_calendar, container, false);

        String busId=CalendarFragmentArgs.fromBundle(getArguments()).getBusinessid();
        ProgressBar calPB=view.findViewById(R.id.progressBar_calendar);
        CalendarView calendarView=view.findViewById(R.id.calendarView_calendar);
        calendarView.setClickable(false);
//        Button editS=view.findViewById(R.id.calendar_editscheduleBT);
////        editS.setOnClickListener(v -> {
//            Navigation.findNavController(view).navigate(CalendarFragmentDirections.actionCalendarFragmentToBusinessScheduleFragment(busId));
//        });


        calPB.setVisibility(View.VISIBLE);
        //get apo
        Date date=new Date();
        String sDate=(date.getMonth()+1)+"/"+date.getDate()+"/"+(date.getYear()+1900);
        Listeners.instance.AppointmentsByDate(busId, sDate, data -> {
            for (HashMap<String,Object> map:data.getAppointments()){
                Appointment appointment=new Appointment();
                apoArr.add(appointment.fromJson(map));
            }
            refresh();
            calPB.setVisibility(View.GONE);
            calendarView.setClickable(true);
        });

        RecyclerView list=view.findViewById(R.id.apo_listRV);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new MyAdapter();
        list.setAdapter(adapter);

        //use this while click
        //calendarView.setClickable(false);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calPB.setVisibility(View.VISIBLE);
                String sDate=(month+1)+"/"+dayOfMonth+"/"+year;
                apoArr.clear();
                Listeners.instance.AppointmentsByDate(busId, sDate, data -> {
                    for (HashMap<String,Object> map:data.getAppointments()){
                        Appointment appointment=new Appointment();
                        apoArr.add(appointment.fromJson(map));
                    }
                    refresh();
                    calPB.setVisibility(View.GONE);
                    calendarView.setClickable(true);
                });

            }
        });
        return view;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,service,phone,time,date;
        ImageButton edit,delete;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            name =itemView.findViewById(R.id.aporow_name);
            service =itemView.findViewById(R.id.aporow_service);
            phone =itemView.findViewById(R.id.aporow_phone);
            time =itemView.findViewById(R.id.aporow_time);
            date =itemView.findViewById(R.id.aporow_date);
            edit=itemView.findViewById(R.id.editApp_rowIB);
            delete=itemView.findViewById(R.id.cancelAppRow_IB);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }



    }

    private void refresh(){
        adapter.notifyDataSetChanged();

    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.apointmet_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            //set  data
            Appointment apo=apoArr.get(position);
            holder.name.setText(apo.getUserName());
            holder.date.setText(apo.getDate());
            holder.time.setText(apo.getTime());
            holder.service.setText(apo.getService());
            holder.phone.setText(apo.getPhone());

            holder.edit.setOnClickListener(v -> {
                apoArr.clear();
                Navigation.findNavController(view).navigate(CalendarFragmentDirections.actionCalendarFragmentToEditAppointmentFragment(apo.getUserId(),apo.getBusinessId(),apo.getTime(),apo.getDate(),apo.getService()));
            });
            holder.delete.setOnClickListener(v -> {
                Listeners.instance.updateAppointment(apo.getUserId(), apo.getBusinessId(), apo.getDate(), apo.getTime(), null, data -> {
                    apoArr.remove(position);
                    notifyItemRemoved(position);
                });
            });
        }

        //return size
        @Override
        public int getItemCount() {
            if(apoArr!=null)
                return apoArr.size();
            return 0;
        }
    }
}