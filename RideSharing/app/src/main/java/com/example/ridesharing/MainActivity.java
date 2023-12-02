/*
COMARLAU VLAD-CONSTANTIN
GRUPA 1115
ANUL 3
ID

TEMA 10
Aplicație destinată persoanelor care doresc să călătorească în mașina altor persoane
sau persoanelor care doresc să călătorească cu alte persoane în mașina personală
*/
package com.example.ridesharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import com.example.ridesharing.network.asyncTaskRunner;
import com.example.ridesharing.network.callback;
import com.example.ridesharing.network.httpManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    public static final String TRIPS_ARRAY_LIST = "tripsArrayList";
    private ArrayList<trip> trips = new ArrayList<>();
    public static int themeSetting = 0;
    private static Fragment selectedFragment = null;

    private final asyncTaskRunner asyncTaskRunner = new asyncTaskRunner();
    //https://api.ipify.org
    //https://jsonkeeper.com/b/UYAD
    private static final String URL_ADDRESS =
            "https://raw.githubusercontent.com/vladcomarlau/rideSharingAndroidApp_mock-up/master/test.json";
    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(TRIPS_ARRAY_LIST,trips);
                if(item.getItemId() == R.id.nav_dashboard){
                    selectedFragment = new dashboard_fragment();
                }else if(item.getItemId() == R.id.nav_profile){
                    selectedFragment = new profile_fragment();
                }else if(item.getItemId() == R.id.nav_trips){
                    selectedFragment = new trips_fragment();
                }else if(item.getItemId() == R.id.nav_settings){
                    selectedFragment = new settings_fragment();
                }
                selectedFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            }
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(trips.isEmpty()){
            getTripsFromUrl();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                themeSetting = 0;
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                themeSetting = 1;
                break;
        }
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }
    private void getTripsFromUrl(){
        Callable<String> asyncOperation = new httpManager(URL_ADDRESS);
        callback<String> mainThreadOperation = new callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                if(trips.isEmpty()){
                    trips.addAll(tripJsonParser.fromJson(result));
                }
                sendDataToDashboard();
            }
        };
        asyncTaskRunner.executeAsync(asyncOperation,mainThreadOperation);
    }
    private void sendDataToDashboard() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TRIPS_ARRAY_LIST,trips);
        selectedFragment = new dashboard_fragment();

        selectedFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }
}

