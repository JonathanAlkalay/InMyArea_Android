package com.example.inmyarea_android.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.ULocale;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.inmyarea_android.R;
import com.example.inmyarea_android.model.Listeners;
import com.example.inmyarea_android.model.ResponseMessages.MainResponseMessage;
import com.example.inmyarea_android.model.Users.Business;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class RegisterBusinessFragment extends Fragment {

    EditText locationET;
    Double longi,lati;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    Geocoder geocoder;
    List<Address> addresses=new ArrayList<>();
    ProgressBar progressBar;
    Button cont;




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
        cont= view.findViewById(R.id.continue_RegisButbusiness);
        progressBar= view.findViewById(R.id.progressBar_regbus);
        progressBar.setVisibility(View.GONE);

        locationET=view.findViewById(R.id.location_registetbusinessET);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLastLocation();



        Spinner spinner = view.findViewById(R.id.spinner_category);
        String[] items = new String[]{"Choose Category","Leisure", "Pedi&Medi", "Hair Styling","Cosmetics"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        cont.setOnClickListener(v -> {
            cont.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            String cname=name.getText().toString().trim();
            String cdes=des.getText().toString().trim();
            String cpass1=pass1.getText().toString().trim();
            String cpass2=pass2.getText().toString().trim();
            String cemail=email.getText().toString().trim();
            String cphone=phone.getText().toString().trim();
            String ccategory=spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
            String clocation=locationET.getText().toString().trim();
            if(cname.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                name.setError("Please write the business name");

            } else if(cdes.isEmpty())
            {
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                des.setError("Please write the business description");

            } else if(cpass1.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                pass1.setError("Please write a password");
            } else if(cpass2.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                pass2.setError("Please confirm the password");
            } else if(!cpass1.equals(cpass2)) {
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                pass2.setError("Passwords not equal");
            }else if(cemail.isEmpty()) {
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                email.setError("Please write your email address");
            }else if(cphone.isEmpty()){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                phone.setError("Please write a phone number ");
            } else if(ccategory.equals("Choose Category")){
                cont.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                name.setError("Please choose Category");
            } else if(clocation.isEmpty()){
            cont.setEnabled(true);
            progressBar.setVisibility(View.GONE);
                locationET.setError("Please put in the business city location");
            } else{
                Business buss=new Business( cemail, cpass1, cname, cphone,  cdes,  ccategory);
                buss.setLocation(clocation);
                buss.setLatitude(lati);
                buss.setLongitude(longi);
                Listeners.instance.createAccount(cemail, "business", buss, data -> {
                    if(data.getStatus().equals("true")){
                        progressBar.setVisibility(View.GONE);
                        Navigation.findNavController(view).navigate((NavDirections) RegisterBusinessFragmentDirections.actionRegisterBusinessFragmentToRegisteBusinessFragment2(ccategory,cemail));

                    }else{
                        progressBar.setVisibility(View.GONE);
                        email.setError(data.getMessage());
                        cont.setEnabled(true);
                    }
                });

            }

        });

        return view;
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        cont.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
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
                            lati=location.getLatitude();
                            //longitTextView.setText(location.getLongitude() + "");
                            longi=location.getLongitude();
                            geocoder = new Geocoder(getActivity(), Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(lati, longi, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            locationET.setText(addresses.get(0).getAddressLine(0));
                            cont.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
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
            lati=mLastLocation.getLatitude();
            //longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
            longi=mLastLocation.getLongitude();
            geocoder = new Geocoder(getActivity(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(lati, longi, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            locationET.setText(addresses.get(0).getAddressLine(0));
            cont.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
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