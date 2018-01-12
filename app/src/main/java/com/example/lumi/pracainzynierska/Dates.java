package com.example.lumi.pracainzynierska;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Lumi on 09.01.2018.
 */

public class Dates {

     public static String getTodayDate()
    {

        long timeInMilis= System.currentTimeMillis();
        Date resultdate = new Date(timeInMilis);
        int year =resultdate.getYear()+1900;
        int month =(resultdate.getMonth())+1;
        int dayOfMonth =resultdate.getDate();

        String date ="";
        date+=year;
        if(month+1<10)
            date+="-0"+(month)+"-";
        else
            date+="-"+(month)+"-";
        if(dayOfMonth<10)
            date+="0"+dayOfMonth;
        else
            date+=+dayOfMonth;

        return date;

    }
    public static String getRefactoredDate(int year, int month, int dayOfMonth)
    {
        String date = "";
        date+=year;
        if(month+1<10)
            date+="-0"+(month+1)+"-";
        else
            date+="-"+(month+1)+"-";
        if(dayOfMonth<10)
            date+="0"+dayOfMonth;
        else
            date+=+dayOfMonth;

        return date;
    }
}
