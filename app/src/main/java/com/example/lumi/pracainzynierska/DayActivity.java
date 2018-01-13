package com.example.lumi.pracainzynierska;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_tasks_activty);
        Button btn_main = (Button)(findViewById(R.id.btn_bckToMain));
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseTasks dbTasks = new DatabaseTasks(this);
        Cursor cursor = dbTasks.getTasks();
        TextView tasks = (TextView) findViewById(R.id.tvTasks);

        Cursor cursorAims = dbTasks.getAims();
        while (cursorAims.moveToNext())
        {
            String toast = "\n"+cursorAims.getString(0)+", "+cursorAims.getString(1)+", "+cursorAims.getString(2)+", "
                    +cursorAims.getString(3)+", "+ cursorAims.getString(4)+", "+ cursorAims.getString(5);
            tasks.setText(tasks.getText()+toast);
        }

        while (cursor.moveToNext())
    {
        String toast = "\n"+cursor.getString(0)+", "+cursor.getString(1)+", "+cursor.getString(2)+", "
                +cursor.getString(3)+", "+ cursor.getString(4)+", "+ cursor.getString(5)+", "+ cursor.getString(6)
                +", "+ cursor.getString(7);
        tasks.setText(tasks.getText()+toast);
    }
    }
}
