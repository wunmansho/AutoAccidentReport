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
 * Created by myron on 4/16/2018.
 */

public class ListNotes extends AppCompatActivity {
    private static final String TAG = "ListNotes";

    //  AccidentNoteDao mAccidentNoteDao;
    private PersistenceObjDao mPersistenceObjDao;

    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private String DA_CALLER;
    private String did_play_ListNotes;
    private Resources res;
    private ListView mListView;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private boolean loaded;
    private PersistenceObj persistenceObj;
    private Intent intent;
    private View view;
    private Context context;
    private String DA_ID_STR;
    private int DUX_ID;
    private String PERSIST_ACTION_IN_PROGRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notes);
        btnHelp = findViewById(R.id.btnHelp);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        //  mAccidentNoteDao = new AccidentNoteDao(this);
        res = getResources();
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
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");


        DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_LIST_NOTES_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];

        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.san));


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);

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

                    mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_NOTES");
                    context = view.getContext();
                    intent = new Intent(context, AddNotes.class);
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

                message = res.getString(R.string.aan);
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
        mListView.setAdapter(new ListNotesAdapter(this));
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


    }
    public void enableButtons() {


        btnAdd.setEnabled(true);

        btnHelp.setEnabled(true);


    }
    public void showSequence0(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        disableButtons();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListNotes.this)
                        .setTarget(tb.getChildAt(2))


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListNotes.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_party_type))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListNotes.this)
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

    public void showSequence1(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        disableButtons();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListNotes.this)
                        .setTarget(tb.getChildAt(2))


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListNotes.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_note))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListNotes.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.to_edit_note))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListNotes.this)
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
        if (DA_CALLER.equals("INVOLVED_MENU")) {
            Intent intent = new Intent(this,  ListInvolvedMenu.class);
            startActivity(intent);
        }
        if (DA_CALLER.equals("ACCIDENT_MENU")) {
            Intent intent = new Intent(this, MultiMediaMenu.class);
            startActivity(intent);
        }
        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE")) {
            Intent intent = new Intent(this, MultiMediaMenu.class);
            startActivity(intent);
        }
        if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {
            Intent intent = new Intent(this, SelectNoteAttachment.class);
            startActivity(intent);
        }

        if (DA_CALLER.equals("LIST_INVOLVED_PARTY")) {
            Intent intent = new Intent(this, MultiMediaMenu.class);
            startActivity(intent);
        }
        if (DA_CALLER.equals("LIST_ACCIDENT")) {
            Intent intent = new Intent(this, MultiMediaMenu.class);
            startActivity(intent);
        }


    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
   //     mVehicleManifestDao.closeAll();
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
        showSequence1(view);
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
