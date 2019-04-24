package com.auto.accident.report.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.InvolvedParty;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.auto.accident.report.util.utils.isNumber;


/**
 * Created by myron on 1/18/2018.
 */

public class DeviceUserDao extends SQLiteOpenHelper {
    private static final String TAG = "DeviceUserDao";
    private InvolvedPartyDao mInvolvedPartyDao;
    private InvolvedParty involvedParty;
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_DEVICE_USER = "device_user_table";
    private static final String CDU_ID = "DU_ID";
    private static final String CDU_FNAME = "DU_FNAME";
    private static final String CDU_MI = "DU_MI";
    private static final String CDU_LNAME = "DU_LNAME";
    private static final String CDU_LICENSE = "DU_LICENSE";
    private static final String CDU_LST = "DU_LST";
    private static final String CDU_ADDR1 = "DU_ADDR1";
    private static final String CDU_ADDR2 = "DU_ADDR2";
    private static final String CDU_CITY = "DU_CITY";
    private static final String CDU_ST = "DU_ST";
    private static final String CDU_ZIP = "DU_ZIP";
    private static final String CDU_EMAIL = "DU_EMAIL";
    private static final String CDU_PHON1 = "DU_PHON1";
    private static final String CDU_PHON2 = "DU_PHON2";
    private static final String CDU_PHON3 = "DU_PHON3";
    private static final String CDU_PTYPE = "DU_PTYPE";
    private static final String CDU_CNAM01 = "DU_CNAM01";
    private static final String CDU_PNUM01 = "DU_PNUM01";
    private static final String CDU_CNAM02 = "DU_CNAM02";
    private static final String CDU_PNUM02 = "DU_PNUM02";
    private static final String CDU_CNAM03 = "DU_CNAM03";
    private static final String CDU_PNUM03 = "DU_PNUM03";
    private static final String CDU_COMP = "DU_COMP";
    private static final String CDU_CUID = "DU_CUID";
    private static final String CDU_CDATE = "DU_CDATE";
    private static final String CDU_UUID = "DU_UUID";
    private static final String CDU_UDATE = "DU_UDATE";
    private static final String CDU_LICENSE_COUNTRY = "DU_LICENSE_COUNTRY";
    private static final String CDU_RESIDENT_COUNTRY = "DU_RESIDENT_COUNTRY";
    private static final String CDU_PHON1_COUNTRY = "DU_PHON1_COUNTRY";
    private static final String CDU_PHON2_COUNTRY = "DU_PHON2_COUNTRY";
    private static final String CDU_PHON3_COUNTRY = "DU_PHON3_COUNTRY";
    // CDU_ID, CDU_FNAME, CDU_MI, CDU_LNAME, CDU_PTYPE, CDU_CNAM01, CDU_PNUM01, CDU_CNAM02, CDU_PNUM02, CDU_CNAM03, CDU_PNUM03, CDU_COMP, CDU_CUID, CDU_CDATE, CDU_UUID, CDU_UDATE
// DU_ID, DU_FNAME, DU_MI, DU_LNAME, DU_PTYPE,DU_CNAM01, DU_PNUM01, DU_CNAM02, DU_PNUM02, DU_CNAM03, DU_PNUM03, DU_COMP, DU_CUID, DU_CDATE, DU_UUID, DU_UDATE
//  Integer DU_ID, Integer String DU_FNAME, String DU_MI, String DU_LNAME, String DU_PTYPE, String DU_CNAM01, String DU_PNUM01, String DU_CNAM02, String DU_PNUM02, String DU_CNAM03, String DU_PNUM03, String DU_COMP, Integer DU_CUID, String DU_CDATE, Integer DU_UUID, String DU_UDATE
// Integer tieDU_ID, String tieDU_FNAME, String tieDU_MI, String tieDU_LNAME, String spDU_PTYPE, String tieDU_CNAM01, String tieDU_PNUM01, String tieDU_CNAM02, String tieDU_PNUM02, String tieDU_CNAM03, String tieDU_PNUM03, String tieDU_COMP, Integer tieDU_CUID, String tvDU_CDATE, Integer tieDU_UUID, String tieDU_UDATE
    private final Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private String DA_ID_STR;
    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;

    public DeviceUserDao(Context context) {
        super(context, TABLE_DEVICE_USER, null, 1);
        this.context = context;
        dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
        mPersistenceObjDao = new PersistenceObjDao(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DEVICE_USER + "(" + CDU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CDU_FNAME + " TEXT NOT NULL DEFAULT '', " +
                CDU_MI + " TEXT NOT NULL DEFAULT '', " +
                CDU_LNAME + " TEXT NOT NULL DEFAULT '', " +
                CDU_LICENSE + " TEXT NOT NULL DEFAULT '', " +
                CDU_LST + " TEXT NOT NULL DEFAULT '', " +
                CDU_ADDR1 + " TEXT NOT NULL DEFAULT '', " +
                CDU_ADDR2 + " TEXT NOT NULL DEFAULT '', " +
                CDU_CITY + " TEXT NOT NULL DEFAULT '', " +
                CDU_ST + " TEXT NOT NULL DEFAULT '', " +
                CDU_ZIP + " TEXT NOT NULL DEFAULT '', " +
                CDU_EMAIL + " TEXT NOT NULL DEFAULT '', " +
                CDU_PHON1 + " TEXT NOT NULL DEFAULT '', " +
                CDU_PHON2 + " TEXT NOT NULL DEFAULT '', " +
                CDU_PHON3 + " TEXT NOT NULL DEFAULT '', " +
                CDU_PTYPE + " TEXT NOT NULL DEFAULT '', " +
                CDU_CNAM01 + " TEXT NOT NULL DEFAULT '', " +
                CDU_PNUM01 + " TEXT NOT NULL DEFAULT '', " +
                CDU_CNAM02 + " TEXT NOT NULL DEFAULT '', " +
                CDU_PNUM02 + " TEXT NOT NULL DEFAULT '', " +
                CDU_CNAM03 + " TEXT NOT NULL DEFAULT '', " +
                CDU_PNUM03 + " TEXT NOT NULL DEFAULT '', " +
                CDU_COMP + " TEXT NOT NULL DEFAULT '', " +
                CDU_CUID + " INTEGER NOT NULL DEFAULT 0, " +
                CDU_CDATE + " TEXT NOT NULL DEFAULT '', " +
                CDU_UUID + " INTEGER NOT NULL DEFAULT 0, " +
                CDU_UDATE + " TEXT NOT NULL DEFAULT '', " +
                CDU_LICENSE_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CDU_RESIDENT_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CDU_PHON1_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CDU_PHON2_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CDU_PHON3_COUNTRY + " TEXT NOT NULL DEFAULT '')";


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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE_USER);
        onCreate(db);
    }

    public long addData(String tieDU_FNAME, String tieDU_MI,
                        String tieDU_LNAME, String tieDU_LICENSE, String tieDU_LST, String tieDU_ADDR1, String tieDU_ADDR2, String tieDU_CITY,
                        String tieDU_ST, String tieDU_ZIP, String tieDU_EMAIL, String tieDU_PHON1,
                        String tieDU_PHON2, String tieDU_PHON3, String spDU_PTYPE, String tieDU_CNAM01,
                        String tieDU_PNUM01, String tieDU_CNAM02, String tieDU_PNUM02,
                        String tieDU_CNAM03, String tieDU_PNUM03, String tieDU_COMP, String DU_LICENSE_COUNTRY,
                        String DU_RESIDENT_COUNTRY, String DU_PHON1_COUNTRY, String DU_PHON2_COUNTRY, String DU_PHON3_COUNTRY) {
        tieDU_FNAME = utils.extractCharacter(tieDU_FNAME);
        tieDU_MI = utils.extractCharacter(tieDU_MI);
        tieDU_LNAME = utils.extractCharacter(tieDU_LNAME);
        tieDU_LICENSE = utils.extractCharacter(tieDU_LICENSE);
        tieDU_LST = utils.extractCharacter(tieDU_LST);
        tieDU_ADDR1 = utils.extractCharacter(tieDU_ADDR1);
        tieDU_ADDR2 = utils.extractCharacter(tieDU_ADDR2);
        tieDU_CITY = utils.extractCharacter(tieDU_CITY);
        tieDU_ST = utils.extractCharacter(tieDU_ST);
        tieDU_EMAIL = utils.extractCharacter(tieDU_EMAIL);
        tieDU_PHON1 = utils.extractCharacter(tieDU_PHON1);
        tieDU_PHON2 = utils.extractCharacter(tieDU_PHON2);
        tieDU_PHON3 = utils.extractCharacter(tieDU_PHON3);
        spDU_PTYPE = utils.extractCharacter(spDU_PTYPE);
        tieDU_CNAM01 = utils.extractCharacter(tieDU_CNAM01);
        tieDU_PNUM01 = utils.extractCharacter(tieDU_PNUM01);
        tieDU_CNAM02 = utils.extractCharacter(tieDU_CNAM02);
        tieDU_PNUM02 = utils.extractCharacter(tieDU_PNUM02);
        tieDU_CNAM03 = utils.extractCharacter(tieDU_CNAM03);
        tieDU_PNUM03 = utils.extractCharacter(tieDU_PNUM03);
        tieDU_COMP = utils.extractCharacter(tieDU_COMP);
        DU_LICENSE_COUNTRY = utils.extractCharacter(DU_LICENSE_COUNTRY);
        DU_RESIDENT_COUNTRY = utils.extractCharacter(DU_RESIDENT_COUNTRY);
        DU_PHON1_COUNTRY = utils.extractCharacter(DU_PHON1_COUNTRY);
        DU_PHON2_COUNTRY = utils.extractCharacter(DU_PHON2_COUNTRY);
        DU_PHON3_COUNTRY = utils.extractCharacter(DU_PHON3_COUNTRY);



        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int DU_CUID;
        if (isNumber(DA_ID_STR)) {
            DU_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            DU_CUID = 0;
        }
        String DU_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
     //   SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CDU_FNAME, tieDU_FNAME);
        contentValues.put(CDU_MI, tieDU_MI);
        contentValues.put(CDU_LNAME, tieDU_LNAME);
        contentValues.put(CDU_LICENSE, tieDU_LICENSE);
        contentValues.put(CDU_LST, tieDU_LST);
        contentValues.put(CDU_ADDR1, tieDU_ADDR1);
        contentValues.put(CDU_ADDR2, tieDU_ADDR2);
        contentValues.put(CDU_CITY, tieDU_CITY);
        contentValues.put(CDU_ST, tieDU_ST);
        contentValues.put(CDU_ZIP, tieDU_ZIP);
        contentValues.put(CDU_EMAIL, tieDU_EMAIL);
        contentValues.put(CDU_PHON1, tieDU_PHON1);
        contentValues.put(CDU_PHON2, tieDU_PHON2);
        contentValues.put(CDU_PHON3, tieDU_PHON3);
        contentValues.put(CDU_PTYPE, spDU_PTYPE);
        contentValues.put(CDU_CNAM01, tieDU_CNAM01);
        contentValues.put(CDU_PNUM01, tieDU_PNUM01);
        contentValues.put(CDU_CNAM02, tieDU_CNAM02);
        contentValues.put(CDU_PNUM02, tieDU_PNUM02);
        contentValues.put(CDU_CNAM03, tieDU_CNAM03);
        contentValues.put(CDU_PNUM03, tieDU_PNUM03);
        contentValues.put(CDU_COMP, tieDU_COMP);
        contentValues.put(CDU_CUID, DU_CUID);
        contentValues.put(CDU_CDATE, DU_UDATE);
        contentValues.put(CDU_UUID, DU_CUID);
        contentValues.put(CDU_UDATE, DU_UDATE);
        contentValues.put(CDU_LICENSE_COUNTRY, DU_LICENSE_COUNTRY);
        contentValues.put(CDU_RESIDENT_COUNTRY, DU_RESIDENT_COUNTRY);
        contentValues.put(CDU_PHON1_COUNTRY, DU_PHON1_COUNTRY);
        contentValues.put(CDU_PHON2_COUNTRY, DU_PHON2_COUNTRY);
        contentValues.put(CDU_PHON3_COUNTRY, DU_PHON3_COUNTRY);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }


        long result = dbW.insert(TABLE_DEVICE_USER, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result;
    }


    public void updateData(String DU_ID, String DU_FNAME, String DU_MI,
                           String DU_LNAME, String DU_LICENSE, String DU_LST, String DU_ADDR1, String DU_ADDR2, String DU_CITY,
                           String DU_ST, String DU_ZIP, String DU_EMAIL, String DU_PHON1, String DU_PHON2,
                           String DU_PHON3, String spDU_PTYPE, String DU_CNAM01, String DU_PNUM01,
                           String DU_CNAM02, String DU_PNUM02, String DU_CNAM03, String DU_PNUM03,
                           String DU_COMP, String DU_LICENSE_COUNTRY,
                           String DU_RESIDENT_COUNTRY, String DU_PHON1_COUNTRY, String DU_PHON2_COUNTRY, String DU_PHON3_COUNTRY) {
        DU_FNAME = utils.extractCharacter(DU_FNAME);
        DU_MI = utils.extractCharacter(DU_MI);
        DU_LNAME = utils.extractCharacter(DU_LNAME);
        DU_LICENSE = utils.extractCharacter(DU_LICENSE);
        DU_LST = utils.extractCharacter(DU_LST);
        DU_ADDR1 = utils.extractCharacter(DU_ADDR1);
        DU_ADDR2 = utils.extractCharacter(DU_ADDR2);
        DU_CITY = utils.extractCharacter(DU_CITY);
        DU_ST = utils.extractCharacter(DU_ST);
        DU_EMAIL = utils.extractCharacter(DU_EMAIL);
        DU_PHON1 = utils.extractCharacter(DU_PHON1);
        DU_PHON2 = utils.extractCharacter(DU_PHON2);
        DU_PHON3 = utils.extractCharacter(DU_PHON3);
        spDU_PTYPE = utils.extractCharacter(spDU_PTYPE);
        DU_CNAM01 = utils.extractCharacter(DU_CNAM01);
        DU_PNUM01 = utils.extractCharacter(DU_PNUM01);
        DU_CNAM02 = utils.extractCharacter(DU_CNAM02);
        DU_PNUM02 = utils.extractCharacter(DU_PNUM02);
        DU_CNAM03 = utils.extractCharacter(DU_CNAM03);
        DU_PNUM03 = utils.extractCharacter(DU_PNUM03);
        DU_COMP = utils.extractCharacter(DU_COMP);
        DU_LICENSE_COUNTRY = utils.extractCharacter(DU_LICENSE_COUNTRY);
        DU_RESIDENT_COUNTRY = utils.extractCharacter(DU_RESIDENT_COUNTRY);
        DU_PHON1_COUNTRY = utils.extractCharacter(DU_PHON1_COUNTRY);
        DU_PHON2_COUNTRY = utils.extractCharacter(DU_PHON2_COUNTRY);
        DU_PHON3_COUNTRY = utils.extractCharacter(DU_PHON3_COUNTRY);



        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int DU_UUID;
        if (isNumber(DA_ID_STR)) {
            DU_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            DU_UUID = 0;
        }
        String DU_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());


        //SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_DEVICE_USER +
                " SET " + CDU_FNAME + " = '" + DU_FNAME + "' , "
                + CDU_MI + " = '" + DU_MI + "' , "
                + CDU_LNAME + " = '" + DU_LNAME + "' , "
                + CDU_LICENSE + " = '" + DU_LICENSE + "' , "
                + CDU_LST + " = '" + DU_LST + "' , "
                + CDU_ADDR1 + " = '" + DU_ADDR1 + "' , "
                + CDU_ADDR2 + " = '" + DU_ADDR2 + "' , "
                + CDU_CITY + " = '" + DU_CITY + "' , "
                + CDU_ST + " = '" + DU_ST + "' , "
                + CDU_ZIP + " = '" + DU_ZIP + "' , "
                + CDU_EMAIL + " = '" + DU_EMAIL + "' , "
                + CDU_PHON1 + " = '" + DU_PHON1 + "' , "
                + CDU_PHON2 + " = '" + DU_PHON2 + "' , "
                + CDU_PHON3 + " = '" + DU_PHON3 + "' , "
                + CDU_PTYPE + " = '" + spDU_PTYPE + "' , "
                + CDU_CNAM01 + " = '" + DU_CNAM01 + "' , "
                + CDU_PNUM01 + " = '" + DU_PNUM01 + "' , "
                + CDU_CNAM02 + " = '" + DU_CNAM02 + "' , "
                + CDU_PNUM02 + " = '" + DU_PNUM02 + "' , "
                + CDU_CNAM03 + " = '" + DU_CNAM03 + "' , "
                + CDU_PNUM03 + " = '" + DU_PNUM03 + "' , "
                + CDU_COMP + " = '" + DU_COMP + "' , "
                + CDU_UUID + " = '" + DU_UUID + "' , "
                + CDU_UDATE + " = '" + DU_UDATE + "' , "
                + CDU_LICENSE_COUNTRY + " = '" + DU_LICENSE_COUNTRY + "' , "
                + CDU_RESIDENT_COUNTRY + " = '" + DU_RESIDENT_COUNTRY + "' , "
                + CDU_PHON1_COUNTRY + " = '" + DU_PHON1_COUNTRY + "' , "
                + CDU_PHON2_COUNTRY + " = '" + DU_PHON2_COUNTRY + "' , "
                + CDU_PHON3_COUNTRY + " = '" + DU_PHON3_COUNTRY + "'"
                + "WHERE " + CDU_ID + " = '" + DU_ID + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);
    }
    public void updateDataName(int DU_ID, String DU_FNAME) {
        DU_FNAME = utils.extractCharacter(DU_FNAME);



        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int DU_UUID;
        if (isNumber(DA_ID_STR)) {
            DU_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            DU_UUID = 0;
        }
        String DU_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

        String query = "UPDATE " + TABLE_DEVICE_USER +
                " SET " + CDU_FNAME + " = '" + DU_FNAME + "'"
                + "WHERE " + CDU_ID + " = " + DU_ID;
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
        //SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DEVICE_USER;
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
            //  cursor.close();
            //   db.close();
        }
        return cursor;
    }

    // Getting one Device User
    public DeviceUser getDeviceUser(int DU_ID) {
        //SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.query(TABLE_DEVICE_USER, new String[]{CDU_ID, CDU_FNAME,
                        CDU_MI, CDU_LNAME, CDU_LICENSE, CDU_LST, CDU_ADDR1, CDU_ADDR2, CDU_CITY, CDU_ST, CDU_ZIP, CDU_EMAIL,
                        CDU_PHON1, CDU_PHON2, CDU_PHON3, CDU_PTYPE, CDU_CNAM01, CDU_PNUM01, CDU_CNAM02,
                        CDU_PNUM02, CDU_CNAM03, CDU_PNUM03, CDU_COMP, CDU_CUID, CDU_CDATE, CDU_UUID,
                        CDU_UDATE, CDU_LICENSE_COUNTRY, CDU_RESIDENT_COUNTRY, CDU_PHON1_COUNTRY, CDU_PHON2_COUNTRY, CDU_PHON3_COUNTRY},

                CDU_ID + "=?",
                new String[]{String.valueOf(DU_ID)}, null, null, null, null);
        DeviceUser deviceuser;
        try {
            cursor.moveToFirst();
            deviceuser = new DeviceUser(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),
                    cursor.getString(21),
                    cursor.getString(22),
                    Integer.parseInt(cursor.getString(23)),
                    cursor.getString(24),
                    Integer.parseInt(cursor.getString(25)),
                    cursor.getString(26),
                    cursor.getString(27),
                    cursor.getString(28),
                    cursor.getString(29),
                    cursor.getString(30),
                    cursor.getString(31));


        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
           // db.close();
        }
        return deviceuser;
    }

    public DeviceUser getDeviceUserByFname(String DU_FNAME) {
        //SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.query(TABLE_DEVICE_USER, new String[]{CDU_ID, CDU_FNAME,
                        CDU_MI, CDU_LNAME, CDU_LICENSE, CDU_LST, CDU_ADDR1, CDU_ADDR2, CDU_CITY, CDU_ST, CDU_ZIP, CDU_EMAIL,
                        CDU_PHON1, CDU_PHON2, CDU_PHON3, CDU_PTYPE, CDU_CNAM01, CDU_PNUM01, CDU_CNAM02,
                        CDU_PNUM02, CDU_CNAM03, CDU_PNUM03, CDU_COMP, CDU_CUID, CDU_CDATE, CDU_UUID,
                        CDU_UDATE, CDU_LICENSE_COUNTRY, CDU_RESIDENT_COUNTRY, CDU_PHON1_COUNTRY, CDU_PHON2_COUNTRY, CDU_PHON3_COUNTRY},

                CDU_FNAME + "=?",
                new String[]{String.valueOf(DU_FNAME)}, null, null, null, null);
        DeviceUser deviceuser;
        try {
            cursor.moveToFirst();
            deviceuser = new DeviceUser(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),
                    cursor.getString(21),
                    cursor.getString(22),
                    Integer.parseInt(cursor.getString(23)),
                    cursor.getString(24),
                    Integer.parseInt(cursor.getString(25)),
                    cursor.getString(26),
                    cursor.getString(27),
                    cursor.getString(28),
                    cursor.getString(29),
                    cursor.getString(30),
                    cursor.getString(31));


        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
           // db.close();
        }
        return deviceuser;
    }


    // Getting All Device Users
    public List<DeviceUser> getAllDeviceUsers() {
        List<DeviceUser> DeviceUserList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_USER;

        //SQLiteDatabase db = this.getWritableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                DeviceUser deviceuser = new DeviceUser();
                deviceuser.setDU_ID(Integer.parseInt(cursor.getString(0)));
                deviceuser.setDU_FNAME(cursor.getString(1));
                deviceuser.setDU_MI(cursor.getString(2));
                deviceuser.setDU_LNAME(cursor.getString(3));
                deviceuser.setDU_LICENSE(cursor.getString(4));
                deviceuser.setDU_LST(cursor.getString(5));
                deviceuser.setDU_ADDR1(cursor.getString(6));
                deviceuser.setDU_ADDR2(cursor.getString(7));
                deviceuser.setDU_CITY(cursor.getString(8));
                deviceuser.setDU_ST(cursor.getString(9));
                deviceuser.setDU_ZIP(cursor.getString(10));
                deviceuser.setDU_EMAIL(cursor.getString(11));
                deviceuser.setDU_PHON1(cursor.getString(12));
                deviceuser.setDU_PHON2(cursor.getString(13));
                deviceuser.setDU_PHON3(cursor.getString(14));
                deviceuser.setDU_PTYPE(cursor.getString(15));
                deviceuser.setDU_CNAM01(cursor.getString(16));
                deviceuser.setDU_PNUM01(cursor.getString(17));
                deviceuser.setDU_CNAM02(cursor.getString(18));
                deviceuser.setDU_PNUM02(cursor.getString(19));
                deviceuser.setDU_CNAM03(cursor.getString(20));
                deviceuser.setDU_PNUM03(cursor.getString(21));
                deviceuser.setDU_COMP(cursor.getString(22));
                deviceuser.setDU_CUID(Integer.parseInt(cursor.getString(23)));
                deviceuser.setDU_CDATE(cursor.getString(24));
                deviceuser.setDU_UUID(Integer.parseInt(cursor.getString(25)));
                deviceuser.setDU_UDATE(cursor.getString(26));
                deviceuser.setDU_LICENSE_COUNTRY(cursor.getString(27));
                deviceuser.setDU_RESIDENT_COUNTRY(cursor.getString(28));
                deviceuser.setDU_PHON1_COUNTRY(cursor.getString(29));
                deviceuser.setDU_PHON2_COUNTRY(cursor.getString(30));
                deviceuser.setDU_PHON3_COUNTRY(cursor.getString(31));
                // Adding contact to list
                DeviceUserList.add(deviceuser);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            //db.close();
        }
        // return device user list
        return DeviceUserList;
    }

    public List<String> getAllDeviceUsersFname() {
        List<String> DeviceUserList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_USER;

        //SQLiteDatabase db = this.getWritableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {

                DeviceUserList.add(cursor.getString(1));
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            //db.close();
        }
        // return device user list
        return DeviceUserList;
    }

    public List<DeviceUser> getAllDeviceUsersNotInvolved(int AID_ID) {
        List<DeviceUser> DeviceUserList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICE_USER;

        //SQLiteDatabase db = this.getWritableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                String IP_FNAME = cursor.getString(1);
                mInvolvedPartyDao = new InvolvedPartyDao(context);
                involvedParty = mInvolvedPartyDao.getInvolvedPartyFname(IP_FNAME, AID_ID);
                int contents = involvedParty.getIP_AID();
                if (contents == 0) {
                    DeviceUser deviceuser = new DeviceUser();
                    deviceuser.setDU_ID(Integer.parseInt(cursor.getString(0)));
                    deviceuser.setDU_FNAME(cursor.getString(1));
                    deviceuser.setDU_MI(cursor.getString(2));
                    deviceuser.setDU_LNAME(cursor.getString(3));
                    deviceuser.setDU_LICENSE(cursor.getString(4));
                    deviceuser.setDU_LST(cursor.getString(5));
                    deviceuser.setDU_ADDR1(cursor.getString(6));
                    deviceuser.setDU_ADDR2(cursor.getString(7));
                    deviceuser.setDU_CITY(cursor.getString(8));
                    deviceuser.setDU_ST(cursor.getString(9));
                    deviceuser.setDU_ZIP(cursor.getString(10));
                    deviceuser.setDU_EMAIL(cursor.getString(11));
                    deviceuser.setDU_PHON1(cursor.getString(12));
                    deviceuser.setDU_PHON2(cursor.getString(13));
                    deviceuser.setDU_PHON3(cursor.getString(14));
                    deviceuser.setDU_PTYPE(cursor.getString(15));
                    deviceuser.setDU_CNAM01(cursor.getString(16));
                    deviceuser.setDU_PNUM01(cursor.getString(17));
                    deviceuser.setDU_CNAM02(cursor.getString(18));
                    deviceuser.setDU_PNUM02(cursor.getString(19));
                    deviceuser.setDU_CNAM03(cursor.getString(20));
                    deviceuser.setDU_PNUM03(cursor.getString(21));
                    deviceuser.setDU_COMP(cursor.getString(22));
                    deviceuser.setDU_CUID(Integer.parseInt(cursor.getString(23)));
                    deviceuser.setDU_CDATE(cursor.getString(24));
                    deviceuser.setDU_UUID(Integer.parseInt(cursor.getString(25)));
                    deviceuser.setDU_UDATE(cursor.getString(26));
                    deviceuser.setDU_LICENSE_COUNTRY(cursor.getString(27));
                    deviceuser.setDU_RESIDENT_COUNTRY(cursor.getString(28));
                    deviceuser.setDU_PHON1_COUNTRY(cursor.getString(29));
                    deviceuser.setDU_PHON2_COUNTRY(cursor.getString(30));
                    deviceuser.setDU_PHON3_COUNTRY(cursor.getString(31));
                    // Adding contact to list
                    DeviceUserList.add(deviceuser);
                }
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            mInvolvedPartyDao.closeAll();
            //db.close();
        }
        // return device user list
        return DeviceUserList;
    }


    //**
    // * Returns only the DU_ID that matches the DU_FNAME passed in
    // * @param DU_FNAME
    //  * @return
    //  */
    public Cursor getDeviceUser(String DU_ID) {
       // SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DEVICE_USER +
                " WHERE " + CDU_ID + " = '" + DU_ID + "'";
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(query, null);
        try {
            cursor.moveToFirst();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
          //  db.close();
        }
        return cursor;
    }


    //**
    // * Delete from database
    // * @param DU_ID
    // * @param DU_FNAME
    //  */
    public void deleteDU_ID(String DU_ID) {
        //SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_DEVICE_USER + " WHERE "
                + CDU_ID + " = '" + DU_ID + "'";
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
