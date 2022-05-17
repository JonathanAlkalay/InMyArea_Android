package com.example.inmyarea_android.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.feed.BaseActivity;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.Users.Client;

public class RegisterClientFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_register_client, container, false);

        EditText name_register = view.findViewById(R.id.name_registerET);
        EditText email_register = view.findViewById(R.id.email_registetET);
        EditText pass1 = view.findViewById(R.id.pw1_registetET);
        EditText pass2 = view.findViewById(R.id.pw2_registetET);
        EditText phone_register = view.findViewById(R.id.phone_registeET);
        Button regis_But= view.findViewById(R.id.register_RegisBT);
        ProgressBar progressBar= view.findViewById(R.id.progressBar_regclient);
        progressBar.setVisibility(View.GONE);

        regis_But.setOnClickListener(v -> {

            regis_But.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            String pas1=pass1.getText().toString().trim();
            String pas2=pass2.getText().toString().trim();
            if(!pas1.equals(pas2)){
                pass2.setError("passwords are not equal!");
                progressBar.setVisibility(View.GONE);
                regis_But.setEnabled(true);
            }
            else {

                String email = email_register.getText().toString().trim();
                String name = name_register.getText().toString().trim();
                String phone = phone_register.getText().toString().trim();

                Client client = new Client(email, pas1, name, phone);
                //server call
                Listeners.instance.createAccount(email, "user", client, data -> {
                    if (data.getStatus().equals("true")) {
                        progressBar.setVisibility(View.GONE);
                        toFeedActivity(email);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        email_register.setError(data.getMessage());
                        regis_But.setEnabled(true);

                    }
                });
            }
        });

        return view;
    }


    private void toFeedActivity(String email) {
        Intent intent = new Intent(getContext(), BaseActivity.class);
        Bundle b = new Bundle();

        b.putString("useremail_id", email); //Your id
        b.putString("type","user");
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        getActivity().finish();
    }
}