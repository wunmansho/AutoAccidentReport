package com.auto.accident.report.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.objects.InsurancePolicy;
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

public class InsurancePolicyDao extends SQLiteOpenHelper {
    private static final String TABLE_INSURANCE_POLICY = "insurance_policy_table";
    private static final String CIPO_ID = "IPO_ID";
    private static final String CIPO_AID = "IPO_AID";
    private static final String CIPO_HOLDER = "IPO_HOLDER";  // IP_ID
    private static final String CIPO_CNAM = "IPO_CNAM";
    private static final String CIPO_PNUM = "IPO_PNUM";
    private static final String CIPO_CUID = "IPO_CUID";
    private static final String CIPO_CDATE = "IPO_CDATE";
    private static final String CIPO_UUID = "IPO_UUID";
    private static final String CIPO_UDATE = "IPO_UDATE";
    private static final String CIPO_V1 = "IPO_V1";
    private static final String CIPO_V2 = "IPO_V2";
    private static final String CIPO_V3 = "IPO_V3";
    private static final String CIPO_V4 = "IPO_V4";
    private static final String CIPO_V5 = "IPO_V5";
    private static final String CIPO_V6 = "IPO_V6";
    private static final String CIPO_V7 = "IPO_V7";
    private static final String CIPO_V8 = "IPO_V8";
    private final Context context;
    private InsurancePolicyPDao mInsurancePolicyPDao;
    private InsurancePolicyVDao mInsurancePolicyVDao;
    private InsurancePolicy insurancepolicy;
    private PersistenceObjDao mPersistenceObjDao;
    private String DA_ID_STR;

    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public InsurancePolicyDao(Context context) {
        super(context, TABLE_INSURANCE_POLICY, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);

         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
        mInsurancePolicyPDao = new InsurancePolicyPDao(context);
        mInsurancePolicyVDao = new InsurancePolicyVDao(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_INSURANCE_POLICY + "(" + CIPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIPO_AID + " INTEGER, " +
                CIPO_HOLDER + " INTEGER, " +
                CIPO_CNAM + " TEXT, " +
                CIPO_PNUM + " TEXT, " +
                CIPO_CUID + " INTEGER, " +
                CIPO_CDATE + " TEXT, " +
                CIPO_UUID + " INTEGER, " +
                CIPO_UDATE + " TEXT, " +
                CIPO_V1 + " TEXT, " +
                CIPO_V2 + " TEXT, " +
                CIPO_V3 + " TEXT, " +
                CIPO_V4 + " TEXT, " +
                CIPO_V5 + " TEXT, " +
                CIPO_V6 + " TEXT, " +
                CIPO_V7 + " TEXT, " +
                CIPO_V8 + " TEXT)";


        db.execSQL(createTable);
        String deviceLocale = Locale.getDefault().getLanguage();
        if (deviceLocale.equals("en")) {
            //       db.execSQL("insert INTO insurance_policy_table ('IPO_AID', 'IPO_HOLDER', 'IPO_CNAM', 'IPO_PNUM', 'IPO_VIN', 'IPO_YEAR', 'IPO_MAKE', 'IPO_MODEL', 'IPO_CUID', 'IPO_CDATE', 'IPO_UUID', 'IPO_UDATE') VALUES (1,'CVN-7791', 'Florida ', '2019', 'V-133721957', '2016 ', 'Chevrolet',  'Corvette', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //        db.execSQL("insert INTO insurance_policy_table ('IPO_AID', 'IPO_HOLDER', 'IPO_CNAM', 'IPO_PNUM', 'IPO_VIN', 'IPO_YEAR', 'IPO_MAKE', 'IPO_MODEL', 'IPO_CUID', 'IPO_CDATE', 'IPO_UUID', 'IPO_UDATE') VALUES (1,'RSN-3364', 'Florida ', '2019', 'V4541892311', '2016 ', 'Austin',  'Martin', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSURANCE_POLICY);
        onCreate(db);
    }

    public long addData(Integer IPO_AID, Integer IPO_HOLDER, String IPO_CNAM,
                        String IPO_PNUM, String IPO_V1, String IPO_V2, String IPO_V3,
                        String IPO_V4, String IPO_V5, String IPO_V6, String IPO_V7, String IPO_V8) {

        IPO_CNAM = utils.extractCharacter(IPO_CNAM);
        IPO_PNUM = utils.extractCharacter(IPO_PNUM);
        IPO_V1 = utils.extractCharacter(IPO_V1);
        IPO_V2 = utils.extractCharacter(IPO_V2);
        IPO_V3 = utils.extractCharacter(IPO_V3);
        IPO_V4 = utils.extractCharacter(IPO_V4);
        IPO_V5 = utils.extractCharacter(IPO_V5);
        IPO_V6 = utils.extractCharacter(IPO_V6);
        IPO_V7 = utils.extractCharacter(IPO_V7);
        IPO_V8 = utils.extractCharacter(IPO_V8);
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPO_CUID;
        if (isNumber(DA_ID_STR)) {
            IPO_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            IPO_CUID = 0;
        }
        String IPO_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

       // db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CIPO_AID, IPO_AID);
        contentValues.put(CIPO_HOLDER, IPO_HOLDER);
        contentValues.put(CIPO_CNAM, IPO_CNAM);
        contentValues.put(CIPO_PNUM, IPO_PNUM);
        contentValues.put(CIPO_CUID, IPO_CUID);
        contentValues.put(CIPO_CDATE, IPO_UDATE);
        contentValues.put(CIPO_UUID, IPO_CUID);
        contentValues.put(CIPO_UDATE, IPO_UDATE);
        contentValues.put(CIPO_V1, IPO_V1);
        contentValues.put(CIPO_V2, IPO_V2);
        contentValues.put(CIPO_V3, IPO_V3);
        contentValues.put(CIPO_V4, IPO_V4);
        contentValues.put(CIPO_V5, IPO_V5);
        contentValues.put(CIPO_V6, IPO_V6);
        contentValues.put(CIPO_V7, IPO_V7);
        contentValues.put(CIPO_V8, IPO_V8);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        return dbW.insert(TABLE_INSURANCE_POLICY, null, contentValues);
    }


    public void updateData(int IPO_ID, int IPO_AID, int IPO_HOLDER, String IPO_CNAM,
                           String IPO_PNUM, String IPO_V1, String IPO_V2, String IPO_V3, String IPO_V4,
                           String IPO_V5, String IPO_V6, String IPO_V7, String IPO_V8) {

        IPO_CNAM = utils.extractCharacter(IPO_CNAM);
        IPO_PNUM = utils.extractCharacter(IPO_PNUM);
        IPO_V1 = utils.extractCharacter(IPO_V1);
        IPO_V2 = utils.extractCharacter(IPO_V2);
        IPO_V3 = utils.extractCharacter(IPO_V3);
        IPO_V4 = utils.extractCharacter(IPO_V4);
        IPO_V5 = utils.extractCharacter(IPO_V5);
        IPO_V6 = utils.extractCharacter(IPO_V6);
        IPO_V7 = utils.extractCharacter(IPO_V7);
        IPO_V8 = utils.extractCharacter(IPO_V8);


        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPO_UUID;
        if (isNumber(DA_ID_STR)) {
            IPO_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            IPO_UUID = 0;
        }
        String IPO_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
       // db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_INSURANCE_POLICY +
                " SET " + CIPO_HOLDER + " = '" + IPO_HOLDER + "' , "
                + CIPO_CNAM + " = '" + IPO_CNAM + "' , "
                + CIPO_PNUM + " = '" + IPO_PNUM + "' , "
                + CIPO_UUID + " = '" + IPO_UUID + "' , "
                + CIPO_UDATE + " = '" + IPO_UDATE + "' , "
                + CIPO_V1 + " = '" + IPO_V1 + "' , "
                + CIPO_V2 + " = '" + IPO_V2 + "' , "
                + CIPO_V3 + " = '" + IPO_V3 + "' , "
                + CIPO_V4 + " = '" + IPO_V4 + "' , "
                + CIPO_V5 + " = '" + IPO_V5 + "' , "
                + CIPO_V6 + " = '" + IPO_V6 + "' , "
                + CIPO_V7 + " = '" + IPO_V7 + "' , "
                + CIPO_V8 + " = '" + IPO_V8 + "' "
                + "WHERE " + CIPO_ID + " = " + IPO_ID
                + " AND " + CIPO_AID + " = " + IPO_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    // Getting one Involved Vehicle
    public InsurancePolicy getInsurancePolicy(int IPO_ID, int IPO_AID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY +
                " WHERE " + CIPO_AID + " = " + IPO_AID +
                " AND " + CIPO_ID + " = " + IPO_ID;
      //  db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        insurancepolicy = new InsurancePolicy(Integer.parseInt(cursor != null ? cursor.getString(0) : null),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),
                cursor.getString(4),
                Integer.parseInt(cursor.getString(5)),
                cursor.getString(6),
                Integer.parseInt(cursor.getString(7)),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11),
                cursor.getString(12),
                cursor.getString(13),
                cursor.getString(14),
                cursor.getString(15),
                cursor.getString(16));
        // return shop
        //if (cursor != null && !cursor.isClosed()) {
        //    cursor.close();
        //   db.close();
        //  }
        return insurancepolicy;
    }

    // Getting All Insurance Policies for Holder
    public List<InsurancePolicy> getHolderInsurancePolicys(int AID_ID, int HOLDER_ID) {
        List<InsurancePolicy> insurancepolicyList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY +
                " WHERE " + CIPO_AID + " = '" + AID_ID + "'" +
                " AND " + CIPO_HOLDER + " = '" + HOLDER_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY;

    //    db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicy = new InsurancePolicy();
                insurancepolicy.setIPO_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicy.setIPO_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicy.setIPO_HOLDER(Integer.parseInt(cursor.getString(2)));
                insurancepolicy.setIPO_CNAM(cursor.getString(3));
                insurancepolicy.setIPO_PNUM(cursor.getString(4));
                insurancepolicy.setIPO_CUID(Integer.parseInt(cursor.getString(5)));
                insurancepolicy.setIPO_CDATE(cursor.getString(6));
                insurancepolicy.setIPO_UUID(Integer.parseInt(cursor.getString(7)));
                insurancepolicy.setIPO_UDATE(cursor.getString(8));
                insurancepolicy.setIPO_V1(cursor.getString(9));
                insurancepolicy.setIPO_V1(cursor.getString(10));
                insurancepolicy.setIPO_V1(cursor.getString(11));
                insurancepolicy.setIPO_V1(cursor.getString(12));
                insurancepolicy.setIPO_V1(cursor.getString(13));
                insurancepolicy.setIPO_V1(cursor.getString(14));
                insurancepolicy.setIPO_V1(cursor.getString(15));
                insurancepolicy.setIPO_V1(cursor.getString(16));


                // Adding contact to list
                insurancepolicyList.add(insurancepolicy);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return insurancepolicyList;
    }

    // Getting All Insurance Policies for the Current Accident
    public List<InsurancePolicy> getAccidentInsurancePolicys(int AID_ID) {
        List<InsurancePolicy> insurancepolicyList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY +
                " WHERE " + CIPO_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INSURANCE_POLICY;

      //  db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                insurancepolicy = new InsurancePolicy();
                insurancepolicy.setIPO_ID(Integer.parseInt(cursor.getString(0)));
                insurancepolicy.setIPO_AID(Integer.parseInt(cursor.getString(1)));
                insurancepolicy.setIPO_HOLDER(Integer.parseInt(cursor.getString(2)));
                insurancepolicy.setIPO_CNAM(cursor.getString(3));
                insurancepolicy.setIPO_PNUM(cursor.getString(4));
                insurancepolicy.setIPO_CUID(Integer.parseInt(cursor.getString(5)));
                insurancepolicy.setIPO_CDATE(cursor.getString(6));
                insurancepolicy.setIPO_UUID(Integer.parseInt(cursor.getString(7)));
                insurancepolicy.setIPO_UDATE(cursor.getString(8));
                insurancepolicy.setIPO_V1(cursor.getString(9));
                insurancepolicy.setIPO_V1(cursor.getString(10));
                insurancepolicy.setIPO_V1(cursor.getString(11));
                insurancepolicy.setIPO_V1(cursor.getString(12));
                insurancepolicy.setIPO_V1(cursor.getString(13));
                insurancepolicy.setIPO_V1(cursor.getString(14));
                insurancepolicy.setIPO_V1(cursor.getString(15));
                insurancepolicy.setIPO_V1(cursor.getString(16));


                // Adding contact to list
                insurancepolicyList.add(insurancepolicy);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            //   cursor.close();
            // db.close();
        }
        // return contact list
        return insurancepolicyList;
    }


    //**
    // * Delete from database
    // * @param IPO_ID
    // * @param IPO_HOLDER
    //  */

    public void deleteIPO_ID(int IPO_ID) {
     //   db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY +
                " WHERE " + CIPO_ID + " = " + IPO_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

        mInsurancePolicyPDao.deleteIPP_POID(IPO_ID);

        mInsurancePolicyVDao.deleteIPV_POID(IPO_ID);

    }

    public void deleteIPO_AID(int IPO_AID) {
       // db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY +
                " WHERE " + CIPO_AID + " = " + IPO_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

        mInsurancePolicyPDao.deleteIPP_AID(IPO_AID);

        mInsurancePolicyVDao.deleteIPV_AID(IPO_AID);

    }

    public void deleteIPO_HOLDER(int IPO_HOLDER) {
     //   dbW = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_INSURANCE_POLICY +
                " WHERE " + CIPO_HOLDER + " = " + IPO_HOLDER;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

        mInsurancePolicyPDao.deleteIPP_POID(IPO_HOLDER);

        mInsurancePolicyVDao.deleteIPV_HOLDER(IPO_HOLDER);
    }
    public void closeAll() {
        dbR.close();
        dbW.close();
        mInsurancePolicyPDao.closeAll();

    }

}