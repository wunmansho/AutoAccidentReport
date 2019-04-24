package com.auto.accident.report.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.auto.accident.report.objects.DeviceImageStore;
import com.auto.accident.report.objects.PersistenceObj;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auto.accident.report.util.utils.isNumber;

//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;


public class DeviceImageStoreDao extends SQLiteOpenHelper {
    private static final String TAG = "DeviceImageStoreDao";
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_DEVICE_PICS = "device_pics_table";
    private static final String CDP_ID = "DP_ID";
    private static final String CDP_DUID = "DP_DUID";
    private static final String CDP_CATEGORY = "DP_CATEGORY";
    private static final String CDP_PATH = "DP_PATH";
    private static final String CDP_FILENAME = "DP_FILENAME";
    private static final String CDP_CUID = "DP_CUID";
    private static final String CDP_CDATE = "DP_CDATE";
    private static final String CDP_UUID = "DP_UUID";
    private static final String CDP_UDATE = "DP_UDATE";
    private final Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private String DA_ID_STR;


    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;

    public DeviceImageStoreDao(Context context) {
        super(context, TABLE_DEVICE_PICS, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    // private int    DP_ID;
    // private int    DP_DUID;
    // private String DP_PATH;
    // private String DP_FILENAME;
    // private int    DP_CUID;
    // private String DP_CDATE;
    // private int    DP_UUID;
    // private String DP_UDATE;
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_DEVICE_PICS + "(" + CDP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CDP_DUID + " INTEGER, " +
                CDP_CATEGORY + " TEXT, " +
                CDP_PATH + " TEXT, " +
                CDP_FILENAME + " TEXT, " +
                CDP_CUID + " INTEGER, " +
                CDP_CDATE + " TEXT, " +
                CDP_UUID + " INTEGER, " +
                CDP_UDATE + " TEXT) ";
        db.execSQL(createTable);


        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
        } else {
            // Locate the image folder in your SD Card
            String albumName = "AccidentReport";
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), albumName);
            file.mkdirs();
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE_PICS);
        onCreate(db);
    }

    public long addData(String DP_PATH, String DP_FILENAME) {
        int DP_CUID, DP_UUID, DP_DUID;
        String DP_CDATE, DP_CATEGORY;
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DP_DUID = Integer.parseInt(DA_ID_STR);
        } else {
            DP_DUID = 0;
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        DP_CATEGORY = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DP_CUID = Integer.parseInt(DA_ID_STR);
        } else {
            DP_CUID = 0;
        }
        DP_UUID = DP_CUID;
        String DP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        DP_CDATE = DP_UDATE;


      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CDP_DUID, DP_DUID);
        contentValues.put(CDP_CATEGORY, DP_CATEGORY);
        contentValues.put(CDP_PATH, DP_PATH);
        contentValues.put(CDP_FILENAME, DP_FILENAME);
        contentValues.put(CDP_CUID, DP_CUID);
        contentValues.put(CDP_CDATE, DP_CDATE);
        contentValues.put(CDP_UUID, DP_UUID);
        contentValues.put(CDP_UDATE, DP_UDATE);

        Log.d(TAG, "addData: Adding " + DP_FILENAME + " to " + TABLE_DEVICE_PICS);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        return dbW.insert(TABLE_DEVICE_PICS, null, contentValues);
    }

    public void updateData(String DP_PATH, String DP_FILENAME) {
        int DP_CUID, DP_UUID, DP_DUID, DP_ID;
        String DP_CDATE, DP_CATEGORY;


        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DP_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DP_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DP_ID = 0;
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DP_DUID = Integer.parseInt(DA_ID_STR);
        } else {
            DP_DUID = 0;
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        DP_CATEGORY = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DP_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            DP_UUID = 0;
        }
        String DP_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_DEVICE_PICS +
                " SET " + CDP_DUID + " = '" + DP_DUID + "' , "
                + CDP_CATEGORY + " = '" + DP_CATEGORY + "' , "
                + CDP_PATH + " = '" + DP_PATH + "' , "
                + CDP_FILENAME + " = '" + DP_FILENAME + "' , "
                + CDP_UUID + " = '" + DP_UUID + "' , "
                + CDP_UDATE + " = '" + DP_UDATE + "'"
                + "WHERE " + CDP_ID + " = '" + DP_ID + "'";
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
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_DEVICE_PICS;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        Cursor cursor = dbW.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //   db.close();
        }
        return cursor;
    }

    public Boolean getImage(String DP_FILENAME) {
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_DEVICE_PICS +
                " WHERE " + CDP_FILENAME + " = '" + DP_FILENAME + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

        Cursor cursor = dbW.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            if (cursor != null && !cursor.isClosed()) {
                //   cursor.close();
                //     db.close();
            }
            return false;
        }
        if (cursor != null && !cursor.isClosed()) {
            //  cursor.close();
            //   db.close();
        }
        return true;
    }

    public List<DeviceImageStore> getNewImage(String DP_FILENAME) {
        List<DeviceImageStore> deviceImageStoreList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DEVICE_PICS +
                " WHERE " + CDP_FILENAME + " = '" + DP_FILENAME + "'";
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DeviceImageStore devpics = new DeviceImageStore();
                devpics.setDP_ID(Integer.parseInt(cursor.getString(0)));
                devpics.setDP_DUID(Integer.parseInt(cursor.getString(1)));
                devpics.setDP_CATEGORY(cursor.getString(2));
                devpics.setDP_PATH(cursor.getString(3));
                devpics.setDP_FILENAME(cursor.getString(4));
                deviceImageStoreList.add(devpics);
            } while (cursor.moveToNext());
        }
        // 'DP_ID' , 'DP_DUID',  'DP_CATEGORY' ,'DP_PATH', 'DP_FILENAME', 'DP_CUID', 'DP_CDATE', 'DP_UUID', 'DP_UDATE'
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //   db.close();
        }
        return deviceImageStoreList;
    }

    /**
     * Returns all pictures for a device user
     *
     * @return
     */
    public List<DeviceImageStore> getAllDevPics() {

        List<DeviceImageStore> deviceImageStoreList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DEVICE_PICS;

     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DeviceImageStore devpics = new DeviceImageStore();
                devpics.setDP_ID(Integer.parseInt(cursor.getString(0)));
                devpics.setDP_DUID(Integer.parseInt(cursor.getString(1)));
                devpics.setDP_CATEGORY(cursor.getString(2));
                devpics.setDP_PATH(cursor.getString(3));
                devpics.setDP_FILENAME(cursor.getString(4));
                deviceImageStoreList.add(devpics);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //   db.close();
        }
        return deviceImageStoreList;
    }

    public List<DeviceImageStore> getDevPics(int DP_DUID) {

        List<DeviceImageStore> deviceImageStoreList = new ArrayList<>();
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        String DP_CATEGORY = persistenceObj.getPERSISTENCE_VALUE();


        String query = "SELECT * FROM " + TABLE_DEVICE_PICS +
                " WHERE " + CDP_DUID + " = " + DP_DUID +
                " AND " + CDP_CATEGORY + " = '" + DP_CATEGORY + "'";
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
        Cursor cursor = dbR.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DeviceImageStore devpics = new DeviceImageStore();
                devpics.setDP_ID(Integer.parseInt(cursor.getString(0)));
                devpics.setDP_DUID(Integer.parseInt(cursor.getString(1)));
                devpics.setDP_CATEGORY(cursor.getString(2));
                devpics.setDP_PATH(cursor.getString(3));

                devpics.setDP_FILENAME(cursor.getString(4));
                deviceImageStoreList.add(devpics);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //   cursor.close();
            //  db.close();
        }
        return deviceImageStoreList;
    }

    public void deletePIC(String DP_PATH) {

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE FROM " + TABLE_DEVICE_PICS + " WHERE "
                + CDP_PATH + " = '" + DP_PATH + "'";
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
