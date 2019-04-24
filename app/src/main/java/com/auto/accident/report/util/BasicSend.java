package com.auto.accident.report.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import com.auto.accident.report.R;

/**
 * Created by myron on 11/23/2018.
 */

public class BasicSend  extends AppCompatActivity {
       private static String Email;


    public static boolean basicSend(Context context, Resources res, String EMAIL) {
      //  Intent intent = new Intent(Intent.ACTION_SEND);

        //  this.startActivity(intent);
        //  email_email
        Email = res.getString(R.string.email_email);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { EMAIL });
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
        return true;


    }
}
