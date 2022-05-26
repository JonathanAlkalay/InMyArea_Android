package com.example.inmyarea_android.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Appointment;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CalendarFragment extends Fragment {

    ArrayList<Appointment> apoarr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView=view.findViewById(R.id.calendarView_calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });



        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,service,phone,time,date;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            name =itemView.findViewById(R.id.aporow_name);
            service =itemView.findViewById(R.id.aporow_service);
            phone =itemView.findViewById(R.id.aporow_phone);
            time =itemView.findViewById(R.id.aporow_time);
            date =itemView.findViewById(R.id.aporow_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });
        }



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

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}