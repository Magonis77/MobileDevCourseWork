package com.example.comp1424_coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    //define all Database strings.
    static final String DATABASE_NAME = "HIKE_APP.DB";
    static final int DATABASE_VERSION = 1;
    Context context;
    static final String HIKES_TABLE = "HIKES";
    static final String HIKE_ID = "hike_id";
    static final String HIKE_NAME = "hike_name";
    static final String HIKE_LOCATION = "hike_location";
    static final String HIKE_DATE = "hike_date";
    static final String HIKE_PARKING = "hike_parking";
    static final String HIKE_LENGTH = "hike_length";
    static final String HIKE_DIFFICULTY = "hike_difficulty";
    static final String HIKE_WEATHER = "hike_weather";
    static final String HIKE_HEARTRATE = "hike_degree";
    static final String HIKE_DESC = "hike_desc";



    static final String OBSERVATION_TABLE = "Observations";
    static final String OBSERVATION_ID = "obs_ID";
    static final String OBSERVATION_HIKE_ID = "hike_id_fk";
    static final String OBSERVATION_NAME = "observation_name";
    static final String OBSERVATION_TIME = "observation_time";
    static final String OBSERVATION_COMMENTS = "observation_comments";

    private SQLiteDatabase database;

    //creates the database table
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    " %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT)",
            HIKES_TABLE, HIKE_ID, HIKE_NAME, HIKE_LOCATION,HIKE_DATE,
            HIKE_PARKING,HIKE_LENGTH,HIKE_DIFFICULTY,HIKE_WEATHER,HIKE_HEARTRATE,HIKE_DESC
    );

    //creates observation database table
    private static final String DATABASE_CREATE_OBSERVATIONS = String.format(
            "CREATE TABLE %s (" +
                    " %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " FOREIGN KEY(%s) REFERENCES HIKES(_ID))",
            OBSERVATION_TABLE, OBSERVATION_ID, OBSERVATION_NAME, OBSERVATION_TIME,OBSERVATION_COMMENTS, HIKE_ID
    );
    //Creates Observation order table
    String createOrderTable = "create table " + OBSERVATION_TABLE +
            "(ObservationID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "observation_name TEXT,"+
            "observation_time TEXT,"+
            "observation_comments TEXT," +
            "hike_id_fk TEXT," +
            "FOREIGN KEY(hike_id_fk) REFERENCES HIKES(hike_id));";

    //defines database Helper
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        db.execSQL(createOrderTable);
    }

    @Override //Upgrades the database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HIKES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OBSERVATION_TABLE);
        Log.w(this.getClass().getName(), HIKES_TABLE + OBSERVATION_TABLE +
                " database upgrade to version " + newVersion + " - old data lost");
        onCreate(db);
    }
    //inserts the Hike into database
    public long InsertDetails(String hike_name, String hike_location, String hike_date, String hike_parking,
                              String hike_length, String hike_difficulty,String hike_weather, String hike_heartrate, String hike_desc){
        ContentValues rowValues = new ContentValues();

        rowValues.put(HIKE_NAME, hike_name);
        rowValues.put(HIKE_LOCATION, hike_location);
        rowValues.put(HIKE_DATE, hike_date);
        rowValues.put(HIKE_PARKING, hike_parking);
        rowValues.put(HIKE_LENGTH, hike_length);
        rowValues.put(HIKE_DIFFICULTY, hike_difficulty);
        rowValues.put(HIKE_WEATHER, hike_weather);
        rowValues.put(HIKE_HEARTRATE, hike_heartrate);
        rowValues.put(HIKE_DESC, hike_desc);

        return  database.insertOrThrow(HIKES_TABLE, null, rowValues);
    }
    //inserts observations into the database.
    public long InsertObservations(String Obs_name, String obs_time, String obs_comment, String strhikeid){
        ContentValues rowValues = new ContentValues();

        rowValues.put(OBSERVATION_NAME, Obs_name);
        rowValues.put(OBSERVATION_TIME, obs_time);
        rowValues.put(OBSERVATION_COMMENTS, obs_comment);
        rowValues.put(OBSERVATION_HIKE_ID, Integer.valueOf(strhikeid));


        return  database.insertOrThrow(OBSERVATION_TABLE, null, rowValues);
    }
    //gets the hike details
    public String getDetails() {
        Cursor results = database.query("Observations", new String[] {"hike_id","hike_name","hike_location",
                        "hike_date","hike_parking","hike_length","hike_difficulty","hike_weather","hike_heartrate","hike_desc"},
                null,null,null,null,"hike_id");
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
            String hike_weather = results.getString(7);
            String hike_degree = results.getString(8);
            String hike_desc = results.getString(9);

            resultText += id + " " + hike_name + " " + hike_location + " "
                    + hike_date + " " + hike_parking + " " + hike_length
                    + " " + hike_difficulty + " " + hike_weather + " "
                    + hike_degree + " " + hike_desc + "\n";

            results.moveToNext();
        }
        return resultText;
    }
    //gets observation details from database
    public String getOBSDetails() {
        Cursor results = database.query("Observations", new String[] {"obs_ID","observation_name","observation_time",
                        "observation_comments"},
                null,null,null,null,"obs_ID");
        String resultText = "";

        results.moveToFirst();
        while(!results.isAfterLast()) {
            int id = results.getInt(0);
            String OBS_name = results.getString(1);
            String OBS_time = results.getString(2);
            String OBS_comments = results.getString(3);

            resultText += id + " " + OBS_name + " " + OBS_time + " "
                    + OBS_comments + "\n";

            results.moveToNext();
        }
        return resultText;
    }
    //gets observations
    public ArrayList<Observations> getObservations(){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + OBSERVATION_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Observations> Observations = new ArrayList<Observations>();
        while(cursor.moveToNext()){
            Observations obs = new Observations();
            obs.set_id(cursor.getInt(0));
            obs.set_obsname(cursor.getString(1));
            obs.set_obstime(cursor.getString(2));
            obs.set_obscomment(cursor.getString(3));
            Observations.add(obs);
        }
        cursor.close();
        db.close();
        return Observations;
    }
    //gets all hikes from db
    public ArrayList<Hikes> getHikes(){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + HIKES_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Hikes> hikelist = new ArrayList<Hikes>();
        while(cursor.moveToNext()){
            Hikes hikes = new Hikes();
            hikes.set_id(cursor.getInt(0));
            hikes.set_hikename(cursor.getString(1));
            hikes.set_hikelocation(cursor.getString(2));
            hikes.set_hikedate(cursor.getString(3));
            hikes.set_hikeparking(cursor.getString(4));
            hikes.set_hikelenght(cursor.getString(5));
            hikes.set_hikedifficulty(cursor.getString(6));
            hikes.set_hikeweather(cursor.getString(7));
            hikes.set_hikeheartrate(cursor.getString(8));
            hikes.set_hikedesc(cursor.getString(9));
            hikelist.add(hikes);
        }
        cursor.close();
        db.close();
        return hikelist;
    }
    //amends the details in hike
    public long AmendDetails(String strid, String strName, String strLocation, String strDate, String strParking, String strLenght, String strDifficulty,String strWeather, String strHeartRate, String strDesc) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(HIKE_NAME, strName);
        rowValues.put(HIKE_LOCATION, strLocation);
        rowValues.put(HIKE_DATE, strDate);
        rowValues.put(HIKE_PARKING, strParking);
        rowValues.put(HIKE_LENGTH, strLenght);
        rowValues.put(HIKE_DIFFICULTY, strDifficulty);
        rowValues.put(HIKE_WEATHER, strWeather);
        rowValues.put(HIKE_HEARTRATE, strHeartRate);
        rowValues.put(HIKE_DESC, strDesc);

        int id = Integer.parseInt(strid);

        return  database.update(HIKES_TABLE, rowValues, "hike_id = " + id, null);
    }
    //deletes a hike from DB
    public long DeleteEntry(String strid) {
        int id = Integer.parseInt(strid);

        return  database.delete(HIKES_TABLE, "hike_id = " + id, null);
    }

    //deletes all of the database hikes
    public void deleteALL() {

        database.delete(HIKES_TABLE, null, null);
    }

    //Gets all observations by specific hike
    public ArrayList<Observations> getObservationsbyID(String GetID) {
        Cursor results = database.rawQuery("Select * from Observations where hike_id_fk=" + GetID + "", null);
        ArrayList<Observations> observationslist = new ArrayList<Observations>();
        while(results.moveToNext()){
            Observations observations = new Observations();
            observations.set_id(results.getInt(0));
            observations.set_obsname(results.getString(1));
            observations.set_obstime(results.getString(2));
            observations.set_obscomment(results.getString(3));

            observationslist.add(observations);
        }
        results.close();
        database.close();
        return observationslist;
    }

    //searches the db for name of hike
    public ArrayList<Hikes> SearchDBName(String strname) {
        Cursor cursor = database.rawQuery("SELECT * FROM HIKES where hike_name like '%"+strname+"%'" , null);
        ArrayList<Hikes> hikelist = new ArrayList<Hikes>();
        while(cursor.moveToNext()){
            Hikes hikes = new Hikes();
            hikes.set_id(cursor.getInt(0));
            hikes.set_hikename(cursor.getString(1));
            hikes.set_hikelocation(cursor.getString(2));
            hikes.set_hikedate(cursor.getString(3));
            hikes.set_hikeparking(cursor.getString(4));
            hikes.set_hikelenght(cursor.getString(5));
            hikes.set_hikedifficulty(cursor.getString(6));
            hikes.set_hikeweather(cursor.getString(7));
            hikes.set_hikeheartrate(cursor.getString(8));
            hikes.set_hikedesc(cursor.getString(9));
            hikelist.add(hikes);
        }
        cursor.close();
        database.close();
        return hikelist;
    }
    //Searches Hikes by location
    public ArrayList<Hikes> SearchDBLoc(String strname) {
        Cursor cursor = database.rawQuery("SELECT * FROM HIKES where hike_location like '%"+strname+"%'" , null);
        ArrayList<Hikes> hikelist = new ArrayList<Hikes>();
        while(cursor.moveToNext()){
            Hikes hikes = new Hikes();
            hikes.set_id(cursor.getInt(0));
            hikes.set_hikename(cursor.getString(1));
            hikes.set_hikelocation(cursor.getString(2));
            hikes.set_hikedate(cursor.getString(3));
            hikes.set_hikeparking(cursor.getString(4));
            hikes.set_hikelenght(cursor.getString(5));
            hikes.set_hikedifficulty(cursor.getString(6));
            hikes.set_hikeweather(cursor.getString(7));
            hikes.set_hikeheartrate(cursor.getString(8));
            hikes.set_hikedesc(cursor.getString(9));
            hikelist.add(hikes);
        }
        cursor.close();
        database.close();
        return hikelist;
    }
    //Searches hikes by lenght
    public ArrayList<Hikes> SearchDBLenght(String strname) {
        Cursor cursor = database.rawQuery("SELECT * FROM HIKES where hike_length like '%"+strname+"%'" , null);
        ArrayList<Hikes> hikelist = new ArrayList<Hikes>();
        while(cursor.moveToNext()){
            Hikes hikes = new Hikes();
            hikes.set_id(cursor.getInt(0));
            hikes.set_hikename(cursor.getString(1));
            hikes.set_hikelocation(cursor.getString(2));
            hikes.set_hikedate(cursor.getString(3));
            hikes.set_hikeparking(cursor.getString(4));
            hikes.set_hikelenght(cursor.getString(5));
            hikes.set_hikedifficulty(cursor.getString(6));
            hikes.set_hikeweather(cursor.getString(7));
            hikes.set_hikeheartrate(cursor.getString(8));
            hikes.set_hikedesc(cursor.getString(9));
            hikelist.add(hikes);
        }
        cursor.close();
        database.close();
        return hikelist;
    }
    //Searches DB hikes by date
    public ArrayList<Hikes> SearchDBDate(String strname) {
        Cursor cursor = database.rawQuery("SELECT * FROM HIKES where hike_date like '%"+strname+"%'" , null);
        ArrayList<Hikes> hikelist = new ArrayList<Hikes>();
        while(cursor.moveToNext()){
            Hikes hikes = new Hikes();
            hikes.set_id(cursor.getInt(0));
            hikes.set_hikename(cursor.getString(1));
            hikes.set_hikelocation(cursor.getString(2));
            hikes.set_hikedate(cursor.getString(3));
            hikes.set_hikeparking(cursor.getString(4));
            hikes.set_hikelenght(cursor.getString(5));
            hikes.set_hikedifficulty(cursor.getString(6));
            hikes.set_hikeweather(cursor.getString(7));
            hikes.set_hikeheartrate(cursor.getString(8));
            hikes.set_hikedesc(cursor.getString(9));
            hikelist.add(hikes);
        }
        cursor.close();
        database.close();
        return hikelist;
    }
}

