package com.auto.accident.report.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.auto.accident.report.objects.AccidentId;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;
import java.util.List;

import static com.auto.accident.report.util.utils.isNumber;

//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;
//


/**
 * Created by User on 2/28/2017.
 */

public class AccidentIdDao extends SQLiteOpenHelper {

    private static final String TAG = "AccidentIdDao";
    private static final String TABLE_ACCIDENT_ID = "accident_id_table";
    private static final String CAID_ID = "AID_ID";
    private static final String CAID_CCODE = "AID_CCODE";
    private static final String CAID_ADDRESS = "AID_ADDRESS";
    private static final String CAID_CITY = "AID_CITY";
    private static final String CAID_STATE = "AID_STATE";
    private static final String CAID_ZIP = "AID_ZIP";
    private static final String CAID_COUNTY = "AID_COUNTY";
    private static final String CAID_LAT = "AID_LAT";
    private static final String CAID_LON = "AID_LON";
    private static final String CAID_DUID = "AID_DUID";
    private static final String CAID_RDATE = "AID_RDATE";
    private static final String CAID_ADATE = "AID_ADATE";
    private static final String CAID_ATIME = "AID_ATIME";
    private final Context context;
    private AccidentId accidentid;
    private PersistenceObjDao mPersistenceObjDao;
    private String DA_ID_STR;



    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public AccidentIdDao(Context context) {

        super(context, TABLE_ACCIDENT_ID, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
          if (this.dbR == null) {
             dbR = this.getReadableDatabase();
        }

        if (this.dbW == null) {
            dbW = this.getWritableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ACCIDENT_ID + "(" + CAID_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAID_CCODE + " TEXT, " +
                CAID_ADDRESS + " TEXT, " +
                CAID_CITY + " TEXT, " +
                CAID_STATE + " TEXT, " +
                CAID_ZIP + " TEXT, " +
                CAID_COUNTY + " TEXT, " +
                CAID_LAT + " TEXT, " +
                CAID_LON + " TEXT, " +
                CAID_DUID + " INTEGER, " +
                CAID_RDATE + " TEXT, " +
                CAID_ADATE + " TEXT, " +
                CAID_ATIME + " TEXT) ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENT_ID);
        onCreate(db);
    }

    public long addData(String spAID_CCODE, String AID_ADDRESS, String AID_CITY,
                        String AID_STATE, String AID_ZIP, String AID_COUNTY, String AID_LAT,
                        String AID_LON, String tvAID_RDATE, String tieAID_ADATE, String tieAID_ATIME) {
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");

        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int AID_DUID;
        if (isNumber(DA_ID_STR)) {
            AID_DUID = Integer.parseInt(DA_ID_STR);
        } else {
            AID_DUID = 0;
        }
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
     //   SQLiteDatabase db = this.getReadableDatabase();
        //      db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENT_ID);
        //    onCreate(db);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAID_CCODE, spAID_CCODE);
        contentValues.put(CAID_ADDRESS, AID_ADDRESS);
        contentValues.put(CAID_CITY, AID_CITY);
        contentValues.put(CAID_STATE, AID_STATE);
        contentValues.put(CAID_ZIP, AID_ZIP);
        contentValues.put(CAID_COUNTY, AID_COUNTY);
        contentValues.put(CAID_LAT, AID_LAT);
        contentValues.put(CAID_LON, AID_LON);
        contentValues.put(CAID_DUID, AID_DUID);
        contentValues.put(CAID_RDATE, tvAID_RDATE);
        contentValues.put(CAID_ADATE, tieAID_ADATE);
        contentValues.put(CAID_ATIME, tieAID_ATIME);


        Log.d(TAG, "addData: Adding " + tieAID_ADATE + " to " + TABLE_ACCIDENT_ID);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        return dbR.insert(TABLE_ACCIDENT_ID, null, contentValues);
    }


    public Cursor getData() {
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
     //   SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ACCIDENT_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        return dbW.rawQuery(query, null);
    }


    // Getting one AccidentId
    public AccidentId getAccidentId(int AID_ID) {
        accidentid = new AccidentId();
        String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_ID +
                " WHERE " + CAID_ID + " = '" + AID_ID + "'";


    //    SQLiteDatabase db = this.getReadableDatabase(xxx);
     //   SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                accidentid.setAID_ID(Integer.parseInt(cursor.getString(0)));
                accidentid.setAID_CCODE(cursor.getString(1));
                accidentid.setAID_ADDRESS(cursor.getString(2));
                accidentid.setAID_CITY(cursor.getString(3));
                accidentid.setAID_STATE(cursor.getString(4));
                accidentid.setAID_ZIP(cursor.getString(5));
                accidentid.setAID_COUNTY(cursor.getString(6));
                accidentid.setAID_LAT(cursor.getString(7));
                accidentid.setAID_LON(cursor.getString(8));
                accidentid.setAID_DUID(Integer.parseInt(cursor.getString(9)));
                accidentid.setAID_RDATE(cursor.getString(10));
                accidentid.setAID_ADATE(cursor.getString(11));
                accidentid.setAID_ATIME(cursor.getString(12));


            }
            // Adding contact to list


        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
           //db.close();
        }

        return accidentid;
    }

    // Getting All AccidentIds
    public List<AccidentId> getAllAccidentIds() {
        List<AccidentId> accidentidList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_ID;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
     //   SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                accidentid = new AccidentId();
                accidentid.setAID_ID(Integer.parseInt(cursor.getString(0)));
                accidentid.setAID_CCODE(cursor.getString(1));
                accidentid.setAID_ADDRESS(cursor.getString(2));
                accidentid.setAID_CITY(cursor.getString(3));
                accidentid.setAID_STATE(cursor.getString(4));
                accidentid.setAID_ZIP(cursor.getString(5));
                accidentid.setAID_COUNTY(cursor.getString(6));
                accidentid.setAID_LAT(cursor.getString(7));
                accidentid.setAID_LON(cursor.getString(8));
                accidentid.setAID_DUID(Integer.parseInt(cursor.getString(9)));
                accidentid.setAID_RDATE(cursor.getString(10));
                accidentid.setAID_ADATE(cursor.getString(11));
                accidentid.setAID_ATIME(cursor.getString(12));


                // Adding contact to list
                accidentidList.add(accidentid);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
         // db.close();
        }
        // return accidentid list
        return accidentidList;
    }

    public List<String> getAllAccidentIdsOnly() {
        List<String> accidentidList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_ID;

    //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
      //  SQLiteDatabase db = this.getWritableDatabase();
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        Cursor cursor = dbW.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {

                accidentidList.add(cursor.getString(0));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
       //db.close();
        }
        // return device user list
        return accidentidList;
    }

    //**
    //* Updates the AID_DUID field
    //* @param newAID_DUID
    //* @param AID_ID
    //* @param oldAID_DUID
    //*/
    public void updateAID_ID(int AID_ID, String AID_CCODE, String AID_ADDRESS, String AID_CITY,
                             String AID_STATE, String AID_ZIP, String AID_COUNTY, String AID_LAT,
                             String AID_LON, String AID_RDATE, String AID_ADATE,
                             String AID_ATIME) {
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int AID_DUID;
        if (isNumber(DA_ID_STR)) {
            AID_DUID = Integer.parseInt(DA_ID_STR);
        } else {
            AID_DUID = 0;
        }
        int X_ID = 1;
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
       // SQLiteDatabase db = this.getWritableDatabase(xxx);
      //  SQLiteDatabase db = this.getWritableDatabase();
        // Notice the Commas
        String query = "UPDATE " + TABLE_ACCIDENT_ID
                + " SET " + CAID_DUID + " = '" + AID_DUID + "', "
                + CAID_CCODE + " = '" + AID_CCODE + "', "
                + CAID_ADDRESS + " = '" + AID_ADDRESS + "', "
                + CAID_CITY + " = '" + AID_CITY + "', "
                + CAID_STATE + " = '" + AID_STATE + "', "
                + CAID_ZIP + " = '" + AID_ZIP + "', "
                + CAID_COUNTY + " = '" + AID_COUNTY + "', "
                + CAID_LAT + " = '" + AID_LAT + "', "
                + CAID_LON + " = '" + AID_LON + "', "
                + CAID_RDATE + " = '" + AID_RDATE + "', "
                + CAID_ADATE + " = '" + AID_ADATE + "', "
                + CAID_ATIME + " = '" + AID_ATIME + "'"
                + " WHERE " + CAID_ID + " = " + AID_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);
    }

    //**
    // * Delete from database
    // * @param AID_ID
    // * @param AID_DUID
    //  */
    public void deleteAID_ID(int AID_ID) {
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
    //    SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ACCIDENT_ID + " WHERE "
                + CAID_ID + " = '" + AID_ID + "'";
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