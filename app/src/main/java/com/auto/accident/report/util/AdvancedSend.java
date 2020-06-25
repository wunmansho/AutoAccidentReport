package com.auto.accident.report.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;

import com.auto.accident.report.R;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.PersistenceObj;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by myron on 11/12/2018.
 */

public class AdvancedSend extends AppCompatActivity {


    private static String ACC_NUM;
    private static String albumName = "AccidentReport";
    private static File[] listFile;
    private static File path;
    private static PersistenceObj persistenceObj;
    private static PersistenceObjDao mPersistenceObjDao;
    private static String Email;


    public static boolean advancedSend(Context context, Resources res) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("text/plain");
        mPersistenceObjDao = new PersistenceObjDao(context);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        ACC_NUM = persistenceObj.getPERSISTENCE_VALUE();
       String ACCIDENT_REPORT_PREFIX = "AccidentReport000" + ACC_NUM;



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

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //  this.startActivity(intent);
      //  email_email
        Email = res.getString(R.string.email_email);
       context.startActivity(Intent.createChooser(intent, Email +":"));
       return true;


    }

}
