package com.auto.accident.report.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.objects.InsurancePolicyV;
import com.auto.accident.report.objects.PersistenceObj;
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

public class InsurancePolicyVDao extends SQLiteOpenHelper {
    private static final String TAG = "InsurancePolicyVDao";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_INSURANCE_POLICY_VEHICLE = "insurance_policy_vehicle_table";
    private static final String CIPV_ID = "IPV_ID";
    private static final String CIPV_AID = "IPV_AID";
    private static final String CIPV_POID = "IPV_POID";
    private static final String CIPV_IVID = "IPV_IVID";
    private static final String CIPV_CUID = "IPV_CUID";
    private static final String CIPV_CDATE = "IPV_CDATE";
    private static final String CIPV_UUID = "IPV_UUID";
    private static final String CIPV_UDATE = "IPV_UDATE";
    private static final String CIPV_V1 = "IPV_V1";
    private static final String CIPV_V2 = "IPV_V2";
    private static final String CIPV_V3 = "IPV_V3";
    private static final String CIPV_V4 = "IPV_V4";
    private static final String CIPV_V5 = "IPV_V5";
    private static final String CIPV_V6 = "IPV_V6";
    private static final String CIPV_V7 = "IPV_V7";
    private static final String CIPV_V8 = "IPV_V8";
    private static final String CIPV_HOLDER = "IPV_HOLDER";
    private final Context context;
    private InsurancePolicyV insurancepolicyv;
    private PersistenceObjDao mPersistenceObjDao;

    private String DA_ID_STR;
    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;

    public InsurancePolicyVDao(Context context) {
        super(context, TABLE_INSURANCE_POLICY_VEHICLE, null, 1);
        this.context = context;

         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_INSURANCE_POLICY_VEHICLE + "(" + CIPV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIPV_AID + " INTEGER, " +              //  Accident ID Zero is a profile Copy only
                CIPV_POID + " INTEGER, " +  //   FK IPO_ID from InsurancePolicyDao
                CIPV_IVID + " INTEGER, " +     // Involved Vehicle
                CIPV_CUID + " INTEGER, " +
                CIPV_CDATE + " TEXT, " +
                CIPV_UUID + " INTEGER, " +
                CIPV_UDATE + " TEXT, " +
                CIPV_V1 + " TEXT, " +
                CIPV_V2 + " TEXT, " +
                CIPV_V3 + " TEXT, " +
                CIPV_V4 + " TEXT, " +
                CIPV_V5 + " TEXT, " +
                CIPV_V6 + " TEXT, " +
                CIPV_V7 + " TEXT, " +
                CIPV_V8 + " TEXT, " +
                CIPV_HOLDER + " INTEGER)";
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSURANCE_POLICY_VEHICLE);
        onCreate(db);
    }

    public long addData(Integer IPV_AID, Integer IPV_POID,
                        Integer IPV_IVID, String IPV_V1, String IPV_V2, String IPV_V3,
                        String IPV_V4, String IPV_V5, String IPV_V6, String IPV_V7, String IPV_V8, Integer IPV_HOLDER) {

        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPV_CUID;
        if (isNumber(DA_ID_STR)) {
            IPV_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            IPV_CUID = 0;
        }
        String IPV_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        IPV_V1 = utils.extractCharacter(IPV_V1);
        IPV_V2 = utils.extractCharacter(IPV_V2);
        IPV_V3 = utils.extractCharacter(IPV_V3);
        IPV_V4 = utils.extractCharacter(IPV_V4);
        IPV_V5 = utils.extractCharacter(IPV_V5);
        IPV_V6 = utils.extractCharacter(IPV_V6);
        IPV_V7 = utils.extractCharacter(IPV_V7);
        IPV_V8 = utils.extractCharacter(IPV_V8);
      //  SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CIPV_AID, IPV_AID);
        contentValues.put(CIPV_POID, IPV_POID);
        contentValues.put(CIPV_IVID, IPV_IVID);
        contentValues.put(CIPV_CUID, IPV_CUID);
        contentValues.put(CIPV_CDATE, IPV_UDATE);
        contentValues.put(CIPV_UUID, IPV_CUID);
        contentValues.put(CIPV_UDATE, IPV_UDATE);
        contentValues.put(CIPV_V1, IPV_V1);
        contentValues.put(CIPV_V2, IPV_V2);
        contentValues.put(CIPV_V3, IPV_V3);
        contentValues.put(CIPV_V4, IPV_V4);
        contentValues.put(CIPV_V5, IPV_V5);
        contentValues.put(CIPV_V6, IPV_V6);
        contentValues.put(CIPV_V7, IPV_V7);
        contentValues.put(CIPV_V8, IPV_V8);
        contentValues.put(CIPV_HOLDER, IPV_HOLDER);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        return dbW.insert(TABLE_INSURANCE_POLICY_VEHICLE, null, contentValues);
    }


    public void updateData(int IPV_ID, int IPV_AID, int IPV_POID, int IPV_IVID, String IPV_V1, String IPV_V2, String IPV_V3,
                           String IPV_V4, String IPV_V5, String IPV_V6, String IPV_V7, String IPV_V8, Integer IPV_HOLDER) {
        IPV_V1 = utils.extractCharacter(IPV_V1);
        IPV_V2 = utils.extractCharacter(IPV_V2);
        IPV_V3 = utils.extractCharacter(IPV_V3);
        IPV_V4 = utils.extractCharacter(IPV_V4);
        IPV_V5 = utils.extractCharacter(IPV_V5);
        IPV_V6 = utils.extractCharacter(IPV_V6);
        IPV_V7 = utils.extractCharacter(IPV_V7);
        IPV_V8 = utils.extractCharacter(IPV_V8);

        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPV_UUID;
        if (isNumber(DA_ID_STR)) {
            IPV_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            IPV_UUID = 0;
        }
        String IPV_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_INSURANCE_POLICY_VEHICLE +
                " SET " + CIPV_AID + " = '" + IPV_AID + "' , "
                + CIPV_POID + " = '" + IPV_POID + "' , "
                + CIPV_IVID + " = '" + IPV_IVID + "' , "
                + CIPV_UUID + " = '" + IPV_UUID + "' , "
                + CIPV_UDATE + " = '" + IPV_UDATE + "' , "
                + CIPV_V1 + " = '" + IPV_V1 + "' , "
                + CIPV_V2 + " = '" + IPV_V2 + "' , "
                + CIPV_V3 + " = '" + IPV_V3 + "' , "
                + CIPV_V4 + " = '" + IPV_V4 + "' , "
                + CIPV_V5 + " = '" + IPV_V5 + "' , "
                + CIPV_V6 + " = '" + IPV_V6 + "' , "
                + CIPV_V7 + " = '" + IPV_V7 + "' , "
                + CIPV_V8 + " = '" + IPV_V8 + "' , "
                + CIPV_HOLDER + " = '" + IPV_HOLDER + "' "
                + "WHERE " + CIPV_ID + " = " + IPV_ID
                + " AND " + CIPV_AID + " = " + IPV_AID;
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
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INSURANCE_POLICY_VEHICLE;
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

    // Getting one Involved Vehicle
    public InsurancePolicyV getInsurancePolicyV(int IPV_AID, int IPV_ID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_AID + " = " + IPV_AID +
                " AND " + CIPV_ID + " = " + IPV_ID;
     //   SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            insurancepolicyv = new InsurancePolicyV(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    Integer.parseInt(cursor.getString(6)),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    Integer.parseInt(cursor.getString(16)));
        } catch (CursorIndexOutOfBoundsException e) {

        } finally {
            cursor.close();
       //     db.close();

        }
        return insurancepolicyv;
    }


    // Getting one Involved Vehicle
    public InsurancePolicyV getInsuredVehicle(int IPV_AID, int IPV_POID, int IPV_IVID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_AID + " = " + IPV_AID +
                " AND " + CIPV_POID + " = " + IPV_POID +
                " AND " + CIPV_IVID + " = " + IPV_IVID;
       // SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            insurancepolicyv = new InsurancePolicyV(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    cursor.getString(5),
                    Integer.parseInt(cursor.getString(6)),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    Integer.parseInt(cursor.getString(16)));
        } catch (CursorIndexOutOfBoundsException e) {

        } finally {
            cursor.close();
        //    db.close();

        }
        return insurancepolicyv;
    }

    // Getting All Involved Pars
    public List<InsurancePolicyV> getInsurancePolicyVs(int AID_ID, int IPV_POID) {
        List<InsurancePolicyV> insurancepolicyvList = new ArrayList<>();
        // Select All QueryIPV_IVID
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_AID + " = " + AID_ID +
                " AND " + CIPV_POID + " = " + IPV_POID;


        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE;

      //  SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicyv = new InsurancePolicyV();
                insurancepolicyv.setIPV_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicyv.setIPV_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicyv.setIPV_POID(Integer.parseInt(cursor.getString(2)));
                insurancepolicyv.setIPV_IVID(Integer.parseInt(cursor.getString(3)));
                insurancepolicyv.setIPV_CUID(Integer.parseInt(cursor.getString(4)));
                insurancepolicyv.setIPV_CDATE(cursor.getString(5));
                insurancepolicyv.setIPV_UUID(Integer.parseInt(cursor.getString(6)));
                insurancepolicyv.setIPV_UDATE(cursor.getString(7));
                insurancepolicyv.setIPV_V1(cursor.getString(8));
                insurancepolicyv.setIPV_V2(cursor.getString(9));
                insurancepolicyv.setIPV_V3(cursor.getString(10));
                insurancepolicyv.setIPV_V4(cursor.getString(11));
                insurancepolicyv.setIPV_V5(cursor.getString(12));
                insurancepolicyv.setIPV_V6(cursor.getString(13));
                insurancepolicyv.setIPV_V7(cursor.getString(14));
                insurancepolicyv.setIPV_V8(cursor.getString(15));
                insurancepolicyv.setIPV_HOLDER(Integer.parseInt(cursor.getString(16)));
                // Adding contact to list
                insurancepolicyvList.add(insurancepolicyv);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return insurancepolicyvList;
    }

    // Get all insurance policys for one vehicle.  To discover if the vehicle is covered.
    public List<InsurancePolicyV> getInsuranceAllPolicyVs2(int AID_ID, int IPV_IVID) {
        List<InsurancePolicyV> insurancepolicyvList = new ArrayList<>();
        // Select All QueryIPV_IVID
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_AID + " = " + AID_ID +
                " AND " + CIPV_IVID + " = " + IPV_IVID;


        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE;

      //  SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicyv = new InsurancePolicyV();
                insurancepolicyv.setIPV_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicyv.setIPV_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicyv.setIPV_POID(Integer.parseInt(cursor.getString(2)));
                insurancepolicyv.setIPV_IVID(Integer.parseInt(cursor.getString(3)));
                insurancepolicyv.setIPV_CUID(Integer.parseInt(cursor.getString(4)));
                insurancepolicyv.setIPV_CDATE(cursor.getString(5));
                insurancepolicyv.setIPV_UUID(Integer.parseInt(cursor.getString(6)));
                insurancepolicyv.setIPV_UDATE(cursor.getString(7));
                insurancepolicyv.setIPV_V1(cursor.getString(8));
                insurancepolicyv.setIPV_V2(cursor.getString(9));
                insurancepolicyv.setIPV_V3(cursor.getString(10));
                insurancepolicyv.setIPV_V4(cursor.getString(11));
                insurancepolicyv.setIPV_V5(cursor.getString(12));
                insurancepolicyv.setIPV_V6(cursor.getString(13));
                insurancepolicyv.setIPV_V7(cursor.getString(14));
                insurancepolicyv.setIPV_V8(cursor.getString(15));
                insurancepolicyv.setIPV_HOLDER(Integer.parseInt(cursor.getString(16)));
                // Adding contact to list
                insurancepolicyvList.add(insurancepolicyv);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return insurancepolicyvList;
    }

    // Getting All Involved Pars
    public List<InsurancePolicyV> getAllInsurancePolicyVs(int AID_ID) {
        List<InsurancePolicyV> insurancepolicyvList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE;

     //   SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicyv = new InsurancePolicyV();
                insurancepolicyv.setIPV_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicyv.setIPV_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicyv.setIPV_POID(Integer.parseInt(cursor.getString(2)));
                insurancepolicyv.setIPV_IVID(Integer.parseInt(cursor.getString(3)));
                insurancepolicyv.setIPV_CUID(Integer.parseInt(cursor.getString(4)));
                insurancepolicyv.setIPV_CDATE(cursor.getString(5));
                insurancepolicyv.setIPV_UUID(Integer.parseInt(cursor.getString(6)));
                insurancepolicyv.setIPV_UDATE(cursor.getString(7));
                insurancepolicyv.setIPV_V1(cursor.getString(8));
                insurancepolicyv.setIPV_V2(cursor.getString(9));
                insurancepolicyv.setIPV_V3(cursor.getString(10));
                insurancepolicyv.setIPV_V4(cursor.getString(11));
                insurancepolicyv.setIPV_V5(cursor.getString(12));
                insurancepolicyv.setIPV_V6(cursor.getString(13));
                insurancepolicyv.setIPV_V7(cursor.getString(14));
                insurancepolicyv.setIPV_V8(cursor.getString(15));
                insurancepolicyv.setIPV_HOLDER(Integer.parseInt(cursor.getString(16)));
                // Adding contact to list
                insurancepolicyvList.add(insurancepolicyv);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return insurancepolicyvList;
    }

    // Getting All Involved Pars
    public List<InsurancePolicyV> getAllInsurancePolicyVsAll() {
        List<InsurancePolicyV> insurancepolicyvList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE;
        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_VEHICLE;

      //  SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicyv = new InsurancePolicyV();
                insurancepolicyv.setIPV_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicyv.setIPV_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicyv.setIPV_POID(Integer.parseInt(cursor.getString(2)));
                insurancepolicyv.setIPV_IVID(Integer.parseInt(cursor.getString(3)));
                insurancepolicyv.setIPV_CUID(Integer.parseInt(cursor.getString(4)));
                insurancepolicyv.setIPV_CDATE(cursor.getString(5));
                insurancepolicyv.setIPV_UUID(Integer.parseInt(cursor.getString(6)));
                insurancepolicyv.setIPV_UDATE(cursor.getString(7));
                insurancepolicyv.setIPV_V1(cursor.getString(8));
                insurancepolicyv.setIPV_V2(cursor.getString(9));
                insurancepolicyv.setIPV_V3(cursor.getString(10));
                insurancepolicyv.setIPV_V4(cursor.getString(11));
                insurancepolicyv.setIPV_V5(cursor.getString(12));
                insurancepolicyv.setIPV_V6(cursor.getString(13));
                insurancepolicyv.setIPV_V7(cursor.getString(14));
                insurancepolicyv.setIPV_V8(cursor.getString(15));
                insurancepolicyv.setIPV_HOLDER(Integer.parseInt(cursor.getString(16)));

                // Adding contact to list
                insurancepolicyvList.add(insurancepolicyv);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //     cursor.close();
            //     db.close();
        }
        // return contact list
        return insurancepolicyvList;
    }


    //**
    // * Delete from database
    // * @param IPV_ID
    // * @param IPV_HOLDER
    //  */

    public void deleteIPV_ID(int IPV_ID) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_ID + " = " + IPV_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);

    }

    public void deleteIPV_AID(int IPV_AID) {
    //    SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_AID + " = " + IPV_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);

    }

    public void deleteIPV_POID(int IPV_POID) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_POID + " = " + IPV_POID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);

    }

    public void deleteIPV_HOLDER(int IPV_HOLDER) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_HOLDER + " = " + IPV_HOLDER;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);

    }

    public void deleteIPV_IVID(int IPV_IVID) {
     //   SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_VEHICLE +
                " WHERE " + CIPV_IVID + " = " + IPV_IVID;
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
