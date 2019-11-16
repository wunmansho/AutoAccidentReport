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

import com.auto.accident.report.models.DeviceImageStoreDao;
import com.auto.accident.report.models.InvolvedImageStoreDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.presenter.MultiMediaMenu;
import com.auto.accident.report.util.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    private static final String JPEG_FILE_PREFIX = "ACC_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    Context context;
    private PersistenceObj persistenceObj;
    private PersistenceObjDao mPersistenceObjDao;
    private InvolvedImageStoreDao mInvolvedImageStoreDao;
    private DeviceImageStoreDao mDeviceImageStoreDao;
    private ImageView mPhotoCapturedImageView;
    private String mImageFileLocation = "";
    private File mStorageDirectory;
    private String imageFileName;
    private String imageF;
    private File mFile;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mPersistenceObjDao = new PersistenceObjDao(this);
        mInvolvedImageStoreDao = new InvolvedImageStoreDao(this);
        mDeviceImageStoreDao = new DeviceImageStoreDao(this);

        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
     //   if (Build.VERSION.SDK_INT < 24) {
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
    //    } else {
     //       callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(CameraActivity.this, BuildConfig.APPLICATION_ID + ".provider", photoFile));
      //  }
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);


    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();

            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
            String rsMode = persistenceObj.getPERSISTENCE_VALUE();
            imageFileName = utils.getPictureName(mImageFileLocation);
            if (rsMode.equals("ACCIDENT") || rsMode.equals("INVOLVED_PARTY") || rsMode.equals("INVOLVED_VEHICLE")) {
                 long imageAdded = mInvolvedImageStoreDao.addData(mImageFileLocation, imageFileName);
            }
            if (rsMode.equals("DEVICE") || rsMode.equals("DEVICE_USER") || rsMode.equals("DEVICE_VEHICLE")) {
                  long imageAdded = mDeviceImageStoreDao.addData(mImageFileLocation, imageFileName);
            }


            mPersistenceObjDao.updateData("PERSIST_AP_FILENAME", imageFileName);


        }
        doClose();
        intent = new Intent(this, MultiMediaMenu.class);
        startActivity(intent);
        finish();
    }

    private File createImageFile() throws IOException {
        String albumName = "AccidentReport";
        File mStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!mStorageDirectory.exists()) {
            mStorageDirectory.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = JPEG_FILE_PREFIX + timeStamp;
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        String rsMode = persistenceObj.getPERSISTENCE_VALUE();
        Boolean y = true;
        if (rsMode.equals("ACCIDENT") || rsMode.equals("INVOLVED_PARTY") || rsMode.equals("INVOLVED_VEHICLE")) {
            for (int x = 1; y; x++) {
                String tryFileName = imageFileName + Integer.toString(x) + JPEG_FILE_SUFFIX;
                y = mInvolvedImageStoreDao.getImage(tryFileName);
                if (x == 30) {
                    break;
                }
                if (!y) {
                    imageFileName = imageFileName + Integer.toString(x);
                }
            }
        }
        if (rsMode.equals("DEVICE") || rsMode.equals("DEVICE_USER") || rsMode.equals("DEVICE_VEHICLE")) {
            for (int x = 1; y; x++) {
                String tryFileName = imageFileName + Integer.toString(x) + JPEG_FILE_SUFFIX;
                y = mDeviceImageStoreDao.getImage(tryFileName);
                if (x == 30) {
                    break;
                }
                if (!y) {
                    imageFileName = imageFileName + Integer.toString(x);
                }
            }


        }
        File image = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, mStorageDirectory);
        mImageFileLocation = image.getAbsolutePath();


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
