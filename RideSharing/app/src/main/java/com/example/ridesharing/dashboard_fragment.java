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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.util.ArrayList;

public class dashboard_fragment extends Fragment implements View.OnClickListener {
    private static final String THEME_FILE = "theme_preferences";
    private static final String THEME_VALUE = "theme";
    public static final String TRIPS_ARRAY_LIST = "tripsArrayList";
    public static final String defaultEmptyValue = "-";
    public static final String defaultEmptyDate = "1/1/1901";
    private SharedPreferences preferences;
    private SharedPreferences preferencesTheme;
    private View view;
    private TextView tvRecentTrip;
    private TextView tvLocation;
    private TextView tvProfileName;
    private TextView tvNoTrips;
    private Button bViewProfile;
    private Button bScheduleTrip;
    private static trip recentTrip;
    private ArrayList<trip> trips;
    public static final String PROFILE_PREFERENCES = "profilePreferences";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String BIRTHDATE = "birthdate";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        trips = (ArrayList<trip>) getArguments().getSerializable(TRIPS_ARRAY_LIST);
        tvRecentTrip = view.findViewById(R.id.tripsCardText);
        tvNoTrips = view.findViewById(R.id.numberOfTrips);
        tvProfileName = view.findViewById(R.id.profileCardName);
        tvProfileName.setText(getString(R.string.hello_fullname_template,
                profile.getFirstName()
                ,profile.getLastName()));
        if(trips.size()>0){
            recentTrip = trips.get(trips.size()-1);
            tvRecentTrip.setText(recentTrip.toString());
            tvNoTrips.setText(getString(R.string.number_of_trips_template,trips.size()));
        }else{
            tvNoTrips.setText(R.string.no_trips);
        }
        bViewProfile = view.findViewById(R.id.profileCardButton);
        bViewProfile.setOnClickListener(this);

        bScheduleTrip = view.findViewById(R.id.tripsCardButton);
        bScheduleTrip.setOnClickListener(this);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loadProfileFromPreferences();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        preferencesTheme = getActivity().getSharedPreferences(
                THEME_FILE,
                Context.MODE_PRIVATE);
        int theme_value = preferencesTheme.getInt(THEME_VALUE, 2);
        settings_fragment.setTheme(theme_value);
    }
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(v.getId() == R.id.profileCardButton){
            fragmentTransaction.replace(R.id.fragment_container, new profile_fragment());
            fragmentTransaction.commit();
        } else if(v.getId() == R.id.tripsCardButton) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(TRIPS_ARRAY_LIST,trips);
            Fragment selectedFragment = new trips_fragment();
            selectedFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
            fragmentTransaction.commit();
        }
    }
    private void loadProfileFromPreferences() throws ParseException {
        preferences = getActivity().getSharedPreferences(
                PROFILE_PREFERENCES,
                Context.MODE_PRIVATE);
        profile.setFirstName(preferences.getString(FIRST_NAME, defaultEmptyValue));
        profile.setLastName(preferences.getString(LAST_NAME,defaultEmptyValue));
        profile.setBirthdate(dateConverter.stringToDate(preferences.getString(
                BIRTHDATE, defaultEmptyDate)));
        profile.setPhone(preferences.getString(PHONE,defaultEmptyValue));
        profile.setEmail(preferences.getString(EMAIL,defaultEmptyValue));
    }
}

