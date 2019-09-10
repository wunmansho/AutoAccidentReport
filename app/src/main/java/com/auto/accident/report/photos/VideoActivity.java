package com.auto.accident.report.photos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.auto.accident.report.database.DeviceImageStoreDao;
import com.auto.accident.report.database.InvolvedImageStoreDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.presenter.MultiMediaMenu;
import com.auto.accident.report.util.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by myron on 11/30/2018.
 */

public class VideoActivity extends AppCompatActivity {
    private static final String MP4_FILE_PREFIX = "ACC_";
    private static final String MP4_FILE_SUFFIX = ".mp4";
    private static final int ACTIVITY_START_VIDEO_APP = 2;
    Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private InvolvedImageStoreDao mInvolvedImageStoreDao;
    private DeviceImageStoreDao mDeviceImageStoreDao;
    private ImageView mPhotoCapturedImageView;
    private String mVideoFileLocation = "";
    private File mStorageDirectory;
    private String videoFileName;
    private String videoF;
    private File mFile;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mInvolvedImageStoreDao = new InvolvedImageStoreDao(this);
        mDeviceImageStoreDao = new DeviceImageStoreDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        File videoFile = null;
        try {
            videoFile = createvideoFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //if (Build.VERSION.SDK_INT < 24) {
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
       //      Uri yuri = Uri.fromFile(videoFile);
       // Uri xuri = FileProvider.getUriForFile(VideoActivity.this, BuildConfig.APPLICATION_ID + ".provider", videoFile);

        //} else {
           // callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(VideoActivity.this, BuildConfig.APPLICATION_ID + ".provider", videoFile));
        //}
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 0);
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_VIDEO_APP);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_START_VIDEO_APP && resultCode == RESULT_OK) {
            Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();


            PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
            String rsMode = persistenceObj.getPERSISTENCE_VALUE();
            videoFileName = utils.getPictureName(mVideoFileLocation);
            if (rsMode.equals("ACCIDENT") || rsMode.equals("INVOLVED_PARTY") || rsMode.equals("INVOLVED_VEHICLE")) {

                long imageAdded = mInvolvedImageStoreDao.addData(mVideoFileLocation, videoFileName);
            }
            if (rsMode.equals("DEVICE") || rsMode.equals("DEVICE_USER") || rsMode.equals("DEVICE_VEHICLE")) {

                long imageAdded = mDeviceImageStoreDao.addData(mVideoFileLocation, videoFileName);
            }



            mPersistenceObjDao.updateData("PERSIST_AP_FILENAME", videoFileName);


        }
        doClose();
        intent = new Intent(this, MultiMediaMenu.class);
        startActivity(intent);
        finish();
    }

    private File createvideoFile() throws IOException {
        String albumName = "AccidentReport";
        File mStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!mStorageDirectory.exists()) {
            mStorageDirectory.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        videoFileName = MP4_FILE_PREFIX + timeStamp;

        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        String rsMode = persistenceObj.getPERSISTENCE_VALUE();
        Boolean y = true;
        if (rsMode.equals("ACCIDENT") || rsMode.equals("INVOLVED_PARTY") || rsMode.equals("INVOLVED_VEHICLE")) {
            for (int x = 1; y; x++) {
                String tryFileName = videoFileName + Integer.toString(x) + MP4_FILE_SUFFIX;
                y = mInvolvedImageStoreDao.getImage(tryFileName);
                if (x == 30) {
                    break;
                }
                if (!y) {
                    videoFileName = videoFileName + Integer.toString(x);
                }
            }
        }
        if (rsMode.equals("DEVICE") || rsMode.equals("DEVICE_USER") || rsMode.equals("DEVICE_VEHICLE")) {
            for (int x = 1; y; x++) {
                String tryFileName = videoFileName + Integer.toString(x) + MP4_FILE_SUFFIX;
                y = mDeviceImageStoreDao.getImage(tryFileName);
                if (x == 30) {
                    break;
                }
                if (!y) {
                    videoFileName = videoFileName + Integer.toString(x);
                }
            }


        }
        File image = File.createTempFile(videoFileName, MP4_FILE_SUFFIX, mStorageDirectory);
        mVideoFileLocation = image.getAbsolutePath();


        return image;

    }
    public void doClose() {
        // mInsurancePolicyVDao.closeAll();
        mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        mInvolvedImageStoreDao.closeAll();
        //  mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();

        //  mDeviceUserDao.closeAll();

        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
    @Override
    public void onBackPressed() {
        doClose();
        intent = new Intent(this, MultiMediaMenu.class);
        startActivity(intent);
        finish();
    }
}
