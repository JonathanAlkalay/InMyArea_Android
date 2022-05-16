package com.example.inmyarea_android.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.feed.BaseActivity;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Service;
import com.example.inmyarea_android.model.Users.Business;
import com.example.inmyarea_android.model.Users.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class RegisteBusinessFragment2 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_registe_business2, container, false);
        String category=RegisteBusinessFragment2Args.fromBundle(getArguments()).getCategory();
        String email=RegisteBusinessFragment2Args.fromBundle(getArguments()).getEmail();
        ProgressBar progressBar=view.findViewById(R.id.progressBarregnus2_PB);
        progressBar.setVisibility(View.GONE);
        TextView services=view.findViewById(R.id.services_dropdownTv);
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Integer> servList = new ArrayList<>();
        String[] servArray={};
        Service service=new Service();
        switch (category){
            case "Hair Styling":
                servArray=service.HairStyling;
                break;
            case "Leisure":
                servArray=service.Leisure;
                break;

            case "Pedi&Medi":
                servArray=service.PediMeni;
                break;

            case "Cosmetics":
                servArray=service.Cosmetics;
                break;
        }
        boolean[] selectedServices = new boolean[servArray.length];


        String[] finalServArray = servArray;
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Services");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(finalServArray, selectedServices, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            servList.add(i);
                            // Sort array list
                            Collections.sort(servList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            servList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder

                        // use for loop
                        for (int j = 0; j < servList.size(); j++) {
                            // concat array value
                            stringBuilder.append(finalServArray[servList.get(j)]);
                            // check condition
                            if (j != servList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        services.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedServices.length; j++) {
                            // remove all selection
                            selectedServices[j] = false;
                            // clear language list
                            servList.clear();
                            // clear text view value
                            services.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
        Button register= view.findViewById(R.id.register_regbusinessBT);
        register.setOnClickListener(v -> {
            Listeners.instance.getAccountByEmail(email, "business", data -> {
                HashMap user=data.getAccount();
                Business busi = new Business(email,(String) user.get("passWord"),(String) user.get("name"),
                        (String) user.get("phoneNumber"), (String) user.get("description"),(String) user.get("category"));

              // busi.setServices(stringBuilder.toString().split(", "));
               Listeners.instance.updateAccountDetails(email, "business", busi, data1 -> {
                    services.setError(data1.getMessage());
                });
            });
            //toFeedActivity();
        });

        return view;
    }



    //send email
    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), BaseActivity.class);
        //Bundle b = new Bundle();

        //b.putString("useremail_id", email); //Your id
        //intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        getActivity().finish();
    }
}