package com.example.inmyarea_android.login;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Users.Business;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegisterBusinessFragment extends Fragment {

    EditText location;
    Place place;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_register_business, container, false);
        EditText name = view.findViewById(R.id.name_registetbusinessET3);
        EditText des = view.findViewById(R.id.description_registerbusinessET);
        EditText pass1 = view.findViewById(R.id.pw1_registebusinesstET);
        EditText pass2 = view.findViewById(R.id.pw2_registetbusinessET);
        EditText email = view.findViewById(R.id.email_registetbusinessET);
        EditText phone = view.findViewById(R.id.phone_registebusinessET);
        Button cont= view.findViewById(R.id.continue_RegisButbusiness);
        ProgressBar progressBar= view.findViewById(R.id.progressBar_regbus);
        progressBar.setVisibility(View.GONE);
        Places.initialize(getContext(), "AIzaSyCQ3Q8914NpWb5OpAteUHE0htG8rerUSoQ");
        //PlacesClient placesClient = Places.createClient(getContext());
        location=view.findViewById(R.id.location_registetbusinessET);

        location.setFocusable(false);
        location.setOnClickListener(v -> {
            List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
            Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(getContext());
            startActivityForResult(intent,100);
        });




        Spinner spinner = view.findViewById(R.id.spinner_category);
        String[] items = new String[]{"Choose Category","Leisure", "Pedi&Medi", "Hair Styling","Cosmetics"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        cont.setOnClickListener(v -> {
            cont.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            String cname=name.getText().toString().trim();
            String cdes=des.getText().toString().trim();
            String cpass1=pass1.getText().toString().trim();
            String cpass2=pass2.getText().toString().trim();
            String cemail=email.getText().toString().trim();
            String cphone=phone.getText().toString().trim();
            String ccategory=spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
            if(cname.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                name.setError("Please write the business name");

            } else if(cdes.isEmpty())
            {
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                des.setError("Please write the business description");

            } else if(cpass1.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                pass1.setError("Please write a password");
            } else if(cpass2.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                pass2.setError("Please confirm the password");
            } else if(!cpass1.equals(cpass2)) {
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                pass2.setError("Passwords not equal");
            }else if(cemail.isEmpty()) {
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                email.setError("Please write your email address");
            }else if(cphone.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                phone.setError("Please write a phone number ");
            } else if(ccategory.equals("Choose Category")){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                name.setError("Please choose Category");
            }
            else{
                Business buss=new Business( cemail, cpass1, cname, cphone,  cdes,  ccategory);
                Listeners.instance.createAccount(cemail, "business", buss, data -> {
                    if(data.getStatus().equals("true")){
                        progressBar.setVisibility(View.GONE);
                        Navigation.findNavController(view).navigate(RegisterBusinessFragmentDirections.actionRegisterBusinessFragmentToRegisteBusinessFragment2(ccategory,cemail));

                    }else{
                        progressBar.setVisibility(View.GONE);
                        email.setError(data.getMessage());
                        cont.setEnabled(true);
                    }
                });

            }

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode== Activity.RESULT_OK){
            place=Autocomplete.getPlaceFromIntent(data);
            location.setText(place.getAddress());
        }
    }
}