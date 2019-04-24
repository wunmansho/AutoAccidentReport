package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
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
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 3/12/2018.
 */

public class ListDeviceVehicle extends AppCompatActivity {
    private static final String TAG = "ListDeviceVehicle";
    //  DeviceVehicleDao mDeviceVehicleDao;
    private PersistenceObjDao mPersistenceObjDao;

    private ListView mListView;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnHelp;
    private FloatingActionButton btnLightning;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private FloatingActionButton btnMedia;
  //  private FloatingActionButton btnCamera;
  //  private FloatingActionButton btnGallery;
    private Resources res;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private String rsMode;
    private boolean loaded;
    private PersistenceObj persistenceObj;
    private DeviceUser deviceUser;
    private  DeviceUserDao mDeviceUserDao;
    private Intent intent;
    private  Context context;
    private String DA_ID_STR;
    private int DUX_ID;
    private View view;
    private String PERSIST_ACTION_IN_PROGRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_device_vehicle);

        btnHelp = findViewById(R.id.btnHelp);
        btnLightning = findViewById(R.id.btnLightning);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        //  mDeviceVehicleDao = new DeviceVehicleDao(this);
        res = getResources();

        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("DP_CATEGORY", "DEVICE_VEHICLE");
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DV_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        mDeviceUserDao = new DeviceUserDao(this);
        switch (rsMode) {
            case "ACCIDENT_MENU":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.sdv));
                toolbar.setTitle(getString(R.string.app_name));
                break;
            case "ACCIDENT_MENU_NAVIGATION_DRAWER":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.sdv));
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

                String DA_RESULT2;
                splitString = DU_FNAME.split(" ");
                DA_RESULT2 = splitString[0];

                toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.sdv));
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
                toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
        }
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
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnAdd.startAnimation(rotateAnimation);
                    intent = new Intent(context, AddDeviceVehicle.class);
                    scheduleDismissIntent();
                }
               // context.startActivity(intent);
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
                res = getResources();
                message = res.getString(R.string.tv0150);
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

        mListView = findViewById(R.id.listView);
        //set an onItemClickListener to the ListView
        mListView.setAdapter(new ListDeviceVehicleAdapter(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!loaded) {
            //First time just set the loaded flag true
            loaded = true;
        } else {
            showListView();
        }
    }
    public void disableButtons() {


        btnAdd.setEnabled(false);

           btnHelp.setEnabled(false);
        btnLightning.setEnabled(false);



    }
    public void enableButtons() {


        btnAdd.setEnabled(true);
        btnHelp.setEnabled(true);
        btnLightning.setEnabled(true);


    }

    public void showSequence1(View view) {
        toolbar = this.findViewById(R.id.my_toolbar);
        disableButtons();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                        .setTarget(toolbar.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_vehicle_profile))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )



                .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
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
        toolbar = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        btnMedia = mListView.getChildAt(0).findViewById(R.id.btnMedia);

  //      btnCamera = mListView.getChildAt(0).findViewById(R.id.btnCamera);
     //   btnGallery = mListView.getChildAt(0).findViewById(R.id.btnGallery);

        switch (rsMode) {

            case "LIST_INVOLVED_VEHICLE": {
                new MaterialTapTargetSequence()

                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(toolbar.getChildAt(2))

                                .setPrimaryText(res.getString(R.string.shield_icon2))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(btnAdd)
                                .setPrimaryText(res.getString(R.string.plus_icon_vehicle_profile))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )



                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(mListView.getChildAt(0))

                                .setPrimaryText(res.getString(R.string.press_list_to_select_profile))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setPromptFocal(new RectanglePromptFocal())
                        )
                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(btnMedia)
                                .setPrimaryText(res.getString(R.string.multi_media_menu_helpa))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )


                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
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
                break;
            }


            default: {
                new MaterialTapTargetSequence()

                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(toolbar.getChildAt(2))

                                .setPrimaryText(res.getString(R.string.shield_icon2))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(btnAdd)
                                .setPrimaryText(res.getString(R.string.plus_icon_vehicle_profile))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )



                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(mListView.getChildAt(0))

                                .setPrimaryText(res.getString(R.string.to_edit_device_vehicle_profile))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setPromptFocal(new RectanglePromptFocal())
                        )
                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
                                .setTarget(btnMedia)

                                .setPrimaryText(res.getString(R.string.multi_media_menu_helpa))
                                .setSecondaryText(res.getString(R.string.got_it))
                                .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                        .addPrompt(new MaterialTapTargetPrompt.Builder(ListDeviceVehicle.this)
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

                break;


            }

        }
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
        if (rsMode.equals("LIST_INVOLVED_VEHICLE")) {
            Intent intent = new Intent(this, ListInvolvedVehicle.class);
            startActivity(intent);
        }
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

            mDeviceUserDao.closeAll();

    }
    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 250);
    }
    private void dismissIntent() {
        doClose();
        this.startActivity(intent);
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
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }
    private void doHelp() {
        int childCount = mListView.getChildCount();
        if (childCount == 0) {
            showSequence1(view);
        } else {
            showSequence2(view);
        }

    }



}
