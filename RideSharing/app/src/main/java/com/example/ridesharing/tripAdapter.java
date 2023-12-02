package com.example.ridesharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class tripAdapter extends ArrayAdapter<trip> {
    Context context;
    int resource;
    List<trip> trips;
    LayoutInflater inflater;
    public tripAdapter(@NonNull Context context, int resource, @NonNull List<trip> objects,
                       LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.trips = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        trip trip = trips.get(position);
        if(trip == null){
            return view;
        }else{
            TextView tvDate = view.findViewById(R.id.trip_item_Date);
            if(trip.getTripDate() == null){
                tvDate.setText(R.string.trip_item_default_value);
            }else{
                tvDate.setText(dateConverter.dateToString(trip.getTripDate()));
            }
            TextView tvTime = view.findViewById(R.id.trip_item_time);
            tvTime.setText(context.getString(R.string.trip_item_time_template,
                    trip.getTripTimeHours(),
                    trip.getTripTimeMinutes()));
            TextView tvPassengers = view.findViewById(R.id.trip_item_passengers);
            tvPassengers.setText(String.valueOf(trip.getPassengers()));
            TextView tvRoundTrip = view.findViewById(R.id.trip_item_roundTrip);
            tvRoundTrip.setText(context.getString(R.string.tv_trip_item_round_trip) + trip.isRoundTrip());
            TextView tvDestination = view.findViewById(R.id.trip_item_destination);
            if(trip.getDestination() == null){
                tvDestination.setText(context.getString(R.string.lv_trip_item_destination) + R.string.trip_item_default_value);
            }else{
                tvDestination.setText(context.getString(R.string.lv_trip_item_destination) + trip.getDestination());
            }
            TextView tvMeetLocation = view.findViewById(R.id.trip_item_meet_location);
            if(trip.getMeetLocation() == null){
                tvMeetLocation.setText(context.getString(R.string.tv_trip_item_meet_location) + R.string.trip_item_default_value);
            }else{
                tvMeetLocation.setText(context.getString(R.string.tv_trip_item_meet_location) + trip.getMeetLocation());
            }
        }
        return view;
    }
}
