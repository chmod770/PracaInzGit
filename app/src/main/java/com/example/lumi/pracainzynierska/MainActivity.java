package com.example.lumi.pracainzynierska;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    }

    public void buttonOnClick(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_ok:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                break;
            case R.id.btn_calendar:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                break;
            case R.id.btn_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.btn_aims:
                startActivity(new Intent(MainActivity.this, AimsActivity.class));
                break;
            case R.id.btn_tasks:
                startActivity(new Intent(MainActivity.this, DayActivity.class));
                break;
            case R.id.btn_day:
                startActivity(new Intent(MainActivity.this, DayPlanActivity.class));
                break;
        }
        updateView();
    }

    public void setNotification()
    {
        Context context = MainActivity.this;
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification(android.R.drawable.stat_notify_more,"This is Notification", System.currentTimeMillis());
        CharSequence title = "Ypou have been notified";
        CharSequence details ="Keep it going";
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context,0,intent,0);
        nm.notify(0,notify);
    }

}
