package com.auto.accident.report.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteOpenHelper;

import com.auto.accident.report.model.PersistenceObj;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

//import net.sqlcipher.database.SQLiteFullException;
//import net.sqlcipher.CursorIndexOutOfBoundsException;
//import net.sqlcipher.Cursor;
//import net.sqlcipher.database.SQLiteOpenHelper;
//import net.sqlcipher.database.SQLiteDatabase;
//

/**
 * Created by myron on 1/29/2018.
 */

public class PersistenceObjDao extends SQLiteOpenHelper {
    private static final String TAG = "PersistenceObjDao";

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "accidentTracking";
    private static final String TABLE_PERSISTENCE_OBJ = "persistence_obj_table";
    private static final String CPERSISTENCE_KEY = "PERSISTENCE_KEY";
    private static final String CPERSISTENCE_VALUE = "PERSISTENCE_VALUE";
    private static final String CPERSIST_ANDROID_ID = "PERSIST_ANDROID_ID";
    private static final String CPERSIST_DU_ID = "PERSIST_DU_ID";
    private static final String CPERSIST_DU_MODE = "PERSIST_DU_MODE";
    private static final String CPERSIST_DUV_ID = "PERSIST_DV_ID";
    private static final String CPERSIST_DUV_MODE = "PERSIST_DV_MODE";
    private static final String CPERSIST_AID_ID = "PERSIST_AID_ID";
    private static final String CPERSIST_AID_MODE = "PERSIST_AID_MODE";
    private static final String CPERSIST_IP_ID = "PERSIST_IP_ID";
    private static final String CPERSIST_IP_CALLER = "PERSIST_IP_CALLER";
    private static final String CPERSIST_IPV_ID = "PERSIST_IV_ID";
    private static final String CPERSIST_CAMERA_CALLER = "PERSIST_CAMERA_CALLER";
    private static final String CPERSIST_AP_ID = "PERSIST_AP_ID";
    private static final String CPERSIST_PHOTOS_STATUS = "PERSIST_PHOTOS_STATUS";
    private static final String CPERSIST_PIC_MODE = "PERSIST_PIC_MODE";
    private static final String CPERSIST_AP_FILENAME = "PERSIST_AP_FILENAME";
    private static final String CPERSIST_IV_ID = "PERSIST_IV_ID";
    private String AID;
    private String PERSISTANCE_KEY;
    private PersistenceObj persistenceobj;
    private String DA_ID_STR;
    private int DUX_ID;


    //protected String xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
    public SQLiteDatabase dbR;
    public SQLiteDatabase dbW;
    // Static strings for persisence

    public PersistenceObjDao(Context context) {
        super(context, TABLE_PERSISTENCE_OBJ, null, 1);
        //SQLiteDatabase.loadLibs(context);
             dbR = this.getReadableDatabase();
            dbW = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + " TEXT PRIMARY KEY , " +
                CPERSISTENCE_VALUE + " TEXT)";

        db.execSQL(createTable);

        try {
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ANDROID_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DU_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DU_MODE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DUV_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DUV_MODE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_AID_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_AID_MODE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IP_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IP_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IPV_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CAMERA_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_AP_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PHOTOS_STATUS', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PIC_MODE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_AP_FILENAME', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DU_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PT_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_GALLERY_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CAROUSEL_CALLER', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IV_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DV_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DV_MODE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DV_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_DEVICE_MODE', 'PRODUCTION');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IV_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_SELECT_NOTE_ATTACHMENT_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ADD_NOTES_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_LIST_NOTES_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_GALLERY_IMAGE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_AN_ID', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_UPDATE_NOTES_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_WEB_VIEW_DATA', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_SELECTED_DU_ID', '');");

            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_FILE_NAME', 'acc.pdf');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT1', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT2', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT3', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT4', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT5', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT6', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT7', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT8', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT9', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT10', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT11', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT12', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT13', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT14', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT15', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT16', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT17', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT18', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT19', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT20', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT21', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT22', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT23', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT24', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT25', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT26', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT27', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT28', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT29', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT30', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT31', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT32', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT33', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT34', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT35', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT36', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT37', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT38', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PDF_PAGE_COUNT39', '0');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ADD_INSURANCE_POLICY_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_LIST_INSURANCE_POLICY_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IPO_ID', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IPO_HOLDER', '');");

            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_LATTITUDE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_LONGITUDE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ADDRESS', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CITY', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_STATE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ZIP', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_COUNTRY', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_MODE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_BUTTON01', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_PHONE01_COUNTRY', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_PHONE01', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_URL01', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_BUTTON02', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_PHONE02_COUNTRY', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_PHONE02', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_URL02', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_ICON_URL01', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_CUSTOM_ICON_URL02', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_VT_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PA_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PT_TYPE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PREFER_OFFLINE', 'FALSE');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_PREFER_VIN', 'FALSE');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_INSURANCE_POLICY_VEHICLE_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_FREE_FORM_EMAIL_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_FREE_FORM_EMAIL_SEND_TO', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_AUTOMOTIVE_SERVICES_MODE', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ALLOW_EMAIL_REPORT', 'NO');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListVehicleCoverage', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListPremiumAdvertiser', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListNotes', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListInvolvedParty', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListInvolvedVehicle', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListDeviceUser', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListAccident', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListPartyType', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListInsuredPeople', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListNotesAdapter', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListInsurancePolicy', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ListDeviceVehicle', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_EditCustom', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddInsurancePolicy', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_CarouselSpinner', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AccidentMenu', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddInvolvedVehicle', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddVehicleType', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_CarouselBaseAdapter', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddInvolvedParty', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AutomotiveRepair', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_FreeFormEmail', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AccidentAttorneyRequest', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddDeviceVehicle', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_FreeFormCall', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_DashboardActivity', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddAccident', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AttorneysSearch', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_InvolvedMenu', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddPremiumAdvertiser', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AutomotivePaintBody', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddNotes', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddPartyType', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AutomotiveServices', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AccidentAttorneyEmail', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AddDeviceUser', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_AutomotiveTowing', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateDeviceVehicle', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateInsurancePolicy', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateNotes', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_PdfPrint', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_SelectNoteAttachment', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateInvolvedVehicle', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_ProfilesMenu', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateAccident', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_SettingsActivity', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateInvolvedParty', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UploadToGoogleDrive', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateDeviceUser', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_PDFViewActivity', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdatePartyType', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdatePremiumAdvertiser', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_UpdateVehicleType', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('did_play_OpenAccidentMenuDrawer', 'false');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('FIREBASE_CLOUD_UPLOAD_IN_PROGRESS', 'false');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('GOOGLE_DRIVE_UPLOAD_IN_PROGRESS', 'false');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_IM_CALLER', '');");

            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('DID_OPEN_INVOLVED_PARTY_DRAWER', 'false');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('DID_OPEN_INVOLVED_VEHICLE_DRAWER', 'false');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_MULTI_MEDIA_CALLER', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_TEMP_01', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_TEMP_02', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_TEMP_03', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_TEMP_04', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_TEMP_05', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_TEMP_06', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_AUDIO_ICON', '');");
            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_COMPANY_NAME', '');");

            db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ACTION_IN_PROGRESS', 'false');");
         //   db.execSQL("insert INTO " + TABLE_PERSISTENCE_OBJ + "(" + CPERSISTENCE_KEY + ",'PERSISTENCE_VALUE') VALUES ('PERSIST_ALBUM_NAME', 'AccidentReport');");


        } finally

        {
            //  db.close();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSISTENCE_OBJ);
        onCreate(db);
    }

    public boolean addData(String tiePERSISTENCE_KEY, String tiePERSISTENCE_VALUE) {

      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
     //  SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CPERSISTENCE_KEY, tiePERSISTENCE_KEY);
        contentValues.put(CPERSISTENCE_VALUE, tiePERSISTENCE_VALUE);
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        long result = dbW.insert(TABLE_PERSISTENCE_OBJ, null, contentValues);
        //if data is inserted incorrectly it will return -1
        if (result == -1) {
         //   dbW.close();
            return false;
        } else {
      //      dbW.close();
            return true;
        }


    }


    public void updateData(String PERSISTENCE_KEY, String PERSISTENCE_VALUE) {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
       // SQLiteDatabase db = this.getWritableDatabase(xxx);
    //    SQLiteDatabase db = this.getWritableDatabase();
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        String query = "UPDATE " + TABLE_PERSISTENCE_OBJ +
                " SET " + CPERSISTENCE_VALUE + " = '" + PERSISTENCE_VALUE + "'"
                + "WHERE " + CPERSISTENCE_KEY + " = '" + PERSISTENCE_KEY + "'";
        try {
            dbW.execSQL(query);

      //      dbW.close();
        } catch (Exception e) {
          //  dbW.close();
            addData(PERSISTENCE_KEY, PERSISTENCE_VALUE);
        }
    }

    public void deletePERSISTENCE_KEY(String PERSISTENCE_KEY) {
     //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getWritableDatabase(xxx);
     //   SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PERSISTENCE_OBJ + " WHERE "
                + CPERSISTENCE_KEY + " = '" + PERSISTENCE_KEY + "'";
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }
        dbW.execSQL(query);
    }

    // Getting one Device User
    public PersistenceObj getPersistence(String PERSISTENCE_KEY) throws SQLiteFullException {
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
     //   SQLiteDatabase db = this.getReadableDatabase(xxx);

     //   SQLiteDatabase db = this.getReadableDatabase();
        if (!dbR.isOpen()) {
            dbR = this.getReadableDatabase();
        }
            try (Cursor cursor = dbR.query(TABLE_PERSISTENCE_OBJ, new String[]{CPERSISTENCE_KEY, CPERSISTENCE_VALUE},
                    CPERSISTENCE_KEY + "=?",
                    new String[]{valueOf(PERSISTENCE_KEY)}, null, null, null, null)) {
                try {
                    cursor.moveToFirst();

                    persistenceobj = new PersistenceObj(cursor.getString(0),
                            cursor.getString(1));
                } catch (CursorIndexOutOfBoundsException e) {

                } finally {
                    cursor.close();
//dbR.close();
                }
            }



        return persistenceobj;

    }


    // Getting All Persistance Values
    public List<PersistenceObj> getAllPersistences() {
        List<PersistenceObj> persistenceObjList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSISTENCE_OBJ;
      //  xxx = FixStrings.getFixedString(REQ_CODE_FIX_IN, MEESALGO);
      //  SQLiteDatabase db = this.getWritableDatabase(xxx);
     //   SQLiteDatabase db = this.getWritableDatabase();
        if (!dbW.isOpen()) {
            dbW = this.getWritableDatabase();
        }

            // looping through all rows and adding to list
            try (Cursor cursor = dbW.rawQuery(selectQuery, null)) {
                cursor.moveToFirst();
                do {
                    persistenceobj = new PersistenceObj();
                    persistenceobj.setPERSISTENCE_KEY(cursor.getString(0));
                    persistenceobj.setPERSISTENCE_VALUE(cursor.getString(1));

                    // Adding contact to list
                    persistenceObjList.add(persistenceobj);
                } while (cursor.moveToNext());


                // return device user list
            }



        return persistenceObjList;
    }
    public void closeAll() {
        dbR.close();
        dbW.close();

    }

}
