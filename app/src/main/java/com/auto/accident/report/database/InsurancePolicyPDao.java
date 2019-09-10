package com.auto.accident.report.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.model.InsurancePolicyP;
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
 * Created by myron on 6/7/2018.
 */

public class InsurancePolicyPDao extends SQLiteOpenHelper {

    private static final String TAG = "InsurancePolicyPDao";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_INSURANCE_POLICY_PEOPLE = "insurance_policy_people_table";
    private static final String CIPP_ID = "IPP_ID";
    private static final String CIPP_AID = "IPP_AID";
    private static final String CIPP_POID = "IPP_POID";
    private static final String CIPP_IPID = "IPP_IPID";
    private static final String CIPP_CUID = "IPP_CUID";
    private static final String CIPP_CDATE = "IPP_CDATE";
    private static final String CIPP_UUID = "IPP_UUID";
    private static final String CIPP_UDATE = "IPP_UDATE";
    private static final String CIPP_V1 = "IPP_V1";
    private static final String CIPP_V2 = "IPP_V2";
    private static final String CIPP_V3 = "IPP_V3";
    private static final String CIPP_V4 = "IPP_V4";
    private static final String CIPP_V5 = "IPP_V5";
    private static final String CIPP_V6 = "IPP_V6";
    private static final String CIPP_V7 = "IPP_V7";
    private static final String CIPP_V8 = "IPP_V8";
    private static final String CIPP_HOLDER = "IPP_HOLDER";
    private PersistenceObjDao mPersistenceObjDao;
    private final Context context;
    private InsurancePolicyP insurancepolicyp;
    private String DA_ID_STR;

    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public InsurancePolicyPDao(Context context) {
        super(context, TABLE_INSURANCE_POLICY_PEOPLE, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);

         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_INSURANCE_POLICY_PEOPLE + "(" + CIPP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIPP_AID + " INTEGER, " +              //  Accident ID Zero is a profile Copy only
                CIPP_POID + " INTEGER, " +
                CIPP_IPID + " INTEGER, " +
                CIPP_CUID + " INTEGER, " +
                CIPP_CDATE + " TEXT, " +
                CIPP_UUID + " INTEGER, " +
                CIPP_UDATE + " TEXT, " +
                CIPP_V1 + " TEXT, " +
                CIPP_V2 + " TEXT, " +
                CIPP_V3 + " TEXT, " +
                CIPP_V4 + " TEXT, " +
                CIPP_V5 + " TEXT, " +
                CIPP_V6 + " TEXT, " +
                CIPP_V7 + " TEXT, " +
                CIPP_V8 + " TEXT, " +
                CIPP_HOLDER + " INTEGER)";
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSURANCE_POLICY_PEOPLE);
        onCreate(db);
    }

    public long addData(Integer IPP_AID, Integer IPP_POID,
                        Integer IPP_IPID, String IPP_V1, String IPP_V2, String IPP_V3,
                        String IPP_V4, String IPP_V5, String IPP_V6, String IPP_V7, String IPP_V8, Integer IPP_HOLDER) {

        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPP_CUID;
        if (isNumber(DA_ID_STR)) {
            IPP_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            IPP_CUID = 0;
        }
        String IPP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        IPP_V1 = utils.extractCharacter(IPP_V1);
        IPP_V2 = utils.extractCharacter(IPP_V2);
        IPP_V3 = utils.extractCharacter(IPP_V3);
        IPP_V4 = utils.extractCharacter(IPP_V4);
        IPP_V5 = utils.extractCharacter(IPP_V5);
        IPP_V6 = utils.extractCharacter(IPP_V6);
        IPP_V7 = utils.extractCharacter(IPP_V7);
        IPP_V8 = utils.extractCharacter(IPP_V8);
       // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CIPP_AID, IPP_AID);
        contentValues.put(CIPP_POID, IPP_POID);
        contentValues.put(CIPP_IPID, IPP_IPID);
        contentValues.put(CIPP_CUID, IPP_CUID);
        contentValues.put(CIPP_CDATE, IPP_UDATE);
        contentValues.put(CIPP_UUID, IPP_CUID);
        contentValues.put(CIPP_UDATE, IPP_UDATE);
        contentValues.put(CIPP_V1, IPP_V1);
        contentValues.put(CIPP_V2, IPP_V2);
        contentValues.put(CIPP_V3, IPP_V3);
        contentValues.put(CIPP_V4, IPP_V4);
        contentValues.put(CIPP_V5, IPP_V5);
        contentValues.put(CIPP_V6, IPP_V6);
        contentValues.put(CIPP_V7, IPP_V7);
        contentValues.put(CIPP_V8, IPP_V8);
        contentValues.put(CIPP_HOLDER, IPP_HOLDER);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        return dbW.insert(TABLE_INSURANCE_POLICY_PEOPLE, null, contentValues);
    }


    public void updateData(int IPP_ID, int IPP_AID, int IPP_POID, int IPP_IPID, String IPP_V1, String IPP_V2, String IPP_V3,
                           String IPP_V4, String IPP_V5, String IPP_V6, String IPP_V7, String IPP_V8, Integer IPP_HOLDER) {
        IPP_V1 = utils.extractCharacter(IPP_V1);
        IPP_V2 = utils.extractCharacter(IPP_V2);
        IPP_V3 = utils.extractCharacter(IPP_V3);
        IPP_V4 = utils.extractCharacter(IPP_V4);
        IPP_V5 = utils.extractCharacter(IPP_V5);
        IPP_V6 = utils.extractCharacter(IPP_V6);
        IPP_V7 = utils.extractCharacter(IPP_V7);
        IPP_V8 = utils.extractCharacter(IPP_V8);

        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPP_UUID;
        if (isNumber(DA_ID_STR)) {
            IPP_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            IPP_UUID = 0;
        }
        String IPP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
     //   SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_INSURANCE_POLICY_PEOPLE +
                " SET " + CIPP_AID + " = '" + IPP_AID + "' , "
                + CIPP_POID + " = '" + IPP_POID + "' , "
                + CIPP_IPID + " = '" + IPP_IPID + "' , "
                + CIPP_UUID + " = '" + IPP_UUID + "' , "
                + CIPP_UDATE + " = '" + IPP_UDATE + "' , "
                + CIPP_V1 + " = '" + IPP_V1 + "' , "
                + CIPP_V2 + " = '" + IPP_V2 + "' , "
                + CIPP_V3 + " = '" + IPP_V3 + "' , "
                + CIPP_V4 + " = '" + IPP_V4 + "' , "
                + CIPP_V5 + " = '" + IPP_V5 + "' , "
                + CIPP_V6 + " = '" + IPP_V6 + "' , "
                + CIPP_V7 + " = '" + IPP_V7 + "' , "
                + CIPP_V8 + " = '" + IPP_V8 + "' , "
                + CIPP_HOLDER + " = '" + IPP_HOLDER + "' "
                + "WHERE " + CIPP_ID + " = " + IPP_ID
                + " AND " + CIPP_AID + " = " + IPP_AID;
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
        String query = "SELECT * FROM " + TABLE_INSURANCE_POLICY_PEOPLE;
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
    public InsurancePolicyP getInsurancePolicyP(int IPP_AID, int IPP_ID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_AID + " = " + IPP_AID +
                " AND " + CIPP_ID + " = " + IPP_ID;
       // SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            insurancepolicyp = new InsurancePolicyP(Integer.parseInt(cursor.getString(0)),
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
        //    dbR.close();

        }
        return insurancepolicyp;
    }

    public InsurancePolicyP getInsuredPerson(int IPP_AID, int IPP_POID, int IPP_IPID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_AID + " = " + IPP_AID +
                " AND " + CIPP_POID + " = " + IPP_POID +
                " AND " + CIPP_IPID + " = " + IPP_IPID;
     //   SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();

            insurancepolicyp = new InsurancePolicyP(Integer.parseInt(cursor.getString(0)),
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
           // db.close();

        }
        return insurancepolicyp;
    }

    // Getting All Involved Pars
    public List<InsurancePolicyP> getInsurancePolicyPs(int AID_ID, int IPP_POID) {
        List<InsurancePolicyP> insurancepolicypList = new ArrayList<>();
        // Select All QueryIPP_IPID
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_AID + " = " + AID_ID +
                " AND " + CIPP_POID + " = " + IPP_POID;


        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE;

        //SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicyp = new InsurancePolicyP();
                insurancepolicyp.setIPP_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicyp.setIPP_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicyp.setIPP_POID(Integer.parseInt(cursor.getString(2)));
                insurancepolicyp.setIPP_IPID(Integer.parseInt(cursor.getString(3)));
                insurancepolicyp.setIPP_CUID(Integer.parseInt(cursor.getString(4)));
                insurancepolicyp.setIPP_CDATE(cursor.getString(5));
                insurancepolicyp.setIPP_UUID(Integer.parseInt(cursor.getString(6)));
                insurancepolicyp.setIPP_UDATE(cursor.getString(7));
                insurancepolicyp.setIPP_V1(cursor.getString(8));
                insurancepolicyp.setIPP_V2(cursor.getString(9));
                insurancepolicyp.setIPP_V3(cursor.getString(10));
                insurancepolicyp.setIPP_V4(cursor.getString(11));
                insurancepolicyp.setIPP_V5(cursor.getString(12));
                insurancepolicyp.setIPP_V6(cursor.getString(13));
                insurancepolicyp.setIPP_V7(cursor.getString(14));
                insurancepolicyp.setIPP_V8(cursor.getString(15));
                insurancepolicyp.setIPP_HOLDER(Integer.parseInt(cursor.getString(16)));
                // Adding contact to list
                insurancepolicypList.add(insurancepolicyp);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return insurancepolicypList;
    }

    // Getting All Involved Pars
    public List<InsurancePolicyP> getAllInsurancePolicyPs(int AID_ID) {
        List<InsurancePolicyP> insurancepolicypList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE;

      //  SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicyp = new InsurancePolicyP();
                insurancepolicyp.setIPP_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicyp.setIPP_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicyp.setIPP_POID(Integer.parseInt(cursor.getString(2)));
                insurancepolicyp.setIPP_IPID(Integer.parseInt(cursor.getString(3)));
                insurancepolicyp.setIPP_CUID(Integer.parseInt(cursor.getString(4)));
                insurancepolicyp.setIPP_CDATE(cursor.getString(5));
                insurancepolicyp.setIPP_UUID(Integer.parseInt(cursor.getString(6)));
                insurancepolicyp.setIPP_UDATE(cursor.getString(7));
                insurancepolicyp.setIPP_V1(cursor.getString(8));
                insurancepolicyp.setIPP_V2(cursor.getString(9));
                insurancepolicyp.setIPP_V3(cursor.getString(10));
                insurancepolicyp.setIPP_V4(cursor.getString(11));
                insurancepolicyp.setIPP_V5(cursor.getString(12));
                insurancepolicyp.setIPP_V6(cursor.getString(13));
                insurancepolicyp.setIPP_V7(cursor.getString(14));
                insurancepolicyp.setIPP_V8(cursor.getString(15));
                insurancepolicyp.setIPP_HOLDER(Integer.parseInt(cursor.getString(16)));
                // Adding contact to list
                insurancepolicypList.add(insurancepolicyp);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return insurancepolicypList;
    }

    // Getting All Involved Pars
    public List<InsurancePolicyP> getAllInsurancePolicyPsAll() {
        List<InsurancePolicyP> insurancepolicypList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE;
        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY_PEOPLE;

      //  SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicyp = new InsurancePolicyP();
                insurancepolicyp.setIPP_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicyp.setIPP_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicyp.setIPP_POID(Integer.parseInt(cursor.getString(2)));
                insurancepolicyp.setIPP_IPID(Integer.parseInt(cursor.getString(3)));
                insurancepolicyp.setIPP_CUID(Integer.parseInt(cursor.getString(4)));
                insurancepolicyp.setIPP_CDATE(cursor.getString(5));
                insurancepolicyp.setIPP_UUID(Integer.parseInt(cursor.getString(6)));
                insurancepolicyp.setIPP_UDATE(cursor.getString(7));
                insurancepolicyp.setIPP_V1(cursor.getString(8));
                insurancepolicyp.setIPP_V2(cursor.getString(9));
                insurancepolicyp.setIPP_V3(cursor.getString(10));
                insurancepolicyp.setIPP_V4(cursor.getString(11));
                insurancepolicyp.setIPP_V5(cursor.getString(12));
                insurancepolicyp.setIPP_V6(cursor.getString(13));
                insurancepolicyp.setIPP_V7(cursor.getString(14));
                insurancepolicyp.setIPP_V8(cursor.getString(15));
                insurancepolicyp.setIPP_HOLDER(Integer.parseInt(cursor.getString(16)));

                // Adding contact to list
                insurancepolicypList.add(insurancepolicyp);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //     cursor.close();
            //     db.close();
        }
        // return contact list
        return insurancepolicypList;
    }


    //**
    // * Delete from database
    // * @param IPP_ID
    // * @param IPP_HOLDER
    //  */

    public void deleteIPP_ID(int IPP_ID) {
       // SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_ID + " = " + IPP_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteIPP_IPID(int IPP_IPID) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_IPID + " = " + IPP_IPID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteIPP_AID(int IPP_AID) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_AID + " = " + IPP_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteIPP_POID(int IPP_POID) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_POID + " = " + IPP_POID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteIPP_HOLDER(int IPP_HOLDER) {
      //  SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY_PEOPLE +
                " WHERE " + CIPP_HOLDER + " = " + IPP_HOLDER;
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
