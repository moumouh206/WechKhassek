package com.mba2dna.wechkhassek.constants;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by MBA2DNA on 07/02/2015.
 */
public class Constants  {

    public static String NexaBold = "fonts/NexaBold.ttf";
    public static String NexaLight = "fonts/NexaLight.ttf";
    public static String GothamBold = "fonts/GothamBold.ttf";
    public static String  GothamLight = "fonts/GothamLight.ttf";
    public static final String URL = "http://depanini.16mb.com/mobile/json.php";//"http://mobile.dirannonce.com/json.php";
    public static String API_KEY ="AIzaSyC3iuEk7YBh6FYU_2NDP3EWA6I1UdqtCBA";
    public static final String FILE_UPLOAD_URL = "http://depanini.16mb.com/mobile/fileUpload.php";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";

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
