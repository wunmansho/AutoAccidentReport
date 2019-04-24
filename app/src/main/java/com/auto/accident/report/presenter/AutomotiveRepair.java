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
import android.widget.RelativeLayout;

import com.auto.accident.report.R;
import com.auto.accident.report.models.PersistenceObjDao;

import java.util.Locale;

/**
 * Created by myron on 6/21/2018.
 */

public class AutomotiveRepair extends AppCompatActivity {

    private static final String TAG = "AttorneysSearch";
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private RotateAnimation rotateAnimation;
    private PersistenceObjDao mPersistenceObjDao;
    private Toolbar toolbar;
    private  Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.automotive_repair);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout rl02 = findViewById(R.id.rl02);
        RelativeLayout rl03 = findViewById(R.id.rl03);
        RelativeLayout rl04 = findViewById(R.id.rl04);
        RelativeLayout rl05 = findViewById(R.id.rl05);
        ImageButton btnPremium = findViewById(R.id.btnPremium);
        ImageButton btnAverage = findViewById(R.id.btnAverage);

        mPersistenceObjDao = new PersistenceObjDao(this);
        String androidId = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

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


        btnPremium.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                Resources res = getResources();
                String SearchString = res.getString(R.string.repair_services);
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            fireClick = true;
            btnPremium.setImageAlpha(alpha1);
        });
        btnPremium.setOnLongClickListener(view -> {
            btnPremium.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
            message = res.getString(R.string.premium_providers);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });
        btnAverage.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                Resources res = getResources();
                String SearchString = res.getString(R.string.repair_services);
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            fireClick = true;
            btnAverage.setImageAlpha(alpha1);
        });
        btnAverage.setOnLongClickListener(view -> {
            btnAverage.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
            message = res.getString(R.string.all_attorneys);
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
        Intent intent = new Intent(this, AutomotiveServices.class);
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
