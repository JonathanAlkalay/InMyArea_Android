package com.example.inmyarea_android.feed;

import android.os.Bundle;

import com.example.inmyarea_android.databinding.ActivityBaseBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
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
        if(b != null)
            value = b.getString("useremail_id");
        String finalValue = value;

        setContentView(R.layout.activity_base);
        NavHost navHost = (NavHost)getSupportFragmentManager().findFragmentById(R.id.feed_navhost);
        navCtl = navHost.getNavController();
        navCtl.setGraph(R.navigation.feednav_graph, b);


    }


}