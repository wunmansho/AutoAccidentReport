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
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PartyTypeDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.auto.accident.report.util.utils.isNumber;

public class UpdatePartyType extends AppCompatActivity
        implements RecognitionListener {
    private static final String TAG = "UpdatePartyType";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_ROLE = 99;
    private static final int REQ_CODE_DESCRIPTION = 98;
    private PartyTypeDao mPartyTypeDao;

    private EditText tiePT_Type, tiePT_Desc;
    private TextView tvPT_ID, tvDA_PROMPT;
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff;
    private String selectedPT_ID;
    private String selectedPT_TYPE;
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

    private Resources res;
    private FloatingActionButton btnSave;
    private FloatingActionButton btnDelete;
    private FloatingActionButton btnSpeakOff;
    private FloatingActionButton btnSpeakOn;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private PersistenceObj persistenceObj;
    private String rsMode;
    private DeviceUser deviceUser;
    private DeviceUserDao mDeviceUserDao;
    private String DA_HORN;
    private Context context;
    private View view;
    private String DA_ID_STR;
    private int DUX_ID;
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

        setContentView(R.layout.update_party_type);
        btnHelp = findViewById(R.id.btnHelp);
        btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        progressBar = findViewById(R.id.progressBar1);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        btnSave = findViewById(R.id.btnSave);

        btnDelete = findViewById(R.id.btnDelete);
        tvPT_ID = findViewById(R.id.tvPT_ID);
        tiePT_Type = findViewById(R.id.tiePT_Type);
        tiePT_Desc = findViewById(R.id.tiePT_Desc);
        mPartyTypeDao = new PartyTypeDao(this);
        toolbar = findViewById(R.id.my_toolbar);
        res = getResources();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Intent receivedIntent = getIntent();
        selectedPT_ID = receivedIntent.getStringExtra("PT_ID");
        selectedPT_TYPE = receivedIntent.getStringExtra("PT_TYPE");
        String selectedPT_DESC = receivedIntent.getStringExtra("PT_DESC");
        //set the text to show the current selected IP_FNAME
        tvPT_ID.setText(selectedPT_ID);
        tiePT_Type.setText(selectedPT_TYPE);
        tiePT_Desc.setText(selectedPT_DESC);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PT_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        mDeviceUserDao = new DeviceUserDao(this);
        switch (rsMode) {
            case "ACCIDENT_MENU":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.upt));
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


                toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.upt));
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_OFFLINE");
        String preferOfflineLanguages = persistenceObj.getPERSISTENCE_VALUE();
        if (preferOfflineLanguages.equals("FALSE")) {
            preferOffline = false;

        } else {
            preferOffline = true;

        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
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
        btnSave.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
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
                context = view.getContext();
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

            message = res.getString(R.string.tv0154);
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
        tiePT_Type.requestFocus();
        int DA_SIZE = tiePT_Type.length();
        tiePT_Type.setSelection(DA_SIZE);
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

    private void startDescriptionInput() {
        tiePT_Desc.requestFocus();
        int DA_SIZE = tiePT_Desc.length();
        tiePT_Desc.setSelection(DA_SIZE);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        tvDA_PROMPT.setText(getString(R.string.tv0267) + getString(R.string.tv0218));
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say Middle Initial QUIT or NEXT to continue");
        try {
            REQ_CODE = REQ_CODE_DESCRIPTION;
            speech.startListening(intent);
            //    startActivityForResult(intent, REQ_CODE_STATE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void retry() {
        switch (REQ_CODE) {
            case REQ_CODE_ROLE: {
                startRoleInput();
                break;
            }
            case REQ_CODE_DESCRIPTION: {
                startDescriptionInput();
                break;

            }
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
                    if (DA_RESULT.equals(getString(R.string.tv0051))) {
                        startDescriptionInput();
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
                        tiePT_Type.setText(DA_RESULT);
                        startDescriptionInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }

                }

                if (DA_RESULT.equals(getString(R.string.tv0222))) {
                    startDescriptionInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0224))) {
                    DA_RESULT = "";
                    tiePT_Type.setText(DA_RESULT);
                    startRoleInput();
                    break;
                }
                startRoleInput();
                break;
            }

            case REQ_CODE_DESCRIPTION: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        int DA_SIZE = DA_RESULT.length();
                        DA_RESULT = DA_RESULT.substring(0, 1).toUpperCase() + DA_RESULT.substring(1, DA_SIZE);
                        tiePT_Desc.setText(DA_RESULT);
                        resetBeforeExit();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }

                }


                if (DA_RESULT.equals(getString(R.string.tv0226))) {
                    DA_RESULT = "";
                    startRoleInput();
                    break;
                }
                if (DA_RESULT.equals(getString(R.string.tv0224))) {
                    DA_RESULT = "";
                    tiePT_Desc.setText(DA_RESULT);
                    startDescriptionInput();
                    break;
                }
                startDescriptionInput();
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

    public void disableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(false);
        btnSpeakOff.setEnabled(false);
        btnSpeakOn2.setEnabled(false);
        btnSave.setEnabled(false);
        btnDelete.setEnabled(false);


    }

    public void enableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(true);
        btnSpeakOff.setEnabled(true);
        btnSpeakOn2.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(true);

    }

    public void showSequence0(View view) {
        KeyboardUtils.hideKeyboard(UpdatePartyType.this);
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //    ccp_license_country, ccp_resident_country, ccp_phon1_country
        //int toolBarColorValue = Color.parseColor("#FF0288D1");

        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdatePartyType.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdatePartyType.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_only))
                        .setSecondaryText(res.getString(R.string.got_it))

                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdatePartyType.this)
                        .setTarget(btnDelete)

                        .setPrimaryText(res.getString(R.string.press_to_delete))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdatePartyType.this)
                        .setTarget(btnSave)

                        .setPrimaryText(res.getString(R.string.press_to_save_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdatePartyType.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                                if (state != MaterialTapTargetPrompt.STATE_REVEALING && state != MaterialTapTargetPrompt.STATE_REVEALED && state != MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)

                                {
                                    enableButtons();
                                    //makeTieFocusableTrue();

                                }
                            }
                        }))


                .show();
    }

    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }

    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, ListPartyType.class);
        startActivity(intent);


    }

    public void doClose() {
        //   mInsurancePolicyVDao.closeAll();
        //   mDeviceImageStoreDao.closeAll();
        mPartyTypeDao.closeAll();
        //  mInvolvedImageStoreDao.closeAll();
        //  mVehicleTypeDao.closeAll();
        //  mAccidentNoteDao.closeAll();
        //  mInvolvedPartyDao.closeAll();
        mDeviceUserDao.closeAll();
        //   mInsurancePolicyPDao.closeAll();
        //   mInvolvedVehicleDao.closeAll();
        //   mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //   mPremiumAdvertiserDao.closeAll();
        //   mInsurancePolicyDao.closeAll();
        //  mDeviceVehicleDao.closeAll();
        //  mVehicleManifestDao.closeAll();

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
                //     AutoAdd = false;
                //    inc01.setVisibility(View.GONE);
                if (speech != null) {
                    speech.stopListening();
                    speech.destroy();
                    Log.i(LOG_TAG, "destroy");
                }

                //    startFirstNameInput();
                break;
            }

            case "btnSpeakOff": {
                tvDA_PROMPT.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                llbtnSpeakOff.setVisibility(View.GONE);
                llbtnSpeakOn.setVisibility(View.VISIBLE);

                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdatePartyType.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
                if (AutoAdd.equals(true)) {

                    startRoleInput();
                } else {
                    startVoiceInput();
                }
                break;
            }
            case "btnSave": {
                String PT_ID = tvPT_ID.getText().toString();
                String PT_Type = tiePT_Type.getText().toString();
                String PT_Desc = tiePT_Desc.getText().toString();
                if (!PT_Type.equals("null")) {
                    mPartyTypeDao.updateData(PT_ID, PT_Type, PT_Desc);
                    doClose();
                    Intent intent = new Intent(UpdatePartyType.this, ListPartyType.class);
                    startActivity(intent);
                } else {
                    //   toastMessage("You must enter a valid Party Type ");
                }
                break;
            }
            case "btnDelete": {
                mPartyTypeDao.deletePT_TYPE(selectedPT_ID, selectedPT_TYPE);
                tiePT_Type.setText("");
                doClose();
                Intent intent = new Intent(UpdatePartyType.this, ListPartyType.class);
                startActivity(intent);
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
