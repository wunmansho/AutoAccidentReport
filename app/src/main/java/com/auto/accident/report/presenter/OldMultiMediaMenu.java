package com.auto.accident.report.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 12/4/2018.
 */

public class OldMultiMediaMenu extends AppCompatActivity {
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
    private View view;
    private  Context context;
    private String DA_ID_STR;
    private int DUX_ID;

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
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
       // tvAN_AID.setText(DA_ID);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID)) {
            DUX_ID = Integer.parseInt(DA_ID);
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
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.multi_media_menu));
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
        String IP_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
        String IV_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AP_ID");
        String AP_ID = persistenceObj.getPERSISTENCE_VALUE();

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
        btnGallery.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnGallery.startAnimation(rotateAnimation);
                   scheduleDismissIntent();

            }
            fireClick = true;
            btnGallery.setImageAlpha(alpha1);

        });
        btnCamera.setOnClickListener(view -> {
            if (fireClick == true) {
                 context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnCamera.startAnimation(rotateAnimation);
                scheduleDismissIntent();

            }
            fireClick = true;
            btnCamera.setImageAlpha(alpha1);

        });
        btnVideo.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnVideo.startAnimation(rotateAnimation);
                scheduleDismissIntent();

            }
            fireClick = true;
            btnVideo.setImageAlpha(alpha1);

        });
        btnShare.setOnClickListener(view -> {
            if (fireClick == true) {
                 context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnShare.startAnimation(rotateAnimation);
                scheduleDismissIntent();

            }
            fireClick = true;
            btnShare.setImageAlpha(alpha1);

        });
        btnNote.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnNote.startAnimation(rotateAnimation);
                scheduleDismissIntent();

            }
            fireClick = true;
            btnNote.setImageAlpha(alpha1);

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

            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnSpeakOn.startAnimation(rotateAnimation);

        });

        btnSpeakOff.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnSpeakOff.startAnimation(rotateAnimation);
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
        KeyboardUtils.hideKeyboard(OldMultiMediaMenu.this);
        disableButtons();


        final Toolbar tb = this.findViewById(R.id.my_toolbar);


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(OldMultiMediaMenu.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(OldMultiMediaMenu.this)
                        .setTarget(btnSpeakOff)

                        .setPrimaryText(res.getString(R.string.mic_only))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(OldMultiMediaMenu.this)
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

    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 200);
    }
    private void dismissIntent() {
        doClose();
        this.startActivity(intent);
    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }
    private void doHelp() {
        showSequence0(view);
    }

}