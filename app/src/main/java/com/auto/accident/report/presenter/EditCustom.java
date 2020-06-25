package com.auto.accident.report.presenter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.auto.accident.report.R;
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.database.VehicleTypeDao;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.util.utils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by myron on 6/23/2018.
 */

public class EditCustom extends AppCompatActivity implements RecognitionListener {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_BUTTON01 = 99;
    private static final int REQ_CODE_PHON1 = 98;
    private static final int REQ_CODE_BUTTON02 = 97;
    private static final int REQ_CODE_PHON2 = 96;
    private static final int REQ_CODE_URL = 95;
    //   private static final int REQ_CODE_DIRECTORY = 94;
    private PersistenceObjDao mPersistenceObjDao;
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff;
    private ImageButton btnUpdate;
    private FloatingActionButton btnSpeakOff;

    private EditText tieXX_BUTTON01, tieXX_PHON1,
            tieXX_URL1, tieXX_ICONURL1;
    private TextView tvDA_PROMPT, tv0009, tv00130, tv00141, tv0010, tv00131;
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
    private CountryCodePicker ccp_phon1_country, ccp_phon2_country;
    private Resources res;
    private RotateAnimation rotateAnimation;
  

    private String rsMode;
    private String CheckedRadioButton;
    private PersistenceObj persistenceObj;
    private  Context context;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_custom);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        ImageButton btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        RadioButton rbUrl = findViewById(R.id.rbUrl);
        RadioButton rbPhone = findViewById(R.id.rbPhone);
        progressBar = findViewById(R.id.progressBar1);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tieXX_BUTTON01 = findViewById(R.id.tieXX_BUTTON01);
        tieXX_ICONURL1 = findViewById(R.id.tieXX_ICONURL1);
        ccp_phon1_country = findViewById(R.id.ccp_phon1_country);
        tieXX_PHON1 = findViewById(R.id.tieXX_PHON1);

        btnUpdate = findViewById(R.id.btnUpdate);

        tv0009 = findViewById(R.id.tv0009);

        tv00130 = findViewById(R.id.tv00130);

        //  tv00141 = findViewById(R.id.tv00141);
        //   tieXX_DIRECTORY1 = findViewById(R.id.tieXX_DIRECTORY1);


        tv0010 = findViewById(R.id.tv0010);
        tv00131 = findViewById(R.id.tv00131);
        tieXX_URL1 = findViewById(R.id.tieXX_URL1);

        VehicleTypeDao mVehicleTypeDao = new VehicleTypeDao(this);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_MODE");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();

        // RIGHT_ICON
        if (rsMode.equals("LEFT_ICON")) {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_BUTTON01");
            final String BUTTON01 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_ICON_URL01");
            final String ICONURL01 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE01_COUNTRY");
            final String COUNTRY01 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE01");
            final String PHONE01 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_URL01");
            final String URL01 = persistenceObj.getPERSISTENCE_VALUE();
            //   persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_DIRECTORY01");
            //   final String DIRECTORY01 = persistenceObj.getPERSISTENCE_VALUE();
            tieXX_BUTTON01.setText(BUTTON01);
            tieXX_ICONURL1.setText(ICONURL01);
            tieXX_PHON1.setText(PHONE01);
            tieXX_URL1.setText(URL01);
            //  tieXX_DIRECTORY1.setText(DIRECTORY01);
            ccp_phon1_country.setCountryForNameCode(COUNTRY01);
            if (COUNTRY01.equals("")) {
                String locale;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    locale = this.getResources().getConfiguration().getLocales().get(0).getCountry();
                } else {
                    locale = this.getResources().getConfiguration().locale.getCountry();
                }

                ccp_phon1_country.setCountryForNameCode(locale);
            }
            toolbar.setSubtitle(getString(R.string.left_icon));

            if (!PHONE01.equals("")) {
                rbPhone.setChecked(true);
                tv0009.setVisibility(View.VISIBLE);
                tieXX_BUTTON01.setVisibility(View.VISIBLE);
                tv00130.setVisibility(View.VISIBLE);
                tieXX_ICONURL1.setVisibility(View.VISIBLE);
                //   tv00141.setVisibility(View.VISIBLE);
                //   tieXX_DIRECTORY1.setVisibility(View.VISIBLE);
                ccp_phon1_country.setVisibility(View.VISIBLE);
                tieXX_PHON1.setVisibility(View.VISIBLE);
                tv0010.setVisibility(View.VISIBLE);
                tv00131.setVisibility(View.GONE);
                tieXX_URL1.setVisibility(View.GONE);
                CheckedRadioButton = "rbPhone";
            }
            if (!URL01.equals("")) {
                rbUrl.setChecked(true);
                tv0009.setVisibility(View.VISIBLE);
                tieXX_BUTTON01.setVisibility(View.VISIBLE);
                tv00130.setVisibility(View.VISIBLE);
                tieXX_ICONURL1.setVisibility(View.VISIBLE);
                //      tv00141.setVisibility(View.VISIBLE);
                //      tieXX_DIRECTORY1.setVisibility(View.VISIBLE);
                ccp_phon1_country.setVisibility(View.GONE);
                tieXX_PHON1.setVisibility(View.GONE);
                tv0010.setVisibility(View.GONE);
                tv00131.setVisibility(View.VISIBLE);
                tieXX_URL1.setVisibility(View.VISIBLE);
                CheckedRadioButton = "rbUrl";
            }
        }

        if (rsMode.equals("RIGHT_ICON")) {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_BUTTON02");
            final String BUTTON02 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_ICON_URL02");
            final String ICONURL02 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE02_COUNTRY");
            final String COUNTRY02 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE02");
            final String PHONE02 = persistenceObj.getPERSISTENCE_VALUE();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_URL02");
            final String URL02 = persistenceObj.getPERSISTENCE_VALUE();
            //     persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_DIRECTORY02");
            //     final String DIRECTORY02 = persistenceObj.getPERSISTENCE_VALUE();
            tieXX_BUTTON01.setText(BUTTON02);
            tieXX_ICONURL1.setText(ICONURL02);
            ccp_phon1_country.getSelectedCountryNameCode();
            tieXX_PHON1.setText(PHONE02);
            tieXX_URL1.setText(URL02);
            //     tieXX_DIRECTORY1.setText(DIRECTORY02);
            ccp_phon1_country.setCountryForNameCode(COUNTRY02);
            if (COUNTRY02.equals("")) {
                String locale;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    locale = this.getResources().getConfiguration().getLocales().get(0).getCountry();
                } else {
                    locale = this.getResources().getConfiguration().locale.getCountry();
                }

                ccp_phon1_country.setCountryForNameCode(locale);
            }
            toolbar.setSubtitle(getString(R.string.right_icon));

            if (!PHONE02.equals("")) {
                rbPhone.setChecked(true);
                tv0009.setVisibility(View.VISIBLE);
                tieXX_BUTTON01.setVisibility(View.VISIBLE);
                tv00130.setVisibility(View.VISIBLE);
                tieXX_ICONURL1.setVisibility(View.VISIBLE);
                //        tv00141.setVisibility(View.VISIBLE);
                //        tieXX_DIRECTORY1.setVisibility(View.VISIBLE);
                ccp_phon1_country.setVisibility(View.VISIBLE);
                tieXX_PHON1.setVisibility(View.VISIBLE);
                tv0010.setVisibility(View.VISIBLE);
                tv00131.setVisibility(View.GONE);
                tieXX_URL1.setVisibility(View.GONE);
                CheckedRadioButton = "rbPhone";
            }
            if (!URL02.equals("")) {
                rbUrl.setChecked(true);
                tv0009.setVisibility(View.VISIBLE);
                tieXX_BUTTON01.setVisibility(View.VISIBLE);
                tv00130.setVisibility(View.VISIBLE);
                tieXX_ICONURL1.setVisibility(View.VISIBLE);
                //        tv00141.setVisibility(View.VISIBLE);
                //        tieXX_DIRECTORY1.setVisibility(View.VISIBLE);
                ccp_phon1_country.setVisibility(View.GONE);
                tieXX_PHON1.setVisibility(View.GONE);
                tv0010.setVisibility(View.GONE);
                tv00131.setVisibility(View.VISIBLE);
                tieXX_URL1.setVisibility(View.VISIBLE);
                CheckedRadioButton = "rbUrl";
            }
        }


        if (rsMode.equals("")) {

            toolbar.setSubtitle(getString(R.string.welcome));
        }
        if (rsMode.equals("")) {
            toolbar.setSubtitle(getString(R.string.welcome));

        }


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(view -> {
            context = view.getContext();
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            rotateAnimation.setRepeatMode(Animation.REVERSE);
            rotateAnimation.setDuration(300);
            View btnBack = toolbar.getChildAt(2);
            btnBack.startAnimation(rotateAnimation);

            Intent intent = new Intent(context, AccidentMenu.class);
            startActivity(intent);

        });
        rbPhone.setOnClickListener(view -> {
            tv0009.setVisibility(View.VISIBLE);
            tieXX_BUTTON01.setVisibility(View.VISIBLE);
            tv00130.setVisibility(View.VISIBLE);
            tieXX_ICONURL1.setVisibility(View.VISIBLE);
            //   tv00141.setVisibility(View.VISIBLE);
            //    tieXX_DIRECTORY1.setVisibility(View.VISIBLE);
            ccp_phon1_country.setVisibility(View.VISIBLE);
            tieXX_PHON1.setVisibility(View.VISIBLE);
            tv0010.setVisibility(View.VISIBLE);
            tv00131.setVisibility(View.GONE);
            tieXX_URL1.setVisibility(View.GONE);
            CheckedRadioButton = "rbPhone";
        });
        rbUrl.setOnClickListener(view -> {
            tv0009.setVisibility(View.VISIBLE);
            tieXX_BUTTON01.setVisibility(View.VISIBLE);
            tv00130.setVisibility(View.VISIBLE);
            tieXX_ICONURL1.setVisibility(View.VISIBLE);
//            tieXX_DIRECTORY1.setVisibility(View.VISIBLE);
            ccp_phon1_country.setVisibility(View.GONE);
            tieXX_PHON1.setVisibility(View.GONE);
            tv0010.setVisibility(View.GONE);
            tv00131.setVisibility(View.VISIBLE);
            tieXX_URL1.setVisibility(View.VISIBLE);
            CheckedRadioButton = "rbUrl";
        });


        btnSpeakOn.setOnClickListener(view -> {
            tvDA_PROMPT.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            llbtnSpeakOff.setVisibility(View.VISIBLE);
            llbtnSpeakOn.setVisibility(View.GONE);
            //    AutoAdd = false;
            //    inc01.setVisibility(View.GONE);
            if (speech != null) {
                speech.stopListening();
                speech.destroy();
                Log.i(LOG_TAG, "destroy");
            }

            //    startButton01Input();
        });

        btnSpeakOff.setOnClickListener(view -> {
            if (fireClick == true) {
                tvDA_PROMPT.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                llbtnSpeakOff.setVisibility(View.GONE);
                llbtnSpeakOn.setVisibility(View.VISIBLE);
                context = view.getContext();
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(EditCustom.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));

                if (AutoAdd.equals(true)) {

                    startButton01Input();
                } else {
                    startVoiceInput();
                }
            }
            fireClick = true;
            btnSpeakOff.setImageAlpha(alpha1);

        });
        btnSpeakOff.setOnLongClickListener(view -> {

            btnSpeakOff.setImageAlpha(alpha2);
            context = view.getContext();
            res = getResources();
            message = res.getString(R.string.mic_only);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);

            mdToast.show();

            fireClick = false;

            return false;

        });

        btnUpdate.setOnClickListener(view -> {
            if (fireClick == true) {
                if (rsMode.equals("LEFT_ICON")) {
                    final String BUTTON01b = tieXX_BUTTON01.getText().toString();
                    final String ICONURL01b = tieXX_ICONURL1.getText().toString();
                    final String COUNTRY01b = ccp_phon1_country.getSelectedCountryNameCode();
                    final String PHONE01b = tieXX_PHON1.getText().toString();
                    final String URL01b = tieXX_URL1.getText().toString();
                    //      final String DIRECTORY01b = tieXX_DIRECTORY1.getText().toString();
                    if (CheckedRadioButton.equals("rbPhone")) {
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_BUTTON01", BUTTON01b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_ICON_URL01", ICONURL01b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE01_COUNTRY", COUNTRY01b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE01", PHONE01b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_URL01", "");
                        //      mPersistenceObjDao.updateData("PERSIST_CUSTOM_DIRECTORY01", "");
                    }
                    if (CheckedRadioButton.equals("rbUrl")) {
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_BUTTON01", BUTTON01b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_ICON_URL01", ICONURL01b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE01_COUNTRY", "");
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE01", "");
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_URL01", URL01b);
                        //     mPersistenceObjDao.updateData("PERSIST_CUSTOM_DIRECTORY01", DIRECTORY01b);
                    }

                }
                if (rsMode.equals("RIGHT_ICON")) {
                    final String BUTTON02b = tieXX_BUTTON01.getText().toString();
                    final String ICONURL02b = tieXX_ICONURL1.getText().toString();
                    final String COUNTRY02b = ccp_phon1_country.getSelectedCountryNameCode();
                    final String PHONE02b = tieXX_PHON1.getText().toString();
                    final String URL02b = tieXX_URL1.getText().toString();
                    //        final String DIRECTORY02b = tieXX_DIRECTORY1.getText().toString();
                    if (CheckedRadioButton.equals("rbPhone")) {
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_BUTTON02", BUTTON02b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_ICON_URL02", ICONURL02b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE02_COUNTRY", COUNTRY02b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE02", PHONE02b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_URL02", "");

                    }
                    if (CheckedRadioButton.equals("rbUrl")) {
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_BUTTON02", BUTTON02b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_ICON_URL02", ICONURL02b);
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE02_COUNTRY", "");
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_PHONE02", "");
                        mPersistenceObjDao.updateData("PERSIST_CUSTOM_URL02", URL02b);

                    }
                }


                Intent intent = new Intent(EditCustom.this, AccidentMenu.class);
                startActivity(intent);

            }
            fireClick = true;
            btnUpdate.setImageAlpha(alpha1);
        });
        btnUpdate.setOnLongClickListener(view -> {

            btnUpdate.setImageAlpha(alpha2);
            context = view.getContext();
            res = getResources();
            message = res.getString(R.string.tv0145);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);

            mdToast.show();

            fireClick = false;

            return false;

        });


    }


    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
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
        //      AutoAdd = false;
        super.onStop();
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    private void startButton01Input() {
        tieXX_BUTTON01.requestFocus();
        int DA_SIZE = tieXX_BUTTON01.length();
        tieXX_BUTTON01.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0255) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_BUTTON01;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_BUTTON01);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startPhon1Input() {
        tieXX_PHON1.requestFocus();
        int DA_SIZE = tieXX_PHON1.length();
        tieXX_PHON1.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        tvDA_PROMPT.setText(getString(R.string.tv0256) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Middle Initial QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_PHON1;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_PHON1);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startButton02Input() {
        tieXX_BUTTON01.requestFocus();
        int DA_SIZE = tieXX_BUTTON01.length();
        tieXX_BUTTON01.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0255) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_BUTTON02;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_BUTTON01);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startPhon2Input() {
        tieXX_PHON1.requestFocus();
        int DA_SIZE = tieXX_PHON1.length();
        tieXX_PHON1.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        tvDA_PROMPT.setText(getString(R.string.tv0256) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Middle Initial QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_PHON2;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_PHON1);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startUrlInput() {
        tieXX_URL1.requestFocus();
        int DA_SIZE = tieXX_URL1.length();
        tieXX_URL1.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        tvDA_PROMPT.setText(getString(R.string.tv0259) + getString(R.string.tv0218));
        //    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok I'm Ready for a State Name.");
        try {
            REQ_CODE = REQ_CODE_URL;
            speech.startListening(intent);
            //  startActivityForResult(intent, REQ_CODE_URL);
        } catch (ActivityNotFoundException a) {

        }
    }


    private void retry() {
        switch (REQ_CODE) {
            case REQ_CODE_BUTTON01: {
                startButton01Input();
                break;
            }
            case REQ_CODE_PHON1: {
                startPhon1Input();
                break;
            }
            case REQ_CODE_BUTTON02: {
                startButton02Input();
                break;
            }
            case REQ_CODE_PHON2: {
                startPhon2Input();
                break;
            }
            case REQ_CODE_URL: {
                startUrlInput();
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
        switch (REQ_CODE) {
            case REQ_CODE_SPEECH_INPUT: {
                if (null != DA_RESULT) {
                    DA_RESULT = DA_RESULT.toUpperCase();
                    if (DA_RESULT.equals(getString(R.string.tv0248))) {
                        startButton01Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0249))) {
                        startPhon1Input();
                        break;
                    }

                }
                startVoiceInput();
                break;
            }

            case REQ_CODE_BUTTON01: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        if (DA_RESULT2 == null) {
                            DA_RESULT2 = "";
                            startButton01Input();
                            break;
                        }
                        DA_RESULT = DA_RESULT.toUpperCase();
                        DA_RESULT = utils.removeSpaces(DA_RESULT);
                        DA_RESULT = utils.removeDashes(DA_RESULT);
                        tieXX_BUTTON01.setText(DA_RESULT);
                        startPhon1Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startPhon1Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_BUTTON01.setText(DA_RESULT);
                        startButton01Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startButton01Input();
                        break;
                    }
                    startButton01Input();
                }
                break;
            }
            case REQ_CODE_PHON1: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        //        String St = mStateDao.getState(DA_RESULT);

                        //       if (St != null) {
                        tieXX_PHON1.setText(DA_RESULT);
                        startButton02Input();
                        break;
                        //    }


                        //      DA_RESULT = DA_RESULT.toUpperCase();
                        //    DA_RESULT = DA_RESULT.substring(0, 1);


                        //   break;

                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startButton01Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startButton02Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_PHON1.setText(DA_RESULT);
                        startPhon1Input();
                        break;
                    }
                    startPhon1Input();
                }
                break;
            }

            case REQ_CODE_BUTTON02: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        if (DA_RESULT2 == null) {
                            DA_RESULT2 = "";
                            startButton02Input();
                            break;
                        }
                        DA_RESULT = DA_RESULT.toUpperCase();
                        DA_RESULT = utils.removeSpaces(DA_RESULT);
                        DA_RESULT = utils.removeDashes(DA_RESULT);
                        tieXX_BUTTON01.setText(DA_RESULT);
                        startPhon2Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startPhon2Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_BUTTON01.setText(DA_RESULT);
                        startButton02Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startButton02Input();
                        break;
                    }
                    startButton02Input();
                }
                break;
            }
            case REQ_CODE_PHON2: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        //        String St = mStateDao.getState(DA_RESULT);

                        //       if (St != null) {
                        tieXX_PHON1.setText(DA_RESULT);
                        startUrlInput();
                        break;


                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startButton02Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startButton02Input();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_PHON1.setText(DA_RESULT);
                        startPhon1Input();
                        break;
                    }
                    startPhon2Input();
                }
                break;
            }


            case REQ_CODE_URL: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        tieXX_URL1.setText(DA_RESULT);
                        resetBeforeExit();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }

                }
                if (DA_RESULT.equals(getString(R.string.tv0226))) {
                    startPhon2Input();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0222))) {
                    resetBeforeExit();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0224))) {
                    DA_RESULT = "";
                    tieXX_URL1.setText(DA_RESULT);
                    startPhon2Input();
                    break;
                }
                startPhon2Input();
                break;
            }


        }
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
        //  tvDA_PROMPT.setText(errorMessage);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        retry();
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

    public void onBackPressed() {
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(100);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        View btnBack = toolbar.getChildAt(2);
        Intent intent = new Intent(context, AccidentMenu.class);
        startActivity(intent);
        btnBack.startAnimation(rotateAnimation);

    }


}
