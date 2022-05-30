package com.example.inmyarea_android.feed;

import android.os.Bundle;

import com.example.inmyarea_android.databinding.ActivityBaseBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.inmyarea_android.R;

public class BaseActivity extends AppCompatActivity {
    NavController navCtl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String value =null; // or other values
        String type =null;
        if(b != null) {
            value = b.getString("useremail_id");
            type = b.getString("type");
            b.putString("profile_email_id","null");
        }
        String finalValue = value;

        setContentView(R.layout.activity_base);
        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.feed_navhost);
        navCtl = navHost.getNavController();


        NavGraph navGraph = navCtl.getNavInflater().inflate(R.navigation.feednav_graph);
        if(type.equals("business")){
            navGraph.setStartDestination(R.id.profileFragment);
        }else navGraph.setStartDestination(R.id.homeFragment);

        navCtl.setGraph(navGraph, b);




    }


}