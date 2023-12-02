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

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class trips_fragment extends Fragment implements View.OnClickListener {
    public static final String TRIPS_ARRAY_LIST = "tripsArrayList";
    public static final String NEW_TRIP_KEY = "NEW_TRIP_KEY";
    private ActivityResultLauncher<Intent> addTripLauncher;
    private View view;
    private ListView lvTrips;
    private ArrayList<trip> trips;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTripLauncher = registerAddTripLauncher();
    }
    private void addTripLvAdapter() {
        trips = (ArrayList<trip>) getArguments().getSerializable(TRIPS_ARRAY_LIST);
        tripAdapter adapter =
                new tripAdapter(
                        getActivity().getApplicationContext(),
                        R.layout.lv_trip_item,
                        trips,
                        getLayoutInflater());
        lvTrips.setAdapter(adapter);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trips, container, false);
        trips = (ArrayList<trip>) getArguments().getSerializable(TRIPS_ARRAY_LIST);
        lvTrips = view.findViewById(R.id.lvTrips);
        addTripLvAdapter();
        FloatingActionButton addTripButton =
                (FloatingActionButton) view.findViewById(R.id.scheduleNewTripButton);
        addTripButton.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.scheduleNewTripButton){
            Intent intent = new Intent(getActivity(), addTripActivity.class);
            addTripLauncher.launch(intent);
        }
    }
    private ActivityResultLauncher<Intent> registerAddTripLauncher(){
        ActivityResultCallback<ActivityResult> callback = getAddTripCallback();
        return registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
                callback);
    }
    private ActivityResultCallback<ActivityResult> getAddTripCallback(){
        return new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result){
                if(result.getResultCode() == RESULT_OK  &&  result.getData() != null){
                    trip newTrip =
                            (trip) result.getData().getSerializableExtra(NEW_TRIP_KEY);
                    if (newTrip != null) {
                        trips.add(newTrip);
                        ArrayAdapter<trip> adapter
                                = (ArrayAdapter<trip>)
                                lvTrips.getAdapter();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
    }
}
