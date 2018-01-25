package com.example.lumi.pracainzynierska;

import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
            EditText etHour = (EditText)findViewById(R.id.et_hour);
            etHour.setText(user.getGodzinaPrzypomnienia());
        //setting priority
            Spinner spPriority = (Spinner)findViewById(R.id.spPriority);
            spPriority.setSelection(user.getMinPriotytet()-1);
        //setting ifIsOnNotification
            Switch swDoSetNotification = (Switch)findViewById(R.id.sw_doSetNotifications);
            if(user.getCzyWlaczane()==0)
                swDoSetNotification.setChecked(false);
            else
                swDoSetNotification.setChecked(true);


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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText etHour =(EditText)findViewById(R.id.et_hour);
        etHour.setText(Dates.getRefactoredTime(hourOfDay, minute));
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
}
