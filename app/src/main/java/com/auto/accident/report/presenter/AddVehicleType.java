package com.auto.accident.report.presenter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
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
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.database.VehicleTypeDao;
import com.auto.accident.report.model.DeviceUser;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.auto.accident.report.util.utils.isNumber;

public class AddVehicleType extends AppCompatActivity implements RecognitionListener {
    private static final String TAG = "AddVehicleType";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_ROLE = 99;
    private static final int REQ_CODE_DESCRIPTION = 98;
    private VehicleTypeDao mVehicleTypeDao;
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff;

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
    private FloatingActionButton btnSpeakOff;
    private FloatingActionButton btnSpeakOn;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private PersistenceObj persistenceObj;
    private String rsMode;
    private DeviceUserDao mDeviceUserDao;
    private DeviceUser deviceUser;
    private String DA_HORN;
    private  Context context;
    private View view;
    private int DUX_ID;
    private String DA_ID_STR;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle_type);
        btnHelp = findViewById(R.id.btnHelp);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        progressBar = findViewById(R.id.progressBar1);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tieVT_Type = findViewById(R.id.tieVT_Type);

        btnAdd = findViewById(R.id.btnAdd);
        mVehicleTypeDao = new VehicleTypeDao(this);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_VT_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        res = getResources();
        mDeviceUserDao = new DeviceUserDao(this);
        switch (rsMode) {
            case "ACCIDENT_MENU":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.add_vehicle_type));
                toolbar.setTitle(getString(R.string.app_name));
                break;
            default:


                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    DUX_ID = Integer.parseInt(DA_ID_STR);
                } else {
                    DUX_ID = 0;
                }
                deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
                String DU_FNAME = deviceUser.getDU_FNAME();
                String[] splitString;
                int splitLength;
                String DA_RESULT2;
                splitString = DU_FNAME.split(" ");
                DA_RESULT2 = splitString[0];

                toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.add_vehicle_type));
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
                toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
                //        int x = 1;
                //   for (int i = 0; i < 3; i++) {
                //      x += 5 * i;
                //  }
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_OFFLINE");
        String preferOfflineLanguages = persistenceObj.getPERSISTENCE_VALUE();
        if (preferOfflineLanguages.equals("FALSE")) {
            preferOffline = false;

        } else {
            preferOffline = true;

        }
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

        btnHelp.setOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnHelp.startAnimation(rotateAnimation);
            scheduleDoHelp();
        });
        btnSpeakOn.setOnClickListener(view -> {
            context = view.getContext();
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

            message = res.getString(R.string.mic_only);
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
                context = view.getContext();
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


    }

    private void AddData(String newVT_Type) {
        boolean insertData = mVehicleTypeDao.addData(newVT_Type);
        // Add Test Comment
        // Add Second Comment
        if (insertData) {
            //   toastMessage("Data Successfully Inserted!");
        } else {
            //    toastMessage("Something went wrong");
        }
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
        //      AutoAdd = false;
        super.onStop();
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    private void startRoleInput() {
        tieVT_Type.requestFocus();
        int DA_SIZE = tieVT_Type.length();
        tieVT_Type.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);

        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.tv0266) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_ROLE;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_PLATE);
        } catch (ActivityNotFoundException a) {

        }
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
                    if (DA_RESULT.equals(getString(R.string.tv0050))) {
                        startRoleInput();
                        break;
                    }

                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }
                }
                startVoiceInput();
                break;
            }

            case REQ_CODE_ROLE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        int DA_SIZE = DA_RESULT.length();
                        DA_RESULT = DA_RESULT.substring(0, 1).toUpperCase() + DA_RESULT.substring(1, DA_SIZE);
                        tieVT_Type.setText(DA_RESULT);
                        resetBeforeExit();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }

                }


                if (DA_RESULT.equals(getString(R.string.tv0224))) {
                    DA_RESULT = "";
                    tieVT_Type.setText(DA_RESULT);
                    startRoleInput();
                    break;
                }
                startRoleInput();
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
        //retry();
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

    public void disableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(false);
        btnSpeakOff.setEnabled(false);
        btnSpeakOn2.setEnabled(false);
        btnAdd.setEnabled(false);


    }
    public void enableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(true);
        btnSpeakOff.setEnabled(true);
        btnSpeakOn2.setEnabled(true);
        btnAdd.setEnabled(true);

    }
    public void showSequence0(View view) {
        KeyboardUtils.hideKeyboard(AddVehicleType.this);

        disableButtons();


        final Toolbar tb = this.findViewById(R.id.my_toolbar);


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddVehicleType.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddVehicleType.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_only))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddVehicleType.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.press_to_add_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddVehicleType.this)
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
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 250);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, ListVehicleType.class);
        startActivity(intent);
    }

    public void doClose() {
       // mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();

            mDeviceUserDao.closeAll();

        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
    private void scheduleDoBullhorn() {
        Handler handler = new Handler();
        handler.postDelayed(this::doBullhorn, 250);
    }

    private void doBullhorn() {
        switch (DA_HORN) {
            case "btnSpeakOn": {
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


                break;
            }

            case "btnSpeakOff": {
                tvDA_PROMPT.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                llbtnSpeakOff.setVisibility(View.GONE);
                llbtnSpeakOn.setVisibility(View.VISIBLE);
                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(AddVehicleType.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));

                if (AutoAdd.equals(true)) {

                    startRoleInput();
                } else {
                    startVoiceInput();
                }

                break;
            }
            case "btnAdd": {
                resetBeforeExit();
                String newVT_Type = tieVT_Type.getText().toString();

                if (tieVT_Type.length() != 0) {
                    AddData(newVT_Type);
                    tieVT_Type.setText("");
                    doClose();
                    Intent intent = new Intent(AddVehicleType.this, ListVehicleType.class);
                    startActivity(intent);
                } else {
                    //   toastMessage("You must put something in the text field!");
                }

                break;
            }
        }
    }

    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {
        showSequence0(view);

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
