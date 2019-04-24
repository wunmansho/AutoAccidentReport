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
import com.auto.accident.report.models.AccidentNoteDao;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.AccidentNote;
import com.auto.accident.report.objects.DeviceUser;
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
 * Created by myron on 4/12/2018.
 */

public class UpdateNotes extends AppCompatActivity implements
        RecognitionListener {

    private static final String TAG = "UpdateNotes";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_NOTE_SUBJECT = 99;
    private static final int REQ_CODE_NOTE = 98;
    private AccidentNoteDao mAccidentNoteDao;
    private final String deviceLocale = Locale.getDefault().getCountry();
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff;

    private EditText tieAN_SUBJECT, tieAN_NOTE;
    private TextView tvDA_PROMPT;
    private TextView tvAN_IPID;
    private TextView tvAN_IVID;
    private TextView tvAN_APPATH;
    private String deviceLocalel = Locale.getDefault().getLanguage();
    private final Boolean AutoAdd = true;
    private Boolean AutoAddSave = false;
    private int REQ_CODE;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private final String LOG_TAG = "VoiceRecActivity";
    private String[] splitString;
    private int splitLength;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;

    private final int alpha1 = 255;
    private final int alpha2 = 50;

    private String preferOfflineLanguages;
    private boolean preferOffline;
    private Resources res;
  
    private FloatingActionButton btnSave;
    private FloatingActionButton btnSpeakOff;
    private FloatingActionButton btnDelete;
    private FloatingActionButton btnSpeakOn;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private AccidentNote accidentNote;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private String DA_HORN;
    private  Context context;
    private View view;
    private String IV_ID;
    private String IP_ID;
    private String ACC_NUM;
    private String AP_ID;

    private int AN_AID;
    private int AN_ID;
    private int AN_IPID;
    private int AN_IVID;

    private int DUX_ID;

    private int pos;

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
        setContentView(R.layout.update_notes);
        btnHelp = findViewById(R.id.btnHelp);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnDelete = findViewById(R.id.btnDelete);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        progressBar = findViewById(R.id.progressBar1);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        TextView tvAN_ID = findViewById(R.id.tvAN_ID);
        TextView tvAN_AID = findViewById(R.id.tvAN_AID);
        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tieAN_SUBJECT = findViewById(R.id.tieAN_SUBJECT);
        tieAN_NOTE = findViewById(R.id.tieAN_NOTE);
        btnSave = findViewById(R.id.btnSave);
        res = getResources();
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        mAccidentNoteDao = new AccidentNoteDao(this);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mPersistenceObjDao = new PersistenceObjDao(this);

        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "UPDATE_NOTES");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        tvAN_AID.setText(DA_ID);
        if (isNumber(DA_ID)) {
            AN_AID = Integer.parseInt(DA_ID);
        } else {
            AN_AID = 0;
        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AN_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            AN_ID = Integer.parseInt(DA_ID_STR);
        } else {
            AN_ID = 0;
        }


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DUX_ID = Integer.parseInt(DA_ID_STR);
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

        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.aan));
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
        IP_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
        IV_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AP_ID");
        AP_ID = persistenceObj.getPERSISTENCE_VALUE();

        accidentNote = mAccidentNoteDao.getAccidentNote(AN_ID, AN_AID);
        tieAN_SUBJECT.setText(accidentNote.getAN_SUBJECT());
        tieAN_NOTE.setText(accidentNote.getAN_NOTE());


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


        //   PERSIST_AN_ID
        //  getAccidentNote
        btnSpeakOn.setOnClickListener(view -> {
            if (fireClick == true) {
            context = view.getContext();
            DA_HORN = "btnSpeakOn";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnSpeakOn.startAnimation(rotateAnimation);
            scheduleDoBullhorn();
            }
            fireClick = true;
            btnSpeakOn.setImageAlpha(alpha1);
        });
        btnSpeakOn.setOnLongClickListener(view -> {

            btnSpeakOn.setImageAlpha(alpha2);
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

        btnSave.setOnLongClickListener(view -> {
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
            return false;
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
        //     AutoAdd = false;
        super.onStop();
        if (speech != null) {
            speech.stopListening();
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    private void startNoteSubjectInput() {
        tieAN_SUBJECT.requestFocus();
        int DA_SIZE = tieAN_SUBJECT.length();
        tieAN_SUBJECT.setSelection(DA_SIZE);
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
            REQ_CODE = REQ_CODE_NOTE_SUBJECT;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_NOTE_SUBJECT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void startNoteInput() {
        tieAN_NOTE.requestFocus();
        int DA_SIZE = tieAN_NOTE.length();
        tieAN_NOTE.setSelection(DA_SIZE);
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
            REQ_CODE = REQ_CODE_NOTE;
            speech.startListening(intent);
            // startActivityForResult(intent, REQ_CODE_NOTE);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void retry() {
        switch (REQ_CODE) {
            case REQ_CODE_NOTE_SUBJECT: {
                startNoteSubjectInput();
                break;
            }
            case REQ_CODE_NOTE: {
                startNoteInput();
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
                        startNoteInput();
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

            case REQ_CODE_NOTE_SUBJECT:
                String DA_RESULT2;
            {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        // DA_RESULT2 = tieAN_NOTE.getText().toString();
                        DA_RESULT = utils.capEachWord(DA_RESULT);
                        tieAN_SUBJECT.setText(DA_RESULT);
                        startNoteInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0222))) {
                        startNoteInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieAN_SUBJECT.setText(DA_RESULT);
                        startNoteSubjectInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startNoteSubjectInput();
                        break;
                    }
                    startNoteSubjectInput();
                }
                break;
            }
            case REQ_CODE_NOTE: {
                if (null != DA_RESULT) {
                    if (!DA_RESULT.equals(getString(R.string.tv0220)) && !DA_RESULT.equals(getString(R.string.tv0222)) && !DA_RESULT.equals(getString(R.string.tv0224)) && !DA_RESULT.equals(getString(R.string.tv0226)) && !DA_RESULT.equals(getString(R.string.tv0226))) {
                        // DA_RESULT2 = tieAN_NOTE.getText().toString();
                        DA_RESULT = darkKnight(DA_RESULT);
                        DA_RESULT = utils.capSentence(DA_RESULT);
                        tieAN_NOTE.setText(DA_RESULT);
                        resetBeforeExit();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0220))) {
                        resetBeforeExit();
                        break;
                    }


                    if (DA_RESULT.equals(getString(R.string.tv0224))) {
                        DA_RESULT = "";
                        tieAN_NOTE.setText(DA_RESULT);
                        startNoteInput();
                        break;
                    }
                    if (DA_RESULT.equals(getString(R.string.tv0226))) {
                        startNoteSubjectInput();
                        break;
                    }
                    startNoteInput();
                }
                break;
            }
        }
    }

    private String darkKnight(String DA_RESULT) {
        if (DA_RESULT != null) {
            //  DA_RESULT = DA_RESULT.toUpperCase();

            DA_RESULT = DA_RESULT.replaceAll("Dark Knight", "dark night");
        }
        return DA_RESULT;
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
    public void showSequence(View view) {
        KeyboardUtils.hideKeyboard(UpdateNotes.this);
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateNotes.this)
                .setTarget(tb.getChildAt(2))


                .setPrimaryText(res.getString(R.string.shield_icon2))
                .setSecondaryText(res.getString(R.string.got_it))
                .setAnimationInterpolator(new LinearOutSlowInInterpolator())
        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateNotes.this)
                        .setTarget(btnSpeakOff)


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateNotes.this)
                        .setTarget(btnDelete)


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateNotes.this)
                        .setTarget(btnSave)


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(UpdateNotes.this)
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
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, ListNotes.class);
        startActivity(intent);
    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
        //mDeviceUserDao.closeAll();
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
                //     AutoAdd = false;
                if (speech != null) {
                    speech.stopListening();
                    speech.destroy();
                    Log.i(LOG_TAG, "destroy");
                }

                startNoteSubjectInput();
                break;
            }

            case "btnSpeakOff": {
                tvDA_PROMPT.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                llbtnSpeakOff.setVisibility(View.GONE);
                llbtnSpeakOn.setVisibility(View.VISIBLE);

                speech = SpeechRecognizer.createSpeechRecognizer(context);
                speech.setRecognitionListener(UpdateNotes.this);
                Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));

                if (AutoAdd.equals(true)) {

                    startNoteSubjectInput();
                } else {
                    startVoiceInput();
                }
                break;
            }
            case "btnSave": {
                if (isNumber(IP_ID)) {
                    AN_IPID = Integer.parseInt(IP_ID);
                } else {
                    AN_IPID = 0;
                }
                if (isNumber(IV_ID)) {
                    AN_IVID = Integer.parseInt(IV_ID);
                } else {
                    AN_IVID = 0;
                }

                String AN_SUBJECT = tieAN_SUBJECT.getText().toString();
                String AN_NOTE = tieAN_NOTE.getText().toString();
                mAccidentNoteDao.updateData(AN_ID, AN_AID, AN_IPID, AN_IVID, AP_ID, AN_SUBJECT, AN_NOTE);
                doClose();
                Intent intent = new Intent(UpdateNotes.this, ListNotes.class);
                startActivity(intent);
                break;
            }
            case "btnDelete": {
                mAccidentNoteDao.deleteACCIDENT_NOTE(AN_AID, AN_ID);
                doClose();
                Intent intent = new Intent(UpdateNotes.this, ListNotes.class);
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
        showSequence(view);
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
