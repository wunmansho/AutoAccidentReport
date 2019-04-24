package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

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

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.auto.accident.report.util.utils.isNumber;


/**
 * Created by myron on 9/28/2018.
 */

public class UploadToGoogleDrive extends AppCompatActivity {

    private static final String TAG = "UploadToGoogleDrive";
    private final String deviceLocale = Locale.getDefault().getCountry();
    private final String deviceLocalel = Locale.getDefault().getLanguage();

    private final String albumName = "AccidentReport";
    private File[] listFile;
    private File path;

  


    private List<String> accidentIdList;
    private String DU_FNAME;

    private String SEND_TO_EMAIL;
    private final String configLocale = Locale.getDefault().getCountry().toLowerCase();

    private Resources res;

    private PersistenceObj persistenceObj;

    private String ACC_NUM;
    private String ACCIDENT_REPORT_PREFIX;
    private String totalPagesS;
    private PersistenceObjDao mPersistenceObjDao;
    private Toolbar toolbar;
    private ImageButton btnSend;
    private FloatingActionButton btnHelp;
    private RotateAnimation rotateAnimation;
    private String DA_HORN;
    private  Context context;
    private View view;
    private String DA_ID_STR;
    private int DUX_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_to_google_drive);

        btnHelp = findViewById(R.id.btnHelp);
        btnSend = findViewById(R.id.btnSend);
        toolbar = findViewById(R.id.my_toolbar);

        res = getResources();
        mPersistenceObjDao = new PersistenceObjDao(this);


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


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        String PERSISTANCE_KEY = "PERSIST_PDF_PAGE_COUNT" + ACC_NUM;
        persistenceObj = mPersistenceObjDao.getPersistence(PERSISTANCE_KEY);
        totalPagesS = persistenceObj.getPERSISTENCE_VALUE();


        ActionBar actionBar = getSupportActionBar();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        mPersistenceObjDao.updateData("DP_CATEGORY", "DEVICE_USER");
        //   Intent receivedIntent = getIntent();
        btnHelp.setOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnHelp.startAnimation(rotateAnimation);
            scheduleDoHelp();
        });

        btnSend.setOnClickListener(view -> {
            context = view.getContext();
            DA_HORN = "btnSend";
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            rotateAnimation.setDuration(100);
            btnSend.startAnimation(rotateAnimation);
            scheduleDoBullhorn();


       });
        //   Intent intent = new Intent(this,  ListInvolvedMenu.class);
        //   startActivity(intent);
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);

        });
    }

    private void doSend() {
        ACCIDENT_REPORT_PREFIX = "AccidentReport000" + ACC_NUM;


        // if (cbSendReport.isChecked()) {
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
                Uri pdfUri = Uri.fromFile(listFile[i]);
                doClose();
                Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setText("Share PDF doc")
                        .setType("application/pdf")
                        .setStream(pdfUri)
                        .getIntent()
                        .setPackage("com.google.android.apps.docs");
                startActivity(shareIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


            }
        }

        // }


    }
    public void disableButtons() {

        btnSend.setEnabled(false);
        btnHelp.setEnabled(false);

    }
    public void enableButtons() {

        btnSend.setEnabled(true);
        btnHelp.setEnabled(true);

    }
    private void doEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);

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

                for (int i = 0; i < listFile.length; i++) {
                    // Get the path of the image file
                    uri.add(Uri.fromFile(listFile[i]));

                }
            }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);


      //  startActivity(intent);
        this.startActivity(Intent.createChooser(intent, "Share PDF doc"));


    }



    public void showSequence(View view) {

        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(UploadToGoogleDrive.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )
              
                .addPrompt(new MaterialTapTargetPrompt.Builder(UploadToGoogleDrive.this)
                        .setTarget(btnSend)

                        .setPrimaryText(res.getString(R.string.press_to_add_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(UploadToGoogleDrive.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state != MaterialTapTargetPrompt.STATE_REVEALING && state != MaterialTapTargetPrompt.STATE_REVEALED  && state != MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)

                                {
                                    enableButtons();


                                }
                            }
                        })
                )



                .show();
    }
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(context, PdfPrint.class);
        startActivity(intent);


    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
    //    mVehicleManifestDao.closeAll();
    }

    private void scheduleDoBullhorn() {
        Handler handler = new Handler();
        handler.postDelayed(this::doBullhorn, 250);
    }

    private void doBullhorn() {
        switch (DA_HORN) {
            case "btnSend": {
                doSend();
                break;
            }

        }
    }

    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {
        showSequence(view);
    }


}
