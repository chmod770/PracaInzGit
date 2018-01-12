package com.example.lumi.pracainzynierska;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        registerDateClick();
        Button btnBack = (Button)findViewById(R.id.btn_add);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void registerDateClick()
    {
        CalendarView cv = (CalendarView) findViewById(R.id.cv_main);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(CalendarActivity.this, DayPlanActivity.class );
                Bundle b = new Bundle();//creating new Bundle where is stored data
                b.putString("date", Dates.getRefactoredDate(year, month, dayOfMonth)); //adding date to Boundle
                intent.putExtras(b); //Put your id to your next Intent, there will be date in Extras
                startActivity(intent);//starting new activity
            }
        });


    }



}
