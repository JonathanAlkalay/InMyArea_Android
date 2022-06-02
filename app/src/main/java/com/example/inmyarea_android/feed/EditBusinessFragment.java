package com.example.inmyarea_android.feed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Service;
import com.example.inmyarea_android.model.Users.Business;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class EditBusinessFragment extends Fragment {


    Business business=new Business();
    String category;
    String[] servArray={};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_businees, container, false);
        String busId=EditBusinessFragmentArgs.fromBundle(getArguments()).getBusinessid();
        EditText name=view.findViewById(R.id.name_editbusET);
        EditText des=view.findViewById(R.id.description_editbusET);
        EditText phone=view.findViewById(R.id.phone_editbusET);
        EditText pass1=view.findViewById(R.id.pass1_editbusET);
        EditText pass2=view.findViewById(R.id.pass2_editbusET);
        ProgressBar progressBar=view.findViewById(R.id.progressBar_editbus);
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<Integer> servList = new ArrayList<>();
        TextView services=view.findViewById(R.id.services_dropdown_editbusTv);
        StringBuilder stringBuilder = new StringBuilder();



        Listeners.instance.getAccountByEmail(busId, "business", data -> {
            name.setText(data.getAccount().get("name").toString().trim());
            des.setText(data.getAccount().get("description").toString().trim());
            phone.setText(data.getAccount().get("phoneNumber").toString().trim());
            business=business.fromJson(data.getAccount());
            category=business.getCategory();
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
            progressBar.setVisibility(View.GONE);
        });

        Button save =view.findViewById(R.id.save_editbusBt);
        save.setOnClickListener(v -> {
            save.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            String sname=name.getText().toString().trim();
            String sdes=des.getText().toString().trim();
            String sphone=phone.getText().toString().trim();
            String spass1=pass1.getText().toString().trim();
            String spass2=pass2.getText().toString().trim();
            if(sname.isEmpty()) {
                name.setError("Please provide a business name!");
                save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                return;
            }
            if(sdes.isEmpty()){
                des.setError("Please provide a business name!");
                save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                return;
            }
            if(sphone.isEmpty()){
                phone.setError("Please provide a business name!");
                save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                return;
            }
            if(stringBuilder.length()==0) {
                Toast.makeText(getActivity(), "please select services ", Toast.LENGTH_LONG).show();
                save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                return;
            }
            if(!spass1.isEmpty()){
                if(!spass1.equals(spass2)){
                    pass2.setError("Passwords are not equal");
                    save.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                business.setName(sname);
                business.setDescription(sdes);
                business.setPhoneNumber(sphone);
                business.setPassWord(spass1);
                ArrayList<String> serv = new ArrayList<String>(Arrays.asList(stringBuilder.toString().split(", ")));
                business.setServices(serv);
            }else{
                business.setName(sname);
                business.setDescription(sdes);
                business.setPhoneNumber(sphone);
                ArrayList<String> serv = new ArrayList<String>(Arrays.asList(stringBuilder.toString().split(", ")));
                business.setServices(serv);
            }
            Listeners.instance.updateAccountDetails(busId, "business", business, data -> {
                save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Navigation.findNavController(view).popBackStack();
            });
        });



        return view;
    }
}