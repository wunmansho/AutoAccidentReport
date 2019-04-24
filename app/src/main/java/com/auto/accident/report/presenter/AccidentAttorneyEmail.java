package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.auto.accident.report.R;
import com.auto.accident.report.models.AccidentIdDao;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.AccidentId;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by myron on 9/28/2018.
 */

public class AccidentAttorneyEmail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQ_CODE_HPH = 90;
    private static final int REQ_CODE_CPH = 89;
    private static final int REQ_CODE_WPH = 88;

    private DeviceUserDao mDeviceUserDao;
    private LinearLayout llbtnSpeakOn, llbtnSpeakOff, spXX_INVOLVEMENT, report2, ll01;
    private ImageButton btnSend;
    private FloatingActionButton btnSpeakOff;
    private EditText tieXX_EMAIL, tieXX_PHON1, tieXX_PHON2,
            tieXX_PHON3;
    private Spinner spXX_FNAME;
    private TextView tvDA_PROMPT, tvAID;

    private final String deviceLocale = Locale.getDefault().getCountry();
    private final String deviceLocalel = Locale.getDefault().getLanguage();
    private final Boolean AutoAdd = true;
    private int REQ_CODE;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private final String LOG_TAG = "VoiceRecActivity";
    private String DA_RESULT2;
    private String[] splitString;
    private int splitLength;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;

    private String St;
    private CountryCodePicker ccp_phon1_country, ccp_phon2_country, ccp_phon3_country;
    private final String albumName = "AccidentReport";
    private File[] listFile;
    private File path;

   
    private String preferOfflineLanguages;
    private boolean preferOffline;
    private RelativeLayout rlfirst, rlcenter, report1;
    private CheckBox cbSendReport, cbInjured, cbInjuredOthers, cbLegalDefense;
    private RadioButton rbRequestAttorney, rbCallAttorney;
    private String DA_RESULT;
    private String[] singleChoiceItems;
    private List<String> accidentIdList;
    private String DU_FNAME;

    private Resources res;
    private AccidentIdDao mAccidentIdDao;
    // remote config
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
    private RotateAnimation rotateAnimation;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private AccidentId accidentId;
    private Toolbar toolbar;
    private Context context;
	private int DA_ID;
	private String DA_ID_STR;
    private boolean ActionInProgress;

    // remote config
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accident_attorney_request);
        ll01 = findViewById(R.id.ll01);
        //     ll02 = findViewById(R.id.ll02);
        report1 = findViewById(R.id.report1);
        report2 = findViewById(R.id.report2);
        tvAID = findViewById(R.id.tvAID);
        rlfirst = findViewById(R.id.rlfirst);
        rlcenter = findViewById(R.id.rlcenter);
        cbInjured = findViewById(R.id.cb_injured);
        cbInjuredOthers = findViewById(R.id.cb_injured_others);
        cbLegalDefense = findViewById(R.id.cb_legal_defense);
        rbCallAttorney = findViewById(R.id.rb_call_attorney);
        rbRequestAttorney = findViewById(R.id.rb_request_attorney);
        cbSendReport = findViewById(R.id.cb_send_report);
        ccp_phon1_country = findViewById(R.id.ccp_phon1_country);
        ccp_phon2_country = findViewById(R.id.ccp_phon2_country);
        ccp_phon3_country = findViewById(R.id.ccp_phon3_country);


        tvDA_PROMPT = findViewById(R.id.tvDA_PROMPT);
        tieXX_EMAIL = findViewById(R.id.tieXX_EMAIL);
        spXX_FNAME = findViewById(R.id.spXX_FNAME);

        tieXX_PHON1 = findViewById(R.id.tieXX_PHON1);
        tieXX_PHON2 = findViewById(R.id.tieXX_PHON2);
        tieXX_PHON3 = findViewById(R.id.tieXX_PHON3);

        ImageButton btnCamera = findViewById(R.id.btnCamera);
        btnSend = findViewById(R.id.btnSend);
        llbtnSpeakOn = findViewById(R.id.llbtnSpeakOn);
        llbtnSpeakOff = findViewById(R.id.llbtnSpeakOff);
        ImageButton btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);

        res = getResources();
       mPersistenceObjDao = new PersistenceObjDao(this);
        ActionInProgress = false;
        mDeviceUserDao = new DeviceUserDao(this);
        progressBar = findViewById(R.id.progressBar1);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle(getString(R.string.welcome) + " - " + getString(R.string.premium));
        toolbar.setTitle(getString(R.string.app_name));
        getConfig(this);


        rbCallAttorney.setVisibility(GONE);
        rbRequestAttorney.setVisibility(GONE);

        cbSendReport.setChecked(true);
        cbSendReport.setEnabled(true);
        btnSend.setImageResource(R.drawable.ic_email_white_48dp);


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PREFER_OFFLINE");
        String preferOfflineLanguages = persistenceObj.getPERSISTENCE_VALUE();
        if (preferOfflineLanguages.equals("FALSE")) {
            preferOffline = false;

        } else {
            preferOffline = true;

        }
        //  spXX_INVOLVEMENT.setVisibility(GONE);
        ActionBar actionBar = getSupportActionBar();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        mPersistenceObjDao.updateData("DP_CATEGORY", "DEVICE_USER");
        Intent receivedIntent = getIntent();
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);



        });
        mAccidentIdDao = new AccidentIdDao(this);
        accidentIdList = new ArrayList<>();
        accidentIdList = mAccidentIdDao.getAllAccidentIdsOnly();
        if (accidentIdList.size() > 1) {
            singleChoiceItems = new String[accidentIdList.size()];

            singleChoiceItems = accidentIdList.toArray(singleChoiceItems);
            getSelectedItemAid();
        } else {
            tvAID.setText(accidentIdList.get(0));
        }


        btnSend.setOnClickListener(view -> {
            if (fireClick == true) {
                //   res = getResources();
                doEmail();

            }

            fireClick = true;
            btnSend.setImageAlpha(alpha1);

        });

        btnSend.setOnLongClickListener(view -> {

            btnSend.setImageAlpha(alpha2);
            context = view.getContext();
            //  res = getResources();
            message = res.getString(R.string.tv0145);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);

            mdToast.show();

            fireClick = false;

            return false;

        });

        spXX_FNAME.setOnItemSelectedListener(this);
        loadSpinnerData();
    }

    private void doEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE});
        intent.putExtra(Intent.EXTRA_SUBJECT, res.getString(R.string.attorney_contact));
        String firstName = utils.getFirstWord(DU_FNAME);
        StringBuilder emailBody = new StringBuilder("Name : ");
        emailBody.append(DU_FNAME).append("\n").append("\n");
        emailBody.append("Email : ").append(tieXX_EMAIL.getText()).append("\n");
        emailBody.append(res.getString(R.string.tv0010) + " : " + tieXX_PHON1.getText() + "\n");
        emailBody.append(res.getString(R.string.tv0011) + " : " + tieXX_PHON2.getText() + "\n");
        emailBody.append(res.getString(R.string.tv0012) + " : " + tieXX_PHON3.getText() + "\n");
        if (cbInjured.isChecked()) {
            emailBody.append(firstName + " ").append(res.getString(R.string.was_injured)).append("\n");
        } else {
            emailBody.append(firstName + " ").append(res.getString(R.string.was_not_injured)).append("\n");
        }
        if (cbInjuredOthers.isChecked()) {
            emailBody.append(firstName + " ").append(res.getString(R.string.calling_about_others)).append("\n");
        }
        if (rbRequestAttorney.isChecked()) {
            if (cbSendReport.isChecked()) {
                emailBody.append(firstName + " ").append(res.getString(R.string.has_asked_for_attorney_contact_sending_report) + "." + "\n");
            } else {
                emailBody.append(firstName + " ").append(res.getString(R.string.has_asked_for_attorney_contact_isnot_sending_report) + "." + "\n");
            }
        } else {
            if (cbSendReport.isChecked()) {
                emailBody.append(firstName + " ").append(res.getString(R.string.calling_attorney_sending_report)).append("\n");

            }
        }
		 DA_ID_STR = tvAID.getText().toString();
		 if (!DA_ID_STR.equals("") && !DA_ID_STR.equals("0")) {
        DA_ID = Integer.parseInt(DA_ID_STR);
		 }
        accidentId = mAccidentIdDao.getAccidentId(DA_ID);
        emailBody.append(res.getString(R.string.tv0182) + " : " + accidentId.getAID_ADATE() + "\n");
        emailBody.append(res.getString(R.string.tv0183) + " : " + accidentId.getAID_ATIME() + "\n");
        emailBody.append(res.getString(R.string.tvCountryName) + " : " + accidentId.getAID_CCODE() + "\n");
        emailBody.append(res.getString(R.string.accident_address) + " : " + accidentId.getAID_ADDRESS() + "\n");
        emailBody.append(res.getString(R.string.accident_city) + " : " + accidentId.getAID_CITY() + "\n");
        emailBody.append(res.getString(R.string.accident_county) + " : " + accidentId.getAID_COUNTY() + "\n");
        emailBody.append(res.getString(R.string.accident_state) + " : " + accidentId.getAID_STATE() + "\n");
        emailBody.append(res.getString(R.string.accident_zip) + " : " + accidentId.getAID_ZIP() + "\n");
        intent.putExtra(Intent.EXTRA_TEXT, emailBody.toString());
        if (cbSendReport.isChecked()) {
            ArrayList<Uri> uri = new ArrayList<Uri>();

            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
            } else {
                // Locate the image folder in your SD Card
                path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
                path.mkdirs();
            }
            FilenameFilter textFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {

                    if (name.startsWith("AccidentReport")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            if (path.isDirectory()) {
                listFile = path.listFiles(textFilter);
                // Create a String array for FilePathStrings
                int j;
                for (int i = 0; i < listFile.length; i++) {
                    // Get the path of the image file
                    uri.add(Uri.fromFile(listFile[i]));
                    j = i;
                    if (j >= 4) {
                        j = 0;
                    }
                }
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //  this.startActivity(intent);
        doClose();
        this.startActivity(Intent.createChooser(intent, "Email:"));


    }

    private void loadSpinnerData() {
        // database handler - Your Data
        DeviceUserDao db = new DeviceUserDao(getApplicationContext());

        // Spinner Drop down elements
        List<String> deviceUserFnameList = db.getAllDeviceUsersFname();


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, deviceUserFnameList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

        spXX_FNAME.setAdapter(dataAdapter);

    }

    public void getSelectedItemAid() {
        int itemSelected = 0;
        //   Resources res = getResources();
        new AlertDialog.Builder(this)
                .setTitle(res.getString(R.string.said)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = accidentIdList.get(selectedIndex);
                                tvAID.setText(DA_RESULT);
                                //  startLicenseStateInput();
                                dialogInterface.dismiss();
                            }
                        }
                )
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner carousel_item

        // On selecting a spinner item
        DU_FNAME = parent.getItemAtPosition(position).toString();
        mDeviceUserDao = new DeviceUserDao(this);
        DeviceUser deviceUser = mDeviceUserDao.getDeviceUserByFname(DU_FNAME);
        tieXX_EMAIL.setText(deviceUser.getDU_EMAIL());
        tieXX_PHON1.setText(deviceUser.getDU_PHON1());
        tieXX_PHON2.setText(deviceUser.getDU_PHON2());
        tieXX_PHON3.setText(deviceUser.getDU_PHON3());

        rlcenter.setVisibility(VISIBLE);

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

        //  toastMessage("Please Select A Valid Party Type");
    }

    public void getConfig(Context context) {

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
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 250);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, AccidentMenu.class);
        startActivity(intent);
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
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }

}
