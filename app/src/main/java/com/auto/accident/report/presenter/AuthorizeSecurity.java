package com.auto.accident.report.presenter;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import android.speech.SpeechRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.R;

import java.util.List;
import java.util.Locale;
import com.auto.accident.report.util.PermissionUtil;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.View.INVISIBLE;
public class AuthorizeSecurity extends AppCompatActivity  implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private static final String TAG = "AuthorizeSecurity";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_ROLE = 99;
    private static final int REQ_CODE_DESCRIPTION = 98;

    private RelativeLayout llbtnSpeakOn, llbtnSpeakOff;

    private EditText tieVT_Type;
    private TextView tvDA_PROMPT;
    private final Boolean AutoAdd = true;
    private Boolean AutoAddSave = false;
    private int REQ_CODE;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private final String LOG_TAG = "VoiceRecActivity";
    private String DA_RESULT2;
    private String[] splitString;
    private int splitLength;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private final String deviceLocale = Locale.getDefault().getCountry();
    private String preferOfflineLanguages;
    private boolean preferOffline;
    private PersistenceObjDao mPersistenceObjDao;

    private String did_play_AddVehicleType;
    private Resources res;
    private FloatingActionButton btnAdd;

    private FloatingActionButton btnHelp;

    private FloatingActionButton btnCam;
    private FloatingActionButton btnRead;
    private FloatingActionButton btnWrite;
    private FloatingActionButton btnRecord;
    private FloatingActionButton btnLoc;
    private FloatingActionButton btnCall;
    private FloatingActionButton btnState;
    private FloatingActionButton btnInternet;
    private RelativeLayout llbtnCam;
    private RelativeLayout llbtnRead;
    private RelativeLayout llbtnWrite;
    private RelativeLayout llbtnRecord;
    private RelativeLayout llbtnLoc;
    private RelativeLayout llbtnCall;
    private RelativeLayout llbtnState;
    private RelativeLayout llbtnInternet;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private PersistenceObj persistenceObj;
    private DeviceUserDao mDeviceUserDao;
    private DeviceUser deviceUser;
    private String rsMode;

    private String DA_HORN;
    private Context context;
    private View view;
    private int DUX_ID;
    private String DA_ID_STR;
    private boolean CameraPermission;
    private boolean ReadExternalStoragePermission;
    private boolean WriteExternalStoragePermission;
    private boolean RecordAudioPermission;
    private boolean AccessFineLocationPermission;
    private boolean CallPhonePermission;
    private boolean ReadPhoneStatePermission;
    private boolean InternetPermission;
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_READ_EXTERNAL_STORAGE_PERM = 124;
    private static final int RC_WRITE_EXTERNAL_STORAGE_PERM = 125;
    private static final int RC_RECORD_AUDIO_PERM = 126;
    private static final int RC_ACCESS_FINE_LOCATION_PERM = 127;
    private static final int RC_CALL_PHONE_PERM = 128;
    private static final int RC_READ_PHONE_STATE_PERM = 129;
    private static final int RC_INTERNET_PERM = 130;
    private int RC_ERROR_CODE;
    private int toolBarBlue = Color.parseColor("#FF0288D1");
    private int colorGray = Color.parseColor("#808080");
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorize_security);
        btnHelp = findViewById(R.id.btnHelp);


        progressBar = findViewById(R.id.progressBar1);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tieVT_Type = findViewById(R.id.tieVT_Type);

        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.my_toolbar);
        btnCam  = findViewById(R.id.btnCam);
        btnRead  = findViewById(R.id.btnRead);
        btnWrite  = findViewById(R.id.btnWrite);
        btnRecord  = findViewById(R.id.btnRecord);
        btnLoc  = findViewById(R.id.btnLoc);
        btnCall  = findViewById(R.id.btnCall);
        btnState  = findViewById(R.id.btnState);
        btnInternet  = findViewById(R.id.btnInternet);
        llbtnCam  = findViewById(R.id.llbtnCam);
        llbtnRead  = findViewById(R.id.llbtnRead);
        llbtnWrite  = findViewById(R.id.llbtnWrite);
        llbtnRecord  = findViewById(R.id.llbtnRecord);
        llbtnLoc  = findViewById(R.id.llbtnLoc);
        llbtnCall  = findViewById(R.id.llbtnCall);
        llbtnState  = findViewById(R.id.llbtnState);
        llbtnInternet  = findViewById(R.id.llbtnInternet);
        setSupportActionBar(toolbar);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_VT_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        res = getResources();
    //    mDeviceUserDao = new DeviceUserDao(this);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);


        });


        btnCam.setOnClickListener(view -> {
            requestCameraPerm();
        });
        btnRead.setOnClickListener(view -> {
            requestReadExternalStoragePerm();
        });
        btnWrite.setOnClickListener(view -> {
            requestWriteExternalStoragePerm();
        });
        btnRecord.setOnClickListener(view -> {
            requestRecordAudioPerm();
        });
        btnLoc.setOnClickListener(view -> {
            requestAccessFineLocationPerm();
        });
        btnCall.setOnClickListener(view -> {
            requestCallPhonePerm();
        });
        btnState.setOnClickListener(view -> {
            requestReadPhoneStatePerm();
        });
        btnInternet.setOnClickListener(view -> {
            requestInternetPerm();
        });
        btnHelp.setOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnHelp.startAnimation(rotateAnimation);
          //  scheduleDoHelp();
        });
        doPermissions();
    }

    private void doPermissions() {
        getPermissions();
        if (!CameraPermission || !ReadExternalStoragePermission || !WriteExternalStoragePermission || !RecordAudioPermission || !AccessFineLocationPermission || !CallPhonePermission || !ReadPhoneStatePermission || !InternetPermission) {
            authorizeSecurity();
        } else {
            authorizeSecurity();
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);

        }
    }

    private void getPermissions() {
        CameraPermission = PermissionUtil.hasCameraPermission(this);
        ReadExternalStoragePermission = PermissionUtil.hasReadExternalStoragePermission(this);
        WriteExternalStoragePermission = PermissionUtil.hasWriteExternalStoragePermission(this);
        RecordAudioPermission = PermissionUtil.hasRecordAudioPermission(this);
        AccessFineLocationPermission = PermissionUtil.hasAccessFineLocationPermission(this);
        CallPhonePermission = PermissionUtil.hasCallPhonePermission(this);
        ReadPhoneStatePermission = PermissionUtil.hasReadPhoneStatePermission(this);
        InternetPermission = PermissionUtil.hasInternetPermission(this);

    }


    private void authorizeSecurity() {

        if(CameraPermission) {



           llbtnCam.setVisibility(GONE);
          } else {

            btnCam.setClickable(true);
            llbtnCam.setVisibility(VISIBLE);
        }

        if(ReadExternalStoragePermission) {
            llbtnRead.setVisibility(GONE);
        } else {
            llbtnRead.setVisibility(VISIBLE);
        }


        if(WriteExternalStoragePermission) {

            llbtnWrite.setVisibility(GONE);
        } else {
            llbtnWrite.setVisibility(VISIBLE);
        }
        if(RecordAudioPermission) {

            llbtnRecord.setVisibility(GONE);
        } else {
            llbtnRecord.setVisibility(VISIBLE);
        }
        if(AccessFineLocationPermission) {
            llbtnLoc.setVisibility(GONE);
        } else {
            llbtnLoc.setVisibility(VISIBLE);
        }
        if(CallPhonePermission) {
            llbtnCall.setVisibility(GONE);
        } else {
            llbtnCall.setVisibility(VISIBLE);
        }
        if(ReadPhoneStatePermission) {
            llbtnState.setVisibility(GONE);
        } else {
            llbtnState.setVisibility(VISIBLE);
        }
        if(InternetPermission) {
            llbtnInternet.setVisibility(GONE);
        } else {
            llbtnInternet.setVisibility(VISIBLE);
        }
    }
    private void requestCameraPerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_camera),
                RC_CAMERA_PERM,
                Manifest.permission.CAMERA);
        
    }
    private void requestReadExternalStoragePerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_read_storage),
                RC_READ_EXTERNAL_STORAGE_PERM,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        

    }
    private void requestWriteExternalStoragePerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_write_storage),
                RC_WRITE_EXTERNAL_STORAGE_PERM,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        

    }
    private void requestRecordAudioPerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_record_audio),
                RC_RECORD_AUDIO_PERM,
                Manifest.permission.RECORD_AUDIO);

    }
    private void requestAccessFineLocationPerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_fine_location),
                RC_ACCESS_FINE_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION);
        

    }
    private void requestCallPhonePerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_call_phone),
                RC_CALL_PHONE_PERM,
                Manifest.permission.CALL_PHONE);
        

    }
    private void requestReadPhoneStatePerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_read_phone_state),
                RC_READ_PHONE_STATE_PERM,
                Manifest.permission.READ_PHONE_STATE);
        

    }
    private void requestInternetPerm() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_internet),
                RC_INTERNET_PERM,
                Manifest.permission.INTERNET);
        

    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        doPermissions();
    }
    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d(TAG, "onRationaleDenied:" + requestCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
        doPermissions();
    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (PermissionUtil.hasCameraPermission(this)) {
            // Have permission, do the thing!
       //     Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_camera),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA);

        }
        
    }

    @AfterPermissionGranted(RC_READ_EXTERNAL_STORAGE_PERM)
    public void readexternalstorageTask() {
        if (PermissionUtil.hasReadExternalStoragePermission(this)) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Read External Storage things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_read_storage),
                    RC_READ_EXTERNAL_STORAGE_PERM,
                    Manifest.permission.READ_EXTERNAL_STORAGE);

        }

    }


    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE_PERM)
    public void writeexternalstorageTask() {
        if (PermissionUtil.hasWriteExternalStoragePermission(this)) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Write External Storage things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_write_storage),
                    RC_WRITE_EXTERNAL_STORAGE_PERM,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }

    }

    @AfterPermissionGranted(RC_RECORD_AUDIO_PERM)
    public void recordaudioTask() {
        if (PermissionUtil.hasRecordAudioPermission(this)) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Record Audio", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_record_audio),
                    RC_RECORD_AUDIO_PERM,
                    Manifest.permission.RECORD_AUDIO);

        }

    }



    @AfterPermissionGranted(RC_ACCESS_FINE_LOCATION_PERM)
    public void accessfinelocationTask() {
        if (PermissionUtil.hasAccessFineLocationPermission(this)) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Fine Location things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_fine_location),
                    RC_ACCESS_FINE_LOCATION_PERM,
                    Manifest.permission.ACCESS_FINE_LOCATION);

        }

    }


    @AfterPermissionGranted(RC_CALL_PHONE_PERM)
    public void callphoneTask() {
        if (PermissionUtil.hasCallPhonePermission(this)) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Call Phone things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_call_phone),
                    RC_CALL_PHONE_PERM,
                    Manifest.permission.CALL_PHONE);

        }

    }

    @AfterPermissionGranted(RC_READ_PHONE_STATE_PERM)
    public void readphonestateTask() {
        if (PermissionUtil.hasReadPhoneStatePermission(this)) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Read Phone State things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_read_phone_state),
                    RC_READ_PHONE_STATE_PERM,
                    Manifest.permission.READ_PHONE_STATE);

        }

    }


    @AfterPermissionGranted(RC_INTERNET_PERM)
    public void internetTask() {
        if (PermissionUtil.hasInternetPermission(this)) {
            // Have permission, do the thing!
           // Toast.makeText(this, "TODO: Internet", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_internet),
                    RC_INTERNET_PERM,
                    Manifest.permission.INTERNET);

        }

    }

    public void disableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(false);

        btnAdd.setEnabled(false);


    }

    public void enableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(true);

        btnAdd.setEnabled(true);

    }



        private void scheduleDismissToolbar () {

            Handler handler = new Handler();
            handler.postDelayed(this::dismissActivity, 250);
        }
        private void dismissActivity () {
            doClose();
            View btnBack = toolbar.getChildAt(2);
            btnBack.clearAnimation();
            if (!CameraPermission || !ReadExternalStoragePermission || !WriteExternalStoragePermission || !RecordAudioPermission || !AccessFineLocationPermission || !CallPhonePermission || !ReadPhoneStatePermission || !InternetPermission) {
                doClose();
                finishAffinity();
                System.exit(0);

            } else {
                intent = new Intent(this, AccidentMenu.class);
                startActivity(intent);

            }

        }

        public void doClose () {
            mPersistenceObjDao.closeAll();
        }
        private void scheduleDoBullhorn () {
            Handler handler = new Handler();
            handler.postDelayed(this::doBullhorn, 250);
        }

        private void doBullhorn () {
            switch (DA_HORN) {

                case "btnAdd": {

                    break;
                }
            }
        }



        public void onBackPressed () {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);

        }

    }

