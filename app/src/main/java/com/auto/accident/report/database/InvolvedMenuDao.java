package com.auto.accident.report.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.auto.accident.report.R;
import com.auto.accident.report.util.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by myron on 12/2/2018.
 */

public class InvolvedMenuDao extends SQLiteOpenHelper {
    private static final String TAG = "InvolvedMenuDao";

    private static final String TABLE_NAME = "involved_menu_table";
    private static final String CIM_ID = "IM_ID";
    private static final String CIM_TYPE = "IM_TYPE";
    private static final String CIM_DESC = "IM_DESC";
    private static final String CIM_PHON1 = "IM_PHON1";
    private static final String CIM_EMAIL = "IM_EMAIL";
    private static final String CIM_URL = "IM_URL";    // Url or Class Name
    private static final String CIM_ICONURL = "IM_ICONURL";
    private static final String CIM_CONFURL = "IM_CONFURL";
    private final Context context;
    private String DA_ID_STR;
    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public InvolvedMenuDao(Context context) {
        super(context, TABLE_NAME, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
        dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + CIM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIM_TYPE + " TEXT NOT NULL DEFAULT '', " +
                CIM_DESC + " TEXT NOT NULL DEFAULT '', " +
                CIM_PHON1 + " TEXT NOT NULL DEFAULT '', " +
                CIM_EMAIL + " TEXT NOT NULL DEFAULT '', " +
                CIM_URL + " TEXT NOT NULL DEFAULT '', " +
                CIM_ICONURL + " TEXT NOT NULL DEFAULT '', " +
                CIM_CONFURL + " TEXT NOT NULL DEFAULT '')";


        db.execSQL(createTable);
        Resources res = context.getResources();
        String keyz = res.getString(R.string.involved_party_key);
        String valuez = res.getString(R.string.involved_party_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.involved_vehicle_key);
        valuez = res.getString(R.string.involved_vehicle_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.broadcast_key);
        valuez = res.getString(R.string.broadcast_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.report_key);
        valuez = res.getString(R.string.report_value);
        setDefaultData(db, keyz, valuez);
       

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean setDefaultData(SQLiteDatabase db, String IM_TYPE, String IM_DESC) {
        IM_TYPE = utils.extractCharacter(IM_TYPE);
        IM_DESC = utils.extractCharacter(IM_DESC);
        String IM_PHON1 = "";
        String  IM_EMAIL = "";
        String IM_URL = "";
        String  IM_ICONURL = "";
        String  IM_CONFURL = "";

        ContentValues contentValues = new ContentValues();
        contentValues.put(CIM_TYPE, IM_TYPE);
        contentValues.put(CIM_DESC, IM_DESC);
        contentValues.put(CIM_PHON1, IM_PHON1);
        contentValues.put(CIM_EMAIL, IM_EMAIL);
        contentValues.put(CIM_URL, IM_URL);
        contentValues.put(CIM_ICONURL, IM_ICONURL);
        contentValues.put(CIM_CONFURL, IM_CONFURL);
        Log.d(TAG, "addData: Adding " + IM_TYPE + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }


    public boolean addData(SQLiteDatabase db, String IM_TYPE, String IM_DESC, String IM_PHON1, String IM_EMAIL, String IM_URL, String IM_ICONURL, String IM_CONFURL) {

        IM_TYPE = utils.extractCharacter(IM_TYPE);
        IM_DESC = utils.extractCharacter(IM_DESC);
        IM_PHON1 = utils.extractCharacter(IM_PHON1);
        IM_EMAIL = utils.extractCharacter(IM_EMAIL);
        IM_URL = utils.extractCharacter(IM_URL);
        IM_ICONURL = utils.extractCharacter(IM_ICONURL);
        IM_CONFURL = utils.extractCharacter(IM_CONFURL);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CIM_TYPE, IM_TYPE);
        contentValues.put(CIM_DESC, IM_DESC);
        contentValues.put(CIM_PHON1, IM_PHON1);
        contentValues.put(CIM_EMAIL, IM_EMAIL);
        contentValues.put(CIM_URL, IM_URL);
        contentValues.put(CIM_ICONURL, IM_ICONURL);
        contentValues.put(CIM_CONFURL, IM_CONFURL);
        Log.d(TAG, "addData: Adding " + IM_TYPE + " to " + TABLE_NAME);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        long result = dbW.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
        // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
        //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_NAME;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        Cursor cursor = dbW.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
            //  cursor.close();
            //    db.close();
        }
        return cursor;
    }

    public List<String> getAllTypes() {
        List<String> types = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                types.add(cursor.getString(1) + " - " + cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //db.close();
        }

        // returning lables
        return types;

    }

    /**
     * Returns only the IM_ID that matches the IM_TYPE passed in
     *
     * @param IM_TYPE
     * @return
     */
  //  private static final String CIM_ID = "IM_ID";
  //  private static final String CIM_TYPE = "IM_TYPE";
  //  private static final String CIM_DESC = "IM_DESC";
  //  private static final String CIM_PHON1 = "IM_PHON1";
  //  private static final String CIM_EMAIL = "IM_EMAIL";
 //   private static final String CIM_URL = "IM_URL";
 //   private static final String CIM_ICONURL = "IM_ICONURL";
  //  private static final String CIM_CONFURL = "IM_CONFURL";
 //   public Cursor getPTID(String IM_TYPE) {
    public Cursor getPTID(String IM_TYPE) {
        //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
        //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT " + CIM_ID + ", " + CIM_TYPE + ", " + CIM_DESC + ", " + CIM_PHON1 + ", " + CIM_EMAIL  + ", " + CIM_URL  +  ", " + CIM_ICONURL  +  ", " + CIM_CONFURL  + " FROM " + TABLE_NAME +
                " WHERE " + CIM_TYPE + " = '" + IM_TYPE + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        Cursor cursor = dbW.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //   db.close();
        }
        return cursor;
    }

    public void updateData(String IM_ID, String IM_TYPE, String IM_DESC) {
        IM_TYPE = utils.extractCharacter(IM_TYPE);
        IM_DESC = utils.extractCharacter(IM_DESC);
        // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
        // SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_NAME +
                " SET " +  CIM_ID + ", " + CIM_TYPE + ", " + CIM_DESC + ", " + CIM_PHON1 + ", " + CIM_EMAIL  + ", " + CIM_URL  +  ", " + CIM_ICONURL  +  ", " + CIM_CONFURL  +
                "'"
                + "WHERE " + CIM_ID + " = '" + IM_ID + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);

    }


    /**
     * Delete from database
     *
     * @param IM_ID
     * @param IM_TYPE
     */
    public void deleteIM_TYPE(String IM_ID, String IM_TYPE) {
        // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
        //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + CIM_ID + " = '" + IM_ID + "'" +
                " AND " + CIM_TYPE + " = '" + IM_TYPE + "'";
        Log.d(TAG, "deleteIM_TYPE: query: " + query);
        Log.d(TAG, "deleteIM_TYPE: Deleting " + CIM_TYPE + " from database.");
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);
    }
    public void closeAll() {
        dbR.close();
        dbW.close();

    }    
    
}
