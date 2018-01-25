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

        return getRefactoredDate(year,month+1,dayOfMonth);

    }

    public static String getRefactoredDate(int year, int month, int dayOfMonth)
    {

        return year+"-"+refactorLessNumber(month)+"-"+refactorLessNumber(dayOfMonth);
    }

    public static String getRefactoredTime(int hour, int minutes)
    {
        return refactorLessNumber(hour)+":"+refactorLessNumber(minutes);
    }

    private static String refactorLessNumber(int number)
    {
        if(number<10)
            return "0"+number;
        else
            return number+"";
    }
}
