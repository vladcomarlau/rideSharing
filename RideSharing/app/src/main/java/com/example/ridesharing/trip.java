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

import java.io.Serializable;
import java.util.Date;

public class trip implements Serializable {
    public static final String MEET_LOCATION = "\nMeet location:   ";
    public static final String DESTINATION = "Destination:     ";
    public static final String DATE = "Date:    ";
    public static final String PICK_UP_TIME_HH_MM = "Pick-up time (HH:MM):     ";
    public static final String SEPARATOR = ":";
    public static final String PASSENGERS = "Passengers: ";
    public static final String ROUND_TRIP = "Round trip: ";
    public static final String LINE_BREAK = "\n";
    public static final String NEW_LINE = " \n";
    private String pickupLocation;
    private String destination;
    private Date tripDate;
    private int tripTimeHours;
    private int tripTimeMinutes;
    private int tripTimeSeconds;
    private int passengers;
    private boolean roundTrip;
    public trip(String meetLocation,
                String destination,
                Date tripDate,
                int tripTimeHours,
                int tripTimeMinutes,
                int tripTimeSeconds, int passengers,
                boolean roundTrip) {
        this.pickupLocation = meetLocation;
        this.destination = destination;
        this.tripDate = tripDate;
        this.tripTimeHours = tripTimeHours;
        this.tripTimeMinutes = tripTimeMinutes;
        this.tripTimeSeconds = tripTimeSeconds;
        this.passengers = passengers;
        this.roundTrip = roundTrip;
    }
    public String getMeetLocation() {
        return pickupLocation;
    }

    public void setMeetLocation(String meetLocation) {
        this.pickupLocation = meetLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }
    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public int getTripTimeHours() {
        return tripTimeHours;
    }

    public void setTripTimeHours(int tripTimeHours) {
        this.tripTimeHours = tripTimeHours;
    }

    public int getTripTimeMinutes() {
        return tripTimeMinutes;
    }

    public void setTripTimeMinutes(int tripTimeMinutes) {
        this.tripTimeMinutes = tripTimeMinutes;
    }
    public int getTripTimeSeconds() {
        return tripTimeSeconds;
    }
    public void setTripTimeSeconds(int tripTimeSeconds) {
        this.tripTimeSeconds = tripTimeSeconds;
    }
    @NonNull
    @Override
    public String toString() {
        return MEET_LOCATION + this.pickupLocation + NEW_LINE
                + DESTINATION + this.destination + NEW_LINE
                + DATE + dateConverter.dateToString(this.tripDate) + NEW_LINE
                + PICK_UP_TIME_HH_MM + this.tripTimeHours + SEPARATOR
                + this.tripTimeMinutes + NEW_LINE
                + PASSENGERS + this.passengers + NEW_LINE
                + ROUND_TRIP + this.roundTrip
                + LINE_BREAK;
    }
}
