package com.example.lumi.pracainzynierska;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;

public class User {

    private  int id;
    private String imie;
    private String godzinaPrzypomnienia;
    private int minPriotytet;
    private int czyWlaczane;

    public String getGodzinaPrzypomnienia() {
        return godzinaPrzypomnienia;
    }

    public void setGodzinaPrzypomnienia(String godzinaPrzypomnienia) {
        this.godzinaPrzypomnienia = godzinaPrzypomnienia;
    }

    public int getMinPriotytet() {
        return minPriotytet;
    }

    public void setMinPriotytet(int minPriotytet) {
        this.minPriotytet = minPriotytet;
    }
    public void setMinPriotytet(String minPriotytet) {
        this.minPriotytet = Integer.parseInt(minPriotytet);
    }

    public int getCzyWlaczane() {
        return czyWlaczane;
    }

    public void setCzyWlaczane(int czyWlaczane) {
        this.czyWlaczane = czyWlaczane;
    }
    public void setCzyWlaczane(String czyWlaczane) {
        this.czyWlaczane = Integer.parseInt(czyWlaczane);
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
        SQLiteCursor cursor= db.getUsers();
        if(cursor.getCount()>0)
        {
            cursor.moveToNext();
            this.setId(cursor.getInt(0));
            this.setImie(cursor.getString(1));
            this.setGodzinaPrzypomnienia(cursor.getString(2));
            this.setMinPriotytet(cursor.getString(3));
            this.setCzyWlaczane(cursor.getString(4));
        }else {
            db.insertUser("");
            this.setImie("");
        }
    }

    public void update(String name,Context context)
    {
        DatabaseTasks db= new DatabaseTasks(context);
        this.setImie(name);
        db.updateUserName(name);
    }

}
