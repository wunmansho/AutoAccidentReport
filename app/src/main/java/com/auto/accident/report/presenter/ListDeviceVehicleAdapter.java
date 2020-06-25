package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.database.DeviceImageStoreDao;
import com.auto.accident.report.database.DeviceVehicleDao;
import com.auto.accident.report.database.InvolvedVehicleDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.DeviceImageStore;
import com.auto.accident.report.model.DeviceVehicle;
import com.auto.accident.report.model.InvolvedVehicle;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.photos.CameraActivity;
import com.auto.accident.report.photos.PhotoGalleryActivity;

import java.util.ArrayList;
import java.util.List;

import static com.auto.accident.report.util.utils.isNumber;
import com.auto.accident.report.database.AppDatabase;
import com.auto.accident.report.database.AppExecutors;
import com.auto.accident.report.model.ApplicationContextProvider;
//import androidx.appcompat.app.AppCompatActivity;
/**
 * Created by myron on 3/12/2018.
 */

class ListDeviceVehicleAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context context;
    private InvolvedVehicleDao mInvolvedVehicleDao;

    private final PersistenceObjDao mPersistenceObjDao;
    private final DeviceImageStoreDao mDeviceImageStoreDao;
    //  String [] result0;
    private final ArrayList<String> rsDV_ID = new ArrayList<>();
    ArrayList<String> rsDV_AID = new ArrayList<>();
    private final ArrayList<String> rsDV_MAKE = new ArrayList<>();
    private final ArrayList<String> rsDV_MODEL = new ArrayList<>();
    ArrayList<String> rsDV_LNAME = new ArrayList<>();
    private final ArrayList<String> rsDV_YEAR = new ArrayList<>();
    private final ArrayList<String> rsDV_TYPE = new ArrayList<>();
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Boolean fireClick = true;
    private String message;

    private InvolvedVehicle involvedvehicle;
    private String rsMode;
    private PersistenceObj persistenceObj;
    private Intent intent;
    private RotateAnimation rotateAnimation;

    private int DV_ID;
    private int DV_ID1;

    private int pos;

    private int XX_AID;
    private String DA_ID;
    private String DA_ID_STR;
    private String PERSIST_ACTION_IN_PROGRESS;
    private AppDatabase mDb;
    public ListDeviceVehicleAdapter(ListDeviceVehicle ListDeviceVehicle) {
        // TODO Auto-generated constructor stub
        context = ListDeviceVehicle;
        mDeviceImageStoreDao = new DeviceImageStoreDao(context);
        mPersistenceObjDao = new PersistenceObjDao(context);
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_DEVICE_VEHICLE");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "DEVICE_VEHICLE");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_DEVICE_VEHICLE");
        mDb = AppDatabase.getInstance(ApplicationContextProvider.getContext());


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                List<DeviceVehicle> devicevehicleList;

                devicevehicleList = mDb.mDeviceVehicleDao().loadAllDeviceVehicles();
                for (DeviceVehicle deviceVehicle : devicevehicleList) {
                    rsDV_ID.add(Integer.toString(deviceVehicle.getDV_ID()));
                    rsDV_TYPE.add(deviceVehicle.getDV_TYPE());
                    rsDV_YEAR.add(deviceVehicle.getDV_YEAR());
                    rsDV_MAKE.add(deviceVehicle.getDV_MAKE());
                    rsDV_MODEL.add(deviceVehicle.getDV_MODEL());

            }
            // final List<Person> persons = mDb.personDao().loadAllPersons();

            }
        });



        mInvolvedVehicleDao = new InvolvedVehicleDao(context);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsDV_ID.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_device_vehicle_adapter, null);
        holder.btnGallery = rowView.findViewById(R.id.btnGallery);
        holder.btnCamera = rowView.findViewById(R.id.btnCamera);
        holder.btnMedia = rowView.findViewById(R.id.btnMedia);
        holder.tvDV_ID = rowView.findViewById(R.id.tvDV_ID);
        holder.tvDV_TYPE = rowView.findViewById(R.id.tvDV_TYPE);
        holder.tvDV_YEAR = rowView.findViewById(R.id.tvDV_YEAR);
        holder.tvDV_MAKE = rowView.findViewById(R.id.tvDV_MAKE);
        holder.tvDV_MODEL = rowView.findViewById(R.id.tvDV_MODEL);
        holder.tvDV_ID.setText(rsDV_ID.get(position));
        holder.tvDV_TYPE.setText(rsDV_TYPE.get(position));
        holder.tvDV_YEAR.setText(rsDV_YEAR.get(position));
        holder.tvDV_MAKE.setText(rsDV_MAKE.get(position));
        holder.tvDV_MODEL.setText(rsDV_MODEL.get(position));
        DA_ID_STR = rsDV_ID.get(position);
        if (isNumber(DA_ID_STR)) {
            DV_ID = Integer.parseInt(DA_ID_STR);
        } else {
            DV_ID = 0;
        }

        List<DeviceImageStore> deviceImageStoreList = new ArrayList<>();
        deviceImageStoreList = mDeviceImageStoreDao.getDevPics(DV_ID);
        Boolean clickImage = true;
        if (deviceImageStoreList.size() == 0) {
            holder.btnGallery.setImageAlpha(alpha2);
            clickImage = false;
        }
        if (1 == 2) {
        if (clickImage) {

                holder.btnGallery.setOnClickListener(view -> {
                    if (fireClick == true) {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                            String DA_ID = rsDV_ID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                            intent = new Intent(context, PhotoGalleryActivity.class);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnGallery.startAnimation(rotateAnimation);
                            pos = position;
                            scheduleDismissIntent();
                    }
                }
                    holder.btnGallery.setImageAlpha(alpha1);
                    fireClick = true;
                });
                holder.btnGallery.setOnLongClickListener(view -> {
                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                                context = view.getContext();
                                Resources res = context.getResources();
                                holder.btnGallery.setImageAlpha(alpha2);
                                message = res.getString(R.string.wipp);
                                makeToast();
                                fireClick = false;
                    }
                    return false;
                });
                }
            holder.btnCamera.setOnClickListener(view -> {
                if (fireClick == true) {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                        context = view.getContext();
                        String DA_ID = rsDV_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_DV_ID", DA_ID);
                        intent = new Intent(context, CameraActivity.class);
                        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setRepeatCount(1);
                        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                        rotateAnimation.setDuration(100);
                        holder.btnCamera.startAnimation(rotateAnimation);
                        pos = position;
                        scheduleDismissIntent();
                }
            }
                holder.btnCamera.setImageAlpha(alpha1);
                fireClick = true;
            });
            holder.btnCamera.setOnLongClickListener(view -> {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                            context = view.getContext();
                            Resources res = context.getResources();
                            holder.btnCamera.setImageAlpha(alpha2);
                            message = res.getString(R.string.tap);
                            makeToast();

                            fireClick = false;
                }
                return false;

            });
            }
        holder.btnMedia.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    context = view.getContext();
                    mPersistenceObjDao.updateData("PERSIST_MULTI_MEDIA_CALLER", "LIST_DEVICE_VEHICLE");
                    //   intent = new Intent(context, OldMultiMediaDeviceMenu.class);
                    DA_ID = holder.tvDV_ID.getText().toString();
                    mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                    mPersistenceObjDao.updateData("PERSIST_TEMP_01", "");
                    mPersistenceObjDao.updateData("PERSIST_TEMP_02", holder.tvDV_TYPE.getText().toString());
                    mPersistenceObjDao.updateData("PERSIST_TEMP_03", holder.tvDV_YEAR.getText().toString());
                    mPersistenceObjDao.updateData("PERSIST_TEMP_04", holder.tvDV_MAKE.getText().toString());
                    mPersistenceObjDao.updateData("PERSIST_TEMP_05", holder.tvDV_MODEL.getText().toString());
                    intent = new Intent(context, MultiMediaMenu.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    holder.btnMedia.startAnimation(rotateAnimation);
                    pos = position;
                    scheduleDismissIntent();
                }
            }
            holder.btnMedia.setImageAlpha(alpha1);
            fireClick = true;

        });
        holder.btnMedia.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                context = view.getContext();
                Resources res = context.getResources();
                holder.btnMedia.setImageAlpha(alpha2);
                message = res.getString(R.string.multi_media_menu_helpa);
                makeToast();

                fireClick = false;
            }
            return false;
        });
        rowView.setOnClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                context = view.getContext();
                DA_ID = rsDV_ID.get(position);
                mPersistenceObjDao.updateData("PERSIST_DV_ID", DA_ID);
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DV_MODE");
                rsMode = persistenceObj.getPERSISTENCE_VALUE();
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    XX_AID = Integer.parseInt(DA_ID_STR);
                } else {
                    XX_AID = 0;
                }

                if (rsMode.equals("SELECTPROFILE")) {
                    if (isNumber(DA_ID)) {
                        DV_ID1 = Integer.parseInt(DA_ID);
                    } else {
                        DV_ID1 = 0;
                    }

                    DeviceVehicle deviceVehicle = mDb.mDeviceVehicleDao().loadDeviceVehicleById(DV_ID1);
                    String DV_TAG = deviceVehicle.getDV_TAG();
                    String DV_STATE = deviceVehicle.getDV_STATE();
                    String DV_EXPIRATION = deviceVehicle.getDV_EXPIRATION();
                    String DV_VIN = deviceVehicle.getDV_VIN();
                    String DV_YEAR = deviceVehicle.getDV_YEAR();
                    String DV_MAKE = deviceVehicle.getDV_MAKE();
                    String DV_MODEL = deviceVehicle.getDV_MODEL();
                    String DV_TYPE = deviceVehicle.getDV_TYPE();
                    String DV_PLATE_COUNTRY = "";
                    //getInvolvedVehicleTag

                    involvedvehicle = mInvolvedVehicleDao.getInvolvedVehicleTag(DV_TAG, XX_AID);
                    if (involvedvehicle == null) {
                        AddData(XX_AID, DV_TAG, DV_STATE, DV_EXPIRATION, DV_VIN, DV_YEAR, DV_MAKE,
                                DV_MODEL, DV_TYPE, DV_PLATE_COUNTRY);

                    } else {
                        Resources res = context.getResources();
                        message = res.getString(R.string.tv2001);
                        makeToast();
                    }
                }
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                rowView.startAnimation(rotateAnimation);
                pos = position;
                scheduleDoListItem();
            }
     });
        return rowView;
        }
    private void scheduleDoListItem() {
        doClose();
        Handler handler = new Handler();
        handler.postDelayed(this::doListItem, 200);
    }
    private void doListItem() {
        if (rsMode.equals("SELECTPROFILE")) {
            intent = new Intent(context, ListInvolvedVehicle.class);
            context.startActivity(intent);
    }
        if (rsMode.equals("UPDATE")) {
            intent = new Intent(context, UpdateDeviceVehicle.class);
            context.startActivity(intent);
        }


    }
    private void AddData(Integer XX_AID, String DV_TAG, String DV_STATE, String DV_EXPIRATION,
                         String DV_VIN, String DV_YEAR, String DV_MAKE, String DV_MODEL, String DV_TYPE, String DV_PLATE_COUNTRY) {
        long insertData = mInvolvedVehicleDao.addData(XX_AID, DV_TAG, DV_STATE,
                DV_EXPIRATION, DV_VIN, DV_YEAR, DV_MAKE, DV_MODEL, DV_TYPE, DV_PLATE_COUNTRY);
    }

    private void makeToast() {
        int duration = 20;
        int type = 0;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);

        mdToast.show();

    }

    public class Holder {
        FloatingActionButton btnGallery;
        FloatingActionButton btnCamera;
        FloatingActionButton btnMedia;
        TextView tvDV_ID;
        TextView tvDV_TYPE;
        TextView tvDV_YEAR;
        TextView tvDV_MAKE;
        TextView tvDV_MODEL;

    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
        //mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        // mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 200);
    }
    private void dismissIntent() {
        doClose();
        context.startActivity(intent);
    }


}
