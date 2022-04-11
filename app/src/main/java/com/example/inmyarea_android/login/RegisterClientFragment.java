package com.example.inmyarea_android.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.inmyarea_android.R;

public class RegisterClientFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_register_client, container, false);

        EditText name_register = view.findViewById(R.id.name_registetET);
        EditText email_register = view.findViewById(R.id.email_registetET);
        EditText pass1 = view.findViewById(R.id.pw1_registetET);
        EditText pass2 = view.findViewById(R.id.pw2_registetET);
        EditText phone_register = view.findViewById(R.id.phone_registetET);
        Button regis_But= view.findViewById(R.id.register_RegisBut);

        regis_But.setOnClickListener(v -> {

            String pas1=pass1.getText().toString().trim();
            String pas2=pass2.getText().toString().trim();
            if(!pas1.equals(pas2)){
                pass2.setError("passwords are not equal!");
            }

            String email=email_register.getText().toString().trim();
            String name=name_register.getText().toString().trim();
            String phone=phone_register.getText().toString().trim();


            //run server function to save Client
        });

        return view;
    }


    private void toFeedActivity() {
        //Intent intent = new Intent(getContext(), BaseActivity.class);
        // startActivity(intent);
        // getActivity().finish();
    }
}