package com.example.lumi.pracainzynierska;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseTasks extends SQLiteOpenHelper {
    public static  final String database_name = "Taski";
    public static  final String table_tasks = "Tasks";
    public static  final String table_aims = "Aims";
    public static  final String table_users = "Users";

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
        db.execSQL("CREATE TABLE " + table_users + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Imie TEXT, " +
                "GodzinaPrzypomnienia TEXT, " +
                "MinPriorytet INTEGER, " +
                "CzyWlaczane INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +table_tasks);
        db.execSQL("DROP TABLE IF EXISTS " +table_aims);
        db.execSQL("DROP TABLE IF EXISTS " +table_users);
        onCreate(db);
    }

    public boolean insertTask(int userID ,String nazwa, String data, int czas, int priorytet,int celID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("UserID",userID);
        cv.put("Nazwa",nazwa);
        cv.put("Data",data);
        cv.put("Czas",czas);
        cv.put("CzyZrobione",0);
        cv.put("Priorytet",priorytet);
        cv.put("CelID",celID);
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
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_tasks, null);
        return cursor;
    }
    public SQLiteCursor getOrderedData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_tasks+ " ORDER BY date(Data) DESC", null);
        return cursor;
    }
    public SQLiteCursor getDayTasks(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_tasks+ " WHERE Data='"+date+"'", null);
        return cursor;
    }

    public void deleteAllTasks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_tasks);
    }

    public void deleteTaskWithId(int idToDelete) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_tasks + " WHERE ID='"+idToDelete+"'");
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
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_aims, null);
        return cursor;
    }

    public SQLiteCursor getAimWithName(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_aims+" WHERE Nazwa='"+name+"'", null);
        return cursor;
    }

    public int getIdAimWithName(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_aims+" WHERE Nazwa='"+name+"'", null);
        if(cursor.moveToNext())
        {
            return Integer.parseInt(cursor.getString(0));
        }
        return 0;
    }

    public boolean ifAimWithNameExits(String name)
    {
        SQLiteCursor cursor =this.getAimWithName(name);
        if(cursor.getCount()>0)
            return true;
        return false;
    }

    public void deleteAllAims()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_aims);
    }

    public void deleteAimWithIdAndTasks(int idToDelete) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + table_aims + " WHERE ID='"+idToDelete+"'");
            db.execSQL("DELETE FROM " + table_tasks + " WHERE CelID='"+idToDelete+"'");
    }

    public boolean insertUser(String imie,String godzinaPrzypomnienia, int minPriorytet, int czyWlaczane)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Imie",imie);
        cv.put("GodzinaPrzypomnienia",godzinaPrzypomnienia);
        cv.put("MinPriorytet",minPriorytet);
        cv.put("CzyWlaczane",czyWlaczane);
        if(db.insert(table_users, null, cv)==-1)
            return false;
        return true;
    }

    public boolean insertUser(String imie)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Imie",imie);
        cv.put("GodzinaPrzypomnienia","13:00");
        cv.put("MinPriorytet",5);
        cv.put("CzyWlaczane",0);

        if(db.insert(table_users, null, cv)==-1)
            return false;
        return true;
    }

    public void updateUserName(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + table_users + " SET Imie='"+name+"'");
    }

    public SQLiteCursor getUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + table_users, null);
        return cursor;
    }

    public boolean deleteAllUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ table_users);
        return false;
    }

    public void updateReminderData(int minPriotity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("MinPriorytet",minPriotity-1);
        db.update(table_users,cv,"",null);

    }

    public void updateReminderHour(String hour) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("GodzinaPrzypomnienia",hour);
        db.update(table_users,cv,"",null);

    }

    public void setIsReminderOn(boolean doIsReminderOn)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CzyWlaczane",doIsReminderOn);
        db.update(table_users, cv, "", null);
    }

    public int numberTasksInCategory(String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery
                ("SELECT * FROM "+table_aims+" ta INNER JOIN "+table_tasks+" tt ON ta.ID=tt.CelID WHERE ta.Kategoria='"+category+"'"
                        , null);
        return cursor.getCount();
    }

    public int numberTasksInCategoryOther()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM "+table_tasks+" WHERE CelID=0",null);
        return cursor.getCount();
    }

    public int numberAimsInCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery
                ("SELECT * FROM "+table_aims+" WHERE Kategoria='"+category+"'", null);
        return cursor.getCount();
    }

    public int numberTodayDoneTasks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery
                ("SELECT * FROM "+table_tasks+" WHERE Data='"+Dates.getTodayDate()+"' AND CzyZrobione=1", null);
        return cursor.getCount();
    }

    public int numberTodayTasks()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery
                ("SELECT * FROM "+table_tasks+" WHERE Data='"+Dates.getTodayDate()+"'", null);
        return cursor.getCount();
    }

}