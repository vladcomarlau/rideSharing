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
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.Locale;

public class editProfileActivity extends AppCompatActivity{
    public static final String PROFILE_FIRSTNAME_KEY = "PROFILE_FIRSTNAME_KEY";
    public static final String PROFILE_LASTNAME_KEY = "PROFILE_LASTNAME_KEY";
    public static final String PROFILE_BIRTHDATE_KEY = "PROFILE_BIRTHDATE_KEY";
    public static final String PROFILE_PHONE_KEY = "PROFILE_PHONE_KEY";
    public static final String PROFILE_EMAIL_KEY = "PROFILE_EMAIL_KEY";
    public static final String PROFILE_PREMIUM_KEY = "PROFILE_PREMIUM_KEY";
    public static final String trim_key = "@";
    private Intent intent;
    private TextInputEditText tietFirstName;
    TextInputEditText tietLastName;
    TextInputEditText tietBirthdate;
    TextInputEditText tietPhoneNumber;
    TextInputEditText tietEmail;
    Switch sPremium;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        intent = getIntent();
        FloatingActionButton saveProfileButton = findViewById(R.id.saveProfileButton);
        FloatingActionButton cancelProfileButton = findViewById(R.id.cancelProfileButton);
        tietFirstName = findViewById(R.id.editFirstName);
        tietLastName = findViewById(R.id.editLastName);
        tietBirthdate = findViewById(R.id.editBirthdate);
        tietPhoneNumber = findViewById(R.id.editPhoneNumber);
        tietEmail = findViewById(R.id.editEmail);
        sPremium = findViewById(R.id.sPremiumUser);
        saveProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    if(validateForm()){
                        profile newProfile = buildProfile();
                        intent.putExtra(PROFILE_FIRSTNAME_KEY, newProfile.getFirstName());
                        intent.putExtra(PROFILE_LASTNAME_KEY, newProfile.getLastName());
                        intent.putExtra(PROFILE_BIRTHDATE_KEY, newProfile.getBirthdate());
                        intent.putExtra(PROFILE_PHONE_KEY, newProfile.getPhone());
                        intent.putExtra(PROFILE_EMAIL_KEY, newProfile.getEmail());
                        intent.putExtra(PROFILE_PREMIUM_KEY, newProfile.isPremiumUser());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        cancelProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
    private profile buildProfile() throws ParseException {
        String firstName = getTietFirstName().getText().toString();
        String lastName = getTietLastName().getText().toString();
        Date birthDate =  dateConverter.stringToDate(getTietBirthdate().getText().toString().trim());
        String phone = getTietPhoneNumber().getText().toString();
        String email = getTietEmail().getText().toString();
        boolean premium = sPremium.isChecked();
        return new profile(firstName, lastName, birthDate, phone, email, premium);
    }
    private boolean validateForm() throws ParseException {
        if(getTietFirstName().getText() == null
                || getTietFirstName().getText().toString().trim().length() <3){
            Toast.makeText(getApplicationContext(), R.string.first_name_warning,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(getTietLastName().getText() == null
                || getTietLastName().getText().toString().trim().length() <3){
            Toast.makeText(getApplicationContext(), R.string.last_name_warning,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        Date date = dateConverter.stringToDate(getTietBirthdate().getText().toString().trim());
        if(date == null
                || dateConverter.dateToString(date).trim().isEmpty()
                || ((   Integer.parseInt(String.valueOf(Year.now()))
                    - ( Integer.parseInt(String.valueOf(date.getYear()))
                        + 1900))
                    < 18)){
            Toast.makeText(getApplicationContext(), R.string.age_warning,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(getTietPhoneNumber().getText().toString().trim() == null
                || getTietPhoneNumber().getText().toString().trim().isEmpty()
                || getTietPhoneNumber().getText().toString().trim().length() < 10
                || Integer.parseInt(getTietPhoneNumber().getText().toString()) < 1){
            Toast.makeText(getApplicationContext(), R.string.invalid_phone,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if(getTietEmail().getText().toString().trim() == null
                || !getTietEmail().getText().toString().trim().contains(trim_key)
                || getTietEmail().getText().toString().trim().isEmpty()
                || getTietEmail().getText().toString().trim().length() < 4){
            Toast.makeText(getApplicationContext(), R.string.invalid_email,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public TextInputEditText getTietFirstName() {
        return tietFirstName;
    }

    public void setTietFirstName(TextInputEditText tietFirstName) {
        this.tietFirstName = tietFirstName;
    }

    public TextInputEditText getTietLastName() {
        return tietLastName;
    }

    public void setTietLastName(TextInputEditText tietLastName) {
        this.tietLastName = tietLastName;
    }

    public TextInputEditText getTietBirthdate() {
        return tietBirthdate;
    }

    public void setTietBirthdate(TextInputEditText tietBirthdate) {
        this.tietBirthdate = tietBirthdate;
    }

    public TextInputEditText getTietPhoneNumber() {
        return tietPhoneNumber;
    }

    public void setTietPhoneNumber(TextInputEditText tietPhoneNumber) {
        this.tietPhoneNumber = tietPhoneNumber;
    }

    public TextInputEditText getTietEmail() {
        return tietEmail;
    }

    public void setTietEmail(TextInputEditText tietEmail) {
        this.tietEmail = tietEmail;
    }
}