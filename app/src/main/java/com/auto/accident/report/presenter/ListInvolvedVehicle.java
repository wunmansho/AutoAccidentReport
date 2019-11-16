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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.InvolvedVehicleDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.models.VehicleManifestDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.InvolvedVehicle;
import com.auto.accident.report.objects.PersistenceObj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;

;
/**
 * Created by myron on 3/12/2018.
 */

public class ListInvolvedVehicle extends AppCompatActivity {
    private static final String TAG = "ListInvolvedVehicle";

    //  InvolvedVehicleDao mInvolvedVehicleDao;
    private PersistenceObjDao mPersistenceObjDao;
    private ListView mListView;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnHelp;
    private FloatingActionButton btnCopy;
    private FloatingActionButton btnLightning;
    private FloatingActionButton btnDelete;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;

    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private FloatingActionButton btnMedia;
   // private FloatingActionButton btnCamera;
 //   private FloatingActionButton btnGallery;
  //  private FloatingActionButton btnNote;
    private FloatingActionButton btnPolicy;
    private FloatingActionButton btnPassenger;
    private Resources res;
    private List<InvolvedVehicle> involvedvehicleList;
    private String did_play_ListInvolvedVehicle;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private String rsMode;
    private boolean loaded;
    private PersistenceObj persistenceObj;
    private Intent intent;
    private int daAxe = 0;
    private View view;
    private Context context;
    private int DUX_ID;
    private int IV_AID;
    private String DA_ID_STR;
    private InvolvedVehicleDao mInvolvedVehicleDao;
    private long insertData;
    private VehicleManifestDao mVehicleManifestDao;
    private  String timeStamp;
    private String PERSIST_ACTION_IN_PROGRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_involved_vehicle);
        btnCopy = findViewById(R.id.btnCopy);
        btnAdd = findViewById(R.id.btnAdd);
        btnHelp = findViewById(R.id.btnHelp);
        btnLightning = findViewById(R.id.btnLightning);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);

        loaded = false;
        //  mInvolvedVehicleDao = new InvolvedVehicleDao(this);
        mVehicleManifestDao = new VehicleManifestDao(this);
        mInvolvedVehicleDao = new InvolvedVehicleDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

        persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DUX_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DUX_ID = 0;
        }
        timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];
        res = getResources();
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.siv));
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            IV_AID = Integer.parseInt(DA_ID_STR);
        } else {
            IV_AID = 0;
        }
        toolbar.setTitle(getString(R.string.app_name) + " # " + DA_ID_STR);
        InvolvedVehicleDao mInvolvedVehicleDao = new InvolvedVehicleDao(this);
        involvedvehicleList = new ArrayList<>();
        involvedvehicleList = mInvolvedVehicleDao.getAllInvolvedVehicles(IV_AID);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        if (rsMode.equals("LIST_INVOLVED_PARTY")) {
            btnCopy.hide();

            btnLightning.hide();
            btnAdd.hide();


        }
        if (rsMode.equals("SELECT_NOTE_ATTACHMENT")) {
            btnCopy.hide();

            btnLightning.hide();
            btnAdd.hide();

            toolbar.setSubtitle(getString(R.string.welcome) + " " + DU_FNAME + " - " + getString(R.string.sna));
        }

        if (rsMode.equals("LIST_INSURED_VEHICLES")) {
            btnCopy.hide();

            btnLightning.hide();
            btnAdd.hide();


        }
        if (rsMode.equals("LIST_INSURED_PARTY")) {
            btnLightning.hide();

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
        if (!involvedvehicleList.isEmpty()) {
            mPersistenceObjDao.updateData("did_play_ListInvolvedVehicle", "true");
        }
        if (involvedvehicleList.isEmpty()) {
            persistenceObj = mPersistenceObjDao.getPersistence("did_play_ListInvolvedVehicle");
            did_play_ListInvolvedVehicle = persistenceObj.getPERSISTENCE_VALUE();
            if (!did_play_ListInvolvedVehicle.equals("true")) {
     //        btnHelp.callOnClick();
                mPersistenceObjDao.updateData("did_play_ListInvolvedVehicle", "true");
            }

        }

        btnCopy.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    mPersistenceObjDao.updateData("PERSIST_DV_MODE", "SELECTPROFILE");
                    mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "LIST_INVOLVED_VEHICLE");
                    context = view.getContext();
                    intent = new Intent(context, ListDeviceVehicle.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnCopy.startAnimation(rotateAnimation);
                    scheduleDismissIntent();
                }
            }
            fireClick = true;
            btnCopy.setImageAlpha(alpha1);
        });

        btnCopy.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                btnCopy.setImageAlpha(alpha2);
                context = view.getContext();

                message = res.getString(R.string.tv0155);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);

                mdToast.show();

                fireClick = false;
            }
            return false;

        });


        btnAdd.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                    context = view.getContext();
                    intent = new Intent(context, AddInvolvedVehicle.class);
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

                message = res.getString(R.string.tv0151);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);

                mdToast.show();

                fireClick = false;
            }
            return false;

        });

        btnLightning.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    context = view.getContext();
                    //    intent = new Intent(context, AddInvolvedVehicle.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnLightning.startAnimation(rotateAnimation);
                    scheduleDoLightning();
                }
                //  scheduleDismissIntent();
            }
            fireClick = true;
            btnLightning.setImageAlpha(alpha1);
        });
        btnLightning.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                btnLightning.setImageAlpha(alpha2);
                context = view.getContext();

                message = res.getString(R.string.tv0151);
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
        mListView.setAdapter(new ListInvolvedVehicleAdapter(this));

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

      // btnCamera.setEnabled(false);

        btnCopy.setEnabled(false);


        btnHelp.setEnabled(false);
        btnLightning.setEnabled(false);



    }
    public void enableButtons() {


        btnAdd.setEnabled(true);

  //      btnCamera.setEnabled(true);

        btnCopy.setEnabled(true);

     //   btnGallery.setEnabled(true);

        btnHelp.setEnabled(true);
        btnLightning.setEnabled(true);


    }


    public void showSequence1(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        disableButtons();
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnLightning)
                        .setPrimaryText(res.getString(R.string.fast_add_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnCopy)
                        .setPrimaryText(res.getString(R.string.btn_copy_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
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
        btnMedia = mListView.getChildAt(0).findViewById(R.id.btnMedia);
    //    btnCamera = mListView.getChildAt(0).findViewById(R.id.btnCamera);
     //   btnGallery = mListView.getChildAt(0).findViewById(R.id.btnGallery);
      //  btnNote = mListView.getChildAt(0).findViewById(R.id.btnNote);
        btnPolicy = mListView.getChildAt(0).findViewById(R.id.btnPolicy);
        btnPassenger = mListView.getChildAt(0).findViewById(R.id.btnPassenger);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnLightning)
                        .setPrimaryText(res.getString(R.string.fast_add_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnCopy)
                        .setPrimaryText(res.getString(R.string.btn_copy_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.to_update_select_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                    .setTarget(btnMedia)
                    .setPrimaryText(res.getString(R.string.multi_media_menu_helpa))
                    .setSecondaryText(res.getString(R.string.got_it))
                    .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnPolicy)

                        .setPrimaryText(res.getString(R.string.subject_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnPassenger)

                        .setPrimaryText(res.getString(R.string.btn_passenger))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
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
     // Just Pick
    public void showSequence3(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();

        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.select_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
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
    public void showSequence4(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        btnDelete = mListView.getChildAt(0).findViewById(R.id.btnDelete);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )



                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
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
    public void showSequence5(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        btnDelete = mListView.getChildAt(0).findViewById(R.id.btnDelete);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.select_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
                        .setTarget(btnDelete)

                        .setPrimaryText(res.getString(R.string.remove_from_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedVehicle.this)
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
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
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

        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        mPersistenceObjDao.updateData("PERSIST_IP_ID", "");
        mPersistenceObjDao.updateData("PERSIST_MULTI_MEDIA_CALLER", "");

        mPersistenceObjDao.updateData("PERSIST_IV_ID", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_01", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_02","");
        mPersistenceObjDao.updateData("PERSIST_TEMP_03", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_04", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_05", "");
        if (rsMode.equals("LIST_INVOLVED_MENU")) {
            doClose();
            Intent intent = new Intent(this,  ListInvolvedMenu.class);
            startActivity(intent);
        }
        if (rsMode.equals("ACCIDENT_MENU")) {
            doClose();
            Intent intent = new Intent(this, AccidentMenu.class);
            startActivity(intent);
        }
        if (rsMode.equals("SELECT_NOTE_ATTACHMENT")) {
            doClose();
            Intent intent = new Intent(this, SelectNoteAttachment.class);
            startActivity(intent);
        }
        if (rsMode.equals("LIST_INSURED_VEHICLES")) {
            doClose();
            Intent intent = new Intent(this, ListVehicleCoverage.class);
            startActivity(intent);
        }
        if (rsMode.equals("LIST_INVOLVED_PARTY")) {
            doClose();
            intent = new Intent(this, ListInvolvedParty.class);
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
        mVehicleManifestDao.closeAll();
        mInvolvedVehicleDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

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
         switch (rsMode) {
                     case "LIST_INVOLVED_PARTY": {
                         if (involvedvehicleList.isEmpty()) {
                             showSequence4(view);
                         } else {
                             showSequence5(view);
                         }
                         break;
                     }

                     case "SELECT_NOTE_ATTACHMENT": {
                         if (involvedvehicleList.isEmpty()) {
                             showSequence1(view);
                         } else {
                             showSequence3(view);
                         }
                         break;
                     }

             case "LIST_INSURED_VEHICLES": {
                 if (involvedvehicleList.isEmpty()) {
                     showSequence1(view);
                 } else {
                     showSequence3(view);
                 }
                 break;
             }
         			default: {
                        if (involvedvehicleList.isEmpty()) {
                            showSequence1(view);
                        } else {
                            showSequence2(view);
                        }
         			break;
         			}
         			}


    }
    private void scheduleDoLightning() {
        Handler handler = new Handler();
        handler.postDelayed(this::doLightning, 250);
    }
    private void doLightning() {

                String IV_TAG = "";
                String IV_STATE = "";
                String IV_EXPIRATION = "";
                String IV_VIN  = "";
                String IV_YEAR = "";
                String IV_MAKE = "";

                String IV_MODEL = "";

                int IV_CUID = DUX_ID;
                String IV_CDATE = timeStamp;
                int IV_UUID = DUX_ID;
                String IV_UDATE = timeStamp;

                String IV_TYPE =  res.getString(R.string.other_value);
                String IV_PLATE_COUNTRY = "";
                AddData(IV_AID, IV_TAG, IV_STATE, IV_EXPIRATION,
                        IV_VIN, IV_YEAR, IV_MAKE, IV_MODEL, IV_TYPE, IV_PLATE_COUNTRY);
                int IVID = (int) insertData;
                String DA_ID = Integer.toString(IVID);
                IV_MODEL = res.getString(R.string.fast_add_vehicle_str) + DA_ID;
                mInvolvedVehicleDao.updateDataModel(IVID, IV_AID, IV_MODEL);
                AddManifestData(IV_AID, IVID);
                doClose();
                intent = new Intent(this, ListInvolvedVehicle.class);
                startActivity(intent);

            }
    private void AddData(Integer IV_AID, String IV_TAG, String IV_STATE, String IV_EXPIRATION, String IV_VIN,
                         String IV_YEAR, String IV_MAKE, String IV_MODEL, String IV_TYPE, String IV_PLATE_COUNTRY) {
        insertData = mInvolvedVehicleDao.addData(IV_AID, IV_TAG, IV_STATE, IV_EXPIRATION,
                IV_VIN, IV_YEAR, IV_MAKE, IV_MODEL, IV_TYPE, IV_PLATE_COUNTRY);

    }
    private void AddManifestData(Integer VMAID_ID, Integer VMIV_ID) {

        insertData = mVehicleManifestDao.addData(VMAID_ID, VMIV_ID, 0);
        // Add Test Comment
        // Add Second Comment

    }


}
