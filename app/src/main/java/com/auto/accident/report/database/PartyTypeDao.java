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

////import net.sqlcipher.database.SQLiteFullException;
////import net.sqlcipher.CursorIndexOutOfBoundsException;
//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;


public class PartyTypeDao extends SQLiteOpenHelper {

    private static final String TAG = "PartyTypeDao";

    private static final String TABLE_NAME = "party_type_table";
    private static final String CPT_ID = "PT_ID";
    private static final String CPT_TYPE = "PT_TYPE";
    private static final String CPT_DESC = "PT_DESC";
    private final Context context;


    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public PartyTypeDao(Context context) {
        super(context, TABLE_NAME, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + CPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CPT_TYPE + " TEXT, " +
                CPT_DESC + " TEXT)";

        db.execSQL(createTable);
        Resources res = context.getResources();
        String keyz = res.getString(R.string.default_key);
        String valuez = res.getString(R.string.default_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.driver_key);
        valuez = res.getString(R.string.driver_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.ownerdriver_key);
        valuez = res.getString(R.string.ownerdriver_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.owner_key);
        valuez = res.getString(R.string.owner_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.passenger_key);
        valuez = res.getString(R.string.passenger_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.officer_key);
        valuez = res.getString(R.string.officer_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.investigator_key);
        valuez = res.getString(R.string.investigator_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.logistics_key);
        valuez = res.getString(R.string.logistics_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.trucking_key);
        valuez = res.getString(R.string.trucking_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.shipper_key);
        valuez = res.getString(R.string.shipper_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.broker_key);
        valuez = res.getString(R.string.broker_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.insurance_key);
        valuez = res.getString(R.string.insurance_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.manufacturer_key);
        valuez = res.getString(R.string.manufacturer_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.roadmaintenance_key);
        valuez = res.getString(R.string.roadmaintenance_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.witness_key);
        valuez = res.getString(R.string.witness_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.commercial_key);
        valuez = res.getString(R.string.commercial_value);
        setDefaultData(db, keyz, valuez);
        keyz = res.getString(R.string.bystander_key);
        valuez = res.getString(R.string.bystander_value);
        setDefaultData(db, keyz, valuez);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean setDefaultData(SQLiteDatabase db, String item, String item2) {
        item = utils.extractCharacter(item);
        item2 = utils.extractCharacter(item2);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CPT_TYPE, item);
        contentValues.put(CPT_DESC, item2);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    public boolean addData(String item, String item2) {
        item = utils.extractCharacter(item);
        item2 = utils.extractCharacter(item2);
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CPT_TYPE, item);
        contentValues.put(CPT_DESC, item2);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
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

     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
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
     * Returns only the PT_ID that matches the PT_TYPE passed in
     *
     * @param PT_TYPE
     * @return
     */
    public Cursor getPTID(String PT_TYPE) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT " + CPT_ID + ", " + CPT_DESC + " FROM " + TABLE_NAME +
                " WHERE " + CPT_TYPE + " = '" + PT_TYPE + "'";
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

    public void updateData(String PT_ID, String PT_TYPE, String PT_DESC) {
        PT_TYPE = utils.extractCharacter(PT_TYPE);
        PT_DESC = utils.extractCharacter(PT_DESC);
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
       // SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_NAME +
                " SET " + CPT_TYPE + " = '" + PT_TYPE + "' , "
                + CPT_DESC + " = '" + PT_DESC + "'"
                + "WHERE " + CPT_ID + " = '" + PT_ID + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }


    /**
     * Delete from database
     *
     * @param PT_ID
     * @param PT_TYPE
     */
    public void deletePT_TYPE(String PT_ID, String PT_TYPE) {
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + CPT_ID + " = '" + PT_ID + "'" +
                " AND " + CPT_TYPE + " = '" + PT_TYPE + "'";
        Log.d(TAG, "deletePT_TYPE: query: " + query);
        Log.d(TAG, "deletePT_TYPE: Deleting " + CPT_TYPE + " from database.");
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
