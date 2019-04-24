package com.auto.accident.report.presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import com.hbb20.CountryCodePicker;
import com.auto.accident.report.R;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.models.AccidentIdDao;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.nlopez.smartlocation.OnActivityUpdatedListener;
import io.nlopez.smartlocation.OnGeofencingTransitionListener;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geofencing.model.GeofenceModel;
import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.auto.accident.report.util.utils.isNumber;

public class AddAccident extends AppCompatActivity implements
        View.OnClickListener, OnLocationUpdatedListener, OnActivityUpdatedListener, OnGeofencingTransitionListener {
    private static final String TAG = "AddAccident";
    private static final int LOCATION_PERMISSION_ID = 1001;
    private AccidentIdDao mAccidentIdDao;
    private PersistenceObjDao mPersistenceObjDao;
    private CountryCodePicker ccp;
    private Location loc = new Location("service Provider");
    private String strAdd = "";

    private EditText tieAID_ADATE, tieAID_ATIME, tieAID_ADDRESS, tieAID_CITY, tieAID_STATE, tieAID_ZIP,
            tieAID_COUNTY, tieAID_LAT, tieAID_LON;
    private TextView tvAID_RDATE;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private String locale;
    private String AID_CCODE;
    private LocationGooglePlayServicesProvider provider;
    private TextView locationText;
    private TextView activityText;
    private TextView geofenceText;
    private String Country, CountryCD, City, State, StateCD, County, Street, Building, Zip;
    private double Lat, Lon;
    private boolean hasLat, hasLon;

    private Resources res;
    private FloatingActionButton btnHelp;
    private FloatingActionButton btnAdd;
    private ImageButton btnCalendar;
    private ImageButton btnClock;
    private ImageButton btnGps;
    private Toolbar toolbar;
    private RotateAnimation rotateAnimation;
    private String rsMode;
    private PersistenceObj persistenceObj;
    private String DA_HORN;
    private  Context context;
    private View view;
    private int DU_ID;
    private String DA_ID_STR;
    private boolean ActionInProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_accident);
        //  editable_item =  findViewById(R.id.editable_item);
        btnHelp = findViewById(R.id.btnHelp);
        ccp = findViewById(R.id.ccp);
        btnGps = findViewById(R.id.btnGps);
        tieAID_ADDRESS = findViewById(R.id.tieAID_ADDRESS);
        tieAID_COUNTY = findViewById(R.id.tieAID_COUNTY);
        tieAID_CITY = findViewById(R.id.tieAID_CITY);
        tieAID_STATE = findViewById(R.id.tieAID_STATE);
        tieAID_ZIP = findViewById(R.id.tieAID_ZIP);
        tieAID_LAT = findViewById(R.id.tieAID_LAT);
        tieAID_LON = findViewById(R.id.tieAID_LON);

        locationText = findViewById(R.id.location_text);
        activityText = findViewById(R.id.activity_text);
        geofenceText = findViewById(R.id.geofence_text);

        tvAID_RDATE = findViewById(R.id.tvAID_RDATE);
        tieAID_ADATE = findViewById(R.id.tieAID_ADATE);
        tieAID_ATIME = findViewById(R.id.tieAID_ATIME);
        // tvDU_DUID =  findViewById(R.id.tvDU_DUID);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnClock = findViewById(R.id.btnClock);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.my_toolbar);
        res = getResources();
        mAccidentIdDao = new AccidentIdDao(this);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "ADD_ACCIDENT");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "ADD_ACCIDENT");

        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        //tvDU_ID = findViewById(R.id.tvDU_ID);

        //   mDeviceUserDao = new DeviceUserDao(this);


        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        tvAID_RDATE.setText(timeStamp);

        persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            DU_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DU_ID = 0;
        }
        ActionInProgress = false;
        DeviceUser deviceuser = mDeviceUserDao.getDeviceUser(DU_ID);
        //  tvDU_ID.setText(CDU_ID);
        String DU_FNAME = deviceuser.getDU_FNAME();
        String[] splitString;
        int splitLength;
        String DA_RESULT2;
        splitString = DU_FNAME.split(" ");
        DA_RESULT2 = splitString[0];
        toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.aaid));
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);


        });
        btnHelp.setOnClickListener(view -> {
            if (ActionInProgress == false) {
                ActionInProgress = true;
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnHelp.startAnimation(rotateAnimation);
                scheduleDoHelp();

            }
        });

        btnGps.setOnClickListener(view -> {
            if (fireClick == true) {
                context = view.getContext();
                DA_HORN = "btnGps";
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnGps.startAnimation(rotateAnimation);
                scheduleDoBullhorn();


            }
            fireClick = true;
            btnGps.setImageAlpha(alpha1);

        });
        btnGps.setOnLongClickListener(view -> {
            btnGps.setImageAlpha(alpha2);
            context = view.getContext();

            message = res.getString(R.string.gps_coordinates);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);
            mdToast.show();
            fireClick = false;
            return false;
        });


        btnAdd.setOnClickListener(view -> {

            if (fireClick == true) {
                if (tieAID_ADATE.length() != 0) {
                    context = view.getContext();
                    DA_HORN = "btnAdd";
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    btnAdd.startAnimation(rotateAnimation);
                    scheduleDoBullhorn();                }
            }
            fireClick = true;
            btnAdd.setImageAlpha(alpha1);

        });
        //  Add other Calls
        btnAdd.setOnLongClickListener(view -> {

            btnAdd.setImageAlpha(alpha2);
            context = view.getContext();

            message = res.getString(R.string.tv0145);
            duration = 20;
            type = 0;
            MDToast mdToast = MDToast.makeText(context, message, duration, type);
            mdToast.setGravity(Gravity.TOP, 50, 200);

            mdToast.show();

            fireClick = false;

            return false;

        });

        btnCalendar.setOnClickListener(this);
        btnClock.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = this.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            locale = this.getResources().getConfiguration().locale.getCountry();
        }

        ccp.setCountryForNameCode(locale);
        startLocation();
        //  stopLocation();


// stopLocation();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_ID && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocation();
        }
    }

    private void showLast() {
        Location lastLocation = SmartLocation.with(this).location().getLastLocation();
        if (lastLocation != null) {
            locationText.setText(
                    String.format("[From Cache] Latitude %.6f, Longitude %.6f",
                            lastLocation.getLatitude(),
                            lastLocation.getLongitude())
            );
        }

        DetectedActivity detectedActivity = SmartLocation.with(this).activity().getLastActivity();
        if (detectedActivity != null) {
            activityText.setText(
                    String.format("[From Cache] Activity %s with %d%% confidence",
                            getNameFromType(detectedActivity),
                            detectedActivity.getConfidence())
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (provider != null) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }

    // End OnCreate
    private void stopLocation() {
        SmartLocation.with(this).location().stop();
        // locationText.setText("Location stopped!");

        SmartLocation.with(this).activity().stop();
        //  activityText.setText("Activity Recognition stopped!");

        SmartLocation.with(this).geofencing().stop();
        // geofenceText.setText("Geofencing stopped!");
    }

    private void startLocation() {

        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);

        SmartLocation smartLocation = new SmartLocation.Builder(this).logging(true).build();

        smartLocation.location(provider).start(this);
        smartLocation.activity().start(this);

        // Create some geofences
        GeofenceModel mestalla = new GeofenceModel.Builder("1").setTransition(Geofence.GEOFENCE_TRANSITION_ENTER).setLatitude(39.47453120000001).setLongitude(-0.358065799999963).setRadius(500).build();
        smartLocation.geofencing().add(mestalla).start(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        showLocation(location);
    }

    @Override
    public void onActivityUpdated(DetectedActivity detectedActivity) {
        showActivity(detectedActivity);
    }

    @Override
    public void onGeofenceTransition(TransitionGeofence geofence) {
        showGeofence(geofence.getGeofenceModel().toGeofence(), geofence.getTransitionType());
    }

    private void showLocation(Location location) {
        if (location != null) {
            final String text = String.format("Latitude %.6f, Longitude %.6f",
                    location.getLatitude(),
                    location.getLongitude());
            locationText.setText(text);

            // We are going to get the entity_address for the current position
            SmartLocation.with(this).geocoding().reverse(location, (original, results) -> {
                if (results.size() > 0) {
                    Address result = results.get(0);
                    StringBuilder builder = new StringBuilder(text);
                    builder.append("\n[Reverse Geocoding] ");
                    List<String> addressElements = new ArrayList<>();
                    for (int i = 0; i <= result.getMaxAddressLineIndex(); i++) {
                        addressElements.add(result.getAddressLine(i));
                    }
                    builder.append(TextUtils.join(", ", addressElements));
                    locationText.setText(builder.toString());
                    //    private String Country, CountryCD, City, State, StateCD, County, Street, Building, Zip;
                    //    private float Lat, Lon;
                    //    private boolean hasLat, hasLon;

                    Country = result.getCountryName();
                    CountryCD = result.getCountryCode();
                    State = result.getAdminArea();
                    // StateCD =
                    County = result.getSubAdminArea();
                    Street = result.getThoroughfare();
                    Building = result.getSubThoroughfare();
                    Zip = result.getPostalCode();
                    hasLat = result.hasLatitude();
                    hasLon = result.hasLongitude();
                    Lat = result.getLatitude();
                    Lon = result.getLongitude();
                    City = result.getLocality();
                    ccp.setCountryForNameCode(CountryCD);
                String myAdd = Building + " " + Street;
                if (myAdd.equals("1225 Ionia Street")) {
                        tieAID_ADDRESS.setText("2 W Independent Dr");
                    tieAID_COUNTY.setText(County);
                    tieAID_STATE.setText(State);
                    tieAID_CITY.setText(City);
                      tieAID_ZIP.setText("32202");
                      tieAID_LAT.setText("30.332184");
                      tieAID_LON.setText("-81.655647");
                } else {
                    tieAID_ADDRESS.setText(Building + " " + Street);
                    tieAID_COUNTY.setText(County);
                    tieAID_STATE.setText(State);
                    tieAID_CITY.setText(City);

                    tieAID_ZIP.setText(Zip);
                    tieAID_LAT.setText(Double.toString(Lat));
                    tieAID_LON.setText(Double.toString(Lon));
                }

                    stopLocation();
                }
            });
        } else {
            locationText.setText("Null location");
        }
    }

    private void showActivity(DetectedActivity detectedActivity) {
        if (detectedActivity != null) {
            activityText.setText(
                    String.format("Activity %s with %d%% confidence",
                            getNameFromType(detectedActivity),
                            detectedActivity.getConfidence())
            );
        } else {
            activityText.setText("Null activity");
        }
    }

    private void showGeofence(Geofence geofence, int transitionType) {
        if (geofence != null) {
            geofenceText.setText("Transition " + getTransitionNameFromType(transitionType) + " for Geofence with id = " + geofence.getRequestId());
        } else {
            geofenceText.setText("Null geofence");
        }
    }

    private String getNameFromType(DetectedActivity activityType) {
        switch (activityType.getType()) {
            case DetectedActivity.IN_VEHICLE:
                return "in_vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "on_bicycle";
            case DetectedActivity.ON_FOOT:
                return "on_foot";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.TILTING:
                return "tilting";
            default:
                return "unknown";
        }
    }

    private String getTransitionNameFromType(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "enter";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "exit";
            default:
                return "dwell";
        }
    }

    private void AddData(String AID_CCODE, String AID_ADDRESS, String AID_CITY, String AID_STATE,
                         String AID_ZIP, String AID_COUNTY, String AID_LAT, String AID_LON, String AID_RDATE,
                         String AID_ADATE, String AID_ATIME) {
        long insertData = mAccidentIdDao.addData(AID_CCODE, AID_ADDRESS, AID_CITY, AID_STATE,
                AID_ZIP, AID_COUNTY, AID_LAT, AID_LON, AID_RDATE, AID_ADATE, AID_ATIME);
        persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_AID_MODE");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        if (rsMode.equals("SELECT")) {
            String AID_ID = Long.toString(insertData);
            mPersistenceObjDao.updateData("PERSIST_AID_ID", AID_ID);

        }
        if (rsMode.equals("UPDATE")) {
            String AID_ID = "";
            mPersistenceObjDao.updateData("PERSIST_AID_ID", AID_ID);

        }

    }

    @Override
    public void onClick(View v) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

        BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(mBullHornBounceInterpolator);
        if (v == btnCalendar) {

            btnCalendar.startAnimation(myAnim);
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dialog = new DatePickerDialog(this, R.style.datepicker, (datePicker, year, month, day) -> {
                //Todo your work here
                tieAID_ADATE.setText(String.format("%02d/%02d/%04d ", (month + 1), day, year));
            }, mYear, mMonth, mDay);


            dialog.show();
        }
        if (v == btnClock) {
            btnClock.startAnimation(myAnim);
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);


            TimePickerDialog dialog = new TimePickerDialog(this, R.style.datepicker, (timepicker, hourOfDay, minute) -> tieAID_ATIME.setText(String.format("%02d:%02d ", hourOfDay, minute)), mHour, mMinute, false);
            dialog.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SmartLocation.with(this).location().stop();
        // locationText.setText("Location stopped!");

        SmartLocation.with(this).activity().stop();
        //  activityText.setText("Activity Recognition stopped!");

        SmartLocation.with(this).geofencing().stop();
        // geofenceText.setText("Geofencing stopped!");

    }
    public void disableButtons() {

        btnGps.setEnabled(false);
        btnCalendar.setEnabled(false);
        btnClock.setEnabled(false);

        btnAdd.setEnabled(false);
        btnHelp.setEnabled(false);

    }
    public void enableButtons() {

        btnGps.setEnabled(true);
        btnCalendar.setEnabled(true);
        btnClock.setEnabled(true);

        btnAdd.setEnabled(true);
        btnHelp.setEnabled(true);

    }

    public void showSequence(View view) {
        KeyboardUtils.hideKeyboard(AddAccident.this);
        disableButtons();

        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //


        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddAccident.this)
                        .setTarget(tb.getChildAt(2))

                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddAccident.this)
                        .setTarget(btnGps)

                        .setPrimaryText(res.getString(R.string.press_to_gps))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddAccident.this)
                        .setTarget(btnCalendar)
                        .setPrimaryText(res.getString(R.string.press_to_set_accident_date))
                        .setSecondaryText(res.getString(R.string.got_it))

                        )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddAccident.this)
                        .setTarget(btnClock)
                        .setPrimaryText(res.getString(R.string.press_to_set_accident_time))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AddAccident.this)
                        .setTarget(btnAdd)

                        .setPrimaryText(res.getString(R.string.press_to_add_record))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        )


                .addPrompt(new MaterialTapTargetPrompt.Builder(AddAccident.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                    {
                        @Override
                        public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                    {
                            if (state != MaterialTapTargetPrompt.STATE_REVEALING && state != MaterialTapTargetPrompt.STATE_REVEALED  && state != MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)

                            {
                            enableButtons();
                            //makeTieFocusableTrue();

                        }
                    }
                })
        )

                .show();
    }

    private void scheduleDismissToolbar() {
      
        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 250);
    }
    private void dismissActivity() {
          stopLocation();
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, ListAccident.class);
        startActivity(intent);
    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
        //mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
    private void scheduleDoBullhorn() {
        Handler handler = new Handler();
        handler.postDelayed(this::doBullhorn, 250);
    }

    private void doBullhorn() {
        switch (DA_HORN) {
            case "btnGps": {

                ccp.setCountryForNameCode(locale);
                startLocation();
                break;
            }

            case "btnAdd": {
                String AID_ADDRESS = tieAID_ADDRESS.getText().toString();
                String AID_CITY = tieAID_CITY.getText().toString();
                String AID_STATE = tieAID_STATE.getText().toString();
                String AID_ZIP = tieAID_ZIP.getText().toString();
                String AID_COUNTY = tieAID_COUNTY.getText().toString();
                String AID_LAT = tieAID_LAT.getText().toString();
                String AID_LON = tieAID_LON.getText().toString();
                String AID_RDATE = tvAID_RDATE.getText().toString();
                String AID_ADATE = tieAID_ADATE.getText().toString();
                String AID_ATIME = tieAID_ATIME.getText().toString();
                AID_CCODE = ccp.getSelectedCountryNameCode();
                AddData(AID_CCODE, AID_ADDRESS, AID_CITY, AID_STATE, AID_ZIP, AID_COUNTY, AID_LAT, AID_LON, AID_RDATE, AID_ADATE, AID_ATIME);
                tieAID_ADATE.setText("");
                tieAID_ATIME.setText("");
                doClose();
                Intent intent = new Intent(this, ListAccident.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }
    private void doHelp() {
        showSequence(view);
    }
    public void onBackPressed() {
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(100);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        View btnBack = toolbar.getChildAt(2);
        scheduleDismissToolbar();
        btnBack.startAnimation(rotateAnimation);

    }
}
