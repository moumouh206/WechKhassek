package com.mba2dna.wechkhassek.constants;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.mba2dna.wechkhassek.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by MBA2DNA on 07/02/2015.
 */
public class Constants  {

    public static String fontBold = "fonts/NexaBold.ttf";
    public static String  fontLight = "fonts/NexaLight.ttf";

    public static final String URL = "http://depanini.16mb.com/mobile/json.php";//"http://mobile.dirannonce.com/json.php";
    public static String API_KEY ="AIzaSyCW-GQfW80cH4V2zufLBVJhwzmABLQYqwU";


public static String RandomDateTime(){
    SimpleDateFormat dfDateTime  = new SimpleDateFormat("hh:mm", Locale.getDefault());
    int year = randBetween(1900, 2013);// Here you can set Range of years you need
    int month = randBetween(0, 11);
    int hour = randBetween(1, 23); //Hours will be displayed in between 9 to 22
    int min = randBetween(0, 59);
    int sec = randBetween(0, 59);
    GregorianCalendar gc = new GregorianCalendar(year, month, 1);
    int day = randBetween(1, gc.getActualMaximum(gc.DAY_OF_MONTH));
    gc.set(year, month, day, hour, min,sec);
    return dfDateTime.format(gc.getTime());
}
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
