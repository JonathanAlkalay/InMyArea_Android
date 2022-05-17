package com.example.inmyarea_android.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.feed.BaseActivity;
import com.example.inmyarea_android.model.Listeners;

import java.util.concurrent.atomic.AtomicInteger;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_login, container, false);

        EditText email_Login= view.findViewById(R.id.email_input_login);
        EditText password_Login= view.findViewById(R.id.password_input_login);
        Button login_But = view.findViewById(R.id.login_button);
        ProgressBar progressBar= view.findViewById(R.id.progressBar_Login);
        progressBar.setVisibility(View.GONE);
        TextView registerClient = view.findViewById(R.id.register_LoginclientTV);
        TextView registerBusiness = view.findViewById(R.id.register_LoginbusinessTV);
        Switch aSwitch = view.findViewById(R.id.switch_login);
        AtomicInteger type= new AtomicInteger(1);

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                type.set(2);
            }else {
                type.set(1);
            }

        });

        registerClient.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterClientFragment());
        });
        registerBusiness.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterBusinessFragment());
        });
        login_But.setOnClickListener(v -> {
            login_But.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            String email=email_Login.getText().toString().trim();
            String password=password_Login.getText().toString().trim();
            if(email.isEmpty()){
                progressBar.setVisibility(View.GONE);
                email_Login.setError("You must enter email!");
                email_Login.requestFocus();
                login_But.setEnabled(true);
                return;
            }

            if(password.isEmpty()){
                progressBar.setVisibility(View.GONE);
                password_Login.setError("You must enter password!");
                password_Login.requestFocus();
                login_But.setEnabled(true);
                return;
            }

            if(type.get()==1) {
                Listeners.instance.logIn(email, password, "user", data -> {

                    String s = data.getStatus();
                    String m = data.getMessage();
                    ///check if status is good and than pass to feed if not display massage
                    if(s.equals("false"))
                    {
                        progressBar.setVisibility(View.GONE);
                        email_Login.setError(m);
                        email_Login.requestFocus();
                        login_But.setEnabled(true);
                    }else {
                        progressBar.setVisibility(View.GONE);
                        login_But.setEnabled(true);
                        toFeedActivity(email, "user");
                    }
                });
            }else {
                Listeners.instance.logIn(email, password, "business", data -> {

                    String s = data.getStatus();
                    String m = data.getMessage();
                    ///check if status is good and than pass to feed if not display massage
                    if(s.equals("false"))
                    {
                        progressBar.setVisibility(View.GONE);
                        email_Login.setError(m);
                        email_Login.requestFocus();
                        login_But.setEnabled(true);
                    }else {
                        progressBar.setVisibility(View.GONE);
                        login_But.setEnabled(true);
                        toFeedActivity(email, "business");
                    }
                });
            }

        });


        return view;
    }


    private void toFeedActivity(String email,String type) {
        Intent intent = new Intent(getContext(), BaseActivity.class);
        Bundle b = new Bundle();

        b.putString("useremail_id", email); //Your id
        b.putString("type",type);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        getActivity().finish();
    }
}