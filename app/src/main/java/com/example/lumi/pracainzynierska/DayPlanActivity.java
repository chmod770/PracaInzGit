package com.example.lumi.pracainzynierska;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DayPlanActivity extends AppCompatActivity {
    private List<Task> myTasks=new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_plan);
        Button btn_main = (Button)(findViewById(R.id.btn_back));
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btn_add = (Button)(findViewById(R.id.btn_add));
        btn_add.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayPlanActivity.this, AddTaskActivity.class );
                Bundle b = getIntent().getExtras();
                if(b!=null)//checking if Boundle in extras is not null(if exists)
                    intent.putExtras(b);//adding existing bouble to new intent
                startActivity(intent);
            }
        });

        populateTaskList();
        populateListView();
        registerClickCallback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateTaskList();
        populateListView();
    }

    private void populateTaskList() {
        DatabaseTasks dbTasks = new DatabaseTasks(this);
        myTasks.clear();
        //Cursor cursor = dbTasks.getOrderedData();
        Bundle b = getIntent().getExtras();
        Cursor cursor;
        if(b!=null)
            cursor = dbTasks.getDayTasks(b.getString("date"));
        else
        cursor = dbTasks.getDayTasks(Dates.getTodayDate());

        TextView tvInfo = (TextView)findViewById(R.id.tv_info);
        if(cursor.getCount()==0)
        {
            tvInfo.setText("Zadania Dnia\n(Aktualnie nie masz zadania ze wskazaną datą)");
        }else
            tvInfo.setText("Zadania Dnia");


            //Toast.makeText(getApplicationContext(),"Aktualnie nie masz zadania ze wskazaną datą",Toast.LENGTH_SHORT).show();

        while (cursor.moveToNext())
        {
            boolean isDone=false;
            if(Integer.parseInt(cursor.getString(5))==1)
                isDone=true;
            myTasks.add(new Task(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),isDone));
        }

    }

    private void populateListView() {
        ArrayAdapter<Task> adapter = new MyListTaskAdapter();
        ListView list = (ListView)findViewById(R.id.lv_tasks);
        list.setAdapter(adapter);
    }


    private void registerClickCallback() {
        ListView list = (ListView)findViewById(R.id.lv_tasks);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task clickedTask=myTasks.get(position);
                deletingTaskPopUp(clickedTask.getIdTask());
                return false;
            }
        });
    }

    private void deletingTaskPopUp(final int idToDelete)
    {
        //Creating dialog interface which we need to set functions on alert dialog buttons
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //using switch where 'which' means which button was presed
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        DatabaseTasks db = new DatabaseTasks(getApplicationContext());
                        db.deleteTaskWithId(idToDelete);
                        populateTaskList();
                        populateListView();
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
        builder.setMessage("Czy chcesz usunąć to zadanie ?").setNegativeButton("Nie", dialogClickListener)
                .setPositiveButton("Tak", dialogClickListener)
                .show();
    }

    private class MyListTaskAdapter extends ArrayAdapter<Task>
    {
        public MyListTaskAdapter()
        {
            super(DayPlanActivity.this, R.layout.task_items, myTasks);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            //make sure that given View is not null
            if(itemView==null)
            {
                itemView = getLayoutInflater().inflate(R.layout.task_items,parent,false);
            }

           final Task currentTask = myTasks.get(position);//we take the actual position in tasklist

            //Fill the name
            TextView name = (TextView)itemView.findViewById(R.id.item_tv_nazwa);
            name.setText(currentTask.getNazwa());

            //Fill the date
            TextView date = (TextView)itemView.findViewById(R.id.item_tv_date);
            date.setText(currentTask.getData());

            //Fill the time
            TextView time= (TextView)itemView.findViewById(R.id.item_tv_time);
            time.setText(currentTask.getCzas()+"");

            final CheckBox isDone= (CheckBox) itemView.findViewById(R.id.item_cb_czyZrobione);
                isDone.setChecked(currentTask.isCzyZrobione());
            isDone.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DatabaseTasks db = new DatabaseTasks(getApplicationContext());
                    if(isDone.isChecked())
                        db.setDoneTask(Integer.parseInt(currentTask.getIdTask()+""),1);
                    else
                        db.setDoneTask(Integer.parseInt(currentTask.getIdTask()+""),0);
                }
            });

            return itemView;
        }
    }
}
