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
import java.util.Locale;

////import net.sqlcipher.database.SQLiteFullException;
////import net.sqlcipher.CursorIndexOutOfBoundsException;
//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;


public class VehicleTypeDao extends SQLiteOpenHelper {

    private static final String TAG = "VehicleTypeDao";

    private static final String TABLE_NAME = "vehicle_type_table";
    private static final String CVT_ID = "VT_ID";
    private static final String CVT_TYPE = "VT_TYPE";
    private final Context context;


    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public VehicleTypeDao(Context context) {
        super(context, TABLE_NAME, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + CVT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CVT_TYPE + " TEXT)";


        db.execSQL(createTable);

        Resources res = context.getResources();
        String valuez = res.getString(R.string.default_car_value);

        setDefaultData(db, valuez);
        valuez = res.getString(R.string.car_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.pickup_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.suv_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.motorcycle_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.schoolbus_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.bus_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.commercialtruck_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.semitruck_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.semitrailer_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.trailer_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.bicycle_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.scooter_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.moped_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.carriage_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.cart_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.rickshaw_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.bicyclerickshaw_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.horse_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.otheranimal_value);
        setDefaultData(db, valuez);
        valuez = res.getString(R.string.other_value);
        setDefaultData(db, valuez);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean setDefaultData(SQLiteDatabase db, String item) {
        item = utils.extractCharacter(item);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CVT_TYPE, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    public boolean addData(String item) {
        item = utils.extractCharacter(item);

    //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CVT_TYPE, item);

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
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(query, null);
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
     * Returns only the VT_ID that matches the VT_TYPE passed in
     *
     * @param VT_TYPE
     * @return
     */
    public Cursor getVTID(String VT_TYPE) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + CVT_TYPE + " = '" + VT_TYPE + "'";
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

    public void updateData(String VT_ID, String VT_TYPE) {
        VT_TYPE = utils.extractCharacter(VT_TYPE);
    //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_NAME +
                " SET " + CVT_TYPE + " = '" + VT_TYPE + "'"
                + "WHERE " + CVT_ID + " = '" + VT_ID + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }


    public void deleteVT_TYPE(String VT_ID, String VT_TYPE) {
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + CVT_ID + " = '" + VT_ID + "'";

        Log.d(TAG, "deleteVT_TYPE: query: " + query);
        Log.d(TAG, "deleteVT_TYPE: Deleting " + CVT_TYPE + " from database.");
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);
    }

    public void deleteALL() {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);

        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + CVT_ID + " != " + 0;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);
        String deviceLocale = Locale.getDefault().getLanguage();
        String en = "en";
        String es = "es";
        String fr = "fr";
        String ru = "ru";
        String zh = "zh";
        String de = "de";
        if (deviceLocale.equals(en)) {

        }
        if (deviceLocale.equals(es)) {

        }
        if (deviceLocale.equals(de)) {


        }
        if (deviceLocale.equals(fr)) {
        }
        if (deviceLocale.equals(ru)) {


        }
        if (deviceLocale.equals(zh)) {


        }


    }
    public void closeAll() {
        dbR.close();
        dbW.close();

    }
}
