package com.auto.accident.report.util;

import android.content.res.Resources;

public class SystemUtils {
    public static int getScreenOrientation() {
        return Resources.getSystem().getConfiguration().orientation;
    }
}
