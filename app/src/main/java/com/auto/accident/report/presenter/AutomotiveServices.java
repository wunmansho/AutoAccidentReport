package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.auto.accident.report.R;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.Locale;

/**
 * Created by myron on 6/21/2018.
 */

public class AutomotiveServices extends AppCompatActivity {

    private static final String TAG = "AutomotiveServices";
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private String PERSIST_AUTOMOTIVE_SERVICES_MODE;
    private RotateAnimation rotateAnimation;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private Toolbar toolbar;
    private  Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.automotive_services);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        LinearLayout ll02 = findViewById(R.id.ll02);
        RelativeLayout rl03 = findViewById(R.id.rl03);
        RelativeLayout rl04 = findViewById(R.id.rl04);
        RelativeLayout rl05 = findViewById(R.id.rl05);
        ImageButton btnTow = findViewById(R.id.btnTow);
        ImageButton btnRepair = findViewById(R.id.btnRepair);
        ImageButton btnPaint = findViewById(R.id.btnPaint);

        mPersistenceObjDao = new PersistenceObjDao(this);
        String androidId = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AUTOMOTIVE_SERVICES_MODE");
        PERSIST_AUTOMOTIVE_SERVICES_MODE = persistenceObj.getPERSISTENCE_VALUE();
        if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
            // commercial_truck_services
            toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.commercial_truck_services));

        }
        String deviceLocale = Locale.getDefault().getLanguage();
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);


        });


        btnTow.setOnClickListener(view -> {
            //   if (fireClick == true) {
            //      context = view.getContext();
            //     Intent intent = new Intent(context, AutomotiveTowing.class);
            //     context.startActivity(intent);
            // }
            if (fireClick == true) {
                if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {

                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.search_towing_service);
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.semi_truck_towing_services);
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }

            fireClick = true;
            btnTow.setImageAlpha(alpha1);
        });
        btnTow.setOnLongClickListener(view -> {
            btnTow.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();

            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                message = res.getString(R.string.search_towing_services);
            }
            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                message = res.getString(R.string.search_semi_truck_towing_services);
            }
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });
        btnRepair.setOnClickListener(view -> {
            //  if (fireClick == true) {
            //       context = view.getContext();
            //       Intent intent = new Intent(context, AutomotiveRepair.class);
            //      context.startActivity(intent);
            //  }
            if (fireClick == true) {
                if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.repair_services);
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.semi_truck_repair_services);
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }

            fireClick = true;
            btnRepair.setImageAlpha(alpha1);
        });
        btnRepair.setOnLongClickListener(view -> {
            btnRepair.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();

            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                message = res.getString(R.string.search_repair_services);
            }
            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                message = res.getString(R.string.search_semi_truck_repair_services);
            }
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });
        btnPaint.setOnClickListener(view -> {
            //    if (fireClick == true) {
            //      context = view.getContext();
            //      Intent intent = new Intent(context, AutomotivePaintBody.class);
            //       context.startActivity(intent);
            //  }
            if (fireClick == true) {
                if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.paint_body);
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.semi_truck_paint_body_services);
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }

            }

            fireClick = true;
            btnPaint.setImageAlpha(alpha1);
        });
        btnPaint.setOnLongClickListener(view -> {
            btnPaint.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                message = res.getString(R.string.paint_body_services);
            }
            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                message = res.getString(R.string.semi_truck_paint_body_services);
            }
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });


    }
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, AccidentMenu.class);
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
