package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.database.PersistenceObjDao;

import java.util.Locale;

/**
 * Created by User on 2/28/2017.
 */

public class AttorneysSearch extends AppCompatActivity {

    private static final String TAG = "AttorneysSearch";
    private PersistenceObjDao mPersistenceObjDao;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private RotateAnimation rotateAnimation;
    private Context context;
    private Toolbar toolbar;
    private Intent GetLatLongIntent;
    private Double Lat;
    private Double Long;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accident_attorneys);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        ImageButton btnPremium = findViewById(R.id.btnPremium);
        ImageButton btnAverage = findViewById(R.id.btnAverage);
        ImageButton btnApply = findViewById(R.id.btnApply);
        TextView textView4 = findViewById(R.id.textView4);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btnApply.getLayoutParams();


        mPersistenceObjDao = new PersistenceObjDao(this);

        String deviceLocale = Locale.getDefault().getLanguage();
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(300);
            View btnBack = toolbar.getChildAt(2);
			scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);
            
        });


        btnPremium.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                Intent intent = new Intent(context, AccidentAttorneyRequest.class);
                context.startActivity(intent);
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
                String SearchString = res.getString(R.string.search_accident_attorney);
                Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString);
                //  Uri gmmIntentUri = Uri.parse("geo:Lat, Long?q=" + SearchString );
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


        btnApply.setOnClickListener(view -> {
            if (fireClick == true) {
                //    context = view.getContext();
                //    Resources res = getResources();
                //    mPersistenceObjDao.updateData("PERSIST_PA_CALLER", "ATTORNEYS_SEARCH");
                //   Intent intent = new Intent(context, SubscriptionActivity.class);
                //   context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

            }
            fireClick = true;
            btnApply.setImageAlpha(alpha1);
        });
        btnApply.setOnLongClickListener(view -> {
            btnApply.setImageAlpha(alpha2);
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
        handler.postDelayed(this::dismissActivity, 250);
    }
    private void dismissActivity() {
         doClose();
        Intent intent = new Intent(this, AccidentMenu.class);
        startActivity(intent);
    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
     //   mVehicleManifestDao.closeAll();
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
