package com.example.lumi.pracainzynierska;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLInput;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static  final String database_name = "User";
    public static  final String database_table = "Users";
    public DatabaseHelper(Context context)
    {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + database_table + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Imie TEXT, " +
                "Zadowolenie TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +database_table);
        onCreate(db);
    }

    public boolean insertData(String imie, String nazwisko)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Imie",imie);
        cv.put("Zadowolenie",nazwisko);
        if(db.insert(database_table, null, cv)==-1)
            return false;
        return true;
    }

    public boolean updateOneData(int id, String name, String satisfaction)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + database_table + " SET Imie='" + name+ "'" + " ID="+id);
        db.execSQL("UPDATE " + database_table + " SET Zadowolenie='"+satisfaction+"'"+ " ID="+id);
        return true;
    }

    public boolean updateData(String name, String satisfaction)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + database_table + " SET Imie='"+name+"'");
        db.execSQL("UPDATE " + database_table + " SET Zadowolenie='"+satisfaction+"'");
        return true;
    }

    public SQLiteCursor pobierzDane()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor kursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + database_table, null);
        return kursor;
    }
    public boolean deleteAllUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ database_table);
        return false;
    }
}
