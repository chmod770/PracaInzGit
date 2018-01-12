package com.example.lumi.pracainzynierska;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment{
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal =Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day= cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(),year,month,day);
    }

}
