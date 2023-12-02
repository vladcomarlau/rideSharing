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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class settings_fragment extends Fragment {
    public static final String THEME_VALUE = "theme";
    public static final String THEME_FILE = "theme_preferences";
    private Spinner spnThemes;
    private static SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        spnThemes = view.findViewById(R.id.spinnerThemes);
        preferences = getActivity().getSharedPreferences(
                THEME_FILE,
                Context.MODE_PRIVATE);
        spnThemes.setSelection( preferences.getInt(THEME_VALUE,3));
        ArrayAdapter<CharSequence> themesSpinnerAdapter = ArrayAdapter
                .createFromResource( getActivity().getApplicationContext(),
                        R.array.fragment_settings_themes,
                        R.layout.custom_spinner_item);
        spnThemes.setAdapter(themesSpinnerAdapter);
        spnThemes.setSelection(MainActivity.themeSetting);
        spnThemes.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        MainActivity.themeSetting = pos;
                        setTheme(pos);
                        saveThemePreferences(pos);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        return view;
    }
    public static void setTheme(int position){
        switch (position){
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }
    public static void saveThemePreferences(int position){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(THEME_VALUE, position);
        editor.apply();
    }
}
