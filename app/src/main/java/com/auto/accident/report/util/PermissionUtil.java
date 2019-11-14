package com.auto.accident.report.util;
import android.Manifest;
import android.content.Context;

import pub.devrel.easypermissions.EasyPermissions;
import androidx.appcompat.app.AppCompatActivity;
public class PermissionUtil extends AppCompatActivity {

    public static boolean hasCameraPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA);
    }

    public static boolean hasReadExternalStoragePermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    public static boolean hasWriteExternalStoragePermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean hasRecordAudioPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.RECORD_AUDIO);
    }

    public static boolean hasAccessFineLocationPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static boolean hasCallPhonePermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.CALL_PHONE);
    }

    public static boolean hasReadPhoneStatePermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE);
    }

    public static boolean hasInternetPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.INTERNET);
    }
}
