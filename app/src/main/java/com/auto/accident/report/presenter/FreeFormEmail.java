package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static com.auto.accident.report.util.utils.isNumber;


/**
 * Created by myron on 9/28/2018.
 */

public class FreeFormEmail extends AppCompatActivity {

    private static final int REQ_CODE_HPH = 90;
    private static final int REQ_CODE_CPH = 89;
    private static final int REQ_CODE_WPH = 88;
    private DeviceUserDao mDeviceUserDao;
    private LinearLayout spXX_INVOLVEMENT, report2, ll01;
    private ImageButton btnSend;
    private FloatingActionButton btnSpeakOff;
    private EditText tieXX_EMAIL, tieXX_PHON1, tieXX_PHON2,
            tieXX_PHON3;
    private Spinner spXX_FNAME;
    private TextView tvDA_PROMPT, tvAID, tvAID_TEXT;
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
    private String SEND_TO_EMAIL;
    private final String configLocale = Locale.getDefault().getCountry().toLowerCase();
    private Resources res;
    private PersistenceObj persistenceObj;
    private String PERSIST_FREE_FORM_EMAIL_CALLER;
    private String ACC_NUM;
    private String ACCIDENT_REPORT_PREFIX;
    private String totalPagesS;
    private PersistenceObjDao mPersistenceObjDao;
    private RotateAnimation rotateAnimation;
    private Toolbar toolbar;
    private Intent emailIntent;
    private  Context context;
    private String DA_ID_STR;
    private int DUX_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_form_email);
        ll01 = findViewById(R.id.ll01);
        //     ll02 = findViewById(R.id.ll02);
        report1 = findViewById(R.id.report1);
        report2 = findViewById(R.id.report2);
        tvAID = findViewById(R.id.tvAID);
        tvAID_TEXT = findViewById(R.id.tvAID_TEXT);
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

        ImageButton btnSpeakOn = findViewById(R.id.btnSpeakOn);
        btnSpeakOff = findViewById(R.id.btnSpeakOff);

        res = getResources();
        mPersistenceObjDao = new PersistenceObjDao(this);

        progressBar = findViewById(R.id.progressBar1);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_FREE_FORM_EMAIL_SEND_TO");
        SEND_TO_EMAIL = persistenceObj.getPERSISTENCE_VALUE();
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DUX_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DUX_ID = 0;
        }
        DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        String[] splitString;

        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];

        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.sdv));
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        toolbar.setTitle(getString(R.string.app_name) + " # " + ACC_NUM);
        String PERSISTANCE_KEY = "PERSIST_PDF_PAGE_COUNT" + ACC_NUM;
        persistenceObj = mPersistenceObjDao.getPersistence(PERSISTANCE_KEY);
        totalPagesS = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_FREE_FORM_EMAIL_CALLER");
        PERSIST_FREE_FORM_EMAIL_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        switch (PERSIST_FREE_FORM_EMAIL_CALLER) {
            case "EMAIL_LIST_INVOLVED_PARTY":
                mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_CALLER", "");
                mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_SEND_TO", "");
                Intent intent = new Intent(this, ListInvolvedParty.class);
                startActivity(intent);
                break;
            case "EMAIL_PDF_PRINT":
                mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_CALLER", "");
                mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_SEND_TO", "");
                intent = new Intent(this, PdfPrint.class);
                startActivity(intent);
                break;
        }
  //      rbCallAttorney.setVisibility(GONE);
   //     rbRequestAttorney.setVisibility(GONE);
   //     rlfirst.setVisibility(GONE);
   //     tvAID_TEXT.setVisibility(GONE);
    //    tvAID.setVisibility(GONE);
    //    cbSendReport.setChecked(true);
    //    cbSendReport.setEnabled(true);
    //    cbInjured.setVisibility(GONE);
     //   cbInjuredOthers.setVisibility(GONE);
     //   cbLegalDefense.setVisibility(GONE);
        report2.setVisibility(GONE);
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
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

			 mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_SEND_TO", "");
            mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_CALLER", "");
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);
           
        });


        btnSend.setOnClickListener(view -> {
            if (fireClick == true) {

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


    }

    private void doEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("text/plain");
        //  EMAIL_PDF_PRINT
        //  EMAIL_LIST_INVOLVED_PARTY
        PERSIST_FREE_FORM_EMAIL_CALLER = "EMAIL_" + PERSIST_FREE_FORM_EMAIL_CALLER;
        mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_CALLER", PERSIST_FREE_FORM_EMAIL_CALLER);


        ACCIDENT_REPORT_PREFIX = "AccidentReport000" + ACC_NUM;


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

                    if (name.startsWith(ACCIDENT_REPORT_PREFIX)) {
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
        this.startActivity(Intent.createChooser(intent, "Email:"));


    }
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        switch (PERSIST_FREE_FORM_EMAIL_CALLER) {

            case "LIST_INVOLVED_PARTY":

                Intent intent = new Intent(this, ListInvolvedParty.class);
                startActivity(intent);
                break;
            case "PDF_PRINT":

                intent = new Intent(this, PdfPrint.class);
                startActivity(intent);
                break;
        }

    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
      //  mVehicleManifestDao.closeAll();
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
