package com.example.lumi.pracainzynierska;


import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DatabaseActivity extends AppCompatActivity {
    DatabaseTasks db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_items);

        Button btnBack = (Button)findViewById(R.id.btn_add);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvID = (TextView)findViewById(R.id.tvColID);
        TextView tvName = (TextView)findViewById(R.id.tvColName);
        TextView tvSatisfaction = (TextView)findViewById(R.id.tvColSatisfaction);

        db = new DatabaseTasks(this);
        Cursor cursor= db.getUsers();
        while (cursor.moveToNext())
        {
            tvID.setText(tvID.getText()+"\n"+cursor.getString(0));
            tvName.setText(tvName.getText()+"\n"+cursor.getString(1));
            tvSatisfaction.setText(tvSatisfaction.getText()+"\n"+cursor.getString(2));
        }
    }
}
