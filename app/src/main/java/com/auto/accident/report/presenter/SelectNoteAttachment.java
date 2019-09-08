package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 4/12/2018.
 */

public class SelectNoteAttachment extends AppCompatActivity {

    private PersistenceObjDao mPersistenceObjDao;
    private FloatingActionButton btnAdd;
    private RadioGroup rg01;
    private RadioButton rb03;
    private View view;
    private String message;
    private int duration;
    private int type;
    private RotateAnimation rotateAnimation;
    private String rsMode;
    private PersistenceObj persistenceObj;
    private  Context context;
    private String DA_ID_STR;
    private int DUX_ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_note_attachment);

        rg01 = findViewById(R.id.rg01);
        RadioButton rb01 = findViewById(R.id.rb01);
        RadioButton rb02 = findViewById(R.id.rb02);
        //   rb03 = (RadioButton) findViewById(R.id.rb03);
        RadioButton rb04 = findViewById(R.id.rb04);
        RadioButton rb05 = findViewById(R.id.rb05);
        btnAdd = findViewById(R.id.btnAdd);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_IP_ID", "0");
        mPersistenceObjDao.updateData("PERSIST_IV_ID", "0");
        mPersistenceObjDao.updateData("PERSIST_AP_ID", "0");
        mPersistenceObjDao.updateData("PERSIST_IP_CALLER", "SELECT_NOTE_ATTACHMENT");
        mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "SELECT_NOTE_ATTACHMENT");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "SELECT_NOTE_ATTACHMENT");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "SELECT_NOTE_ATTACHMENT");
        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "SELECT_NOTE_ATTACHMENT");

        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID)) {
            DUX_ID = Integer.parseInt(DA_ID);
        } else {
            DUX_ID = 0;
        }
        DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
        String DU_FNAME = deviceUser.getDU_FNAME();
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];

        toolbar.setSubtitle(getString(R.string.welcome) + " " + DU_FNAME + " - " + getString(R.string.aip));
        toolbar.setNavigationOnClickListener(view -> {
            context = view.getContext();
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            rotateAnimation.setRepeatMode(Animation.REVERSE);
            rotateAnimation.setDuration(1000);
            View btnBack = toolbar.getChildAt(2);
            btnBack.startAnimation(rotateAnimation);

            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_SELECT_NOTE_ATTACHMENT_CALLER");
            rsMode = persistenceObj.getPERSISTENCE_VALUE();
            if (rsMode.equals("INVOLVED_MENU")) {
                Intent intent = new Intent(context,  ListInvolvedMenu.class);
                startActivity(intent);
            }


        });

        rb01.setOnClickListener(view -> btnAdd.show());

        rb02.setOnClickListener(view -> btnAdd.show());

        //      rb03.setOnClickListener(new View.OnClickListener()  {
        //         @Override
        //        public void onClick(View v){
        //            btnAdd.show();
        //        }
        //   });

        rb04.setOnClickListener(view -> btnAdd.show());
        rb05.setOnClickListener(view -> btnAdd.show());
        btnAdd.setOnClickListener(view -> {
            int id = rg01.getCheckedRadioButtonId();
            switch (id) {
                case R.id.rb01:
                    context = view.getContext();
                    Intent intent = new Intent(context, ListInvolvedParty.class);
                    context.startActivity(intent);
                    break;
                case R.id.rb02:

                    context = view.getContext();
                    intent = new Intent(context, ListInvolvedVehicle.class);
                    context.startActivity(intent);

                    break;
                //        case R.id.rb03:
                //         context = view.getContext();
                //          intent = new Intent(context, PhotoGalleryActivity.class);
                //        context.startActivity(intent);


                //      break;
                case R.id.rb04:
                    context = view.getContext();
                    mPersistenceObjDao.updateData("PERSIST_IV_ID", "0");
                    mPersistenceObjDao.updateData("PERSIST_IP_ID", "0");
                    intent = new Intent(context, AddNotes.class);
                    context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

                    break;
                case R.id.rb05:
                    context = view.getContext();
                    intent = new Intent(context, ListNotes.class);
                    context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

                    break;
            }
        });
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
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
}
