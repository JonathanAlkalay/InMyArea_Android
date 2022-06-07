package com.example.inmyarea_android.feed;



import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inmyarea_android.MainActivity;
import com.example.inmyarea_android.R;
import com.example.inmyarea_android.login.LoginFragmentDirections;
import com.example.inmyarea_android.model.Appointment;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.GetAccountResponseMessage;
import com.example.inmyarea_android.model.ResponseMessages.GetBusinessesRespMsg;
import com.example.inmyarea_android.model.Users.Business;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    String email,type;
    Double longitude,latitude;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    ProgressBar progressBar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment, container, false);
        email = getArguments().getString("useremail_id");
        type = getArguments().getString("type");
        mViewModel = ViewModelProviders.of(this.getActivity()).get(HomeViewModel.class);
        progressBar=view.findViewById(R.id.progressBar_homefeed);
        progressBar.setVisibility(View.VISIBLE);

        TextView greet = view.findViewById(R.id.home_TV);
        Listeners.instance.getAccountByEmail(email, "user", new Listeners.getAccountByEmailListener() {
            @Override
            public void onComplete(GetAccountResponseMessage data) throws IOException {
                greet.setText("Hello "+(String)data.getAccount().get("name")+"");
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // method to get the location
        getLastLocation();


        Button medi = view.findViewById(R.id.catagory1_BT);
        Button cos = view.findViewById(R.id.catagory3_BT);
        Button hair = view.findViewById(R.id.catagory4_BT);
        Button leisure = view.findViewById(R.id.catagory2_BT);
        //Button rec = view.findViewById(R.id.catagory5_BT);
        Button profile = view.findViewById(R.id.toProflie_BT);
        profile.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate((NavDirections) HomeFragmentDirections.actionHomeFragmentToProfileFragment(email,email,type));
        });

        medi.setOnClickListener(v -> {
            if(mViewModel.getBusinessByCategory("Pedi&Medi").isEmpty()){
                Toast.makeText(getActivity(), "There is no businesses with this category in your area", Toast.LENGTH_LONG).show();
            }else {
                Navigation.findNavController(view).navigate((NavDirections) HomeFragmentDirections.actionHomeFragmentToVideoClipRVFragment(email, "Pedi&Medi"));
            }
        });
        cos.setOnClickListener(v -> {
            if(mViewModel.getBusinessByCategory("Cosmetics").isEmpty()){
                Toast.makeText(getActivity(), "There is no businesses with this category in your area", Toast.LENGTH_LONG).show();
            }else {
                Navigation.findNavController(view).navigate((NavDirections) HomeFragmentDirections.actionHomeFragmentToVideoClipRVFragment(email, "Cosmetics"));
            }
        });
        hair.setOnClickListener(v -> {
            if (mViewModel.getBusinessByCategory("Hair Styling").isEmpty()) {
                Toast.makeText(getActivity(), "There is no businesses with this category in your area", Toast.LENGTH_LONG).show();
            } else {
                Navigation.findNavController(view).navigate((NavDirections) HomeFragmentDirections.actionHomeFragmentToVideoClipRVFragment(email, "Hair Styling"));
            }
        });
        leisure.setOnClickListener(v -> {
            if(mViewModel.getBusinessByCategory("Leisure").isEmpty()){
                Toast.makeText(getActivity(), "There is no businesses with this category in your area", Toast.LENGTH_LONG).show();
            }else {
                Navigation.findNavController(view).navigate((NavDirections) HomeFragmentDirections.actionHomeFragmentToVideoClipRVFragment(email, "Leisure"));
            }
        });


        return view;

    }


    private void getBusByLocation(){
        Listeners.instance.getAccountsByLocation(longitude, latitude, data -> {
            List<Business> busList=new ArrayList<>();
            for (HashMap<String,Object> map:data.getAccounts()){
                Business business=new Business();
                busList.add(business.fromJson(map));
            }
            mViewModel.setBusinesses(busList);
            progressBar.setVisibility(View.GONE);
        });
    }



    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            getBusByLocation();
                        }
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude=mLastLocation.getLatitude();
            longitude=mLastLocation.getLongitude();
            getBusByLocation();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


}