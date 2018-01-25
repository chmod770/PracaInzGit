package com.example.lumi.pracainzynierska;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetNameActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_new_user_name);

        Button btnOk =(Button)findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        EditText etName = (EditText)findViewById(R.id.et_name);
        if(etName.getText().length()<3)
        {
            Toast.makeText(getApplicationContext(),"Nazwa użytkownika musi się składać z minimum trzech znaków ",Toast.LENGTH_LONG).show();
        }else {
                DatabaseTasks db = new DatabaseTasks(this);
                db.updateUserName(etName.getText().toString());
                finish();
        }
    }
}
