package com.auto.accident.report.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.auto.accident.report.R;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.Locale;

/**
 * Created by myron on 9/17/2018.
 */

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Resources res;
    private RotateAnimation rotateAnimation;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private  Context context;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Switch preferOffline = findViewById(R.id.prefer_offline_languages);
        Switch preferVin = findViewById(R.id.prefer_vin);

        res = getResources();
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_OFFLINE");
        String preferOfflineLanguages = persistenceObj.getPERSISTENCE_VALUE();
        if (preferOfflineLanguages.equals("FALSE")) {
            preferOffline.setChecked(false);
            preferOffline.setText(res.getString(R.string.preference_offline_language_off));
        } else {
            preferOffline.setChecked(true);
            preferOffline.setText(res.getString(R.string.preference_offline_language_on));
        }


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_VIN");
        String preferVinTF = persistenceObj.getPERSISTENCE_VALUE();
        if (preferVinTF.equals("FALSE")) {
            preferVin.setChecked(false);
            preferVin.setText(res.getString(R.string.include_vehicle_identification_number_false));
        } else {
            preferVin.setChecked(true);
            preferVin.setText(res.getString(R.string.include_vehicle_identification_number_true));
        }
        String androidId = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String deviceLocale = Locale.getDefault().getLanguage();
        toolbar.setNavigationOnClickListener(view -> {
            context = view.getContext();
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);


        });

        preferOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    preferOffline.setText(res.getString(R.string.preference_offline_language_on));
                    mPersistenceObjDao.updateData("PERSIST_PREFER_OFFLINE", "TRUE");
                } else {
                    preferOffline.setText(res.getString(R.string.preference_offline_language_off));
                    mPersistenceObjDao.updateData("PERSIST_PREFER_OFFLINE", "FALSE");
                }
            }
        });

        preferVin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    preferVin.setText(res.getString(R.string.include_vehicle_identification_number_true));
                    mPersistenceObjDao.updateData("PERSIST_PREFER_VIN", "TRUE");
                } else {
                    preferVin.setText(res.getString(R.string.include_vehicle_identification_number_false));
                    mPersistenceObjDao.updateData("PERSIST_PREFER_VIN", "FALSE");
                }
            }
        });

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
    private void scheduleDismissToolbar() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(context, AccidentMenu.class);
        startActivity(intent);
    }

}
