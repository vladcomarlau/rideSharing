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

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class profile implements Serializable {
    private static String firstName = "-";
    private static String lastName = "-";
    private static Date birthdate = new Date();
    private static String phone = "0777777777";
    private static String email = "-";
    private static boolean premiumUser = false;
    public profile(String firstName,
            String lastName,
            Date birthdate,
            String phone,
            String email,
            Boolean premium){
                this.firstName = firstName;
                this.lastName = lastName;
                this.birthdate = birthdate;
                this.phone = phone;
                this.email = email;
                this.premiumUser = premium;
    }
    public static String getFirstName() {
        return firstName;
    }
    public static void setFirstName(String firstName) {
        profile.firstName = firstName;
    }
    public static String getLastName() {
        return lastName;
    }
    public static void setLastName(String lastName) {
        profile.lastName = lastName;
    }
    public static Date getBirthdate() {
        return birthdate;
    }
    public static void setBirthdate(Date birthdate) {
        profile.birthdate = birthdate;
    }
    public static String getPhone() {
        return phone;
    }
    public static void setPhone(String phone) {
        profile.phone = phone;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        profile.email = email;
    }

    public static boolean isPremiumUser() {
        return premiumUser;
    }

    public static void setPremiumUser(boolean premiumUser) {
        profile.premiumUser = premiumUser;
    }

    @NonNull
    @Override
    public String toString(){
        return profile.firstName + ", "
                + profile.lastName + ", "
                + profile.birthdate + ", "
                + profile.phone + ", "
                + profile.email;
    }
}
