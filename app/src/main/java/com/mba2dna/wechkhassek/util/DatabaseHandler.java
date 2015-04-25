package com.mba2dna.wechkhassek.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mba2dna.wechkhassek.model.Artisan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "wechkhassek";
 
    // Login table name
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_ARTISAN = "artisan";
    private static final String TABLE_Tutorial = "tutorial";
 
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        String CREATE_ARTISAN_TABLE = "CREATE TABLE " + TABLE_ARTISAN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + "spe TEXT,"
                + "adr TEXT,"
                + "tele TEXT,"
                + "lat TEXT,"
                + "lang TEXT,"
                + "Calls TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_ARTISAN_TABLE);
        String CREATE_TUTORIAL_TABLE = "CREATE TABLE " + TABLE_Tutorial + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_TUTORIAL_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Tutorial);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        	
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
    public void addArtisan(String name, String spe, String adr, String tele,String lat, String lang, String Calls) {
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put("spe", spe); // Email
        values.put("adr", adr); // Email
        values.put("tele", tele); // Email
        values.put("lat", lat); // Email
        values.put("lang", lang); // Email
        values.put("Calls", Calls); // Email
        values.put(KEY_CREATED_AT, dateFormat.format(date).toString()); // Created At
       // Toast.makeText(getActivity(), lat.getText().toString()+" SUCCESS "+lang.getText().toString(), Toast.LENGTH_LONG).show();
      // Log.v("lat", " lat : "+lat  + "  lang : "+lang);
        // Inserting Row
        db.insert(TABLE_ARTISAN, null, values);
        db.close(); // Closing database connection
    }
    public void addTutorial() {
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, "moh"); // Name
        values.put(KEY_CREATED_AT, dateFormat.format(date).toString()); // Created At
        // Inserting Row
        db.insert(TABLE_Tutorial, null, values);
        db.close(); // Closing database connection
    }
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
   // getAllArtisans()
    // Getting All Contacts
 public List<Artisan> getAllArtisans() {
    List<Artisan> ArtisantList = new ArrayList<Artisan>();
    // Select All Query
    String selectQuery = "SELECT DISTINCT "+KEY_NAME+",spe,adr,tele,lat,lang,Calls  FROM " + TABLE_ARTISAN +" ORDER BY id DESC LIMIT 15";
 
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
 
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
        	Artisan Artisan = new Artisan();
        //	Artisan.setID(Integer.parseInt(cursor.getString(0)));
        	Artisan.setName(cursor.getString(0));
        	Artisan.setspecialite(cursor.getString(1));
        	Artisan.setAdress(cursor.getString(2));
        	Artisan.setTele(cursor.getString(3));
        	Artisan.setLat(cursor.getString(4));
        	Artisan.setLang(cursor.getString(5));
        	Artisan.setCalls(cursor.getString(6));
            // Adding contact to list
            ArtisantList.add(Artisan);
        } while (cursor.moveToNext());
    }
 
    // return contact list
    return ArtisantList;
}
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    public int getSession() {
        String countQuery = "SELECT  * FROM " + TABLE_Tutorial;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;
    }
    public int DeleteRowCount(Integer coun ) {
        String countQuery = "SELECT  * FROM " + TABLE_ARTISAN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        if(rowCount > coun){
        	int s = rowCount-coun;
        	SQLiteDatabase db2 = this.getWritableDatabase();
        	String Query = "DELETE FROM " + TABLE_ARTISAN;
        	//db2.delete(TABLE_ARTISAN, whereClause, whereArgs)
        	db2.rawQuery(Query, null);
        }
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
    public boolean deleteTitle(String name) 
    {
    	SQLiteDatabase db2 = this.getWritableDatabase();
        return db2.delete(TABLE_ARTISAN, KEY_NAME + "=" + name, null) > 0;
    }
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.delete(TABLE_ARTISAN, null, null);
        db.close();
    }
 
}