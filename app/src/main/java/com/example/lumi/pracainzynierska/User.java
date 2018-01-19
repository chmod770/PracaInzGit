package com.example.lumi.pracainzynierska;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.widget.Toast;

public class User {

    private  int id;
    private String imie;
    private int zadowolenie;

    public int getZadowolenie() {
        return zadowolenie;
    }

    public void setZadowolenie(int zadowolenie) {
        if(zadowolenie>5||zadowolenie<1) {
            this.zadowolenie = 1;
        }
        else
        this.zadowolenie = zadowolenie;

    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(Context context)
    {
        DatabaseTasks db= new DatabaseTasks(context);
        SQLiteCursor kursor = db.getUsers();
        if(kursor.getCount()>0)
        {
            kursor.moveToNext();
            this.setId(kursor.getInt(0));
            this.setImie(kursor.getString(1));
            this.setZadowolenie(Integer.parseInt(kursor.getString(2)));
        }else {
            db.insertUser("-nowy-","1");
            this.setImie("-nowy-");
            this.setZadowolenie(1);
        }
    }
    public void update(String name, int satisfaction,Context context)
    {
        DatabaseTasks db= new DatabaseTasks(context);
        this.setImie(name);
        this.setZadowolenie(satisfaction);
        db.updateUsers(name, satisfaction+"");
    }

}
