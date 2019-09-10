package com.auto.accident.report.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.model.VehicleManifest;

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
 * Created by myron on 4/6/2018.
 */

public class VehicleManifestDao extends SQLiteOpenHelper {
    private static final String TAG = "VehicleManifestDao";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_VEHICLE_MANIFEST = "vehicle_manifest_table";
    private static final String CVMU_ID = "VMU_ID";
    private static final String CVMAID_ID = "VMAID_ID";  // Foriegn key accident implied
    private static final String CVMIV_ID = "VMIV_ID";    // Foriegn key invovled vehicle implied
    private static final String CVMIP_ID = "VMIP_ID";     // Foriegn key invovled party implied
    private static final String CVM_CUID = "VM_CUID";
    private static final String CVM_CDATE = "VM_CDATE";
    private static final String CVM_UUID = "VM_UUID";
    private static final String CVM_UDATE = "VM_UDATE";
    private final Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private String DA_ID_STR;

    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    public VehicleManifestDao(Context context) {
        super(context, TABLE_VEHICLE_MANIFEST, null, 1);
        this.context = context;
        //SQLiteDatabase.loadLibs(context);
         dbR = this.getReadableDatabase();
        dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_VEHICLE_MANIFEST + "(" + CVMU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CVMAID_ID + " INTEGER, " +
                CVMIV_ID + " INTEGER, " +
                CVMIP_ID + " INTEGER, " +
                CVM_CUID + " INTEGER, " +
                CVM_CDATE + " TEXT, " +
                CVM_UUID + " INTEGER, " +
                CVM_UDATE + " TEXT) ";

        db.execSQL(createTable);
        String deviceLocale = Locale.getDefault().getLanguage();
        if (deviceLocale.equals("en")) {
            //       db.execSQL("insert INTO involved_vehicle_table ('VMAID_ID', 'IV_TAG', 'IV_STATE', 'IV_EXPIRATION', 'IV_VIN', 'IV_YEAR', 'IV_MAKE', 'IV_MODEL', 'IV_CUID', 'IV_CDATE', 'IV_UUID', 'IV_UDATE') VALUES (1,'CVN-7791', 'FL ', '2019', 'V-133721957', '2016 ', 'Chevrolet',  'Corvette', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
            //        db.execSQL("insert INTO involved_vehicle_table ('VMAID_ID', 'IV_TAG', 'IV_STATE', 'IV_EXPIRATION', 'IV_VIN', 'IV_YEAR', 'IV_MAKE', 'IV_MODEL', 'IV_CUID', 'IV_CDATE', 'IV_UUID', 'IV_UDATE') VALUES (1,'RSN-3364', 'FL ', '2019', 'V4541892311', '2016 ', 'Austin',  'Martin', 1, '02/27/2017 6:47:27', 2, '02/27/2017 6:47:27');");
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE_MANIFEST);
        onCreate(db);
    }

    public long addData(Integer VMAID_ID, Integer VMIV_ID, Integer VMIP_ID) {
        List<VehicleManifest> vehicleManifestList = new ArrayList<>();
        vehicleManifestList = getVehicleManifestPerson(VMIP_ID, VMAID_ID);
        long result = -1;
        if (vehicleManifestList.size() == 0) {
            mPersistenceObjDao = new PersistenceObjDao(context);
            PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            int VM_CUID;
            if (isNumber(DA_ID_STR)) {
                VM_CUID = Integer.parseInt(DA_ID_STR);
            } else {
                VM_CUID = 0;
            }

            String VM_CDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());

          //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
          //  SQLiteDatabase db = this.getWritableDatabase(xxx);
            ContentValues contentValues = new ContentValues();
            contentValues.put(CVMAID_ID, VMAID_ID);
            contentValues.put(CVMIV_ID, VMIV_ID);
            contentValues.put(CVMIP_ID, VMIP_ID);
            contentValues.put(CVM_CUID, VM_CUID);
            contentValues.put(CVM_CDATE, VM_CDATE);
            contentValues.put(CVM_UUID, VM_CUID);
            contentValues.put(CVM_UDATE, VM_CDATE);

            contentValues.put(CVM_CDATE, VM_CDATE);

            if (!dbW.isOpen()) {
                dbW = this.getWritableDatabase();
            }
            result = dbW.insert(TABLE_VEHICLE_MANIFEST, null, contentValues);
        }

        return result;
    }


    public void updateData(int VMU_ID, int VMAID_ID, int VMIV_ID, int VMIP_ID) {
        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");

        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        int VM_UUID;
        if (isNumber(DA_ID_STR)) {
            VM_UUID = Integer.parseInt(DA_ID_STR);
        } else {
            VM_UUID = 0;
        }

        String VM_UDATE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "UPDATE " + TABLE_VEHICLE_MANIFEST +
                " SET " + CVMIV_ID + " = '" + VMIV_ID + "' , "
                + CVMIP_ID + " = '" + VMIP_ID + "' , "
                + CVMAID_ID + " = '" + VMAID_ID + "' , "
                + CVM_UUID + " = '" + VM_UUID + "' , "
                + CVM_UDATE + " = '" + VM_UDATE + "'"
                + "WHERE " + CVMU_ID + " = " + VMU_ID;
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
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "SELECT * FROM " + TABLE_VEHICLE_MANIFEST;
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(query, null);
        if (cursor != null && !cursor.isClosed()) {
        }
        return cursor;
    }

    // Getting All Involved Pars
    public List<VehicleManifest> getVehicleManifest(int VMIV_ID, int AID_ID) {
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMAID_ID + " = " + AID_ID +
                " AND " + CVMIV_ID + " = " + VMIV_ID;
        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
        }
        // return contact list
        return vehiclemanifestList;
    }


    // Getting All Involved Pars
    public List<VehicleManifest> getVehicleManifestPerson(int VMIP_ID, int AID_ID) {
        // this = context;
        //  List<VehicleManifest> vehiclemanifestList = new ArrayList<VehicleManifest>();
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMAID_ID + " = " + AID_ID +
                " AND " + CVMIP_ID + " = " + VMIP_ID;
        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;
     //   xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    //    SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

            Cursor cursor = dbR.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {

                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);

            }



        // return contact list
        return vehiclemanifestList;
    }

    public List<VehicleManifest> getVehicleManifestPersonNZ(int VMIP_ID, int AID_ID) {
        // Where Vehicle Not Zero
        // this = context;
        //  List<VehicleManifest> vehiclemanifestList = new ArrayList<VehicleManifest>();
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMAID_ID + " = " + AID_ID +
                " AND " + CVMIP_ID + " = " + VMIP_ID +
                " AND " + CVMIV_ID + " != " + 0;
        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

            Cursor cursor = dbR.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {

                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);

            }



        // return contact list
        return vehiclemanifestList;
    }

    // Getting All Involved Pars
    public List<VehicleManifest> getAllVehicleManifests(int AID_ID) {
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMAID_ID + " = '" + AID_ID + "'";
        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
        }
        // return contact list
        return vehiclemanifestList;
    }

    public List<VehicleManifest> getAllVehicleManifestsBothNZ(int AID_ID) {
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query where IP != 0
        //  " AND   " + CVMIV_ID + " != " + 0 +
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMAID_ID + " = " + AID_ID +
                " AND   " + CVMIP_ID + " != " + 0 +
                " AND   " + CVMIV_ID + " != " + 0 +
                " ORDER BY " + CVMIV_ID + " ASC ";
        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
        }
        // return contact list
        return vehiclemanifestList;
    }

    public List<VehicleManifest> getAllVehicleManifestsVehicleZ(int AID_ID) {
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query where IP != 0
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMAID_ID + " = " + AID_ID +
                " AND   " + CVMIP_ID + " != " + 0 +
                " AND   " + CVMIV_ID + " = " + 0;

        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
        }
        // return contact list
        return vehiclemanifestList;
    }
    public List<VehicleManifest> getAllVehicleManifestsPersonZ(int AID_ID) {
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query where IP != 0
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMAID_ID + " = " + AID_ID +
                " AND   " + CVMIP_ID + " = " + 0 +
                " AND   " + CVMIV_ID + " != " + 0;

        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;

        //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
        //   SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
        }
        // return contact list
        return vehiclemanifestList;
    }


    // Getting All Involved Pars
    public List<VehicleManifest> getAllVehicleManifestsAllAccidents() {
        List<VehicleManifest> vehiclemanifestList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;
        //  String selectQuery = "SELECT  * FROM " + TABLE_VEHICLE_MANIFEST;

       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
       // SQLiteDatabase db = this.getReadableDatabase(xxx);
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }

        Cursor cursor = dbR.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VehicleManifest vehiclemanifest = new VehicleManifest();
                vehiclemanifest.setVMU_ID(Integer.parseInt(cursor.getString(0)));
                vehiclemanifest.setVMAID_ID(Integer.parseInt(cursor.getString(1)));
                vehiclemanifest.setVMIV_ID(Integer.parseInt(cursor.getString(2)));
                vehiclemanifest.setVMIP_ID(Integer.parseInt(cursor.getString(3)));


                // Adding contact to list
                vehiclemanifestList.add(vehiclemanifest);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            //     cursor.close();
            //     db.close();
        }
        // return contact list
        return vehiclemanifestList;
    }

    public void deleteVMIP_ID(int VMIP_ID) {
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
       // SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE  FROM " + TABLE_VEHICLE_MANIFEST +
                "  WHERE " + CVMIP_ID + " = " + VMIP_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteVMIV_ID(int VMIV_ID) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE  FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMIV_ID + " = " + VMIV_ID;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteVMIP_IDWZ(int VMIP_ID) {
        // Delete Where IV = 0
        int IV = 0;
       // xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE  FROM " + TABLE_VEHICLE_MANIFEST +
                "  WHERE " + CVMIP_ID + " = " + VMIP_ID +
                " AND    " + CVMIV_ID + " = " + IV;
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);

    }

    public void deleteVMIV_IDWZ(int VMIV_ID) {
        // Delete Where IP = 0
        int IP = 0;
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
        String query = "DELETE  FROM " + TABLE_VEHICLE_MANIFEST +
                " WHERE " + CVMIV_ID + " = " + VMIV_ID +
                " AND   " + CVMIP_ID + " = " + IP;
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



