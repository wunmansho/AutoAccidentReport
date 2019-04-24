package com.auto.accident.report.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.auto.accident.report.objects.PremiumAdvertiser;
import com.auto.accident.report.util.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import net.sqlcipher.database.SQLiteFullException;
//import net.sqlcipher.CursorIndexOutOfBoundsException;
//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;


public class PremiumAdvertiserDao extends SQLiteOpenHelper {
    private PremiumAdvertiser premiumadvertiser;
    private static final String TAG = "PremiumAdvertiserDao";

    private static final String TABLE_NAME = "premium_advertisers_table";
    private static final String CPA_ID = "PA_ID";
    private static final String CPA_MENU = "PA_MENU";
    private static final String CPA_ADVERTISER = "PA_ADVERTISER";
    private static final String CPA_PHONE = "PA_PHONE";
    private static final String CPA_URL = "PA_URL";
    private static final String CPA_PATH = "PA_PATH";
    private static final String CPA_FILENAME = "PA_FILENAME";
    private File file;


    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public PremiumAdvertiserDao(Context context) {
        super(context, TABLE_NAME, null, 1);
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + CPA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CPA_MENU + " TEXT, " +
                CPA_ADVERTISER + " TEXT, " +
                CPA_PHONE + " TEXT, " +
                CPA_URL + " TEXT, " +
                CPA_PATH + " TEXT, " +
                CPA_FILENAME + " TEXT) ";


        db.execSQL(createTable);
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
        } else {
            // Locate the image folder in your SD Card
            String albumName = "Ads";
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), albumName);
            file.mkdirs();
        }

        if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            // Create a String array for FilePathStrings
            String[] filePathStrings = new String[listFile.length];
            String[] fileNameStrings = new String[listFile.length];
            int j;
            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file

                filePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                fileNameStrings[i] = listFile[i].getName();
                //   FileNameStrings[i] = "The Wiz";
                j = i;
                if (j >= 4) {
                    j = 0;
                }
                db.execSQL("insert INTO premium_advertisers_table ('PA_MENU', 'PA_ADVERTISER', 'PA_PHONE', 'PA_URL', 'PA_PATH' ,'PA_FILENAME') VALUES ('ATTORNEYS', 'ANON', '', '' ,'" + filePathStrings[i] + "','" + fileNameStrings[i] + "');");

            }
        }

        String en = "en";
        String es = "es";
        String fr = "fr";
        String ru = "ru";
        String zh = "zh";
        String de = "de";
        //        db.ExecSQL ("insert into checkLists (checkListID, checkListType,  checkListName, checkListDesc) values (0, 0, 'Widgeon','Widgeon Daysailer');");
        String deviceLocale = Locale.getDefault().getLanguage();
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


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String PA_MENU, String PA_ADVERTISER, String PA_PHONE, String PA_URL, String PA_PATH, String PA_FILENAME) {

       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CPA_MENU, PA_MENU);
        contentValues.put(CPA_ADVERTISER, PA_ADVERTISER);
        contentValues.put(CPA_PHONE, PA_PHONE);
        contentValues.put(CPA_URL, PA_URL);
        contentValues.put(CPA_PATH, PA_PATH);
        contentValues.put(CPA_FILENAME, PA_FILENAME);
        Log.d(TAG, "addData: Adding " + PA_MENU + " to " + TABLE_NAME);
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

    public PremiumAdvertiser getPremiumAdvertiser(int PA_ID) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +
                " WHERE " + CPA_ID + " = " + PA_ID + "'";
      ////  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getReadableDatabase(xxx);

        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
            Cursor cursor = dbR.rawQuery(selectQuery, null);

            if (cursor != null) {
                try {
                    cursor.moveToFirst();

                    premiumadvertiser = new PremiumAdvertiser(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6));
                } catch (CursorIndexOutOfBoundsException e) {

                } finally {
                    cursor.close();

                }
            }


        return premiumadvertiser;
    }

    public List<PremiumAdvertiser> getAllPremiumAdvertiser(int PA_ID) {
        List<PremiumAdvertiser> premiumadvertiserList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME +
                " WHERE " + CPA_ID + " = " + PA_ID + "'";
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);


        try {
            while (cursor.moveToNext()) {
                PremiumAdvertiser premiumadvertiser = new PremiumAdvertiser();
                premiumadvertiser.setPA_ID(Integer.parseInt(cursor.getString(0)));
                premiumadvertiser.setPA_MENU(cursor.getString(1));
                premiumadvertiser.setPA_ADVERTISER(cursor.getString(2));
                premiumadvertiser.setPA_PHONE(cursor.getString(3));
                premiumadvertiser.setPA_URL(cursor.getString(4));
                premiumadvertiser.setPA_PATH(cursor.getString(5));
                premiumadvertiser.setPA_FILENAME(cursor.getString(6));

                premiumadvertiserList.add(premiumadvertiser);
            }

        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
         //   db.close();

        }

        return premiumadvertiserList;
    }


    /**
     * Returns only the PA_ID that matches the PA_MENU passed in
     *
     * @param PA_MENU
     * @return
     */
    public List<PremiumAdvertiser> getPAByMenu(String PA_MENU) {
        List<PremiumAdvertiser> premiumadvertiserList = new ArrayList<>();
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        String selectQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + CPA_MENU + " = '" + PA_MENU + "'";

        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(selectQuery, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    PremiumAdvertiser premiumadvertiser = new PremiumAdvertiser();
                    premiumadvertiser.setPA_ID(Integer.parseInt(cursor.getString(0)));
                    premiumadvertiser.setPA_MENU(cursor.getString(1));
                    premiumadvertiser.setPA_ADVERTISER(cursor.getString(2));
                    premiumadvertiser.setPA_PHONE(cursor.getString(3));
                    premiumadvertiser.setPA_URL(cursor.getString(4));
                    premiumadvertiser.setPA_PATH(cursor.getString(5));
                    premiumadvertiser.setPA_FILENAME(cursor.getString(6));
                    premiumadvertiserList.add(premiumadvertiser);
                }

            } finally {
                if (cursor != null && !cursor.isClosed())
                    cursor.close();
             //   db.close();

            }
        }
        return premiumadvertiserList;
    }

    public void updateData(int PA_ID, String PA_MENU, String PA_ADVERTISER, String PA_PHONE,
                           String PA_URL, String PA_PATH, String PA_FILENAME) {
        PA_MENU = utils.extractCharacter(PA_MENU);
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_NAME +
                " SET " + CPA_MENU + " = '" + PA_MENU + "'" +
                " SET " + CPA_ADVERTISER + " = '" + PA_ADVERTISER + "'" +
                " SET " + CPA_PHONE + " = '" + PA_PHONE + "'" +
                " SET " + CPA_URL + " = '" + PA_URL + "'" +
                " SET " + CPA_PATH + " = '" + PA_PATH + "'" +
                " SET " + CPA_FILENAME + " = '" + PA_FILENAME + "'"
                + "WHERE " + CPA_ID + " = '" + PA_ID + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }


    public void deletePA_MENU(String PA_ID) {
    //    xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + CPA_ID + " = '" + PA_ID + "'";

        Log.d(TAG, "deletePA_MENU: query: " + query);
        Log.d(TAG, "deletePA_MENU: Deleting " + CPA_MENU + " from database.");
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);
    }

    public void deleteALL() {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);

        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + CPA_ID + " != " + 0;
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
