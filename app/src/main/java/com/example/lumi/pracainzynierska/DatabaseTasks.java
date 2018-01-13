package com.example.lumi.pracainzynierska;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DatabaseTasks extends SQLiteOpenHelper {
    public static  final String database_name = "Taski";
    public static  final String table_tasks = "Tasks";
    public static  final String table_aims = "Aims";
    public DatabaseTasks(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +table_tasks+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " UserID INTEGER," +
                " Nazwa TEXT," +
                " Data TEXT," +
                " Czas INTEGER," +
                " CzyZrobione INTEGER,"+
                " Priorytet INTEGER,"+
                " CelID INTEGER);");

        db.execSQL("CREATE TABLE " + table_aims + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " UserID INTEGER," +
                " Nazwa TEXT," +
                " DataDo TEXT," +
                " Opis TEXT," +
                " Kategoria TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +table_tasks);
        db.execSQL("DROP TABLE IF EXISTS " +table_aims);
        onCreate(db);
    }

    public boolean insertTask(int userID ,String nazwa, String data, int czas, int czyZrobione,int priorytet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("UserID",userID);
        cv.put("Nazwa",nazwa);
        cv.put("Data",data);
        cv.put("Czas",czas);
        cv.put("CzyZrobione",czyZrobione);
        cv.put("Priorytet",priorytet);
        cv.put("CelID",1);
        if(db.insert(table_tasks, null, cv)==-1)
            return false;
        return true;
    }

    public void setDoneTask(int taskID,int czyZrobione)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CzyZrobione",czyZrobione);
        db.update(table_tasks, cv, "ID=" + taskID, null);
    }

    public SQLiteCursor getTasks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_tasks, null);
        return kursor;
    }
    public SQLiteCursor getOrderedData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_tasks+ " ORDER BY date(Data) DESC", null);
        return kursor;
    }
    public SQLiteCursor getDayTasks(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_tasks+ " WHERE Data='"+date+"'", null);
        return kursor;
    }

    public void deleteAllTasks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_tasks);
    }


    public boolean insertAim(int userID,String nazwa,String opis, String dataDo, String Kategoria)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("UserID",userID);
        cv.put("Nazwa",nazwa);
        cv.put("DataDo",dataDo);
        cv.put("Kategoria",Kategoria);
        cv.put("Opis", opis);

        if(db.insert(table_aims, null, cv)==-1)
            return false;
        return true;

    }

    public SQLiteCursor getAims()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_aims, null);
        return kursor;
    }

}
