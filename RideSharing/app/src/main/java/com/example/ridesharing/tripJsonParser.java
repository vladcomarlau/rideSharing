package com.example.ridesharing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class tripJsonParser {

    public static final String PICKUP_LOCATION = "pickupLocation";
    public static final String DESTINATION = "destination";
    public static final String PASSENGERS = "passengers";
    public static final String ROUND_TRIP = "roundTrip";
    public static final String TRIP_DATE = "tripDate";
    public static final String TRIP_DATE_YEAR = "tripDateYear";
    public static final String TRIP_DATE_MONTH = "tripDateMonth";
    public static final String TRIP_DATE_DAY = "tripDateDay";
    public static final String TRIP_TIME = "tripTime";
    public static final String TRIP_TIME_HOURS = "tripTimeHours";
    public static final String TRIP_TIME_MINUTES = "tripTimeMinutes";
    public static final String TRIP_TIME_SECONDS = "tripTimeSeconds";
    public static final String SLASH_SEPARATOR = "/";

    /*
    JSON FILE STRUCTURE
        trip:
        - pickupLocation
        - destination
        - passengers
        - roundTrip
        - tripDate:
            - tripDateYear
            - tripDateMonth
            - tripDateDay
            - tripTime:
                - tripTimeHours
                - tripTimeMinutes
                - tripTimeSeconds
            */
    public static List<trip> fromJson(String json){
        try {
            JSONArray array = new JSONArray(json);
            return readTripsFromJson(array);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    private static List<trip> readTripsFromJson(JSONArray array) throws JSONException,
            ParseException {
        List<trip> results = new ArrayList<>();
        for(int i = 0; i< array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            trip trip = readTripFromJson(object);
            results.add(trip);
        }
        return results;
    }
    private static trip readTripFromJson(JSONObject object) throws JSONException,
            ParseException {
        String pickupLocation = object.getString(PICKUP_LOCATION);
        String destination = object.getString(DESTINATION);

        int passengers = object.getInt(PASSENGERS);
        boolean roundTrip = object.getBoolean(ROUND_TRIP);

        JSONObject tripDateObject = object.getJSONObject(TRIP_DATE);

        int tripDateYear = tripDateObject.getInt(TRIP_DATE_YEAR);
        int tripDateMonth = tripDateObject.getInt(TRIP_DATE_MONTH);
        int tripDateDay = tripDateObject.getInt(TRIP_DATE_DAY);
        Date tripDate = dateConverter.stringToDate(
                String.valueOf(tripDateDay) + SLASH_SEPARATOR
                        +String.valueOf(tripDateMonth) + SLASH_SEPARATOR
                        +String.valueOf(tripDateYear));

        JSONObject tripTimeObject = tripDateObject.getJSONObject(TRIP_TIME);
        int tripTimeHours = tripTimeObject.getInt(TRIP_TIME_HOURS);
        int tripTimeMinutes = tripTimeObject.getInt(TRIP_TIME_MINUTES);
        int tripTimeSeconds = tripTimeObject.getInt(TRIP_TIME_SECONDS);

        return new trip(pickupLocation,
                destination,
                tripDate,
                tripTimeHours,tripTimeMinutes,tripTimeSeconds,
                passengers,
                roundTrip);
    }
}
