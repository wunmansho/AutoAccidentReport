package com.auto.accident.report.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.auto.accident.report.model.InvolvedImageStore;
import com.auto.accident.report.model.PersistenceObj;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auto.accident.report.util.utils.isNumber;

////import net.sqlcipher.database.SQLiteFullException;
////import net.sqlcipher.CursorIndexOutOfBoundsException;
//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;


public class InvolvedImageStoreDao extends SQLiteOpenHelper {
    private static final String TAG = "InvolvedImageStoreDao";
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_ACCIDENT_PICS = "accident_pics_table";
    private static final String CAP_ID = "AP_ID";
    private static final String CAP_AID = "AP_AID";
    private static final String CAP_IPID = "AP_IPID";
    private static final String CAP_CATEGORY = "AP_CATEGORY";
    private static final String CAP_PATH = "AP_PATH";
    private static final String CAP_FILENAME = "AP_FILENAME";
    private static final String CAP_CUID = "AP_CUID";
    private static final String CAP_CDATE = "AP_CDATE";
    private static final String CAP_UUID = "AP_UUID";
    private static final String CAP_UDATE = "AP_UDATE";
    private final Context context;
    private PersistenceObjDao mPersistenceObjDao;

    private File file;
    private String DA_ID_STR;

    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;

    //   mPersistenceObjDao = new PersistenceObjDao();
    //  PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");

    public InvolvedImageStoreDao(Context context) {
        super(context, TABLE_ACCIDENT_PICS, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    // private int    AP_ID;
    // private int    AP_AID;
    // private int    AP_IPID;
    // private String AP_PATH;
    // private String AP_FILENAME;
    // private int    AP_CUID;
    // private String AP_CDATE;
    // private int    AP_UUID;
    // private String AP_UDATE;
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ACCIDENT_PICS + "(" + CAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAP_AID + " INTEGER, " +
                CAP_IPID + " INTEGER, " +
                CAP_CATEGORY + " TEXT, " +
                CAP_PATH + " TEXT, " +
                CAP_FILENAME + " TEXT, " +
                CAP_CUID + " INTEGER, " +
                CAP_CDATE + " TEXT, " +
                CAP_UUID + " INTEGER, " +
                CAP_UDATE + " TEXT) ";
        db.execSQL(createTable);


        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
        } else {
            // Locate the image folder in your SD Card
            String albumName = "AccidentReport";
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), albumName);
            file.mkdirs();
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIDENT_PICS);
        onCreate(db);
    }


    public long addData(String AP_PATH, String AP_FILENAME) {
        //   genPics();
        int AP_CUID, AP_UUID, AP_AID, AP_IPID;
        String AP_CDATE, AP_CATEGORY;
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            AP_AID = Integer.parseInt(DA_ID_STR);
        } else {
            AP_AID = 0;
        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        AP_CATEGORY = persistenceObj.getPERSISTENCE_VALUE();
        AP_IPID = 0;
        if (AP_CATEGORY.equals("INVOLVED_PARTY")) {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                AP_IPID = Integer.parseInt(DA_ID_STR);
            } else {
                AP_IPID = 0;
            }

        }
        if (AP_CATEGORY.equals("INVOLVED_VEHICLE")) {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                AP_IPID = Integer.parseInt(DA_ID_STR);
            } else {
                AP_IPID = 0;
            }

        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            AP_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            AP_CUID = 0;
        }
        AP_UUID = AP_CUID;
        String AP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        AP_CDATE = AP_UDATE;


     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAP_AID, AP_AID);
        contentValues.put(CAP_IPID, AP_IPID);
        contentValues.put(CAP_CATEGORY, AP_CATEGORY);
        contentValues.put(CAP_PATH, AP_PATH);
        contentValues.put(CAP_FILENAME, AP_FILENAME);
        contentValues.put(CAP_CUID, AP_CUID);
        contentValues.put(CAP_CDATE, AP_CDATE);
        contentValues.put(CAP_UUID, AP_UUID);
        contentValues.put(CAP_UDATE, AP_UDATE);
        Log.d(TAG, "addData: Adding " + AP_FILENAME + " to " + TABLE_ACCIDENT_PICS);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        return dbW.insert(TABLE_ACCIDENT_PICS, null, contentValues);
    }

    public void updateData(String AP_PATH, String AP_FILENAME) {
        int AP_UUID, AP_AID, AP_IPID, AP_ID;
        String AP_CATEGORY;
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            AP_AID = Integer.parseInt(DA_ID_STR);
        } else {
            AP_AID = 0;
        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AP_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            AP_ID = Integer.parseInt(DA_ID_STR);
        } else {
            AP_ID = 0;
        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        AP_CATEGORY = persistenceObj.getPERSISTENCE_VALUE();
        AP_IPID = 0;
        if (AP_CATEGORY.equals("INVOLVED_PARTY")) {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                AP_IPID = Integer.parseInt(DA_ID_STR);
            } else {
                AP_IPID = 0;
            }

        }
        if (AP_CATEGORY.equals("INVOLVED_VEHICLE")) {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                AP_IPID = Integer.parseInt(DA_ID_STR);
            } else {
                AP_IPID = 0;
            }

        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            AP_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            AP_UUID = 0;
        }
        String AP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_ACCIDENT_PICS +
                " SET " + CAP_AID + " = '" + AP_AID + "' , "
                + CAP_IPID + " = '" + AP_IPID + "' , "
                + CAP_CATEGORY + " = '" + AP_CATEGORY + "' , "
                + CAP_PATH + " = '" + AP_PATH + "' , "
                + CAP_FILENAME + " = '" + AP_FILENAME + "' , "
                + CAP_UUID + " = '" + AP_UUID + "' , "
                + CAP_UDATE + " = '" + AP_UDATE + "'"
                + "WHERE " + CAP_ID + " = '" + AP_ID + "'";
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
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_ACCIDENT_PICS;
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

    public Boolean getImage(String AP_FILENAME) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_ACCIDENT_PICS +
                " WHERE " + CAP_FILENAME + " = '" + AP_FILENAME + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        Cursor cursor = dbW.rawQuery(query, null);
        try {
            cursor.moveToFirst();

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
          //      db.close();
                return true;
            } else {
                cursor.close();
          //      db.close();
                return false;
            }
        }

    }

    public List<InvolvedImageStore> getNewImage(String AP_FILENAME) {
        List<InvolvedImageStore> involvedImageStoreList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ACCIDENT_PICS +
                " WHERE " + CAP_FILENAME + " = '" + AP_FILENAME + "'";
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        Cursor cursor = dbR.rawQuery(query, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                InvolvedImageStore accpics = new InvolvedImageStore();
                accpics.setAP_ID(Integer.parseInt(cursor.getString(0)));
                accpics.setAP_AID(Integer.parseInt(cursor.getString(1)));
                accpics.setAP_IPID(Integer.parseInt(cursor.getString(2)));
                accpics.setAP_CATEGORY(cursor.getString(3));
                accpics.setAP_PATH(cursor.getString(4));
                accpics.setAP_FILENAME(cursor.getString(5));
                involvedImageStoreList.add(accpics);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
          //  db.close();
        }
        // 'AP_ID' 'AP_AID', 'AP_IPID',  'AP_CATEGORY' ,'AP_PATH', 'AP_FILENAME', 'AP_CUID', 'AP_CDATE', 'AP_UUID', 'AP_UDATE'

        return involvedImageStoreList;
    }

    /**
     * Returns all pictures for an a accidentold
     *
     * @return
     */
    public List<InvolvedImageStore> getAllAccPics(int AP_AID) {
        // pass in -1 for AP_AID when it's a DEVICE_USER

        List<InvolvedImageStore> involvedImageStoreList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ACCIDENT_PICS +
                " WHERE " + CAP_AID + " = '" + AP_AID + "'";
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        Cursor cursor = dbR.rawQuery(query, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                InvolvedImageStore accpics = new InvolvedImageStore();
                accpics.setAP_ID(Integer.parseInt(cursor.getString(0)));
                accpics.setAP_AID(Integer.parseInt(cursor.getString(1)));
                accpics.setAP_IPID(Integer.parseInt(cursor.getString(2)));
                accpics.setAP_CATEGORY(cursor.getString(3));
                accpics.setAP_PATH(cursor.getString(4));
                accpics.setAP_FILENAME(cursor.getString(5));
                involvedImageStoreList.add(accpics);

            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
         //   db.close();
        }

        return involvedImageStoreList;
    }

    /**
     * Returns all pictures for an a accidentold
     *
     * @return
     */
    public List<InvolvedImageStore> getAllUnattachedAccPics(int AP_AID) {
        // pass in -1 for AP_AID when it's a DEVICE_USER

        List<InvolvedImageStore> involvedImageStoreList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ACCIDENT_PICS +
                " WHERE " + CAP_AID + " = " + AP_AID +
                " AND " + CAP_IPID + " =  0";
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        Cursor cursor = dbR.rawQuery(query, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                InvolvedImageStore accpics = new InvolvedImageStore();
                accpics.setAP_ID(Integer.parseInt(cursor.getString(0)));
                accpics.setAP_AID(Integer.parseInt(cursor.getString(1)));
                accpics.setAP_IPID(Integer.parseInt(cursor.getString(2)));
                accpics.setAP_CATEGORY(cursor.getString(3));
                accpics.setAP_PATH(cursor.getString(4));
                accpics.setAP_FILENAME(cursor.getString(5));
                involvedImageStoreList.add(accpics);

            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
      //      db.close();
        }

        return involvedImageStoreList;
    }


    public List<InvolvedImageStore> getAccPics(int AP_AID, int AP_IPID) {
        // pass in -1 for AP_AID when it's a DEVICE_USER
        List<InvolvedImageStore> involvedImageStoreList = new ArrayList<>();
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        String AP_CATEGORY = persistenceObj.getPERSISTENCE_VALUE();


        String query = "SELECT * FROM " + TABLE_ACCIDENT_PICS +
                " WHERE " + CAP_AID + " = " + AP_AID +
                " AND " + CAP_IPID + " = " + AP_IPID +
                " AND " + CAP_CATEGORY + " = '" + AP_CATEGORY + "'";
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        Cursor cursor = dbR.rawQuery(query, null);

        // looping through all rows and adding to list
        try {
            while (cursor.moveToNext()) {
                InvolvedImageStore accpics = new InvolvedImageStore();
                accpics.setAP_ID(Integer.parseInt(cursor.getString(0)));
                accpics.setAP_AID(Integer.parseInt(cursor.getString(1)));
                accpics.setAP_IPID(Integer.parseInt(cursor.getString(2)));
                accpics.setAP_CATEGORY(cursor.getString(3));
                accpics.setAP_PATH(cursor.getString(4));

                accpics.setAP_FILENAME(cursor.getString(5));
                involvedImageStoreList.add(accpics);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        //    db.close();
        }
        return involvedImageStoreList;
    }


    //Delete from database

    public void deletePIC(String AP_PATH) {

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_ACCIDENT_PICS + " WHERE "
                + CAP_PATH + " = '" + AP_PATH + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        dbW.execSQL(query);
    }

    //Delete from database

    public void deleteACCPICS(int AP_AID) {
        int AP_ID;
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AP_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            AP_ID = Integer.parseInt(DA_ID_STR);
        } else {
            AP_ID = 0;
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        String AP_CATEGORY = persistenceObj.getPERSISTENCE_VALUE();

       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_ACCIDENT_PICS + " WHERE "
                + CAP_AID + " = '" + AP_AID + "'";

        Log.d(TAG, "deleteAP_AID: query: " + query);
        Log.d(TAG, "deleteAP_AID: Deleting " + CAP_ID + " from database.");
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
