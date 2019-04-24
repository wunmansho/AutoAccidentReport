package com.auto.accident.report.util;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.auto.accident.report.BuildConfig;
import com.auto.accident.report.R;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.Locale;

/**
 * Created by myron on 5/20/2018.
 */

public class utils extends AppCompatActivity {
    private static final String configLocale = Locale.getDefault().getCountry().toLowerCase();
    private static final String TRUE = "true";
    private static final String FALSE = "false";
 //   private static final Context context = ApplicationContextProvider.getContext();
    private static PersistenceObjDao mPersistenceObjDao;
    private static PersistenceObj persistenceObj;

    //





    public static String extractCharacter(String DA_RESULT) {
        if (DA_RESULT != null) {
            DA_RESULT = DA_RESULT.replaceAll("'", "");

        }
        if (DA_RESULT == null) {
            DA_RESULT = "";

        }


        return DA_RESULT;
    }

    public static String getFirstWord(String DA_RESULT2) {
        String[] splitString;
        int splitLength;
        String DA_RESULT = "";
        if (!DA_RESULT2.equals(null)) {
            String[] words = DA_RESULT2.split(" ");
            splitLength = words.length;
            int index = 0;
            DA_RESULT = words[0];
        }
        return DA_RESULT;
    }

    public static String getSecondWord(String DA_RESULT2) {
        int splitLength;
        int index = 1;
        StringBuilder DA_RESULT = new StringBuilder();
        String[] words = DA_RESULT2.split(" ");
        splitLength = words.length;
        while (index < splitLength) {
            DA_RESULT.append(words[index]).append(" ");
            index++;
        }
        return DA_RESULT.toString();
    }

    public static String getThirdWord(String DA_RESULT2) {
        int splitLength;
        int index = 2;
        StringBuilder DA_RESULT = new StringBuilder();
        String[] words = DA_RESULT2.split(" ");
        splitLength = words.length;
        while (index < splitLength) {
            DA_RESULT.append(words[index]).append(" ");
            index++;
        }
        return DA_RESULT.toString();
    }

    public static String getFourthWord(String DA_RESULT2) {
        int splitLength;
        int index = 3;
        StringBuilder DA_RESULT = new StringBuilder();
        String[] words = DA_RESULT2.split(" ");
        splitLength = words.length;
        while (index < splitLength) {
            DA_RESULT.append(words[index]).append(" ");
            index++;
        }
        return DA_RESULT.toString();
    }

    public static String getFifthWord(String DA_RESULT2) {
        int splitLength;
        int index = 4;
        StringBuilder DA_RESULT = new StringBuilder();
        String[] words = DA_RESULT2.split(" ");
        splitLength = words.length;
        while (index < splitLength) {
            DA_RESULT.append(words[index]).append(" ");
            index++;
        }
        return DA_RESULT.toString();
    }

    public static String removeSpaces(String DA_RESULT2) {
        int splitLength;
        int index = 0;
        StringBuilder DA_RESULT = new StringBuilder();
        String[] words = DA_RESULT2.split(" ");
        splitLength = words.length;
        while (index < splitLength) {
            DA_RESULT.append(words[index]);
            index++;
        }
        return DA_RESULT.toString();
    }

    public static String removeDashes(String DA_RESULT2) {
        int splitLength;
        int index = 0;
        StringBuilder DA_RESULT = new StringBuilder();
        String[] words = DA_RESULT2.split("-");
        splitLength = words.length;
        while (index < splitLength) {
            DA_RESULT.append(words[index]);
            index++;
        }
        DA_RESULT = new StringBuilder(removePluses(DA_RESULT.toString()));
        return DA_RESULT.toString();
    }

    public static String getPictureName(String DA_RESULT) {
        int splitLength;
        String[] words = DA_RESULT.split("/");
        splitLength = words.length;
        splitLength = splitLength - 1;
        DA_RESULT = words[splitLength];
        return DA_RESULT;
    }

    public static String removePluses(String DA_RESULT) {
        DA_RESULT = DA_RESULT.replaceAll("[^a-zA-Z0-9]", "");

        return DA_RESULT;
    }

    public static String capEachWord(String DA_RESULT) {
        int splitLength;
        int index = 0;
        String[] words = DA_RESULT.split(" ");
        splitLength = words.length;
        StringBuilder DA_RESULTBuilder = new StringBuilder();
        while (index < splitLength) {
            int DA_SIZE = words[index].length();
            words[index] = words[index].substring(0, 1).toUpperCase() + words[index].substring(1, DA_SIZE);
            DA_RESULTBuilder.append(words[index]);
            if (index != splitLength) {
                DA_RESULTBuilder.append(" ");
            }
            index++;
        }
        DA_RESULT = DA_RESULTBuilder.toString();

        return DA_RESULT;
    }

    public static String capEachSentence(String DA_RESULT) {
        int splitLength;
        int index = 0;
        String[] words = DA_RESULT.split("\\.");
        splitLength = words.length;
        StringBuilder DA_RESULTBuilder = new StringBuilder();
        while (index < splitLength) {
            int DA_SIZE = words[index].length();
            words[index] = words[index].substring(0, 1).toUpperCase() + words[index].substring(1, DA_SIZE);
            DA_RESULTBuilder.append(words[index]);
            if (index != splitLength) {
                DA_RESULTBuilder.append(". ");
            }
            index++;
        }
        DA_RESULT = DA_RESULTBuilder.toString();

        return DA_RESULT;
    }

    public static String capSentence(String DA_RESULT) {
        int DA_SIZE = DA_RESULT.length();
        DA_RESULT = DA_RESULT.substring(0, 1).toUpperCase() + DA_RESULT.substring(1, DA_SIZE);


        return DA_RESULT;
    }

    public static boolean isNumber(String string) {
        return string.matches("^\\d+$");
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static String removeBadChars(String DA_RESULT) {
       // int splitLength;
      //  int index = 0;
     //   StringBuilder DA_RESULT = new StringBuilder();
      //  String[] words = DA_RESULT2.split("=\n");
     //   splitLength = words.length;
     //   while (index < splitLength) {
       //     DA_RESULT.append(words[index]);
       //     index++;
       // }
        String[] words = DA_RESULT.split("=\n");
               DA_RESULT = words[0];
               DA_RESULT = removePluses(DA_RESULT);
      //  return DA_RESULT.toString();
        return DA_RESULT;
    }
    public static String splitDash(String DA_RESULT2) {
        String[] splitString;
        int splitLength;
        String DA_RESULT = "";
        if (!DA_RESULT2.equals(null)) {
            String[] words = DA_RESULT2.split("-");
            splitLength = words.length;
            int index = 0;
            DA_RESULT = words[0];
        }
        return DA_RESULT;
    }
    public static String splitDash2(String DA_RESULT2) {
        String[] splitString;
        int splitLength;
        String DA_RESULT = "";
        if (!DA_RESULT2.equals(null)) {
            String[] words = DA_RESULT2.split("-");
            splitLength = words.length;
            int index = 0;
            if (splitLength == 2) {
                DA_RESULT = words[1];
            }
            if (splitLength == 1) {
                DA_RESULT = words[0];
            }
        }
        return DA_RESULT;
    }
    public static String splitFileExt(String DA_RESULT2) {
        String[] splitString;
        int splitLength;
        String DA_RESULT = "";
        if (!DA_RESULT2.equals(null)) {
            DA_RESULT2 = getPictureName(DA_RESULT2);
            String[] words = DA_RESULT2.split("\\.");
            splitLength = words.length;
            int index = 0;
            if (splitLength == 2) {
                DA_RESULT = words[1];
            }
            if (splitLength == 1) {
                DA_RESULT = "none";
            }
        }
        return DA_RESULT;
    }
    public static String getRemoteConfig(String DA_CONFIG, Activity activity, Context context) {
        FirebaseApp.initializeApp(context);
        FirebaseRemoteConfig mFirebaseRemoteConfig;

        // Get Remote Config instance.
        // [START get_remote_config_instance]
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        // Create a Remote Config Setting to enable developer mode, which you can use to increase
        // the number of fetches available per hour during development. See Best Practices in the
        // README for more information.
        // [START enable_dev_mode]
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]

        // Set default Remote Config parameter values. An app uses the in-app default values, and
        // when you need to adjust those defaults, you set an updated value for only the values you
        // want to change in the Firebase console. See Best Practices in the README for more
        // information.
        // [START set_default_values]
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // [END set_default_values]

        //    fetchWelcome();
        String DA_RESULT = mFirebaseRemoteConfig.getString(DA_CONFIG);

        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //    Toast.makeText(NavigationTabExample.this, "Fetch Succeeded",
                            //       Toast.LENGTH_SHORT).show();

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            //         Toast.makeText(NavigationTabExample.this, "Fetch Failed",
                            //               Toast.LENGTH_SHORT).show();
                        }
                        //    displayWelcomeMessage();
                    }
                });
        // [END fetch_config_with_callback]


        return DA_RESULT;
    }

    public  static String getPersistantRemoteConfig(String PERSISTENCE_KEY, Context context) {
        mPersistenceObjDao = new PersistenceObjDao(context);
        persistenceObj = mPersistenceObjDao.getPersistence(PERSISTENCE_KEY);
        if (persistenceObj == null) {
            String PERSISTENCE_VALUE = "false";
            return PERSISTENCE_VALUE;
        } else {
            String PERSISTENCE_VALUE = persistenceObj.getPERSISTENCE_VALUE();
            return PERSISTENCE_VALUE;
        }

    }
}
