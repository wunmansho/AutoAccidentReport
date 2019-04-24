package com.auto.accident.report.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.objects.DeviceVehicle;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.auto.accident.report.util.utils.isNumber;

////import net.sqlcipher.database.SQLiteFullException;
////import net.sqlcipher.CursorIndexOutOfBoundsException;
//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by User on 2/28/2017.
 */

public class DeviceVehicleDao extends SQLiteOpenHelper {
    private static final String TAG = "DeviceVehicleDao";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_DEVICE_VEHICLE = "device_vehicle_table";
    private static final String CDV_ID = "DV_ID";
    private static final String CDV_TAG = "DV_TAG";
    private static final String CDV_STATE = "DV_STATE";
    private static final String CDV_EXPIRATION = "DV_EXPIRATION";
    private static final String CDV_VIN = "DV_VIN";
    private static final String CDV_YEAR = "DV_YEAR";
    private static final String CDV_MAKE = "DV_MAKE";
    private static final String CDV_MODEL = "DV_MODEL";
    private static final String CDV_CUID = "DV_CUID";
    private static final String CDV_CDATE = "DV_CDATE";
    private static final String CDV_UUID = "DV_UUID";
    private static final String CDV_UDATE = "DV_UDATE";
    private static final String CDV_TYPE = "DV_TYPE";
    private static final String CDV_PLATE_COUNTRY = "DV_PLATE_COUNTRY";
    private final Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private String DA_ID_STR;

    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public DeviceVehicleDao(Context context) {
        super(context, TABLE_DEVICE_VEHICLE, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DEVICE_VEHICLE + "(" + CDV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CDV_TAG + " TEXT, " +
                CDV_STATE + " TEXT, " +
                CDV_EXPIRATION + " TEXT, " +
                CDV_VIN + " TEXT, " +
                CDV_YEAR + " TEXT, " +
                CDV_MAKE + " TEXT, " +
                CDV_MODEL + " TEXT, " +
                CDV_CUID + " INTEGER, " +
                CDV_CDATE + " TEXT, " +
                CDV_UUID + " INTEGER, " +
                CDV_UDATE + " TEXT, " +
                CDV_TYPE + " TEXT, " +
                CDV_PLATE_COUNTRY + " TEXT)";

        db.execSQL(createTable);

        String deviceLocale = Locale.getDefault().getLanguage();
        if (deviceLocale.equals("en")) {
        }
        if (deviceLocale.equals("es")) {
        }
        if (deviceLocale.equals("fr")) {
        }
        if (deviceLocale.equals("ru")) {
        }
        if (deviceLocale.equals("zh")) {
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE_VEHICLE);
        onCreate(db);
    }

    public long addData(String DV_TAG, String DV_STATE,
                        String DV_EXPIRATION, String DV_VIN, String DV_YEAR, String DV_MAKE,
                        String DV_MODEL, String DV_TYPE, String DV_PLATE_COUNTRY) {
        DV_TAG = utils.extractCharacter(DV_TAG);
        DV_STATE = utils.extractCharacter(DV_STATE);
        DV_EXPIRATION = utils.extractCharacter(DV_EXPIRATION);
        DV_VIN = utils.extractCharacter(DV_VIN);
        DV_YEAR = utils.extractCharacter(DV_YEAR);
        DV_MAKE = utils.extractCharacter(DV_MAKE);
        DV_MODEL = utils.extractCharacter(DV_MODEL);
        DV_TYPE = utils.extractCharacter(DV_TYPE);
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int DV_CUID;
        if (isNumber(DA_ID_STR)) {
            DV_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            DV_CUID = 0;
        }
        String DV_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
       // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CDV_TAG, DV_TAG);
        contentValues.put(CDV_STATE, DV_STATE);
        contentValues.put(CDV_EXPIRATION, DV_EXPIRATION);
        contentValues.put(CDV_VIN, DV_VIN);
        contentValues.put(CDV_YEAR, DV_YEAR);
        contentValues.put(CDV_MAKE, DV_MAKE);
        contentValues.put(CDV_MODEL, DV_MODEL);
        contentValues.put(CDV_CUID, DV_CUID);
        contentValues.put(CDV_CDATE, DV_UDATE);
        contentValues.put(CDV_UUID, DV_CUID);
        contentValues.put(CDV_UDATE, DV_UDATE);
        contentValues.put(CDV_TYPE, DV_TYPE);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        return dbW.insert(TABLE_DEVICE_VEHICLE, null, contentValues);
    }


    public void updateData(int DV_ID, String DV_TAG, String DV_STATE,
                           String DV_EXPIRATION, String DV_VIN, String DV_YEAR, String DV_MAKE,
                           String DV_MODEL, String DV_TYPE, String DV_PLATE_COUNTRY) {
        DV_TAG = utils.extractCharacter(DV_TAG);
        DV_STATE = utils.extractCharacter(DV_STATE);
        DV_EXPIRATION = utils.extractCharacter(DV_EXPIRATION);
        DV_VIN = utils.extractCharacter(DV_VIN);
        DV_YEAR = utils.extractCharacter(DV_YEAR);
        DV_MAKE = utils.extractCharacter(DV_MAKE);
        DV_MODEL = utils.extractCharacter(DV_MODEL);
        DV_TYPE = utils.extractCharacter(DV_TYPE);
        DV_PLATE_COUNTRY = utils.extractCharacter(DV_PLATE_COUNTRY);

        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int DV_UUID;
        if (isNumber(DA_ID_STR)) {
            DV_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            DV_UUID = 0;
        }
        String DV_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
       // SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_DEVICE_VEHICLE +
                " SET " + CDV_TAG + " = '" + DV_TAG + "' , "
                + CDV_STATE + " = '" + DV_STATE + "' , "
                + CDV_EXPIRATION + " = '" + DV_EXPIRATION + "' , "
                + CDV_VIN + " = '" + DV_VIN + "' , "
                + CDV_YEAR + " = '" + DV_YEAR + "' , "
                + CDV_MAKE + " = '" + DV_MAKE + "' , "
                + CDV_MODEL + " = '" + DV_MODEL + "' , "
                + CDV_UUID + " = '" + DV_UUID + "' , "
                + CDV_UDATE + " = '" + DV_UDATE + "' , "
                + CDV_TYPE + " = '" + DV_TYPE + "' , "
                + CDV_PLATE_COUNTRY + " = '" + DV_PLATE_COUNTRY + "'"
                + "WHERE " + CDV_ID + " = " + DV_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
     //   SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DEVICE_VEHICLE;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        Cursor cursor = dbW.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
            //  cursor.close();
            //  db.close();
        }
        return cursor;
    }


    // Getting one Involved Party
    public DeviceVehicle getDeviceVehicle(int DV_ID) {
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_VEHICLE +
                " WHERE " + CDV_ID + " = " + DV_ID;
      //  SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        // return shop
        //if (cursor != null && !cursor.isClosed()) {
        //    cursor.close();
        //   db.close();
        //  }
        return new DeviceVehicle(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                Integer.parseInt(cursor.getString(8)),
                cursor.getString(9),
                Integer.parseInt(cursor.getString(10)),
                cursor.getString(11),
                cursor.getString(12),
                cursor.getString(13));
    }


    // Getting All Involved Pars
    public List<DeviceVehicle> getAllDeviceVehicles() {
        List<DeviceVehicle> involvedvehicleList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_VEHICLE;

        //  String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_VEHICLE;

      //  SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DeviceVehicle involvedvehicle = new DeviceVehicle();
                involvedvehicle.setDV_ID(Integer.parseInt(cursor.getString(0)));
                involvedvehicle.setDV_TAG(cursor.getString(1));
                involvedvehicle.setDV_STATE(cursor.getString(2));
                involvedvehicle.setDV_EXPIRATION(cursor.getString(3));
                involvedvehicle.setDV_VIN(cursor.getString(4));
                involvedvehicle.setDV_YEAR(cursor.getString(5));
                involvedvehicle.setDV_MAKE(cursor.getString(6));
                involvedvehicle.setDV_MODEL(cursor.getString(7));
                involvedvehicle.setDV_CUID(Integer.parseInt(cursor.getString(8)));
                involvedvehicle.setDV_CDATE(cursor.getString(9));
                involvedvehicle.setDV_UUID(Integer.parseInt(cursor.getString(10)));
                involvedvehicle.setDV_UDATE(cursor.getString(11));
                involvedvehicle.setDV_TYPE(cursor.getString(12));
                involvedvehicle.setDV_PLATE_COUNTRY(cursor.getString(13));

                // Adding contact to list
                involvedvehicleList.add(involvedvehicle);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return involvedvehicleList;
    }

    // Getting All Involved Pars
    public List<DeviceVehicle> getAllDeviceVehiclesAll() {
        List<DeviceVehicle> involvedvehicleList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_VEHICLE;
        //  String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_VEHICLE;

    //    SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DeviceVehicle involvedvehicle = new DeviceVehicle();
                involvedvehicle.setDV_ID(Integer.parseInt(cursor.getString(0)));
                involvedvehicle.setDV_TAG(cursor.getString(1));
                involvedvehicle.setDV_STATE(cursor.getString(2));
                involvedvehicle.setDV_EXPIRATION(cursor.getString(3));
                involvedvehicle.setDV_VIN(cursor.getString(4));
                involvedvehicle.setDV_YEAR(cursor.getString(5));
                involvedvehicle.setDV_MAKE(cursor.getString(6));
                involvedvehicle.setDV_MODEL(cursor.getString(7));
                involvedvehicle.setDV_CUID(Integer.parseInt(cursor.getString(8)));
                involvedvehicle.setDV_CDATE(cursor.getString(9));
                involvedvehicle.setDV_UUID(Integer.parseInt(cursor.getString(10)));
                involvedvehicle.setDV_UDATE(cursor.getString(11));
                involvedvehicle.setDV_TYPE(cursor.getString(12));
                involvedvehicle.setDV_PLATE_COUNTRY(cursor.getString(13));

                // Adding contact to list
                involvedvehicleList.add(involvedvehicle);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //     cursor.close();
            //     db.close();
        }
        // return contact list
        return involvedvehicleList;
    }


    /**
     * Returns only the DV_ID that matches the DV_TAG passed in
     *
     * @param DV_TAG
     * @return
     */
    public Cursor getItemID(String DV_TAG) {
       // SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + CDV_ID + " FROM " + TABLE_DEVICE_VEHICLE +
                " WHERE " + CDV_TAG + " = '" + DV_TAG + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        Cursor cursor = dbW.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //  db.close();
        }
        return cursor;
    }

    //**
    // * Delete from database
    // * @param DV_ID
    // * @param DV_TAG
    //  */
    public void deleteDV_ID(String DV_ID) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_DEVICE_VEHICLE + " WHERE "
                + CDV_ID + " = '" + DV_ID + "'";
        dbW.execSQL(query);
    }
    public void closeAll() {
        dbR.close();
        dbW.close();

    }

}