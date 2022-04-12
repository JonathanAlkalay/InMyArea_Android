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
import android.widget.TextView;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Listeners;

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
        TextView register = view.findViewById(R.id.register_LoginTV);
        register.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterClientFragment());
        });
        login_But.setOnClickListener(v -> {
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

            //we dont know the type in log in
            Listeners.instance.logIn(email, password, "user", data -> {

                String s = data.getStatus();
                String m = data.getMessage();
                ///check if status is good and than pass to feed if not display massage
                progressBar.setVisibility(View.GONE);
                login_But.setEnabled(true);
                password_Login.setError(m);

            });
        });


        return view;
    }


    private void toFeedActivity() {
        //Intent intent = new Intent(getContext(), BaseActivity.class);
       // startActivity(intent);
       // getActivity().finish();
    }
}