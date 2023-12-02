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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;

public class profile_fragment extends Fragment implements View.OnClickListener {
    public static final String PROFILE_PREFERENCES = "profilePreferences";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String BIRTHDATE = "birthdate";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String PROFILE_FIRSTNAME_KEY = "PROFILE_FIRSTNAME_KEY";
    public static final String PROFILE_LASTNAME_KEY = "PROFILE_LASTNAME_KEY";
    public static final String PROFILE_BIRTHDATE_KEY = "PROFILE_BIRTHDATE_KEY";
    public static final String PROFILE_PHONE_KEY = "PROFILE_PHONE_KEY";
    public static final String PROFILE_EMAIL_KEY = "PROFILE_EMAIL_KEY";
    public static final String LAST_PROFILE_UPDATE_MONTH = "lastProfileUpdateMonth";
    public static final String LAST_PROFILE_UPDATE_YEAR = "LAST_PROFILE_UPDATE_YEAR";
    public static final String PREMIUM_USER = "PREMIUM_USER";
    public static final String PROFILE_PREMIUM_KEY = "PROFILE_PREMIUM_KEY";
    public static final String NEW_PROFILE = "NEW PROFILE";
    public static final String DEF_EMPTY_VALUE = "-";
    private ActivityResultLauncher<Intent> editProfileLauncher;
    private SharedPreferences preferences;

    private View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editProfileLauncher = registerEditProfileLauncher();
        preferences = getActivity().getSharedPreferences(
                PROFILE_PREFERENCES,
                Context.MODE_PRIVATE);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Button editButton = (Button) view.findViewById(R.id.profilePageButton);
        try {
            loadProfileFromPreferences();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        displayProfileInfo();
        editButton.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.profilePageButton){
            Intent intent = new Intent(getActivity(), editProfileActivity.class);
            editProfileLauncher.launch(intent);
        }
    }
    private ActivityResultLauncher<Intent> registerEditProfileLauncher(){
        ActivityResultCallback<ActivityResult> callback = getEditProfileCallback();
        return registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
                callback);
    }
    private ActivityResultCallback<ActivityResult> getEditProfileCallback(){
        return new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result){
                if(result.getResultCode() == RESULT_OK  &&  result.getData() != null){
                    String firstName = result.getData().getStringExtra(PROFILE_FIRSTNAME_KEY);
                    String lastName = result.getData().getStringExtra(PROFILE_LASTNAME_KEY);
                    Date birthdate = (Date)result.getData().getSerializableExtra(PROFILE_BIRTHDATE_KEY);
                    String phone = result.getData().getStringExtra(PROFILE_PHONE_KEY);
                    String email = result.getData().getStringExtra(PROFILE_EMAIL_KEY);
                    Boolean premium = result.getData().getBooleanExtra(PROFILE_PREMIUM_KEY,false);

                    profile newProfile =
                            new profile(firstName, lastName, birthdate, phone, email, premium);

                    if (newProfile != null) {
                        Log.i(NEW_PROFILE, newProfile.toString());
                        try {
                            saveProfileInPreferences(newProfile);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        displayProfileInfo();
                    }
                }
            }
        };
    }
    private void displayProfileInfo(){
        TextView tvFullName = view.findViewById(R.id.profilePageFullNameData);
        TextView tvBirthdate = view.findViewById(R.id.profilePageInfoBirthdateData);
        TextView tvPhone = view.findViewById(R.id.profilePageInfoPhoneData);
        TextView tvEmail = view.findViewById(R.id.profilePageInfoEmailData);
        TextView tvLastUpdate = view.findViewById(R.id.profilePageInfoLastUpdate);
        TextView tvPremiumUser = view.findViewById(R.id.profilePageInfoIsPremium);
        tvFullName.setText(getString(R.string.fullname_template, profile.getFirstName(), profile.getLastName()));
        tvBirthdate.setText(dateConverter.dateToString(profile.getBirthdate()));
        tvPhone.setText(profile.getPhone());
        tvEmail.setText(profile.getEmail());
        tvLastUpdate.setText(getString(R.string.last_profile_update_template,
                preferences.getInt(LAST_PROFILE_UPDATE_MONTH,0),
                preferences.getInt(LAST_PROFILE_UPDATE_YEAR,0)));
        tvPremiumUser.setText(String.valueOf(profile.isPremiumUser()));
    }
    private void saveProfileInPreferences(profile newProfile) throws ParseException {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FIRST_NAME, newProfile.getFirstName());
        editor.putString(LAST_NAME, newProfile.getLastName());
        editor.putString(BIRTHDATE, dateConverter.dateToString(newProfile.getBirthdate()));
        editor.putString(PHONE, newProfile.getPhone());
        editor.putString(EMAIL, newProfile.getEmail());
        editor.putInt(LAST_PROFILE_UPDATE_MONTH, LocalDate.now().getMonthValue());
        editor.putInt(LAST_PROFILE_UPDATE_YEAR, Year.now().getValue());
        editor.putBoolean(PREMIUM_USER, newProfile.isPremiumUser());
        editor.apply();
    }
    private void loadProfileFromPreferences() throws ParseException {
        profile.setFirstName(preferences.getString(FIRST_NAME, DEF_EMPTY_VALUE));
        profile.setLastName(preferences.getString(LAST_NAME,DEF_EMPTY_VALUE));
        profile.setBirthdate(dateConverter.stringToDate(preferences.getString(BIRTHDATE,
                "1/1/1901")));
        profile.setPhone(preferences.getString(PHONE,DEF_EMPTY_VALUE));
        profile.setEmail(preferences.getString(EMAIL,DEF_EMPTY_VALUE));
        profile.setPremiumUser(preferences.getBoolean(PREMIUM_USER,false));
        TextView tvFullName = view.findViewById(R.id.profilePageFullNameData);
        TextView tvBirthdate = view.findViewById(R.id.profilePageInfoBirthdateData);
        TextView tvPhone = view.findViewById(R.id.profilePageInfoPhoneData);
        TextView tvEmail = view.findViewById(R.id.profilePageInfoEmailData);
        TextView tvLastUpdate = view.findViewById(R.id.profilePageInfoLastUpdate);
        TextView tvPremiumUser = view.findViewById(R.id.profilePageInfoIsPremium);
        tvFullName.setText(getString(R.string.fullname_template,
                profile.getFirstName(),
                profile.getLastName()));
        tvBirthdate.setText(dateConverter.dateToString(profile.getBirthdate()));
        tvPhone.setText(profile.getPhone());
        tvEmail.setText(profile.getEmail());
        tvLastUpdate.setText(getString(R.string.last_profile_update_template,
                preferences.getInt(LAST_PROFILE_UPDATE_MONTH,0),
                preferences.getInt(LAST_PROFILE_UPDATE_YEAR,0)));
        tvPremiumUser.setText(String.valueOf(profile.isPremiumUser()));
    }
}
