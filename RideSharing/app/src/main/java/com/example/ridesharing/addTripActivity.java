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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.TimePickerDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addTripActivity extends AppCompatActivity{
    private Intent intent;
    private TextInputEditText tietPickupLocation;
    private TextInputEditText tietDestination;
    private Button btnDate;
    private Button btnTime;
    private static int hour = -1;
    private static int minute = -1;
    private RadioGroup rgPassengers;
    private Switch sRoundTrip;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        intent = getIntent();
        FloatingActionButton scheduleTripButton = findViewById(R.id.scheduleNewTripButton);
        FloatingActionButton cancelTripButton = findViewById(R.id.cancelTripButton);
        tietPickupLocation = findViewById(R.id.tripPickupLocation);
        tietDestination = findViewById(R.id.tripDestination);
        btnDate = findViewById(R.id.btnDatePicker);
        btnTime = findViewById(R.id.btnTimePicker);
        rgPassengers = findViewById(R.id.rgPassengers);
        sRoundTrip = findViewById(R.id.sRoundTrip);
        scheduleTripButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    if(validateForm()){
                        intent.putExtra("NEW_TRIP_KEY", buildTrip());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        cancelTripButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        addTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                btnDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(addTripActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minuteOfHour) {
                                btnTime.setText(hourOfDay+":"+minuteOfHour);
                                addTripActivity.setHour(hourOfDay);
                                addTripActivity.setMinute(minuteOfHour);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }
    private trip buildTrip() throws ParseException {
        String pickupLocation = getTietPickupLocation().getText().toString();
        String destination = getTietDestination().getText().toString();
        Date tripDate = new SimpleDateFormat("dd/MM/yyyy").parse(
                btnDate.getText().toString());
        int passengers = 1;
        if(rgPassengers.getCheckedRadioButtonId() == R.id.rb2Passengers){
                passengers = 2;
        } else if (rgPassengers.getCheckedRadioButtonId() == R.id.rb3Passengers){
            passengers = 3;
        }
        boolean roundTrip = false;
        if(sRoundTrip.isChecked()){
            roundTrip = true;
        }
        return new trip(pickupLocation, destination, tripDate, hour,
                        minute, 0, passengers, roundTrip);
    }
    private boolean validateForm() throws ParseException {
        if(getTietPickupLocation().getText() == null
                || getTietPickupLocation().getText().toString().trim().length() <3){
            Toast.makeText(getApplicationContext(), R.string.location_warning,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(getTietDestination().getText() == null
                || getTietDestination().getText().toString().trim().length() <3){
            Toast.makeText(getApplicationContext(), R.string.destination_warning,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        String date = btnDate.getText().toString();
        if( date == null
            || date.equals("Select date")
            || date.isEmpty()
            || date.length() < 8){
            Toast.makeText(getApplicationContext(), R.string.date_invalid,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(hour < 0
            || hour > 24
            || minute < 0
            || minute > 60){
                Toast.makeText(getApplicationContext(), R.string.time_invalid, Toast.LENGTH_SHORT).show();
                return false;
        }
        if(getRgPassengers().getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), R.string.select_passengers,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public TextInputEditText getTietPickupLocation() {
        return tietPickupLocation;
    }

    public void setTietPickupLocation(TextInputEditText tietPickupLocation) {
        this.tietPickupLocation = tietPickupLocation;
    }

    public TextInputEditText getTietDestination() {
        return tietDestination;
    }

    public void setTietDestination(TextInputEditText tietDestination) {
        this.tietDestination = tietDestination;
    }
    public RadioGroup getRgPassengers() {
        return rgPassengers;
    }

    public void setRgPassengers(RadioGroup rgPassengers) {
        this.rgPassengers = rgPassengers;
    }

    public Switch getsRoundTrip() {
        return sRoundTrip;
    }

    public void setsRoundTrip(Switch sRoundTrip) {
        this.sRoundTrip = sRoundTrip;
    }

    public Button getBtnDate() {
        return btnDate;
    }

    public void setBtnDate(Button btnDate) {
        this.btnDate = btnDate;
    }

    public Button getBtnTime() {
        return btnTime;
    }

    public void setBtnTime(Button btnTime) {
        this.btnTime = btnTime;
    }

    public static int getHour() {
        return hour;
    }

    public static void setHour(int hour) {
        addTripActivity.hour = hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static void setMinute(int minute) {
        addTripActivity.minute = minute;
    }
}