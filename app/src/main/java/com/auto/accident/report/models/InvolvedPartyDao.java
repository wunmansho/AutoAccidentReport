package com.auto.accident.report.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.objects.InsurancePolicyP;
import com.auto.accident.report.objects.InvolvedParty;
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

/**
 * Created by User on 2/28/2017.
 */

public class InvolvedPartyDao extends SQLiteOpenHelper {
    private static final String TAG = "InvolvedPartyDao";

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_INVOLVED_PARTY = "involved_party_table";
    private static final String CIP_ID = "IP_ID";
    private static final String CIP_AID = "IP_AID";
    private static final String CIP_FNAME = "IP_FNAME";
    private static final String CIP_MI = "IP_MI";
    private static final String CIP_LNAME = "IP_LNAME";
    private static final String CIP_LICENSE = "IP_LICENSE";
    private static final String CIP_LST = "IP_LST";
    private static final String CIP_PTYPE = "IP_PTYPE";
    private static final String CIP_ADDR1 = "IP_ADDR1";
    private static final String CIP_ADDR2 = "IP_ADDR2";
    private static final String CIP_CITY = "IP_CITY";
    private static final String CIP_ST = "IP_ST";
    private static final String CIP_ZIP = "IP_ZIP";
    private static final String CIP_EMAIL = "IP_EMAIL";
    private static final String CIP_PHON1 = "IP_PHON1";
    private static final String CIP_PHON2 = "IP_PHON2";
    private static final String CIP_PHON3 = "IP_PHON3";
    private static final String CIP_CNAM01 = "IP_CNAM01";
    private static final String CIP_PNUM01 = "IP_PNUM01";
    private static final String CIP_CNAM02 = "IP_CNAM02";
    private static final String CIP_PNUM02 = "IP_PNUM02";
    private static final String CIP_CNAM03 = "IP_CNAM03";  // use for allow email report
    private static final String CIP_PNUM03 = "IP_PNUM03";
    private static final String CIP_COMP = "IP_COMP";
    private static final String CIP_CUID = "IP_CUID";
    private static final String CIP_CDATE = "IP_CDATE";
    private static final String CIP_UUID = "IP_UUID";
    private static final String CIP_UDATE = "IP_UDATE";
    private static final String CIP_LICENSE_COUNTRY = "IP_LICENSE_COUNTRY";
    private static final String CIP_RESIDENT_COUNTRY = "IP_RESIDENT_COUNTRY";
    private static final String CIP_PHON1_COUNTRY = "IP_PHON1_COUNTRY";
    private static final String CIP_PHON2_COUNTRY = "IP_PHON2_COUNTRY";
    private static final String CIP_PHON3_COUNTRY = "IP_PHON3_COUNTRY";
    // CIP_ID, CIP_AID, CIP_FNAME, CIP_MI, CIP_LNAME, CIP_PTYPE, CIP_CNAM01, CIP_PNUM01, CIP_CNAM02, CIP_PNUM02, CIP_CNAM03, CIP_PNUM03, CIP_COMP, CIP_CUID, CIP_CDATE, CIP_UUID, CIP_UDATE
// IP_ID, IP_AID, IP_FNAME, IP_MI, IP_LNAME, IP_PTYPE,IP_CNAM01, IP_PNUM01, IP_CNAM02, IP_PNUM02, IP_CNAM03, IP_PNUM03, IP_COMP, IP_CUID, IP_CDATE, IP_UUID, IP_UDATE
//  Integer IP_ID, Integer IP_AID, String IP_FNAME, String IP_MI, String IP_LNAME, String IP_PTYPE, String IP_CNAM01, String IP_PNUM01, String IP_CNAM02, String IP_PNUM02, String IP_CNAM03, String IP_PNUM03, String IP_COMP, Integer IP_CUID, String IP_CDATE, Integer IP_UUID, String IP_UDATE
// Integer tieIP_ID, Integer tieIP_AID, String tieIP_FNAME, String tieIP_MI, String tieIP_LNAME, String spIP_PTYPE, String tieIP_CNAM01, String tieIP_PNUM01, String tieIP_CNAM02, String tieIP_PNUM02, String tieIP_CNAM03, String tieIP_PNUM03, String tieIP_COMP, Integer tieIP_CUID, String tvIP_CDATE, Integer tieIP_UUID, String tieIP_UDATE
    private final Context context;
    private String DA_RESULT;
    private InsurancePolicyPDao mInsurancePolicyPDao;
    private InsurancePolicyDao mInsurancePolicyDao;
    private PersistenceObjDao mPersistenceObjDao;
    private String DA_ID_STR;


    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
   private InsurancePolicyP insurancePolicyP;

   private PersistenceObj persistenceObj;
    private InvolvedParty involvedParty;

    public InvolvedPartyDao(Context context) {
        super(context, TABLE_INVOLVED_PARTY, null, 1);
        this.context = context;
        involvedParty = new InvolvedParty();
        persistenceObj = new PersistenceObj();
        insurancePolicyP = new InsurancePolicyP();

        mInsurancePolicyDao = new InsurancePolicyDao(context);
        mInsurancePolicyPDao = new InsurancePolicyPDao(context);
        mPersistenceObjDao = new PersistenceObjDao(context);
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_INVOLVED_PARTY + "(" + CIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIP_AID + " INTEGER NOT NULL DEFAULT 0, " +
                CIP_FNAME + " TEXT NOT NULL DEFAULT '', " +
                CIP_MI + " TEXT NOT NULL DEFAULT '', " +
                CIP_LNAME + " TEXT NOT NULL DEFAULT '', " +
                CIP_LICENSE + " TEXT NOT NULL DEFAULT '', " +
                CIP_LST + " TEXT NOT NULL DEFAULT '', " +
                CIP_ADDR1 + " TEXT NOT NULL DEFAULT '', " +
                CIP_ADDR2 + " TEXT NOT NULL DEFAULT '', " +
                CIP_CITY + " TEXT NOT NULL DEFAULT '', " +
                CIP_ST + " TEXT NOT NULL DEFAULT '', " +
                CIP_ZIP + " TEXT NOT NULL DEFAULT '', " +
                CIP_EMAIL + " TEXT NOT NULL DEFAULT '', " +
                CIP_PHON1 + " TEXT NOT NULL DEFAULT '', " +
                CIP_PHON2 + " TEXT NOT NULL DEFAULT '', " +
                CIP_PHON3 + " TEXT NOT NULL DEFAULT '', " +
                CIP_PTYPE + " TEXT NOT NULL DEFAULT '', " +
                CIP_CNAM01 + " TEXT NOT NULL DEFAULT '', " +
                CIP_PNUM01 + " TEXT NOT NULL DEFAULT '', " +
                CIP_CNAM02 + " TEXT NOT NULL DEFAULT '', " +
                CIP_PNUM02 + " TEXT NOT NULL DEFAULT '', " +
                CIP_CNAM03 + " TEXT NOT NULL DEFAULT '', " +
                CIP_PNUM03 + " TEXT NOT NULL DEFAULT '', " +
                CIP_COMP + " TEXT NOT NULL DEFAULT '', " +
                CIP_CUID + " INTEGER NOT NULL DEFAULT 0, " +
                CIP_CDATE + " TEXT NOT NULL DEFAULT '', " +
                CIP_UUID + " INTEGER NOT NULL DEFAULT 0, " +
                CIP_UDATE + " TEXT NOT NULL DEFAULT '', " +
                CIP_LICENSE_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CIP_RESIDENT_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CIP_PHON1_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CIP_PHON2_COUNTRY + " TEXT NOT NULL DEFAULT '', " +
                CIP_PHON3_COUNTRY + " TEXT NOT NULL DEFAULT '')";
        db.execSQL(createTable);

        String deviceLocale = Locale.getDefault().getLanguage();
        if (deviceLocale.equals("en")) {
            //       db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE') VALUES (1,'Callum N Reynolds','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Driver','GEICO', '1234', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //        db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE') VALUES (1,'Benedict T Reynolds','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Passenger','GEICO', '3333', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //        db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE') VALUES (1,'Nero O Reynolds','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Passenger','GEICO', '4444', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //    db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE', 'IP_LICENSE_COUNTRY', 'IP_RESIDENT_COUNTRY', 'IP_PHON1_COUNTRY', 'IP_PHON2_COUNTRY', 'IP_PHON3_COUNTRY') VALUES (1,'Nichole H Wilkins','','','F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Driver','GEICO', '5555', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2,'02/27/2017 6:47:27', 'US', 'US', 'US', 'US', 'US');");
            //     db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE', 'IP_LICENSE_COUNTRY', 'IP_RESIDENT_COUNTRY', 'IP_PHON1_COUNTRY', 'IP_PHON2_COUNTRY', 'IP_PHON3_COUNTRY') VALUES (1,'Veronica C Wilkins','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Passenger','GEICO', '7777', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27', 'US', 'US', 'US', 'US', 'US');");
        }
        if (deviceLocale.equals("es")) {
            //           db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE') VALUES (1,'Pedro Pérez','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Conductor','GEICO', '1234', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //            db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE') VALUES (1,'Gabriel Pérez','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Pasajero','GEICO', '3333', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //           db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE') VALUES (1,'Antônia Pérez','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Pasajero','GEICO', '4444', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //           db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE') VALUES (1,'Francisca Pérez','','', 'F255-921-50-094-0', 'Florida', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Pasajero','GEICO', '4444', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //       db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE', 'IP_LICENSE_COUNTRY', 'IP_RESIDENT_COUNTRY', 'IP_PHON1_COUNTRY', 'IP_PHON2_COUNTRY', 'IP_PHON3_COUNTRY') VALUES (1,'Victor Álvarez','','','F255-921-50-094-0', 'San Paulo', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Propietario/Conductor','Sul America', '987654321', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2,'02/27/2017 6:47:27', 'BR', 'BR', 'BR', 'BR', 'BR');");
            //      db.execSQL("insert INTO involved_party_table ('IP_AID', 'IP_FNAME', 'IP_MI', 'IP_LNAME', 'IP_LICENSE', 'IP_LST', 'IP_ADDR1', 'IP_ADDR2', 'IP_CITY', 'IP_ST', 'IP_ZIP', 'IP_EMAIL', 'IP_PHON1', 'IP_PHON2', 'IP_PHON3', 'IP_PTYPE', 'IP_CNAM01', 'IP_PNUM01', 'IP_CNAM02', 'IP_PNUM02', 'IP_CNAM03', 'IP_PNUM03', 'IP_COMP', 'IP_CUID', 'IP_CDATE', 'IP_UUID', 'IP_UDATE', 'IP_LICENSE_COUNTRY', 'IP_RESIDENT_COUNTRY', 'IP_PHON1_COUNTRY', 'IP_PHON2_COUNTRY', 'IP_PHON3_COUNTRY') VALUES (1,'Kaua Álvarez','','', 'F255-921-50-094-0', 'San Paulo', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'Pasajero','Sul America', '987654321', ' ', ' ', ' ', ' ', ' ', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27', 'BR', 'BR', 'BR', 'BR', 'BR');");

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOLVED_PARTY);
        onCreate(db);
    }

    public long addData(Integer IP_AID, String IP_FNAME, String IP_MI,
                        String IP_LNAME, String IP_LICENSE, String IP_LST, String tieIP_ADDR1, String tieIP_ADDR2, String tieIP_CITY,
                        String tieIP_ST, String tieIP_ZIP, String tieIP_EMAIL, String tieIP_PHON1,
                        String tieIP_PHON2, String tieIP_PHON3, String IP_PTYPE, String IP_CNAM01, String IP_PNUM01,
                        String IP_CNAM02, String IP_PNUM02, String IP_CNAM03, String IP_PNUM03,
                        String IP_COMP, String IP_LICENSE_COUNTRY, String IP_RESIDENT_COUNTRY, String IP_PHON1_COUNTRY, String IP_PHON2_COUNTRY, String IP_PHON3_COUNTRY) {

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IP_CUID;
        if (isNumber(DA_ID_STR)) {
            IP_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            IP_CUID = 0;
        }
        String IP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        IP_FNAME = utils.extractCharacter(IP_FNAME);
        IP_MI = utils.extractCharacter(IP_MI);
        IP_LNAME = utils.extractCharacter(IP_LNAME);
        IP_LICENSE = utils.extractCharacter(IP_LICENSE);
        IP_LST = utils.extractCharacter(IP_LST);
        tieIP_ADDR1 = utils.extractCharacter(tieIP_ADDR1);
        tieIP_ADDR2 = utils.extractCharacter(tieIP_ADDR2);
        tieIP_CITY = utils.extractCharacter(tieIP_CITY);
        tieIP_ST = utils.extractCharacter(tieIP_ST);
        tieIP_EMAIL = utils.extractCharacter(tieIP_EMAIL);
        tieIP_PHON1 = utils.extractCharacter(tieIP_PHON1);
        tieIP_PHON2 = utils.extractCharacter(tieIP_PHON2);
        tieIP_PHON3 = utils.extractCharacter(tieIP_PHON3);
        IP_PTYPE = utils.extractCharacter(IP_PTYPE);
        IP_CNAM01 = utils.extractCharacter(IP_CNAM01);
        IP_PNUM01 = utils.extractCharacter(IP_PNUM01);
        IP_CNAM02 = utils.extractCharacter(IP_CNAM02);
        IP_PNUM02 = utils.extractCharacter(IP_PNUM02);
        IP_CNAM03 = utils.extractCharacter(IP_CNAM03);
        IP_PNUM03 = utils.extractCharacter(IP_PNUM03);
        IP_COMP = utils.extractCharacter(IP_COMP);
        IP_LICENSE_COUNTRY = utils.extractCharacter(IP_LICENSE_COUNTRY);
        IP_RESIDENT_COUNTRY = utils.extractCharacter(IP_RESIDENT_COUNTRY);
        IP_PHON1_COUNTRY = utils.extractCharacter(IP_PHON1_COUNTRY);
        IP_PHON2_COUNTRY = utils.extractCharacter(IP_PHON2_COUNTRY);
        IP_PHON3_COUNTRY = utils.extractCharacter(IP_PHON3_COUNTRY);

        long result;
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);

            ContentValues contentValues = new ContentValues();
            contentValues.put(CIP_AID, IP_AID);
            contentValues.put(CIP_FNAME, IP_FNAME);
            contentValues.put(CIP_MI, IP_MI);
            contentValues.put(CIP_LNAME, IP_LNAME);
            contentValues.put(CIP_LICENSE, IP_LICENSE);
            contentValues.put(CIP_LST, IP_LST);
            contentValues.put(CIP_ADDR1, tieIP_ADDR1);
            contentValues.put(CIP_ADDR2, tieIP_ADDR2);
            contentValues.put(CIP_CITY, tieIP_CITY);
            contentValues.put(CIP_ST, tieIP_ST);
            contentValues.put(CIP_ZIP, tieIP_ZIP);
            contentValues.put(CIP_EMAIL, tieIP_EMAIL);
            contentValues.put(CIP_PHON1, tieIP_PHON1);
            contentValues.put(CIP_PHON2, tieIP_PHON2);
            contentValues.put(CIP_PHON3, tieIP_PHON3);
            contentValues.put(CIP_PTYPE, IP_PTYPE);
            contentValues.put(CIP_CNAM01, IP_CNAM01);
            contentValues.put(CIP_PNUM01, IP_PNUM01);
            contentValues.put(CIP_CNAM02, IP_CNAM02);
            contentValues.put(CIP_PNUM02, IP_PNUM02);
            contentValues.put(CIP_CNAM03, IP_CNAM03);
            contentValues.put(CIP_PNUM03, IP_PNUM03);
            contentValues.put(CIP_COMP, IP_COMP);
            contentValues.put(CIP_CUID, IP_CUID);
            contentValues.put(CIP_CDATE, IP_UDATE);
            contentValues.put(CIP_UUID, IP_CUID);
            contentValues.put(CIP_UDATE, IP_UDATE);

            contentValues.put(CIP_LICENSE_COUNTRY, IP_LICENSE_COUNTRY);
            contentValues.put(CIP_RESIDENT_COUNTRY, IP_RESIDENT_COUNTRY);
            contentValues.put(CIP_PHON1_COUNTRY, IP_PHON1_COUNTRY);
            contentValues.put(CIP_PHON2_COUNTRY, IP_PHON2_COUNTRY);
            contentValues.put(CIP_PHON3_COUNTRY, IP_PHON3_COUNTRY);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

            result = dbW.insert(TABLE_INVOLVED_PARTY, null, contentValues);


        return result;
    }


    public void updateData(int IP_ID, int IP_AID, String IP_FNAME, String IP_MI,
                           String IP_LNAME, String IP_LICENSE, String IP_LST, String IP_ADDR1, String IP_ADDR2, String IP_CITY,
                           String IP_ST, String IP_ZIP, String IP_EMAIL, String IP_PHON1, String IP_PHON2,
                           String IP_PHON3, String spIP_PTYPE, String IP_CNAM01, String IP_PNUM01,
                           String IP_CNAM02, String IP_PNUM02, String IP_CNAM03, String IP_PNUM03,
                           String IP_COMP, String IP_LICENSE_COUNTRY, String IP_RESIDENT_COUNTRY,
                           String IP_PHON1_COUNTRY, String IP_PHON2_COUNTRY, String IP_PHON3_COUNTRY) {
        IP_FNAME = utils.extractCharacter(IP_FNAME);
        IP_MI = utils.extractCharacter(IP_MI);
        IP_LNAME = utils.extractCharacter(IP_LNAME);
        IP_LICENSE = utils.extractCharacter(IP_LICENSE);
        IP_LST = utils.extractCharacter(IP_LST);
        IP_ADDR1 = utils.extractCharacter(IP_ADDR1);
        IP_ADDR2 = utils.extractCharacter(IP_ADDR2);
        IP_CITY = utils.extractCharacter(IP_CITY);
        IP_ST = utils.extractCharacter(IP_ST);
        IP_EMAIL = utils.extractCharacter(IP_EMAIL);
        IP_PHON1 = utils.extractCharacter(IP_PHON1);
        IP_PHON2 = utils.extractCharacter(IP_PHON2);
        IP_PHON3 = utils.extractCharacter(IP_PHON3);
        spIP_PTYPE = utils.extractCharacter(spIP_PTYPE);
        IP_CNAM01 = utils.extractCharacter(IP_CNAM01);
        IP_PNUM01 = utils.extractCharacter(IP_PNUM01);
        IP_CNAM02 = utils.extractCharacter(IP_CNAM02);
        IP_PNUM02 = utils.extractCharacter(IP_PNUM02);
        IP_CNAM03 = utils.extractCharacter(IP_CNAM03);
        IP_PNUM03 = utils.extractCharacter(IP_PNUM03);
        IP_COMP = utils.extractCharacter(IP_COMP);
        IP_LICENSE_COUNTRY = utils.extractCharacter(IP_LICENSE_COUNTRY);
        IP_RESIDENT_COUNTRY = utils.extractCharacter(IP_RESIDENT_COUNTRY);
        IP_PHON1_COUNTRY = utils.extractCharacter(IP_PHON1_COUNTRY);
        IP_PHON2_COUNTRY = utils.extractCharacter(IP_PHON2_COUNTRY);
        IP_PHON3_COUNTRY = utils.extractCharacter(IP_PHON3_COUNTRY);


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IP_UUID;
        if (isNumber(DA_ID_STR)) {
            IP_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            IP_UUID = 0;
        }
        String IP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
    //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);

            String query = "UPDATE " + TABLE_INVOLVED_PARTY +
                    " SET " + CIP_FNAME + " = '" + IP_FNAME + "' , "
                    + CIP_MI + " = '" + IP_MI + "' , "
                    + CIP_LNAME + " = '" + IP_LNAME + "' , "
                    + CIP_LICENSE + " = '" + IP_LICENSE + "' , "
                    + CIP_LST + " = '" + IP_LST + "' , "
                    + CIP_ADDR1 + " = '" + IP_ADDR1 + "' , "
                    + CIP_ADDR2 + " = '" + IP_ADDR2 + "' , "
                    + CIP_CITY + " = '" + IP_CITY + "' , "
                    + CIP_ST + " = '" + IP_ST + "' , "
                    + CIP_ZIP + " = '" + IP_ZIP + "' , "
                    + CIP_EMAIL + " = '" + IP_EMAIL + "' , "
                    + CIP_PHON1 + " = '" + IP_PHON1 + "' , "
                    + CIP_PHON2 + " = '" + IP_PHON2 + "' , "
                    + CIP_PHON3 + " = '" + IP_PHON3 + "' , "
                    + CIP_PTYPE + " = '" + spIP_PTYPE + "' , "
                    + CIP_CNAM01 + " = '" + IP_CNAM01 + "' , "
                    + CIP_PNUM01 + " = '" + IP_PNUM01 + "' , "
                    + CIP_CNAM02 + " = '" + IP_CNAM02 + "' , "
                    + CIP_PNUM02 + " = '" + IP_PNUM02 + "' , "
                    + CIP_CNAM03 + " = '" + IP_CNAM03 + "' , "
                    + CIP_PNUM03 + " = '" + IP_PNUM03 + "' , "
                    + CIP_COMP + " = '" + IP_COMP + "' , "
                    + CIP_UUID + " = '" + IP_UUID + "' , "
                    + CIP_UDATE + " = '" + IP_UDATE + "' , "
                    + CIP_LICENSE_COUNTRY + " = '" + IP_LICENSE_COUNTRY + "' , "
                    + CIP_RESIDENT_COUNTRY + " = '" + IP_RESIDENT_COUNTRY + "' , "
                    + CIP_PHON1_COUNTRY + " = '" + IP_PHON1_COUNTRY + "' , "
                    + CIP_PHON2_COUNTRY + " = '" + IP_PHON2_COUNTRY + "' , "
                    + CIP_PHON3_COUNTRY + " = '" + IP_PHON3_COUNTRY + "'"
                    + "WHERE " + CIP_ID + " = " + IP_ID
                    + " AND " + CIP_AID + " = " + IP_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
            dbW.execSQL(query);


    }
    public void updateDataName(int IP_ID, int IP_AID, String IP_FNAME) {
        IP_FNAME = utils.extractCharacter(IP_FNAME);



        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IP_UUID;
        if (isNumber(DA_ID_STR)) {
            IP_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            IP_UUID = 0;
        }
        String IP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
        //   SQLiteDatabase db = this.getWritableDatabase(xxx);

        String query = "UPDATE " + TABLE_INVOLVED_PARTY +
                " SET " + CIP_FNAME + " = '" + IP_FNAME + "'"
                + "WHERE " + CIP_ID + " = " + IP_ID
                + " AND " + CIP_AID + " = " + IP_AID;
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

    //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);

        String query = "SELECT * FROM " + TABLE_INVOLVED_PARTY;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        Cursor cursor = dbW.rawQuery(query, null);

      //  db.close();
        cursor.close();
        return cursor;
    }

    public InvolvedParty getInvolvedPartyFname(String IP_FNAME, int IP_AID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_PARTY +
                " WHERE " + CIP_AID + " = " + IP_AID +
                " AND " + CIP_FNAME + " = '" + IP_FNAME + "'";
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

            Cursor cursor = dbR.rawQuery(selectQuery, null);

            if (cursor != null) {
                try {
                    cursor.moveToFirst();

                    involvedParty = new InvolvedParty(Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)),
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
                            cursor.getString(23),
                            Integer.parseInt(cursor.getString(24)),
                            cursor.getString(25),
                            Integer.parseInt(cursor.getString(26)),
                            cursor.getString(27),
                            cursor.getString(28),
                            cursor.getString(29),
                            cursor.getString(30),
                            cursor.getString(31),
                            cursor.getString(32));

                } catch (CursorIndexOutOfBoundsException e) {

                } finally {
                    cursor.close();

                }
            }


        return involvedParty;
    }

    // Getting one Involved Party
    public InvolvedParty getInvolvedParty(int IP_ID, int IP_AID) {
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_PARTY +
                " WHERE " + CIP_AID + " = " + IP_AID +
                " AND " + CIP_ID + " = " + IP_ID;
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);

        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
            Cursor cursor = dbR.rawQuery(selectQuery, null);

            if (cursor != null) {
                try {
                    cursor.moveToFirst();

                    involvedParty = new InvolvedParty(Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)),
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
                            cursor.getString(23),
                            Integer.parseInt(cursor.getString(24)),
                            cursor.getString(25),
                            Integer.parseInt(cursor.getString(26)),
                            cursor.getString(27),
                            cursor.getString(28),
                            cursor.getString(29),
                            cursor.getString(30),
                            cursor.getString(31),
                            cursor.getString(32));

                } catch (CursorIndexOutOfBoundsException e) {

                } finally {
                    cursor.close();

                }
            }

        return involvedParty;
    }


    // Getting All Involved Parties
    public List<InvolvedParty> getAllInvolvedParties(int AID_ID) {
        List<InvolvedParty> involvedPartyList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  " + CIP_ID + "," + CIP_AID + "," + CIP_FNAME + "," + CIP_MI + "," + CIP_LNAME + "," +
                CIP_LICENSE + "," + CIP_LST + "," + CIP_ADDR1 + "," + CIP_ADDR2 + "," + CIP_CITY + "," + CIP_ST + "," +
                CIP_ZIP + "," + CIP_EMAIL + "," + CIP_PHON1 + "," + CIP_PHON2 + "," + CIP_PHON3 + "," + CIP_PTYPE + "," +
                CIP_CNAM01 + "," + CIP_PNUM01 + "," + CIP_CNAM02 + "," + CIP_PNUM02 + "," + CIP_CNAM03 + "," +
                CIP_PNUM03 + "," + CIP_COMP + "," + CIP_CUID + "," + CIP_CDATE + "," + CIP_UUID + "," + CIP_UDATE + "," +
                CIP_LICENSE_COUNTRY + "," + CIP_RESIDENT_COUNTRY + "," + CIP_PHON1_COUNTRY + "," + CIP_PHON2_COUNTRY + "," + CIP_PHON3_COUNTRY +
                " FROM " + TABLE_INVOLVED_PARTY +
                " WHERE " + CIP_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_PARTY;

     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        try {
            while (cursor.moveToNext()) {

                involvedParty = new InvolvedParty();
                involvedParty.setIP_ID(Integer.parseInt(cursor.getString(0)));
                involvedParty.setIP_AID(Integer.parseInt(cursor.getString(1)));
                involvedParty.setIP_FNAME(cursor.getString(2));
                involvedParty.setIP_MI(cursor.getString(3));
                involvedParty.setIP_LNAME(cursor.getString(4));
                involvedParty.setIP_LICENSE(cursor.getString(5));
                involvedParty.setIP_LST(cursor.getString(6));
                involvedParty.setIP_ADDR1(cursor.getString(7));
                involvedParty.setIP_ADDR2(cursor.getString(8));
                involvedParty.setIP_CITY(cursor.getString(9));
                involvedParty.setIP_ST(cursor.getString(10));
                involvedParty.setIP_ZIP(cursor.getString(11));
                involvedParty.setIP_EMAIL(cursor.getString(12));
                involvedParty.setIP_PHON1(cursor.getString(13));
                involvedParty.setIP_PHON2(cursor.getString(14));
                involvedParty.setIP_PHON3(cursor.getString(15));
                involvedParty.setIP_PTYPE(cursor.getString(16));
                involvedParty.setIP_CNAM01(cursor.getString(17));
                involvedParty.setIP_PNUM01(cursor.getString(18));
                involvedParty.setIP_CNAM02(cursor.getString(19));
                involvedParty.setIP_PNUM02(cursor.getString(20));
                involvedParty.setIP_CNAM03(cursor.getString(21));
                involvedParty.setIP_PNUM03(cursor.getString(22));
                involvedParty.setIP_COMP(cursor.getString(23));
                involvedParty.setIP_CUID(Integer.parseInt(cursor.getString(24)));
                involvedParty.setIP_CDATE(cursor.getString(25));
                involvedParty.setIP_UUID(Integer.parseInt(cursor.getString(26)));
                involvedParty.setIP_UDATE(cursor.getString(27));
                involvedParty.setIP_LICENSE_COUNTRY(cursor.getString(28));
                involvedParty.setIP_RESIDENT_COUNTRY(cursor.getString(29));
                involvedParty.setIP_PHON1_COUNTRY(cursor.getString(30));
                involvedParty.setIP_PHON2_COUNTRY(cursor.getString(31));
                involvedParty.setIP_PHON3_COUNTRY(cursor.getString(32));

                // Adding contact to list
                involvedPartyList.add(involvedParty);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
          //  db.close();
        }

        return involvedPartyList;
    }

    public List<InvolvedParty> getAllUninsuredInvolvedParties(int AID_ID) {
        List<InvolvedParty> involvedPartyList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  " + CIP_ID + "," + CIP_AID + "," + CIP_FNAME + "," + CIP_MI + "," + CIP_LNAME + "," +
                CIP_LICENSE + "," + CIP_LST + "," + CIP_ADDR1 + "," + CIP_ADDR2 + "," + CIP_CITY + "," + CIP_ST + "," +
                CIP_ZIP + "," + CIP_EMAIL + "," + CIP_PHON1 + "," + CIP_PHON2 + "," + CIP_PHON3 + "," + CIP_PTYPE + "," +
                CIP_CNAM01 + "," + CIP_PNUM01 + "," + CIP_CNAM02 + "," + CIP_PNUM02 + "," + CIP_CNAM03 + "," +
                CIP_PNUM03 + "," + CIP_COMP + "," + CIP_CUID + "," + CIP_CDATE + "," + CIP_UUID + "," + CIP_UDATE + "," +
                CIP_LICENSE_COUNTRY + "," + CIP_RESIDENT_COUNTRY + "," + CIP_PHON1_COUNTRY + "," + CIP_PHON2_COUNTRY + "," + CIP_PHON3_COUNTRY +
                " FROM " + TABLE_INVOLVED_PARTY +
                " WHERE " + CIP_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_PARTY;

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int IPP_POID;
        if (isNumber(DA_ID_STR)) {
            IPP_POID = Integer.parseInt(DA_ID_STR);
        } else {
            IPP_POID = 0;
        }
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
              Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        try {
            while (cursor.moveToNext()) {

                involvedParty = new InvolvedParty();
                int IP_ID = Integer.parseInt(cursor.getString(0));
                int DA_ID;
                insurancePolicyP = mInsurancePolicyPDao.getInsuredPerson(AID_ID, IPP_POID, IP_ID);
                if (insurancePolicyP != null) {
                  DA_ID   = insurancePolicyP.getIPP_IPID();
                } else {
                    DA_ID = 0;
                }

                    if (DA_ID != IP_ID) {
                        involvedParty.setIP_ID(Integer.parseInt(cursor.getString(0)));
                        involvedParty.setIP_AID(Integer.parseInt(cursor.getString(1)));
                        involvedParty.setIP_FNAME(cursor.getString(2));
                        involvedParty.setIP_MI(cursor.getString(3));
                        involvedParty.setIP_LNAME(cursor.getString(4));
                        involvedParty.setIP_LICENSE(cursor.getString(5));
                        involvedParty.setIP_LST(cursor.getString(6));
                        involvedParty.setIP_ADDR1(cursor.getString(7));
                        involvedParty.setIP_ADDR2(cursor.getString(8));
                        involvedParty.setIP_CITY(cursor.getString(9));
                        involvedParty.setIP_ST(cursor.getString(10));
                        involvedParty.setIP_ZIP(cursor.getString(11));
                        involvedParty.setIP_EMAIL(cursor.getString(12));
                        involvedParty.setIP_PHON1(cursor.getString(13));
                        involvedParty.setIP_PHON2(cursor.getString(14));
                        involvedParty.setIP_PHON3(cursor.getString(15));
                        involvedParty.setIP_PTYPE(cursor.getString(16));
                        involvedParty.setIP_CNAM01(cursor.getString(17));
                        involvedParty.setIP_PNUM01(cursor.getString(18));
                        involvedParty.setIP_CNAM02(cursor.getString(19));
                        involvedParty.setIP_PNUM02(cursor.getString(20));
                        involvedParty.setIP_CNAM03(cursor.getString(21));
                        involvedParty.setIP_PNUM03(cursor.getString(22));
                        involvedParty.setIP_COMP(cursor.getString(23));
                        involvedParty.setIP_CUID(Integer.parseInt(cursor.getString(24)));
                        involvedParty.setIP_CDATE(cursor.getString(25));
                        involvedParty.setIP_UUID(Integer.parseInt(cursor.getString(26)));
                        involvedParty.setIP_UDATE(cursor.getString(27));
                        involvedParty.setIP_LICENSE_COUNTRY(cursor.getString(28));
                        involvedParty.setIP_RESIDENT_COUNTRY(cursor.getString(29));
                        involvedParty.setIP_PHON1_COUNTRY(cursor.getString(30));
                        involvedParty.setIP_PHON2_COUNTRY(cursor.getString(31));
                        involvedParty.setIP_PHON3_COUNTRY(cursor.getString(32));

                        // Adding contact to list
                        involvedPartyList.add(involvedParty);
                    }

                }
            } finally{
                if (cursor != null && !cursor.isClosed())
                    cursor.close();
                // db.close();
            }

        return involvedPartyList;
    }


    // Getting All Involved Parties
    public List<InvolvedParty> getAllInvolvedPartiesAll() {
        List<InvolvedParty> involvedPartyList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_PARTY;
        //  String selectQuery = "SELECT  * FROM " + TABLE_INVOLVED_PARTY;

     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {

                involvedParty.setIP_ID(Integer.parseInt(cursor.getString(0)));
                involvedParty.setIP_AID(Integer.parseInt(cursor.getString(1)));
                involvedParty.setIP_FNAME(cursor.getString(2));
                involvedParty.setIP_MI(cursor.getString(3));
                involvedParty.setIP_LNAME(cursor.getString(4));
                involvedParty.setIP_LICENSE(cursor.getString(5));
                involvedParty.setIP_LST(cursor.getString(6));
                involvedParty.setIP_ADDR1(cursor.getString(7));
                involvedParty.setIP_ADDR2(cursor.getString(8));
                involvedParty.setIP_CITY(cursor.getString(9));
                involvedParty.setIP_ST(cursor.getString(10));
                involvedParty.setIP_ZIP(cursor.getString(11));
                involvedParty.setIP_EMAIL(cursor.getString(12));
                involvedParty.setIP_PHON1(cursor.getString(13));
                involvedParty.setIP_PHON2(cursor.getString(14));
                involvedParty.setIP_PHON3(cursor.getString(15));
                involvedParty.setIP_PTYPE(cursor.getString(16));
                involvedParty.setIP_CNAM01(cursor.getString(17));
                involvedParty.setIP_PNUM01(cursor.getString(18));
                involvedParty.setIP_CNAM02(cursor.getString(19));
                involvedParty.setIP_PNUM02(cursor.getString(20));
                involvedParty.setIP_CNAM03(cursor.getString(21));
                involvedParty.setIP_PNUM03(cursor.getString(22));
                involvedParty.setIP_COMP(cursor.getString(23));
                involvedParty.setIP_CUID(Integer.parseInt(cursor.getString(24)));
                involvedParty.setIP_CDATE(cursor.getString(25));
                involvedParty.setIP_UUID(Integer.parseInt(cursor.getString(26)));
                involvedParty.setIP_UDATE(cursor.getString(27));
                involvedParty.setIP_LICENSE_COUNTRY(cursor.getString(28));
                involvedParty.setIP_RESIDENT_COUNTRY(cursor.getString(29));
                involvedParty.setIP_PHON1_COUNTRY(cursor.getString(30));
                involvedParty.setIP_PHON2_COUNTRY(cursor.getString(31));
                involvedParty.setIP_PHON3_COUNTRY(cursor.getString(32));

                // Adding contact to list
                involvedPartyList.add(involvedParty);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
         //   db.close();
        }
        // return contact list
        return involvedPartyList;
    }


    /**
     * Returns only the IP_ID that matches the IP_FNAME passed in
     *
     * @param IP_FNAME
     * @return
     */
    public Cursor getItemID(String IP_FNAME) {
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT " + CIP_ID + " FROM " + TABLE_INVOLVED_PARTY +
                " WHERE " + CIP_FNAME + " = '" + IP_FNAME + "'";
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

    //**
    // * Delete from database
    // * @param IP_ID
    // * @param IP_FNAME
    //  */
    public void deleteIP_ID(int IP_ID, int IP_AID) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_INVOLVED_PARTY +
                " WHERE " + CIP_AID + " = " + IP_AID +
                " AND " + CIP_ID + " = " + IP_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

        mInsurancePolicyDao.deleteIPO_HOLDER(IP_ID);

        mInsurancePolicyPDao.deleteIPP_IPID(IP_ID);

    }

    public void deleteIP_AID(int IP_AID) {
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_INVOLVED_PARTY +
                " WHERE " + CIP_AID + " = " + IP_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

        mInsurancePolicyDao.deleteIPO_AID(IP_AID);


    }
    public void closeAll() {
        dbR.close();
        dbW.close();
        mInsurancePolicyDao.close();
        mInsurancePolicyPDao.close();
        mPersistenceObjDao.close();
    }

}