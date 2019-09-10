package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListView;

import com.auto.accident.report.R;
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.DeviceUser;
import com.auto.accident.report.model.PersistenceObj;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 6/9/2018.
 */

public class ListVehicleCoverage extends AppCompatActivity {
    private static final String TAG = "ListVehicleCoverage";

    //  AccidentNoteDao mAccidentNoteDao;
    private PersistenceObjDao mPersistenceObjDao;
    private ListView mListView;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnHelp;
    private FloatingActionButton btnDelete;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private String DA_CALLER;
    private boolean loaded;
    private PersistenceObj persistenceObj;
    private DeviceUserDao mDeviceUserDao;
    private DeviceUser deviceUser;
    private Intent intent;
    private Resources res;
    private String DA_HORN;
    private Context context;
    private View view;
    private String DA_ID_STR;
    private int DUX_ID;
    private String PERSIST_ACTION_IN_PROGRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_insurance_policy);
        btnHelp = findViewById(R.id.btnHelp);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        //  mAccidentNoteDao = new AccidentNoteDao(this);
        res = getResources();
        mDeviceUserDao = new DeviceUserDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

        persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DUX_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DUX_ID = 0;
        }
        deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_INSURANCE_POLICY_VEHICLE_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        String[] splitString;
        int splitLength;
        loaded = false;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];

        toolbar.setSubtitle(getString(R.string.add_remove_policy_vehicle));


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);

        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


			mPersistenceObjDao.updateData("PERSIST_INSURANCE_POLICY_VEHICLE_CALLER", "");
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
                Resources res = getResources();
                message = res.getString(R.string.add_insured);
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
        mListView.setAdapter(new ListVehicleCoverageAdapter(this));
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
        switch (DA_CALLER) {
            case "LIST_INVOLVED_VEHICLE":
                Intent intent = new Intent(this, ListInvolvedVehicle.class);
                startActivity(intent);
                break;
            default:
                intent = new Intent(this, ListInsurancePolicy.class);startActivity(intent);
        }
    }
    public void disableButtons() {


        btnAdd.setEnabled(false);


        btnHelp.setEnabled(false);


    }
    public void enableButtons() {


        btnAdd.setEnabled(true);


        btnHelp.setEnabled(true);



    }

    public void showSequence(View view) {
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        int listViewItemsCount = mListView.getChildCount();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
if (listViewItemsCount == 0) {
    new MaterialTapTargetSequence()
            .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleCoverage.this)
                    .setTarget(tb.getChildAt(2))
                    .setPrimaryText(res.getString(R.string.shield_icon2))
                    .setSecondaryText(res.getString(R.string.got_it))
                    .setAnimationInterpolator(new LinearOutSlowInInterpolator())
            )
            .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleCoverage.this)
                    .setTarget(btnAdd)
                    .setPrimaryText(res.getString(R.string.plus_icon_vehicle_insurance))
                    .setSecondaryText(res.getString(R.string.got_it))
                    .setAnimationInterpolator(new LinearOutSlowInInterpolator())
            )

            .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleCoverage.this)
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
} else {
    btnDelete = mListView.getChildAt(0).findViewById(R.id.btnDelete);
    new MaterialTapTargetSequence()
            .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleCoverage.this)
                    .setTarget(tb.getChildAt(2))
                    .setPrimaryText(res.getString(R.string.shield_icon2))
                    .setSecondaryText(res.getString(R.string.got_it))
                    .setAnimationInterpolator(new LinearOutSlowInInterpolator())
            )
            .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleCoverage.this)
                    .setTarget(btnDelete)
                    .setPrimaryText(res.getString(R.string.delete_icon_insurance))
                    .setSecondaryText(res.getString(R.string.got_it))
                    .setAnimationInterpolator(new LinearOutSlowInInterpolator())
            )
            .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleCoverage.this)
                    .setTarget(btnAdd)
                    .setPrimaryText(res.getString(R.string.plus_icon_vehicle_insurance))
                    .setSecondaryText(res.getString(R.string.got_it))
                    .setAnimationInterpolator(new LinearOutSlowInInterpolator())
            )


            .addPrompt(new MaterialTapTargetPrompt.Builder(ListVehicleCoverage.this)
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
    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
      //  mVehicleManifestDao.closeAll();
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
            case "btnSpeakOn": {

                break;
            }

            case "btnSpeakOff": {


                break;
            }
            case "btnAdd": {
                switch (DA_CALLER) {
                    case "LIST_INVOLVED_VEHICLE":

                        mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "LIST_INSURED_VEHICLES");

                        intent = new Intent(this, ListInsurancePolicy.class);
                        scheduleDismissIntent();
                        // context.startActivity(intent);
                        break;
                    default:
                        mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "LIST_INSURED_VEHICLES");

                        intent = new Intent(this, ListInvolvedVehicle.class);
                        scheduleDismissIntent();
                        // context.startActivity(intent);
                }
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
