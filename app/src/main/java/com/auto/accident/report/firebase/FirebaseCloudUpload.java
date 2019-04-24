package com.auto.accident.report.firebase;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.auto.accident.report.R;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.presenter.MDToast;
import com.auto.accident.report.presenter.PdfPrint;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import static com.auto.accident.report.objects.ApplicationContextProvider.getContext;


/**
 * Created by myron on 10/14/2018.
 */

public class FirebaseCloudUpload extends AppCompatActivity {
    private static final String TAG = "FirebaseUpload";

    private static final int RC_TAKE_PICTURE = 101;

    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";

    private BroadcastReceiver mBroadcastReceiver;
     private FirebaseAuth mAuth;

    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    private String ACCIDENT_REPORT_PREFIX;
    private String ACC_NUM;
    private final String albumName = "AccidentReport";
    private File path;
    private File[] listFile;

    private StorageReference mStorageRef;
    private PersistenceObjDao mPersistenceObjDao;
    private String message;
    private int duration;
    private int type;
    private int PageI;
    private String PageS;
    private int PagesI;
    private String PagesS;
    private int PagesUploaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.firebase_upload);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // Click listeners
    //    findViewById(R.id.buttonCamera).setOnClickListener(this);
      //  findViewById(R.id.buttonSignIn).setOnClickListener(this);
     //   findViewById(R.id.buttonDownload).setOnClickListener(this);

        // Restore instance state
        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
        }
        mPersistenceObjDao = new PersistenceObjDao(this);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        signInAnonymously();

        // Local broadcast receiver
        //  mBroadcastReceiver = new BroadcastReceiver() {
        //     @Override
        //   public void onReceive(Context context, Intent intent) {
        //       Log.d(TAG, "onReceive:" + intent);

        //    switch (intent.getAction()) {
        //      case MyUploadService.UPLOAD_COMPLETED:
        //     case MyUploadService.UPLOAD_ERROR:
        //     onUploadResultIntent(intent);
        //    break;
        //  }
        // }
        //  };
    }


    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }

    private void prepareUpload() {

        // TODO  get yourself a real accident number
        ACCIDENT_REPORT_PREFIX = "AccidentReport000" + ACC_NUM;


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
            PagesI = listFile.length;
            PagesS = Integer.toString(PagesI);
            PagesUploaded = 0;
            for (int i = 0; i < PagesI; i++) {
                PageI = i + 1;
                PageS = Integer.toString(PageI);
                // Get the path of the image file
                //       if (i == 0) {
                mFileUri = Uri.fromFile(listFile[i]);
                String FileNameString = listFile[i].getName();
                uploadFromUri(mFileUri, FileNameString, PagesS, PageS);
                //   }
                uri.add(Uri.fromFile(listFile[i]));
                j = i;
                if (j >= 4) {
                    j = 0;
                }
            }
        }

        Intent intent = new Intent(this, PdfPrint.class);
        startActivity(intent);


    }

    private void uploadFromUri(Uri fileUri, String FileNameString, String PagesS, String PageS ) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());

        // Save the File URI
        //    mFileUri = fileUri;

        // Clear the last download, if any
        updateUI(mAuth.getCurrentUser());
        mDownloadUrl = null;
        //     StorageReference riversRef = mStorageRef.child(FileString);
        StorageReference riversRef = mStorageRef.child("reports/" + FileNameString);
        riversRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        PagesUploaded++;
                        Context context = getContext();
                        Resources res1 = getResources();
                        if (PagesI == PagesUploaded) {
                            message = PagesS + " " + res1.getString(R.string.of) + " " + PagesS +  " " + res1.getString(R.string.pages) +  " "+ res1.getString(R.string.successfully_uploaded) +  " " + res1.getString(R.string.to_firebase_storage) +  " " + res1.getString(R.string.upload_complete);
                            mPersistenceObjDao.updateData("FIREBASE_CLOUD_UPLOAD_IN_PROGRESS", "false");
                        } else {

                            message = res1.getString(R.string.page) + " " + PageS + " " + res1.getString(R.string.of) + " " + PagesS + " " + res1.getString(R.string.pages) + " " + res1.getString(R.string.successfully_uploaded)  +  " " + res1.getString(R.string.to_firebase_storage);
                    }
                        duration = 1;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Context context = getContext();
                        Resources res1 = getResources();
                        message = res1.getString(R.string.page) + " " + PageS + " " + res1.getString(R.string.of) + " " +  PagesS + " " + res1.getString(R.string.pages) + " " + res1.getString(R.string.failed_upload);
                        duration = 20;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                    }
                });
        // Start MyUploadService to upload the file, so that the file is uploaded
        // even if this Activity is killed or put in the background
        //   startService(new Intent(this, MyUploadService.class)
        //        .putExtra(MyUploadService.EXTRA_FILE_URI, fileUri)
        //        .setAction(MyUploadService.ACTION_UPLOAD));

        // Show loading spinner
            }


    private void signInAnonymously() {
        // Sign in anonymously. Authentication is required to read or write from Firebase Storage.
        mAuth.signInAnonymously()
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "signInAnonymously:SUCCESS");
                         updateUI(authResult.getUser());
                        Context context = getContext();
                        Resources res1 = getResources();
                        message = res1.getString(R.string.firebase_login_success);
                        duration = 1;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                        prepareUpload();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);
                         updateUI(null);
                        Context context = getContext();
                        Resources res1 = getResources();
                        message = res1.getString(R.string.firebase_login_fail);
                        duration = 20;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        // Signed in or Signed out
        if (user != null) {
        //    findViewById(R.id.layoutSignin).setVisibility(View.GONE);
      //      findViewById(R.id.layoutStorage).setVisibility(View.VISIBLE);
        } else {
       //     findViewById(R.id.layoutSignin).setVisibility(View.VISIBLE);
      //      findViewById(R.id.layoutStorage).setVisibility(View.GONE);
        }

        // Download URL and Download button

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.firebase_cloud_upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            updateUI(null);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

  //  @Override
 //   public void onClick(View v) {
  //      int i = v.getId();
   //     if (i == R.id.buttonCamera) {

    //        prepareUpload();
     //   } else if (i == R.id.buttonSignIn) {
      //      signInAnonymously();
     //   }
   // }
}

