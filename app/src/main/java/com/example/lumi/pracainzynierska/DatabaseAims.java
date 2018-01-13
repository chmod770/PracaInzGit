package com.example.lumi.pracainzynierska;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lumi on 11.01.2018.
 */

public class DatabaseAims extends SQLiteOpenHelper{
    public static  final String database_name = "Taski";
    public static  final String database_table = "Aims";
    public DatabaseAims(Context context) {
        super(context, database_name, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + database_table + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " UserID INTEGER," +
                " Nazwa TEXT," +
                " DataDo TEXT," +
                " Opis TEXT," +
                " Kategoria TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +database_table);
        onCreate(db);
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

        if(db.insert(database_table, null, cv)==-1)
            return false;
        return true;

    }

}
