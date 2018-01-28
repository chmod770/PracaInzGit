package com.example.lumi.pracainzynierska;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    DatabaseTasks db;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        populatePrioritySpinner();
        startSettings();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void startSettings()
    {

        db=new DatabaseTasks(getApplicationContext());
        final User user = new User(getApplicationContext());
        //final Cursor cursor = db.getUsers();

        //setting name
            TextView tvName = (TextView)findViewById(R.id.tvName);
            tvName.setText("Nazwa: " + user.getImie());
        //setting hour
            final EditText etHour = (EditText)findViewById(R.id.et_hour);
            etHour.setText(user.getGodzinaPrzypomnienia());

        //setting priority
            Spinner spPriority = (Spinner)findViewById(R.id.spPriority);
            spPriority.setSelection(user.getMinPriotytet()-1);

        //setting ifIsOnNotification
            final Switch swDoSetNotification = (Switch)findViewById(R.id.sw_doSetNotifications);
            if(user.getCzyWlaczane()==0)
                swDoSetNotification.setChecked(false);
            else
                swDoSetNotification.setChecked(true);
        //setting onChangeMethod
        swDoSetNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.setIsReminderOn(swDoSetNotification.isChecked());//update in database
                setReminder(swDoSetNotification.isChecked());//set reminder notification
            }
        });

        Button btnBack = (Button)findViewById(R.id.btn_add);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvName = (TextView)findViewById(R.id.tvName);
                EditText etName = (EditText)findViewById(R.id.et_name);
                Spinner spMinPriority = (Spinner)findViewById(R.id.spPriority);

                db.updateReminderData(Integer.parseInt(spMinPriority.getSelectedItem().toString())+1);

                switch (etName.getText().length())
                {
                    case 0:
                        break;
                    case 1:
                    case 2:
                        Toast.makeText(getApplicationContext(),"Nazwa użytkownika musi się składać z minimum trzech znaków ",Toast.LENGTH_LONG).show();
                        break;
                    default:
                    {
                        tvName.setText("Imie: " + etName.getText());
                        db.updateUserName(etName.getText().toString());
                    }
                }

            }
        });

        etHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Wybierz godzine");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText etHour =(EditText)findViewById(R.id.et_hour);
        etHour.setText(Dates.getRefactoredTime(hourOfDay, minute));
        db.updateReminderHour(Dates.getRefactoredTime(hourOfDay, minute));
         Switch swDoSetNotification = (Switch)findViewById(R.id.sw_doSetNotifications);
        setReminder(swDoSetNotification.isChecked());
    }

    private void populatePrioritySpinner()
    {
        Spinner spPriority = (Spinner)findViewById(R.id.spPriority);
        //creating adapter with TextView which store strings (aims) loaded from database
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(SettingsActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.priority));
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(priorityAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setReminder(boolean ifIsReminderOn)
    {

        if(ifIsReminderOn)
        {
            Calendar cal = Calendar.getInstance();

            String etHourText = ((EditText)findViewById(R.id.et_hour)).getText().toString();
            int hour=Integer.parseInt(etHourText.charAt(0)+""+etHourText.charAt(1));
            int minute=Integer.parseInt(etHourText.charAt(3)+""+etHourText.charAt(4));

            cal.set(Calendar.HOUR, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 1);


            Intent intent = new Intent("reminder_intent");
            PendingIntent broadcast =PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),broadcast);
        }else
        {
            Intent intent = new Intent("reminder_intent");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }
}
