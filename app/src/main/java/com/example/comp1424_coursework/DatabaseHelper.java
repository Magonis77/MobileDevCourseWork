package com.example.comp1424_coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "HIKE_APP.DB";
    static final int DATABASE_VERSION = 1;
    Context context;
    static final String DATABASE_TABLE = "HIKES";
    static final String HIKE_ID = "_ID";
    static final String HIKE_NAME = "hike_name";
    static final String HIKE_LOCATION = "hike_location";
    static final String HIKE_DATE = "hike_date";
    static final String HIKE_PARKING = "hike_parking";
    static final String HIKE_LENGTH = "hike_length";
    static final String HIKE_DIFFICULTY = "hike_difficulty";
    static final String HIKE_DESC = "hike_desc";

    private SQLiteDatabase database;

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    " %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT)",
            DATABASE_TABLE, HIKE_ID, HIKE_NAME, HIKE_LOCATION,HIKE_DATE,
            HIKE_PARKING,HIKE_LENGTH,HIKE_DIFFICULTY,HIKE_DESC
    );


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        Log.w(this.getClass().getName(), DATABASE_TABLE +
                " database upgrade to version " + newVersion + " - old data lost");
        onCreate(db);
    }

    public long InsertDetails(String hike_name, String hike_location, String hike_date, String hike_parking,
                              String hike_length, String hike_difficulty, String hike_desc){
        ContentValues rowValues = new ContentValues();

        rowValues.put(HIKE_NAME, hike_name);
        rowValues.put(HIKE_LOCATION, hike_location);
        rowValues.put(HIKE_DATE, hike_date);
        rowValues.put(HIKE_PARKING, hike_parking);
        rowValues.put(HIKE_LENGTH, hike_length);
        rowValues.put(HIKE_DIFFICULTY, hike_difficulty);
        rowValues.put(HIKE_DESC, hike_desc);

        return  database.insertOrThrow(DATABASE_TABLE, null, rowValues);
    }

    public String getDetails() {
        Cursor results = database.query("HIKES", new String[] {"_ID","hike_name","hike_location",
                        "hike_date","hike_parking","hike_length","hike_difficulty","hike_desc"},
                null,null,null,null,"_ID");
        String resultText = "";

        results.moveToFirst();
        while(!results.isAfterLast()) {
            int id = results.getInt(0);
            String hike_name = results.getString(1);
            String hike_location = results.getString(2);
            String hike_date = results.getString(3);
            String hike_parking = results.getString(4);
            String hike_length = results.getString(5);
            String hike_difficulty = results.getString(6);
            String hike_desc = results.getString(7);

            resultText += id + " " + hike_name + " " + hike_location + " "
                    + hike_date + " " + hike_parking + " " + hike_length
                    + " " + hike_difficulty + " " + hike_desc + "\n";

            results.moveToNext();
        }
        return resultText;
    }

    public ArrayList<Hikes> getHikes(){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Hikes> hike = new ArrayList<Hikes>();
        while(cursor.moveToNext()){
            Hikes hikes = new Hikes();
            hikes.set_id(cursor.getInt(0));
            hikes.set_hikename(cursor.getString(1));
            hikes.set_hikelocation(cursor.getString(2));
            hikes.set_hikedate(cursor.getString(3));
            hikes.set_hikeparking(cursor.getString(4));
            hikes.set_hikelenght(cursor.getString(5));
            hikes.set_hikedifficulty(cursor.getString(6));
            hikes.set_hikedesc(cursor.getString(7));
            hike.add(hikes);
        }
        cursor.close();
        db.close();
        return hike;
    }
}

