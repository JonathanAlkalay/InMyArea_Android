package com.example.inmyarea_android.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inmyarea_android.MainActivity;
import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Appointment;
import com.example.inmyarea_android.model.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {

    MyAdapter adapter;
    boolean serOrApo;
    List<Appointment> apoArr=new ArrayList<>();
    List<String> serviceArr=new ArrayList<>();
    String emailUseridId,type,profileEmailId;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        emailUseridId = getArguments().getString("useremail_id");
        type = getArguments().getString("type");
        if(!getArguments().get("profile_email_id").equals("null")) {
            profileEmailId = ProfileFragmentArgs.fromBundle(getArguments()).getProfileEmailId();
        }else profileEmailId=emailUseridId;

        TextView userName= view.findViewById(R.id.profile_nameTV);
        TextView desc= view.findViewById(R.id.profile_descTV);
        ImageView calendar= view.findViewById(R.id.calendar_profileIV);
        TextView serOrApoTV= view.findViewById(R.id.profile_serorhisTV);
        TextView phone=view.findViewById(R.id.profilePhone_TV);
        TextView address=view.findViewById(R.id.profileaddress_TV);
        Button editBt= view.findViewById(R.id.profile_editBT);
        Button apoBt= view.findViewById(R.id.profile_apointmentBT);
        RecyclerView list=view.findViewById(R.id.serorapo_RV);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new ProfileFragment.MyAdapter();
        list.setAdapter(adapter);
        editBt.setVisibility(View.GONE);


        Button preview=view.findViewById(R.id.profile_previewvideoBT);
        preview.setVisibility(View.GONE);
        Button logout=view.findViewById(R.id.logout_BT);
        logout.setVisibility(View.GONE);
        logout.setOnClickListener(v -> {
            //logout

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getActivity().finish();
            startActivity(intent);
        });
        preview.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToBusinessVideoPreviewFragment(profileEmailId));
        });

        calendar.setVisibility(View.GONE);

        if(type.equals("business")) {
            apoBt.setVisibility(View.GONE);
            if (emailUseridId.equals(profileEmailId)){
                logout.setVisibility(View.VISIBLE);
                preview.setVisibility(View.VISIBLE);
                serOrApo=false;
                editBt.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
                calendar.setOnClickListener(v -> {
                    apoArr.clear();
                    Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToCalendarFragment(profileEmailId));
                });
            }
            serOrApoTV.setText("Services");
            Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                userName.setText((String)data.getAccount().get("name"));
                desc.setText((String)data.getAccount().get("description"));
                phone.setText((String)data.getAccount().get("phoneNumber"));
                address.setText((String)data.getAccount().get("location"));
                serviceArr=(List<String>) data.getAccount().get("services");
                refresh();
            });


        }else{
            if (emailUseridId.equals(profileEmailId)) {
                logout.setVisibility(View.VISIBLE);
                serOrApo=true;
                apoBt.setVisibility(View.GONE);
                editBt.setVisibility(View.VISIBLE);
                serOrApoTV.setText("Appointments");
                address.setVisibility(View.GONE);
                Listeners.instance.getAccountByEmail(profileEmailId, type, data -> {
                    userName.setText((String)data.getAccount().get("name"));
                    desc.setText((String)data.getAccount().get("email"));
                    phone.setText((String)data.getAccount().get("phoneNumber"));
                });
                Listeners.instance.AppointmentsByUser(profileEmailId, data -> {
                    if(data!=null)
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
                    apoArr.clear();
                    Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToMakeAppointmentFragment(profileEmailId,emailUseridId));
                });
                Listeners.instance.getAccountByEmail(profileEmailId, "business", data -> {
                    userName.setText((String)data.getAccount().get("name"));
                    desc.setText((String)data.getAccount().get("description"));
                    phone.setText((String)data.getAccount().get("phoneNumber"));
                    address.setText((String)data.getAccount().get("location"));
                    serviceArr=(List<String>) data.getAccount().get("services");
                    refresh();
                });
            }


        }


        editBt.setOnClickListener(v -> {
            if(type.equals("user")) {
                apoArr.clear();
                Navigation.findNavController(view).navigate((NavDirections) ProfileFragmentDirections.actionProfileFragmentToEditClientFragment(profileEmailId));
            } else {
                apoArr.clear();
                Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToEditBusineesFragment(profileEmailId));
            }
        });




        return view;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,service,phone,time,date,service_Bus;
        ImageButton edit,delete;


        public MyViewHolder(@NonNull View itemView, ProfileFragment.OnItemClickListener listener) {
            super(itemView);
            if(serOrApo) {
                name = itemView.findViewById(R.id.aporow_name);
                service = itemView.findViewById(R.id.aporow_service);
                phone = itemView.findViewById(R.id.aporow_phone);
                time = itemView.findViewById(R.id.aporow_time);
                date = itemView.findViewById(R.id.aporow_date);
                edit=itemView.findViewById(R.id.editApp_rowIB);
                delete=itemView.findViewById(R.id.cancelAppRow_IB);

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
                Listeners.instance.getAccountByEmail(apo.getBusinessId(), "business", data ->{
                    holder.name.setText((String)data.getAccount().get("name"));
                    holder.phone.setText((String)data.getAccount().get("phoneNumber"));
                } );
                holder.date.setText(apo.getDate());
                holder.time.setText(apo.getTime());
                holder.service.setText(apo.getService());
                holder.edit.setOnClickListener(v -> {
                    apoArr.clear();
                    Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToEditAppointmentFragment(apo.getUserId(),apo.getBusinessId(),apo.getTime(),apo.getDate(),apo.getService()));
                });
                HashMap<String,Object> map =new HashMap<>();
                holder.delete.setOnClickListener(v -> {
                    Listeners.instance.updateAppointment(apo.getUserId(), apo.getBusinessId(), apo.getDate(), apo.getTime(), null, data -> {
                        apoArr.remove(position);
                        notifyItemRemoved(position);
                    });
                });

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