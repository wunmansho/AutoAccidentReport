package com.auto.accident.report.presenter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.auto.accident.report.R;
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.DeviceVehicleDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.database.VehicleTypeDao;
import com.auto.accident.report.model.DeviceUser;
import com.auto.accident.report.model.DeviceVehicle;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;
import com.auto.accident.report.util.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

//import com.mydamp.accidents.database.StateDao;

public class UpdateDeviceVehicle extends AppCompatActivity
        implements RecognitionListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "UpdateDeviceVehicle";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_PLATE = 99;
    private static final int REQ_CODE_STATE = 98;
    private static final int REQ_CODE_EXPIRATION_DATE = 97;
    private static final int REQ_CODE_VIN = 96;
    private static final int REQ_CODE_YEAR = 94;
    private static final int REQ_CODE_MAKE = 93;
    private static final int REQ_CODE_MODEL = 92;
    private static final int REQ_CODE_PLATE_ONLY = 30;
    private static final int REQ_CODE_STATE_ONLY = 29;
    private static final int REQ_CODE_EXPIRATION_DATE_ONLY = 28;
    private static final int REQ_CODE_VIN_ONLY = 27;
    private static final int REQ_CODE_YEAR_ONLY = 26;
    private static final int REQ_CODE_MAKE_ONLY = 25;
    private static final int REQ_CODE_MODEL_ONLY = 24;
    private DeviceVehicleDao mDeviceVehicleDao;
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff, ll00, ll01;
    private FloatingActionButton btnSave;
    private FloatingActionButton btnDelete;
    private FloatingActionButton btnSpeakOff;
    private EditText tieXX_TAG, tieXX_STATE, tieXX_EXPIRATION, tieXX_VIN, tieXX_YEAR,
            tieXX_MAKE, tieXX_MODEL;
    private TextView tvXX_ID;
    private TextView tvDA_PROMPT;
    //   private Spinner  spDV_PTYPE;
    private String DV_PTYPE;
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
    private Resources res;
    private Spinner vehicle_type_spinner;
    private final List<String> vehicletype = new ArrayList<>();
    private String VehicleType;
    private CountryCodePicker ccpXX_PLATE_COUNTRY;
    List<com.auto.accident.report.model.VehicleType> vehicletypeList = new ArrayList<>();

 

    private String preferOfflineLanguages;
    private boolean preferOffline;
    private String preferVinShown;
    private boolean preferVin;
    private TextView tvXX_VIN;
    private String DA_RESULT;
    private String[] singleChoiceItems;
    private ArrayList<String> resultx;
    private ImageButton bullHornYearOn;
    private ImageButton bullHornMakeOn;
    private ImageButton bullHornModelOn;
    private ImageButton bullHornPlateOn;
    private ImageButton bullHornVinOn;
    private ImageButton bullHornExpOn;
    private ImageButton bullHornStateOn;

    private ImageButton bullHornYearOff;
    private ImageButton bullHornMakeOff;
    private ImageButton bullHornModelOff;
    private ImageButton bullHornPlateOff;
    private ImageButton bullHornVinOff;
    private ImageButton bullHornExpOff;
    private ImageButton bullHornStateOff;
    private FloatingActionButton btnHelp;
    private FloatingActionButton btnSpeakOn;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private PersistenceObjDao mPersistenceObjDao;
    private String rsMode;
    private PersistenceObj persistenceObj;
    private DeviceUser deviceUser;
    private  DeviceUserDao mDeviceUserDao;
    private View view;
    private String DA_HORN;
    private Context context;
    private String timeStamp;
    private int DV_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.update_device_vehicle);
        ll00 = findViewById(R.id.add_device_vehicle_ll00);
        btnHelp = findViewById(R.id.btnHelp);
        bullHornYearOn = findViewById(R.id.bullHornYearOn);
        bullHornMakeOn = findViewById(R.id.bullHornMakeOn);
        bullHornModelOn = findViewById(R.id.bullHornModelOn);
        bullHornPlateOn = findViewById(R.id.bullHornPlateOn);
        bullHornVinOn = findViewById(R.id.bullHornVinOn);
        bullHornExpOn = findViewById(R.id.bullHornExpOn);
        bullHornStateOn = findViewById(R.id.bullHornStateOn);
        bullHornYearOff = findViewById(R.id.bullHornYearOff);
        bullHornMakeOff = findViewById(R.id.bullHornMakeOff);
        bullHornModelOff = findViewById(R.id.bullHornModelOff);
        bullHornPlateOff = findViewById(R.id.bullHornPlateOff);
        bullHornVinOff = findViewById(R.id.bullHornVinOff);
        bullHornExpOff = findViewById(R.id.bullHornExpOff);
        bullHornStateOff = findViewById(R.id.bullHornStateOff);
        ll01 = findViewById(R.id.ll01);
        ccpXX_PLATE_COUNTRY = findViewById(R.id.ccpXX_PLATE_COUNTRY);
        vehicle_type_spinner = findViewById(R.id.vehicle_type_spinner);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        progressBar = findViewById(R.id.progressBar1);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tvXX_ID = findViewById(R.id.tvXX_ID);
        TextView tvXX_AID = findViewById(R.id.tvXX_ID);
        tieXX_TAG = findViewById(R.id.tieXX_TAG);
        tieXX_STATE = findViewById(R.id.tieXX_STATE);
        tieXX_EXPIRATION = findViewById(R.id.tieXX_EXPIRATION);
        tieXX_VIN = findViewById(R.id.tieXX_VIN);
        tvXX_VIN = findViewById(R.id.tvXX_VIN);
        tieXX_YEAR = findViewById(R.id.tieXX_YEAR);
        tieXX_MAKE = findViewById(R.id.tieXX_MAKE);
        tieXX_MODEL = findViewById(R.id.tieXX_MODEL);
        TextView tvXX_CUID = findViewById(R.id.tvXX_CUID);
        TextView tvXX_CDATE = findViewById(R.id.tvXX_CDATE);
        TextView tvXX_UUID = findViewById(R.id.tvXX_UUID);
        TextView tvXX_UDATE = findViewById(R.id.tvXX_UDATE);
        btnSave = findViewById(R.id.btnSave);
        btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        btnDelete = findViewById(R.id.btnDelete);
        mDeviceVehicleDao = new DeviceVehicleDao(this);
        //   mStateDao = new StateDao(this);
        res = getResources();
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "DEVICE_VEHICLE");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "UPDATE_DEVICE_VEHICLE");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DV_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DV_ID");
        tvXX_ID.setText(persistenceObj.getPERSISTENCE_VALUE());
        DV_ID = Integer.parseInt(tvXX_ID.getText().toString());
        mDeviceUserDao = new DeviceUserDao(this);
        switch (rsMode) {
            case "ACCIDENT_MENU":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.udv));
                toolbar.setTitle(getString(R.string.app_name));
                break;
            case "ACCIDENT_MENU_NAVIGATION_DRAWER":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.udv));
                toolbar.setTitle(getString(R.string.app_name));
                break;
            default:
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
                String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                int DUX_ID = Integer.parseInt(DA_ID);

                deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
                String DU_FNAME = deviceUser.getDU_FNAME();
                String[] splitString;
                int splitLength;
                String DA_RESULT2;
                splitString = DU_FNAME.split(" ");
                DA_RESULT2 = splitString[0];

                toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.udv));
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
                toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);

                tvXX_UUID.setText(DA_ID);

        }
        mPersistenceObjDao.updateData("DP_CATEGORY", "DEVICE_VEHICLE");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_OFFLINE");
        String preferOfflineLanguages = persistenceObj.getPERSISTENCE_VALUE();
        if (preferOfflineLanguages.equals("FALSE")) {
            preferOffline = false;

        } else {
            preferOffline = true;

        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_VIN");
        preferVinShown = persistenceObj.getPERSISTENCE_VALUE();
        if (preferVinShown.equals("FALSE")) {
            preferVin = false;
            bullHornVinOff.setVisibility(GONE);
            tvXX_VIN.setVisibility(GONE);
            tieXX_VIN.setVisibility(GONE);
        } else {
            preferVin = true;
            bullHornVinOff.setVisibility(View.VISIBLE);
            tvXX_VIN.setVisibility(View.VISIBLE);
            tieXX_VIN.setVisibility(View.VISIBLE);

        }
        ActionBar actionBar = getSupportActionBar();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        DeviceVehicle deviceVehicle = mDeviceVehicleDao.getDeviceVehicle(DV_ID);
        tieXX_TAG.setText(deviceVehicle.getDV_TAG());
        tieXX_STATE.setText(deviceVehicle.getDV_STATE());
        tieXX_EXPIRATION.setText(deviceVehicle.getDV_EXPIRATION());
        tieXX_VIN.setText(deviceVehicle.getDV_VIN());
        tieXX_YEAR.setText(deviceVehicle.getDV_YEAR());
        tieXX_MAKE.setText(deviceVehicle.getDV_MAKE());
        tieXX_MODEL.setText(deviceVehicle.getDV_MODEL());
        tvXX_CUID.setText(Integer.toString(deviceVehicle.getDV_CUID()));
        tvXX_CDATE.setText(deviceVehicle.getDV_CDATE());
        tvXX_UUID.setText(Integer.toString(deviceVehicle.getDV_UUID()));
        tvXX_UDATE.setText(deviceVehicle.getDV_UDATE());
        String CDV_TYPE = deviceVehicle.getDV_TYPE();
        ccpXX_PLATE_COUNTRY.setCountryForNameCode(deviceVehicle.getDV_PLATE_COUNTRY());
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);
        });
        btnHelp.setOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnHelp.startAnimation(rotateAnimation);
            scheduleDoHelp();

        });
        //      Intent receivedIntent = getIntent();
        bullHornYearOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornYearOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornYearOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();

        });

        bullHornYearOn.setOnClickListener(view -> {
            DA_HORN = "bullHornYearOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornYearOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornMakeOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornMakeOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornMakeOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornMakeOn.setOnClickListener(view -> {
            DA_HORN = "bullHornMakeOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornMakeOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornModelOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornModelOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornModelOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornModelOn.setOnClickListener(view -> {
            DA_HORN = "bullHornModelOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornModelOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();

        });
        bullHornPlateOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornPlateOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPlateOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornPlateOn.setOnClickListener(view -> {
            DA_HORN = "bullHornPlateOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPlateOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();

        });
        bullHornVinOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornVinOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornVinOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornVinOn.setOnClickListener(view -> {
            DA_HORN = "bullHornVinOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornVinOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornExpOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornExpOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornExpOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornExpOn.setOnClickListener(view -> {
            DA_HORN = "bullHornExpOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornExpOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });
        bullHornStateOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornStateOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornStateOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornStateOn.setOnClickListener(view -> {
            DA_HORN = "bullHornStateOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornStateOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });
        btnSpeakOn.setOnClickListener(view -> {
            DA_HORN = "btnSpeakOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnSpeakOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        btnSpeakOff.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                turnOffLastButton(view);
                DA_HORN = "btnSpeakOff";
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnSpeakOff.startAnimation(rotateAnimation);
                scheduleDoBullhorn();
            }
            fireClick = true;
            btnSpeakOff.setImageAlpha(alpha1);

        });
        btnSpeakOff.setOnLongClickListener(view -> {

            btnSpeakOff.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
            message = res.getString(R.string.mic_and_meg);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);

            mdToast.show();

            fireClick = false;

            return false;

        });


        btnSave.setOnClickListener(view -> {
            if (fireClick == true) {
                DA_HORN = "btnSave";
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnSave.startAnimation(rotateAnimation);
                scheduleDoBullhorn();
            }
            fireClick = true;
            btnSave.setImageAlpha(alpha1);
        });
        btnSave.setOnLongClickListener(view -> {
            btnSave.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
            message = res.getString(R.string.tv0145);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });

        btnDelete.setOnClickListener(view -> {
            if (fireClick == true) {
                DA_HORN = "btnDelete";
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnDelete.startAnimation(rotateAnimation);
                scheduleDoBullhorn();
            }
            fireClick = true;
            btnDelete.setImageAlpha(alpha1);
        });
        btnDelete.setOnLongClickListener(view -> {
            btnDelete.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
            message = res.getString(R.string.tv0154);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });

        vehicle_type_spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        int y = vehicle_type_spinner.getAdapter().getCount();
        for (int x = 0; x < y; x++) {
            String SpinnerText = ((Spinner) findViewById(R.id.vehicle_type_spinner)).getItemAtPosition(x).toString();
            if (SpinnerText.equals(CDV_TYPE)) {
                //     Toast.makeText(this, SpinnerText, Toast.LENGTH_SHORT).show();
                if (x > 0) {
                    llbtnSpeakOff.setVisibility(View.VISIBLE);
                    ll01.setVisibility(View.VISIBLE);
                    btnDelete.show();
                    btnSave.show();

                } else {
                    llbtnSpeakOff.setVisibility(View.GONE);
                    ll01.setVisibility(View.GONE);
                    btnDelete.hide();
                    btnSave.hide();
                }

                vehicle_type_spinner.setSelection(x);
                break;
            }
        }


    }

    private void loadSpinnerData() {
        VehicleTypeDao mVehicleTypeDao = new VehicleTypeDao(getApplicationContext());
        Cursor cursor = mVehicleTypeDao.getData();
        // Spinner Drop down elements
        if (cursor.moveToFirst()) {
            do {
                vehicletype.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, vehicletype);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        vehicle_type_spinner.setAdapter(dataAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        if (position > 0) {
            llbtnSpeakOff.setVisibility(View.VISIBLE);
            ll01.setVisibility(View.VISIBLE);
            btnDelete.show();
            btnSave.show();
        } else {
            llbtnSpeakOff.setVisibility(View.GONE);
            ll01.setVisibility(View.GONE);
            btnDelete.hide();
            btnSave.hide();
        }
        // On selecting a spinner carousel_item
        String type = parent.getItemAtPosition(position).toString();
        VehicleType = parent.getItemAtPosition(position).toString();
        // Showing selected spinner carousel_item
        if (VehicleType.equals("null")) {
            // Toast.makeText(parent.getContext(), "Please Select A Valid Party Type",
            //     Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //  toastMessage("Please Select A Valid Party Type");
    }


    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the Field Name to Begin or Quit to Exit");
        tvDA_PROMPT.setText(getString(R.string.tv0200) + getString(R.string.tv0227));
        try {
            REQ_CODE = REQ_CODE_SPEECH_INPUT;
            speech.startListening(intent);
            //   startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void resetBeforeExit() {
        tvDA_PROMPT.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        llbtnSpeakOff.setVisibility(View.VISIBLE);
        llbtnSpeakOn.setVisibility(View.GONE);
        //   inc01.setVisibility(View.GONE);
        //    AutoAdd = false;
        super.onStop();
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    private void startVehicleYearInput() {
        tieXX_YEAR.requestFocus();
        int DA_SIZE = tieXX_YEAR.length();
        tieXX_YEAR.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0259) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a State Name.");
        try {
            REQ_CODE = REQ_CODE_YEAR;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_YEAR);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startVehicleMakeInput() {
        tieXX_MAKE.requestFocus();
        int DA_SIZE = tieXX_MAKE.length();
        tieXX_MAKE.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0260) + getString(R.string.tv0218));

        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Address, QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_MAKE;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_MAKE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startVehicleModelInput() {
        tieXX_MODEL.requestFocus();
        int DA_SIZE = tieXX_MODEL.length();
        tieXX_MODEL.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0261) + getString(R.string.tv0218));
        //     intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Im ready for entity_address line 2 or next to skip.");
        try {
            REQ_CODE = REQ_CODE_MODEL;
            speech.startListening(intent);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startLicensePlateInput() {
        tieXX_TAG.requestFocus();
        int DA_SIZE = tieXX_TAG.length();
        tieXX_TAG.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0255) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_PLATE;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_PLATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startPlateStateInput() {
        tieXX_STATE.requestFocus();
        int DA_SIZE = tieXX_STATE.length();
        tieXX_STATE.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0256) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Middle Initial QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_STATE;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_STATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startPlateExpirationInput() {
        tieXX_EXPIRATION.requestFocus();
        int DA_SIZE = tieXX_EXPIRATION.length();
        tieXX_EXPIRATION.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Last Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0257) + getString(R.string.tv0218));
        try {
            REQ_CODE = REQ_CODE_EXPIRATION_DATE;
            speech.startListening(intent);
            //   startActivityForResult(intent, REQ_CODE_EXPIRATION_DATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startVinInput() {
        tieXX_VIN.requestFocus();
        int DA_SIZE = tieXX_VIN.length();
        tieXX_VIN.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0258) + getString(R.string.tv0218));
        try {
            REQ_CODE = REQ_CODE_VIN;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_VIN);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startVehicleYearInputOnly() {
        tieXX_YEAR.requestFocus();
        int DA_SIZE = tieXX_YEAR.length();
        tieXX_YEAR.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0259) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a State Name.");
        try {
            REQ_CODE = REQ_CODE_YEAR_ONLY;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_YEAR);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startVehicleMakeInputOnly() {
        tieXX_MAKE.requestFocus();
        int DA_SIZE = tieXX_MAKE.length();
        tieXX_MAKE.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0260) + getString(R.string.tv0218));

        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Address, QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_MAKE_ONLY;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_MAKE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startVehicleModelInputOnly() {
        tieXX_MODEL.requestFocus();
        int DA_SIZE = tieXX_MODEL.length();
        tieXX_MODEL.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0261) + getString(R.string.tv0218));
        //     intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Im ready for entity_address line 2 or next to skip.");
        try {
            REQ_CODE = REQ_CODE_MODEL_ONLY;
            speech.startListening(intent);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startLicensePlateInputOnly() {
        tieXX_TAG.requestFocus();
        int DA_SIZE = tieXX_TAG.length();
        tieXX_TAG.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0255) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_PLATE_ONLY;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_PLATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startPlateStateInputOnly() {
        tieXX_STATE.requestFocus();
        int DA_SIZE = tieXX_STATE.length();
        tieXX_STATE.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0256) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Middle Initial QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_STATE_ONLY;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_STATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startPlateExpirationInputOnly() {
        tieXX_EXPIRATION.requestFocus();
        int DA_SIZE = tieXX_EXPIRATION.length();
        tieXX_EXPIRATION.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Last Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0257) + getString(R.string.tv0218));
        try {
            REQ_CODE = REQ_CODE_EXPIRATION_DATE_ONLY;
            speech.startListening(intent);
            //   startActivityForResult(intent, REQ_CODE_EXPIRATION_DATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startVinInputOnly() {
        tieXX_VIN.requestFocus();
        int DA_SIZE = tieXX_VIN.length();
        tieXX_VIN.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0258) + getString(R.string.tv0218));
        try {
            REQ_CODE = REQ_CODE_VIN_ONLY;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_VIN);
        } catch (ActivityNotFoundException a) {

        }
    }


    private void retry() {
        switch (REQ_CODE) {
            case REQ_CODE_PLATE: {
                startLicensePlateInput();
                break;
            }
            case REQ_CODE_STATE: {
                startPlateStateInput();
                break;
            }
            case REQ_CODE_EXPIRATION_DATE: {
                startPlateExpirationInput();
                break;
            }
            case REQ_CODE_VIN: {
                startVinInput();
                break;
            }
            case REQ_CODE_YEAR: {
                startVehicleYearInput();
                break;
            }
            case REQ_CODE_MAKE: {
                startVehicleMakeInput();
                break;
            }
            case REQ_CODE_MODEL: {
                startVehicleModelInput();
                break;
            }
        }
    }

    private String undoCharacter(String DA_RESULT2) {
        int DA_SIZE = DA_RESULT2.length() - 1;
        DA_RESULT2 = DA_RESULT2.substring(0, DA_SIZE);

        return DA_RESULT2;
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> result = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String DA_RESULT = result.get(0);
        String DA_RESULTCAP = DA_RESULT.toUpperCase();
        if (DA_RESULTCAP.equals(getString(R.string.tv0220)) || DA_RESULTCAP.equals(getString(R.string.tv0222)) || DA_RESULTCAP.equals(getString(R.string.tv0224)) || DA_RESULTCAP.equals(getString(R.string.tv0226))) {
            DA_RESULT = DA_RESULT.toUpperCase();
        }

        switch (REQ_CODE) {
            case REQ_CODE_SPEECH_INPUT: {
                if (null != DA_RESULT) {
                    DA_RESULT = DA_RESULT.toUpperCase();
                    if (DA_RESULT.equals(getString(R.string.tv0248))) {
                        startLicensePlateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0249))) {
                        startPlateStateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0250))) {
                        startPlateExpirationInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0251))) {
                        startVinInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0252))) {
                        startVehicleYearInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0253))) {
                        startVehicleMakeInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0254))) {
                        startVehicleModelInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                }
                startVoiceInput();
                break;
            }
            case REQ_CODE_YEAR: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_YEAR.setText(DA_RESULT);
                        startVehicleMakeInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }

                }
                if (DA_RESULT.equals(getString(R.string.tv0226))) {
                    startVinInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0222))) {
                    startVehicleMakeInput();
                    break;
                }

                if (DA_RESULT.equals(getString(R.string.tv0224))) {

                    DA_RESULT = "";
                    tieXX_YEAR.setText(DA_RESULT);
                    startVehicleYearInput();
                    break;
                }
                startVehicleYearInput();
                break;
            }

            case REQ_CODE_MAKE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        DA_RESULT = utils.capEachWord(DA_RESULT);
                        tieXX_MAKE.setText(DA_RESULT);
                        startVehicleModelInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startVehicleYearInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        if (AutoAdd.equals(true)) {
                            startVehicleModelInput();
                            break;
                        } else {
                            startVoiceInput();
                            break;
                        }
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_MAKE.setText(DA_RESULT);
                        startVehicleMakeInput();
                        break;
                    }


                    startVehicleMakeInput();
                }
                break;
            }
            case REQ_CODE_MODEL: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        DA_RESULT = utils.capEachWord(DA_RESULT);
                        tieXX_MODEL.setText(DA_RESULT);
                        //   resetBeforeExit();
                        startLicensePlateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startVehicleMakeInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_MODEL.setText(DA_RESULT);
                        startVehicleModelInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        if (AutoAdd.equals(true)) {
                            startLicensePlateInput();
                            break;
                        } else {
                            startVoiceInput();
                            break;
                        }
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_MAKE.setText(DA_RESULT);
                        startVehicleMakeInput();
                        break;
                    }

                    startVehicleModelInput();
                }
                break;
            }
            case REQ_CODE_PLATE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        resultx = result;
                        int counter = 0;
                        for (String DA_RESULT3 : resultx) {
                            DA_RESULT3 = DA_RESULT3.toUpperCase();
                            DA_RESULT3 = utils.removeSpaces(DA_RESULT3);
                            DA_RESULT3 = utils.removeDashes(DA_RESULT3);
                            resultx.set(counter, DA_RESULT3);
                            counter++;
                        }
                        singleChoiceItems = new String[resultx.size()];

                        singleChoiceItems = resultx.toArray(singleChoiceItems);
                        getSelectedItemLicensePlate();

                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startPlateStateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_TAG.setText(DA_RESULT);
                        startLicensePlateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startVehicleModelInput();
                        break;
                    }
                    startLicensePlateInput();
                }
                break;
            }
            case REQ_CODE_STATE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        //      String St = mStateDao.getState(DA_RESULT);

                        //      if (St != null) {
                        tieXX_STATE.setText(DA_RESULT);
                        startPlateExpirationInput();
                        break;
                        //  }


                        //  DA_RESULT = DA_RESULT.toUpperCase();
                        //  DA_RESULT = DA_RESULT.substring(0, 1);


                        // break;

                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startLicensePlateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startPlateExpirationInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_STATE.setText(DA_RESULT);
                        startPlateStateInput();
                        break;
                    }
                    startPlateStateInput();
                }
                break;
            }
            case REQ_CODE_EXPIRATION_DATE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {


                        tieXX_EXPIRATION.setText(DA_RESULT);
                        if (preferVin == false) {
                            resetBeforeExit();
                            resetButtons();
                        }
                        if (preferVin == true) {
                            startVinInput();
                        }
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startPlateStateInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        if (preferVin == true) {
                            startVinInput();
                        }
                        if (preferVin == false) {
                            resetBeforeExit();
                            resetButtons();
                        }
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_EXPIRATION.setText(DA_RESULT);
                        startPlateExpirationInput();
                        break;
                    }
                    startPlateExpirationInput();
                    break;
                }
            }
            // undoCharacter

            case REQ_CODE_VIN: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        resultx = result;
                        int counter = 0;
                        for (String DA_RESULT3 : resultx) {
                            DA_RESULT3 = DA_RESULT3.toUpperCase();
                            DA_RESULT3 = utils.removeSpaces(DA_RESULT3);
                            DA_RESULT3 = utils.removeDashes(DA_RESULT3);
                            resultx.set(counter, DA_RESULT3);
                            counter++;
                        }
                        singleChoiceItems = new String[resultx.size()];
                        singleChoiceItems = resultx.toArray(singleChoiceItems);
                        getSelectedItemVin();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }

                }
                if (DA_RESULT.equals(getString(R.string.tv0224))) {
                    DA_RESULT = "";
                    tieXX_VIN.setText(DA_RESULT);
                    startVinInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0226))) {
                    startPlateExpirationInput();
                    break;
                }
                //    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                //       startVehicleYearInput();
                //      break;
                //  }

                startVinInput();
                break;
            }
            case REQ_CODE_YEAR_ONLY: {
                if (null != DA_RESULT) {

                    tieXX_YEAR.setText(DA_RESULT);
                    bullHornYearOn.callOnClick();
                    resetBeforeExit();
                    resetButtons();
                    break;
                }

                bullHornYearOn.callOnClick();
                resetBeforeExit();
                resetButtons();
                break;
            }

            case REQ_CODE_MAKE_ONLY: {
                if (null != DA_RESULT) {
                    DA_RESULT = utils.capEachWord(DA_RESULT);
                    tieXX_MAKE.setText(DA_RESULT);
                    bullHornMakeOn.callOnClick();
                    resetBeforeExit();
                    resetButtons();
                    break;

                }
                bullHornMakeOn.callOnClick();
                resetBeforeExit();
                resetButtons();
                break;
            }
            case REQ_CODE_MODEL_ONLY: {
                if (null != DA_RESULT) {
                    DA_RESULT = utils.capEachWord(DA_RESULT);
                    tieXX_MODEL.setText(DA_RESULT);

                    resetBeforeExit();
                    bullHornModelOn.callOnClick();
                    break;

                }
                resetBeforeExit();
                bullHornModelOn.callOnClick();
                break;
            }
            case REQ_CODE_PLATE_ONLY: {
                if (null != DA_RESULT) {
                    resultx = result;
                    int counter = 0;
                    for (String DA_RESULT3 : resultx) {
                        DA_RESULT3 = DA_RESULT3.toUpperCase();
                        DA_RESULT3 = utils.removeSpaces(DA_RESULT3);
                        DA_RESULT3 = utils.removeDashes(DA_RESULT3);
                        resultx.set(counter, DA_RESULT3);
                        counter++;
                    }
                    singleChoiceItems = new String[resultx.size()];

                    singleChoiceItems = resultx.toArray(singleChoiceItems);
                    getSelectedItemLicensePlateOnly();

                    break;

                }
                bullHornPlateOn.callOnClick();
                resetBeforeExit();
                break;
            }
            case REQ_CODE_STATE_ONLY: {
                if (null != DA_RESULT) {
                    tieXX_STATE.setText(DA_RESULT);
                    bullHornStateOn.callOnClick();
                    resetBeforeExit();
                    break;

                }
                bullHornStateOn.callOnClick();
                resetBeforeExit();
                break;
            }
            case REQ_CODE_EXPIRATION_DATE_ONLY: {
                if (null != DA_RESULT) {
                    tieXX_EXPIRATION.setText(DA_RESULT);
                    bullHornExpOn.callOnClick();
                    resetBeforeExit();
                    break;
                }
                bullHornExpOn.callOnClick();
                resetBeforeExit();
                break;
            }
            // undoCharacter

            case REQ_CODE_VIN_ONLY: {
                if (null != DA_RESULT) {
                    resultx = result;
                    int counter = 0;
                    for (String DA_RESULT3 : resultx) {
                        DA_RESULT3 = DA_RESULT3.toUpperCase();
                        DA_RESULT3 = utils.removeSpaces(DA_RESULT3);
                        DA_RESULT3 = utils.removeDashes(DA_RESULT3);
                        resultx.set(counter, DA_RESULT3);
                        counter++;
                    }
                    singleChoiceItems = new String[resultx.size()];
                    singleChoiceItems = resultx.toArray(singleChoiceItems);
                    getSelectedItemVinOnly();
                    break;
                }

                bullHornVinOn.callOnClick();
                resetBeforeExit();
                break;
            }


        }
    }
    public void getSelectedItemLicensePlateOnly() {
        int itemSelected = 0;
        Resources res = getResources();
        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.select_tag_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resultx.get(selectedIndex);
                                tieXX_TAG.setText(DA_RESULT);
                                resetBeforeExit();
                                bullHornPlateOn.callOnClick();
                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        resetBeforeExit();
                        bullHornPlateOn.callOnClick();
                        dialogInterface.dismiss();
                    }
                })

                .show();


    }

    public void getSelectedItemVinOnly() {
        int itemSelected = 0;
        Resources res = getResources();
        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.select_vehicle_identification_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resultx.get(selectedIndex);
                                tieXX_VIN.setText(DA_RESULT);
                                bullHornVinOn.callOnClick();
                                resetBeforeExit();
                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        bullHornVinOn.callOnClick();
                        resetBeforeExit();
                        dialogInterface.dismiss();
                    }
                })

                .show();


    }


    public void getSelectedItemLicensePlate() {
        int itemSelected = 0;
        Resources res = getResources();
        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.select_tag_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resultx.get(selectedIndex);
                                tieXX_TAG.setText(DA_RESULT);
                                startPlateStateInput();
                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        startLicensePlateInput();
                        dialogInterface.dismiss();
                    }
                })

                .show();


    }

    public void getSelectedItemVin() {
        int itemSelected = 0;
        Resources res = getResources();
        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.select_vehicle_identification_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resultx.get(selectedIndex);
                                tieXX_VIN.setText(DA_RESULT);
                                resetBeforeExit();
                                resetButtons();
                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        startVinInput();
                        dialogInterface.dismiss();
                    }
                })

                .show();


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setVisibility(View.VISIBLE);

        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.INVISIBLE);
        //   toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        resetBeforeExit();
        resetButtons();
        message = res.getString(R.string.speech_timeout);
        duration = 20;
        type = 3;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);
        mdToast.show();
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    private String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = res.getString(R.string.tv1000);
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = res.getString(R.string.tv1001);
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = res.getString(R.string.tv1002);
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = res.getString(R.string.tv1003);
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = res.getString(R.string.tv1004);
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = res.getString(R.string.tv1005);
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = res.getString(R.string.tv1006);
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = res.getString(R.string.tv1007);
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = res.getString(R.string.tv1008);
                break;
            default:
                message = res.getString(R.string.tv1009);
                break;
        }
        return message;
    }
    public void resetButtons() {
        bullHornYearOn.setVisibility(GONE);
        bullHornMakeOn.setVisibility(GONE);
        bullHornModelOn.setVisibility(GONE);
        bullHornPlateOn.setVisibility(GONE);
        bullHornVinOn.setVisibility(GONE);
        bullHornExpOn.setVisibility(GONE);
        bullHornStateOn.setVisibility(GONE);
        btnSpeakOn.hide();
        llbtnSpeakOn.setVisibility(GONE);
        tvDA_PROMPT.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
        bullHornYearOff.setVisibility(VISIBLE);
        bullHornMakeOff.setVisibility(VISIBLE);
        bullHornModelOff.setVisibility(VISIBLE);
        bullHornPlateOff.setVisibility(VISIBLE);
        if (preferVin == true) {
            bullHornVinOff.setVisibility(VISIBLE);
        }
        bullHornExpOff.setVisibility(VISIBLE);
        bullHornStateOff.setVisibility(VISIBLE);
        btnHelp.hide();
        btnSpeakOff.show();
        btnSave.show();
        btnDelete.show();
        llbtnSpeakOff.setVisibility(VISIBLE);
    }

    public void hideButtons() {
        bullHornYearOn.setVisibility(GONE);
        bullHornMakeOn.setVisibility(GONE);
        bullHornModelOn.setVisibility(GONE);
        bullHornPlateOn.setVisibility(GONE);
        bullHornVinOn.setVisibility(GONE);
        bullHornExpOn.setVisibility(GONE);
        bullHornStateOn.setVisibility(GONE);
        btnSpeakOn.hide();
        bullHornYearOff.setVisibility(GONE);
        bullHornMakeOff.setVisibility(GONE);
        bullHornModelOff.setVisibility(GONE);
        bullHornPlateOff.setVisibility(GONE);
        bullHornVinOff.setVisibility(GONE);
        bullHornExpOff.setVisibility(GONE);
        bullHornStateOff.setVisibility(GONE);
        btnHelp.hide();
        btnSpeakOff.hide();
        btnSave.hide();
        btnDelete.hide();
    }

    public void turnOffLastButton(View view) {
        if (bullHornYearOn.getVisibility() == VISIBLE) {
            bullHornYearOn.callOnClick();
        }
        if (bullHornMakeOn.getVisibility() == VISIBLE) {
            bullHornMakeOn.callOnClick();
        }
        if (bullHornModelOn.getVisibility() == VISIBLE) {
            bullHornModelOn.callOnClick();
        }
        if (bullHornPlateOn.getVisibility() == VISIBLE) {
            bullHornPlateOn.callOnClick();
        }
        if (bullHornExpOn.getVisibility() == VISIBLE) {
            bullHornExpOn.callOnClick();
        }
        if (bullHornVinOn.getVisibility() == VISIBLE) {
            bullHornVinOn.callOnClick();
        }
        if (bullHornStateOn.getVisibility() == VISIBLE) {
            bullHornStateOn.callOnClick();
        }
        if (llbtnSpeakOn.getVisibility() == VISIBLE) {
            btnSpeakOn.callOnClick();
        }

    }

    public void disableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(false);
        btnSpeakOff.setEnabled(false);
        btnSpeakOn2.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);
        bullHornYearOn.setEnabled(false);
        bullHornMakeOn.setEnabled(false);
        bullHornModelOn.setEnabled(false);
        bullHornPlateOn.setEnabled(false);
        bullHornVinOn.setEnabled(false);
        bullHornExpOn.setEnabled(false);
        bullHornStateOn.setEnabled(false);
        bullHornYearOff.setEnabled(false);
        bullHornMakeOff.setEnabled(false);
        bullHornModelOff.setEnabled(false);
        bullHornPlateOff.setEnabled(false);
        bullHornVinOff.setEnabled(false);
        bullHornExpOff.setEnabled(false);
        bullHornStateOff.setEnabled(false);

    }
    public void enableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(true);
        btnSpeakOff.setEnabled(true);
        btnSpeakOn2.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(true);
        bullHornYearOn.setEnabled(true);
        bullHornMakeOn.setEnabled(true);
        bullHornModelOn.setEnabled(true);
        bullHornPlateOn.setEnabled(true);
        bullHornVinOn.setEnabled(true);
        bullHornExpOn.setEnabled(true);
        bullHornStateOn.setEnabled(true);
        bullHornYearOff.setEnabled(true);
        bullHornMakeOff.setEnabled(true);
        bullHornModelOff.setEnabled(true);
        bullHornPlateOff.setEnabled(true);
        bullHornVinOff.setEnabled(true);
        bullHornExpOff.setEnabled(true);
        bullHornStateOff.setEnabled(true);
    }

    public void showSequence0(View view) {
        turnOffLastButton(view);
        KeyboardUtils.hideKeyboard(UpdateDeviceVehicle.this);
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(ll00)
                        .setPrimaryText(res.getString(R.string.press_to_select_vehicle_type))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state != MaterialTapTargetPrompt.STATE_REVEALING && state != MaterialTapTargetPrompt.STATE_REVEALED  && state != MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)

                                {
                                    enableButtons();
                                    //makeTieFocusableTrue();

                                }
                            }
                        })
                )


                .show();
    }


    public void showSequence1(View view) {
        turnOffLastButton(view);
        KeyboardUtils.hideKeyboard(UpdateDeviceVehicle.this);
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //    ccp_license_country, ccp_resident_country, ccp_phon1_country
        //int toolBarColorValue = Color.parseColor("#FF0288D1");

        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_and_meg))
                        .setSecondaryText(res.getString(R.string.got_it))

                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(bullHornYearOff)
                        .setPrimaryText(res.getString(R.string.speech_recognition_individual))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(btnDelete)

                        .setPrimaryText(res.getString(R.string.press_to_delete_vehicle_profile))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(btnSave)

                        .setPrimaryText(res.getString(R.string.press_to_save_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )


                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateDeviceVehicle.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state != MaterialTapTargetPrompt.STATE_REVEALING && state != MaterialTapTargetPrompt.STATE_REVEALED  && state != MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)

                        {
                            enableButtons();
                            //makeTieFocusableTrue();

                        }
                    }
                })
                )

                .show();
    }
    private void scheduleDoBullhorn() {
        Handler handler = new Handler();
        handler.postDelayed(this::doBullhorn, 250);
    }

    private void doBullhorn() {
        switch (DA_HORN) {
            case "btnSave": {
                Integer DV_ID1 = Integer.parseInt(tvXX_ID.getText().toString());
                String DV_TAG = tieXX_TAG.getText().toString();
                String DV_STATE = tieXX_STATE.getText().toString();
                String DV_EXPIRATION = tieXX_EXPIRATION.getText().toString();
                String DV_VIN = tieXX_VIN.getText().toString();
                String DV_YEAR = tieXX_YEAR.getText().toString();
                String DV_MAKE = tieXX_MAKE.getText().toString();
                String DV_MODEL = tieXX_MODEL.getText().toString();
                String DV_TYPE = VehicleType;
                String DV_PLATE_COUNTRY = ccpXX_PLATE_COUNTRY.getSelectedCountryNameCode();
                mDeviceVehicleDao.updateData(DV_ID1, DV_TAG, DV_STATE, DV_EXPIRATION,
                        DV_VIN, DV_YEAR, DV_MAKE, DV_MODEL, DV_TYPE, DV_PLATE_COUNTRY);
                doClose();
                Intent intent = new Intent(UpdateDeviceVehicle.this, ListDeviceVehicle.class);
                startActivity(intent);
                break;
                           }
            case "btnDelete": {
                String IP_ID = tvXX_ID.getText().toString();
                mDeviceVehicleDao.deleteDV_ID(IP_ID);
                doClose();
                Intent intent = new Intent(UpdateDeviceVehicle.this, ListDeviceVehicle.class);
                startActivity(intent);
               break;

            }
            case "btnSpeakOff": {
                hideButtons();
                tvDA_PROMPT.setVisibility(View.VISIBLE);
                progressBar.setVisibility(VISIBLE);

                llbtnSpeakOn.setVisibility(VISIBLE);
                btnSpeakOn.show();
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                if (AutoAdd.equals(true)) {

                    startVehicleYearInput();


                } else {
                    startVoiceInput();
                }
                break;
            }
            case "btnSpeakOn": {
                resetButtons();

                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }

                break;
            }
            case "bullHornYearOff": {
                hideButtons();

                progressBar.setVisibility(VISIBLE);
                bullHornYearOn.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startVehicleYearInputOnly();
                break;
            }

            case "bullHornYearOn": {
                resetButtons();
                bullHornYearOff.setVisibility(VISIBLE);
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }

                break;
            }
            case "bullHornMakeOff": {
                hideButtons();
                bullHornMakeOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startVehicleMakeInputOnly();

                break;
            }
            case "bullHornMakeOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }


                break;
            }
            case "bullHornModelOff": {

                hideButtons();
                bullHornModelOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startVehicleModelInputOnly();
                break;
            }
            case "bullHornModelOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornPlateOff": {

                hideButtons();
                bullHornPlateOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startLicensePlateInputOnly();
                break;
            }
            case "bullHornPlateOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }

            case " bullHornVinOff": {

                hideButtons();
                bullHornVinOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startVinInputOnly();
                break;
            }
            case " bullHornVinOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }

            case "bullHornExpOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornExpOff": {

                hideButtons();
                bullHornExpOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startPlateExpirationInputOnly();
                break;
            }
            case "bullHornStateOff": {

                hideButtons();
                bullHornStateOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateDeviceVehicle.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startPlateStateInputOnly();
                break;
            }
            case "bullHornStateOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
        }
    }

    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, ListDeviceVehicle.class);
        startActivity(intent);

    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
        mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {

            if (ll01.getVisibility() == GONE) {
                showSequence0(view);
            }
            if (ll01.getVisibility() == VISIBLE) {
                showSequence1(view);
            }

    }

    public void onBackPressed() {
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(100);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        View btnBack = toolbar.getChildAt(2);
        scheduleDismissToolbar();
        btnBack.startAnimation(rotateAnimation);

    }
}
