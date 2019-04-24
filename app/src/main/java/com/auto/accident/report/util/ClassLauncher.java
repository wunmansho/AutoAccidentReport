package com.auto.accident.report.util;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
/**
 * Created by myron on 12/3/2018.
 */

public class ClassLauncher {
    private final Context context;

    public ClassLauncher(Context context) {
        this.context = context;
    }

    public void launchActivity(String className) throws Exception {
        Intent intent = new Intent(context, getActivityClass(className));
        context.startActivity(intent);
    }

    private Class<? extends Activity> getActivityClass(String target) throws Exception {
        ClassLoader classLoader = context.getClassLoader();

        @SuppressWarnings("unchecked")
        Class<? extends Activity> activityClass = (Class<? extends Activity>) classLoader.loadClass(target);

        return activityClass;
    }
}