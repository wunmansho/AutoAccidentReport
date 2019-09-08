package com.auto.accident.report.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.AccidentIdDao;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.AccidentId;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;
import java.util.List;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;

public class ListAccident extends AppCompatActivity {

    private static final String TAG = "ListAccident";

    private ListView mListView;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnHelp;
 //   private FloatingActionButton btnLightning;
    private String stAID_MODE;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Toolbar toolbar;
    private List<AccidentId> accidentidList;
    private Resources res;
    private FloatingActionButton btnMedia;
   // private FloatingActionButton btnCamera;
   // private FloatingActionButton btnGallery;
    private FloatingActionButton btnEdit;
    private FloatingActionButton btnNote;
    private String did_play_ListAccident;
    private RotateAnimation rotateAnimation;
    private AccidentIdDao mAccidentIdDao;
    private DeviceUserDao mDeviceUserDao;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private DeviceUser deviceUser;
    private boolean loaded;
    private Intent intent;
    private int daAxe = 0;
    private View view;
    private int helpSequence;
    private  Context context;
    private String DA_ID_STR;
    private int DUX_ID;
    private String PERSIST_ACTION_IN_PROGRESS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_accident);
        mListView = findViewById(R.id.list_accident_listview);
        btnAdd = findViewById(R.id.btnAdd);
        btnHelp = findViewById(R.id.btnHelp);
   //     btnLightning = findViewById(R.id.btnLightning);

        //    mImageView = (ImageView) findViewById(R.id.ImageView);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //   mListView = (ListView) findViewById(R.id.list_accident_listview);
        mAccidentIdDao = new AccidentIdDao(this);
        accidentidList = new ArrayList<>();
        accidentidList = mAccidentIdDao.getAllAccidentIds();
        mDeviceUserDao = new DeviceUserDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
        mPersistenceObjDao.updateData("PERSIST_IP_ID", "0");
        mPersistenceObjDao.updateData("PERSIST_MULTI_MEDIA_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_COMPANY_NAME", "");
        mPersistenceObjDao.updateData("PERSIST_IV_ID", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_01", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_02","");
        mPersistenceObjDao.updateData("PERSIST_TEMP_03", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_04", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_05", "");
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        res = getResources();
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
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.said));
        toolbar.setNavigationOnClickListener(view -> {
            context = view.getContext();
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

			 mPersistenceObjDao.updateData("PERSIST_DU_MODE", "SELECT");
            mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU");
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
        if (!accidentidList.isEmpty()) {
            mPersistenceObjDao.updateData("did_play_ListAccident", "true");
        }
        if (accidentidList.isEmpty()) {
            persistenceObj = mPersistenceObjDao.getPersistence("did_play_ListAccident");
            did_play_ListAccident = persistenceObj.getPERSISTENCE_VALUE();
            // if (!did_play_ListAccident.equals("true")) {
            //         btnHelp.callOnClick();
            //        mPersistenceObjDao.updateData("did_play_ListAccident", "true");
            //    }

        }

        btnAdd.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnAdd.startAnimation(rotateAnimation);
                intent = new Intent(context, AddAccident.class);
                scheduleDismissIntent();

            }
            fireClick = true;
            btnAdd.setImageAlpha(alpha1);
        });
        btnAdd.setOnLongClickListener(view -> {

            btnAdd.setImageAlpha(alpha2);
            context = view.getContext();
            Resources res = getResources();
            message = res.getString(R.string.tv0153);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);

            mdToast.show();

            fireClick = false;

            return false;

        });
      //  btnLightning.setOnClickListener(view -> {
      //      if (fireClick == true) {
     //           context = view.getContext();
      //          rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
      //          rotateAnimation.setRepeatCount(1);
       //         rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
       //         rotateAnimation.setDuration(100);
       //         btnLightning.startAnimation(rotateAnimation);

              //  scheduleDismissIntent();
                //context.startActivity(intent);
         //   }
         //   fireClick = true;
         //   btnLightning.setImageAlpha(alpha1);
       // });
      //  btnLightning.setOnLongClickListener(view -> {

     //       btnLightning.setImageAlpha(alpha2);
     //       context = view.getContext();
     //       Resources res = getResources();
     //       message = res.getString(R.string.tv0153);
      //      duration = 20;
     //       type = 0;
      //      MDToast mdToast = MDToast.makeText(context, message, duration, type);
      //      mdToast.setGravity(Gravity.TOP, 50, 200);

     //       mdToast.show();

      //      fireClick = false;

      //      return false;

     //   });



        showListView();
    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }
    private void doHelp() {
        if (accidentidList.isEmpty()) {
            showSequence1(view);
        } else {
            showSequence2(view);
        }
    }
    private void showListView() {
        //  Log.d(TAG, "showListView: Displaying data in the ListView.");

        //get the data and append to a list

        // ListView mListView = findViewById(R.id.list_accident_listview);
        Log.i("ListAccident 186", "startListAccidentAdapter");
        mListView.setAdapter(new ListAccidentAdapter(this));

        //set an onItemClickListener to the ListView

    }
    public void disableButtons() {
          //     btnEdit = mListView.getChildAt(0).findViewById(R.id.btnEdit);
     //   btnMedia = mListView.getChildAt(0).findViewById(R.id.btnMedia);
        btnAdd.setEnabled(false);
        btnHelp.setEnabled(false);
      }
    public void enableButtons() {
      //  btnEdit = mListView.getChildAt(0).findViewById(R.id.btnEdit);
       // btnMedia = mListView.getChildAt(0).findViewById(R.id.btnMedia);

    //   btnEdit = mListView.getChildAt(0).findViewById(R.id.btnEdit);
              btnAdd.setEnabled(true);
       //     btnEdit.setEnabled(true);
         btnHelp.setEnabled(true);
      //   btnMedia.setEnabled(true);


    }
    public void showSequence1(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
         disableButtons();
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_accident_first_time))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
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
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");
                                    enableButtons();
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
        btnEdit = mListView.getChildAt(0).findViewById(R.id.btnEdit);
        btnMedia = mListView.getChildAt(0).findViewById(R.id.btnMedia);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
                        .setTarget(btnAdd)
                        .setPrimaryText(res.getString(R.string.plus_icon_accident))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.to_procede_select_accident))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
                        .setTarget(btnMedia)

                        .setPrimaryText(res.getString(R.string.multi_media_menu_helpa))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
                        .setTarget(btnEdit)

                        .setPrimaryText(res.getString(R.string.edit_accident))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListAccident.this)
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
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");
                                    enableButtons();
                                }
                            }
                        }))


                .show();
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

    private void scheduleDismissToolbar() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }

    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();

        Intent intent = new Intent(this, ListDeviceUser.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
        mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();


    }

    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 200);
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


}


