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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class dateConverter {
    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public static Date stringToDate(String value) throws ParseException {
        return formatter.parse(value);
    }
    public static String dateToString(Date value){
        if(value == null){return null;}
        return formatter.format(value);
    }
}
