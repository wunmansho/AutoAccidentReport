package com.auto.accident.report.presenter;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.auto.accident.report.R;
import com.auto.accident.report.about.AboutActivity;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.models.AccidentIdDao;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.AccidentId;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.showInstallOfflineVoiceFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.auto.accident.report.util.utils.getPersistantRemoteConfig;
import static com.auto.accident.report.util.utils.getRemoteConfig;

//import com.auto.accident.report.models.PersistenceObjTestDao;


/**
 * Created by User on 2/28/2017.
 */

public class AccidentMenu extends AppCompatActivity {
    public static final String CSPROFESSIONAL_ID = "SPROFESSIONAL_ID";
    public static final String CSPDU_ID = "SPDU_ID";
    public static final String CSPDU_FNAME = "SPDU_FNAME";
    public static final String CSPDU_MI = "SPDU_MI";
    public static final String CSPDU_LNAME = "SPDU_LNAME";
    public static final String CSPDU_PTYPE = "SPDU_PTYPE";
    public static final String CSPDU_CNAM01 = "SPDU_CNAM01";
    public static final String CSPDU_PNUM01 = "SPDU_PNUM01";
    public static final String CSPDU_CNAM02 = "SPDU_CNAM02";
    public static final String CSPDU_PNUM02 = "SPDU_PNUM02";
    public static final String CSPDU_CNAM03 = "SPDU_CNAM03";
    public static final String CSPDU_PNUM03 = "SPDU_PNUM03";
    public static final String CSPDU_COMP = "SPDU_COMP";
    public static final String CSPDU_CUID = "SPDU_CUID";
    public static final String CSPDU_CDATE = "SPDU_CDATE";
    public static final String CSPDU_UUID = "SPDU_UUID";
    public static final String CSPDU_UDATE = "SPDU_UDATE";
    private static final String TAG = "AccidentMenu";
    private PersistenceObjDao mPersistenceObjDao;
 //   private PersistenceObjTestDao mPersistenceObjTestDao;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private ImageButton btnTow;
    private ImageButton btnLogo;


    private static final float METRIC_LDPI = 0.75f;
    private static final float METRIC_MDPI = 1.0f;
    private static final float METRIC_HDPI = 1.5f;
    private static final float METRIC_XHDPI = 2.0f;
    private static final float METRIC_XXHDPI = 3.0f;
    private static final float METRIC_XXXHDPI = 4.0f;

    private Drawer mDrawer;
    // remote config
    private static final String configLocale = Locale.getDefault().getCountry().toLowerCase();
  //  private static final String configLocale = "zl";
    private static final String KEY_REMOTE_CONFIG_SCAN = "REMOTE_CONFIG_SCAN";
    private static final String KEY_REMOTE_CONFIG_VIDEO_CALL = "REMOTE_CONFIG_VIDEO_CALL";
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
    private static final String KEY_DID_RETRIEVE_REMOTE_CONFIG = "KEY_DID_RETRIEVE_REMOTE_CONFIG";
    private static final String DEF_KEY_REMOTE_CONFIG_SCAN = "REMOTE_CONFIG_SCAN";
    private static final String DEF_KEY_REMOTE_CONFIG_VIDEO_CALL = "REMOTE_CONFIG_VIDEO_CALL";
    private static final String DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = "KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON";
    private static final String DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = "KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON";
    private static final String DEF_KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE";
    private static final String DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE";
    private static final String DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE";
    private static final String DEF_KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE";
    private static final String DEF_KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = "KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY";

    private static final String DEF_KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = "KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL";
    private static final String DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER";
    private static final String DEF_KEY_DID_RETRIEVE_REMOTE_CONFIG = "KEY_DID_RETRIEVE_REMOTE_CONFIG";

    private String DID_RETRIEVE_REMOTE_CONFIG;
    private String REMOTE_CONFIG_KEY;
    private String REMOTE_CONFIG_VALUE;
    private String REMOTE_CONFIG_SCAN;
    private String REMOTE_CONFIG_VIDEO_CALL;
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
    private String did_play_OpenAccidentMenuDrawer;
    // remote config
    private TextView textView4, tvBtnLowLeftText, tvBtnLowRightText;
    private List<AccidentId> accidentIdList;
    private List<DeviceUser> deviceuserList;
    private Resources res;
    private FloatingActionButton btnHelp;
    private ImageButton btnE911;
    private ImageButton concierge;
    private ImageButton btnFire;
    private ImageButton taxi_ride;
    private ImageButton btnLogo2;
    private ImageButton btnAccidentInfo;
    private String REMOTE_CONFIG_CLEAR_DID_PLAY;


    private PrimaryDrawerItem pitem1;
    private PrimaryDrawerItem pitem2;
    private PrimaryDrawerItem pitem3;
    private PrimaryDrawerItem pitem4;
    private PrimaryDrawerItem sitem1;
    private PrimaryDrawerItem sitem2;
    private PrimaryDrawerItem sitem3;
    private PrimaryDrawerItem sitem4;
    private PrimaryDrawerItem sitem5;
    private PrimaryDrawerItem sitem6;
    private PrimaryDrawerItem sitem7;
    private int DrawerItemSet;
    private RecyclerView mRecyclerView;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private String btnPushed;
    private String PHONE01;
    private String PHONE02;

    private String URL01;
    private String URL02;
    private PersistenceObj persistenceObj;
    private AccidentIdDao mAccidentIdDao;
    private Context context;
    private int helpSequence;
    private boolean ActionInProgress;
    private View view;
    private String PERSIST_ACTION_IN_PROGRESS;
    private Intent intent;
    private Drawer.OnDrawerListener onDrawerListener;
    // private ImageButton
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accident_menu);
        toolbar = findViewById(R.id.my_toolbar);
        // setSupportActionBar(toolbar);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        btnHelp = findViewById(R.id.btnHelp);
        tvBtnLowRightText = findViewById(R.id.tvBtnLowRightText);
        tvBtnLowLeftText = findViewById(R.id.tvBtnLowLeftText);
        textView4 = findViewById(R.id.textView4);
        LinearLayout ll03a = findViewById(R.id.ll03a);
        LinearLayout ll03b = findViewById(R.id.ll03b);
        ImageButton btnCustom01 = findViewById(R.id.btnCustom01);
        ImageButton btnCustom02 = findViewById(R.id.btnCustom02);
        btnE911 = findViewById(R.id.item_list_accident_menu_btnE911);
        concierge = findViewById(R.id.item_list_accident_menu_concierge);
        btnFire = findViewById(R.id.item_list_accident_menu_btnFire);
        btnTow = findViewById(R.id.item_list_accident_menu_btnTow);
        btnLogo = findViewById(R.id.item_list_accident_menu_btnLogo);
        ImageButton btnLegal = findViewById(R.id.item_list_accident_menu_btnLegal);
        taxi_ride = findViewById(R.id.item_list_accident_menu_taxi_ride);
        btnLogo2 = findViewById(R.id.item_list_accident_menu_btnLogo2);
        btnAccidentInfo = findViewById(R.id.item_list_accident_menu_btnAccidentInfo);
      //  mPersistenceObjTestDao = new PersistenceObjTestDao(this);
      //  mPersistenceObjTestDao.updateData("did_play_ListNotes", "blahblahblahblah");
        ActionInProgress = false;
        mPersistenceObjDao = new PersistenceObjDao(this);
        String androidId = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);


        getConfig(this);

        mPersistenceObjDao.updateData("PERSIST_ANDROID_ID", androidId);
        mPersistenceObjDao.updateData("PERSIST_DU_ID", "-1");
        mPersistenceObjDao.updateData("PERSIST_DU_MODE", "");
        mPersistenceObjDao.updateData("PERSIST_DV_ID", "-1");
        mPersistenceObjDao.updateData("PERSIST_DV_MODE", "");
        mPersistenceObjDao.updateData("PERSIST_AID_ID", "-1");
        mPersistenceObjDao.updateData("PERSIST_AID_MODE", "");
        mPersistenceObjDao.updateData("PERSIST_IP_ID", "-1");
        mPersistenceObjDao.updateData("PERSIST_IP_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_IV_ID", "-1");
        mPersistenceObjDao.updateData("PERSIST_IV_MODE", "");
        mPersistenceObjDao.updateData("PERSIST_PHOTOS_STATUS", "");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
        mPersistenceObjDao.updateData("PERSIST_AP_FILENAME", "");
        mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU");
        mPersistenceObjDao.updateData("PERSIST_PT_CALLER", "ACCIDENT_MENU");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "ACCIDENT_MENU");
        mPersistenceObjDao.updateData("PERSIST_CAROUSEL_CALLER", "0");
        mPersistenceObjDao.updateData("PERSIST_IV_ID", "-1");
        mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "ACCIDENT_MENU");
        mPersistenceObjDao.updateData("PERSIST_DV_MODE", "");
        mPersistenceObjDao.updateData("PERSIST_DV_ID", "-1");
        mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "ACCIDENT_MENU");
        mPersistenceObjDao.updateData("PERSIST_SELECT_NOTE_ATTACHMENT_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_IMAGE", "");
        mPersistenceObjDao.updateData("PERSIST_AP_ID", "0");
        mPersistenceObjDao.updateData("PERSIST_AN_ID", "0");
        mPersistenceObjDao.updateData("PERSIST_UPDATE_NOTES_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_WEB_VIEW_DATA", "");
        mPersistenceObjDao.updateData("PERSIST_FILE_NAME", "acc.pdf");
        mPersistenceObjDao.updateData("PERSIST_SELECTED_DU_ID", "");
        mPersistenceObjDao.updateData("PERSIST_ADD_INSURANCE_POLICY_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_IPO_ID", "");
        mPersistenceObjDao.updateData("PERSIST_IPO_HOLDER", "");
        mPersistenceObjDao.updateData("PERSIST_LATTITUDE", "");
        mPersistenceObjDao.updateData("PERSIST_LONGITUDE", "");
        mPersistenceObjDao.updateData("PERSIST_ADDRESS", "");
        mPersistenceObjDao.updateData("PERSIST_CITY", "");
        mPersistenceObjDao.updateData("PERSIST_STATE", "");
        mPersistenceObjDao.updateData("PERSIST_ZIP", "");
        mPersistenceObjDao.updateData("PERSIST_COUNTRY", "");
        mPersistenceObjDao.updateData("PERSIST_CUSTOM_MODE", "");
        mPersistenceObjDao.updateData("PERSIST_VT_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_PA_CALLER", "");

        mPersistenceObjDao.updateData("PERSIST_PT_TYPE", "");
        mPersistenceObjDao.updateData("PERSIST_INSURANCE_POLICY_VEHICLE_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_SEND_TO", "");
        mPersistenceObjDao.updateData("PERSIST_AUTOMOTIVE_SERVICES_MODE", "");
        mPersistenceObjDao.updateData("PERSIST_ALLOW_EMAIL_REPORT", "NO");

        mPersistenceObjDao.updateData("FIREBASE_CLOUD_UPLOAD_IN_PROGRESS", "false");
        mPersistenceObjDao.updateData("GOOGLE_DRIVE_UPLOAD_IN_PROGRESS", "false");
        mPersistenceObjDao.updateData("PERSIST_IM_CALLER", "");
        mPersistenceObjDao.updateData("DID_OPEN_INVOLVED_PARTY_DRAWER", "false");
        mPersistenceObjDao.updateData("DID_OPEN_INVOLVED_VEHICLE_DRAWER", "false");
        mPersistenceObjDao.updateData("PERSIST_MULTI_MEDIA_CALLER", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_01", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_02", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_03", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_04", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_05", "");
        mPersistenceObjDao.updateData("PERSIST_TEMP_06", "");
        mPersistenceObjDao.updateData("PERSIST_COMPANY_NAME", "");
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");

        float metrics = getResources().getDisplayMetrics().density;
        res = getResources();
        String deviceLocale = Locale.getDefault().getLanguage();
        if (!configLocale.equals("us") && !configLocale.equals("ca")) {
            taxi_ride.setVisibility(GONE);
            btnFire.setVisibility(VISIBLE);
            textView4.setVisibility(VISIBLE);
        }
        helpSequence = 0;
        mAccidentIdDao = new AccidentIdDao(this);
        accidentIdList = new ArrayList<>();
        accidentIdList = mAccidentIdDao.getAllAccidentIds();
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        deviceuserList = new ArrayList<>();
        deviceuserList = mDeviceUserDao.getAllDeviceUsers();

        // REMOTE_CONFIG_KEY = "accident_menu_bottom_left_icon";
        //  REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = getRemoteConfig(REMOTE_CONFIG_KEY, this);
        int resId = getResources().getIdentifier(REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON, "drawable", getPackageName());
        btnTow.setImageResource(resId);


        //  REMOTE_CONFIG_KEY = "accident_menu_bottom_right_icon";
        //  REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = getRemoteConfig(REMOTE_CONFIG_KEY, this);
        resId = getResources().getIdentifier(REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON, "drawable", getPackageName());
        btnLogo.setImageResource(resId);
if (REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON.equals("justice_scales2")) {

    tvBtnLowRightText.setText(res.getString(R.string.attorney_search));
}

        //    REMOTE_CONFIG_KEY = "affiliate_search_send_to_email_address";
        //    REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getRemoteConfig(REMOTE_CONFIG_KEY, this);

        if (accidentIdList.size() > 0 && deviceuserList.size() > 0) {
            //      REMOTE_CONFIG_KEY = "affiliate_search_send_to_email_address";
            //    REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getRemoteConfig(REMOTE_CONFIG_KEY, this);
            //  REMOTE_CONFIG_KEY = "affiliate_search_allow_email";
            //  REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = getRemoteConfig(REMOTE_CONFIG_KEY, this);
            //  REMOTE_CONFIG_KEY = "affiliate_search_allow_call";
            //  REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = getRemoteConfig(REMOTE_CONFIG_KEY, this);
            //  REMOTE_CONFIG_KEY = "affiliate_search_phone_number";
            //  REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = getRemoteConfig(REMOTE_CONFIG_KEY);


            if (REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE.equals("true") && REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("true") && REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("true") && REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE.equals("true")) {
                DrawerItemSet = 1;
// Allow Email Allow Call
                mDrawer = new DrawerBuilder()
                        .withActivity(this)


                        .withTranslucentStatusBar(true)
                        .withTranslucentNavigationBar(true)
                        .withActionBarDrawerToggle(true)
                        .withActionBarDrawerToggleAnimated(true)
                        .addDrawerItems(
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.app_name),
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                pitem1 = new PrimaryDrawerItem().withName(R.string.action_settings).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem1 = new PrimaryDrawerItem().withName(R.string.action_download_offline_language_libraries).withIcon(FontAwesome.Icon.faw_eye).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem2 = new PrimaryDrawerItem().withName(R.string.user_profile).withIcon(FontAwesome.Icon.faw_user).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem3 = new PrimaryDrawerItem().withName(R.string.tv0140).withIcon(FontAwesome.Icon.faw_id_badge).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem4 = new PrimaryDrawerItem().withName(R.string.tv0142).withIcon(FontAwesome.Icon.faw_car).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem5 = new PrimaryDrawerItem().withName(R.string.vehicle_type).withIcon(CommunityMaterial.Icon.cmd_car_convertible).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem6 = new PrimaryDrawerItem().withName(R.string.exit_app).withIcon(FontAwesome.Icon.faw_home).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                pitem2 = new PrimaryDrawerItem().withName(R.string.attorney_contact_me).withIcon(FontAwesome.Icon.faw_envelope).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                pitem3 = new PrimaryDrawerItem().withName(R.string.call_attorney_now).withIcon(FontAwesome.Icon.faw_phone).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem7 = new PrimaryDrawerItem().withName(R.string.action_credits).withIcon(FontAwesome.Icon.faw_star).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                pitem4 = new PrimaryDrawerItem().withName(R.string.label_help).withIcon(FontAwesome.Icon.faw_question_circle).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)


                        ) // add the items we want to use with our Drawer
                        .withOnDrawerListener(new Drawer.OnDrawerListener() {
                            @Override
                            public void onDrawerOpened(View drawerView) {

                            }

                            @Override
                            public void onDrawerClosed(View drawerView) {

                            }

                            @Override
                            public void onDrawerSlide(View drawerView, float slideOffset) {
                                if (ActionInProgress) {
                                     mDrawer.openDrawer();
                                }
                            }
                        })
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                context = view.getContext();
                                //  Toast.makeText(AccidentMenu.this, ((Nameable) drawerItem).getName().getText(AccidentMenu.this), Toast.LENGTH_SHORT).show();
                                switch (position) {
                                    case 5:
                                        if (ActionInProgress == false) {
                                            doClose();
                                             intent = new Intent(context, SettingsActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;

                                    case 6:
                                        if (ActionInProgress == false) {
                                            boolean hello = showInstallOfflineVoiceFiles.showInstallOfflineVoiceFiles(context);
                                        }
                                        break;
                                    case 7:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DU_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU_NAVIGATION_DRAWER");
                                            doClose();
                                            deviceuserList = new ArrayList<>();
                                            deviceuserList = mDeviceUserDao.getAllDeviceUsers();
                                            intent = new Intent(context, ListDeviceUser.class);
                                            context.startActivity(intent);
                                        }
                                        break;
                                    case 8:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_PT_CALLER", "ACCIDENT_MENU");
                                            doClose();

                                            intent = new Intent(context, ListPartyType.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 9:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DV_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListDeviceVehicle.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 10:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_VT_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListVehicleType.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 11:
                                        if (ActionInProgress == false) {
                                            DID_RETRIEVE_REMOTE_CONFIG = "false";
                                            handleSetPersitenceValue(KEY_DID_RETRIEVE_REMOTE_CONFIG, DID_RETRIEVE_REMOTE_CONFIG);
                                            mPersistenceObjDao.updateData("did_play_OpenAccidentMenuDrawer", "false");
                                            doClose();
                                            finishAffinity();
                                            System.exit(0);
                                        }
                                        break;
                                    case 12:
                                        if (ActionInProgress == false) {
                                            context = view.getContext();
                                            doClose();
                                            intent = new Intent(context, AccidentAttorneyEmail.class);
                                            context.startActivity(intent);
                                        }
                                        break;
                                    case 13:
                                        makeCall(REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE);
                                        break;
                                    case 14:
                                        if (ActionInProgress == false) {
                                            doClose();
                                            intent = new Intent(context, AboutActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 15:
                                        if (ActionInProgress == false) {
                                            ActionInProgress = true;
                                            showNavSequence1(viewGroup);
                                        }
                                        break;
                                }


                                //we do not consume the event and want the Drawer to continue with the event chain
                                return false;
                            }
                        })
                        .withSavedInstance(savedInstanceState)
                        .withShowDrawerOnFirstLaunch(true)
                        .withCloseOnClick(false)
                        .build();

            }

            if (REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE.equals("true") && REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("true") && REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE.equals("false")) {
                DrawerItemSet = 2;
                // Don't Call  Allow Email
                mDrawer = new DrawerBuilder()
                        .withActivity(this)

                        .withTranslucentStatusBar(true)
                        .withTranslucentNavigationBar(true)
                        .withActionBarDrawerToggle(true)
                        .withActionBarDrawerToggleAnimated(true)
                        .addDrawerItems(
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.app_name),
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                pitem1 = new PrimaryDrawerItem().withName(R.string.action_settings).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem1 = new PrimaryDrawerItem().withName(R.string.action_download_offline_language_libraries).withIcon(FontAwesome.Icon.faw_eye).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem2 = new PrimaryDrawerItem().withName(R.string.user_profile).withIcon(FontAwesome.Icon.faw_user).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem3 = new PrimaryDrawerItem().withName(R.string.tv0140).withIcon(FontAwesome.Icon.faw_id_badge).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem4 = new PrimaryDrawerItem().withName(R.string.tv0142).withIcon(FontAwesome.Icon.faw_car).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem5 = new PrimaryDrawerItem().withName(R.string.vehicle_type).withIcon(CommunityMaterial.Icon.cmd_car_convertible).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem6 = new PrimaryDrawerItem().withName(R.string.exit_app).withIcon(FontAwesome.Icon.faw_home).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),

                                pitem2 = new PrimaryDrawerItem().withName(R.string.attorney_contact_me).withIcon(FontAwesome.Icon.faw_envelope).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem7 = new PrimaryDrawerItem().withName(R.string.action_credits).withIcon(FontAwesome.Icon.faw_star).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                pitem4 = new PrimaryDrawerItem().withName(R.string.label_help).withIcon(FontAwesome.Icon.faw_question_circle).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)

                        ) // add the items we want to use with our Drawer
                        .withOnDrawerListener(new Drawer.OnDrawerListener() {
                            @Override
                            public void onDrawerOpened(View drawerView) {

                            }

                            @Override
                            public void onDrawerClosed(View drawerView) {

                            }

                            @Override
                            public void onDrawerSlide(View drawerView, float slideOffset) {
                                if (ActionInProgress) {
                                    mDrawer.openDrawer();
                                }
                            }
                        })
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                context = view.getContext();
                                //  Toast.makeText(AccidentMenu.this, ((Nameable) drawerItem).getName().getText(AccidentMenu.this), Toast.LENGTH_SHORT).show();
                                switch (position) {
                                    case 5:
                                        if (ActionInProgress == false) {
                                            doClose();
                                            Intent intent = new Intent(context, SettingsActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;

                                    case 6:
                                        if (ActionInProgress == false) {
                                            boolean hello = showInstallOfflineVoiceFiles.showInstallOfflineVoiceFiles(context);
                                        }
                                        break;
                                    case 7:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DU_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU_NAVIGATION_DRAWER");
                                            // deviceuserList = new ArrayList<>();
                                            // deviceuserList = mDeviceUserDao.getAllDeviceUsers();
                                            doClose();
                                            intent = new Intent(context, ListDeviceUser.class);
                                            context.startActivity(intent);
                                        }
                                        break;
                                    case 8:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_PT_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListPartyType.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 9:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DV_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListDeviceVehicle.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 10:
                                        mPersistenceObjDao.updateData("PERSIST_VT_CALLER", "ACCIDENT_MENU");
                                        doClose();
                                        intent = new Intent(context, ListVehicleType.class);
                                        context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        break;
                                    case 11:
                                        if (ActionInProgress == false) {
                                            DID_RETRIEVE_REMOTE_CONFIG = "false";
                                            handleSetPersitenceValue(KEY_DID_RETRIEVE_REMOTE_CONFIG, DID_RETRIEVE_REMOTE_CONFIG);
                                            mPersistenceObjDao.updateData("did_play_OpenAccidentMenuDrawer", "false");
                                            doClose();
                                            finishAffinity();
                                            System.exit(0);
                                        }
                                        break;
                                    case 12:
                                        if (ActionInProgress == false) {
                                            context = view.getContext();
                                            doClose();
                                            intent = new Intent(context, AccidentAttorneyEmail.class);
                                            context.startActivity(intent);
                                        }
                                        break;
                                    case 13:
                                        if (ActionInProgress == false) {
                                            doClose();
                                            intent = new Intent(context, AboutActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 14:
                                        if (ActionInProgress == false) {
                                            ActionInProgress = true;
                                            showNavSequence2(viewGroup);
                                        }

                                        break;


                                }


                                //we do not consume the event and want the Drawer to continue with the event chain
                                return false;
                            }
                        })
                        .withSavedInstance(savedInstanceState)
                        .withShowDrawerOnFirstLaunch(true)
                        .withCloseOnClick(false)
                        .build();

            }

            if (REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE.equals("false") && REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("true") && REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE.equals("true")) {
                DrawerItemSet = 3;
                // Allow Call  Don't Email
                mDrawer = new DrawerBuilder()
                        .withActivity(this)

                        .withTranslucentStatusBar(true)
                        .withTranslucentNavigationBar(true)
                        .withActionBarDrawerToggle(true)
                        .withActionBarDrawerToggleAnimated(true)

                        .addDrawerItems(
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.app_name),
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                pitem1 = new PrimaryDrawerItem().withName(R.string.action_settings).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem1 = new PrimaryDrawerItem().withName(R.string.action_download_offline_language_libraries).withIcon(FontAwesome.Icon.faw_eye).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem2 = new PrimaryDrawerItem().withName(R.string.user_profile).withIcon(FontAwesome.Icon.faw_user).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem3 = new PrimaryDrawerItem().withName(R.string.tv0140).withIcon(FontAwesome.Icon.faw_id_badge).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem4 = new PrimaryDrawerItem().withName(R.string.tv0142).withIcon(FontAwesome.Icon.faw_car).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem5 = new PrimaryDrawerItem().withName(R.string.vehicle_type).withIcon(CommunityMaterial.Icon.cmd_car_convertible).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem6 = new PrimaryDrawerItem().withName(R.string.exit_app).withIcon(FontAwesome.Icon.faw_home).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                pitem2 = new PrimaryDrawerItem().withName(R.string.call_attorney_now).withIcon(FontAwesome.Icon.faw_phone).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem7 = new PrimaryDrawerItem().withName(R.string.action_credits).withIcon(FontAwesome.Icon.faw_star).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                pitem4 = new PrimaryDrawerItem().withName(R.string.label_help).withIcon(FontAwesome.Icon.faw_question_circle).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)
                        ) // add the items we want to use with our Drawer
                        .withOnDrawerListener(new Drawer.OnDrawerListener() {
                            @Override
                            public void onDrawerOpened(View drawerView) {

                            }

                            @Override
                            public void onDrawerClosed(View drawerView) {

                            }

                            @Override
                            public void onDrawerSlide(View drawerView, float slideOffset) {
                                if (ActionInProgress) {
                                    mDrawer.openDrawer();
                                }
                            }
                        })
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                context = view.getContext();
                                //  Toast.makeText(AccidentMenu.this, ((Nameable) drawerItem).getName().getText(AccidentMenu.this), Toast.LENGTH_SHORT).show();
                                switch (position) {
                                    case 5:
                                        if (ActionInProgress == false) {
                                            doClose();
                                            Intent intent = new Intent(context, SettingsActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 6:
                                        if (ActionInProgress == false) {
                                            boolean hello = showInstallOfflineVoiceFiles.showInstallOfflineVoiceFiles(context);
                                        }
                                        break;
                                    case 7:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DU_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU_NAVIGATION_DRAWER");
                                            // deviceuserList = new ArrayList<>();
                                            // deviceuserList = mDeviceUserDao.getAllDeviceUsers();
                                            doClose();
                                            intent = new Intent(context, ListDeviceUser.class);
                                            context.startActivity(intent);
                                        }
                                        break;
                                    case 8:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_PT_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListPartyType.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 9:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DV_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListDeviceVehicle.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 10:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_VT_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListVehicleType.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 11:
                                        if (ActionInProgress == false) {
                                            DID_RETRIEVE_REMOTE_CONFIG = "false";
                                            handleSetPersitenceValue(KEY_DID_RETRIEVE_REMOTE_CONFIG, DID_RETRIEVE_REMOTE_CONFIG);
                                            mPersistenceObjDao.updateData("did_play_OpenAccidentMenuDrawer", "false");
                                            doClose();
                                            finishAffinity();
                                            System.exit(0);
                                        }
                                        break;
                                    case 12:
                                        if (ActionInProgress == false) {
                                            makeCall(REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE);
                                        }
                                        break;
                                    case 13:
                                        if (ActionInProgress == false) {
                                            doClose();
                                            intent = new Intent(context, AboutActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 14:
                                        if (ActionInProgress == false) {
                                            ActionInProgress = true;
                                            showNavSequence3(viewGroup);
                                        }
                                        break;
                                }
                                return false;
                            }
                        })
                        .withSavedInstance(savedInstanceState)
                        .withShowDrawerOnFirstLaunch(true)
                        .withCloseOnClick(false)
                        .build();

            }


            if (REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE.equals("false") && REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE.equals("false") || REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("false") && REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("false")) {
                DrawerItemSet = 4;
                mDrawer = new DrawerBuilder()
                        .withActivity(this)
                        .withTranslucentStatusBar(true)

                        .withTranslucentNavigationBar(true)
                        .withActionBarDrawerToggle(true)
                        .withActionBarDrawerToggleAnimated(true)

                        .addDrawerItems(
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.app_name),
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                pitem1 = new PrimaryDrawerItem().withName(R.string.action_settings).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem1 = new PrimaryDrawerItem().withName(R.string.action_download_offline_language_libraries).withIcon(FontAwesome.Icon.faw_eye).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem2 = new PrimaryDrawerItem().withName(R.string.user_profile).withIcon(FontAwesome.Icon.faw_user).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem3 = new PrimaryDrawerItem().withName(R.string.tv0140).withIcon(FontAwesome.Icon.faw_id_badge).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem4 = new PrimaryDrawerItem().withName(R.string.tv0142).withIcon(FontAwesome.Icon.faw_car).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem5 = new PrimaryDrawerItem().withName(R.string.vehicle_type).withIcon(CommunityMaterial.Icon.cmd_car_convertible).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem6 = new PrimaryDrawerItem().withName(R.string.exit_app).withIcon(FontAwesome.Icon.faw_home).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                sitem7 = new PrimaryDrawerItem().withName(R.string.action_credits).withIcon(FontAwesome.Icon.faw_star).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                                pitem4 = new PrimaryDrawerItem().withName(R.string.label_help).withIcon(FontAwesome.Icon.faw_question_circle).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)
                           //     new PrimaryDrawerItem().withName(R.string.route_menu).withIcon(FontAwesome.Icon.faw_map).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            //    new PrimaryDrawerItem().withName(R.string.route_weather).withIcon(FontAwesome.Icon.faw_cloud).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                           //     new PrimaryDrawerItem().withName(R.string.music_player).withIcon(FontAwesome.Icon.faw_music).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                             //   new PrimaryDrawerItem().withName(R.string.video_player).withIcon(FontAwesome.Icon.faw_video).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)
                           //     new PrimaryDrawerItem().withName(R.string.driver_paperwork).withIcon(FontAwesome.Icon.faw_image).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)





                                ) // add the items we want to use with our Drawer
                        .withOnDrawerListener(new Drawer.OnDrawerListener() {
                            @Override
                            public void onDrawerOpened(View drawerView) {

                            }

                            @Override
                            public void onDrawerClosed(View drawerView) {

                            }

                            @Override
                            public void onDrawerSlide(View drawerView, float slideOffset) {
                                if (ActionInProgress) {
                                    mDrawer.openDrawer();
                                }
                            }
                        })
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                context = view.getContext();
                                switch (position) {
                                    case 5:
                                        if (ActionInProgress == false) {
                                            doClose();
                                            Intent intent = new Intent(context, SettingsActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;

                                    case 6:
                                        if (ActionInProgress == false) {
                                            boolean hello = showInstallOfflineVoiceFiles.showInstallOfflineVoiceFiles(context);
                                        }
                                        break;
                                    case 7:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DU_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU_NAVIGATION_DRAWER");
                                            //  deviceuserList = new ArrayList<>();
                                            //   deviceuserList = mDeviceUserDao.getAllDeviceUsers();
                                            doClose();
                                            intent = new Intent(context, ListDeviceUser.class);
                                            context.startActivity(intent);
                                        }
                                        break;
                                    case 8:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_PT_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListPartyType.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 9:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_DV_MODE", "UPDATE");
                                            mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListDeviceVehicle.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 10:
                                        if (ActionInProgress == false) {
                                            mPersistenceObjDao.updateData("PERSIST_VT_CALLER", "ACCIDENT_MENU");
                                            doClose();
                                            intent = new Intent(context, ListVehicleType.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 11:
                                        if (ActionInProgress == false) {
                                            DID_RETRIEVE_REMOTE_CONFIG = "false";
                                            handleSetPersitenceValue(KEY_DID_RETRIEVE_REMOTE_CONFIG, DID_RETRIEVE_REMOTE_CONFIG);
                                            mPersistenceObjDao.updateData("did_play_OpenAccidentMenuDrawer", "false");
                                            doClose();
                                            finishAffinity();
                                            System.exit(0);
                                        }
                                        break;
                                    case 12:
                                        if (ActionInProgress == false) {
                                            doClose();
                                            intent = new Intent(context, AboutActivity.class);
                                            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        }
                                        break;
                                    case 13:
                                        if (ActionInProgress == false) {
                                            ActionInProgress = true;
                                            showNavSequence4(viewGroup);
                                        }
                                        break;
                                }
                                return false;
                            }
                        })
                        .withSavedInstance(savedInstanceState)
                        .withShowDrawerOnFirstLaunch(true)
                        .withCloseOnClick(false)
                        .build();

            }

        } else {
            DrawerItemSet = 5;
            mDrawer = new DrawerBuilder()
                    .withActivity(this)

                    .withTranslucentStatusBar(true)
                    .withTranslucentNavigationBar(true)
                    .withActionBarDrawerToggle(true)
                    .withActionBarDrawerToggleAnimated(true)

                    .addDrawerItems(
                            new DividerDrawerItem(),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().withName(R.string.app_name),
                            new DividerDrawerItem(),
                            new DividerDrawerItem(),
                            pitem1 = new PrimaryDrawerItem().withName(R.string.action_settings).withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            sitem1 = new PrimaryDrawerItem().withName(R.string.action_download_offline_language_libraries).withIcon(FontAwesome.Icon.faw_eye).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            sitem2 = new PrimaryDrawerItem().withName(R.string.user_profile).withIcon(FontAwesome.Icon.faw_user).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            sitem3 = new PrimaryDrawerItem().withName(R.string.tv0140).withIcon(FontAwesome.Icon.faw_id_badge).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            sitem4 = new PrimaryDrawerItem().withName(R.string.tv0142).withIcon(FontAwesome.Icon.faw_car).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            sitem5 = new PrimaryDrawerItem().withName(R.string.vehicle_type).withIcon(CommunityMaterial.Icon.cmd_car_convertible).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            sitem6 = new PrimaryDrawerItem().withName(R.string.exit_app).withIcon(FontAwesome.Icon.faw_home).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            sitem7 = new PrimaryDrawerItem().withName(R.string.action_credits).withIcon(FontAwesome.Icon.faw_star).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),
                            pitem4 = new PrimaryDrawerItem().withName(R.string.label_help).withIcon(FontAwesome.Icon.faw_question_circle).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)
                    ) // add the items we want to use with our Drawer
                    .withOnDrawerListener(new Drawer.OnDrawerListener() {
                        @Override
                        public void onDrawerOpened(View drawerView) {

                        }

                        @Override
                        public void onDrawerClosed(View drawerView) {

                        }

                        @Override
                        public void onDrawerSlide(View drawerView, float slideOffset) {
                            if (ActionInProgress) {
                                mDrawer.openDrawer();
                            }
                        }
                    })
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            context = view.getContext();
                            //  Toast.makeText(AccidentMenu.this, ((Nameable) drawerItem).getName().getText(AccidentMenu.this), Toast.LENGTH_SHORT).show();
                            switch (position) {
                                case 5:
                                    if (ActionInProgress == false) {
                                        doClose();
                                        Intent intent = new Intent(context, SettingsActivity.class);
                                        context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    }
                                    break;

                                case 6:
                                    boolean hello = showInstallOfflineVoiceFiles.showInstallOfflineVoiceFiles(context);
                                    break;
                                case 7:
                                    if (ActionInProgress == false) {
                                        mPersistenceObjDao.updateData("PERSIST_DU_MODE", "UPDATE");
                                        mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU_NAVIGATION_DRAWER");
                                        // deviceuserList = new ArrayList<>();
                                        //   deviceuserList = mDeviceUserDao.getAllDeviceUsers();
                                        doClose();
                                        intent = new Intent(context, ListDeviceUser.class);
                                        context.startActivity(intent);
                                    }
                                    break;
                                case 8:
                                    if (ActionInProgress == false) {
                                        mPersistenceObjDao.updateData("PERSIST_PT_CALLER", "ACCIDENT_MENU");
                                        doClose();
                                        intent = new Intent(context, ListPartyType.class);
                                        context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    }
                                    break;
                                case 9:
                                    if (ActionInProgress == false) {
                                        mPersistenceObjDao.updateData("PERSIST_DV_MODE", "UPDATE");
                                        mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "ACCIDENT_MENU");
                                        doClose();
                                        intent = new Intent(context, ListDeviceVehicle.class);
                                        context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    }
                                    break;
                                case 10:
                                    if (ActionInProgress == false) {
                                        mPersistenceObjDao.updateData("PERSIST_VT_CALLER", "ACCIDENT_MENU");
                                        doClose();
                                        intent = new Intent(context, ListVehicleType.class);
                                        context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    }
                                    break;
                                case 11:
                                    if (ActionInProgress == false) {
                                        DID_RETRIEVE_REMOTE_CONFIG = "false";
                                        handleSetPersitenceValue(KEY_DID_RETRIEVE_REMOTE_CONFIG, DID_RETRIEVE_REMOTE_CONFIG);
                                        mPersistenceObjDao.updateData("did_play_OpenAccidentMenuDrawer", "false");
                                        doClose();
                                        finishAffinity();
                                        System.exit(0);
                                    }
                                    break;
                                case 12:
                                    if (ActionInProgress == false) {
                                        doClose();
                                        intent = new Intent(context, AboutActivity.class);
                                        context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    }
                                    break;
                                case 13:
                                    if (ActionInProgress == false) {
                                        ActionInProgress = true;
                                        showNavSequence4(viewGroup);
                                    }
                                    break;
                            }


                            //we do not consume the event and want the Drawer to continue with the event chain
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .withShowDrawerOnFirstLaunch(true)
                    .withCloseOnClick(false)
                    .build();
        }

        switch (DrawerItemSet) {
            case 1: {

                break;
            }

            case 2: {

                break;
            }
            case 3: {

                break;
            }
            case 4: {

                break;
            }
            case 5: {

                break;
            }
        }

         persistenceObj = mPersistenceObjDao.getPersistence("did_play_OpenAccidentMenuDrawer");
        did_play_OpenAccidentMenuDrawer = persistenceObj.getPERSISTENCE_VALUE();

        if  (did_play_OpenAccidentMenuDrawer.equals("false")) {
          mPersistenceObjDao.updateData("did_play_OpenAccidentMenuDrawer", "true");
          scheduleDismiss();
      }
        //   mPersistenceObjDao.addData("PERSIST_DEVICE_MODE", "PRODUCTION");
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);

        });

// Search for ambulance nearby
        btnHelp.setOnClickListener(view -> {
            if (ActionInProgress == false) {
                ActionInProgress = true;
                helpSequence++;
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnHelp.startAnimation(rotateAnimation);
                scheduleDoHelp();
            }
        });
        btnE911.setOnClickListener(view -> {

            if (fireClick == true) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme3);
                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);
                    btnE911.startAnimation(myAnim);
                    btnPushed = "btnE911";
                    scheduleDoButton();
                }
            }
            fireClick = true;
            btnE911.setImageAlpha(alpha1);

        });
        btnE911.setOnLongClickListener(view -> {
            if (ActionInProgress == false) {
                btnE911.setImageAlpha(alpha2);
                context = view.getContext();
                //     Resources res = getResources();
                message = res.getString(R.string.search_ambulance);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
            }
            return false;
        });
        // Search for police nearby
        concierge.setOnClickListener(view -> {
            if (fireClick == true) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme3);
                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);
                    concierge.startAnimation(myAnim);
                    btnPushed = "concierge";
                    scheduleDoButton();
                }
            }
            fireClick = true;
            concierge.setImageAlpha(alpha1);
        });
        concierge.setOnLongClickListener(view -> {
            if (ActionInProgress == false) {
                concierge.setImageAlpha(alpha2);
                context = view.getContext();
                //  Resources res = getResources();
                message = res.getString(R.string.search_police);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
            }
            return false;
        });

        btnTow.setOnClickListener(view -> {
            if (fireClick == true) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme3);
                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);
                    btnTow.startAnimation(myAnim);
                    mPersistenceObjDao.updateData("PERSIST_AUTOMOTIVE_SERVICES_MODE", "PERSONAL_VEHICLES");
                    btnPushed = "btnTow";
                    scheduleDoButton();
                }
               }


            fireClick = true;
            btnTow.setImageAlpha(alpha1);
        });
        btnTow.setOnLongClickListener(view -> {
            if (ActionInProgress == false) {
                btnTow.setImageAlpha(alpha2);
                context = view.getContext();
                //   Resources res = getResources();
                message = res.getString(R.string.search_auto);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
            }
            return false;
        });


        btnLogo.setOnClickListener(view -> {
            if (fireClick == true) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme3);
                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);
                    btnLogo.startAnimation(myAnim);
                    btnPushed = "btnLogo";
                    scheduleDoButton();
                }

                    }

            fireClick = true;
            btnLogo.setImageAlpha(alpha1);
        });
        btnLogo.setOnLongClickListener(view -> {
            if (ActionInProgress == false) {
                btnLogo.setImageAlpha(alpha2);
                context = view.getContext();
                if (REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON.equals("justice_scales2")) {
                    message = res.getString(R.string.search_attorneys);
                }
                if (REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON.equals("tow")) {
                    message = res.getString(R.string.search_commercial_truck_services);
                }
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
            }
            return false;
        });

        //        Dial 911
        taxi_ride.setOnClickListener(view -> {
            if (fireClick == true) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme3);
                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);
                    taxi_ride.startAnimation(myAnim);
                    btnPushed = "taxi_ride";
                    scheduleDoButton();

                }
                       }
            fireClick = true;
            taxi_ride.setImageAlpha(alpha1);

        });
        taxi_ride.setOnLongClickListener(view -> {
            if (ActionInProgress == false) {
                taxi_ride.setImageAlpha(alpha2);
                context = view.getContext();
                //    Resources res = getResources();
                message = res.getString(R.string.dial_911);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
            }
            return false;
        });

        btnAccidentInfo.setOnClickListener(view -> {
            if (fireClick == true) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme3);
                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);
                    btnAccidentInfo.startAnimation(myAnim);
                    mPersistenceObjDao.updateData("PERSIST_DU_MODE", "SELECT");
                    mPersistenceObjDao.updateData("PERSIST_DUV_MODE", "SELECT");  // V = Vehicle
                    mPersistenceObjDao.updateData("PERSIST_AID_MODE", "SELECT");
                    mPersistenceObjDao.updateData("PERSIST_IPV_MODE", "SELECT");
                    mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU");
                    btnPushed = "btnAccidentInfo";
                    scheduleDoButton();
                }
            }
            fireClick = true;
            btnAccidentInfo.setImageAlpha(alpha1);
        });
        btnAccidentInfo.setOnLongClickListener(view -> {
            if (ActionInProgress == false) {
                btnAccidentInfo.setImageAlpha(alpha2);
                context = view.getContext();
                //      Resources res = getResources();
                message = res.getString(R.string.enter_accident_info);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
            }
            return false;
        });

        String drawablePath = "drawable-xxhdpi/";
        if (metrics == METRIC_LDPI) {
            drawablePath = "drawable-ldpi/";
        }
        if (metrics == METRIC_MDPI) {
            drawablePath = "drawable-mdpi/";
        }
        if (metrics == METRIC_HDPI) {
            drawablePath = "drawable-hdpi/";
        }
        if (metrics == METRIC_XHDPI) {
            drawablePath = "drawable-xhdpi/";
        }
        if (metrics == METRIC_XXHDPI) {
            drawablePath = "drawable-xxhdpi/";
        }
        if (metrics == METRIC_XXXHDPI) {
            drawablePath = "drawable-xxxhdpi/";
        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_BUTTON01");
        String BUTTON01 = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_ICON_URL01");
        String ICONURL01 = persistenceObj.getPERSISTENCE_VALUE();

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE01_COUNTRY");
        String COUNTRY01 = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE01");
        PHONE01 = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_URL01");
        URL01 = persistenceObj.getPERSISTENCE_VALUE();
        //   persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_DIRECTORY01");
        //   String DIRECTORY01 = persistenceObj.getPERSISTENCE_VALUE();

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_BUTTON02");
        String BUTTON02 = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_ICON_URL02");
        String ICONURL02 = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE02_COUNTRY");
        String COUNTRY02 = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_PHONE02");
        PHONE02 = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_URL02");
        URL02 = persistenceObj.getPERSISTENCE_VALUE();
        //      persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_CUSTOM_DIRECTORY02");
        //      String DIRECTORY02 = persistenceObj.getPERSISTENCE_VALUE();

        if (!BUTTON01.equals("")) {
            ll03a.setVisibility(GONE);
            ll03b.setVisibility(VISIBLE);

// String image = "http://desktop-7tfutu7/accidentdefense/" + drawablePath + "/pizza1.png";
            if (!ICONURL01.equals("") && !drawablePath.equals("") && !BUTTON01.equals("")) {
              //  String S;
                String image = ICONURL01 + drawablePath + BUTTON01;
                RequestOptions options = new RequestOptions();
                options.centerCrop().override(450, 450);
                // String image = "http://desktop-7tfutu7/accidentdefense/drawable-xxhdpi/pizza1.png";
                //  Context context = ApplicationContextProvider.getContext();
                Glide.with(context)
                        .load(image)
                        .apply(options)
                        .into(btnCustom01);

              //  S = utils.loadImage(btnCustom01, image);
            }
            ll03b.setVisibility(VISIBLE);

        }
        if (!BUTTON02.equals("")) {
            ll03a.setVisibility(GONE);
            ll03b.setVisibility(VISIBLE);
            // String image = "http://desktop-7tfutu7/accidentdefense/" + drawablePath + "/pizza1.png";
            if (!ICONURL02.equals("") && !drawablePath.equals("") && !BUTTON02.equals("")) {
             //   String S;
                String image = ICONURL02 + drawablePath + BUTTON02;
                RequestOptions options = new RequestOptions();
                options.centerCrop().override(450, 450);
                // String image = "http://desktop-7tfutu7/accidentdefense/drawable-xxhdpi/pizza1.png";
                //  Context context = ApplicationContextProvider.getContext();
                Glide.with(context)
                        .load(image)
                        .apply(options)
                        .into(btnCustom02);

            //    S = utils.loadImage(btnCustom02, image);
            }
            ll03b.setVisibility(VISIBLE);

        }
        btnCustom01.setOnClickListener(view -> {
            if (!PHONE01.equals("")) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme3);
                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);
                    btnCustom01.startAnimation(myAnim);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + PHONE01));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }

                if (!URL01.equals("")) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(URL01));
                    startActivity(intent);
                }
            }
        });


        btnCustom02.setOnClickListener(view -> {
            if (!PHONE02.equals("")) {
                if (ActionInProgress == false) {
                    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme2);

                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);

                    btnCustom02.startAnimation(myAnim);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + PHONE02));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                }

                if (!URL02.equals("")) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(URL02));
                    startActivity(intent);
                }
            }
        });

    }
    private void scheduleDismiss() {
        Handler handler = new Handler();
        //hud.dismiss();
        handler.postDelayed(this::openIt, 1000);
    }
    private void openIt() {

        mDrawer.openDrawer();
        scheduleDismiss2();
    }
    private void scheduleDismiss2() {
        Handler handler = new Handler();
        //hud.dismiss();
        handler.postDelayed(this::closeIt, 1000);
    }

    private void closeIt() {

        mDrawer.closeDrawer();
    }


    public void makeCall(String PHONE_NUMBER) {
        intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + PHONE_NUMBER));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //   return;
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
            startActivity(intent);
        } else {
            if (intent.resolveActivity(getPackageManager()) != null) {

                startActivity(intent);
            }
        }


    }

    public void getConfig(Context context) {

        DID_RETRIEVE_REMOTE_CONFIG = getPersistantRemoteConfig(KEY_DID_RETRIEVE_REMOTE_CONFIG, context);
        if (DID_RETRIEVE_REMOTE_CONFIG.equals("true")) {
            REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON, context);
            REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON, context);


            //    REMOTE_CONFIG_KEY = "affiliate_search_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE_address";
            REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE, context);


            //	REMOTE_CONFIG_KEY = "affiliate_search_allow_email";
            REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE, context);


            //	REMOTE_CONFIG_KEY = "affiliate_search_allow_call";
            REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE, context);

            //	REMOTE_CONFIG_KEY = "affiliate_search_phone_number";
            REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE, context);

            //REMOTE_CONFIG_KEY = "search_internet_for_attorney_only";
            REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY, context);

            //  REMOTE_CONFIG_KEY = "allow_email_contact";
            REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT, context);

            //  REMOTE_CONFIG_KEY = "allow_phone_contact";
            REMOTE_CONFIG_ALLOW_PHONE_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT, context);

            //	REMOTE_CONFIG_KEY = "default_referral_phone_not_email_" ;
            REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL, context);

            //	REMOTE_CONFIG_KEY = "affiliate_search_show_phone_number";
            REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER, context);
            REMOTE_CONFIG_VIDEO_CALL = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_VIDEO_CALL, context);
            REMOTE_CONFIG_SCAN = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_SCAN, context);
        } else {
            setConfig(context);
        }
        REMOTE_CONFIG_KEY = "REMOTE_CONFIG_CLEAR_DID_PLAY";
        REMOTE_CONFIG_CLEAR_DID_PLAY = getRemoteConfig(REMOTE_CONFIG_KEY, this, this);
        if (REMOTE_CONFIG_CLEAR_DID_PLAY.equals("true")) {
            mPersistenceObjDao.updateData("did_play_ListVehicleCoverage", "");
            mPersistenceObjDao.updateData("did_play_ListPremiumAdvertiser", "");
            mPersistenceObjDao.updateData("did_play_ListNotes", "");
            mPersistenceObjDao.updateData("did_play_ListInvolvedParty", "");
            mPersistenceObjDao.updateData("did_play_ListInvolvedVehicle", "");
            mPersistenceObjDao.updateData("did_play_ListDeviceUser", "");
            mPersistenceObjDao.updateData("did_play_ListAccident", "");
            mPersistenceObjDao.updateData("did_play_ListPartyType", "");
            mPersistenceObjDao.updateData("did_play_ListInsuredPeople", "");
            mPersistenceObjDao.updateData("did_play_ListNotesAdapter", "");
            mPersistenceObjDao.updateData("did_play_ListInsurancePolicy", "");
            mPersistenceObjDao.updateData("did_play_ListDeviceVehicle", "");
            mPersistenceObjDao.updateData("did_play_EditCustom", "");
            mPersistenceObjDao.updateData("did_play_AddInsurancePolicy", "");
            mPersistenceObjDao.updateData("did_play_CarouselSpinner", "");
            mPersistenceObjDao.updateData("did_play_AccidentMenu", "");
            mPersistenceObjDao.updateData("did_play_AddInvolvedVehicle", "");
            mPersistenceObjDao.updateData("did_play_AddVehicleType", "");
            mPersistenceObjDao.updateData("did_play_CarouselBaseAdapter", "");
            mPersistenceObjDao.updateData("did_play_AddInvolvedParty", "");
            mPersistenceObjDao.updateData("did_play_AutomotiveRepair", "");
            mPersistenceObjDao.updateData("did_play_FreeFormEmail", "");
            mPersistenceObjDao.updateData("did_play_AccidentAttorneyRequest", "");
            mPersistenceObjDao.updateData("did_play_AddDeviceVehicle", "");
            mPersistenceObjDao.updateData("did_play_FreeFormCall", "");
            mPersistenceObjDao.updateData("did_play_DashboardActivity", "");
            mPersistenceObjDao.updateData("did_play_AddAccident", "");
            mPersistenceObjDao.updateData("did_play_AttorneysSearch", "");
            mPersistenceObjDao.updateData("did_play_InvolvedMenu", "");
            mPersistenceObjDao.updateData("did_play_AddPremiumAdvertiser", "");
            mPersistenceObjDao.updateData("did_play_AutomotivePaintBody", "");
            mPersistenceObjDao.updateData("did_play_AddNotes", "");
            mPersistenceObjDao.updateData("did_play_AddPartyType", "");
            mPersistenceObjDao.updateData("did_play_AutomotiveServices", "");
            mPersistenceObjDao.updateData("did_play_AccidentAttorneyEmail", "");
            mPersistenceObjDao.updateData("did_play_AddDeviceUser", "");
            mPersistenceObjDao.updateData("did_play_AutomotiveTowing", "");
            mPersistenceObjDao.updateData("did_play_UpdateDeviceVehicle", "");
            mPersistenceObjDao.updateData("did_play_UpdateInsurancePolicy", "");
            mPersistenceObjDao.updateData("did_play_UpdateNotes", "");
            mPersistenceObjDao.updateData("did_play_PdfPrint", "");
            mPersistenceObjDao.updateData("did_play_SelectNoteAttachment", "");
            mPersistenceObjDao.updateData("did_play_UpdateInvolvedVehicle", "");
            mPersistenceObjDao.updateData("did_play_ProfilesMenu", "");
            mPersistenceObjDao.updateData("did_play_UpdateAccident", "");
            mPersistenceObjDao.updateData("did_play_SettingsActivity", "");
            mPersistenceObjDao.updateData("did_play_UpdateInvolvedParty", "");
            mPersistenceObjDao.updateData("did_play_UploadToGoogleDrive", "");
            mPersistenceObjDao.updateData("did_play_UpdateDeviceUser", "");
            mPersistenceObjDao.updateData("did_play_PDFViewActivity", "");
            mPersistenceObjDao.updateData("did_play_UpdatePartyType", "");
            mPersistenceObjDao.updateData("did_play_UpdatePremiumAdvertiser", "");
            mPersistenceObjDao.updateData("did_play_UpdateVehicleType", "");

        }
    }

    public void setConfig(Context context) {

        REMOTE_CONFIG_SCAN = getRemoteConfig(KEY_REMOTE_CONFIG_SCAN, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_SCAN, REMOTE_CONFIG_SCAN);
        REMOTE_CONFIG_VIDEO_CALL = getRemoteConfig(KEY_REMOTE_CONFIG_VIDEO_CALL, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_VIDEO_CALL, REMOTE_CONFIG_VIDEO_CALL);
        REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = getRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON, REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON);

        REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = getRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON, REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON);

        //    REMOTE_CONFIG_KEY = "affiliate_search_send_to_email_address";
        REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE, REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_email";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = getRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE, REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_call";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = getRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE, REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_phone_number";
        REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = getRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE, REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE);

        //REMOTE_CONFIG_KEY = "search_internet_for_attorney_only";
        REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = getRemoteConfig(KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY, REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY);

        //  REMOTE_CONFIG_KEY = "allow_email_contact";
        REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = getRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT, REMOTE_CONFIG_ALLOW_EMAIL_CONTACT);

        //  REMOTE_CONFIG_KEY = "allow_phone_contact";
        REMOTE_CONFIG_ALLOW_PHONE_CONTACT = getRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT, REMOTE_CONFIG_ALLOW_PHONE_CONTACT);

        //	REMOTE_CONFIG_KEY = "default_referral_phone_not_email_" ;
        REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = getRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL, REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL);

        //	REMOTE_CONFIG_KEY = "affiliate_search_show_phone_number";
        REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = getRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER, REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER);
        DID_RETRIEVE_REMOTE_CONFIG = "true";
        handleSetPersitenceValue(KEY_DID_RETRIEVE_REMOTE_CONFIG, DID_RETRIEVE_REMOTE_CONFIG);


    }
    public void getDefConfig(Context context) {
        DID_RETRIEVE_REMOTE_CONFIG = getPersistantRemoteConfig(DEF_KEY_DID_RETRIEVE_REMOTE_CONFIG, context);
        if (DID_RETRIEVE_REMOTE_CONFIG.equals("true")) {
            REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON, context);
            REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON, context);


            //    REMOTE_CONFIG_KEY = "affiliate_search_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE_address";
            REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE, context);


            //	REMOTE_CONFIG_KEY = "affiliate_search_allow_email";
            REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE, context);


            //	REMOTE_CONFIG_KEY = "affiliate_search_allow_call";
            REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE, context);

            //	REMOTE_CONFIG_KEY = "affiliate_search_phone_number";
            REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE, context);

            //REMOTE_CONFIG_KEY = "search_internet_for_attorney_only";
            REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY, context);

            //  REMOTE_CONFIG_KEY = "allow_email_contact";
            REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT, context);

            //  REMOTE_CONFIG_KEY = "allow_phone_contact";
            REMOTE_CONFIG_ALLOW_PHONE_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT, context);

            //	REMOTE_CONFIG_KEY = "default_referral_phone_not_email_" ;
            REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL, context);

            //	REMOTE_CONFIG_KEY = "affiliate_search_show_phone_number";
            REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER, context);
            REMOTE_CONFIG_VIDEO_CALL = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_VIDEO_CALL, context);
            REMOTE_CONFIG_SCAN = getPersistantRemoteConfig(DEF_KEY_REMOTE_CONFIG_SCAN, context);
        } else {
            setDefConfig(context);
        }

    }
    public void setDefConfig(Context context) {

        REMOTE_CONFIG_SCAN = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_SCAN, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_SCAN, REMOTE_CONFIG_SCAN);
        REMOTE_CONFIG_VIDEO_CALL = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_VIDEO_CALL, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_VIDEO_CALL, REMOTE_CONFIG_VIDEO_CALL);
        REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON, REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON);

        REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON, REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON);

        //    REMOTE_CONFIG_KEY = "affiliate_search_send_to_email_address";
        REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE, REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_email";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE, REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_call";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE, REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_phone_number";
        REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE, REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE);

        //REMOTE_CONFIG_KEY = "search_internet_for_attorney_only";
        REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY, REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY);

        //  REMOTE_CONFIG_KEY = "allow_email_contact";
        REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = getRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT, REMOTE_CONFIG_ALLOW_EMAIL_CONTACT);

        //  REMOTE_CONFIG_KEY = "allow_phone_contact";
        REMOTE_CONFIG_ALLOW_PHONE_CONTACT = getRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT, this, context);
        handleSetPersitenceValue(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT, REMOTE_CONFIG_ALLOW_PHONE_CONTACT);

        //	REMOTE_CONFIG_KEY = "default_referral_phone_not_email_" ;
        REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL, REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL);

        //	REMOTE_CONFIG_KEY = "affiliate_search_show_phone_number";
        REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = getRemoteConfig(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER, this, context);
        handleSetPersitenceValue(DEF_KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER, REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER);
        DID_RETRIEVE_REMOTE_CONFIG = "true";
        handleSetPersitenceValue(DEF_KEY_DID_RETRIEVE_REMOTE_CONFIG, DID_RETRIEVE_REMOTE_CONFIG);


    }
    public void handleSetPersitenceValue(String PERSISTENCE_KEY, String PERSISTENCE_VALUE) {

         persistenceObj = mPersistenceObjDao.getPersistence(PERSISTENCE_KEY);
        //   String TEST_P = persistenceObj.getPERSISTENCE_VALUE();
        if (persistenceObj == null) {
            mPersistenceObjDao.addData(PERSISTENCE_KEY, PERSISTENCE_VALUE);
        } else {
            mPersistenceObjDao.updateData(PERSISTENCE_KEY, PERSISTENCE_VALUE);
        }
    }

   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //   context = view.getContext();
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            //       this.startActivity(intent);


            return true;
        }

        if (id == R.id.action_offline) {


            boolean hello = showInstallOfflineVoiceFiles.showInstallOfflineVoiceFiles(this);
            return true;
        }

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            //   this.startActivity(intent);

            return true;

        }
        if (id == R.id.user_profiles) {

            mPersistenceObjDao.updateData("PERSIST_DU_MODE", "UPDATE");
            mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU");
            Intent intent = new Intent(this, ListDeviceUser.class);
            this.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            //    this.startActivity(intent);

            return true;

        }

        if (id == R.id.accident_roles) {
            mPersistenceObjDao.updateData("PERSIST_PT_CALLER", "ACCIDENT_MENU");
            Intent intent = new Intent(this, ListPartyType.class);
            this.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            //   this.startActivity(intent);

            return true;

        }
        if (id == R.id.vehicle_profiles) {
            mPersistenceObjDao.updateData("PERSIST_DV_MODE", "UPDATE");
            mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "ACCIDENT_MENU");
            Intent intent = new Intent(this, ListDeviceVehicle.class);
            this.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            //      this.startActivity(intent);

            return true;

        }
        if (id == R.id.vehicle_types) {
            mPersistenceObjDao.updateData("PERSIST_VT_CALLER", "ACCIDENT_MENU");
            Intent intent = new Intent(this, ListVehicleType.class);
            this.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            //       this.startActivity(intent);

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
    public void disableButtons() {



    }
    public void enableButtons() {



    }

    public void showFirstSequence(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");

        //

        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnE911)

                        .setPrimaryText(res.getString(R.string.dial_911))
                        .setSecondaryText(res.getString(R.string.got_it))
                      //  .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnAccidentInfo)

                        .setPrimaryText(res.getString(R.string.enter_accident_info))
                        .setSecondaryText(res.getString(R.string.got_it))
                      //  .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_first_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                   //     .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                     .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                     {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                        {
                            ActionInProgress = false;
                        }
                    }
                }))


                                .show();
    }
    public void showSecondSequence(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        //int toolBarColorValue = Color.parseColor("#FF0288D1");

        //

        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnE911)

                        .setPrimaryText(res.getString(R.string.dial_911))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnAccidentInfo)

                        .setPrimaryText(res.getString(R.string.enter_accident_info))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon1))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(concierge)

                        .setPrimaryText(res.getString(R.string.concierge_help))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(taxi_ride)

                        .setPrimaryText(res.getString(R.string.taxi_help))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnTow)

                        .setPrimaryText(res.getString(R.string.automotive_services))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnLogo)

                        .setPrimaryText(res.getString(R.string.search_attorneys))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
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
                                }
                            }
                        }))


                .show();
    }

    public void showSequence2(View view) {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");

        //

        new MaterialTapTargetSequence()


                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon1))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnE911)

                        .setPrimaryText(res.getString(R.string.search_ambulance))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(concierge)

                        .setPrimaryText(res.getString(R.string.search_police))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(taxi_ride)

                        .setPrimaryText(res.getString(R.string.dial_911))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnTow)

                        .setPrimaryText(res.getString(R.string.automotive_services))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnLogo)

                        .setPrimaryText(res.getString(R.string.commercial_truck_services))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(btnAccidentInfo)

                        .setPrimaryText(res.getString(R.string.enter_accident_info))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
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
                                }
                            }
                        }))


                .show();
    }

    public void showNavSequence1(View view) {

        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();


        int xx = cc;
        // important - repeat sequence 0 twice
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_settings_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_download_offline_language_libraries_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.user_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.role_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_profile_drawer))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_type_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(11))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.exit_app_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(12))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.attorney_contact_me_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(13))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.call_attorney_now_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(14))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_credits_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(15))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }

    public void showNavSequence2(View view) {

        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();


        int xx = cc;
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_settings_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_download_offline_language_libraries_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.user_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.role_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(11))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_type_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(12))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.exit_app_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(13))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.attorney_contact_me_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(14))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_credits_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(15))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }

    public void showNavSequence3(View view) {

        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();


        int xx = cc;
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_settings_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_download_offline_language_libraries_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.user_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.role_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_type_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(11))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.exit_app_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(12))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.call_attorney_now_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(13))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_credits_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(14))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }

    public void showNavSequence4(View view) {

        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();


        int xx = cc;
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_settings_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_download_offline_language_libraries_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.user_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.role_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_profile_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.vehicle_type_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(11))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.exit_app_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(12))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.action_credits_drawer))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AccidentMenu.this)
                        .setTarget(mRecyclerView.getChildAt(13))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }
    private void scheduleDismissToolbar() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        View btnBack = toolbar.getChildAt(2);

        mDrawer.openDrawer();
        btnBack.clearAnimation();
    }
    private void scheduleDoButton() {
        Handler handler = new Handler();
        handler.postDelayed(this::doButton, 600);
    }
    private void doButton() {
        if (ActionInProgress == false) {
            switch (btnPushed) {

                case "taxi_ride": {
                    String SearchString = res.getString(R.string.search_taxi);
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    break;
                }

                case "btnE911": {
           Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + 911));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }


                    break;
                }

                // Search for police nearby
                case "concierge": {
                    Intent intent = new Intent(this, ConciergeServices.class);
                    this.startActivity(intent);
                    break;
                }
    /*            case "concierge": {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=police");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    break;
                }*/

                // Search for tow trucks nearby
                case "btnTow": {
                    Intent intent = new Intent(this, AutomotiveServices.class);
                    this.startActivity(intent);
                    break;
                }
                case "btnLogo": {

                    if (REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON.equals("justice_scales2")) {
                        if (!REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY.equals("true")) {

                            if (accidentIdList.size() > 0 && deviceuserList.size() > 0) {
                                Intent intent = new Intent(this, AttorneysSearch.class);
                                this.startActivity(intent);
                            } else {
                                //   Resources res = getResources();
                                String SearchString = res.getString(R.string.search_accident_attorney);
                                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }

                        }

                        //    Resources res = getResources();
                        String SearchString = res.getString(R.string.search_accident_attorney);
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + SearchString);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                    if (REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON.equals("tow")) {
                        mPersistenceObjDao.updateData("PERSIST_AUTOMOTIVE_SERVICES_MODE", "COMMERCIAL_VEHICLES");
                        doClose();
                        Intent intent = new Intent(this, AutomotiveServices.class);
                        this.startActivity(intent);
                    }
                    break;

                }

                case "btnAccidentInfo": {
                    doClose();
                    Intent intent = new Intent(this, ListDeviceUser.class);
                    this.startActivity(intent);
                    break;
                }
                case "btnCustom01": {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + PHONE01));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    break;
                }


                case "btnCustom02": {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + PHONE02));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    break;
                }


            }
        }
}

    public void doClose() {
      //  mInsurancePolicyVDao.closeAll();
      //  mDeviceImageStoreDao.closeAll();
      //  mPartyTypeDao.closeAll();
      //  mInvolvedImageStoreDao.closeAll();
      //  mVehicleTypeDao.closeAll();
      //  mAccidentNoteDao.closeAll();
       // mInvolvedPartyDao.closeAll();
       // mDeviceUserDao.closeAll();
       // mInsurancePolicyPDao.closeAll();
       // mInvolvedVehicleDao.closeAll();
        mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
      //  mPremiumAdvertiserDao.closeAll();
       // mInsurancePolicyDao.closeAll();
       // mDeviceVehicleDao.closeAll();
       // mVehicleManifestDao.closeAll();

    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {
        if (helpSequence < 2) {
            showFirstSequence(view);
        } else {
            showSecondSequence(view);
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


