package com.example.lumi.pracainzynierska;


import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AimsActivity extends AppCompatActivity {
    private List<Aim> myAims= new ArrayList<Aim>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aim_activity);

        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnAdd = (Button)findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AimsActivity.this, AddAimActivity.class );
                startActivity(intent);
            }
        });

        if((new DatabaseTasks(this)).getAims().getCount()==0)
            Toast.makeText(getApplicationContext(),"Aktualnie nie masz żadnego celu",Toast.LENGTH_SHORT).show();

        populateAimsList();
        populateAimAdapterView();
    }
    private void populateAimsList()
    {
        DatabaseTasks db = new DatabaseTasks(this);
        myAims.clear();

        Cursor cursor = db.getAims();

        while (cursor.moveToNext())
        {
            myAims.add(new Aim(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
    }

    private void populateAimAdapterView()
    {
        ArrayAdapter<Aim> adapter = new MyListAimsAdapter();
        ListView list = (ListView)findViewById(R.id.lv_aims);
        list.setAdapter(adapter);
    }

    private class MyListAimsAdapter extends ArrayAdapter<Aim>
    {
        public MyListAimsAdapter()
        {
            super(AimsActivity.this,R.layout.aim_items, myAims);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView=getLayoutInflater().inflate(R.layout.aim_items,parent,false);
            }

            final Aim currentAim = myAims.get(position);

            //fill name field
            TextView name = (TextView)itemView.findViewById(R.id.item_tv_name);
            name.setText(currentAim.getName());

            //fill dateTo field
            TextView dateTo = (TextView)itemView.findViewById(R.id.item_tv_dataDo);
            dateTo.setText(currentAim.getDataDo());

            //fill category field
            TextView category = (TextView)itemView.findViewById(R.id.item_tv_kategoria);
            category.setText(currentAim.getKategoria());

            //fill description field
            TextView description= (TextView)itemView.findViewById(R.id.item_tv_opis);
            description.setText(currentAim.getOpis());

            return itemView;
        }
    }

}