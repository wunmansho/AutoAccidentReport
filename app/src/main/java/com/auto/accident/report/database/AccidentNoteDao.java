package com.auto.accident.report.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.model.AccidentNote;
import com.auto.accident.report.model.PersistenceObj;
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
 * Created by myron on 4/12/2018.
 */

public class AccidentNoteDao extends SQLiteOpenHelper {
    private static final String TAG = "AccidentNoteDao";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_ACCIDENT_NOTE = "accident_note_table";
    private static final String CAN_ID = "AN_ID";
    private static final String CAN_AID = "AN_AID";
    private static final String CAN_IPID = "AN_IPID";
    private static final String CAN_IVID = "AN_IVID";
    private static final String CAN_APPATH = "AN_APPATH";
    private static final String CAN_SUBJECT = "AN_SUBJECT";
    private static final String CAN_NOTE = "AN_NOTE";
    private static final String CAN_CUID = "AN_CUID";
    private static final String CAN_CDATE = "AN_CDATE";
    private static final String CAN_UUID = "AN_UUID";
    private static final String CAN_UDATE = "AN_UDATE";
    private final Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private String DA_ID_STR;
    private PersistenceObj persistenceObj;
    private AccidentNote accidentnote;
    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;

    public AccidentNoteDao(Context context) {
        super(context, TABLE_ACCIDENT_NOTE, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ACCIDENT_NOTE + "(" + CAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAN_AID + " INTEGER, " +
                CAN_IPID + " INTEGER, " +
                CAN_IVID + " INTEGER, " +
                CAN_APPATH + " TEXT, " +
                CAN_SUBJECT + " TEXT, " +
                CAN_NOTE + " TEXT, " +
                CAN_CUID + " INTEGER, " +
                CAN_CDATE + " TEXT, " +
                CAN_UUID + " INTEGER, " +
                CAN_UDATE + " TEXT)";
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENT_NOTE);
        onCreate(db);
    }


    public void dropDb() {
        // SQLiteDatabase db = this.getWritableDatabase();
        //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENT_NOTE);
    }

    public long addData(Integer AN_AID, Integer AN_IPID, Integer AN_IVID, String AN_APPATH,
                        String AN_SUBJECT, String AN_NOTE) {
        AN_APPATH = utils.extractCharacter(AN_APPATH);
        AN_SUBJECT = utils.extractCharacter(AN_SUBJECT);
        AN_NOTE = utils.extractCharacter(AN_NOTE);

        mPersistenceObjDao = new PersistenceObjDao(context);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");

        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int AN_CUID;
        if (isNumber(DA_ID_STR)) {
            AN_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            AN_CUID = 0;
        }

        String AN_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
          ContentValues contentValues = new ContentValues();
        contentValues.put(CAN_AID, AN_AID);
        contentValues.put(CAN_IPID, AN_IPID);
        contentValues.put(CAN_IVID, AN_IVID);
        contentValues.put(CAN_APPATH, AN_APPATH);
        contentValues.put(CAN_SUBJECT, AN_SUBJECT);
        contentValues.put(CAN_NOTE, AN_NOTE);
        contentValues.put(CAN_CUID, AN_CUID);
        contentValues.put(CAN_CDATE, AN_UDATE);
        contentValues.put(CAN_UUID, AN_CUID);
        contentValues.put(CAN_UDATE, AN_UDATE);


        return dbW.insert(TABLE_ACCIDENT_NOTE, null, contentValues);
    }


    public void updateData(int AN_ID, int AN_AID, int AN_IPID, int AN_IVID, String AN_APPATH,
                           String AN_SUBJECT, String AN_NOTE) {
        AN_APPATH = utils.extractCharacter(AN_APPATH);
        AN_SUBJECT = utils.extractCharacter(AN_SUBJECT);
        AN_NOTE = utils.extractCharacter(AN_NOTE);
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");

        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int AN_UUID;
        if (isNumber(DA_ID_STR)) {
            AN_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            AN_UUID = 0;
        }

        String AN_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_ACCIDENT_NOTE +
                " SET " + CAN_IPID + " = '" + AN_IPID + "' , "
                + CAN_IVID + " = '" + AN_IVID + "' , "
                + CAN_APPATH + " = '" + AN_APPATH + "' , "
                + CAN_SUBJECT + " = '" + AN_SUBJECT + "' , "
                + CAN_NOTE + " = '" + AN_NOTE + "' , "
                + CAN_UUID + " = '" + AN_UUID + "' , "
                + CAN_UDATE + " = '" + AN_UDATE + "'"
                + "WHERE " + CAN_ID + " = " + AN_ID
                + " AND " + CAN_AID + " = " + AN_AID;
        dbW.execSQL(query);
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_ACCIDENT_NOTE;
        Cursor cursor = dbW.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //  db.close();
        }
        return cursor;
    }


    // Getting one Involved Party
    public AccidentNote getAccidentNote(int AN_ID, int AN_AID) {
        String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AN_AID +
                " AND " + CAN_ID + " = " + AN_ID;
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
         if (!dbR.isOpen()) {
             dbR = this.getReadableDatabase();
         }
        Cursor cursor = dbR.rawQuery(selectQuery, null);


        try {
            cursor.moveToFirst();

            accidentnote = new AccidentNote(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    Integer.parseInt(cursor.getString(7)),
                    cursor.getString(8),
                    Integer.parseInt(cursor.getString(9)),
                    cursor.getString(10));

        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        //    dbR.close();
        }


        return accidentnote;
    }


    // Getting All Involved Parties
    public List<AccidentNote> getAllAccidentNotes(int AID_ID) {
        List<AccidentNote> accidentnoteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  " + CAN_ID + "," + CAN_AID + "," + CAN_IPID + "," + CAN_IVID + "," + CAN_APPATH + "," + CAN_SUBJECT + "," + CAN_NOTE + "," + CAN_CUID + "," + CAN_CDATE + "," + CAN_UUID + "," + CAN_UDATE + " FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_NOTE;

       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                accidentnote = new AccidentNote();
                accidentnote.setAN_ID(Integer.parseInt(cursor.getString(0)));
                accidentnote.setAN_AID(Integer.parseInt(cursor.getString(1)));
                accidentnote.setAN_IPID(Integer.parseInt(cursor.getString(2)));
                accidentnote.setAN_IVID(Integer.parseInt(cursor.getString(3)));
                accidentnote.setAN_APPATH(cursor.getString(4));
                accidentnote.setAN_SUBJECT(cursor.getString(5));
                accidentnote.setAN_NOTE(cursor.getString(6));
                accidentnote.setAN_CUID(Integer.parseInt(cursor.getString(7)));
                accidentnote.setAN_CDATE(cursor.getString(8));
                accidentnote.setAN_UUID(Integer.parseInt(cursor.getString(9)));
                accidentnote.setAN_UDATE(cursor.getString(10));
                accidentnoteList.add(accidentnote);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
       //     dbR.close();
        }
        // return contact list
        return accidentnoteList;
    }

    // Getting All Involved Parties
    public List<AccidentNote> getIPAccidentNotes(int AID_ID, int IP_ID) {
        List<AccidentNote> accidentnoteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  " + CAN_ID + "," + CAN_AID + "," + CAN_IPID + "," + CAN_IVID + "," + CAN_APPATH + "," + CAN_SUBJECT + "," + CAN_NOTE + "," + CAN_CUID + "," + CAN_CDATE + "," + CAN_UUID + "," + CAN_UDATE + " FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AID_ID +
                " AND   " + CAN_IPID + " = " + IP_ID;
        //  String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_NOTE;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                accidentnote = new AccidentNote();
                accidentnote.setAN_ID(Integer.parseInt(cursor.getString(0)));
                accidentnote.setAN_AID(Integer.parseInt(cursor.getString(1)));
                accidentnote.setAN_IPID(Integer.parseInt(cursor.getString(2)));
                accidentnote.setAN_IVID(Integer.parseInt(cursor.getString(3)));
                accidentnote.setAN_APPATH(cursor.getString(4));
                accidentnote.setAN_SUBJECT(cursor.getString(5));
                accidentnote.setAN_NOTE(cursor.getString(6));
                accidentnote.setAN_CUID(Integer.parseInt(cursor.getString(7)));
                accidentnote.setAN_CDATE(cursor.getString(8));
                accidentnote.setAN_UUID(Integer.parseInt(cursor.getString(9)));
                accidentnote.setAN_UDATE(cursor.getString(10));

                // Adding contact to list
                accidentnoteList.add(accidentnote);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        //    dbR.close();
        }
        return accidentnoteList;
    }

    public List<AccidentNote> getIVAccidentNotes(int AID_ID, int IV_ID) {
        List<AccidentNote> accidentnoteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  " + CAN_ID + "," + CAN_AID + "," + CAN_IPID + "," + CAN_IVID + "," + CAN_APPATH + "," + CAN_SUBJECT + "," + CAN_NOTE + "," + CAN_CUID + "," + CAN_CDATE + "," + CAN_UUID + "," + CAN_UDATE + " FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AID_ID +
                " AND   " + CAN_IVID + " = " + IV_ID;
        //  String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_NOTE;

       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                accidentnote = new AccidentNote();
                accidentnote.setAN_ID(Integer.parseInt(cursor.getString(0)));
                accidentnote.setAN_AID(Integer.parseInt(cursor.getString(1)));
                accidentnote.setAN_IPID(Integer.parseInt(cursor.getString(2)));
                accidentnote.setAN_IVID(Integer.parseInt(cursor.getString(3)));
                accidentnote.setAN_APPATH(cursor.getString(4));
                accidentnote.setAN_SUBJECT(cursor.getString(5));
                accidentnote.setAN_NOTE(cursor.getString(6));
                accidentnote.setAN_CUID(Integer.parseInt(cursor.getString(7)));
                accidentnote.setAN_CDATE(cursor.getString(8));
                accidentnote.setAN_UUID(Integer.parseInt(cursor.getString(9)));
                accidentnote.setAN_UDATE(cursor.getString(10));
                accidentnoteList.add(accidentnote);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
      //      dbR.close();
        }
        return accidentnoteList;
    }
    public List<AccidentNote> getUnattachedAccidentNotes(int AID_ID) {
        List<AccidentNote> accidentnoteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  " + CAN_ID + "," + CAN_AID + "," + CAN_IPID + "," + CAN_IVID + "," + CAN_APPATH + "," + CAN_SUBJECT + "," + CAN_NOTE + "," + CAN_CUID + "," + CAN_CDATE + "," + CAN_UUID + "," + CAN_UDATE + " FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AID_ID +
                " AND   " + CAN_IVID + " = 0" +
                " AND   " + CAN_IPID + " = 0" ;
        //  String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_NOTE;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                accidentnote = new AccidentNote();
                accidentnote.setAN_ID(Integer.parseInt(cursor.getString(0)));
                accidentnote.setAN_AID(Integer.parseInt(cursor.getString(1)));
                accidentnote.setAN_IPID(Integer.parseInt(cursor.getString(2)));
                accidentnote.setAN_IVID(Integer.parseInt(cursor.getString(3)));
                accidentnote.setAN_APPATH(cursor.getString(4));
                accidentnote.setAN_SUBJECT(cursor.getString(5));
                accidentnote.setAN_NOTE(cursor.getString(6));
                accidentnote.setAN_CUID(Integer.parseInt(cursor.getString(7)));
                accidentnote.setAN_CDATE(cursor.getString(8));
                accidentnote.setAN_UUID(Integer.parseInt(cursor.getString(9)));
                accidentnote.setAN_UDATE(cursor.getString(10));
                accidentnoteList.add(accidentnote);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            // dbR.close();
        }
        return accidentnoteList;
    }

    // Getting All Involved Parties
    public List<AccidentNote> getAllAccidentNotesAll() {
        List<AccidentNote> accidentnoteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_NOTE;
        //  String selectQuery = "SELECT  * FROM " + TABLE_ACCIDENT_NOTE;

   //     xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                accidentnote = new AccidentNote();
                accidentnote.setAN_ID(Integer.parseInt(cursor.getString(0)));
                accidentnote.setAN_AID(Integer.parseInt(cursor.getString(1)));
                accidentnote.setAN_IPID(Integer.parseInt(cursor.getString(2)));
                accidentnote.setAN_IVID(Integer.parseInt(cursor.getString(3)));
                accidentnote.setAN_APPATH(cursor.getString(4));
                accidentnote.setAN_SUBJECT(cursor.getString(5));
                accidentnote.setAN_NOTE(cursor.getString(6));
                accidentnote.setAN_CUID(Integer.parseInt(cursor.getString(7)));
                accidentnote.setAN_CDATE(cursor.getString(8));
                accidentnote.setAN_UUID(Integer.parseInt(cursor.getString(9)));
                accidentnote.setAN_UDATE(cursor.getString(10));
                accidentnoteList.add(accidentnote);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
         //   dbR.close();
        }
        return accidentnoteList;
    }


    public void deleteACCIDENT_NOTES(int AN_AID) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
       // SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AN_AID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }
    //**
    // * Delete from database
    // * @param AN_ID
    // * @param AN_IPID
    //  */

    public void deleteACCIDENT_NOTE(int AN_AID, int AN_ID) {
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AN_AID +
                " AND " + CAN_ID + " = " + AN_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteACCIDENT_NOTEIP(int AN_AID, int IP_ID) {
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AN_AID +
                " AND " + CAN_IPID + " = " + IP_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteACCIDENT_NOTEIV(int AN_AID, int IV_ID) {
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_ACCIDENT_NOTE +
                " WHERE " + CAN_AID + " = " + AN_AID +
                " AND " + CAN_IVID + " = " + IV_ID;
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
