package com.example.inmyarea_android.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Users.Client;

import java.util.HashMap;


public class EditClientFragment extends Fragment {

    Client client;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_edit_client, container, false);
        String emailId= EditClientFragmentArgs.fromBundle(getArguments()).getEmail();
        EditText name= view.findViewById(R.id.editClient_nameET);
        EditText phone= view.findViewById(R.id.editClient_phoneET);
        EditText pass1= view.findViewById(R.id.editClient_pass1ET);
        EditText pass2= view.findViewById(R.id.editClient_pass2ET);
        EditText curpass= view.findViewById(R.id.editClient_curpassET);
        Button save = view.findViewById(R.id.editClient_saveBT);
        ProgressBar progressBar = view.findViewById(R.id.progressBar_editClient);
        progressBar.setVisibility(View.VISIBLE);

        Listeners.instance.getAccountByEmail(emailId, "user", data -> {
            HashMap user=data.getAccount();
            client= new Client((String)user.get("email"),(String)user.get("passWord"),(String)user.get("name"),(String)user.get("phoneNumber"));
            name.setText(client.getName());
            phone.setText(client.getPhoneNumber());
            progressBar.setVisibility(View.GONE);
        });

        save.setOnClickListener(v -> {
            save.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            String cname = name.getText().toString().trim();
            String cphone = phone.getText().toString().trim();
            String ccurpass=curpass.getText().toString().trim();
            String cpass1=pass1.getText().toString().trim();
            String cpass2=pass2.getText().toString().trim();

            if(ccurpass.isEmpty()){
                if(cname.isEmpty()||cphone.isEmpty()){
                    name.setError("Please fill out name and phone");
                    save.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }else{
                    client.setName(cname);
                    client.setPhoneNumber(cphone);
                    Listeners.instance.updateAccountDetails(emailId, "user", client, data -> {
                        save.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        Navigation.findNavController(view).popBackStack();
                    });
                }
            }else{
                if(cname.isEmpty()||cphone.isEmpty()){
                    name.setError("Please fill out name and phone");
                    save.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }else if(!ccurpass.equals(client.getPassWord())){
                    curpass.setError("Wrong password");
                    save.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }else{
                    if(!cpass1.equals(cpass2)){
                        pass2.setError("passwords are not equal!");
                        save.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                    }else{
                        client.setName(cname);
                        client.setPhoneNumber(cphone);
                        client.setPassWord(cpass1);
                        Listeners.instance.updateAccountDetails(emailId, "user", client, data -> {
                            save.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            Navigation.findNavController(view).popBackStack();
                        });
                    }
                }
            }

        });



        return view;
    }
}