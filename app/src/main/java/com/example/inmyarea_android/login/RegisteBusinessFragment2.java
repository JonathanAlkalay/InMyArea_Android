package com.example.inmyarea_android.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.feed.BaseActivity;


public class RegisteBusinessFragment2 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_registe_business2, container, false);

        Button register= view.findViewById(R.id.register_regbusinessBT);
        register.setOnClickListener(v -> {
            toFeedActivity();
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