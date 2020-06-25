package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.database.AccidentIdDao;
import com.auto.accident.report.database.AccidentNoteDao;
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.InvolvedImageStoreDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.AccidentId;
import com.auto.accident.report.model.AccidentNote;
import com.auto.accident.report.model.DeviceUser;
import com.auto.accident.report.model.InvolvedImageStore;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.photos.CameraActivity;
import com.auto.accident.report.photos.PhotoGalleryActivity;

import java.util.ArrayList;
import java.util.List;

import static com.auto.accident.report.util.utils.isNumber;

class ListAccidentAdapter extends BaseAdapter {
    private static final int CAMERA_ACTIVITY_REQUEST_CODE = 0;
    private static LayoutInflater inflater = null;
    //   String [] result1;
    private Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private final InvolvedImageStoreDao mInvolvedImageStoreDao;
    private final ArrayList<String> rsAID_ID = new ArrayList<>();
    //   ArrayList<String> rsAID_DUID    = new ArrayList<>();
    private final ArrayList<String> rsAID_RDATE = new ArrayList<>();
    private final ArrayList<String> rsAID_ADATE = new ArrayList<>();
    private final ArrayList<String> rsAID_ATIME = new ArrayList<>();
    private int alpha = 50;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Boolean fireClick = true;
    private String message;
    //  String [] result0;
    private String CDU_ID;
    private String rsMode;
    private PersistenceObj persistenceObj;
    private AccidentIdDao mAccidentIdDao;
    private AccidentNoteDao mAccidentNoteDao;
    private DeviceUserDao mDeviceUserDao;
    private DeviceUser deviceuser;
    private Intent intent;
    private RotateAnimation rotateAnimation;
    private int pos;
    private int AID_ID;
    private int DU_ID;
    private String DA_ID_STR;
    private String PERSIST_ACTION_IN_PROGRESS;
    public ListAccidentAdapter(ListAccident ListAccident) {
        // TODO Auto-generated constructor stub

        context = ListAccident;
        mInvolvedImageStoreDao = new InvolvedImageStoreDao(context);
        mAccidentNoteDao = new AccidentNoteDao(context);
        mPersistenceObjDao = new PersistenceObjDao(context);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_MODE");
        String AID_MODE = persistenceObj.getPERSISTENCE_VALUE();
        mAccidentIdDao = new AccidentIdDao(context);
        List<AccidentId> accidentIdList = new ArrayList<>();
        accidentIdList = mAccidentIdDao.getAllAccidentIds();
        mDeviceUserDao = new DeviceUserDao(context);
        for (AccidentId accidentId : accidentIdList) {
            rsAID_ID.add(Integer.toString(accidentId.getAID_ID()));
            //  rsAID_DUID.add(Integer.toString(accidentId.getAID_DUID()));
            rsAID_RDATE.add(accidentId.getAID_RDATE());
            rsAID_ADATE.add(accidentId.getAID_ADATE());
            rsAID_ATIME.add(accidentId.getAID_ATIME());
            //    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
            //      CDU_ID = persistenceObj.getPERSISTENCE_VALUE();
            //      int CDU_ID   = accidentId.getAID_DUID();
            //  int AID_DUID = accidentId.getAID_DUID();
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                DU_ID = Integer.parseInt(DA_ID_STR);
            } else {
                DU_ID = 0;
            }
            deviceuser = mDeviceUserDao.getDeviceUser(DU_ID);
            ArrayList<String> rsDU_FNAME = new ArrayList<>();
            rsDU_FNAME.add(deviceuser.getDU_FNAME());
            ArrayList<String> rsDU_MI = new ArrayList<>();
            rsDU_MI.add(deviceuser.getDU_MI());
            ArrayList<String> rsDU_LNAME = new ArrayList<>();
            rsDU_LNAME.add(deviceuser.getDU_LNAME());

            mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
            mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
            mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");


        }


        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsAID_ID.size();

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
        Log.i("ListAccidentAdapter 130", "start getView");
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_accident_adapter, null);
        holder.btnNote = rowView.findViewById(R.id.btnNote);
        holder.btnEdit = rowView.findViewById(R.id.btnEdit);
        holder.btnGallery = rowView.findViewById(R.id.btnGallery);
        holder.btnCamera = rowView.findViewById(R.id.btnCamera);
        holder.btnMedia = rowView.findViewById(R.id.btnMedia);
        holder.tv01 = rowView.findViewById(R.id.tvAID_ID);
        //  holder.tv02 = rowView.findViewById(R.id.tvAID_DUID);
        holder.tv03 = rowView.findViewById(R.id.tvAID_RDATE);
        holder.tv04 = rowView.findViewById(R.id.tvAID_ADATE);
        holder.tv05 = rowView.findViewById(R.id.tvAID_ATIME);
        //  holder.tv06 = rowView.findViewById(R.id.tvDU_FNAME);
        //  holder.tv07 = rowView.findViewById(R.id.tvDU_MI);
        //   holder.tv08 = rowView.findViewById(R.id.tvDU_LNAME);
        holder.tv09 = rowView.findViewById(R.id.tvACC_NUM);
        holder.tv01.setText(rsAID_ID.get(position));
        //  holder.tv02.setText(rsAID_DUID.get(position));
        holder.tv03.setText(rsAID_RDATE.get(position));
        holder.tv04.setText(rsAID_ADATE.get(position));
        holder.tv05.setText(rsAID_ATIME.get(position));
        //   holder.tv06.setText(rsDU_FNAME.get(position));
        //    holder.tv07.setText(rsDU_MI.get(position));
        //    holder.tv08.setText(rsDU_LNAME.get(position));
        holder.tv09.setText(rsAID_ID.get(position));
        DA_ID_STR = rsAID_ID.get(position);
        if (isNumber(DA_ID_STR)) {
            AID_ID = Integer.parseInt(DA_ID_STR);
        } else {
            AID_ID = 0;
        }
        List<InvolvedImageStore> involvedImageStoreList = new ArrayList<>();
        involvedImageStoreList = mInvolvedImageStoreDao.getAllAccPics(AID_ID);
        Boolean clickImage = true;
        holder.btnGallery.setImageAlpha(alpha1);
        if (involvedImageStoreList.size() == 0) {
            holder.btnGallery.setImageAlpha(alpha2);
            clickImage = false;
        }



        List<AccidentNote> accidentnoteList = new ArrayList<>();
        accidentnoteList = mAccidentNoteDao.getAllAccidentNotes(AID_ID);
        if (accidentnoteList.size() == 0) {
            holder.btnNote.setImageResource(R.drawable.ic_note_add_white_48dp);
            holder.btnNote.setRotation(0);

        }

            holder.btnNote.setOnClickListener(view -> {
                if (fireClick == true) {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                        context = view.getContext();
                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                        String DA_ID = "-1";
                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                        DA_ID = rsAID_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                        float x = holder.btnNote.getRotation();
                        if (x == 0) {
                            intent = new Intent(context, AddNotes.class);
                        } else {
                            intent = new Intent(context, ListNotes.class);
                        }
                        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setRepeatCount(1);
                        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                        rotateAnimation.setDuration(100);
                        holder.btnNote.startAnimation(rotateAnimation);
                        pos = position;
                        scheduleDismissIntent();
                    }
                }
                holder.btnNote.setImageAlpha(alpha1);
                fireClick = true;
            });
            holder.btnNote.setOnLongClickListener(view -> {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                    float x = holder.btnNote.getRotation();
                    if (x == 0) {
                        context = view.getContext();
                        Resources res = context.getResources();
                        holder.btnNote.setImageAlpha(alpha2);
                        message = res.getString(R.string.aan);
                        makeToast();
                    } else {
                        context = view.getContext();
                        Resources res = context.getResources();
                        holder.btnNote.setImageAlpha(alpha2);
                        message = res.getString(R.string.uan);
                        makeToast();
                    }
                    fireClick = false;
                }
                return false;
            });
            if (1 == 2) {
            if (clickImage) {
                holder.btnGallery.setOnClickListener(view -> {
                    if (fireClick == true) {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false")) {
                            context = view.getContext();


                            String DA_ID = rsAID_ID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                            mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                            intent = new Intent(context, PhotoGalleryActivity.class);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnGallery.startAnimation(rotateAnimation);
                            pos = position;
                            scheduleDismissIntent();
                            // context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
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
                        String DA_ID = rsAID_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                        intent = new Intent(context, CameraActivity.class);
                        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setRepeatCount(1);
                        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                        rotateAnimation.setDuration(100);
                        holder.btnCamera.startAnimation(rotateAnimation);
                        pos = position;
                        scheduleDismissIntent();
                        //context.startActivity(intent);
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
        holder.btnEdit.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                    context = view.getContext();
                    String DA_ID = rsAID_ID.get(position);
                    mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                    intent = new Intent(context, UpdateAccident.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    holder.btnEdit.startAnimation(rotateAnimation);
                    pos = position;
                    scheduleDismissIntent();
                }
               }
            holder.btnEdit.setImageAlpha(alpha1);
            fireClick = true;
        });
        holder.btnEdit.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                context = view.getContext();
                Resources res = context.getResources();
                holder.btnEdit.setImageAlpha(alpha2);
                message = res.getString(R.string.uaid);
                makeToast();
                fireClick = false;
            }
            return false;
        });




        holder.btnMedia.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false")) {
                    context = view.getContext();
                    String DA_ID = rsAID_ID.get(position);
                    mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                    mPersistenceObjDao.updateData("PERSIST_MULTI_MEDIA_CALLER", "LIST_ACCIDENT");
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
            // TODO Auto-generated method stub
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                context = view.getContext();
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_MODE");
                rsMode = persistenceObj.getPERSISTENCE_VALUE();
                String DA_ID = rsAID_ID.get(position);
                mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                if (rsMode.equals("SELECT")) {
                    //   Intent intent = new Intent(context, ListInvolvedParty.class);
                    mPersistenceObjDao.updateData("PERSIST_IM_CALLER", "LIST_ACCIDENT");
                    intent = new Intent(context, ListInvolvedMenu.class);
                }
                if (rsMode.equals("UPDATE")) {
                    intent = new Intent(context, UpdateAccident.class);
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
        Handler handler = new Handler();
        handler.postDelayed(this::doListItem, 200);
    }
    private void doListItem() {

        doClose();
        context.startActivity(intent);
    }
    private void makeToast() {
        int duration = 20;
        int type = 0;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);

        mdToast.show();

    }

    public class Holder {
        FloatingActionButton btnNote;
        FloatingActionButton btnGallery;
        FloatingActionButton btnCamera;
        FloatingActionButton btnMedia;
        FloatingActionButton btnEdit;

        TextView tv01;
        //   TextView tv02;
        TextView tv03;
        TextView tv04;
        TextView tv05;
        //   TextView tv06;
        //   TextView tv07;
        //   TextView tv08;
        TextView tv09;

    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
        mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
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


