package com.example.inmyarea_android.login;

import android.os.Bundle;

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


public class RegisterBusinessFragment extends Fragment {


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
        cont.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(RegisterBusinessFragmentDirections.actionRegisterBusinessFragmentToRegisteBusinessFragment2());
        });

        Spinner spinner = view.findViewById(R.id.spinner_category);
        String[] items = new String[]{"Choose Category","Leisure", "Pedi&Medi", "Hair Styling","Cosmetics"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);




        return view;
    }
}