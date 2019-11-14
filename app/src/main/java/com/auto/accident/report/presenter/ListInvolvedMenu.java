package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.InvolvedMenuDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;


/**
 * Created by myron on 12/3/2018.
 */

public class ListInvolvedMenu extends AppCompatActivity {
    private static final String TAG = "ListInvolvedMenu";
    private InvolvedMenuDao mInvolvedMenuDao;

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
    private String did_play_InvolvedMenu;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private String rsMode;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private boolean loaded;
    private DeviceUser deviceUser;
    private  DeviceUserDao mDeviceUserDao;
    private String DA_HORN;
    private  Context context;
    private View view;
    private Intent intent;
    private int helpSequence;
    private String DA_ID_STR;
    private int DUX_ID;
    private String PERSIST_ACTION_IN_PROGRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_involved_menu);
        btnHelp = findViewById(R.id.btnHelp);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        res = getResources();
        mInvolvedMenuDao = new InvolvedMenuDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IM_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        loaded = false;
        mPersistenceObjDao.updateData("PERSIST_IP_CALLER", "LIST_INVOLVED_MENU");
        mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "LIST_INVOLVED_MENU");
        mPersistenceObjDao.updateData("PERSIST_COMPANY_NAME", "");
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

        mDeviceUserDao = new DeviceUserDao(this);
        helpSequence = 0;
        switch (rsMode) {
            case "LIST_ACCIDENT":
                persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    DUX_ID = Integer.parseInt(DA_ID_STR);
                } else {
                    DUX_ID = 0;
                }
                mDeviceUserDao = new DeviceUserDao(this);
                deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
                String DU_FNAME = deviceUser.getDU_FNAME();
                String[] splitString;
                String DA_RESULT2;
                splitString = DU_FNAME.split(" ");
                DA_RESULT2 = splitString[0];
                toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.select_option));
                toolbar.setTitle(getString(R.string.app_name));
                break;
            default:
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.select_option));
                toolbar.setTitle(getString(R.string.app_name));
                break;

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
        Cursor data = mInvolvedMenuDao.getData();
        // ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> alIM_ID = new ArrayList<>();
        ArrayList<String> alIM_TYPE = new ArrayList<>();
        ArrayList<String> alIM_DESC = new ArrayList<>();
        ArrayList<String> alIM_PHON1 = new ArrayList<>();
        ArrayList<String> alIM_EMAIL = new ArrayList<>();
        ArrayList<String> alIM_URL = new ArrayList<>();
        ArrayList<String> alIM_ICONURL = new ArrayList<>();
        ArrayList<String> alIM_CONFURL = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            // listData.add(data.getString(6) + " - " + data.getString(1));
            String typex = data.getString(1);
            if (!typex.equals("BCM")) {
                alIM_ID.add(data.getString(0));
                alIM_TYPE.add(data.getString(1));
                alIM_DESC.add(data.getString(2));
                alIM_PHON1.add(data.getString(3));
                alIM_EMAIL.add(data.getString(4));
                alIM_URL.add(data.getString(5));
                alIM_ICONURL.add(data.getString(6));
                alIM_CONFURL.add(data.getString(7));
            }


        }

        mListView = findViewById(R.id.listView);
        mListView.setAdapter(new ListInvolvedMenuAdapter(this, alIM_ID, alIM_TYPE, alIM_DESC, alIM_PHON1, alIM_EMAIL, alIM_URL, alIM_ICONURL, alIM_CONFURL));

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

    public void showSimpleSequence(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        disableButtons();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        float texSize = 50;
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(0))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.simple_first_accident_step))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(1))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.simple_second_accident_step))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(2))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.simple_third_accident_step))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(tb.getChildAt(2))
                        .setPrimaryTextSize(texSize)

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(btnHelp)
                        .setPrimaryText(res.getString(R.string.btn_first_help) + TAG)
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

    public void showExtendedSequence(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        disableButtons();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
float texSize = 50;
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(0))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.first_accident_stepa))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(0))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.first_accident_stepb))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(0))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.first_accident_stepc))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(0))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.first_accident_stepd))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(0))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.first_accident_stepe))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(1))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.second_accident_stepa))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(1))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.second_accident_stepb))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(1))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.second_accident_stepc))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(mListView.getChildAt(2))
                        .setPrimaryTextSize(texSize)
                        .setPrimaryText(res.getString(R.string.third_accident_step))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
                        .setTarget(tb.getChildAt(2))
                        .setPrimaryTextSize(texSize)

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedMenu.this)
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
        if (rsMode.equals("ACCIDENT_MENU_NAVIGATION_DRAWER")) {
            Intent intent = new Intent(this, AccidentMenu.class);
            startActivity(intent);
        }
        if (rsMode.equals("LIST_ACCIDENT")) {
            Intent intent = new Intent(this, ListAccident.class);
            startActivity(intent);
        }


    }
    public void doClose() {
        //   mInsurancePolicyVDao.closeAll();
        //    mDeviceImageStoreDao.closeAll();
        //    mPartyTypeDao.closeAll();
        //    mInvolvedImageStoreDao.closeAll();
        mInvolvedMenuDao.closeAll();
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
                scheduleDismissIntent();
                break;
            }
        }
    }

    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {
        helpSequence++;
        if (helpSequence < 2) {
            showSimpleSequence(view);
        } else {
            showExtendedSequence(view);
            helpSequence = 0;
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
