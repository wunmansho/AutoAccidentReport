package com.auto.accident.report.presenter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.InvolvedPartyDao;
import com.auto.accident.report.models.PartyTypeDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.models.VehicleManifestDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;
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
import static com.auto.accident.report.util.utils.isNumber;

//import com.mydamp.accidents.models.StateDao;

/**
 * Created by myron on 1/18/2018.
 */

// android.speech.extra.PREFER_OFFLINE

public class AddInvolvedParty extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, RecognitionListener {
    //  StateDao mStateDao;
    private static final String TAG = "AddInvolvedParty";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_FIRST_NAME = 99;
    private static final int REQ_CODE_ADDRESS = 96;
    private static final int REQ_CODE_ADDRESS2 = 95;
    private static final int REQ_CODE_CITY = 94;
    private static final int REQ_CODE_STATE = 93;
    private static final int REQ_CODE_ZIP = 92;
    private static final int REQ_CODE_EMAIL = 91;
    private static final int REQ_CODE_HPH = 90;
    private static final int REQ_CODE_CPH = 89;
    private static final int REQ_CODE_WPH = 88;
    private static final int REQ_CODE_INVOLVMENT = 87;
    private static final int REQ_CODE_COMPANY = 84;
    private static final int REQ_CODE_LICENSE = 83;
    private static final int REQ_CODE_LICENSE_STATE = 82;
    private static final int REQ_CODE_FIRST_NAME_ONLY = 50;
    private static final int REQ_CODE_ADDRESS_ONLY = 49;
    private static final int REQ_CODE_ADDRESS2_ONLY = 48;
    private static final int REQ_CODE_CITY_ONLY = 47;
    private static final int REQ_CODE_STATE_ONLY = 46;
    private static final int REQ_CODE_ZIP_ONLY = 45;
    private static final int REQ_CODE_EMAIL_ONLY = 44;
    private static final int REQ_CODE_HPH_ONLY = 43;
    private static final int REQ_CODE_CPH_ONLY = 42;
    private static final int REQ_CODE_WPH_ONLY = 41;
    private static final int REQ_CODE_COMPANY_ONLY = 39;
    private static final int REQ_CODE_LICENSE_ONLY = 38;
    private static final int REQ_CODE_LICENSE_STATE_ONLY = 37;
    private InvolvedPartyDao mInvolvedPartyDao;

    private final String deviceLocale = Locale.getDefault().getCountry();
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff, spXX_INVOLVEMENT, ll00, ll01, ll02, ll03, llcompany;
    private FloatingActionButton btnAdd;
    private EditText tieXX_FNAME, tieXX_MI, tieXX_LNAME, tieXX_LICENSE, tieXX_LST, tieXX_ADDR1, tieXX_ADDR2,
            tieXX_CITY, tieXX_ST, tieXX_ZIP, tieXX_EMAIL, tieXX_PHON1, tieXX_PHON2,
            tieXX_PHON3, tieXX_PTYPE, tieXX_CNAM01, tieXX_PNUM01, tieXX_CNAM02, tieXX_PNUM02,
            tieXX_CNAM03, tieXX_PNUM03, tieXX_COMP, tieXX_CUID, tieXX_CDATE, tieXX_UUID,
            tieXX_UDATE;
    private TextView tvXX_AID;
    private TextView tvXX_CNAM02;
    private TextView tvXX_PNUM02;
    private TextView tvXX_CNAM03;
    private TextView tvXX_PNUM03;
    private TextView tvXX_CUID;
    private TextView tvXX_CDATE;
    private TextView tvXX_UUID;
    private TextView tvXX_UDATE;
    private TextView tvDA_PROMPT;
    private Spinner spXX_PTYPE;
    private String IP_PTYPE;
    private final String deviceLocalel = Locale.getDefault().getLanguage();
    private final Boolean AutoAdd = true;
    private Boolean AutoAddSave = false;
    private int REQ_CODE;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private final String LOG_TAG = "VoiceRecActivity";
    private String DA_RESULT2;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private CountryCodePicker ccp_license_country, ccp_resident_country, ccp_phon1_country, ccp_phon2_country, ccp_phon3_country;
    private long insertData;
    private String doOnce = "0";

   

    private String preferOfflineLanguages;
    private boolean preferOffline;
    private String DA_RESULT;
    private String[] singleChoiceItems;
    private ArrayList<String> resultx;
    private ImageButton bullHornAddr1Off;
    private ImageButton bullHornAddr2Off;
    private ImageButton bullHornCityOff;
    private ImageButton bullHornStateOff;
    private ImageButton bullHornZipOff;
    private ImageButton bullHornEmailOff;
    private ImageButton bullHornPhon1Off;
    private ImageButton bullHornPhon2Off;
    private ImageButton bullHornPhon3Off;
    private ImageButton bullHornLicOff;
    private ImageButton bullHornLicStOff;
    private ImageButton bullHornFnameOff;
    private ImageButton bullHornCompOff;
    private ImageButton bullHornAddr1On;
    private ImageButton bullHornAddr2On;
    private ImageButton bullHornCityOn;
    private ImageButton bullHornStateOn;
    private ImageButton bullHornZipOn;
    private ImageButton bullHornEmailOn;
    private ImageButton bullHornPhon1On;
    private ImageButton bullHornPhon2On;
    private ImageButton bullHornPhon3On;
    private ImageButton bullHornLicOn;
    private ImageButton bullHornLicStOn;
    private ImageButton bullHornFnameOn;
    private ImageButton bullHornCompOn;
    private Resources res;

    private FloatingActionButton btnName;
    private FloatingActionButton btnPhone;
    private FloatingActionButton btnAddress;
    private RelativeLayout tabBar;
    private RelativeLayout rlEmail;
    private FloatingActionButton btnHelp;
    private FloatingActionButton btnGallery;
    private DeviceUserDao mDeviceUserDao;
    private FloatingActionButton btnSpeakOff;
    private FloatingActionButton btnSpeakOn;
    private int CurrentTab;
    private static final int NAME_TAB = 1;
    private static final int ADDRESS_TAB = 2;
    private static final int PHONE_TAB = 3;
    private Toolbar toolbar;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private RotateAnimation rotateAnimation;
    private VehicleManifestDao mVehicleManifestDao;
    private View view;
    private String DA_HORN;
    private  Context context;
    private String timeStamp;
    private AppBarLayout ab01, ab02;
    private int doStartSpeech;
    private int doStopSpeech;
    private String DA_ID_STR;
    private int DUX_ID;
    private int IP_AID;
    private int IP_CUID;
    private int IP_ID;
    private int IP_UUID;

    //  private RadioButton rbTrue;
//    private RadioButton rbFalse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_involved_party);
        //   inc01 =  findViewById(R.id.inc01);
        //    rbTrue = findViewById(R.id.rbTrue);
        //     rbFalse = findViewById(R.id.rbFalse);
        ab02 = findViewById(R.id.ab02);
        llcompany = findViewById(R.id.llcompany);
        btnName = findViewById(R.id.btnName);
        btnPhone = findViewById(R.id.btnPhone);
        btnAddress = findViewById(R.id.btnAddress);
        tabBar = findViewById(R.id.tabBar);
        rlEmail = findViewById(R.id.rlEmail);
        btnHelp = findViewById(R.id.btnHelp);
        bullHornAddr1Off = findViewById(R.id.bullHornAddr1Off);
        bullHornAddr2Off = findViewById(R.id.bullHornAddr2Off);
        bullHornCityOff = findViewById(R.id.bullHornCityOff);
        bullHornStateOff = findViewById(R.id.bullHornStateOff);
        bullHornZipOff = findViewById(R.id.bullHornZipOff);
        bullHornEmailOff = findViewById(R.id.bullHornEmailOff);
        bullHornPhon1Off = findViewById(R.id.bullHornPhon1Off);
        bullHornPhon2Off = findViewById(R.id.bullHornPhon2Off);
        bullHornPhon3Off = findViewById(R.id.bullHornPhon3Off);
        bullHornLicOff = findViewById(R.id.bullHornLicOff);
        bullHornLicStOff = findViewById(R.id.bullHornLicStOff);
        bullHornFnameOff = findViewById(R.id.bullHornFnameOff);
        bullHornCompOff = findViewById(R.id.bullHornCompOff);
        bullHornAddr1On = findViewById(R.id.bullHornAddr1On);
        bullHornAddr2On = findViewById(R.id.bullHornAddr2On);
        bullHornCityOn = findViewById(R.id.bullHornCityOn);
        bullHornStateOn = findViewById(R.id.bullHornStateOn);
        bullHornZipOn = findViewById(R.id.bullHornZipOn);
        bullHornEmailOn = findViewById(R.id.bullHornEmailOn);
        bullHornPhon1On = findViewById(R.id.bullHornPhon1On);
        bullHornPhon2On = findViewById(R.id.bullHornPhon2On);
        bullHornPhon3On = findViewById(R.id.bullHornPhon3On);
        bullHornLicOn = findViewById(R.id.bullHornLicOn);
        bullHornLicStOn = findViewById(R.id.bullHornLicStOn);
        bullHornFnameOn = findViewById(R.id.bullHornFnameOn);
        bullHornCompOn = findViewById(R.id.bullHornCompOn);
        ll00 = findViewById(R.id.involvement_type_ll00);
        ll01 = findViewById(R.id.add_device_user_ll01);
        ll02 = findViewById(R.id.add_device_user_ll02);
        ll03 = findViewById(R.id.add_device_user_ll03);
        ccp_license_country = findViewById(R.id.ccp_license_country);
        ccp_resident_country = findViewById(R.id.ccp_resident_country);
        ccp_phon1_country = findViewById(R.id.ccp_phon1_country);
        ccp_phon2_country = findViewById(R.id.ccp_phon2_country);
        ccp_phon3_country = findViewById(R.id.ccp_phon3_country);


        progressBar = findViewById(R.id.progressBar1);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        TextView tvXX_ID = findViewById(R.id.tvXX_ID);
        tvXX_AID = findViewById(R.id.tvXX_AID);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tieXX_FNAME = findViewById(R.id.tieXX_FNAME);
        tieXX_MI = findViewById(R.id.tieXX_MI);
        tieXX_LNAME = findViewById(R.id.tieXX_LNAME);
        tieXX_LICENSE = findViewById(R.id.tieXX_LICENSE);
        tieXX_LST = findViewById(R.id.tieXX_LST);
        tieXX_ADDR1 = findViewById(R.id.tieXX_ADDR1);
        tieXX_ADDR2 = findViewById(R.id.tieXX_ADDR2);
        tieXX_CITY = findViewById(R.id.tieXX_CITY);
        tieXX_ST = findViewById(R.id.tieXX_ST);
        tieXX_ZIP = findViewById(R.id.tieXX_ZIP);
        tieXX_EMAIL = findViewById(R.id.tieXX_EMAIL);
        tieXX_PHON1 = findViewById(R.id.tieXX_PHON1);
        tieXX_PHON2 = findViewById(R.id.tieXX_PHON2);
        tieXX_PHON3 = findViewById(R.id.tieXX_PHON3);
        spXX_PTYPE = findViewById(R.id.spXX_PTYPE);
        tieXX_COMP = findViewById(R.id.tieXX_COMP);
        tvXX_CUID = findViewById(R.id.tvXX_CUID);
        tvXX_CDATE = findViewById(R.id.tvXX_CDATE);
        tvXX_UUID = findViewById(R.id.tvXX_UUID);
        tvXX_UDATE = findViewById(R.id.tvXX_UDATE);

        btnAdd = findViewById(R.id.btnAdd);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        mDeviceUserDao = new DeviceUserDao(this);
        mInvolvedPartyDao = new InvolvedPartyDao(this);

        mVehicleManifestDao = new VehicleManifestDao(this);

        toolbar = findViewById(R.id.my_toolbar);
        CurrentTab = NAME_TAB;
        setSupportActionBar(toolbar);
        res = getResources();
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "ADD_INVOVLVED_PARTY");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_PARTY");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "ADD_INVOVLVED_PARTY");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        doStopSpeech = 0;
        doStartSpeech = 0;
        tvXX_AID.setText(DA_ID);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID)) {
            DUX_ID = Integer.parseInt(DA_ID);
        } else {
            DUX_ID = 0;
        }

        tvXX_CUID.setText(DA_ID);
        tvXX_UUID.setText(DA_ID);

        DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.aip));
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_OFFLINE");
        preferOfflineLanguages = persistenceObj.getPERSISTENCE_VALUE();
        if (preferOfflineLanguages.equals("FALSE")) {
            preferOffline = false;

        } else {
            preferOffline = true;

        }
        ActionBar actionBar = getSupportActionBar();
        timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);


        });

        btnName.setOnClickListener(view -> {
            CurrentTab = NAME_TAB;
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnName.startAnimation(rotateAnimation);
            scheduleAllowAnimation();

        });
        btnAddress.setOnClickListener(view -> {
            CurrentTab = ADDRESS_TAB;
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnAddress.startAnimation(rotateAnimation);
            scheduleAllowAnimation();

        });
        btnPhone.setOnClickListener(view -> {
            CurrentTab = PHONE_TAB;
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnPhone.startAnimation(rotateAnimation);
            scheduleAllowAnimation();

        });

        btnHelp.setOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnHelp.startAnimation(rotateAnimation);
            scheduleDoHelp();

        });


        bullHornFnameOff.setOnClickListener(view -> {
            turnOffLastButton(view);
            context = view.getContext();
            DA_HORN = "bullHornFnameOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornFnameOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();

            //    startFirstNameInput();
        });

        bullHornFnameOn.setOnClickListener(view -> {
            context = view.getContext();
            DA_HORN = "bullHornFnameOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornFnameOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornLicOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornLicOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornLicOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
            //    startFirstNameInput();
        });

        bullHornLicOn.setOnClickListener(view -> {
            context = view.getContext();
            DA_HORN = "bullHornLicOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornLicOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornLicStOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornLicStOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornLicStOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornLicStOn.setOnClickListener(view -> {
            DA_HORN = "bullHornLicStOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornLicStOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });
        bullHornAddr1Off.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornAddr1Off";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornAddr1Off.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornAddr1On.setOnClickListener(view -> {
            DA_HORN = "bullHornAddr1On";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornAddr1On.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });
        bullHornAddr2Off.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornAddr2Off";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornAddr2Off.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornAddr2On.setOnClickListener(view -> {
            DA_HORN = "bullHornAddr2On";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornAddr2On.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornCityOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornCityOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornCityOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornCityOn.setOnClickListener(view -> {
            DA_HORN = "bullHornCityOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornCityOn.startAnimation(rotateAnimation);
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
            context = view.getContext();
            DA_HORN = "bullHornStateOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornStateOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornZipOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornZipOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornZipOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();

        });

        bullHornZipOn.setOnClickListener(view -> {
            DA_HORN = "bullHornZipOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornZipOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornEmailOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornEmailOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornEmailOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornEmailOn.setOnClickListener(view -> {
            DA_HORN = "bullHornEmailOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornEmailOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornPhon1Off.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornPhon1Off";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPhon1Off.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornPhon1On.setOnClickListener(view -> {
            DA_HORN = "bullHornPhon1On";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPhon1On.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornPhon2Off.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornPhon2Off";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPhon2Off.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornPhon2On.setOnClickListener(view -> {
            DA_HORN = "bullHornPhon2On";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPhon2On.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });
        bullHornPhon3Off.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornPhon3Off";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPhon3Off.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornPhon3On.setOnClickListener(view -> {
            DA_HORN = "bullHornPhon3On";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornPhon3On.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornCompOff.setOnClickListener(view -> {
            context = view.getContext();
            turnOffLastButton(view);
            DA_HORN = "bullHornCompOff";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornCompOff.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
        });

        bullHornCompOn.setOnClickListener(view -> {
            DA_HORN = "bullHornCompOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            bullHornCompOn.startAnimation(rotateAnimation);
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
            context = view.getContext();
            btnSpeakOff.setImageAlpha(alpha2);
            message = res.getString(R.string.mic_and_meg);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });

        btnAdd.setOnClickListener(view -> {
            if (fireClick == true) {
                DA_HORN = "btnAdd";
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnAdd.startAnimation(rotateAnimation);
                scheduleDoBullhorn();
            }
            fireClick = true;
            btnAdd.setImageAlpha(alpha1);

        });
        btnAdd.setOnLongClickListener(view -> {
            btnAdd.setImageAlpha(alpha2);
            context = view.getContext();

            message = res.getString(R.string.tv0145);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });


        spXX_PTYPE.setOnItemSelectedListener(this);
        loadSpinnerData();
    }

    private void AddData(Integer IP_AID, String IP_FNAME, String IP_MI, String IP_LNAME, String IP_LICENSE, String IP_LST, String IP_ADDR1,
                         String IP_ADDR2, String IP_CITY, String IP_ST, String IP_ZIP, String IP_EMAIL,
                         String IP_PHON1, String IP_PHON2, String IP_PHON3, String IP_PTYPE,
                         String IP_CNAM01, String IP_PNUM01, String IP_CNAM02, String IP_PNUM02,
                         String IP_CNAM03, String IP_PNUM03, String IP_COMP, String IP_LICENSE_COUNTRY,
                         String IP_RESIDENT_COUNTRY, String IP_PHON1_COUNTRY, String IP_PHON2_COUNTRY,
                         String IP_PHON3_COUNTRY) {
        insertData = mInvolvedPartyDao.addData(IP_AID, IP_FNAME, IP_MI, IP_LNAME, IP_LICENSE, IP_LST,
                IP_ADDR1, IP_ADDR2, IP_CITY, IP_ST, IP_ZIP, IP_EMAIL, IP_PHON1, IP_PHON2,
                IP_PHON3, IP_PTYPE, IP_CNAM01, IP_PNUM01, IP_CNAM02, IP_PNUM02, IP_CNAM03,
                IP_PNUM03, IP_COMP, IP_LICENSE_COUNTRY, IP_RESIDENT_COUNTRY, IP_PHON1_COUNTRY,
                IP_PHON2_COUNTRY, IP_PHON3_COUNTRY);


    }

    private void AddManifestData(Integer VMAID_ID, Integer VMIP_ID) {

        insertData = mVehicleManifestDao.addData(VMAID_ID, 0, VMIP_ID);
        // Add Test Comment
        // Add Second Comment

    }

    private void loadSpinnerData() {
        // database handler - Your Data
        PartyTypeDao db = new PartyTypeDao(getApplicationContext());

        // Spinner Drop down elements
        List<String> types = db.getAllTypes();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, types);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spXX_PTYPE.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner carousel_item
        if (position > 0) {
            llbtnSpeakOff.setVisibility(View.VISIBLE);
            tabBar.setVisibility(View.VISIBLE);
            ll01.setVisibility(View.VISIBLE);
            btnAdd.show();
        } else {
            llbtnSpeakOff.setVisibility(View.GONE);
            tabBar.setVisibility(View.GONE);
            ll01.setVisibility(View.GONE);
            btnAdd.hide();
        }
        String type = parent.getItemAtPosition(position).toString();
        IP_PTYPE = parent.getItemAtPosition(position).toString();
        // Showing selected spinner carousel_item
        if (IP_PTYPE.equals("null")) {
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
        //  intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        tvDA_PROMPT.setText(getString(R.string.tv0200) + getString(R.string.tv0227));
        try {
            REQ_CODE = REQ_CODE_SPEECH_INPUT;
            speech.startListening(intent);
            //   startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void resetBeforeExit() {
        tvDA_PROMPT.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        llbtnSpeakOff.setVisibility(View.VISIBLE);
        llbtnSpeakOn.setVisibility(GONE);
        super.onStop();
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }


     }

    private void startFirstNameInput() {

        tieXX_FNAME.requestFocus();
        int DA_SIZE = tieXX_FNAME.length();
        tieXX_FNAME.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);


        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0201) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_FIRST_NAME;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_FIRST_NAME);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startLicenseInput() {
        tieXX_LICENSE.requestFocus();
        int DA_SIZE = tieXX_LICENSE.length();
        tieXX_LICENSE.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);

        tvDA_PROMPT.setText(getString(R.string.tv0204) + getString(R.string.tv0218));
        //     inc01.setVisibility(View.VISIBLE);
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Drivers License Number, QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_LICENSE;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_LICENSE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startLicenseStateInput() {
        tieXX_LST.requestFocus();
        int DA_SIZE = tieXX_LST.length();
        tieXX_LST.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        //  intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000000);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0208) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a State Name.");
        try {
            REQ_CODE = REQ_CODE_LICENSE_STATE;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_LICENSE_STATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startAddressInput() {
        tieXX_ADDR1.requestFocus();
        int DA_SIZE = tieXX_ADDR1.length();
        tieXX_ADDR1.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);

        //  intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0205) + getString(R.string.tv0218));

        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Address, QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_ADDRESS;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_ADDRESS);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startAddress2Input() {
        tieXX_ADDR2.requestFocus();
        int DA_SIZE = tieXX_ADDR2.length();
        tieXX_ADDR2.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);

        //  intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0206) + getString(R.string.tv0218));
        //     intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Im ready for entity_address line 2 or next to skip.");
        try {
            REQ_CODE = REQ_CODE_ADDRESS2;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_ADDRESS2);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startCityInput() {
        tieXX_CITY.requestFocus();
        int DA_SIZE = tieXX_CITY.length();

        tieXX_CITY.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);

        //  intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0207) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for City Name.");
        try {
            REQ_CODE = REQ_CODE_CITY;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_CITY);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startStateInput() {
        tieXX_ST.requestFocus();
        int DA_SIZE = tieXX_ST.length();
        tieXX_ST.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0208) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a State Name.");
        try {
            REQ_CODE = REQ_CODE_STATE;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_STATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startZipInput() {
        tieXX_ZIP.requestFocus();
        int DA_SIZE = tieXX_ZIP.length();

        tieXX_ZIP.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        //  intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0209) + getString(R.string.tv0218));
        //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Zip Code.");
        try {
            REQ_CODE = REQ_CODE_ZIP;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_ZIP);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startEmailInput() {
        tieXX_EMAIL.requestFocus();
        int DA_SIZE = tieXX_EMAIL.length();
        tieXX_EMAIL.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0210) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for an Email Address.");
        try {
            REQ_CODE = REQ_CODE_EMAIL;
            speech.startListening(intent);
            //   startActivityForResult(intent, REQ_CODE_EMAIL);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startHphInput() {
        tieXX_PHON1.requestFocus();
        int DA_SIZE = tieXX_PHON1.length();
        tieXX_PHON1.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0211) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Home Phone Number.");
        try {
            REQ_CODE = REQ_CODE_HPH;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_HPH);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startCphInput() {
        tieXX_PHON2.requestFocus();
        int DA_SIZE = tieXX_PHON2.length();
        tieXX_PHON2.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0212) + getString(R.string.tv0218));
        //      intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Cell Phone Number.");
        try {
            REQ_CODE = REQ_CODE_CPH;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_CPH);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startWphInput() {
        tieXX_PHON3.requestFocus();
        int DA_SIZE = tieXX_PHON3.length();
        tieXX_PHON3.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0213) + getString(R.string.tv0218));
        //     intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Work Phone Number.");
        try {
            REQ_CODE = REQ_CODE_WPH;
            speech.startListening(intent);
            //     startActivityForResult(intent, REQ_CODE_WPH);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startInvolvementInput() {
        //  tieXX_ZIP.requestFocus();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0214) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok what is you role  Owner, Driver, Passenger, Investigator etc?.");
        try {
            REQ_CODE = REQ_CODE_INVOLVMENT;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_INVOLVMENT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startCompanyInput() {
        tieXX_COMP.requestFocus();
        int DA_SIZE = tieXX_COMP.length();
        tieXX_COMP.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0217) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for your company name if applicable or Quit to exit.");

        try {
            REQ_CODE = REQ_CODE_COMPANY;
            speech.startListening(intent);
            //     startActivityForResult(intent, REQ_CODE_COMPANY);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startFirstNameInputOnly() {
        utils.hideKeyboard(this);
        tieXX_FNAME.requestFocus();
        int DA_SIZE = tieXX_FNAME.length();
        tieXX_FNAME.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0201) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_FIRST_NAME_ONLY;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_FIRST_NAME);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startLicenseInputOnly() {
        utils.hideKeyboard(this);
        tieXX_LICENSE.requestFocus();
        int DA_SIZE = tieXX_LICENSE.length();
        tieXX_LICENSE.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);


        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0204) + getString(R.string.tv0218));
        //     inc01.setVisibility(View.VISIBLE);
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Drivers License Number, QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_LICENSE_ONLY;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_LICENSE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startLicenseStateInputOnly() {
        utils.hideKeyboard(this);
        tieXX_LST.requestFocus();
        int DA_SIZE = tieXX_LST.length();
        tieXX_LST.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0208) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a State Name.");
        try {
            REQ_CODE = REQ_CODE_LICENSE_STATE_ONLY;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_LICENSE_STATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startAddressInputOnly() {
        utils.hideKeyboard(this);
        tieXX_ADDR1.requestFocus();
        int DA_SIZE = tieXX_ADDR1.length();
        tieXX_ADDR1.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0205) + getString(R.string.tv0218));

        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Address, QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_ADDRESS_ONLY;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_ADDRESS);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startAddress2InputOnly() {
        utils.hideKeyboard(this);
        tieXX_ADDR2.requestFocus();
        int DA_SIZE = tieXX_ADDR2.length();
        tieXX_ADDR2.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0206) + getString(R.string.tv0218));
        //     intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Im ready for entity_address line 2 or next to skip.");
        try {
            REQ_CODE = REQ_CODE_ADDRESS2_ONLY;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_ADDRESS2);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startCityInputOnly() {
        utils.hideKeyboard(this);
        tieXX_CITY.requestFocus();
        int DA_SIZE = tieXX_CITY.length();

        tieXX_CITY.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0207) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for City Name.");
        try {
            REQ_CODE = REQ_CODE_CITY_ONLY;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_CITY);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startStateInputOnly() {
        utils.hideKeyboard(this);
        tieXX_ST.requestFocus();
        int DA_SIZE = tieXX_ST.length();
        tieXX_ST.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0208) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a State Name.");
        try {
            REQ_CODE = REQ_CODE_STATE_ONLY;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_STATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startZipInputOnly() {
        utils.hideKeyboard(this);
        tieXX_ZIP.requestFocus();
        int DA_SIZE = tieXX_ZIP.length();

        tieXX_ZIP.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0209) + getString(R.string.tv0218));
        //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Zip Code.");
        try {
            REQ_CODE = REQ_CODE_ZIP_ONLY;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_ZIP);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startEmailInputOnly() {
        utils.hideKeyboard(this);
        tieXX_EMAIL.requestFocus();
        int DA_SIZE = tieXX_EMAIL.length();
        tieXX_EMAIL.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0210) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for an Email Address.");
        try {
            REQ_CODE = REQ_CODE_EMAIL_ONLY;
            speech.startListening(intent);
            //   startActivityForResult(intent, REQ_CODE_EMAIL);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startHphInputOnly() {
        utils.hideKeyboard(this);
        tieXX_PHON1.requestFocus();
        int DA_SIZE = tieXX_PHON1.length();
        tieXX_PHON1.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0211) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Home Phone Number.");
        try {
            REQ_CODE = REQ_CODE_HPH_ONLY;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_HPH);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startCphInputOnly() {
        utils.hideKeyboard(this);
        tieXX_PHON2.requestFocus();
        int DA_SIZE = tieXX_PHON2.length();
        tieXX_PHON2.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0212) + getString(R.string.tv0218));
        //      intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Cell Phone Number.");
        try {
            REQ_CODE = REQ_CODE_CPH_ONLY;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_CPH);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startWphInputOnly() {
        utils.hideKeyboard(this);
        tieXX_PHON3.requestFocus();
        int DA_SIZE = tieXX_PHON3.length();
        tieXX_PHON3.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0213) + getString(R.string.tv0218));
        //     intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a Work Phone Number.");
        try {
            REQ_CODE = REQ_CODE_WPH_ONLY;
            speech.startListening(intent);
            //     startActivityForResult(intent, REQ_CODE_WPH);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startInvolvementInputOnly() {
        //  tieXX_ZIP.requestFocus();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0214) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok what is you role  Owner, Driver, Passenger, Investigator etc?.");
        try {
            REQ_CODE = REQ_CODE_INVOLVMENT;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_INVOLVMENT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startCompanyInputOnly() {
        tieXX_COMP.requestFocus();
        int DA_SIZE = tieXX_COMP.length();
        tieXX_COMP.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        tvDA_PROMPT.setText(getString(R.string.tv0217) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for your company name if applicable or Quit to exit.");

        try {
            REQ_CODE = REQ_CODE_COMPANY_ONLY;
            speech.startListening(intent);
            //     startActivityForResult(intent, REQ_CODE_COMPANY);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void retry() {
        switch (REQ_CODE) {
            case REQ_CODE_FIRST_NAME: {
                startFirstNameInput();
                break;
            }

            case REQ_CODE_LICENSE: {
                startLicenseInput();
                break;
            }
            case REQ_CODE_LICENSE_STATE: {
                startLicenseStateInput();
                break;
            }
            case REQ_CODE_ADDRESS: {
                startAddressInput();
                break;
            }
            case REQ_CODE_ADDRESS2: {
                startAddress2Input();
                break;
            }
            case REQ_CODE_CITY: {
                startCityInput();
                break;
            }
            case REQ_CODE_STATE: {
                startStateInput();
                break;
            }

            case REQ_CODE_ZIP: {
                startZipInput();
                break;
            }
            case REQ_CODE_EMAIL: {
                startEmailInput();
                break;
            }
            case REQ_CODE_HPH: {
                startHphInput();
                break;
            }
            case REQ_CODE_CPH: {
                startCphInput();
                break;
            }
            case REQ_CODE_WPH: {
                startWphInput();
                break;
            }

            case REQ_CODE_COMPANY: {
                startCompanyInput();
                break;
            }
            case REQ_CODE_FIRST_NAME_ONLY: {
                startFirstNameInputOnly();
                break;
            }

            case REQ_CODE_LICENSE_ONLY: {
                startLicenseInputOnly();
                break;
            }
            case REQ_CODE_LICENSE_STATE_ONLY: {
                startLicenseStateInputOnly();
                break;
            }
            case REQ_CODE_ADDRESS_ONLY: {
                startAddressInputOnly();
                break;
            }
            case REQ_CODE_ADDRESS2_ONLY: {
                startAddress2InputOnly();
                break;
            }
            case REQ_CODE_CITY_ONLY: {
                startCityInputOnly();
                break;
            }
            case REQ_CODE_STATE_ONLY: {
                startStateInputOnly();
                break;
            }

            case REQ_CODE_ZIP_ONLY: {
                startZipInputOnly();
                break;
            }
            case REQ_CODE_EMAIL_ONLY: {
                startEmailInputOnly();
                break;
            }
            case REQ_CODE_HPH_ONLY: {
                startHphInputOnly();
                break;
            }
            case REQ_CODE_CPH_ONLY: {
                startCphInputOnly();
                break;
            }
            case REQ_CODE_WPH_ONLY: {
                startWphInputOnly();
                break;
            }

            case REQ_CODE_COMPANY_ONLY: {
                startCompanyInputOnly();
                break;
            }
        }
    }

    private String undoWord(String DA_RESULT2) {
        String[] splitString = DA_RESULT2.split(" ");
        int splitLength = splitString.length;
        int index = 0;
        StringBuilder DA_RESULT = new StringBuilder();

        String[] words = DA_RESULT2.split("\\s+");
        while (index < splitString.length - 1) {

            DA_RESULT.append(" ").append(words[index]);
            index++;
        }
        return DA_RESULT.toString();
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

        DA_RESULT = result.get(0);
        String DA_RESULTCAP = DA_RESULT.toUpperCase();
        if (DA_RESULTCAP.equals(getString(R.string.tv0220)) || DA_RESULTCAP.equals(getString(R.string.tv0222)) || DA_RESULTCAP.equals(getString(R.string.tv0224)) || DA_RESULTCAP.equals(getString(R.string.tv0226))) {
            DA_RESULT = DA_RESULT.toUpperCase();
        }

        switch (REQ_CODE) {
            case REQ_CODE_SPEECH_INPUT: {
                if (null != DA_RESULT) {
                    DA_RESULT = DA_RESULT.toUpperCase();
                    if (DA_RESULT.equals(getString(R.string.tv0230))) {
                        startFirstNameInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0231))) {
                        //  startMiddleInitialInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0232))) {
                        // startLastNameInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0233))) {
                        startLicenseInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0234))) {
                        startLicenseStateInput();
                        break;
                    }
                    if (deviceLocalel.equals("en")) {
                        if (DA_RESULT.equals(getString(R.string.tv0247))) {
                            startLicenseStateInput();
                            break;
                        }
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0235))) {
                        startAddressInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0236))) {
                        startAddress2Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0237))) {
                        startCityInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0238))) {
                        startStateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.accident_zip))) {
                        startZipInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0240))) {
                        startEmailInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0241))) {
                        startHphInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0242))) {
                        startCphInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0243))) {
                        startWphInput();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0246))) {
                        startCompanyInput();
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

            case REQ_CODE_FIRST_NAME: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        DA_RESULT = utils.capEachWord(DA_RESULT);
                        tieXX_FNAME.setText(DA_RESULT);

                        startLicenseInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        //  startMiddleInitialInput();
                        startLicenseInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_FNAME.setText(DA_RESULT);
                        startFirstNameInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startFirstNameInput();
                        break;
                    }
                    startFirstNameInput();
                }
                break;
            }

            // undoCharacter
            case REQ_CODE_LICENSE: {
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
                        getSelectedItemLicense();

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
                    tieXX_LICENSE.setText(DA_RESULT);
                    startLicenseInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0226))) {
                    //  startLastNameInput();
                    startFirstNameInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0222))) {
                    startLicenseStateInput();
                    break;
                }

                startLicenseInput();
                break;
            }


            case REQ_CODE_LICENSE_STATE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        //   String St = mStateDao.getState(DA_RESULT);

                        //    if (St != null) {
                        tieXX_LST.setText(DA_RESULT);
                        startCompanyInput();
                        break;
                        //  }
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }

                }
                if (DA_RESULT.equals(getString(R.string.tv0226))) {
                    startLicenseInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0222))) {
                    startCompanyInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0224))) {
                    DA_RESULT = "";
                    tieXX_LST.setText(DA_RESULT);
                    startLicenseStateInput();
                    break;
                }
                startLicenseStateInput();
                break;
            }
            case REQ_CODE_COMPANY: {

                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        DA_RESULT = utils.capEachWord(DA_RESULT);
                        tieXX_COMP.setText(DA_RESULT);
                        btnAddress.callOnClick();
                        startAddressInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        btnAddress.callOnClick();
                        startAddressInput();

                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        //  startPolicyNumberInput();

                        startLicenseStateInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_COMP.setText(DA_RESULT);
                        startCompanyInput();
                    }
                    startCompanyInput();
                    break;
                }
            }
            case REQ_CODE_ADDRESS: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_ADDR1.setText(DA_RESULT);
                        startAddress2Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        btnName.callOnClick();
                        startCompanyInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        if (AutoAdd.equals(true)) {
                            startAddress2Input();
                            break;
                        } else {
                            startVoiceInput();
                            break;
                        }
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_ADDR1.setText(DA_RESULT);
                        startAddressInput();
                        break;
                    }


                    startAddressInput();
                }
                break;
            }
            case REQ_CODE_ADDRESS2: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_ADDR2.setText(DA_RESULT);
                        startCityInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startAddressInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startCityInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_ADDR2.setText(DA_RESULT);
                        startAddress2Input();
                        break;
                    }
                    startAddress2Input();
                }
                break;
            }
            case REQ_CODE_CITY: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_CITY.setText(DA_RESULT);
                        startStateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startAddress2Input();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startStateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_CITY.setText(DA_RESULT);
                        startCityInput();
                        break;
                    }
                    startCityInput();
                }
                break;
            }
            case REQ_CODE_STATE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        //   String St = mStateDao.getState(DA_RESULT);
                        //  if (St != null) {
                        tieXX_ST.setText(DA_RESULT);
                        startZipInput();
                        break;
                        // }
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }

                }
                if (DA_RESULT.equals(getString(R.string.tv0226))) {
                    startCityInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0222))) {
                    startZipInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0224))) {
                    DA_RESULT = "";
                    tieXX_ST.setText(DA_RESULT);
                    startStateInput();
                    break;
                }
                startStateInput();
            }
            break;
            case REQ_CODE_ZIP: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_ZIP.setText(DA_RESULT);
                        btnPhone.callOnClick();
                        startEmailInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startStateInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        btnPhone.callOnClick();
                        startEmailInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_ZIP.setText(DA_RESULT);
                        startZipInput();
                        break;
                    }
                    startZipInput();
                }
                break;
            }
            case REQ_CODE_EMAIL: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        if (DA_RESULT != null) {
                            DA_RESULT = DA_RESULT.toLowerCase();
                            DA_RESULT = DA_RESULT.replaceAll("\\s", "");
                            DA_RESULT = DA_RESULT.replaceAll("atsign", "@");
                            tieXX_EMAIL.setText(DA_RESULT);
                            startHphInput();
                            break;
                        }
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        btnAddress.callOnClick();
                        startZipInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startHphInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_EMAIL.setText(DA_RESULT);
                        startEmailInput();
                        break;
                    }
                    startEmailInput();
                }
                break;
            }
            case REQ_CODE_HPH: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_PHON1.setText(DA_RESULT);
                        startCphInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startEmailInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startCphInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_PHON1.setText(DA_RESULT);
                        startHphInput();
                        break;
                    }
                    startHphInput();
                }
                break;
            }
            case REQ_CODE_CPH: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_PHON2.setText(DA_RESULT);
                        startWphInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();

                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startHphInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startWphInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_PHON2.setText(DA_RESULT);
                        startCphInput();
                        break;
                    }
                    startCphInput();
                }
                break;
            }
            case REQ_CODE_WPH: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_PHON3.setText(DA_RESULT);
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startCphInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        resetBeforeExit();
                        resetButtons();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_PHON3.setText(DA_RESULT);
                        startWphInput();
                        break;
                    }
                    startWphInput();
                }
                break;
            }


            case REQ_CODE_FIRST_NAME_ONLY: {
                if (null != DA_RESULT) {
                    DA_RESULT = utils.capEachWord(DA_RESULT);
                    tieXX_FNAME.setText(DA_RESULT);
                    resetBeforeExit();

                    bullHornFnameOn.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornFnameOn.callOnClick();
                break;
            }
            case REQ_CODE_LICENSE_ONLY: {
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
                    getSelectedItemLicenseOnly();

                    break;
                }
                bullHornLicOn.callOnClick();
                resetBeforeExit();
                break;
            }
            case REQ_CODE_LICENSE_STATE_ONLY: {
                if (null != DA_RESULT) {
                    tieXX_LST.setText(DA_RESULT);

                    resetBeforeExit();
                    bullHornLicStOn.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornLicStOn.callOnClick();
                break;
            }

            case REQ_CODE_ADDRESS_ONLY: {
                if (null != DA_RESULT) {

                    tieXX_ADDR1.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornAddr1On.callOnClick();

                    break;

                }
                resetBeforeExit();
                bullHornAddr1On.callOnClick();
                break;
            }
            case REQ_CODE_ADDRESS2_ONLY: {
                if (null != DA_RESULT) {

                    tieXX_ADDR2.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornAddr2On.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornAddr2On.callOnClick();
                break;
            }
            case REQ_CODE_CITY_ONLY: {
                if (null != DA_RESULT) {
                    tieXX_CITY.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornCityOn.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornCityOn.callOnClick();
                break;
            }
            case REQ_CODE_STATE_ONLY: {
                if (null != DA_RESULT) {
                    tieXX_ST.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornStateOn.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornStateOn.callOnClick();
                break;
            }
            case REQ_CODE_ZIP_ONLY: {
                if (null != DA_RESULT) {
                    tieXX_ZIP.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornZipOn.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornZipOn.callOnClick();
                break;
            }
            case REQ_CODE_EMAIL_ONLY: {
                if (null != DA_RESULT) {


                    DA_RESULT = DA_RESULT.toLowerCase();
                    DA_RESULT = DA_RESULT.replaceAll("\\s", "");
                    DA_RESULT = DA_RESULT.replaceAll("atsign", "@");
                    tieXX_EMAIL.setText(DA_RESULT);
                    resetBeforeExit();

                    bullHornEmailOn.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornEmailOn.callOnClick();
                break;
            }
            case REQ_CODE_HPH_ONLY: {
                if (null != DA_RESULT) {

                    tieXX_PHON1.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornPhon1On.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornPhon1On.callOnClick();
                break;
            }
            case REQ_CODE_CPH_ONLY: {
                if (null != DA_RESULT) {

                    tieXX_PHON2.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornPhon2On.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornPhon2On.callOnClick();
                break;
            }
            case REQ_CODE_WPH_ONLY: {
                if (null != DA_RESULT) {

                    tieXX_PHON3.setText(DA_RESULT);

                    resetBeforeExit();
                    bullHornPhon3On.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornPhon3On.callOnClick();
                break;
            }
            case REQ_CODE_COMPANY_ONLY: {
                if (null != DA_RESULT) {

                    DA_RESULT = utils.capEachWord(DA_RESULT);
                    tieXX_COMP.setText(DA_RESULT);
                    resetBeforeExit();
                    bullHornCompOn.callOnClick();
                    break;
                }
                resetBeforeExit();
                bullHornCompOn.callOnClick();
                break;
            }
        }
    }


    public void getSelectedItemLicenseOnly() {
        int itemSelected = 0;

        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.select_drivers_license_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resultx.get(selectedIndex);
                                tieXX_LICENSE.setText(DA_RESULT);
                                resetBeforeExit();
                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        startLicenseInput();
                        dialogInterface.dismiss();
                    }
                })

                .show();
    }

    public void getSelectedItemLicense() {
        int itemSelected = 0;

        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.select_drivers_license_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resultx.get(selectedIndex);
                                tieXX_LICENSE.setText(DA_RESULT);
                                startLicenseStateInput();
                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        startLicenseInput();
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
           resetBeforeExit();
            resetButtons();
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

    private static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_PT_TYPE", "");
    }
    public void resetButtons() {
        bullHornAddr1On.setVisibility(GONE);
        bullHornAddr2On.setVisibility(GONE);
        bullHornCityOn.setVisibility(GONE);
        bullHornStateOn.setVisibility(GONE);
        bullHornZipOn.setVisibility(GONE);
        bullHornEmailOn.setVisibility(GONE);
        bullHornPhon1On.setVisibility(GONE);
        bullHornPhon2On.setVisibility(GONE);
        bullHornPhon3On.setVisibility(GONE);
        bullHornLicOn.setVisibility(GONE);
        bullHornLicStOn.setVisibility(GONE);
        bullHornFnameOn.setVisibility(GONE);
        bullHornCompOn.setVisibility(GONE);
        btnSpeakOn.hide();
        llbtnSpeakOn.setVisibility(GONE);
        tvDA_PROMPT.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
        bullHornAddr1Off.setVisibility(VISIBLE);
        bullHornAddr2Off.setVisibility(VISIBLE);
        bullHornCityOff.setVisibility(VISIBLE);
        bullHornStateOff.setVisibility(VISIBLE);
        bullHornZipOff.setVisibility(VISIBLE);
        bullHornEmailOff.setVisibility(VISIBLE);
        bullHornPhon1Off.setVisibility(VISIBLE);
        bullHornPhon2Off.setVisibility(VISIBLE);
        bullHornPhon3Off.setVisibility(VISIBLE);
        bullHornLicOff.setVisibility(VISIBLE);
        bullHornLicStOff.setVisibility(VISIBLE);
        bullHornFnameOff.setVisibility(VISIBLE);
        bullHornCompOff.setVisibility(VISIBLE);
        btnName.show();
        btnPhone.show();
        btnAddress.show();
        btnHelp.show();
        btnSpeakOff.show();
        btnAdd.show();
        llbtnSpeakOff.setVisibility(VISIBLE);

    }

    public void hideButtons() {
        bullHornAddr1Off.setVisibility(GONE);
        bullHornAddr2Off.setVisibility(GONE);
        bullHornCityOff.setVisibility(GONE);
        bullHornStateOff.setVisibility(GONE);
        bullHornZipOff.setVisibility(GONE);
        bullHornEmailOff.setVisibility(GONE);
        bullHornPhon1Off.setVisibility(GONE);
        bullHornPhon2Off.setVisibility(GONE);
        bullHornPhon3Off.setVisibility(GONE);
        bullHornLicOff.setVisibility(GONE);
        bullHornLicStOff.setVisibility(GONE);
        bullHornFnameOff.setVisibility(GONE);
        bullHornCompOff.setVisibility(GONE);
        btnName.hide();
        btnPhone.hide();
        btnAddress.hide();
        btnHelp.hide();
        btnSpeakOff.hide();
        btnAdd.hide();

    }

    public void turnOffLastButton(View view) {


        if (bullHornFnameOn.getVisibility() == VISIBLE) {
            bullHornFnameOn.callOnClick();
        }
        if (bullHornLicOn.getVisibility() == VISIBLE) {
            bullHornLicOn.callOnClick();
        }
        if (bullHornLicStOn.getVisibility() == VISIBLE) {
            bullHornLicStOn.callOnClick();
        }
        if (bullHornAddr1On.getVisibility() == VISIBLE) {
            bullHornAddr1On.callOnClick();
        }
        if (bullHornAddr2On.getVisibility() == VISIBLE) {
            bullHornAddr2On.callOnClick();
        }
        if (bullHornCityOn.getVisibility() == VISIBLE) {
            bullHornCityOn.callOnClick();
        }
        if (bullHornStateOn.getVisibility() == VISIBLE) {
            bullHornStateOn.callOnClick();
        }
        if (bullHornZipOn.getVisibility() == VISIBLE) {
            bullHornZipOn.callOnClick();
        }
        if (bullHornEmailOn.getVisibility() == VISIBLE) {
            bullHornEmailOn.callOnClick();
        }
        if (bullHornPhon1On.getVisibility() == VISIBLE) {
            bullHornPhon1On.callOnClick();
        }
        if (bullHornPhon2On.getVisibility() == VISIBLE) {
            bullHornPhon2On.callOnClick();
        }
        if (bullHornPhon3On.getVisibility() == VISIBLE) {
            bullHornPhon3On.callOnClick();
        }
        if (bullHornCompOn.getVisibility() == VISIBLE) {
            bullHornCompOn.callOnClick();
        }
        if (llbtnSpeakOn.getVisibility() == VISIBLE) {
            btnSpeakOn.callOnClick();
        }
    }
    public void disableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);
        bullHornAddr1Off.setEnabled(false);
        bullHornAddr2Off.setEnabled(false);
        bullHornCityOff.setEnabled(false);
        bullHornStateOff.setEnabled(false);
        bullHornZipOff.setEnabled(false);
        bullHornEmailOff.setEnabled(false);
        bullHornPhon1Off.setEnabled(false);
        bullHornPhon2Off.setEnabled(false);
        bullHornPhon3Off.setEnabled(false);
        bullHornLicOff.setEnabled(false);
        bullHornLicStOff.setEnabled(false);
        bullHornFnameOff.setEnabled(false);
        bullHornCompOff.setEnabled(false);
        bullHornAddr1On.setEnabled(false);
        bullHornAddr2On.setEnabled(false);
        bullHornCityOn.setEnabled(false);
        bullHornStateOn.setEnabled(false);
        bullHornZipOn.setEnabled(false);
        bullHornEmailOn.setEnabled(false);
        bullHornPhon1On.setEnabled(false);
        bullHornPhon2On.setEnabled(false);
        bullHornPhon3On.setEnabled(false);
        bullHornLicOn.setEnabled(false);
        bullHornLicStOn.setEnabled(false);
        bullHornFnameOn.setEnabled(false);
        bullHornCompOn.setEnabled(false);
        btnName.setEnabled(false);
        btnPhone.setEnabled(false);
        btnAddress.setEnabled(false);
        btnHelp.setEnabled(false);
        btnSpeakOff.setEnabled(false);
        btnSpeakOn2.setEnabled(false);
        btnAdd.setEnabled(false);



    }
    public void enableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);
        bullHornAddr1Off.setEnabled(true);
        bullHornAddr2Off.setEnabled(true);
        bullHornCityOff.setEnabled(true);
        bullHornStateOff.setEnabled(true);
        bullHornZipOff.setEnabled(true);
        bullHornEmailOff.setEnabled(true);
        bullHornPhon1Off.setEnabled(true);
        bullHornPhon2Off.setEnabled(true);
        bullHornPhon3Off.setEnabled(true);
        bullHornLicOff.setEnabled(true);
        bullHornLicStOff.setEnabled(true);
        bullHornFnameOff.setEnabled(true);
        bullHornCompOff.setEnabled(true);
        bullHornAddr1On.setEnabled(true);
        bullHornAddr2On.setEnabled(true);
        bullHornCityOn.setEnabled(true);
        bullHornStateOn.setEnabled(true);
        bullHornZipOn.setEnabled(true);
        bullHornEmailOn.setEnabled(true);
        bullHornPhon1On.setEnabled(true);
        bullHornPhon2On.setEnabled(true);
        bullHornPhon3On.setEnabled(true);
        bullHornLicOn.setEnabled(true);
        bullHornLicStOn.setEnabled(true);
        bullHornFnameOn.setEnabled(true);
        bullHornCompOn.setEnabled(true);
        btnName.setEnabled(true);
        btnPhone.setEnabled(true);
        btnAddress.setEnabled(true);
        btnHelp.setEnabled(true);
        btnSpeakOff.setEnabled(true);
        btnSpeakOn2.setEnabled(true);
        btnAdd.setEnabled(true);


    }
    public void showSequence0(View view) {
        KeyboardUtils.hideKeyboard(AddInvolvedParty.this);
        turnOffLastButton(view);
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()


                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(ll00)
                        .setPrimaryText(res.getString(R.string.involvement_type))
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
        KeyboardUtils.hideKeyboard(AddInvolvedParty.this);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        turnOffLastButton(view);
        disableButtons();
        //   llbtnSpeakOff.setVisibility(GONE);
        //  llbtnSpeakOn.setVisibility(VISIBLE);
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //    ccp_license_country, ccp_resident_country, ccp_phon1_country


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_and_meg))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnName)

                        .setPrimaryText(res.getString(R.string.name_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnAddress)

                        .setPrimaryText(res.getString(R.string.address_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnPhone)

                        .setPrimaryText(res.getString(R.string.contact_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(bullHornFnameOff)
                        .setPrimaryText(res.getString(R.string.speech_recognition_individual))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(llcompany)

                        .setPrimaryText(res.getString(R.string.company_help))
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
                                    KeyboardUtils.hideKeyboard(AddInvolvedParty.this);
                                }
                            }
                        })
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.press_to_add_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
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

    public void showSequence2(View view) {
        KeyboardUtils.hideKeyboard(AddInvolvedParty.this);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        turnOffLastButton(view);
        disableButtons();

        final Toolbar tb = this.findViewById(R.id.my_toolbar);


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_and_meg))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnName)

                        .setPrimaryText(res.getString(R.string.name_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnAddress)

                        .setPrimaryText(res.getString(R.string.address_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnPhone)

                        .setPrimaryText(res.getString(R.string.contact_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(bullHornAddr1Off)
                        .setPrimaryText(res.getString(R.string.speech_recognition_individual))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.press_to_add_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
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

    public void showSequence3(View view) {
        KeyboardUtils.hideKeyboard(AddInvolvedParty.this);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        turnOffLastButton(view);
        disableButtons();

        final Toolbar tb = this.findViewById(R.id.my_toolbar);


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_and_meg))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnName)

                        .setPrimaryText(res.getString(R.string.name_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnAddress)

                        .setPrimaryText(res.getString(R.string.address_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnPhone)

                        .setPrimaryText(res.getString(R.string.contact_tab))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )


                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(bullHornEmailOff)
                        .setPrimaryText(res.getString(R.string.speech_recognition_individual))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.press_to_add_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                         .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddInvolvedParty.this)
                        .setTarget(rlEmail)
                        .setPrimaryText(res.getString(R.string.speech_recognition_email))
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
    private void scheduleDoBullhorn() {
        Handler handler = new Handler();
        handler.postDelayed(this::doBullhorn, 250);
    }

    private void doBullhorn() {
        switch (DA_HORN) {
            case "btnAdd": {
                tvXX_CDATE.setText(timeStamp);
                tvXX_UDATE = tvXX_CDATE;
                DA_ID_STR = tvXX_AID.getText().toString();
                if (isNumber(DA_ID_STR)) {
                    IP_AID = Integer.parseInt(DA_ID_STR);
                } else {
                    IP_AID = 0;
                }
                String IP_FNAME = tieXX_FNAME.getText().toString();
                String IP_MI = tieXX_MI.getText().toString();
                String IP_LNAME = tieXX_LNAME.getText().toString();
                String IP_LICENSE = tieXX_LICENSE.getText().toString();
                String IP_LST = tieXX_LST.getText().toString();
                String IP_ADDR1 = tieXX_ADDR1.getText().toString();
                String IP_ADDR2 = tieXX_ADDR2.getText().toString();
                String IP_CITY = tieXX_CITY.getText().toString();
                String IP_ST = tieXX_ST.getText().toString();
                String IP_ZIP = tieXX_ZIP.getText().toString();
                String IP_EMAIL = tieXX_EMAIL.getText().toString();
                String IP_PHON1 = tieXX_PHON1.getText().toString();
                String IP_PHON2 = tieXX_PHON2.getText().toString();
                String IP_PHON3 = tieXX_PHON3.getText().toString();
                String IP_CNAM01 = "";
                String IP_PNUM01 = "";
                String IP_CNAM02 = "";
                String IP_PNUM02 = "";
                String IP_CNAM03 = "";
                String IP_PNUM03 = "";
                String IP_COMP = tieXX_COMP.getText().toString();
                DA_ID_STR = tvXX_CUID.getText().toString();
                if (isNumber(DA_ID_STR)) {
                    IP_CUID = Integer.parseInt(DA_ID_STR);
                } else {
                    IP_CUID = 0;
                }
                String IP_CDATE = tvXX_CDATE.getText().toString();
                DA_ID_STR = tvXX_UUID.getText().toString();
                if (isNumber(DA_ID_STR)) {
                    IP_UUID = Integer.parseInt(DA_ID_STR);
                } else {
                    IP_UUID = 0;
                }
                String IP_UDATE = tvXX_UDATE.getText().toString();
                String IP_LICENSE_COUNTRY = ccp_license_country.getSelectedCountryNameCode();
                String IP_RESIDENT_COUNTRY = ccp_resident_country.getSelectedCountryNameCode();
                String IP_PHON1_COUNTRY = ccp_phon1_country.getSelectedCountryNameCode() + " " + ccp_phon1_country.getSelectedCountryCodeWithPlus();
                String IP_PHON2_COUNTRY = ccp_phon2_country.getSelectedCountryNameCode() + " " + ccp_phon2_country.getSelectedCountryCodeWithPlus();
                String IP_PHON3_COUNTRY = ccp_phon3_country.getSelectedCountryNameCode() + " " + ccp_phon3_country.getSelectedCountryCodeWithPlus();
                AddData(IP_AID, IP_FNAME, IP_MI, IP_LNAME, IP_LICENSE, IP_LST,
                        IP_ADDR1, IP_ADDR2, IP_CITY, IP_ST, IP_ZIP, IP_EMAIL, IP_PHON1, IP_PHON2,
                        IP_PHON3, IP_PTYPE, IP_CNAM01, IP_PNUM01, IP_CNAM02, IP_PNUM02, IP_CNAM03,
                        IP_PNUM03, IP_COMP, IP_LICENSE_COUNTRY, IP_RESIDENT_COUNTRY,
                        IP_PHON1_COUNTRY, IP_PHON2_COUNTRY, IP_PHON3_COUNTRY);
                int IPID = (int) insertData;
                AddManifestData(IP_AID, IPID);
                doClose();
                Intent intent = new Intent(AddInvolvedParty.this, ListInvolvedParty.class);
                startActivity(intent);
                break;
            }
            case "btnSpeakOff": {
                hideButtons();
                tvDA_PROMPT.setVisibility(View.VISIBLE);
                progressBar.setVisibility(VISIBLE);

                llbtnSpeakOn.setVisibility(View.VISIBLE);
                btnSpeakOn.show();
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                if (AutoAdd.equals(true)) {
                    switch (CurrentTab) {
                        case NAME_TAB: {
                            startFirstNameInput();
                            break;
                        }
                        case ADDRESS_TAB: {
                            startAddressInput();
                            break;
                        }
                        case PHONE_TAB: {
                            startEmailInput();
                            break;
                        }
                        default: {
                            startFirstNameInput();
                            break;
                        }
                    }
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
            case "bullHornFnameOff": {
                hideButtons();

                progressBar.setVisibility(VISIBLE);
                bullHornFnameOn.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startFirstNameInputOnly();
                break;
            }

            case "bullHornFnameOn": {
                resetButtons();
                bullHornFnameOff.setVisibility(VISIBLE);
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }

                break;
            }
            case "bullHornLicOff": {
                hideButtons();
                bullHornLicOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startLicenseInputOnly();

                break;
            }
            case "bullHornAddr1Off": {

                hideButtons();
                bullHornAddr1On.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startAddressInputOnly();
                break;
            }
            case "bullHornAddr1On": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornAddr2Off": {

                hideButtons();
                bullHornAddr2On.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startAddress2InputOnly();
                break;
            }
            case "bullHornAddr2On": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornCityOff": {

                hideButtons();
                bullHornCityOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startCityInputOnly();
                break;
            }
            case "bullHornCityOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornStateOff": {

                hideButtons();
                bullHornStateOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startStateInputOnly();
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
            case "bullHornLicOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }


                break;
            }
            case "bullHornZipOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornZipOff": {

                hideButtons();
                bullHornZipOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startZipInputOnly();
                break;
            }
            case "bullHornEmailOff": {

                hideButtons();
                bullHornEmailOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startEmailInputOnly();
                break;
            }
            case "bullHornEmailOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornPhon1Off": {

                hideButtons();
                bullHornPhon1On.setVisibility(VISIBLE);

                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startHphInputOnly();
                break;
            }
            case "bullHornPhon1On": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornPhon2Off": {

                hideButtons();
                bullHornPhon2On.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startCphInputOnly();
                break;
            }
            case "bullHornPhon2On": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornPhon3Off": {

                hideButtons();
                bullHornPhon3On.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startWphInputOnly();
                break;
            }
            case "bullHornPhon3On": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornLicStOff": {

                hideButtons();
                bullHornLicStOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startLicenseStateInputOnly();
                break;
            }
            case "bullHornLicStOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }
            case "bullHornCompOff": {

                hideButtons();
                bullHornCompOn.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddInvolvedParty.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                startCompanyInputOnly();
                break;
            }
            case "bullHornCompOn": {
                resetButtons();
                if (speech != null) {
                    resetBeforeExit();
                    Log.i(LOG_TAG, "destroy");
                }
                break;
            }

            default: {

                break;
            }
        }

    }

    private void scheduleAllowAnimation() {
        Handler handler = new Handler();
        handler.postDelayed(this::animationPassed, 250);
    }

    private void animationPassed() {
        switch (CurrentTab) {
            case PHONE_TAB: {
                ll00.setVisibility(GONE);
                ll01.setVisibility(GONE);
                ll02.setVisibility(GONE);
                ll03.setVisibility(VISIBLE);
                break;
            }

            case ADDRESS_TAB: {
                ll00.setVisibility(GONE);
                ll01.setVisibility(GONE);
                ll02.setVisibility(VISIBLE);
                ll03.setVisibility(GONE);

                break;
            }
            case NAME_TAB: {
                ll00.setVisibility(VISIBLE);
                ll01.setVisibility(VISIBLE);
                ll02.setVisibility(GONE);
                ll03.setVisibility(GONE);
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
        Intent intent = new Intent(this, ListInvolvedParty.class);
        startActivity(intent);
    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        mInvolvedPartyDao.closeAll();
        mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        mVehicleManifestDao.closeAll();

    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {
        if (ll01.getVisibility() == GONE && ll02.getVisibility() == GONE && ll03.getVisibility() == GONE) {
            showSequence0(view);
        }
        if (ll01.getVisibility() == VISIBLE) {
            showSequence1(view);
        }
        if (ll02.getVisibility() == VISIBLE) {
            showSequence2(view);
        }
        if (ll03.getVisibility() == VISIBLE) {
            showSequence3(view);
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
