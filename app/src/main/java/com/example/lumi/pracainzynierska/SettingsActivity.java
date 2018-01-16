package com.example.lumi.pracainzynierska;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        db=new DatabaseHelper(getApplicationContext());
        final Cursor cursor = db.pobierzDane();
        //wczytywanie imienia z bazy danych
        if(cursor.getCount()>0)
        {
            cursor.moveToNext();
            TextView tvName = (TextView)findViewById(R.id.tvName);
            tvName.setText("Imie: " + cursor.getString(1));
        }

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
                tvName.setText("Imie: " +  etName.getText());

                if(cursor.getCount()>0)
                 db.updateData(etName.getText()+"",cursor.getString(2)+"");
                else
                    db.updateData(etName.getText()+"","6");

            }
        });
    }


}
