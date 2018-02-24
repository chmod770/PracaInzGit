package com.example.lumi.pracainzynierska;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onResume()
    {
        user = new User(this);
        updateView();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User(this);
        if(user.getImie().length()==0)
        startActivity(new Intent(MainActivity.this, SetNameActivity.class));

        setContentView(R.layout.main_activity);
        updateView();

    }

    public void updateView()
    {
        TextView data = (TextView)findViewById(R.id.tvData);
        data.setText("Witaj! "+ user.getImie());
        TextView tv_done = (TextView)findViewById(R.id.tv_done);
        DatabaseTasks db = new DatabaseTasks(getApplicationContext());
        if(db.numberTodayTasks()==0)
            tv_done.setText("Nie posiadasz ustawionych zadań na dzisiaj");
        else {
            tv_done.setText("Wykonano dziś " + db.numberTodayDoneTasks() + "/" + db.numberTodayTasks() + " zadań \n");
            if(db.numberTodayTasks()==db.numberTodayDoneTasks())
                tv_done.append("Gratulacje wykonano dziś 100% na dziś");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void buttonOnClick(View view)
    {
        switch(view.getId())
        {

            case R.id.btn_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.btn_aims:
                startActivity(new Intent(MainActivity.this, AimsActivity.class));
                break;
            case R.id.btn_calendar:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                break;
            case R.id.btn_day:
                startActivity(new Intent(MainActivity.this, DayPlanActivity.class));
                break;
            case R.id.btn_statistics:
                startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                break;
            /*case R.id.btn_tasks:
                startActivity(new Intent(MainActivity.this, DayActivity.class));
                break;*/
        }
        updateView();
    }

}
