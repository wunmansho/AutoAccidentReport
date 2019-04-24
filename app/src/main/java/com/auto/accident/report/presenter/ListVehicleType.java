package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.models.VehicleTypeDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 1/19/2018.
 */

public class ListVehicleType extends AppCompatActivity {
    private static final String TAG = "ListVehicleType";
    private VehicleTypeDao mVehicleTypeDao;

    private ListView mListView;
    private FloatingActionButton btnAdd;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Toolbar toolbar;
    private Resources res;
    private String did_play_ListVehicleType;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private String rsMode;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private boolean loaded;
    private DeviceUser deviceUser;
    private  DeviceUserDao mDeviceUserDao;
    private Intent intent;
    private String DA_HORN;
    private  Context context;
    private View view;
    private String DA_ID_STR;
    private int DUX_ID;
    private String PERSIST_ACTION_IN_PROGRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_party_type);
        btnHelp = findViewById(R.id.btnHelp);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        res = getResources();
        mVehicleTypeDao = new VehicleTypeDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_VT_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

        loaded = false;
        mDeviceUserDao = new DeviceUserDao(this);
        switch (rsMode) {
            case "ACCIDENT_MENU":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.select_vehicle_type));
                toolbar.setTitle(getString(R.string.app_name));
                break;
            case "ACCIDENT_MENU_NAVIGATION_DRAWER":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.select_vehicle_type));
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

                toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.select_vehicle_type));
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
                toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
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
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "true");

                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnHelp.startAnimation(rotateAnimation);
                scheduleDoHelp();
            }
        });

        btnAdd.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    context = view.getContext();
                    DA_HORN = "btnAdd";
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnAdd.startAnimation(rotateAnimation);
                    scheduleDoBullhorn();


                    intent = new Intent(context, AddVehicleType.class);
                }
            }
            fireClick = true;
            btnAdd.setImageAlpha(alpha1);
        });
        btnAdd.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                btnAdd.setImageAlpha(alpha2);
                context = view.getContext();

                message = res.getString(R.string.tv0152);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);

                mdToast.show();

                fireClick = false;
            }
            return false;

        });

        showListView();
    }

    private void showListView() {
        //  Log.d(TAG, "showListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mVehicleTypeDao.getData();
        // ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> alVT_ID = new ArrayList<>();
        ArrayList<String> alVT_TYPE = new ArrayList<>();

        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            // listData.add(data.getString(6) + " - " + data.getString(1));
            alVT_ID.add(data.getString(0));
            alVT_TYPE.add(data.getString(1));


        }

        mListView = findViewById(R.id.listView);
        mListView.setAdapter(new ListVehicleTypeAdapter(this, alVT_ID, alVT_TYPE));

        //set an onItemClickListener to the ListView

    }

    public void disableButtons() {


        btnAdd.setEnabled(false);

        btnHelp.setEnabled(false);

    }
    public void enableButtons() {


        btnAdd.setEnabled(true);

        btnHelp.setEnabled(true);



    }


    public void showSequence1(View view) {
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleType.this)
                        .setTarget(tb.getChildAt(2))


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleType.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_profile_first_time))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleType.this)
                        .setTarget(btnHelp)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        }))


                .show();
    }
    public void showSequence2(View view) {
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleType.this)
                        .setTarget(tb.getChildAt(2))


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleType.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_vehicle_type))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleType.this)
                        .setTarget(mListView.getChildAt(1))

                        .setPrimaryText(res.getString(R.string.to_edit_vehicle_type))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleType.this)
                        .setTarget(btnHelp)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        }))


                .show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
    }
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        if (rsMode.equals("INVOLVED_MENU")) {
            Intent intent = new Intent(this,  ListInvolvedMenu.class);
            startActivity(intent);
        }
        if (rsMode.equals("ACCIDENT_MENU")) {
            Intent intent = new Intent(this, AccidentMenu.class);
            startActivity(intent);
        }


    }
    public void doClose() {
     //   mInsurancePolicyVDao.closeAll();
    //    mDeviceImageStoreDao.closeAll();
    //    mPartyTypeDao.closeAll();
    //    mInvolvedImageStoreDao.closeAll();
        mVehicleTypeDao.closeAll();
   //     mAccidentNoteDao.closeAll();
    //    mInvolvedPartyDao.closeAll();
    //    mDeviceUserDao.closeAll();
    //    mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
       //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();
        mDeviceUserDao.closeAll();
    }
    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 1250);
    }
    private void dismissIntent() {
        doClose();
        this.startActivity(intent);
    }
    private void scheduleDoBullhorn() {
        Handler handler = new Handler();
        handler.postDelayed(this::doBullhorn, 250);
    }

    private void doBullhorn() {
        switch (DA_HORN) {
            case "btnAdd": {
                intent = new Intent(context, AddVehicleType.class);
                startActivity(intent);
                break;
            }

            case "": {

                break;
            }

            default: {

                break;
            }
        }
    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {

        showSequence2(view);

    }
    @Override
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
