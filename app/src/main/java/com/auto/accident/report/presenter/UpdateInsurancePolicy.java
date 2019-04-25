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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.InsurancePolicyDao;
import com.auto.accident.report.models.InvolvedPartyDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.InsurancePolicy;
import com.auto.accident.report.objects.InvolvedParty;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;
import com.auto.accident.report.util.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 6/7/2018.
 */

public class UpdateInsurancePolicy extends AppCompatActivity implements
        RecognitionListener {
    private static final String TAG = "UpdateInsurancePolicy";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_INSURANCE_COMPANY = 99;
    private static final int REQ_CODE_POLICY_NUMBER = 98;
    private InsurancePolicyDao mInsurancePolicyDao;
    private final String deviceLocale = Locale.getDefault().getCountry();
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff;
    private FloatingActionButton btnSave;
    private FloatingActionButton btnSpeakOff;
    private EditText tieXX_CNAM, tieXX_PNUM;
    private TextView tvDA_PROMPT;
    private String deviceLocalel = Locale.getDefault().getLanguage();
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
    private int IPO_ID;
    private int IPO_AID;
    private int IPO_HOLDER;
    private String preferOfflineLanguages;
    private boolean preferOffline;

   

    private String DA_RESULT;
    private String[] singleChoiceItems;
    private ArrayList<String> resultx;
    private FloatingActionButton btnHelp;
    private Resources res;
    private FloatingActionButton btnSpeakOn;
    private FloatingActionButton btnDelete;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private InvolvedParty involvedParty;
    private InsurancePolicy insurancePolicy;
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
        setContentView(R.layout.update_insurance_policy);

        btnHelp = findViewById(R.id.btnHelp);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        btnSpeakOn = findViewById(R.id.btnSpeakOn);

        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        progressBar = findViewById(R.id.progressBar1);
        TextView tvpolicy_holder = findViewById(R.id.tvpolicy_holder);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        TextView tvIPO_ID = findViewById(R.id.tvIPO_ID);
        TextView tvIPO_AID = findViewById(R.id.tvIPO_AID);
        TextView tvIPO_HOLDER = findViewById(R.id.tvIPO_HOLDER);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tieXX_CNAM = findViewById(R.id.tieXX_CNAM);
        tieXX_PNUM = findViewById(R.id.tieXX_PNUM);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        res = getResources();
        InvolvedPartyDao mInvolvedPartyDao = new InvolvedPartyDao(this);
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        mInsurancePolicyDao = new InsurancePolicyDao(this);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        tvIPO_AID.setText(DA_ID);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID)) {
            DUX_ID = Integer.parseInt(DA_ID);
        } else {
            DUX_ID = 0;
        }

        DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.update_insurance_policy));
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(ACC_NUM)) {
            IPO_AID = Integer.parseInt(ACC_NUM);
        } else {
            IPO_AID = 0;
        }

         persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
        String IP_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            IPO_ID = Integer.parseInt(DA_ID_STR);
        } else {
            IPO_ID = 0;
        }
        insurancePolicy = mInsurancePolicyDao.getInsurancePolicy(IPO_ID, IPO_AID);
        tvIPO_ID.setText(Integer.toString(insurancePolicy.getIPO_ID()));
        tvIPO_AID.setText(Integer.toString(insurancePolicy.getIPO_AID()));
        tvIPO_HOLDER.setText(Integer.toString(insurancePolicy.getIPO_HOLDER()));
        IPO_HOLDER = insurancePolicy.getIPO_HOLDER();
        tieXX_CNAM.setText(insurancePolicy.getIPO_CNAM());
        tieXX_PNUM.setText(insurancePolicy.getIPO_PNUM());
        involvedParty = mInvolvedPartyDao.getInvolvedParty(IPO_HOLDER, IPO_AID);
        tvpolicy_holder.setText(involvedParty.getIP_FNAME());
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_OFFLINE");
        String preferOfflineLanguages = persistenceObj.getPERSISTENCE_VALUE();
        if (preferOfflineLanguages.equals("FALSE")) {
            preferOffline = false;

        } else {
            preferOffline = true;

        }
        ActionBar actionBar = getSupportActionBar();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
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
            btnSave.setImageAlpha(alpha1);

        });

        btnDelete.setOnLongClickListener(view -> {
            btnDelete.setImageAlpha(alpha2);
            context = view.getContext();

            message = res.getString(R.string.press_to_delete);
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

    private void startInsurancCompanyInput() {
        tieXX_CNAM.requestFocus();
        int DA_SIZE = tieXX_CNAM.length();
        tieXX_CNAM.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.insurance_company_name) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_INSURANCE_COMPANY;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_INSURANCE_COMPANY);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startPolicyNumberInput() {
        tieXX_PNUM.requestFocus();
        int DA_SIZE = tieXX_PNUM.length();
        tieXX_PNUM.setSelection(DA_SIZE);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, preferOffline);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, deviceLocale);
        //   intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ok Say First Name, QUIT or NEXT to continue");
        tvDA_PROMPT.setText(getString(R.string.insurance_policy_number) + getString(R.string.tv0228));
        //  String textToSpeak = "Ok Say First Name QUIT or NEXT to continue";
        // String str_result= new RunInBackGround().execute().get();
        //  speak(textToSpeak);
        try {
            REQ_CODE = REQ_CODE_POLICY_NUMBER;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_POLICY_NUMBER);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void retry() {
        switch (REQ_CODE) {
            case REQ_CODE_INSURANCE_COMPANY: {
                startInsurancCompanyInput();
                break;
            }
            case REQ_CODE_POLICY_NUMBER: {
                startPolicyNumberInput();
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
                    if (DA_RESULT.equals(getString(R.string.tv0230))) {
                        startPolicyNumberInput();
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

            case REQ_CODE_INSURANCE_COMPANY: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226)) && !DA_RESULT.equals(getString(R.string.tv0226))) {


                        tieXX_CNAM.setText(DA_RESULT);
                        startPolicyNumberInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startPolicyNumberInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_CNAM.setText(DA_RESULT);
                        startInsurancCompanyInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startInsurancCompanyInput();
                        break;
                    }
                    startInsurancCompanyInput();
                }
                break;
            }
            case REQ_CODE_POLICY_NUMBER: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
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
                        getSelectedItemPolicy();

                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        //      startMiddleInitialInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieXX_PNUM.setText(DA_RESULT);
                        startPolicyNumberInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startPolicyNumberInput();
                        break;
                    }
                    startPolicyNumberInput();
                }
                break;
            }
        }
    }

    public void getSelectedItemPolicy() {
        int itemSelected = 0;

        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.select_insurance_policy_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resultx.get(selectedIndex);
                                tieXX_PNUM.setText(DA_RESULT);
                                resetBeforeExit();
                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        startPolicyNumberInput();
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
        KeyboardUtils.hideKeyboard(UpdateInsurancePolicy.this);
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //    ccp_license_country, ccp_resident_country, ccp_phon1_country
        //int toolBarColorValue = Color.parseColor("#FF0288D1");

        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateInsurancePolicy.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateInsurancePolicy.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_only))
                        .setSecondaryText(res.getString(R.string.got_it))

                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateInsurancePolicy.this)
                        .setTarget(btnDelete)

                        .setPrimaryText(res.getString(R.string.press_to_delete_vehicle_profile))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateInsurancePolicy.this)
                        .setTarget(btnSave)

                        .setPrimaryText(res.getString(R.string.press_to_save_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateInsurancePolicy.this)
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
                        })
                )


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
        Intent intent = new Intent(this, ListInsurancePolicy.class);
        startActivity(intent);
    }

    public void doClose() {
        //  mInsurancePolicyVDao.closeAll();
        //  mDeviceImageStoreDao.closeAll();
        //  mPartyTypeDao.closeAll();
        //  mInvolvedImageStoreDao.closeAll();
        //  mVehicleTypeDao.closeAll();
        //   mAccidentNoteDao.closeAll();
        //   mInvolvedPartyDao.closeAll();
        //   mDeviceUserDao.closeAll();
        //   mInsurancePolicyPDao.closeAll();
        //   mInvolvedVehicleDao.closeAll();
        //   mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //  mPremiumAdvertiserDao.closeAll();
        mInsurancePolicyDao.closeAll();
        //   mDeviceVehicleDao.closeAll();
        //   mVehicleManifestDao.closeAll();
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
                //   AutoAdd = false;
                if (speech != null) {
                    speech.stopListening();
                    speech.destroy();
                    Log.i(LOG_TAG, "destroy");
                }

                startInsurancCompanyInput();
                break;
            }

            case "btnSpeakOff": {
                tvDA_PROMPT.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                llbtnSpeakOff.setVisibility(View.GONE);
                llbtnSpeakOn.setVisibility(View.VISIBLE);

                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateInsurancePolicy.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));

                if (AutoAdd.equals(true)) {

                    startInsurancCompanyInput();
                } else {
                    startVoiceInput();
                }
                break;
            }
            case "btnSave": {
                String IPO_CNAM = tieXX_CNAM.getText().toString();
                String IPO_PNUM = tieXX_PNUM.getText().toString();
                String IPO_V1 = "";
                String IPO_V2 = "";
                String IPO_V3 = "";
                String IPO_V4 = "";
                String IPO_V5 = "";
                String IPO_V6 = "";
                String IPO_V7 = "";
                String IPO_V8 = "";
                mInsurancePolicyDao.updateData(IPO_ID, IPO_AID, IPO_HOLDER, IPO_CNAM, IPO_PNUM, IPO_V1, IPO_V2, IPO_V3, IPO_V4,
                        IPO_V5, IPO_V6, IPO_V7, IPO_V8);
                doClose();
                Intent intent = new Intent(UpdateInsurancePolicy.this, ListInsurancePolicy.class);
                startActivity(intent);
                break;
            }
            case "btnDelete": {
                mInsurancePolicyDao.deleteIPO_ID(IPO_ID);
                doClose();
                Intent intent = new Intent(UpdateInsurancePolicy.this, ListInsurancePolicy.class);
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