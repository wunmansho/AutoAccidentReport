package com.auto.accident.report.util;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by myron on 9/14/2018.
 */

public class showInstallOfflineVoiceFiles extends AppCompatActivity {
    public static final String PACKAGE_NAME_GOOGLE_NOW = "com.google.android.googlequicksearchbox";
    public static final String ACTIVITY_INSTALL_OFFLINE_FILES = "com.google.android.voicesearch.greco3.languagepack.InstallActivity";

    public static boolean showInstallOfflineVoiceFiles(@NonNull final Context ctx) {

        final Intent intent = new Intent();
        intent.setComponent(new ComponentName(PACKAGE_NAME_GOOGLE_NOW, ACTIVITY_INSTALL_OFFLINE_FILES));

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            ctx.startActivity(intent);
            return true;
        } catch (final ActivityNotFoundException e) {

        } catch (final Exception e) {

        }

        return false;
    }

}
