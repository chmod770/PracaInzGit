package com.example.lumi.pracainzynierska;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        populateAimsList();
        populateAimAdapterView();
        registerClickCallback();
    }


    @Override
    protected void onResume() {
        super.onResume();
        populateAimsList();
        populateAimAdapterView();
    }

    private void populateAimsList()
    {
        DatabaseTasks db = new DatabaseTasks(this);
        myAims.clear();

        Cursor cursor = db.getAims();
        TextView tvInfo = (TextView)findViewById(R.id.tv_info);
        if(cursor.getCount()==0)
        {
            tvInfo.setText("Cele\n(Aktualnie nie masz celów)");
        }else
            tvInfo.setText("Cele");

        while (cursor.moveToNext())
        {
            myAims.add(new Aim(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
    }

    private void registerClickCallback() {
        ListView list = (ListView)findViewById(R.id.lv_aims);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Aim clickedAim=myAims.get(position);
                deletingAimPopUp(clickedAim.getIdAim());
                return false;
            }
        });
    }

    private void deletingAimPopUp(final int idToDelete)
    {
        //Creating dialog interface which we need to set functions on alert dialog buttons
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //using switch where 'which' means which button was presed
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        DatabaseTasks db = new DatabaseTasks(getApplicationContext());
                        db.deleteAimWithIdAndTasks(idToDelete);
                        populateAimsList();
                        populateAimAdapterView();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        //building alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting buttons, and texts on alert buttons
        builder.setMessage("Czy chcesz usunąć ten cel\n i wszystkie taski do niego podpięte ?")
                .setNegativeButton("Nie", dialogClickListener)
                .setPositiveButton("Tak", dialogClickListener)
                .show();
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
            dateTo.setText("Do dnia: \n"+currentAim.getDataDo());

            //fill category field
            TextView category = (TextView)itemView.findViewById(R.id.item_tv_kategoria);
            category.setText("Kategoria: \n"+currentAim.getKategoria());

            //fill description field
            TextView description= (TextView)itemView.findViewById(R.id.item_tv_opis);
            description.setText("Opis:\n"+currentAim.getOpis());

            return itemView;
        }
    }

}