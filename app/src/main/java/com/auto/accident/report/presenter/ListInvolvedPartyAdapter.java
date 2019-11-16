package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.AccidentNoteDao;
import com.auto.accident.report.models.InsurancePolicyDao;
import com.auto.accident.report.models.InsurancePolicyPDao;
import com.auto.accident.report.models.InvolvedImageStoreDao;
import com.auto.accident.report.models.InvolvedPartyDao;
import com.auto.accident.report.models.InvolvedVehicleDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.models.VehicleManifestDao;
import com.auto.accident.report.objects.AccidentNote;
import com.auto.accident.report.objects.InsurancePolicy;
import com.auto.accident.report.objects.InvolvedImageStore;
import com.auto.accident.report.objects.InvolvedParty;
import com.auto.accident.report.objects.InvolvedVehicle;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.objects.VehicleManifest;
import com.auto.accident.report.photos.PhotoGalleryActivity;
import com.auto.accident.report.util.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.view.View.VISIBLE;
import static com.auto.accident.report.util.utils.isNumber;
/**
 * Created by myron on 1/15/2018.
 */

class ListInvolvedPartyAdapter extends BaseAdapter  {
    private static LayoutInflater inflater = null;
    //   String [] result1;
    private Context context;
    private final InsurancePolicyPDao mInsurancePolicyPDao;
    private final VehicleManifestDao mVehicleManifestDao;
    private final InvolvedVehicleDao mInvolvedVehicleDao;
    private PersistenceObjDao mPersistenceObjDao;
    private final InvolvedImageStoreDao mInvolvedImageStoreDao;
    private final AccidentNoteDao mAccidentNoteDao;
    //  String [] result0;
    private final ArrayList<String> rsIP_ID = new ArrayList<>();
    private final ArrayList<String> rsIP_AID = new ArrayList<>();
    private final ArrayList<String> rsIP_FNAME = new ArrayList<>();
    private final ArrayList<String> rsIP_MI = new ArrayList<>();
    private final ArrayList<String> rsIP_LNAME = new ArrayList<>();
    private final ArrayList<String> rsIP_PTYPE = new ArrayList<>();
    private final ArrayList<String> rsIP_PHON1 = new ArrayList<>();
    private final ArrayList<String> rsIP_PHON2 = new ArrayList<>();
    private final ArrayList<String> rsIP_PHON3 = new ArrayList<>();
    private final ArrayList<String> rsIP_PHON1_COUNTRY = new ArrayList<>();
    private final ArrayList<String> rsIP_PHON2_COUNTRY = new ArrayList<>();
    private final ArrayList<String> rsIP_PHON3_COUNTRY = new ArrayList<>();
    private final ArrayList<String> rsIP_EMAIL = new ArrayList<>();
    private final ArrayList<String> rsIP_CNAM03 = new ArrayList<>();
    private final ArrayList<String> rsIP_COMP = new ArrayList<>();
    // IP_PHON1_COUNTRY
    private int alpha = 50;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private int alpha3 = 150;
    private int alphaCompare;
    private Boolean fireClick = true;
    private String message;

    private String DA_RESULT;
    private ArrayList<String> resultx;
    private ArrayList<String> resulty;
    private String[] singleChoiceItems;
    private String PHON1;
    private String PHON2;
    private String PHON3;
    private String EMAIL;
    private String PHON1_COUNTRY;
    private String PHON2_COUNTRY;
    private String PHON3_COUNTRY;

    private String PHON1_LABEL;
    private String PHON2_LABEL;
    private String PHON3_LABEL;
    private String CNAM03;
    private int phoneNumberIndex;
    // remote config
    private static final String configLocale = Locale.getDefault().getCountry().toLowerCase();
    private static final String KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = "KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON";
    private static final String KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = "KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = "KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE";
    private static final String KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = "KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY";
    private static final String KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = "KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT";
    private static final String KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT = "KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT";
    private static final String KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = "KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL";
    private static final String KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = "KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER";
    private String REMOTE_CONFIG_KEY;
    private String REMOTE_CONFIG_VALUE;
    private String REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE;
    private String REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE;
    private String REMOTE_CONFIG_ALLOW_EMAIL_CONTACT;
    private String REMOTE_CONFIG_ALLOW_PHONE_CONTACT;
    private String REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL;
    private String REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER;
    private String REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE;
    private String REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON_TEXT1;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON_TEXT2;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON_TEXT3;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON_TEXT1;
    private String REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON_TEXT2;
    private String REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY;
    private String DA_CALLER;
    private PersistenceObj persistenceObj;
    private InsurancePolicyDao mInsurancePolicyDao;
    private InvolvedPartyDao mInvolvedPartyDao;
    private Boolean clickImage;
    private Boolean clickBtnDelete;
    private Intent intent;
    private InvolvedVehicle involvedVehicle;
    private boolean ActionInProgress;
    private RotateAnimation rotateAnimation;
    private Resources res;
    private Intent emailIntent;
    private int AID_ID;

    private int CURRENT_IPO_HOLDER;
    private int DAIP_ID;
    private int IP_ID;
    private int IPP_AID;
    private int IPP_IPID;
    private int IPP_POID;
    private int IVDA_ID;
    private int VMAID_ID;
    private int VMIP_ID;
    private int VMIV_ID;
    private int pos;
    private String DA_ID_STR;
    private String PERSIST_ACTION_IN_PROGRESS;
    // remote config

    public ListInvolvedPartyAdapter(ListInvolvedParty ListInvolvedParty) {
        // TODO Auto-generated constructor stub
        context = ListInvolvedParty;
        mAccidentNoteDao = new AccidentNoteDao(context);
        mInvolvedVehicleDao = new InvolvedVehicleDao(context);
        mVehicleManifestDao = new VehicleManifestDao(context);
        mInvolvedImageStoreDao = new InvolvedImageStoreDao(context);
        mPersistenceObjDao = new PersistenceObjDao(context);
        mPersistenceObjDao.updateData("PERSIST_COMPANY_NAME", "");
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_PARTY");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_PARTY");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_PARTY");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        mInsurancePolicyPDao = new InsurancePolicyPDao(context);
        mInsurancePolicyDao = new InsurancePolicyDao(context);
        ActionInProgress = false;
        res = context.getResources();
        getConfig();
        int AID_ID;
        if (DA_ID != null && DA_ID.trim().length() > 0) {
            AID_ID = Integer.parseInt(DA_ID);
        } else {
            AID_ID = 0;
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();

        mInvolvedPartyDao = new InvolvedPartyDao(context);
        List<InvolvedParty> involvedpartyList = new ArrayList<>();
        if (AID_ID != 0) {
            if (!DA_CALLER.equals("LIST_INSURED_PEOPLE")) {
                involvedpartyList = mInvolvedPartyDao.getAllInvolvedParties(AID_ID);
            }
            if (DA_CALLER.equals("LIST_INSURED_PEOPLE")) {
                // Get all parties not covered by the current policy
                involvedpartyList = mInvolvedPartyDao.getAllUninsuredInvolvedParties(AID_ID);
            }
        } else {

            involvedpartyList = mInvolvedPartyDao.getAllInvolvedPartiesAll();

        }

        //   getAllVehicleManifestsAll  Change Logic for Passengers
        //     List<VehicleManifest> vehicleManifestList2 = new ArrayList<VehicleManifest>();
        //    vehicleManifestList2 = mVehicleManifestDao.getAllVehicleManifestsAll();
        for (InvolvedParty involvedParty : involvedpartyList) {
         //   int IP_ID = involvedParty.getIP_ID();
          //  List<VehicleManifest> vehicleManifestList = new ArrayList<>();
          //  vehicleManifestList = mVehicleManifestDao.getVehicleManifestPersonNZ(IP_ID, AID_ID);
           // if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE") && vehicleManifestList.size() > 0) {

           // } else {Change Logic for Passengers
                rsIP_ID.add(Integer.toString(involvedParty.getIP_ID()));
                rsIP_AID.add(Integer.toString(involvedParty.getIP_AID()));

                String IP_PTYPE = involvedParty.getIP_PTYPE();
                IP_PTYPE = utils.splitDash(IP_PTYPE);
                rsIP_PTYPE.add(IP_PTYPE);
                rsIP_FNAME.add(involvedParty.getIP_FNAME());
                rsIP_MI.add(involvedParty.getIP_MI());
                rsIP_LNAME.add(involvedParty.getIP_LNAME());
                rsIP_PHON1.add(involvedParty.getIP_PHON1());
                rsIP_PHON2.add(involvedParty.getIP_PHON2());
                rsIP_PHON3.add(involvedParty.getIP_PHON3());
                rsIP_PHON1_COUNTRY.add(involvedParty.getIP_PHON1_COUNTRY());
                rsIP_PHON2_COUNTRY.add(involvedParty.getIP_PHON2_COUNTRY());
                rsIP_PHON3_COUNTRY.add(involvedParty.getIP_PHON3_COUNTRY());
                rsIP_EMAIL.add(involvedParty.getIP_EMAIL());
                rsIP_CNAM03.add(involvedParty.getIP_CNAM03());
            rsIP_COMP.add(involvedParty.getIP_COMP());
           // }Change Logic for Passengers

        }


        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsIP_ID.size();

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

        rowView = inflater.inflate(R.layout.list_involved_party_adapter, null);
        //  holder.tv0= rowView.findViewById(R.id.textView0);
        //  holder.tv1= rowView.findViewById(R.id.textView1);
        holder.btnEmail = rowView.findViewById(R.id.btnEmail);
        holder.rl01 = rowView.findViewById(R.id.rl01);
        holder.btnCall = rowView.findViewById(R.id.btnCall);
     //   holder.btnCall = rowView.findViewById(R.id.btnCall);
        holder.hasPolicy = rowView.findViewById(R.id.hasPolicy);
        holder.btnNote = rowView.findViewById(R.id.btnNote);
        holder.btnPolicy = rowView.findViewById(R.id.btnPolicy);
        holder.btnDelete = rowView.findViewById(R.id.btnDelete);
        holder.btnGallery = rowView.findViewById(R.id.btnGallery);
        holder.btnMedia = rowView.findViewById(R.id.btnMedia);

        holder.btnViewVehicle = rowView.findViewById(R.id.btnViewVehicle);
     //   holder.rmvText = rowView.findViewById(R.id.rmvText);
        holder.tvIP_PTYPE = rowView.findViewById(R.id.tvIP_PTYPE);
        holder.tvIP_ID = rowView.findViewById(R.id.tvIP_ID);
        holder.tvAID_ID = rowView.findViewById(R.id.tvAID_ID);
        holder.tvIP_FNAME = rowView.findViewById(R.id.tvIP_FNAME);
        holder.tvIP_MI = rowView.findViewById(R.id.tvIP_MI);
        holder.tvIP_LNAME = rowView.findViewById(R.id.tvIP_LNAME);
        holder.tvIV_ID = rowView.findViewById(R.id.tvIV_ID);
        holder.tvIV_YEAR = rowView.findViewById(R.id.tvIV_YEAR);
        holder.tvIV_MAKE = rowView.findViewById(R.id.tvIV_MAKE);
        holder.tvIV_MODEL = rowView.findViewById(R.id.tvIV_MODEL);
        holder.ll01 = rowView.findViewById(R.id.ll01);
        holder.rl02 = rowView.findViewById(R.id.rl02);
        holder.tvIP_ID.setText(rsIP_ID.get(position));
        holder.tvIP_PTYPE.setText(rsIP_PTYPE.get(position));
        holder.tvAID_ID.setText(rsIP_AID.get(position));
        holder.tvIP_FNAME.setText(rsIP_FNAME.get(position));
        holder.tvIP_MI.setText(rsIP_MI.get(position));
        holder.tvIP_LNAME.setText(rsIP_LNAME.get(position));
        holder.hasPolicy.setText("true");
        holder.btnViewVehicle.setImageResource(R.drawable.ic_directions_car_plus_white_24dp);
        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE"))  {
            holder.btnDelete.show();
            holder.btnDelete.setImageAlpha(alpha2);

        }
        clickBtnDelete = false;
        PHON1 = rsIP_PHON1.get(position);
        PHON2 = rsIP_PHON2.get(position);
        PHON3 = rsIP_PHON3.get(position);
        PHON1_COUNTRY = utils.getSecondWord(rsIP_PHON1_COUNTRY.get(position));
        PHON2_COUNTRY = utils.getSecondWord(rsIP_PHON2_COUNTRY.get(position));
        PHON3_COUNTRY = utils.getSecondWord(rsIP_PHON3_COUNTRY.get(position));
        EMAIL = rsIP_EMAIL.get(position);
        DA_ID_STR = rsIP_AID.get(position);
        if (isNumber(DA_ID_STR)) {
            AID_ID = Integer.parseInt(DA_ID_STR);
        } else {
            AID_ID = 0;
        }
        DA_ID_STR = rsIP_ID.get(position);
        if (isNumber(DA_ID_STR)) {
            IP_ID = Integer.parseInt(DA_ID_STR);
        } else {
            IP_ID = 0;
        }

        List<VehicleManifest> vehicleManifestList = new ArrayList<>();
        vehicleManifestList = mVehicleManifestDao.getVehicleManifestPerson(IP_ID, AID_ID);
        for (VehicleManifest vehicleManifest : vehicleManifestList) {
            int IV_ID = vehicleManifest.getVMIV_ID();

            if (IV_ID != 0) {
                involvedVehicle = mInvolvedVehicleDao.getInvolvedVehicle(IV_ID, AID_ID);
                  if (involvedVehicle != null) {
                      if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE")) {
                          persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
                          String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                          if (isNumber(DA_ID)) {
                              VMIV_ID = Integer.parseInt(DA_ID);
                          } else {
                              VMIV_ID = 0;
                          }

                          if (VMIV_ID == IV_ID) {

                              holder.btnDelete.setImageAlpha(alpha1);
                              clickBtnDelete = true;
                          }
                      }
                     // btnDelete
                holder.tvIV_ID.setText(Integer.toString(IV_ID));




                    holder.tvIV_YEAR.setText(involvedVehicle.getIV_YEAR());
                    holder.tvIV_MAKE.setText(involvedVehicle.getIV_MAKE());
                    holder.tvIV_MODEL.setText(involvedVehicle.getIV_MODEL());
                    holder.tvIV_YEAR.setVisibility(VISIBLE);
                    holder.tvIV_MAKE.setVisibility(VISIBLE);
                    holder.tvIV_MODEL.setVisibility(VISIBLE);
                      holder.btnViewVehicle.setImageResource(R.drawable.ic_directions_car_white_24dp);
                  //  holder.rl01.setVisibility(VISIBLE);
                  //  holder.btnViewVehicle.setImageAlpha(alpha1);
                    //clickbtnViewVehicle = true;
                   // holder.rmvText.setVisibility(VISIBLE);
                }
            }
        }
        if (clickBtnDelete) {
            holder.btnDelete.setOnClickListener(view -> {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                            String DA_ID = rsIP_ID.get(position);
                            if (isNumber(DA_ID)) {
                                DAIP_ID = Integer.parseInt(DA_ID);
                            } else {
                                DAIP_ID = 0;
                            }

                            mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                            mVehicleManifestDao.getVehicleManifestPerson(DAIP_ID, AID_ID);
                            DA_ID_STR = holder.tvIV_ID.getText().toString();
                            if (isNumber(DA_ID_STR)) {
                                IVDA_ID = Integer.parseInt(DA_ID_STR);
                            } else {
                                IVDA_ID = 0;
                            }

                            List<VehicleManifest> vehiclemanifestListNew = new ArrayList<>();
                            mVehicleManifestDao.deleteVMIP_ID(DAIP_ID);
                            AddData(AID_ID, 0, DAIP_ID);
                            vehiclemanifestListNew = mVehicleManifestDao.getVehicleManifest(IVDA_ID, AID_ID);
                            if (vehiclemanifestListNew.size() == 0) {
                                AddData(AID_ID, IVDA_ID, 0);
                            }

                            intent = new Intent(context, ListInvolvedParty.class);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnDelete.startAnimation(rotateAnimation);
                            pos = position;

                            scheduleDismissIntent();
                            // context.startActivity(intent);

                            // return false;
                        }
            });
        }
        List<InvolvedImageStore> involvedImageStoreList = new ArrayList<>();
        involvedImageStoreList = mInvolvedImageStoreDao.getAccPics(AID_ID, IP_ID);

        List<InsurancePolicy> insurancePolicyList = mInsurancePolicyDao.getHolderInsurancePolicys(AID_ID, IP_ID);


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();

        // if (!DA_CALLER.equals("LIST_INVOLVED_VEHICLE") && !DA_CALLER.equals("SELECT_NOTE_ATTACHMENT") && !DA_CALLER.equals("LIST_INSURED_PEOPLE")) {
        if (!DA_CALLER.equals("LIST_INVOLVED_VEHICLE") && !DA_CALLER.equals("LIST_INSURED_PEOPLE")&& !DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
            List<AccidentNote> accidentnoteList = new ArrayList<>();
            accidentnoteList = mAccidentNoteDao.getIPAccidentNotes(AID_ID, IP_ID);
            if (accidentnoteList.size() == 0) {
                holder.btnNote.setImageResource(R.drawable.ic_note_add_white_48dp);
                holder.btnNote.setRotation(0);

            }
            clickImage = true;
            if (involvedImageStoreList.size() == 0) {
                holder.btnGallery.setImageAlpha(alpha2);
                clickImage = false;
            }


            if (insurancePolicyList.size() == 0) {
                holder.btnPolicy.setImageResource(R.drawable.add_car_insurance);
                holder.hasPolicy.setText("false");
            }

            if (clickImage) {
                holder.btnGallery.setOnClickListener(view -> {
                    if (fireClick == true) {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                            String DA_ID = rsIP_AID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                            DA_ID = rsIP_ID.get(position);
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

                                holder.btnGallery.setImageAlpha(alpha2);
                                message = res.getString(R.string.wipp);
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

                        String DA_ID = rsIP_AID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                        DA_ID = rsIP_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                        mPersistenceObjDao.updateData("PERSIST_MULTI_MEDIA_CALLER", "LIST_INVOLVED_PARTY");
                        DA_ID = holder.tvIV_ID.getText().toString();
                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                        mPersistenceObjDao.updateData("PERSIST_TEMP_01", holder.tvIP_FNAME.getText().toString());
                        mPersistenceObjDao.updateData("PERSIST_TEMP_02", holder.tvIP_PTYPE.getText().toString());
                        mPersistenceObjDao.updateData("PERSIST_TEMP_03", holder.tvIV_YEAR.getText().toString());
                        mPersistenceObjDao.updateData("PERSIST_TEMP_04", holder.tvIV_MAKE.getText().toString());
                        mPersistenceObjDao.updateData("PERSIST_TEMP_05", holder.tvIV_MODEL.getText().toString());
                        DA_ID = rsIP_COMP.get(position);
                        mPersistenceObjDao.updateData("PERSIST_COMPANY_NAME", DA_ID);

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

                    holder.btnMedia.setImageAlpha(alpha2);
                    message = res.getString(R.string.multi_media_menu_helpa);
                    makeToast();

                    fireClick = false;
                }
                return false;
            });
            holder.btnNote.setOnClickListener(view -> {
                if (fireClick == true) {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_PARTY");
                        String DA_ID = rsIP_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                        mPersistenceObjDao.updateData("PERSIST_IV_ID", "0");
                        float x = holder.btnNote.getRotation();
                        if (x == 0) {
                            intent = new Intent(context, AddNotes.class);
                            scheduleDismissIntent();
                            //  context.startActivity(intent);
                        } else {
                            intent = new Intent(context, ListNotes.class);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnGallery.startAnimation(rotateAnimation);
                            pos = position;

                            scheduleDismissIntent();
                        }
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


                        holder.btnNote.setImageAlpha(alpha2);
                        message = res.getString(R.string.ain);
                        makeToast();
                    } else {


                        holder.btnNote.setImageAlpha(alpha2);
                        message = res.getString(R.string.wipn);
                        makeToast();
                    }
                    fireClick = false;
                }
                return false;
            });


            holder.btnPolicy.setOnClickListener(view -> {
                String hasPolicy = holder.hasPolicy.getText().toString();
                if (fireClick == true) {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                        String DA_ID = rsIP_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                        mPersistenceObjDao.updateData("PERSIST_IV_ID", "0");
                        mPersistenceObjDao.updateData("PERSIST_IPO_HOLDER", DA_ID);
                        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setRepeatCount(1);
                        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                        rotateAnimation.setDuration(100);
                        holder.btnPolicy.startAnimation(rotateAnimation);
                        pos = position;
                        if (hasPolicy.equals("false")) {
                            mPersistenceObjDao.updateData("PERSIST_ADD_INSURANCE_POLICY_CALLER", "LIST_INVOLVED_PARTY");

                            intent = new Intent(context, AddInsurancePolicy.class);
                            scheduleDismissIntent();
                            // context.startActivity(intent);
                        }
                        if (hasPolicy.equals("true")) {
                            mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "LIST_INVOLVED_PARTY");
                            intent = new Intent(context, ListInsurancePolicy.class);
                            scheduleDismissIntent();
                        }

                    }
                }

                holder.btnPolicy.setImageAlpha(alpha1);

                fireClick = true;
            });
            holder.btnPolicy.setOnLongClickListener(view -> {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    String hasPolicy = holder.hasPolicy.getText().toString();
                    if (hasPolicy.equals("false")) {

                        message = res.getString(R.string.add_insurance_policy);
                    }
                    if (hasPolicy.equals("true")) {

                        message = res.getString(R.string.select_insurance_policy);
                    }
                    makeToast();
                    holder.btnPolicy.setImageAlpha(alpha2);
                    fireClick = false;
                }
                return false;
            });

          //  if (clickbtnViewVehicle) {
                holder.btnViewVehicle.setOnClickListener(view -> {
                    if (fireClick == true) {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                            String DA_ID = rsIP_ID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                            mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "LIST_INVOLVED_PARTY");
                            intent = new Intent(context, ListInvolvedVehicle.class);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnViewVehicle.startAnimation(rotateAnimation);
                            pos = position;

                            scheduleDismissIntent();
                        }
                    }
                    holder.btnViewVehicle.setImageAlpha(alpha1);
                    fireClick = true;
                });
                holder.btnViewVehicle.setOnLongClickListener(view -> {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                        holder.btnViewVehicle.setImageAlpha(alpha2);
                        message = res.getString(R.string.associate_with_involved_vehicle);
                        makeToast();
                        fireClick = false;
                    }
                    return false;
                });
           // }
            if (REMOTE_CONFIG_ALLOW_PHONE_CONTACT.equals("false")) {
                holder.btnCall.hide();
            } else {
                Boolean clickPhon = true;
                if (PHON1.equals("") && PHON2.equals("") && PHON3.equals("")) {
                    holder.btnCall.setImageAlpha(alpha2);
                    clickPhon = false;
                }
                if (clickPhon) {
                    holder.btnCall.setOnClickListener(view -> {
                                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                                    String hasPolicy = holder.hasPolicy.getText().toString();
                        if (fireClick == true) {

                            PHON1_LABEL = res.getString(R.string.home);
                            PHON2_LABEL = res.getString(R.string.cell);
                            PHON3_LABEL = res.getString(R.string.work);
                            PHON1 = rsIP_PHON1.get(position);
                            PHON2 = rsIP_PHON2.get(position);
                            PHON3 = rsIP_PHON3.get(position);
                            PHON1_COUNTRY = utils.getSecondWord(rsIP_PHON1_COUNTRY.get(position));
                            PHON2_COUNTRY = utils.getSecondWord(rsIP_PHON2_COUNTRY.get(position));
                            PHON3_COUNTRY = utils.getSecondWord(rsIP_PHON3_COUNTRY.get(position));
                            phoneNumberIndex = 0;
                            // What is displayed
                            resultx = new ArrayList<String>();
                            if (!PHON1.equals("")) {
                                resultx.add(phoneNumberIndex, PHON1_LABEL + " : " + PHON1_COUNTRY + PHON1);
                                phoneNumberIndex++;
                            }
                            if (!PHON2.equals("")) {
                                resultx.add(phoneNumberIndex, PHON2_LABEL + " : " + PHON2_COUNTRY + PHON2);
                                phoneNumberIndex++;
                            }
                            if (!PHON3.equals("")) {
                                resultx.add(phoneNumberIndex, PHON3_LABEL + " : " + PHON3_COUNTRY + PHON3);
                                phoneNumberIndex++;
                            }
                            singleChoiceItems = new String[resultx.size()];

                            singleChoiceItems = resultx.toArray(singleChoiceItems);
                            phoneNumberIndex = 0;
                            // What is used to make the call
                            resulty = new ArrayList<String>();
                            if (!PHON1.equals("")) {
                                resulty.add(phoneNumberIndex, PHON1_COUNTRY + PHON1);
                                phoneNumberIndex++;
                            }
                            if (!PHON2.equals("")) {
                                resulty.add(phoneNumberIndex, PHON2_COUNTRY + PHON2);
                                phoneNumberIndex++;
                            }
                            if (!PHON3.equals("")) {
                                resulty.add(phoneNumberIndex, PHON3_COUNTRY + PHON3);
                                phoneNumberIndex++;
                            }
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnCall.startAnimation(rotateAnimation);
                            pos = position;

                            scheduleSelectedItemPhon();
                            //   getSelectedItemPhon(res);

                        }
                        }


                        holder.btnCall.setImageAlpha(alpha1);

                        fireClick = true;
                    });
                }

                holder.btnCall.setOnLongClickListener(view -> {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                        message = res.getString(R.string.call_this_person);

                        makeToast();
                        holder.btnCall.setImageAlpha(alpha2);
                        fireClick = false;
                    }
                    return false;
                });
            }
            if (REMOTE_CONFIG_ALLOW_EMAIL_CONTACT.equals("false")) {
                holder.btnEmail.hide();
            } else {
                Boolean clickEmail = true;
                if (EMAIL.equals("")) {
                    holder.btnEmail.setImageAlpha(alpha2);
                    clickEmail = false;
                }
                if (clickEmail) {
                    holder.btnEmail.setOnClickListener(view -> {
                        String hasPolicy = holder.hasPolicy.getText().toString();
                        if (fireClick == true) {
                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                            if (PERSIST_ACTION_IN_PROGRESS.equals("false")) {

                                EMAIL = rsIP_EMAIL.get(position);
                                CNAM03 = rsIP_CNAM03.get(position);
                                mPersistenceObjDao.updateData("PERSIST_ALLOW_EMAIL_REPORT", CNAM03);
                                mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_CALLER", "LIST_INVOLVED_PARTY");
                                mPersistenceObjDao.updateData("PERSIST_FREE_FORM_EMAIL_SEND_TO", EMAIL);
                                //    String Email = res.getString(R.string.email_email);
                                emailIntent = new Intent(
                                        Intent.ACTION_SEND);
                                emailIntent.setAction(Intent.ACTION_SEND);
                                emailIntent.setType("text/html");
                                emailIntent.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{EMAIL});
                                emailIntent.putExtra(Intent.EXTRA_CC, "");
                                emailIntent.putExtra(Intent.EXTRA_BCC, "");
                                emailIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                //  emailIntent.setType("text/html");
                                //  intent = new Intent(Intent.ACTION_SEND);
                                //  intent.putExtra(intent.EXTRA_EMAIL, new String[] { EMAIL });
                                //  intent.addFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                rotateAnimation.setRepeatCount(1);
                                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                                rotateAnimation.setDuration(100);
                                holder.btnEmail.startAnimation(rotateAnimation);
                                scheduleDismissEmail();


                            }
                        }

                        holder.btnEmail.setImageAlpha(alpha1);

                        fireClick = true;
                    });
                }

                holder.btnEmail.setOnLongClickListener(view -> {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                        message = res.getString(R.string.email_this_person);

                        makeToast();
                        holder.btnEmail.setImageAlpha(alpha2);
                        fireClick = false;
                    }
                    return false;
                });
            }
        }
        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE") || DA_CALLER.equals("LIST_INSURED_PEOPLE") || DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
            holder.btnCall.hide();
            holder.btnEmail.hide();
            holder.btnNote.hide();
            holder.btnGallery.hide();
            holder.btnMedia.hide();
            holder.btnPolicy.hide();
            holder.hasPolicy.setVisibility(View.INVISIBLE);
            holder.rl01.setVisibility(View.INVISIBLE);
        }
        rowView.setOnClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                if (ActionInProgress == false) {
                    String DA_ID = rsIP_AID.get(position);
                    mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                    DA_ID = rsIP_ID.get(position);
                    mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    rowView.startAnimation(rotateAnimation);
                    pos = position;

                    if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE")) {
                        DA_ID = rsIP_AID.get(position);
                        if (isNumber(DA_ID)) {
                            VMAID_ID = Integer.parseInt(DA_ID);
                        } else {
                            VMAID_ID = 0;
                        }
                        DA_ID = rsIP_ID.get(position);
                        if (isNumber(DA_ID)) {
                            VMIP_ID = Integer.parseInt(DA_ID);
                        } else {
                            VMIP_ID = 0;
                        }
                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            VMIV_ID = Integer.parseInt(DA_ID);
                        } else {
                            VMIV_ID = 0;
                        }
                        mVehicleManifestDao.deleteVMIP_ID(VMIP_ID);
                        mVehicleManifestDao.deleteVMIV_IDWZ(VMIV_ID);
                        AddData(VMAID_ID, VMIV_ID, VMIP_ID);
                        intent = new Intent(context, ListInvolvedParty.class);
                        ActionInProgress = true;
                        scheduleDismissIntent();

                    }
                    if (DA_CALLER.equals("LIST_INSURED_PEOPLE")) {
                        mPersistenceObjDao.updateData("PERSIST_IP_CALLER", "INVOLVED_MENU");
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            IPP_AID = Integer.parseInt(DA_ID);
                        } else {
                            IPP_AID = 0;
                        }

                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            IPP_POID = Integer.parseInt(DA_ID);
                        } else {
                            IPP_POID = 0;
                        }
                        DA_ID_STR = rsIP_ID.get(position);
                        if (isNumber(DA_ID_STR)) {
                            IPP_IPID = Integer.parseInt(DA_ID_STR);
                        } else {
                            IPP_IPID = 0;
                        }


                        String IPP_V1 = "";
                        String IPP_V2 = "";
                        String IPP_V3 = "";
                        String IPP_V4 = "";
                        String IPP_V5 = "";
                        String IPP_V6 = "";
                        String IPP_V7 = "";
                        String IPP_V8 = "";
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_HOLDER");
                        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID_STR)) {
                            CURRENT_IPO_HOLDER = Integer.parseInt(DA_ID_STR);
                        } else {
                            CURRENT_IPO_HOLDER = 0;
                        }


                        Integer IPP_HOLDER = CURRENT_IPO_HOLDER;

                        mInsurancePolicyPDao.addData(IPP_AID, IPP_POID,
                                IPP_IPID, IPP_V1, IPP_V2, IPP_V3,
                                IPP_V4, IPP_V5, IPP_V6, IPP_V7, IPP_V8, IPP_HOLDER);
                    }

                    if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {

                        mPersistenceObjDao.updateData("PERSIST_IV_ID", "0");
                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_INVOLVED_PARTY");
                    }
                    if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
                        DA_ID = rsIP_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_IPO_HOLDER", DA_ID);

                        mPersistenceObjDao.updateData("PERSIST_ADD_INSURANCE_POLICY_CALLER", "LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY_LIST_INVOLVED_PARTY");

                    }
                    if (ActionInProgress == false) {

                        scheduleDoListItem();
                    }
                }
            }
        });
        return rowView;
    }
    private void scheduleDoListItem() {
          Handler handler = new Handler();
        handler.postDelayed(this::doListItem, 200);
    }
    private void doListItem() {
    //    if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE")) {

       //     intent = new Intent(context, ListInvolvedVehicle.class);
      //      dismissIntent();
          //  context.startActivity(intent);
     //   }
        if (DA_CALLER.equals("LIST_INSURED_PEOPLE")) {

            intent = new Intent(context, ListInsuredPeople.class);
            dismissIntent();
           // context.startActivity(intent);
        }

        if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {

            intent = new Intent(context, AddNotes.class);
            dismissIntent();
           // context.startActivity(intent);
        }
        if (DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {

            intent = new Intent(context, AddInsurancePolicy.class);
            dismissIntent();
          //  context.startActivity(intent);

        }

        if (!DA_CALLER.equals("LIST_INVOLVED_VEHICLE") && !DA_CALLER.equals("LIST_INSURED_PEOPLE") && !DA_CALLER.equals("SELECT_NOTE_ATTACHMENT") && !DA_CALLER.equals("LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY")) {
            intent = new Intent(context, UpdateInvolvedParty.class);
            dismissIntent();
         //   context.startActivity(intent);
        }



    }
    private void AddData(Integer VMAID_ID, Integer VMIV_ID, Integer VMIP_ID) {
        long insertData = mVehicleManifestDao.addData(VMAID_ID, VMIV_ID, VMIP_ID);
        // Add Test Comment
        // Add Second Comment

    }

    private void makeToast() {
        int duration = 20;
        int type = 0;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);

        mdToast.show();

    }

    public class Holder {

        RelativeLayout rl01;
        FloatingActionButton btnEmail;
        FloatingActionButton btnCall;
        FloatingActionButton btnNote;
        FloatingActionButton btnPolicy;
        FloatingActionButton btnDelete;
        TextView hasPolicy;
        FloatingActionButton btnGallery;
        FloatingActionButton btnMedia;
        FloatingActionButton btnViewVehicle;
      //  TextView rmvText;
        TextView tvIP_PTYPE;
        TextView tvIP_ID;
        TextView tvAID_ID;
        TextView tvIP_FNAME;
        TextView tvIP_MI;
        TextView tvIP_LNAME;
        TextView tvIV_ID;
        TextView tvIV_YEAR;
        TextView tvIV_MAKE;
        TextView tvIV_MODEL;
        RelativeLayout ll01;
        RelativeLayout rl02;
        //   TextView tv10;
        //   TextView tv11;
        //   TextView tv12;
        //   TextView tv13;
        //   TextView tv14;
        //
        //   TextView tv16;
        //   TextView tv17;
        //   TextView tv18;
        //   TextView tv19;
        //   TextView tv20;
        //   TextView tv21;
        //   TextView tv22;
        //   TextView tv23;
        //   TextView tv24;
        //   TextView tv25;
        //   TextView tv26;

    }

    private void scheduleSelectedItemPhon() {
        Handler handler = new Handler();
        handler.postDelayed(this::getSelectedItemPhon, 200);
    }
    public void getSelectedItemPhon() {
        int itemSelected = 0;
        // Resources res = this.getResources();
        new AlertDialog.Builder(context)
                .setTitle(res.getString(R.string.select_phone_number)

                )
                .setCancelable(false)

                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                DA_RESULT = resulty.get(selectedIndex);
                                Intent intent = new Intent(Intent.ACTION_CALL);

                                intent.setData(Uri.parse("tel:" + DA_RESULT));

                                if (ActivityCompat.checkSelfPermission(ListInvolvedPartyAdapter.this.context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    //   return;
                                    intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + DA_RESULT));
                                    context.startActivity(intent);
                                } else {
                                    context.startActivity(intent);
                                    dialogInterface.dismiss();
                                }

                                dialogInterface.dismiss();
                            }
                        }
                )

                .setNegativeButton(res.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //  startLicenseInput();
                        dialogInterface.dismiss();
                    }
                })

                .show();


    }

    public void getConfig() {

        REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_LEFT_ICON);
        REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_BOTTOM_RIGHT_ICON);


        //    REMOTE_CONFIG_KEY = "affiliate_search_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE_address";
        REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_EMAIL_ADDRESS_VALUE);


        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_email";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_EMAIL_VALUE);


        //	REMOTE_CONFIG_KEY = "affiliate_search_allow_call";
        REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_ALLOW_CALL_VALUE);

        //	REMOTE_CONFIG_KEY = "affiliate_search_phone_number";
        REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_PHONE_NUMBER_VALUE);

        //REMOTE_CONFIG_KEY = "search_internet_for_attorney_only";
        REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_SEARCH_INTERNET_FOR_ATTORNEY_ONLY);

        //  REMOTE_CONFIG_KEY = "allow_email_contact";
        REMOTE_CONFIG_ALLOW_EMAIL_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_EMAIL_CONTACT);

        //  REMOTE_CONFIG_KEY = "allow_phone_contact";
        REMOTE_CONFIG_ALLOW_PHONE_CONTACT = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_ALLOW_PHONE_CONTACT);

        //	REMOTE_CONFIG_KEY = "default_referral_phone_not_email;
        REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_DEFAULT_REFERAL_PHONE_NOT_EMAIL);

        //	REMOTE_CONFIG_KEY = "affiliate_search_show_phone_number";
        REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER = getPersistantRemoteConfig(KEY_REMOTE_CONFIG_AFFILIATE_SEARCH_SHOW_PHONE_NUMBER);
    }

    public String getPersistantRemoteConfig(String PERSISTENCE_KEY) {

        persistenceObj = mPersistenceObjDao.getPersistence(PERSISTENCE_KEY);
        String PERSISTENCE_VALUE = persistenceObj.getPERSISTENCE_VALUE();
        return PERSISTENCE_VALUE;
    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        mAccidentNoteDao.closeAll();
        mInvolvedPartyDao.closeAll();
        //mDeviceUserDao.closeAll();
        mInsurancePolicyPDao.closeAll();
        mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        mVehicleManifestDao.closeAll();

    }
    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 200);
    }
    private void dismissIntent() {
        doClose();
        context.startActivity(intent);
    }
    private void scheduleDismissEmail() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissEmail, 250);
    }
    private void dismissEmail() {


        context.startActivity(emailIntent);
      //  boolean sendresult = basicSend(context, res, EMAIL);

    }

}
