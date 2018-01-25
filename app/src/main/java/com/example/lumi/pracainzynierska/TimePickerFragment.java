package com.example.lumi.pracainzynierska;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        //TimePickerDialog(Context context, int themeResId, TimePickerDialog.OnTimeSetListener listener,
        // int hourOfDay, int minute, boolean is24HourView)
        return new TimePickerDialog(getActivity(),1, (TimePickerDialog.OnTimeSetListener)getActivity(), hour, minute, true);
    }
}
