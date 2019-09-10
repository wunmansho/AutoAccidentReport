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
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.PartyTypeDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.DeviceUser;
import com.auto.accident.report.model.PersistenceObj;

import java.util.ArrayList;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 1/19/2018.
 */

public class ListPartyType extends AppCompatActivity {
    private static final String TAG = "ListPartyType";
    private PartyTypeDao mPartyTypeDao;
    private PersistenceObjDao mPersistenceObjDao;

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
    private String did_play_ListPartyType;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private String rsMode;
    private boolean loaded;
    private PersistenceObj persistenceObj;
    private DeviceUser deviceUser;
    private  DeviceUserDao mDeviceUserDao;
    private Intent intent;
    private View view;
    private Context context;
    private String DA_ID_STR;
    private int DUX_ID;
    private String PERSIST_ACTION_IN_PROGRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_party_type);
        btnHelp = findViewById(R.id.btnHelp);
        btnAdd = findViewById(R.id.btnAdd);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        mPartyTypeDao = new PartyTypeDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        res = getResources();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PT_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        loaded = false;
        mDeviceUserDao = new DeviceUserDao(this);
        switch (rsMode) {
            case "ACCIDENT_MENU":
                toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.spt));
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

                toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.spt));
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
            btnBack.startAnimation(rotateAnimation);
            scheduleDismissToolbar();

           
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
                    intent = new Intent(context, AddPartyType.class);
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
                res = getResources();
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
        Cursor data = mPartyTypeDao.getData();
        // ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> alPT_ID = new ArrayList<>();
        ArrayList<String> alPT_TYPE = new ArrayList<>();
        ArrayList<String> alPT_DESC = new ArrayList<>();

        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            // listData.add(data.getString(6) + " - " + data.getString(1));
            alPT_ID.add(data.getString(0));
            alPT_TYPE.add(data.getString(1));
            alPT_DESC.add(data.getString(2));

        }

        mListView = findViewById(R.id.listView);
        mListView.setAdapter(new ListPartyTypeAdapter(this, alPT_ID, alPT_TYPE, alPT_DESC));

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

    public void showSequence2(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        disableButtons();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListPartyType.this)
                        .setTarget(tb.getChildAt(2))


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListPartyType.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_party_type))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListPartyType.this)
                        .setTarget(mListView.getChildAt(1))

                        .setPrimaryText(res.getString(R.string.to_edit_party_type))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListPartyType.this)
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
    //    View btnBack = toolbar.getChildAt(2);
  //      btnBack.clearAnimation();
    }
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 250);
    }
    private void dismissActivity() {
        doClose();
     //   View btnBack = toolbar.getChildAt(2);
    //    btnBack.clearAnimation();
        if (rsMode.equals("INVOLVED_MENU")) {
            Intent intent = new Intent(this,  ListInvolvedMenu.class);
            startActivity(intent);
        }
        if (rsMode.equals("ACCIDENT_MENU")) {
            Intent intent = new Intent(this, AccidentMenu.class);
            startActivity(intent);
        }
        if (rsMode.equals("ADD_INVOLVED_PARTY")) {
            Intent intent = new Intent(this, ListInvolvedParty.class);
            this.startActivity(intent);
        }
        if (rsMode.equals("LIST_INVOLVED_PARTY")) {
            Intent intent = new Intent(this, ListInvolvedParty.class);
            this.startActivity(intent);
        }

    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
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
            final Toolbar tb = this.findViewById(R.id.my_toolbar);
            View btnBack = tb.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);


    }


}
