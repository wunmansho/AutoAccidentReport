package com.auto.accident.report.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.auto.accident.report.R;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.presenter.MDToast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

//import com.auto.accident.report.objects.ApplicationContextProvider;
//import com.auto.accident.report.presenter.PdfPrint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.view.Menu;
//import android.view.MenuItem;

//import static com.auto.accident.report.objects.ApplicationContextProvider.getContext;

/**
 * Created by myron on 11/11/2018.
 */



public class FirebaseCloudUpload extends AppCompatActivity {


    private static final String TAG = "FirebaseUpload";




    private static FirebaseAuth mAuth;

    private static Uri mDownloadUrl = null;
    private static Uri mFileUri = null;
    private static String ACCIDENT_REPORT_PREFIX;
    private static String ACC_NUM;
    private static String albumName = "AccidentReport";
    private static File path;
    private static File[] listFile;

    private static StorageReference mStorageRef;
    private static PersistenceObjDao mPersistenceObjDao;
    private static String message;
    private static int duration;
    private static int type;
    private static int PageI;
    private static String PageS;
    private static int PagesI;
    private static String PagesS;
    private static int PagesUploaded;
    private static int i = 0;
    private static ArrayList<Uri> uri;
    //


    public  static void uploadToFirebase(Context context, Resources res) {
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mPersistenceObjDao = new PersistenceObjDao(context);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
        signInAnonymously(context, res);


    }




    private static void prepareUpload(Context context, Resources res) {

        // TODO  get yourself a real accident number
        ACCIDENT_REPORT_PREFIX = "AccidentReport000" + ACC_NUM;


        uri = new ArrayList<Uri>();

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

            i = 0;
            PagesI = listFile.length;
            PagesS = Integer.toString(PagesI);
            PagesUploaded = 0;

                PageI = i + 1;
                PageS = Integer.toString(PageI);
                mFileUri = Uri.fromFile(listFile[i]);
                String FileNameString = listFile[i].getName();
                uploadFromUri(mFileUri, FileNameString, PagesS, PageS, context, res);

                uri.add(Uri.fromFile(listFile[i]));

        }

       // Intent intent = new Intent(this, PdfPrint.class);
     //   startActivity(intent);


    }

    private static void uploadFromUri(Uri fileUri, String FileNameString, String PagesS, String PageS, Context context, Resources res ) {
        Log.d(TAG, "uploadFromUri:src:" + fileUri.toString());


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
                     //   Context context = getContext();
                      //  Resources res = getResources();
                        if (PagesI == PagesUploaded) {
                            message = PagesS + " " + res.getString(R.string.of) + " " + PagesS +  " " + res.getString(R.string.pages) +  " "+ res.getString(R.string.successfully_uploaded) +  " " + res.getString(R.string.to_firebase_storage) +  " " + res.getString(R.string.upload_complete);

                        } else {

                            message = res.getString(R.string.page) + " " + PageS + " " + res.getString(R.string.of) + " " + PagesS + " " + res.getString(R.string.pages) + " " + res.getString(R.string.successfully_uploaded)  +  " " + res.getString(R.string.to_firebase_storage);
                            i++;
                            PageI = i + 1;
                            final String PageS = Integer.toString(PageI);
                            mFileUri = Uri.fromFile(listFile[i]);
                            String FileNameString = listFile[i].getName();
                            uploadFromUri(mFileUri, FileNameString, PagesS, PageS, context, res);

                            uri.add(Uri.fromFile(listFile[i]));
                        }
                        duration = 0;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                        if (PagesI == PagesUploaded) {
                            mPersistenceObjDao.updateData("FIREBASE_CLOUD_UPLOAD_IN_PROGRESS", "false");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                      //  Context context = getContext();
                      //  Resources res = getResources();
                        message = res.getString(R.string.page) + " " + PageS + " " + res.getString(R.string.of) + " " +  PagesS + " " + res.getString(R.string.pages) + " " + res.getString(R.string.failed_upload);
                        duration = 1;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                    }
                });

    }


    private static void signInAnonymously(Context context, Resources res) {
        // Sign in anonymously. Authentication is required to read or write from Firebase Storage.

        mAuth.signInAnonymously()
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "signInAnonymously:SUCCESS");


                     //   Context context = getContext();
                      //  Resources res = getResources();
                        message = res.getString(R.string.firebase_login_success);
                        duration = 1;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                        prepareUpload(context, res);
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);


                     //   Context context = getContext();
                     //   Resources res = getResources();
                        message = res.getString(R.string.firebase_login_fail);
                        duration = 20;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                    }
                });
    }

    private static void signInAnonymously2(Context context, Resources res) {
        // Sign in anonymously. Authentication is required to read or write from Firebase Storage.

        mAuth.signInAnonymously()
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "signInAnonymously:SUCCESS");


                        //   Context context = getContext();
                        //  Resources res = getResources();
                        message = res.getString(R.string.firebase_login_success);
                        duration = 1;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                        prepareUpload(context, res);
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);


                        //   Context context = getContext();
                        //   Resources res = getResources();
                        message = res.getString(R.string.firebase_login_fail);
                        duration = 20;
                        type = 0;
                        MDToast mdToast = MDToast.makeText(context, message, duration, type);
                        mdToast.setGravity(Gravity.TOP, 50, 200);

                        mdToast.show();
                    }
                });
    }














}
