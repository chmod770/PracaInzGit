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

public class AddAimActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_aim);
        Button btn_add = (Button)(findViewById(R.id.btn_add));
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAim();
            }
        });

        Button btn_back = (Button)(findViewById(R.id.btn_back));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        EditText etDate = (EditText)findViewById(R.id.et_date);

        //setting not default data if exist
            etDate.setHint(Dates.getTodayDate());

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker= new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        Spinner spCategory = (Spinner)findViewById(R.id.sp_category);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(AddAimActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories));
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(priorityAdapter);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =Dates.getRefactoredDate(year,month,dayOfMonth);
        EditText etDate = (EditText)findViewById(R.id.et_date);
        etDate.setText(date);
    }


    private void addAim() {
        EditText etName = (EditText) findViewById(R.id.et_name);
        EditText etDate = (EditText) findViewById(R.id.et_date);
        EditText etDescription = (EditText) findViewById(R.id.et_description);
        Spinner spCategory = (Spinner) findViewById(R.id.sp_category);
        String date;

        if (etDate.getText().toString().length() == 0)
            date = etDate.getHint().toString();
        else
            date = etDate.getText().toString();

        if (etName.getText().length() == 0 || etDescription.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą być wypełnione", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseTasks db = new DatabaseTasks(getApplicationContext());
        if (db.ifAimWithNameExits(etName.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Cel o takiej nazwie już istnieje", Toast.LENGTH_LONG).show();
            return;
        }

        if(db.insertAim(1, etName.getText().toString(), date, etDescription.getText().toString(), spCategory.getSelectedItem().toString()))
        {
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "Kolejne błędy", Toast.LENGTH_LONG).show();
        }
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