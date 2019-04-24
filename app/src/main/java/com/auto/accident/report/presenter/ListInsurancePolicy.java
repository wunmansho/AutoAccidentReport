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
import android.widget.ImageView;
import android.widget.ListView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.InsurancePolicyDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.InsurancePolicy;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;
import java.util.List;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;


/**
 * Created by myron on 6/7/2018.
 */

public class ListInsurancePolicy extends AppCompatActivity {
    private static final String TAG = "ListInsurancePolicy";

    //  AccidentNoteDao mAccidentNoteDao;
    private PersistenceObjDao mPersistenceObjDao;
    private ListView mListView;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnHelp;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private String ListInsurancePolicy;
    private String PERSIST_LIST_INSURANCE_POLICY_CALLER;
    private Resources res;
    private ImageView btnCoveredVehicles;
    private ImageView btnCoveredPeople;
    private Toolbar toolbar;
    private List<InsurancePolicy> insurancePolicyList;
    private int CURRENT_AID;
    int CURRENT_IP_ID;
    private int CURRENT_IPO_ID;

    private String ACC_NUM;
    private int CURRENT_IV_ID;
    private DeviceUser deviceUser;

    private Context context;
    private String did_play_ListInsurancePolicy;
    private boolean doAnimation = true;
    private RotateAnimation rotateAnimation;
    private InsurancePolicyDao mInsurancePolicyDao;
    private boolean loaded;
    private PersistenceObj persistenceObj;
    private Intent intent;
    private View view;
    private String DA_ID_STR;
    private int CURRENT_IPO_HOLDER;
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
        mInsurancePolicyDao = new InsurancePolicyDao(this);
        loaded = false;
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DUX_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DUX_ID = 0;
        }
        deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_LIST_INSURANCE_POLICY_CALLER");
        PERSIST_LIST_INSURANCE_POLICY_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.select_insurance_policy));


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();

        if (isNumber(ACC_NUM)) {
            CURRENT_AID = Integer.parseInt(ACC_NUM);
        } else {
            CURRENT_AID = 0;
        }


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            CURRENT_IV_ID = Integer.parseInt(DA_ID_STR);
        } else {
            CURRENT_IV_ID = 0;
        }




        insurancePolicyList = new ArrayList<>();
        if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_INVOLVED_PARTY")) {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_HOLDER");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                CURRENT_IPO_HOLDER = Integer.parseInt(DA_ID_STR);
            } else {
                CURRENT_IPO_HOLDER = 0;
            }
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                CURRENT_IP_ID = Integer.parseInt(DA_ID_STR);
            } else {
                CURRENT_IP_ID = 0;
            }


            insurancePolicyList = mInsurancePolicyDao.getHolderInsurancePolicys(CURRENT_AID, CURRENT_IPO_HOLDER);
        }
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);

        toolbar.setNavigationOnClickListener(view -> {
            context = view.getContext();

            mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "");
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            btnBack.startAnimation(rotateAnimation);
            scheduleDismissToolbar();


        });
        btnHelp.setOnClickListener(view -> {
            if (doAnimation == true) {
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
            }
            doAnimation = true;
          });


        btnAdd.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {


                    // LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY
                    mPersistenceObjDao.updateData("PERSIST_ADD_INSURANCE_POLICY_CALLER", "LIST_INSURANCE_POLICY");
                    context = view.getContext();
                    if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_INSURED_VEHICLES")) {
                        mPersistenceObjDao.updateData("PERSIST_IP_CALLER", "LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY");
                        intent = new Intent(context, ListInvolvedParty.class);
                        // context.startActivity(intent);

                    } else {
                        intent = new Intent(context, AddInsurancePolicy.class);
                        // context.startActivity(intent);
                    }
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnAdd.startAnimation(rotateAnimation);
                    scheduleDismissIntent();
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

                message = res.getString(R.string.add_insurance_policy);
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
        mListView.setAdapter(new ListInsurancePolicyAdapter(this));
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



    }
    public void enableButtons() {


        btnAdd.setEnabled(true);


        btnHelp.setEnabled(true);


    }
    public void showSequence1(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(tb.getChildAt(2))
                        .setPrimaryText(res.getString(R.string.shield_icon2))

                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_insurance))
                        .setSecondaryText(res.getString(R.string.plus_icon_profile_first_time))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
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
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        btnCoveredPeople = mListView.getChildAt(0).findViewById(R.id.btnCoveredPeople);
        btnCoveredVehicles = mListView.getChildAt(0).findViewById(R.id.btnCoveredVehicles);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.to_edit_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(btnCoveredVehicles)

                        .setPrimaryText(res.getString(R.string.to_select_insured_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(btnCoveredPeople)

                        .setPrimaryText(res.getString(R.string.to_select_insured_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
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

    public void showSequence3(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        btnCoveredPeople = mListView.getChildAt(0).findViewById(R.id.btnCoveredPeople);
        btnCoveredVehicles = mListView.getChildAt(0).findViewById(R.id.btnCoveredVehicles);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.to_pick_vehicle_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInsurancePolicy.this)
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
        if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_INVOLVED_PARTY")) {
            Intent intent = new Intent(this, ListInvolvedParty.class);
            startActivity(intent);
        }
        if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_DEVICE_USER")) {
            Intent intent = new Intent(this, ListDeviceUser.class);
            startActivity(intent);
        }
        if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_INSURED_VEHICLES")) {
            Intent intent = new Intent(this, ListVehicleCoverage.class);
            startActivity(intent);
        }
        if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_INVOLVED_VEHICLES")) {
            Intent intent = new Intent(this, ListVehicleCoverage.class);
            startActivity(intent);
        }
        if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY_LIST_INVOLVED_PARTY")) {
            Intent intent = new Intent(this, ListVehicleCoverage.class);
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
        mInsurancePolicyDao.closeAll();
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
        int listViewItemsCount = mListView.getChildCount();

        if (listViewItemsCount == 0) {
            showSequence1(view);
        } else {
            if (PERSIST_LIST_INSURANCE_POLICY_CALLER.equals("LIST_INVOLVED_PARTY")) {
                showSequence2(view);
            } else {
                showSequence3(view);
            }
        }

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
