package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.auto.accident.report.R;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.PersistenceObj;

import java.util.Locale;

/**
 * Created by myron on 2/8/2019.
 */

public class ConciergeServices extends AppCompatActivity {
    private static final String TAG = "ConciergeServices";
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
//    private String PERSIST_AUTOMOTIVE_SERVICES_MODE;
    private RotateAnimation rotateAnimation;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private Toolbar toolbar;
    private Context context;
    private Intent GetLatLongIntent;
    private Double Lat;
    private Double Long;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concierge_services);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        LinearLayout ll02 = findViewById(R.id.ll02);
        RelativeLayout rl03 = findViewById(R.id.rl03);
        RelativeLayout rl04 = findViewById(R.id.rl04);
        RelativeLayout rl05 = findViewById(R.id.rl05);
        ImageButton btnHotel = findViewById(R.id.btnHotel);
        ImageButton btnCarRental = findViewById(R.id.btnCarRental);
        ImageButton btnFlight = findViewById(R.id.btnFlight);

        mPersistenceObjDao = new PersistenceObjDao(this);
        String androidId = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
      /*  persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AUTOMOTIVE_SERVICES_MODE");
        PERSIST_AUTOMOTIVE_SERVICES_MODE = persistenceObj.getPERSISTENCE_VALUE();
        if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
            // commercial_truck_services
            toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.commercial_truck_services));

        }*/
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


        btnHotel.setOnClickListener(view -> {
            //   if (fireClick == true) {
            //      context = view.getContext();
            //     Intent intent = new Intent(context, AutomotiveTowing.class);
            //     context.startActivity(intent);
            // }
            if (fireClick == true) {
         //       if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {

                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.hotel_services);
                    Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
         //       }
           /*     if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.semi_truck_towing_services);
                    Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }*/
            }

            fireClick = true;
            btnHotel.setImageAlpha(alpha1);
        });
        btnHotel.setOnLongClickListener(view -> {
            btnHotel.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();

 /*           if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                message = res.getString(R.string.search_towing_services);
            }
            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                message = res.getString(R.string.search_semi_truck_towing_services);
            }*/
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });
        btnCarRental.setOnClickListener(view -> {
            //  if (fireClick == true) {
            //       context = view.getContext();
            //       Intent intent = new Intent(context, AutomotiveRepair.class);
            //      context.startActivity(intent);
            //  }
            if (fireClick == true) {
      //          if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.car_rental);
                    Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
   //             }
/*                if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.semi_truck_repair_services);
                    Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }*/
            }

            fireClick = true;
            btnCarRental.setImageAlpha(alpha1);
        });
        btnCarRental.setOnLongClickListener(view -> {
            btnCarRental.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();

 /*           if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                message = res.getString(R.string.search_repair_services);
            }
            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                message = res.getString(R.string.search_semi_truck_repair_services);
            }
*/
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });
        btnFlight.setOnClickListener(view -> {
            //    if (fireClick == true) {
            //      context = view.getContext();
            //      Intent intent = new Intent(context, AutomotivePaintBody.class);
            //       context.startActivity(intent);
            //  }
            if (fireClick == true) {
         //       if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                    context = view.getContext();
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.book_flight);
                    Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
           //     }
  /*              if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                    Resources res = getResources();
                    String SearchString = res.getString(R.string.semi_truck_paint_body_services);
                    Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }*/

            }

            fireClick = true;
            btnFlight.setImageAlpha(alpha1);
        });
        btnFlight.setOnLongClickListener(view -> {
            btnFlight.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
/*            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("PERSONAL_VEHICLES")) {
                message = res.getString(R.string.paint_body_services);
            }
            if (PERSIST_AUTOMOTIVE_SERVICES_MODE.equals("COMMERCIAL_VEHICLES")) {
                message = res.getString(R.string.semi_truck_paint_body_services);
            }*/
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });

        GetLatLongIntent = new Intent(this, GetLatLong.class);
        startActivityForResult(GetLatLongIntent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK)
        {
            if(resultCode == RESULT_OK)
            {
                Lat = Double.parseDouble(data.getStringExtra("Lat"));
                Long = Double.parseDouble(data.getStringExtra("Long"));

            }
        }
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
