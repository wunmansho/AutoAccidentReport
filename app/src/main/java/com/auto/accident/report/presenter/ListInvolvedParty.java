package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.InvolvedPartyDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.models.VehicleManifestDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.InvolvedParty;
import com.auto.accident.report.objects.PersistenceObj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 1/15/2018.
 */

public class ListInvolvedParty extends AppCompatActivity {
    private static final String TAG = "ListInvolvedParty";

    //  InvolvedPartyDao mInvolvedPartyDao;
    private PersistenceObjDao mPersistenceObjDao;
    private ListView mListView;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnHelp;
    private FloatingActionButton btnCopy;
    private FloatingActionButton btnLightning;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private int IP_AID;
    private List<InvolvedParty> involvedpartyList;
    private Toolbar toolbar;
    private Resources res;
//    private FloatingActionButton btnDelete;
  //  private FloatingActionButton btnCamera;
  //  private FloatingActionButton btnGallery;
    private FloatingActionButton btnMedia;
    private FloatingActionButton btnPolicy;
    private FloatingActionButton btnViewVehicle;
   // private FloatingActionButton btnNote;
    private FloatingActionButton btnCall;
    private FloatingActionButton btnEmail;
    private FloatingActionButton btnSettings;
    private String did_play_ListInvolvedParty;
    private boolean doAnimation = true;
    private RelativeLayout rmvFromVehicle;
    private String DA_CALLER;
    private RotateAnimation rotateAnimation;
    private String rsMode;
    private boolean loaded;
    private PersistenceObj persistenceObj;
    private Intent intent;
    private boolean ActionInProgress;
    private int daAxe = 0;
    private Drawer mDrawer;
    private PrimaryDrawerItem pitem1;
    private PrimaryDrawerItem pitem2;
    private PrimaryDrawerItem pitem3;
    private PrimaryDrawerItem pitem4;
    private Context context;
    private RecyclerView mRecyclerView;
    private String DID_OPEN_INVOLVED_PARTY_DRAWER;
    private View view;
    private static final String configLocale = Locale.getDefault().getCountry().toLowerCase();
    private static final String KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = "KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON";
    private static final String KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = "KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE";
    private static final String KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = "KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY";
    private static final String KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = "KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT";
    private static final String KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT = "KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT";
    private static final String KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = "KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER";
    private String REMOTE_CONFIG_KEY;
    private String REMOTE_CONFIG_VALUE;
    private String REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE;
    private String REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE;
    private String REMOTE_CONFIG_ALLOW_EMAIL_CONTACT;
    private String REMOTE_CONFIG_ALLOW_PHONE_CONTACT;
    private String REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL;
    private String REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER;
    private String REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE;
    private String REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON_TEXT1;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON_TEXT2;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON_TEXT3;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON_TEXT1;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON_TEXT2;
    private String REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY;
    private int DUX_ID;
    private String DA_ID_STR;
    private String DU_FNAME;
    private InvolvedPartyDao mInvolvedPartyDao;
    private VehicleManifestDao mVehicleManifestDao;
    private long insertData;
    private String timeStamp;
    private String PERSIST_ACTION_IN_PROGRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_involved_party);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        btnSettings = findViewById(R.id.btnSettings);
        btnCopy = findViewById(R.id.btnCopy);
        btnAdd = findViewById(R.id.btnAdd);
        btnHelp = findViewById(R.id.btnHelp);
        btnLightning = findViewById(R.id.btnLightning);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        mInvolvedPartyDao = new InvolvedPartyDao(this);
        mVehicleManifestDao = new VehicleManifestDao(this);
        loaded = false;
        ActionInProgress = false;
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");
        persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DUX_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DUX_ID = 0;
        }
        DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        DU_FNAME = deviceUser.getDU_FNAME();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];
        getConfig();
        res = getResources();
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.sip));
        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE") || DA_CALLER.equals("LIST_INSURED_PEOPLE")|| DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
            btnCopy.setVisibility(View.GONE);
            btnAdd.hide();
            btnLightning.setVisibility(View.GONE);
        }
        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE")) {
            //   btnCopy.setVisibility(View.GONE);
            //   btnAdd.hide();
            toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.svo));
        }
        if (DA_CALLER.equals("LIST_INSURED_PEOPLE")) {
            //  btnCopy.setVisibility(View.GONE);
            //   btnAdd.hide();
            toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.add_insured));
        }
        if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {
            //     btnCopy.setVisibility(View.GONE);
            //    btnAdd.hide();
            toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.sna));
        }
        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
            //     btnCopy.setVisibility(View.GONE);
            //    btnAdd.hide();
            toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.select_policy_holder));
        }


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(ACC_NUM)) {
            IP_AID = Integer.parseInt(ACC_NUM);
        } else {
            IP_AID = 0;
        }

        InvolvedPartyDao mInvolvedPartyDao = new InvolvedPartyDao(this);
        involvedpartyList = new ArrayList<>();
        involvedpartyList = mInvolvedPartyDao.getAllInvolvedParties(IP_AID);
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);

        toolbar.setNavigationOnClickListener(view -> {
            context = view.getContext();

            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_CALLER");
            rsMode = persistenceObj.getPERSISTENCE_VALUE();
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            btnBack.startAnimation(rotateAnimation);

            scheduleDismissToolbar();

        });
        btnSettings.setOnClickListener(view -> {

            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(300);

                scheduleDismissBtnSettings();
                btnSettings.startAnimation(rotateAnimation);
            }
        });

        btnHelp.setOnClickListener(view -> {

            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                context = view.getContext();
            if (ActionInProgress == false) {
                       if (doAnimation == true) {
                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "true");
                           ActionInProgress = true;
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnHelp.startAnimation(rotateAnimation);
                    scheduleDoHelp();

                }
            }
            }
        });
        if (!involvedpartyList.isEmpty()) {
            mPersistenceObjDao.updateData("did_play_ListInvolvedParty", "true");
        }
        if (involvedpartyList.isEmpty()) {
            persistenceObj = mPersistenceObjDao.getPersistence("did_play_ListInvolvedParty");
            did_play_ListInvolvedParty = persistenceObj.getPERSISTENCE_VALUE();
            if (!did_play_ListInvolvedParty.equals("true")) {
                doAnimation = false;
     //        btnHelp.callOnClick();
                mPersistenceObjDao.updateData("did_play_ListInvolvedParty", "true");
            }

        }


        btnCopy.setOnClickListener(view -> {
            if (fireClick == true) {

                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false")) {

                    mPersistenceObjDao.updateData("PERSIST_DU_MODE", "SELECT_PROFILE");
                    mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "LIST_INVOLVED_PARTY");
                    context = view.getContext();
                    intent = new Intent(context, ListDeviceUser.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnCopy.startAnimation(rotateAnimation);
                    scheduleDismissIntent();
                    //   Intent intent = new Intent(context, ListPartyType.class);
                    // context.startActivity(intent);
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
                message = res.getString(R.string.tv0144);
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
                    intent = new Intent(context, AddInvolvedParty.class);
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
                message = res.getString(R.string.tv0148);
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
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnLightning.startAnimation(rotateAnimation);
                    scheduleDoLightning();
                }
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

                message = res.getString(R.string.tv0149);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);

                mdToast.show();

                fireClick = false;
            }
            return false;

        });
  //      mDrawer = new DrawerBuilder()
    //            .withActivity(this)


    //            .withTranslucentStatusBar(true)
    //            .withTranslucentNavigationBar(true)
     //           .withActionBarDrawerToggle(true)
      //          .withActionBarDrawerToggleAnimated(true)



         //       .addDrawerItems(

           //             new DividerDrawerItem(),
         //               new DividerDrawerItem(),
         //               new PrimaryDrawerItem().withName(R.string.app_name),
          //              new DividerDrawerItem(),
         //               new DividerDrawerItem(),
        //                pitem1 = new PrimaryDrawerItem().withName("Pics, Video, Rec Voice, Rec Notes.").withIcon(R.drawable.number1_24dp).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),

           //             new DividerDrawerItem(),

              //          pitem2 = new PrimaryDrawerItem().withName("Insurance, Vehicle, Contact Detail.").withIcon(R.drawable.number2_24dp).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
             //           new DividerDrawerItem(),

               //         pitem2 = new PrimaryDrawerItem().withName("Call, Email, Accident Contacts.").withIcon(R.drawable.number3_24dp).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                 //       new DividerDrawerItem(),

                 //        pitem3 = new PrimaryDrawerItem().withName("Advanced level, All Options.").withIcon(FontAwesome.Icon.faw_thumbs_up).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),

            //            new DividerDrawerItem(),

              //          pitem4 = new PrimaryDrawerItem().withName("Advanced level, All Options.").withIcon(FontAwesome.Icon.faw_thumbs_up).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)


             //   ) // add the items we want to use with our Drawer
              //  .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
               //     @Override
               //     public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                //        context = view.getContext();                        //  Toast.makeText( ListInvolvedParty.this, ((Nameable) drawerItem).getName().getText( ListInvolvedParty.this), Toast.LENGTH_SHORT).show();
                 //       switch (position) {
                    //        case 0:
                                //  Intent intent = new Intent(FullScreenImageActivity.this, PhotoGalleryActivity.class);
                                //  context.startActivity(intent);
                      //          break;
                     //       case 2:

                   //             break;
                   //         case 4:

                     //           break;


                        //    case 6:
                          //      showNavSequence1(viewGroup);

                            //    break;
                            //we do not consume the event and want the Drawer to continue with the event chain
                      //  }
                      //  return false;
                   // }
               // })

               // .withSavedInstance(savedInstanceState)
                //.withCloseOnClick(false)
               // .withDrawerGravity(Gravity.END)
              //  .build();

        showListView();

       persistenceObj = mPersistenceObjDao.getPersistence("DID_OPEN_INVOLVED_PARTY_DRAWER");
        DID_OPEN_INVOLVED_PARTY_DRAWER = persistenceObj.getPERSISTENCE_VALUE();
if (DID_OPEN_INVOLVED_PARTY_DRAWER.equals("false")) {
  //  mDrawer.openDrawer();
  //  mPersistenceObjDao.updateData("DID_OPEN_INVOLVED_PARTY_DRAWER", "true");
}

           }

    private void showListView() {

        mListView = findViewById(R.id.listView);
        //set an onItemClickListener to the ListView
        mListView.setAdapter(new ListInvolvedPartyAdapter(this));
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


        btnCopy.setEnabled(false);

     //   btnDelete.setEnabled(false);


        btnHelp.setEnabled(false);
        btnLightning.setEnabled(false);



    }
    public void enableButtons() {


        btnAdd.setEnabled(true);


        btnCopy.setEnabled(true);


        btnHelp.setEnabled(true);
        btnLightning.setEnabled(true);


    }
    public void showSequence0(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        disableButtons();
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
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
                                    ActionInProgress = false;
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        }))


                .show();
    }

    public void showSequence1(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        disableButtons();
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.plus_icon_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnLightning)
                        .setPrimaryText(res.getString(R.string.fast_add_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnCopy)
                        .setPrimaryText(res.getString(R.string.btn_copy_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
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
                                    ActionInProgress = false;
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        }))


                .show();
    }

    public void showSequence2(View view) {
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        String DA_ROLE =  res.getString(R.string.select_involved_person);

        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
            DA_ROLE = res.getString(R.string.press_to_select_policy_holder);
        }
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )



                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(DA_ROLE)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
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
                                    ActionInProgress = false;
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        }))


                .show();

    }

    public void showSequenceNoCallNoEmail(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
       // btnCamera = mListView.getChildAt(0).findViewById(R.id.btnCamera);
       // btnGallery = mListView.getChildAt(0).findViewById(R.id.btnGallery);
       // btnNote = mListView.getChildAt(0).findViewById(R.id.btnNote);
        btnPolicy = mListView.getChildAt(0).findViewById(R.id.btnPolicy);
        btnMedia = mListView.getChildAt(0).findViewById(R.id.btnMedia);


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.plus_icon_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnLightning)
                        .setPrimaryText(res.getString(R.string.fast_add_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnCopy)
                        .setPrimaryText(res.getString(R.string.btn_copy_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.select_involved_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnMedia)

                        .setPrimaryText(res.getString(R.string.multi_media_menu_helpb))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnPolicy)

                        .setPrimaryText(res.getString(R.string.subject_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnViewVehicle)
                        .setPrimaryText(res.getString(R.string.associate_with_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
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
                                    ActionInProgress = false;
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        }))


                .show();

    }
    public void showSequenceCallEmail(View view) {
        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        btnPolicy = mListView.getChildAt(0).findViewById(R.id.btnPolicy);
        btnMedia= mListView.getChildAt(0).findViewById(R.id.btnMedia);
        btnViewVehicle = mListView.getChildAt(0).findViewById(R.id.btnViewVehicle);
        btnCall = mListView.getChildAt(0).findViewById(R.id.btnCall);
        btnEmail = mListView.getChildAt(0).findViewById(R.id.btnEmail);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.plus_icon_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnLightning)
                        .setPrimaryText(res.getString(R.string.fast_add_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnCopy)
                        .setPrimaryText(res.getString(R.string.btn_copy_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.select_involved_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnMedia)

                        .setPrimaryText(res.getString(R.string.multi_media_menu_helpb))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnPolicy)

                        .setPrimaryText(res.getString(R.string.subject_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnViewVehicle)
                        .setPrimaryText(res.getString(R.string.associate_with_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnCall)

                        .setPrimaryText(res.getString(R.string.call_this_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnEmail)

                        .setPrimaryText(res.getString(R.string.email_this_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
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
                                    ActionInProgress = false;
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        }))



                .show();

    }
    public void showSequenceCallNoEmail(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        btnPolicy = mListView.getChildAt(0).findViewById(R.id.btnPolicy);
        btnMedia= mListView.getChildAt(0).findViewById(R.id.btnMedia);
        btnViewVehicle = findViewById(R.id.btnViewVehicle);
        btnCall = findViewById(R.id.btnCall);
        btnEmail = findViewById(R.id.btnEmail);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.plus_icon_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnLightning)
                        .setPrimaryText(res.getString(R.string.fast_add_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnCopy)
                        .setPrimaryText(res.getString(R.string.btn_copy_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.select_involved_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnMedia)

                        .setPrimaryText(res.getString(R.string.multi_media_menu_helpb))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnPolicy)

                        .setPrimaryText(res.getString(R.string.subject_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnViewVehicle)
                        .setPrimaryText(res.getString(R.string.associate_with_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnCall)

                        .setPrimaryText(res.getString(R.string.call_this_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
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
                                    ActionInProgress = false;
                                    enableButtons();
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        })
                )


                .show();

    }
    public void showSequenceNoCallEmail(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        btnPolicy = mListView.getChildAt(0).findViewById(R.id.btnPolicy);
        btnMedia= mListView.getChildAt(0).findViewById(R.id.btnMedia);
        btnViewVehicle = findViewById(R.id.btnViewVehicle);
        btnCall = findViewById(R.id.btnCall);
        btnEmail = findViewById(R.id.btnEmail);
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.plus_icon_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnLightning)
                        .setPrimaryText(res.getString(R.string.fast_add_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnCopy)
                        .setPrimaryText(res.getString(R.string.btn_copy_involved_party))
                        .setSecondaryText(res.getString(R.string.got_it))

                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(mListView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.select_involved_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnMedia)

                        .setPrimaryText(res.getString(R.string.multi_media_menu_helpb))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnPolicy)

                        .setPrimaryText(res.getString(R.string.subject_insurance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnViewVehicle)
                        .setPrimaryText(res.getString(R.string.associate_with_involved_vehicle))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
                        .setTarget(btnEmail)

                        .setPrimaryText(res.getString(R.string.email_this_person))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(ListInvolvedParty.this)
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
                                    ActionInProgress = false;
                                    mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

                                }
                            }
                        })
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    ActionInProgress = false;
                                    enableButtons();
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

    private void scheduleDismissBtnSettings() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissBtnSettings, 250);
    }
    private void dismissBtnSettings() {

        mDrawer.openDrawer();
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
         switch (rsMode) {
                     case "LIST_INVOLVED_MENU": {
                         doClose();
                         Intent intent = new Intent(this,  ListInvolvedMenu.class);
                         startActivity(intent);
                         break;
                     }

                     case "ACCIDENT_MENU": {
                         doClose();
                         Intent intent = new Intent(this, AccidentMenu.class);
                         startActivity(intent);
                         break;
                     }
                     case "LIST_INVOLVED_VEHICLE": {
                         doClose();
                         Intent intent = new Intent(this, ListInvolvedVehicle.class);
                         startActivity(intent);
                         break;
                     }
                     case "LIST_INSURED_PEOPLE": {
                         mPersistenceObjDao.updateData("PERSIST_IP_CALLER", "INVOLVED_MENU");
                         doClose();
                         Intent intent = new Intent(this, ListInsuredPeople.class);
                         startActivity(intent);
                         break;
                     }
                     case "SELECT_NOTE_ATTACHMENT": {
                         doClose();
                         Intent intent = new Intent(this, SelectNoteAttachment.class);
                         startActivity(intent);
                         break;
                     }
                     case "LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY": {
                         doClose();
                         Intent intent = new Intent(this, ListInsurancePolicy.class);
                         startActivity(intent);
                         break;
                     }
         			default: {
                        doClose();
                        Intent intent = new Intent(this, ListInvolvedMenu.class);
                        startActivity(intent);
         			break;
         			}
         			}








    }
    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 250);
    }
    private void dismissIntent() {
        doClose();
        this.startActivity(intent);
    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
        mVehicleManifestDao.closeAll();
        mInvolvedPartyDao.closeAll();
      //  mVehicleManifestDao.closeAll();
    }
    @Override
    public void onBackPressed() {

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        View btnBack = toolbar.getChildAt(2);
        btnBack.startAnimation(rotateAnimation);

        scheduleDismissToolbar();
    }
    public void showNavSequence1(View view) {

        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();


        int xx = cc;
        // important - repeat sequence 0 twice
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder( ListInvolvedParty.this)
                        .setTarget(mRecyclerView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.action_settings_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder( ListInvolvedParty.this)
                        .setTarget(mRecyclerView.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.action_download_offline_language_libraries_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder( ListInvolvedParty.this)
                        .setTarget(mRecyclerView.getChildAt(4))

                        .setPrimaryText(res.getString(R.string.user_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder( ListInvolvedParty.this)
                        .setTarget(mRecyclerView.getChildAt(6))

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .show();

    }
    private void scheduleDoLightning() {
        Handler handler = new Handler();
        handler.postDelayed(this::doLightning, 200);
    }
    private void doLightning() {
        String IP_PTYPE = res.getString(R.string.default_key) + " - " + res.getString(R.string.default_value);
        String IP_FNAME = "";
        String IP_MI = "";
        String IP_LNAME = "";
        String IP_LICENSE = "";
        String IP_LST = "";
        String IP_ADDR1 = "";
        String IP_ADDR2 = "";
        String IP_CITY = "";
        String IP_ST = "";
        String IP_ZIP = "";
        String IP_EMAIL ="";
        String IP_PHON1 = "";
        String IP_PHON2 = "";
        String IP_PHON3 = "";
        String IP_CNAM01 = "";
        String IP_PNUM01 = "";
        String IP_CNAM02 = "";
        String IP_PNUM02 = "";
        String IP_CNAM03 = "";
        String IP_PNUM03 = "";
        String IP_COMP = "";

          int  IP_CUID = DUX_ID;

        timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        String IP_CDATE = timeStamp;

        int    IP_UUID = DUX_ID;

        String IP_UDATE = timeStamp;
        String IP_LICENSE_COUNTRY = "";
        String IP_RESIDENT_COUNTRY = "";
        String IP_PHON1_COUNTRY = "";
        String IP_PHON2_COUNTRY = "";
        String IP_PHON3_COUNTRY = "";
        AddData(IP_AID, IP_FNAME, IP_MI, IP_LNAME, IP_LICENSE, IP_LST,
                IP_ADDR1, IP_ADDR2, IP_CITY, IP_ST, IP_ZIP, IP_EMAIL, IP_PHON1, IP_PHON2,
                IP_PHON3, IP_PTYPE, IP_CNAM01, IP_PNUM01, IP_CNAM02, IP_PNUM02, IP_CNAM03,
                IP_PNUM03, IP_COMP, IP_LICENSE_COUNTRY, IP_RESIDENT_COUNTRY,
                IP_PHON1_COUNTRY, IP_PHON2_COUNTRY, IP_PHON3_COUNTRY);
        int IPID = (int) insertData;
        String DA_ID = Integer.toString(IPID);
        IP_FNAME = res.getString(R.string.fast_add_person_str) + DA_ID;
        mInvolvedPartyDao.updateDataName(IPID, IP_AID, IP_FNAME);
        AddManifestData(IP_AID, IPID);
        intent = new Intent(this, ListInvolvedParty.class);
        startActivity(intent);

    }
    private void AddData(Integer IP_AID, String IP_FNAME, String IP_MI, String IP_LNAME, String IP_LICENSE, String IP_LST, String IP_ADDR1,
                         String IP_ADDR2, String IP_CITY, String IP_ST, String IP_ZIP, String IP_EMAIL,
                         String IP_PHON1, String IP_PHON2, String IP_PHON3, String IP_PTYPE,
                         String IP_CNAM01, String IP_PNUM01, String IP_CNAM02, String IP_PNUM02,
                         String IP_CNAM03, String IP_PNUM03, String IP_COMP, String IP_LICENSE_COUNTRY,
                         String IP_RESIDENT_COUNTRY, String IP_PHON1_COUNTRY, String IP_PHON2_COUNTRY,
                         String IP_PHON3_COUNTRY) {
        insertData = mInvolvedPartyDao.addData(IP_AID, IP_FNAME, IP_MI, IP_LNAME, IP_LICENSE, IP_LST,
                IP_ADDR1, IP_ADDR2, IP_CITY, IP_ST, IP_ZIP, IP_EMAIL, IP_PHON1, IP_PHON2,
                IP_PHON3, IP_PTYPE, IP_CNAM01, IP_PNUM01, IP_CNAM02, IP_PNUM02, IP_CNAM03,
                IP_PNUM03, IP_COMP, IP_LICENSE_COUNTRY, IP_RESIDENT_COUNTRY, IP_PHON1_COUNTRY,
                IP_PHON2_COUNTRY, IP_PHON3_COUNTRY);


    }
    private void AddManifestData(Integer VMAID_ID, Integer VMIP_ID) {

        insertData = mVehicleManifestDao.addData(VMAID_ID, 0, VMIP_ID);
        // Add Test Comment
        // Add Second Comment

    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }
    private void doHelp() {
        doAnimation = true;
        int listViewItemsCount = mListView.getChildCount();

        if (listViewItemsCount == 0) {
            switch (DA_CALLER) {
                case "LIST_INVOLVED_VEHICLE": {
                    showSequence0(view);
                    break;
                }
                case "LIST_INSURED_PEOPLE": {
                    showSequence0(view);
                    break;
                }
                case "SELECT_NOTE_ATTACHMENT": {
                    showSequence0(view);
                    break;
                }
                case "LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY": {
                    showSequence0(view);
                    break;
                }

                default: {
                    showSequence1(view);
                    break;
                }

            }

        } else {
            //  if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE") || DA_CALLER.equals("LIST_INSURED_PEOPLE")|| DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
            switch (DA_CALLER) {
                case "LIST_INVOLVED_VEHICLE": {
                    showSequence2(view);
                    break;
                }
                case "LIST_INSURED_PEOPLE": {
                    showSequence2(view);
                    break;
                }
                case "SELECT_NOTE_ATTACHMENT": {
                    showSequence2(view);
                    break;
                }
                case "LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY": {
                    showSequence2(view);
                    break;
                }

                default: {
                    rmvFromVehicle = findViewById(R.id.rl01);
                    if (REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("true") && REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("true")) {
                        //  if (rmvFromVehicle.getVisibility() == VISIBLE) {
                        showSequenceCallEmail(view);
                    }
                    if (REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("false") && REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("true")) {
                        //  if (rmvFromVehicle.getVisibility() == VISIBLE) {
                        showSequenceNoCallEmail(view);
                    }
                    if (REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("true") && REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("false")) {
                        //  if (rmvFromVehicle.getVisibility() == VISIBLE) {
                        showSequenceCallNoEmail(view);
                    }
                    if (REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("false") && REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("false")) {
                        //  if (rmvFromVehicle.getVisibility() == VISIBLE) {
                        showSequenceNoCallNoEmail(view);
                    }
                    break;
                }

            }
            if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE")) {
                //   btnCopy.setVisibility(View.GONE);
                //   btnAdd.hide();
                toolbar.setSubtitle(getString(R.string.welcome) + " " + DU_FNAME + " - " + getString(R.string.svo));
            }
            if (DA_CALLER.equals("LIST_INSURED_PEOPLE")) {
                //  btnCopy.setVisibility(View.GONE);
                //   btnAdd.hide();
                toolbar.setSubtitle(getString(R.string.welcome) + " " + DU_FNAME + " - " + getString(R.string.add_insured));
            }
            if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {
                //     btnCopy.setVisibility(View.GONE);
                //    btnAdd.hide();
                toolbar.setSubtitle(getString(R.string.welcome) + " " + DU_FNAME + " - " + getString(R.string.sna));
            }

        }
    }
    public void getConfig() {

        REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON);
        REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON);


        //    REMOTE_CONFIG_KEY = "affiliate_search_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE_address";
        REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE);


        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_email";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE);


        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_call";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_phone_number";
        REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE);

        //REMOTE_CONFIG_KEY = "search_internet_for_attorney_only";
        REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY);

        //  REMOTE_CONFIG_KEY = "allow_email_contact";
        REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT);

        //  REMOTE_CONFIG_KEY = "allow_phone_contact";
        REMOTE_CONFIG_ALLOW_PHONE_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT);

        //	REMOTE_CONFIG_KEY = "default_referral_phone_not_email";
        REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL);

        //	REMOTE_CONFIG_KEY = "affiliate_search_show_phone_number";
        REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER);
    }

    public String getPersistantRemoteConfig(String PERSISTENCE_KEY) {

        persistenceObj = mPersistenceObjDao.getPersistence(PERSISTENCE_KEY);
        String PERSISTENCE_VALUE = persistenceObj.getPERSISTENCE_VALUE();
        return PERSISTENCE_VALUE;
    }


}
