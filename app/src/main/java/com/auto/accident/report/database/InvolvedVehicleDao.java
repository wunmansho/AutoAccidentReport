package com.auto.accident.report.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.model.InsurancePolicyV;
import com.auto.accident.report.model.InvolvedVehicle;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.util.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.auto.accident.report.util.utils.isNumber;

////import net.sqlcipher.database.SQLiteFullException;
//import net.sqlcipher.CursorIndexOutOfBoundsException;
//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by User on 2/28/2017.
 */

public class InvolvedVehicleDao extends SQLiteOpenHelper {
    private static final String TAG = "InvolvedVehicleDao";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_INVOLVED_VEHICLE = "involved_vehicle_table";
    private static final String CIV_ID = "IV_ID";
    private static final String CIV_AID = "IV_AID";
    private static final String CIV_TAG = "IV_TAG";
    private static final String CIV_STATE = "IV_STATE";
    private static final String CIV_EXPIRATION = "IV_EXPIRATION";
    private static final String CIV_VIN = "IV_VIN";
    private static final String CIV_YEAR = "IV_YEAR";
    private static final String CIV_MAKE = "IV_MAKE";
    private static final String CIV_MODEL = "IV_MODEL";
    private static final String CIV_CUID = "IV_CUID";
    private static final String CIV_CDATE = "IV_CDATE";
    private static final String CIV_UUID = "IV_UUID";
    private static final String CIV_UDATE = "IV_UDATE";
    private static final String CIV_TYPE = "IV_TYPE";
    private static final String CIV_PLATE_COUNTRY = "IV_PLATE_COUNTRY";
    private InsurancePolicyVDao mInsurancePolicyVDao;
    private final Context context;
    private InvolvedVehicle involvedvehicle;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private String DA_ID_STR;
    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public InvolvedVehicleDao(Context context) {
        super(context, TABLE_INVOLVED_VEHICLE, null, 1);
        this.context = context;
        persistenceObj = new PersistenceObj();
        mPersistenceObjDao = new PersistenceObjDao(context);
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_INVOLVED_VEHICLE + "(" + CIV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIV_AID + " INTEGER, " +
                CIV_TAG + " TEXT, " +
                CIV_STATE + " TEXT, " +
                CIV_EXPIRATION + " TEXT, " +
                CIV_VIN + " TEXT, " +
                CIV_YEAR + " TEXT, " +
                CIV_MAKE + " TEXT, " +
                CIV_MODEL + " TEXT, " +
                CIV_CUID + " INTEGER, " +
                CIV_CDATE + " TEXT, " +
                CIV_UUID + " INTEGER, " +
                CIV_UDATE + " TEXT, " +
                CIV_TYPE + " TEXT, " +
                CIV_PLATE_COUNTRY + " TEXT)";
        db.execSQL(createTable);
        String deviceLocale = Locale.getDefault().getLanguage();
        if (deviceLocale.equals("en")) {
            //       db.execSQL("insert INTO involved_vehicle_table ('IV_AID', 'IV_TAG', 'IV_STATE', 'IV_EXPIRATION', 'IV_VIN', 'IV_YEAR', 'IV_MAKE', 'IV_MODEL', 'IV_CUID', 'IV_CDATE', 'IV_UUID', 'IV_UDATE') VALUES (1,'CVN-7791', 'Florida ', '2019', 'V-133721957', '2016 ', 'Chevrolet',  'Corvette', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //        db.execSQL("insert INTO involved_vehicle_table ('IV_AID', 'IV_TAG', 'IV_STATE', 'IV_EXPIRATION', 'IV_VIN', 'IV_YEAR', 'IV_MAKE', 'IV_MODEL', 'IV_CUID', 'IV_CDATE', 'IV_UUID', 'IV_UDATE') VALUES (1,'RSN-3364', 'Florida ', '2019', 'V4541892311', '2016 ', 'Austin',  'Martin', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOLVED_VEHICLE);
        onCreate(db);
    }

    public long addData(Integer IV_AID, String IV_TAG, String IV_STATE,
                        String IV_EXPIRATION, String IV_VIN, String IV_YEAR, String IV_MAKE,
                        String IV_MODEL, String IV_TYPE, String IV_PLATE_COUNTRY) {
        IV_TAG = utils.extractCharacter(IV_TAG);
        IV_STATE = utils.extractCharacter(IV_STATE);
        IV_EXPIRATION = utils.extractCharacter(IV_EXPIRATION);
        IV_VIN = utils.extractCharacter(IV_VIN);
        IV_YEAR = utils.extractCharacter(IV_YEAR);
        IV_MAKE = utils.extractCharacter(IV_MAKE);
        IV_MODEL = utils.extractCharacter(IV_MODEL);
        IV_TYPE = utils.extractCharacter(IV_TYPE);
        IV_PLATE_COUNTRY = utils.extractCharacter(IV_PLATE_COUNTRY);
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IV_CUID;
        if (isNumber(DA_ID_STR)) {
            IV_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            IV_CUID = 0;
        }

        String IV_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CIV_AID, IV_AID);
        contentValues.put(CIV_TAG, IV_TAG);
        contentValues.put(CIV_STATE, IV_STATE);
        contentValues.put(CIV_EXPIRATION, IV_EXPIRATION);
        contentValues.put(CIV_VIN, IV_VIN);
        contentValues.put(CIV_YEAR, IV_YEAR);
        contentValues.put(CIV_MAKE, IV_MAKE);
        contentValues.put(CIV_MODEL, IV_MODEL);

        contentValues.put(CIV_CUID, IV_CUID);
        contentValues.put(CIV_CDATE, IV_UDATE);
        contentValues.put(CIV_UUID, IV_CUID);
        contentValues.put(CIV_UDATE, IV_UDATE);
        contentValues.put(CIV_TYPE, IV_TYPE);
        contentValues.put(CIV_PLATE_COUNTRY, IV_PLATE_COUNTRY);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        return dbW.insert(TABLE_INVOLVED_VEHICLE, null, contentValues);
    }


    public void updateData(int IV_ID, int IV_AID, String IV_TAG, String IV_STATE,
                           String IV_EXPIRATION, String IV_VIN, String IV_YEAR, String IV_MAKE,
                           String IV_MODEL, String IV_TYPE, String IV_PLATE_COUNTRY) {
        IV_TAG = utils.extractCharacter(IV_TAG);
        IV_STATE = utils.extractCharacter(IV_STATE);
        IV_EXPIRATION = utils.extractCharacter(IV_EXPIRATION);
        IV_VIN = utils.extractCharacter(IV_VIN);
        IV_YEAR = utils.extractCharacter(IV_YEAR);
        IV_MAKE = utils.extractCharacter(IV_MAKE);
        IV_MODEL = utils.extractCharacter(IV_MODEL);
        IV_TYPE = utils.extractCharacter(IV_TYPE);
        IV_PLATE_COUNTRY = utils.extractCharacter(IV_PLATE_COUNTRY);
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IV_UUID;
        if (isNumber(DA_ID_STR)) {
            IV_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            IV_UUID = 0;
        }

        String IV_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
         String query = "UPDATE " + TABLE_INVOLVED_VEHICLE +
                " SET " + CIV_TAG + " = '" + IV_TAG + "' , "
                + CIV_STATE + " = '" + IV_STATE + "' , "
                + CIV_EXPIRATION + " = '" + IV_EXPIRATION + "' , "
                + CIV_VIN + " = '" + IV_VIN + "' , "
                + CIV_YEAR + " = '" + IV_YEAR + "' , "
                + CIV_MAKE + " = '" + IV_MAKE + "' , "
                + CIV_MODEL + " = '" + IV_MODEL + "' , "
                + CIV_UUID + " = '" + IV_UUID + "' , "
                + CIV_UDATE + " = '" + IV_UDATE + "' , "
                + CIV_TYPE + " = '" + IV_TYPE + "' , "
                + CIV_PLATE_COUNTRY + " = '" + IV_PLATE_COUNTRY + "'"
                + "WHERE " + CIV_ID + " = " + IV_ID
                + " AND " + CIV_AID + " = " + IV_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }
    public void updateDataModel(int IV_ID, int IV_AID, String IV_MODEL) {
        IV_MODEL = utils.extractCharacter(IV_MODEL);



        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IV_UUID;
        if (isNumber(DA_ID_STR)) {
            IV_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            IV_UUID = 0;
        }

        String IV_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
        //   SQLiteDatabase db = this.getWritableDatabase(xxx);

        String query = "UPDATE " + TABLE_INVOLVED_VEHICLE +
                " SET " + CIV_MODEL + " = '" + IV_MODEL + "'"
                + "WHERE " + CIV_ID + " = " + IV_ID
                + " AND " + CIV_AID + " = " + IV_AID;
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
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_INVOLVED_VEHICLE;
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

    public InvolvedVehicle getInvolvedVehicleTag(String IV_TAG, int IV_AID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE +
                " WHERE " + CIV_AID + " = " + IV_AID +
                " AND " + CIV_TAG + " = '" + IV_TAG + "'";
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();
            involvedvehicle = new InvolvedVehicle(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    Integer.parseInt(cursor.getString(9)),
                    cursor.getString(10),
                    Integer.parseInt(cursor.getString(11)),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14));
        } catch (CursorIndexOutOfBoundsException e) {

        } finally {
            cursor.close();
          //  db.close();

        }
        return involvedvehicle;
    }

    // Getting one Involved Vehicle
    public InvolvedVehicle getInvolvedVehicle(int IV_ID, int IV_AID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE +
                " WHERE " + CIV_AID + " = " + IV_AID +
                " AND " + CIV_ID + " = " + IV_ID;
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
       // SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        involvedvehicle = new InvolvedVehicle(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                Integer.parseInt(cursor.getString(9)),
                cursor.getString(10),
                Integer.parseInt(cursor.getString(11)),
                cursor.getString(12),
                cursor.getString(13),
                cursor.getString(14));
        // return shop
        //if (cursor != null && !cursor.isClosed()) {
        //    cursor.close();
        //   db.close();
        //  }
        return involvedvehicle;
    }


    // Getting All Involved Pars
    public List<InvolvedVehicle> getAllInvolvedVehicles(int AID_ID) {
        List<InvolvedVehicle> involvedvehicleList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE +
                " WHERE " + CIV_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                involvedvehicle = new InvolvedVehicle();
                involvedvehicle.setIV_ID(Integer.parseInt(cursor.getString(0)));
                involvedvehicle.setIV_AID(Integer.parseInt(cursor.getString(1)));
                involvedvehicle.setIV_TAG(cursor.getString(2));
                involvedvehicle.setIV_STATE(cursor.getString(3));
                involvedvehicle.setIV_EXPIRATION(cursor.getString(4));
                involvedvehicle.setIV_VIN(cursor.getString(5));
                involvedvehicle.setIV_YEAR(cursor.getString(6));
                involvedvehicle.setIV_MAKE(cursor.getString(7));
                involvedvehicle.setIV_MODEL(cursor.getString(8));
                involvedvehicle.setIV_CUID(Integer.parseInt(cursor.getString(9)));
                involvedvehicle.setIV_CDATE(cursor.getString(10));
                involvedvehicle.setIV_UUID(Integer.parseInt(cursor.getString(11)));
                involvedvehicle.setIV_UDATE(cursor.getString(12));
                involvedvehicle.setIV_TYPE(cursor.getString(13));
                involvedvehicle.setIV_PLATE_COUNTRY(cursor.getString(14));

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
    public List<InvolvedVehicle> getAllUninsuredInvolvedVehicles(int AID_ID) {
        List<InvolvedVehicle> involvedvehicleList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE +
                " WHERE " + CIV_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE;
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPV_POID;
        if (isNumber(DA_ID_STR)) {
            IPV_POID = Integer.parseInt(DA_ID_STR);
        } else {
            IPV_POID = 0;
        }

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                involvedvehicle = new InvolvedVehicle();
                int IV_ID = Integer.parseInt(cursor.getString(0));
                mInsurancePolicyVDao = new InsurancePolicyVDao(context);
                InsurancePolicyV insurancePolicyV = mInsurancePolicyVDao.getInsuredVehicle(AID_ID, IPV_POID, IV_ID);
                if (insurancePolicyV == null) {
                    involvedvehicle.setIV_ID(Integer.parseInt(cursor.getString(0)));
                    involvedvehicle.setIV_AID(Integer.parseInt(cursor.getString(1)));
                    involvedvehicle.setIV_TAG(cursor.getString(2));
                    involvedvehicle.setIV_STATE(cursor.getString(3));
                    involvedvehicle.setIV_EXPIRATION(cursor.getString(4));
                    involvedvehicle.setIV_VIN(cursor.getString(5));
                    involvedvehicle.setIV_YEAR(cursor.getString(6));
                    involvedvehicle.setIV_MAKE(cursor.getString(7));
                    involvedvehicle.setIV_MODEL(cursor.getString(8));
                    involvedvehicle.setIV_CUID(Integer.parseInt(cursor.getString(9)));
                    involvedvehicle.setIV_CDATE(cursor.getString(10));
                    involvedvehicle.setIV_UUID(Integer.parseInt(cursor.getString(11)));
                    involvedvehicle.setIV_UDATE(cursor.getString(12));
                    involvedvehicle.setIV_TYPE(cursor.getString(13));
                    involvedvehicle.setIV_PLATE_COUNTRY(cursor.getString(14));

                    // Adding contact to list
                    involvedvehicleList.add(involvedvehicle);
                }
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
    public List<InvolvedVehicle> getAllInvolvedVehiclesAll() {
        List<InvolvedVehicle> involvedvehicleList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE;
        //  String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_VEHICLE;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                involvedvehicle = new InvolvedVehicle();
                involvedvehicle.setIV_ID(Integer.parseInt(cursor.getString(0)));
                involvedvehicle.setIV_AID(Integer.parseInt(cursor.getString(1)));
                involvedvehicle.setIV_TAG(cursor.getString(2));
                involvedvehicle.setIV_STATE(cursor.getString(3));
                involvedvehicle.setIV_EXPIRATION(cursor.getString(4));
                involvedvehicle.setIV_VIN(cursor.getString(5));
                involvedvehicle.setIV_YEAR(cursor.getString(6));
                involvedvehicle.setIV_MAKE(cursor.getString(7));
                involvedvehicle.setIV_MODEL(cursor.getString(8));
                involvedvehicle.setIV_CUID(Integer.parseInt(cursor.getString(9)));
                involvedvehicle.setIV_CDATE(cursor.getString(10));
                involvedvehicle.setIV_UUID(Integer.parseInt(cursor.getString(11)));
                involvedvehicle.setIV_UDATE(cursor.getString(12));
                involvedvehicle.setIV_TYPE(cursor.getString(13));
                involvedvehicle.setIV_PLATE_COUNTRY(cursor.getString(14));


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
     * Returns only the IV_ID that matches the IV_TAG passed in
     *
     * @param IV_TAG
     * @return
     */
    public Cursor getItemID(String IV_TAG) {
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT " + CIV_ID + " FROM " + TABLE_INVOLVED_VEHICLE +
                " WHERE " + CIV_TAG + " = '" + IV_TAG + "'";
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
    // * @param IV_ID
    // * @param IV_TAG
    //  */

    public void deleteIV_ID(int IV_ID, int IV_AID) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_INVOLVED_VEHICLE +
                " WHERE " + CIV_AID + " = " + IV_AID +
                " AND " + CIV_ID + " = " + IV_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);
        mInsurancePolicyVDao = new InsurancePolicyVDao(context);
        mInsurancePolicyVDao.deleteIPV_IVID(IV_ID);
    }

    public void deleteIV_AID(int IV_AID) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_INVOLVED_VEHICLE +
                " WHERE " + CIV_AID + " = " + IV_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);
        mInsurancePolicyVDao = new InsurancePolicyVDao(context);
        mInsurancePolicyVDao.deleteIPV_AID(IV_AID);

    }
    public void closeAll() {
        dbR.close();
        dbW.close();
        mPersistenceObjDao.close();
    }

}