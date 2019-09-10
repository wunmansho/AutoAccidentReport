package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

import com.auto.accident.report.R;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.DeviceUser;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static android.view.View.GONE;

/**
 * Created by myron on 12/5/2018.
 */

public class OldMultiMediaDeviceMenu extends AppCompatActivity {
    private static final String TAG = "OldMultiMediaMenu";
    private final String deviceLocale = Locale.getDefault().getCountry();
    //  private EditText tieAN_SUBJECT, tieAN_NOTE;
    //   private TextView tvAN_IPID;
    //   private TextView tvAN_IVID;
    //   private TextView tvAN_APPATH;
    // private String deviceLocalel = Locale.getDefault().getLanguage();
    private final Boolean AutoAdd = true;
    private Intent recognizerIntent;
    private String[] splitString;
    //  private int splitLength;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    Resources res;

    private FloatingActionButton btnSpeakOff;
    private FloatingActionButton btnSpeakOn;
    private FloatingActionButton btnGallery;
    private FloatingActionButton btnCamera;
    private FloatingActionButton btnVideo;
    private FloatingActionButton btnShare;
    private FloatingActionButton btnNote;
    private FloatingActionButton btnHelp;

    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;

   

    private String preferOfflineLanguages;
    private boolean preferOffline;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private Intent intent;
    private String rsMode;
    private DeviceUserDao mDeviceUserDao;
    private DeviceUser deviceUser;
    private  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_multi_media_menu);

        btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);
        btnVideo = findViewById(R.id.btnVideo);
        btnShare = findViewById(R.id.btnShare);
        btnNote = findViewById(R.id.btnNote);
        btnHelp = findViewById(R.id.btnHelp);

        btnNote.setVisibility(GONE);
        //   tieAN_SUBJECT = findViewById(R.id.tieAN_SUBJECT);
        //  tieAN_NOTE = findViewById(R.id.tieAN_NOTE);
        mPersistenceObjDao = new PersistenceObjDao(this);
        res = getResources();
        mDeviceUserDao = new DeviceUserDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_MULTI_MEDIA_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "ADD_INVOVLVED_PARTY");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_PARTY");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "ADD_INVOVLVED_PARTY");

        // tvAN_AID.setText(DA_ID);

        toolbar.setSubtitle(getString(R.string.multi_media_menu));

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        String IP_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DV_ID");
        String IV_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AP_ID");
        String AP_ID = persistenceObj.getPERSISTENCE_VALUE();

        // Integer AN_APPATH = Integer.parseInt(tvAN_APPATH.getText().toString());
      //  toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
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
        btnGallery.setOnClickListener(view -> {
            if (fireClick == true) {
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

                BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(mBullHornBounceInterpolator);
                btnGallery.startAnimation(myAnim);

                context = view.getContext();
                // Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));


            }
            fireClick = true;
            btnGallery.setImageAlpha(alpha1);

        });
        btnCamera.setOnClickListener(view -> {
            if (fireClick == true) {
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

                BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(mBullHornBounceInterpolator);
                btnCamera.startAnimation(myAnim);

                context = view.getContext();
                // Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));


            }
            fireClick = true;
            btnCamera.setImageAlpha(alpha1);

        });
        btnVideo.setOnClickListener(view -> {
            if (fireClick == true) {
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

                BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(mBullHornBounceInterpolator);
                btnVideo.startAnimation(myAnim);

                context = view.getContext();
                // Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));


            }
            fireClick = true;
            btnVideo.setImageAlpha(alpha1);

        });
        btnShare.setOnClickListener(view -> {
            if (fireClick == true) {
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

                BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(mBullHornBounceInterpolator);
                btnShare.startAnimation(myAnim);

                context = view.getContext();
                // Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));


            }
            fireClick = true;
            btnShare.setImageAlpha(alpha1);

        });



    }







    public void disableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(false);
        btnSpeakOff.setEnabled(false);
        btnSpeakOn2.setEnabled(false);



    }
    public void enableButtons() {
        final ImageButton btnSpeakOn2 = findViewById(R.id.btnSpeakOn);

        btnHelp.setEnabled(true);
        btnSpeakOff.setEnabled(true);
        btnSpeakOn2.setEnabled(true);


    }
    public void showSequence0(View view) {
        KeyboardUtils.hideKeyboard(OldMultiMediaDeviceMenu.this);
        disableButtons();


        final Toolbar tb = this.findViewById(R.id.my_toolbar);


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(OldMultiMediaDeviceMenu.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(OldMultiMediaDeviceMenu.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_only))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(OldMultiMediaDeviceMenu.this)
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
        switch (rsMode) {
            case "LIST_INVOLVED_PARTY": {
                intent = new Intent(this, ListInvolvedParty.class);
                break;
            }

            case "LIST_INVOLVED_VEHICLE": {
                intent = new Intent(this, ListInvolvedVehicle.class);
                break;
            }
            case "LIST_ACCIDENT": {
                intent = new Intent(this, ListAccident.class);
                break;
            }
            case "LIST_DEVICE_USER": {
                intent = new Intent(this, ListDeviceUser.class);
                break;
            }
            case "LIST_DEVICE_VEHICLE": {
                intent = new Intent(this, ListDeviceVehicle.class);
                break;
            }
            default: {
                intent = new Intent(this, ListInvolvedMenu.class);
                break;
            }
        }

        startActivity(intent);
    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();

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
}
