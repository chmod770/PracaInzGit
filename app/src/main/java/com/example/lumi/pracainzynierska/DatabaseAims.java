package com.example.lumi.pracainzynierska;

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
                " Kategoria TEXT," +
                " DataDo TEXT," +
                " Kategoria TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +database_table);
        onCreate(db);
    }

    public void insertAim()
    {

    }

}
