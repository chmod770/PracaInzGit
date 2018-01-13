package com.example.lumi.pracainzynierska;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        Button btn_add = (Button)(findViewById(R.id.btn_add));
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        EditText etDate = (EditText)findViewById(R.id.et_date);

        //setting not default data if exist
        Bundle b = getIntent().getExtras();//stetting new Boundle which is stored in Extras
        if(b!=null)//if Boundle doesnt exist thesre is not sent date
            etDate.setHint(b.getString("date"));
        else
        etDate.setHint(Dates.getTodayDate());

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker= new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });


        /*Spinner spCategories = (Spinner)findViewById(R.id.sp_category);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(AddTaskActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories));
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(categoriesAdapter);*/

        Spinner spPriority = (Spinner)findViewById(R.id.sp_priority);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(AddTaskActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.priority));
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(priorityAdapter);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =Dates.getRefactoredDate(year,month,dayOfMonth);
        EditText etDate = (EditText)findViewById(R.id.et_date);
        etDate.setText(date);
    }

    private void addTask()
    {
        EditText etName= (EditText)findViewById(R.id.et_name);
        EditText etDate= (EditText)findViewById(R.id.et_date);
        EditText etTime= (EditText)findViewById(R.id.et_time);
        Spinner spPriority = (Spinner)findViewById(R.id.sp_priority);
        String date;

        if(etDate.getText().toString().length()==0)
            date=etDate.getHint().toString();
        else
            date=etDate.getText().toString();

        if(etName.getText().length()==0||etTime.getText().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Wszystkie pola muszą być wypełnione",Toast.LENGTH_LONG).show();
            return;
        }
        if(!tryParseInt(etTime.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Czas musi być liczbą", Toast.LENGTH_LONG).show();
                return;
        }

        DatabaseTasks db = new DatabaseTasks(getApplicationContext());
        if(db.insertTask(1, etName.getText().toString(), date, Integer.parseInt(etTime.getText().toString()), 0,spPriority.getSelectedItemPosition()))
        {
            finish();
        }else
            Toast.makeText(getApplicationContext(), "Kolejne błędy", Toast.LENGTH_LONG).show();

    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}