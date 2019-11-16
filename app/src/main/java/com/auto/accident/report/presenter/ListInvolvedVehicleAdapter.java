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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.AccidentNoteDao;
import com.auto.accident.report.models.InsurancePolicyVDao;
import com.auto.accident.report.models.InvolvedImageStoreDao;
import com.auto.accident.report.models.InvolvedPartyDao;
import com.auto.accident.report.models.InvolvedVehicleDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.models.VehicleManifestDao;
import com.auto.accident.report.objects.AccidentNote;
import com.auto.accident.report.objects.InsurancePolicyV;
import com.auto.accident.report.objects.InvolvedImageStore;
import com.auto.accident.report.objects.InvolvedParty;
import com.auto.accident.report.objects.InvolvedVehicle;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.objects.VehicleManifest;
import com.auto.accident.report.photos.CameraActivity;
import com.auto.accident.report.photos.PhotoGalleryActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.auto.accident.report.util.utils.isNumber;
/**
 * Created by myron on 3/12/2018.
 */

class ListInvolvedVehicleAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ImageView newHolder;
    //   String [] result1;
    private final InsurancePolicyVDao mInsurancePolicyVDao;
    private Context context;
    private final InvolvedPartyDao mInvolvedPartyDao;
    private final VehicleManifestDao mVehicleManifestDao;
    private final PersistenceObjDao mPersistenceObjDao;
    private final InvolvedImageStoreDao mInvolvedImageStoreDao;
    private final AccidentNoteDao mAccidentNoteDao;
    //  String [] result0;
    private final ArrayList<String> rsIV_ID = new ArrayList<>();
    private final ArrayList<String> rsIV_AID = new ArrayList<>();
    private final ArrayList<String> rsIV_MAKE = new ArrayList<>();
    private final ArrayList<String> rsIV_MODEL = new ArrayList<>();
    ArrayList<String> rsIV_LNAME = new ArrayList<>();
    private final ArrayList<String> rsIV_YEAR = new ArrayList<>();
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Boolean fireClick = true;
    private String message;

    private final String DA_CALLER;

    private List<InsurancePolicyV> InsurancePolicyVList = new ArrayList<>();
    private PersistenceObj persistenceObj;
    private InvolvedParty involvedParty;
    private Intent intent;
    private boolean ActionInProgress;
    private RotateAnimation rotateAnimation;
    private Resources res;
    private int personInt;

    private boolean personinvehicleBool;
    private int AID_ID;

    private int CURRENT_IPO_HOLDER;

    private int IPV_AID;
    private int IPV_HOLDER;
    private int IPV_IVID;
    private int IPV_POID;

    private int IV_ID;

    private int pos;

    private int VMAID_ID;
    private int VMIP_ID;
    private int VMIV_ID;

    private String DA_ID;
    private String DA_ID_STR;
    private String PERSIST_ACTION_IN_PROGRESS;

    public ListInvolvedVehicleAdapter(ListInvolvedVehicle ListInvolvedVehicle) {
        // TODO Auto-generated constructor stub
        context = ListInvolvedVehicle;
        ActionInProgress = false;
        mAccidentNoteDao = new AccidentNoteDao(context);
        mInvolvedPartyDao = new InvolvedPartyDao(context);
        mVehicleManifestDao = new VehicleManifestDao(context);
        mInvolvedImageStoreDao = new InvolvedImageStoreDao(context);
        mPersistenceObjDao = new PersistenceObjDao(context);
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_VEHICLE");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_VEHICLE");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_VEHICLE");
        mPersistenceObjDao.updateData("PERSIST_DV_MODE", "SELECTPROFILE");
        mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "LIST_INVOLVED_VEHICLE");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();

        if (isNumber(DA_ID)) {
            AID_ID = Integer.parseInt(DA_ID);
        } else {
            AID_ID = 0;
        }

        personinvehicleBool = false;
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            personInt = Integer.parseInt(DA_ID_STR);
        } else {
            personInt = 0;
        }

        mInsurancePolicyVDao = new InsurancePolicyVDao(context);
        InvolvedVehicleDao mInvolvedVehicleDao = new InvolvedVehicleDao(context);
        List<InvolvedVehicle> involvedvehicleList = new ArrayList<>();
        if (AID_ID != 0) {
            if (!DA_CALLER.equals("LIST_INSURED_VEHICLES")) {
                involvedvehicleList = mInvolvedVehicleDao.getAllInvolvedVehicles(AID_ID);
            }
            if (DA_CALLER.equals("LIST_INSURED_VEHICLES")) {
                involvedvehicleList = mInvolvedVehicleDao.getAllUninsuredInvolvedVehicles(AID_ID);
            }
        } else {
            involvedvehicleList = mInvolvedVehicleDao.getAllInvolvedVehiclesAll();
        }
        for (InvolvedVehicle involvedVehicle : involvedvehicleList) {
            rsIV_ID.add(Integer.toString(involvedVehicle.getIV_ID()));
            rsIV_AID.add(Integer.toString(involvedVehicle.getIV_AID()));
            rsIV_YEAR.add(involvedVehicle.getIV_YEAR());
            rsIV_MAKE.add(involvedVehicle.getIV_MAKE());
            rsIV_MODEL.add(involvedVehicle.getIV_MODEL());

        }


        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsIV_ID.size();

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
        rowView = inflater.inflate(R.layout.list_involved_vehicle_adapter, null);
        //  holder.tv0= rowView.findViewById(R.id.textView0);
        //  holder.tv1= rowView.findViewById(R.id.textView1);
        holder.ll01 = rowView.findViewById(R.id.ll01);
        holder.rl02 = rowView.findViewById(R.id.rl02);
        holder.ll03 = rowView.findViewById(R.id.ll03);

        holder.ll05 = rowView.findViewById(R.id.ll05);
        holder.ll10 = rowView.findViewById(R.id.ll10);
        holder.ll11 = rowView.findViewById(R.id.ll11);
        holder.ll12 = rowView.findViewById(R.id.ll12);
        holder.ll13 = rowView.findViewById(R.id.ll13);
        holder.ll14 = rowView.findViewById(R.id.ll14);
        holder.ll15 = rowView.findViewById(R.id.ll15);
        holder.ll16 = rowView.findViewById(R.id.ll16);
        holder.ll17 = rowView.findViewById(R.id.ll17);
        holder.ll18 = rowView.findViewById(R.id.ll19);
        holder.ll19 = rowView.findViewById(R.id.ll19);
        holder.btnNote = rowView.findViewById(R.id.btnNote);
        holder.btnGallery = rowView.findViewById(R.id.btnGallery);
        holder.btnMedia = rowView.findViewById(R.id.btnMedia);
        holder.btnDelete2 = rowView.findViewById(R.id.btnDelete2);
        holder.btnCamera = rowView.findViewById(R.id.btnCamera);
        holder.btnPassenger = rowView.findViewById(R.id.btnPassenger);
        holder.iv004 = rowView.findViewById(R.id.imageView004);
        holder.iv005 = rowView.findViewById(R.id.imageView005);
        holder.iv006 = rowView.findViewById(R.id.imageView006);
        holder.iv007 = rowView.findViewById(R.id.imageView007);
        holder.iv008 = rowView.findViewById(R.id.imageView008);
        holder.iv009 = rowView.findViewById(R.id.imageView009);
        holder.iv010 = rowView.findViewById(R.id.imageView010);
        holder.iv011 = rowView.findViewById(R.id.imageView011);
        holder.iv012 = rowView.findViewById(R.id.imageView012);
        holder.iv013 = rowView.findViewById(R.id.imageView013);
        holder.iv014 = rowView.findViewById(R.id.imageView014);
        holder.iv015 = rowView.findViewById(R.id.imageView015);
        holder.iv016 = rowView.findViewById(R.id.imageView016);
        holder.iv017 = rowView.findViewById(R.id.imageView017);
        holder.iv018 = rowView.findViewById(R.id.imageView018);
        holder.iv019 = rowView.findViewById(R.id.imageView019);
        holder.iv020 = rowView.findViewById(R.id.imageView020);
        holder.iv021 = rowView.findViewById(R.id.imageView021);
        holder.iv022 = rowView.findViewById(R.id.imageView022);
        holder.iv023 = rowView.findViewById(R.id.imageView023);
        holder.iv024 = rowView.findViewById(R.id.imageView024);
        holder.iv025 = rowView.findViewById(R.id.imageView025);
        holder.iv026 = rowView.findViewById(R.id.imageView026);
        holder.iv027 = rowView.findViewById(R.id.imageView027);
        holder.iv028 = rowView.findViewById(R.id.imageView028);
        holder.iv029 = rowView.findViewById(R.id.imageView029);
        holder.iv030 = rowView.findViewById(R.id.imageView030);
        holder.iv031 = rowView.findViewById(R.id.imageView031);
        holder.iv032 = rowView.findViewById(R.id.imageView032);
        holder.iv033 = rowView.findViewById(R.id.imageView033);
        holder.iv034 = rowView.findViewById(R.id.imageView034);
        holder.iv035 = rowView.findViewById(R.id.imageView035);
        holder.iv036 = rowView.findViewById(R.id.imageView036);
        holder.iv037 = rowView.findViewById(R.id.imageView037);
        holder.iv038 = rowView.findViewById(R.id.imageView038);
        holder.iv039 = rowView.findViewById(R.id.imageView039);
        holder.iv040 = rowView.findViewById(R.id.imageView040);
        holder.iv041 = rowView.findViewById(R.id.imageView041);
        holder.iv042 = rowView.findViewById(R.id.imageView042);
        holder.iv043 = rowView.findViewById(R.id.imageView043);
        holder.iv044 = rowView.findViewById(R.id.imageView044);
        holder.iv045 = rowView.findViewById(R.id.imageView045);
        holder.iv046 = rowView.findViewById(R.id.imageView046);
        holder.iv047 = rowView.findViewById(R.id.imageView047);
        holder.iv048 = rowView.findViewById(R.id.imageView048);
        holder.iv049 = rowView.findViewById(R.id.imageView049);
        holder.iv050 = rowView.findViewById(R.id.imageView050);
        holder.iv051 = rowView.findViewById(R.id.imageView051);
        holder.iv052 = rowView.findViewById(R.id.imageView052);
        holder.iv053 = rowView.findViewById(R.id.imageView053);
        holder.iv054 = rowView.findViewById(R.id.imageView054);
        holder.iv055 = rowView.findViewById(R.id.imageView055);
        holder.iv056 = rowView.findViewById(R.id.imageView056);
        holder.iv057 = rowView.findViewById(R.id.imageView057);
        holder.iv058 = rowView.findViewById(R.id.imageView058);
        holder.iv059 = rowView.findViewById(R.id.imageView059);
        holder.iv060 = rowView.findViewById(R.id.imageView060);
        holder.iv061 = rowView.findViewById(R.id.imageView061);
        holder.iv062 = rowView.findViewById(R.id.imageView062);
        holder.iv063 = rowView.findViewById(R.id.imageView063);
        holder.iv064 = rowView.findViewById(R.id.imageView064);
        holder.iv065 = rowView.findViewById(R.id.imageView065);
        holder.iv066 = rowView.findViewById(R.id.imageView066);
        holder.iv067 = rowView.findViewById(R.id.imageView067);
        holder.iv068 = rowView.findViewById(R.id.imageView068);
        holder.iv069 = rowView.findViewById(R.id.imageView069);
        holder.iv070 = rowView.findViewById(R.id.imageView070);
        holder.iv071 = rowView.findViewById(R.id.imageView071);
        holder.iv072 = rowView.findViewById(R.id.imageView072);
        holder.iv073 = rowView.findViewById(R.id.imageView073);
        holder.iv074 = rowView.findViewById(R.id.imageView074);
        holder.iv075 = rowView.findViewById(R.id.imageView075);
        holder.iv076 = rowView.findViewById(R.id.imageView076);
        holder.iv077 = rowView.findViewById(R.id.imageView077);
        holder.iv078 = rowView.findViewById(R.id.imageView078);
        holder.iv079 = rowView.findViewById(R.id.imageView079);
        holder.iv080 = rowView.findViewById(R.id.imageView080);
        holder.iv081 = rowView.findViewById(R.id.imageView081);
        holder.iv082 = rowView.findViewById(R.id.imageView082);
        holder.iv083 = rowView.findViewById(R.id.imageView083);
        holder.iv084 = rowView.findViewById(R.id.imageView084);
        holder.iv085 = rowView.findViewById(R.id.imageView085);
        holder.iv086 = rowView.findViewById(R.id.imageView086);
        holder.iv087 = rowView.findViewById(R.id.imageView087);
        holder.iv088 = rowView.findViewById(R.id.imageView088);
        holder.iv089 = rowView.findViewById(R.id.imageView089);
        holder.iv090 = rowView.findViewById(R.id.imageView090);
        holder.iv091 = rowView.findViewById(R.id.imageView091);
        holder.iv092 = rowView.findViewById(R.id.imageView092);
        holder.iv093 = rowView.findViewById(R.id.imageView093);
        holder.iv094 = rowView.findViewById(R.id.imageView094);
        holder.iv095 = rowView.findViewById(R.id.imageView095);
        holder.iv096 = rowView.findViewById(R.id.imageView096);
        holder.iv097 = rowView.findViewById(R.id.imageView097);
        holder.iv098 = rowView.findViewById(R.id.imageView098);
        holder.iv099 = rowView.findViewById(R.id.imageView099);
        holder.iv100 = rowView.findViewById(R.id.imageView100);

        holder.tvIV_ID = rowView.findViewById(R.id.tvIV_ID);
        holder.tvAID_ID = rowView.findViewById(R.id.tvAID_ID);
        holder.tvIV_YEAR  = rowView.findViewById(R.id.tvIV_YEAR);
        holder.tvIV_MAKE = rowView.findViewById(R.id.tvIV_MAKE);
        holder.tvIV_MODEL  = rowView.findViewById(R.id.tvIV_MODEL);
        holder.tvIV_ID.setText(rsIV_ID.get(position));
        holder.tvAID_ID.setText (rsIV_AID.get(position));
        holder.tvIV_YEAR.setText (rsIV_YEAR.get(position));
        holder.tvIV_MAKE.setText (rsIV_MAKE.get(position));
        holder.tvIV_MODEL.setText (rsIV_MODEL.get(position));
        holder.btnPolicy = rowView.findViewById(R.id.btnPolicy);
        holder.hasPolicy = rowView.findViewById(R.id.hasPolicy);
        holder.hasPolicy.setText("true");
        holder.btnPassenger.setImageResource(R.drawable.ic_person_add_white_36dp);

        DA_ID_STR = rsIV_AID.get(position);
        if (isNumber(DA_ID_STR)) {
            AID_ID = Integer.parseInt(DA_ID_STR);
        } else {
            AID_ID = 0;
        }

        DA_ID_STR = rsIV_ID.get(position);
        if (isNumber(DA_ID_STR)) {
            IV_ID = Integer.parseInt(DA_ID_STR);
        } else {
            IV_ID = 0;
        }

        InsurancePolicyVDao mInsurancePolicyVDao = new InsurancePolicyVDao(context);
        List<InsurancePolicyV> InsurancePolicyVList = mInsurancePolicyVDao.getInsuranceAllPolicyVs2(AID_ID, IV_ID);
        if (InsurancePolicyVList.size() == 0) {
            holder.btnPolicy.setImageResource(R.drawable.add_car_insurance);
            holder.hasPolicy.setText("false");
        }
        List<AccidentNote> accidentnoteList = new ArrayList<>();
        accidentnoteList = mAccidentNoteDao.getIVAccidentNotes(AID_ID, IV_ID);
        if (accidentnoteList.size() == 0) {
            holder.btnNote.setImageResource(R.drawable.ic_note_add_white_48dp);
            holder.btnNote.setRotation(0);

        }

        holder.btnPolicy.setOnClickListener(view -> {
            String hasPolicy = holder.hasPolicy.getText().toString();
            String DA_ID = rsIV_ID.get(position);
            mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
            mPersistenceObjDao.updateData("PERSIST_INSURANCE_POLICY_VEHICLE_CALLER", "LIST_INVOLVED_VEHICLE");
            mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "LIST_INVOLVED_VEHICLES");
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    intent = new Intent(context, ListVehicleCoverage.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    holder.btnPolicy.startAnimation(rotateAnimation);
                    scheduleDismissIntent();
                }
             }

            holder.btnPolicy.setImageAlpha(alpha1);

            fireClick = true;
        });
        holder.btnPolicy.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {


                context = view.getContext();
                Resources res = context.getResources();

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
        if (1 == 2) {
            holder.btnNote.setOnClickListener(view -> {
                if (fireClick == true) {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false")) {

                        context = view.getContext();
                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_VEHICLE");
                        String DA_ID = rsIV_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                        mPersistenceObjDao.updateData("PERSIST_IP_ID", "-1");
                        float x = holder.btnNote.getRotation();
                        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setRepeatCount(1);
                        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                        rotateAnimation.setDuration(100);
                        holder.btnNote.startAnimation(rotateAnimation);
                        if (x == 0) {
                            intent = new Intent(context, AddNotes.class);
                            scheduleDismissIntent();
                            //context.startActivity(intent);
                        } else {
                            intent = new Intent(context, ListNotes.class);
                            scheduleDismissIntent();
                            //  context.startActivity(intent);
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
                        context = view.getContext();
                        Resources res = context.getResources();
                        holder.btnNote.setImageAlpha(alpha2);
                        message = res.getString(R.string.avn);
                        makeToast();

                    } else {
                        context = view.getContext();
                        Resources res = context.getResources();
                        holder.btnNote.setImageAlpha(alpha2);
                        message = res.getString(R.string.wivn);
                        makeToast();
                    }
                    fireClick = false;
                }
                return false;
            });
        }
        int DA_COUNT = 0;
        if (!DA_CALLER.equals("SELECT_NOTE_ATTACHMENT") && !DA_CALLER.equals("LIST_INSURED_VEHICLES")) {
            List<VehicleManifest> vehicleManifestList = new ArrayList<>();
            vehicleManifestList = mVehicleManifestDao.getVehicleManifest(IV_ID, AID_ID);
            int DA_SIZE = vehicleManifestList.size();
            DA_COUNT = 2;
            String DA_CNT = Integer.toString(DA_COUNT);
            for (VehicleManifest vehicleManifest : vehicleManifestList) {
                int IP_ID = vehicleManifest.getVMIP_ID();
                if (IP_ID != 0) {
                    if (IP_ID == personInt) {
                        personinvehicleBool = true;
                    }
                    involvedParty = mInvolvedPartyDao.getInvolvedParty(IP_ID, AID_ID);
                    String DA_NAME = involvedParty.getIP_FNAME() + " " + involvedParty.getIP_MI() + " " + involvedParty.getIP_LNAME();
                    //  tieIP_FNAME.setText(involvedParty.getIP_FNAME());
                    if (DA_SIZE >= 1) {
                        switch (DA_CNT) {

                            case "0":
                                holder.iv004.setVisibility(VISIBLE);
                                holder.iv004.setContentDescription(DA_NAME);
                                holder.iv004.setTag(IP_ID);

                                holder.iv004.setOnClickListener(view -> {
                                    newHolder = holder.iv004;
                                    scheduleDismiss();
                                    message = holder.iv004.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "1":
                                holder.iv005.setVisibility(VISIBLE);
                                holder.iv005.setContentDescription(DA_NAME);
                                holder.iv005.setTag(IP_ID);
                                holder.iv005.setOnClickListener(view -> {
                                    newHolder = holder.iv005;
                                    scheduleDismiss();
                                    message = holder.iv005.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "2":
                                holder.iv006.setVisibility(VISIBLE);
                                holder.iv006.setContentDescription(DA_NAME);
                                holder.iv006.setTag(IP_ID);

                                holder.iv006.setOnClickListener(view -> {
                                    newHolder = holder.iv006;
                                    scheduleDismiss();
                                    message = holder.iv006.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });

                                break;
                            case "3":
                                holder.iv007.setVisibility(VISIBLE);
                                holder.iv007.setContentDescription(DA_NAME);
                                holder.iv007.setTag(IP_ID);

                                holder.iv007.setOnClickListener(view -> {
                                    newHolder = holder.iv007;
                                    scheduleDismiss();
                                    message = holder.iv007.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "4":
                                holder.iv008.setVisibility(VISIBLE);
                                holder.iv008.setContentDescription(DA_NAME);
                                holder.iv008.setTag(IP_ID);

                                holder.iv008.setOnClickListener(view -> {
                                    newHolder = holder.iv008;
                                    scheduleDismiss();
                                    message = holder.iv008.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "5":
                                holder.iv009.setVisibility(VISIBLE);
                                holder.iv009.setContentDescription(DA_NAME);
                                holder.iv009.setTag(IP_ID);

                                holder.iv009.setOnClickListener(view -> {
                                    newHolder = holder.iv009;
                                    scheduleDismiss();
                                    message = holder.iv009.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "6":
                                holder.ll11.setVisibility(VISIBLE);
                                holder.iv010.setVisibility(VISIBLE);
                                holder.iv010.setContentDescription(DA_NAME);
                                holder.iv010.setTag(IP_ID);

                                holder.iv010.setOnClickListener(view -> {
                                    newHolder = holder.iv010;
                                    scheduleDismiss();
                                    message = holder.iv010.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "7":

                                holder.iv011.setVisibility(VISIBLE);
                                holder.iv011.setContentDescription(DA_NAME);
                                holder.iv011.setTag(IP_ID);

                                holder.iv011.setOnClickListener(view -> {
                                    newHolder = holder.iv011;
                                    scheduleDismiss();
                                    message = holder.iv011.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "8":
                                holder.iv012.setVisibility(VISIBLE);
                                holder.iv012.setContentDescription(DA_NAME);
                                holder.iv012.setTag(IP_ID);

                                holder.iv012.setOnClickListener(view -> {
                                    newHolder = holder.iv012;
                                    scheduleDismiss();
                                    message = holder.iv012.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "9":
                                holder.iv013.setVisibility(VISIBLE);
                                holder.iv013.setContentDescription(DA_NAME);
                                holder.iv013.setTag(IP_ID);

                                holder.iv013.setOnClickListener(view -> {
                                    newHolder = holder.iv013;
                                    scheduleDismiss();
                                    message = holder.iv013.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "10":
                                holder.iv014.setVisibility(VISIBLE);
                                holder.iv014.setContentDescription(DA_NAME);
                                holder.iv014.setTag(IP_ID);

                                holder.iv014.setOnClickListener(view -> {
                                    newHolder = holder.iv014;
                                    scheduleDismiss();
                                    message = holder.iv014.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "11":
                                holder.iv015.setVisibility(VISIBLE);
                                holder.iv015.setContentDescription(DA_NAME);
                                holder.iv015.setTag(IP_ID);

                                holder.iv015.setOnClickListener(view -> {
                                    newHolder = holder.iv015;
                                    scheduleDismiss();
                                    message = holder.iv015.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "12":
                                holder.iv016.setVisibility(VISIBLE);
                                holder.iv016.setContentDescription(DA_NAME);
                                holder.iv016.setTag(IP_ID);

                                holder.iv016.setOnClickListener(view -> {
                                    newHolder = holder.iv016;
                                    scheduleDismiss();
                                    message = holder.iv016.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "13":
                                holder.iv017.setVisibility(VISIBLE);
                                holder.iv017.setContentDescription(DA_NAME);
                                holder.iv017.setTag(IP_ID);

                                holder.iv017.setOnClickListener(view -> {
                                    newHolder = holder.iv017;
                                    scheduleDismiss();
                                    message = holder.iv017.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "14":
                                holder.iv018.setVisibility(VISIBLE);
                                holder.iv018.setContentDescription(DA_NAME);
                                holder.iv018.setTag(IP_ID);

                                holder.iv018.setOnClickListener(view -> {
                                    newHolder = holder.iv018;
                                    scheduleDismiss();
                                    message = holder.iv018.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "15":
                                holder.iv019.setVisibility(VISIBLE);
                                holder.iv019.setContentDescription(DA_NAME);
                                holder.iv019.setTag(IP_ID);

                                holder.iv019.setOnClickListener(view -> {
                                    newHolder = holder.iv019;
                                    scheduleDismiss();
                                    message = holder.iv019.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "16":
                                holder.ll12.setVisibility(VISIBLE);
                                holder.iv020.setVisibility(VISIBLE);
                                holder.iv020.setContentDescription(DA_NAME);
                                holder.iv020.setTag(IP_ID);

                                holder.iv021.setOnClickListener(view -> {
                                    newHolder = holder.iv021;
                                    scheduleDismiss();
                                    message = holder.iv021.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "17":
                                holder.iv022.setVisibility(VISIBLE);
                                holder.iv022.setContentDescription(DA_NAME);
                                holder.iv022.setTag(IP_ID);

                                holder.iv022.setOnClickListener(view -> {
                                    newHolder = holder.iv022;
                                    scheduleDismiss();
                                    message = holder.iv022.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "18":
                                holder.iv023.setVisibility(VISIBLE);
                                holder.iv023.setContentDescription(DA_NAME);
                                holder.iv023.setTag(IP_ID);

                                holder.iv023.setOnClickListener(view -> {
                                    newHolder = holder.iv023;
                                    scheduleDismiss();
                                    message = holder.iv023.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "19":
                                holder.iv024.setVisibility(VISIBLE);
                                holder.iv024.setContentDescription(DA_NAME);
                                holder.iv024.setTag(IP_ID);

                                holder.iv024.setOnClickListener(view -> {
                                    newHolder = holder.iv024;
                                    scheduleDismiss();
                                    message = holder.iv024.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "20":
                                holder.iv025.setVisibility(VISIBLE);
                                holder.iv025.setContentDescription(DA_NAME);
                                holder.iv025.setTag(IP_ID);

                                holder.iv025.setOnClickListener(view -> {
                                    newHolder = holder.iv025;
                                    scheduleDismiss();
                                    message = holder.iv025.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "21":
                                holder.iv026.setVisibility(VISIBLE);
                                holder.iv026.setContentDescription(DA_NAME);
                                holder.iv026.setTag(IP_ID);

                                holder.iv026.setOnClickListener(view -> {
                                    newHolder = holder.iv026;
                                    scheduleDismiss();
                                    message = holder.iv026.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "22":
                                holder.iv027.setVisibility(VISIBLE);
                                holder.iv027.setContentDescription(DA_NAME);
                                holder.iv027.setTag(IP_ID);

                                holder.iv027.setOnClickListener(view -> {
                                    newHolder = holder.iv027;
                                    scheduleDismiss();
                                    message = holder.iv027.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "23":
                                holder.iv028.setVisibility(VISIBLE);
                                holder.iv028.setContentDescription(DA_NAME);
                                holder.iv028.setTag(IP_ID);

                                holder.iv028.setOnClickListener(view -> {
                                    newHolder = holder.iv028;
                                    scheduleDismiss();
                                    message = holder.iv028.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "24":
                                holder.iv029.setVisibility(VISIBLE);
                                holder.iv029.setContentDescription(DA_NAME);
                                holder.iv029.setTag(IP_ID);

                                holder.iv029.setOnClickListener(view -> {
                                    newHolder = holder.iv029;
                                    scheduleDismiss();
                                    message = holder.iv029.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "25":
                                holder.ll13.setVisibility(VISIBLE);
                                holder.iv030.setVisibility(VISIBLE);
                                holder.iv030.setContentDescription(DA_NAME);
                                holder.iv030.setTag(IP_ID);

                                holder.iv030.setOnClickListener(view -> {
                                    newHolder = holder.iv030;
                                    scheduleDismiss();
                                    message = holder.iv030.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "26":
                                holder.iv031.setVisibility(VISIBLE);
                                holder.iv031.setContentDescription(DA_NAME);
                                holder.iv031.setTag(IP_ID);

                                holder.iv031.setOnClickListener(view -> {
                                    newHolder = holder.iv031;
                                    scheduleDismiss();
                                    message = holder.iv031.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "27":
                                holder.iv032.setVisibility(VISIBLE);
                                holder.iv032.setContentDescription(DA_NAME);
                                holder.iv032.setTag(IP_ID);

                                holder.iv032.setOnClickListener(view -> {
                                    newHolder = holder.iv032;
                                    scheduleDismiss();
                                    message = holder.iv032.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "28":
                                holder.iv033.setVisibility(VISIBLE);
                                holder.iv033.setContentDescription(DA_NAME);
                                holder.iv033.setTag(IP_ID);

                                holder.iv033.setOnClickListener(view -> {
                                    newHolder = holder.iv033;
                                    scheduleDismiss();
                                    message = holder.iv033.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "29":
                                holder.iv034.setVisibility(VISIBLE);
                                holder.iv034.setContentDescription(DA_NAME);
                                holder.iv034.setTag(IP_ID);

                                holder.iv034.setOnClickListener(view -> {
                                    newHolder = holder.iv034;
                                    scheduleDismiss();
                                    message = holder.iv034.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "30":
                                holder.iv035.setVisibility(VISIBLE);
                                holder.iv035.setContentDescription(DA_NAME);
                                holder.iv035.setTag(IP_ID);

                                holder.iv035.setOnClickListener(view -> {
                                    newHolder = holder.iv035;
                                    scheduleDismiss();
                                    message = holder.iv035.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "31":
                                holder.iv036.setVisibility(VISIBLE);
                                holder.iv036.setContentDescription(DA_NAME);
                                holder.iv036.setTag(IP_ID);

                                holder.iv036.setOnClickListener(view -> {
                                    newHolder = holder.iv036;
                                    scheduleDismiss();
                                    message = holder.iv036.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "32":
                                holder.iv037.setVisibility(VISIBLE);
                                holder.iv037.setContentDescription(DA_NAME);
                                holder.iv037.setTag(IP_ID);

                                holder.iv037.setOnClickListener(view -> {
                                    newHolder = holder.iv037;
                                    scheduleDismiss();
                                    message = holder.iv037.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "33":
                                holder.iv038.setVisibility(VISIBLE);
                                holder.iv038.setContentDescription(DA_NAME);
                                holder.iv038.setTag(IP_ID);

                                holder.iv038.setOnClickListener(view -> {
                                    newHolder = holder.iv038;
                                    scheduleDismiss();
                                    message = holder.iv038.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "34":
                                holder.iv039.setVisibility(VISIBLE);
                                holder.iv039.setContentDescription(DA_NAME);
                                holder.iv039.setTag(IP_ID);

                                holder.iv039.setOnClickListener(view -> {
                                    newHolder = holder.iv039;
                                    scheduleDismiss();
                                    message = holder.iv039.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "35":
                                holder.ll14.setVisibility(VISIBLE);
                                holder.iv040.setVisibility(VISIBLE);
                                holder.iv040.setContentDescription(DA_NAME);
                                holder.iv040.setTag(IP_ID);

                                holder.iv040.setOnClickListener(view -> {
                                    newHolder = holder.iv040;
                                    scheduleDismiss();
                                    message = holder.iv040.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "36":
                                holder.iv041.setVisibility(VISIBLE);
                                holder.iv041.setContentDescription(DA_NAME);
                                holder.iv041.setTag(IP_ID);

                                holder.iv041.setOnClickListener(view -> {
                                    newHolder = holder.iv041;
                                    scheduleDismiss();
                                    message = holder.iv041.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "37":
                                holder.ll14.setVisibility(VISIBLE);
                                holder.iv042.setVisibility(VISIBLE);
                                holder.iv042.setContentDescription(DA_NAME);
                                holder.iv042.setTag(IP_ID);

                                holder.iv042.setOnClickListener(view -> {
                                    newHolder = holder.iv042;
                                    scheduleDismiss();
                                    message = holder.iv042.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;

                            case "38":
                                holder.iv043.setVisibility(VISIBLE);
                                holder.iv043.setContentDescription(DA_NAME);
                                holder.iv043.setTag(IP_ID);

                                holder.iv043.setOnClickListener(view -> {
                                    newHolder = holder.iv043;
                                    scheduleDismiss();
                                    message = holder.iv043.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "39":
                                holder.iv044.setVisibility(VISIBLE);
                                holder.iv044.setContentDescription(DA_NAME);
                                holder.iv044.setTag(IP_ID);

                                holder.iv044.setOnClickListener(view -> {
                                    newHolder = holder.iv044;
                                    scheduleDismiss();
                                    message = holder.iv044.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "40":
                                holder.iv045.setVisibility(VISIBLE);
                                holder.iv045.setContentDescription(DA_NAME);
                                holder.iv045.setTag(IP_ID);

                                holder.iv045.setOnClickListener(view -> {
                                    newHolder = holder.iv045;
                                    scheduleDismiss();
                                    message = holder.iv045.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "41":
                                holder.iv046.setVisibility(VISIBLE);
                                holder.iv046.setContentDescription(DA_NAME);
                                holder.iv046.setTag(IP_ID);

                                holder.iv046.setOnClickListener(view -> {
                                    newHolder = holder.iv046;
                                    scheduleDismiss();
                                    message = holder.iv046.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "42":
                                holder.iv047.setVisibility(VISIBLE);
                                holder.iv047.setContentDescription(DA_NAME);
                                holder.iv047.setTag(IP_ID);

                                holder.iv047.setOnClickListener(view -> {
                                    newHolder = holder.iv047;
                                    scheduleDismiss();
                                    message = holder.iv047.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "43":
                                holder.iv048.setVisibility(VISIBLE);
                                holder.iv048.setContentDescription(DA_NAME);
                                holder.iv048.setTag(IP_ID);

                                holder.iv048.setOnClickListener(view -> {
                                    newHolder = holder.iv048;
                                    scheduleDismiss();
                                    message = holder.iv048.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "44":
                                holder.iv049.setVisibility(VISIBLE);
                                holder.iv049.setContentDescription(DA_NAME);
                                holder.iv049.setTag(IP_ID);

                                holder.iv049.setOnClickListener(view -> {
                                    newHolder = holder.iv049;
                                    scheduleDismiss();
                                    message = holder.iv049.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "45":
                                holder.ll15.setVisibility(VISIBLE);
                                holder.iv050.setVisibility(VISIBLE);
                                holder.iv050.setContentDescription(DA_NAME);
                                holder.iv050.setTag(IP_ID);

                                holder.iv050.setOnClickListener(view -> {
                                    newHolder = holder.iv050;
                                    scheduleDismiss();
                                    message = holder.iv050.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "46":
                                holder.iv051.setVisibility(VISIBLE);
                                holder.iv051.setContentDescription(DA_NAME);
                                holder.iv051.setTag(IP_ID);

                                holder.iv051.setOnClickListener(view -> {
                                    newHolder = holder.iv051;
                                    scheduleDismiss();
                                    message = holder.iv051.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;

                            case "47":
                                holder.iv052.setVisibility(VISIBLE);
                                holder.iv052.setContentDescription(DA_NAME);
                                holder.iv052.setTag(IP_ID);

                                holder.iv052.setOnClickListener(view -> {
                                    newHolder = holder.iv052;
                                    scheduleDismiss();
                                    message = holder.iv052.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "48":
                                holder.iv053.setVisibility(VISIBLE);
                                holder.iv053.setContentDescription(DA_NAME);
                                holder.iv053.setTag(IP_ID);

                                holder.iv053.setOnClickListener(view -> {
                                    newHolder = holder.iv053;
                                    scheduleDismiss();
                                    message = holder.iv053.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "49":
                                holder.iv054.setVisibility(VISIBLE);
                                holder.iv054.setContentDescription(DA_NAME);
                                holder.iv054.setTag(IP_ID);

                                holder.iv054.setOnClickListener(view -> {
                                    newHolder = holder.iv054;
                                    scheduleDismiss();
                                    message = holder.iv054.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "50":
                                holder.iv055.setVisibility(VISIBLE);
                                holder.iv055.setContentDescription(DA_NAME);
                                holder.iv055.setTag(IP_ID);

                                holder.iv055.setOnClickListener(view -> {
                                    newHolder = holder.iv055;
                                    scheduleDismiss();
                                    message = holder.iv055.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "51":
                                holder.iv056.setVisibility(VISIBLE);
                                holder.iv056.setContentDescription(DA_NAME);
                                holder.iv056.setTag(IP_ID);

                                holder.iv056.setOnClickListener(view -> {
                                    newHolder = holder.iv056;
                                    scheduleDismiss();
                                    message = holder.iv056.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "52":
                                holder.iv057.setVisibility(VISIBLE);
                                holder.iv057.setContentDescription(DA_NAME);
                                holder.iv057.setTag(IP_ID);

                                holder.iv057.setOnClickListener(view -> {
                                    newHolder = holder.iv057;
                                    scheduleDismiss();
                                    message = holder.iv057.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "53":
                                holder.iv058.setVisibility(VISIBLE);
                                holder.iv058.setContentDescription(DA_NAME);
                                holder.iv058.setTag(IP_ID);

                                holder.iv058.setOnClickListener(view -> {
                                    newHolder = holder.iv058;
                                    scheduleDismiss();
                                    message = holder.iv058.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "54":
                                holder.iv059.setVisibility(VISIBLE);
                                holder.iv059.setContentDescription(DA_NAME);
                                holder.iv059.setTag(IP_ID);

                                holder.iv059.setOnClickListener(view -> {
                                    newHolder = holder.iv059;
                                    scheduleDismiss();
                                    message = holder.iv059.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "55":
                                holder.ll16.setVisibility(VISIBLE);
                                holder.iv060.setVisibility(VISIBLE);
                                holder.iv060.setContentDescription(DA_NAME);
                                holder.iv060.setTag(IP_ID);

                                holder.iv060.setOnClickListener(view -> {
                                    newHolder = holder.iv060;
                                    scheduleDismiss();
                                    message = holder.iv060.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "56":
                                holder.iv061.setVisibility(VISIBLE);
                                holder.iv061.setContentDescription(DA_NAME);
                                holder.iv061.setTag(IP_ID);

                                holder.iv061.setOnClickListener(view -> {
                                    newHolder = holder.iv061;
                                    scheduleDismiss();
                                    message = holder.iv061.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "57":
                                holder.iv062.setVisibility(VISIBLE);
                                holder.iv062.setContentDescription(DA_NAME);
                                holder.iv062.setTag(IP_ID);

                                holder.iv062.setOnClickListener(view -> {
                                    newHolder = holder.iv062;
                                    scheduleDismiss();
                                    message = holder.iv062.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "58":
                                holder.iv063.setVisibility(VISIBLE);
                                holder.iv063.setContentDescription(DA_NAME);
                                holder.iv063.setTag(IP_ID);

                                holder.iv063.setOnClickListener(view -> {
                                    newHolder = holder.iv063;
                                    scheduleDismiss();
                                    message = holder.iv063.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "59":
                                holder.iv064.setVisibility(VISIBLE);
                                holder.iv064.setContentDescription(DA_NAME);
                                holder.iv064.setTag(IP_ID);

                                holder.iv064.setOnClickListener(view -> {
                                    newHolder = holder.iv064;
                                    scheduleDismiss();
                                    message = holder.iv064.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "60":
                                holder.iv065.setVisibility(VISIBLE);
                                holder.iv065.setContentDescription(DA_NAME);
                                holder.iv065.setTag(IP_ID);

                                holder.iv065.setOnClickListener(view -> {
                                    newHolder = holder.iv065;
                                    scheduleDismiss();
                                    message = holder.iv065.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "61":
                                holder.iv066.setVisibility(VISIBLE);
                                holder.iv066.setContentDescription(DA_NAME);
                                holder.iv066.setTag(IP_ID);

                                holder.iv066.setOnClickListener(view -> {
                                    newHolder = holder.iv066;
                                    scheduleDismiss();
                                    message = holder.iv066.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "62":
                                holder.iv067.setVisibility(VISIBLE);
                                holder.iv067.setContentDescription(DA_NAME);
                                holder.iv067.setTag(IP_ID);

                                holder.iv067.setOnClickListener(view -> {
                                    newHolder = holder.iv067;
                                    scheduleDismiss();
                                    message = holder.iv067.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "63":
                                holder.iv068.setVisibility(VISIBLE);
                                holder.iv068.setContentDescription(DA_NAME);
                                holder.iv068.setTag(IP_ID);

                                holder.iv068.setOnClickListener(view -> {
                                    newHolder = holder.iv068;
                                    scheduleDismiss();
                                    message = holder.iv068.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "64":
                                holder.iv069.setVisibility(VISIBLE);
                                holder.iv069.setContentDescription(DA_NAME);
                                holder.iv069.setTag(IP_ID);

                                holder.iv069.setOnClickListener(view -> {
                                    newHolder = holder.iv069;
                                    scheduleDismiss();
                                    message = holder.iv069.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "65":
                                holder.ll17.setVisibility(VISIBLE);
                                holder.iv070.setVisibility(VISIBLE);
                                holder.iv070.setContentDescription(DA_NAME);
                                holder.iv070.setTag(IP_ID);

                                holder.iv070.setOnClickListener(view -> {
                                    newHolder = holder.iv070;
                                    scheduleDismiss();
                                    message = holder.iv070.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "66":
                                holder.iv071.setVisibility(VISIBLE);
                                holder.iv071.setContentDescription(DA_NAME);
                                holder.iv071.setTag(IP_ID);

                                holder.iv071.setOnClickListener(view -> {
                                    newHolder = holder.iv071;
                                    scheduleDismiss();
                                    message = holder.iv071.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;

                            case "67":
                                holder.iv072.setVisibility(VISIBLE);
                                holder.iv072.setContentDescription(DA_NAME);
                                holder.iv072.setTag(IP_ID);

                                holder.iv072.setOnClickListener(view -> {
                                    newHolder = holder.iv072;
                                    scheduleDismiss();
                                    message = holder.iv072.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "68":
                                holder.iv073.setVisibility(VISIBLE);
                                holder.iv073.setContentDescription(DA_NAME);
                                holder.iv073.setTag(IP_ID);

                                holder.iv073.setOnClickListener(view -> {
                                    newHolder = holder.iv073;
                                    scheduleDismiss();
                                    message = holder.iv073.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "69":
                                holder.iv074.setVisibility(VISIBLE);
                                holder.iv074.setContentDescription(DA_NAME);
                                holder.iv074.setTag(IP_ID);

                                holder.iv074.setOnClickListener(view -> {
                                    newHolder = holder.iv074;
                                    scheduleDismiss();
                                    message = holder.iv074.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "70":
                                holder.iv075.setVisibility(VISIBLE);
                                holder.iv075.setContentDescription(DA_NAME);
                                holder.iv075.setTag(IP_ID);

                                holder.iv075.setOnClickListener(view -> {
                                    newHolder = holder.iv075;
                                    scheduleDismiss();
                                    message = holder.iv075.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "71":
                                holder.iv076.setVisibility(VISIBLE);
                                holder.iv076.setContentDescription(DA_NAME);
                                holder.iv076.setTag(IP_ID);

                                holder.iv076.setOnClickListener(view -> {
                                    newHolder = holder.iv076;
                                    scheduleDismiss();
                                    message = holder.iv076.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "72":
                                holder.iv077.setVisibility(VISIBLE);
                                holder.iv077.setContentDescription(DA_NAME);
                                holder.iv077.setTag(IP_ID);

                                holder.iv077.setOnClickListener(view -> {
                                    newHolder = holder.iv077;
                                    scheduleDismiss();
                                    message = holder.iv077.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "73":
                                holder.iv078.setVisibility(VISIBLE);
                                holder.iv078.setContentDescription(DA_NAME);
                                holder.iv078.setTag(IP_ID);

                                holder.iv078.setOnClickListener(view -> {
                                    newHolder = holder.iv078;
                                    scheduleDismiss();
                                    message = holder.iv078.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "74":
                                holder.iv079.setVisibility(VISIBLE);
                                holder.iv079.setContentDescription(DA_NAME);
                                holder.iv079.setTag(IP_ID);

                                holder.iv079.setOnClickListener(view -> {
                                    newHolder = holder.iv079;
                                    scheduleDismiss();
                                    message = holder.iv079.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "75":
                                holder.ll18.setVisibility(VISIBLE);
                                holder.iv080.setVisibility(VISIBLE);
                                holder.iv080.setContentDescription(DA_NAME);
                                holder.iv080.setTag(IP_ID);

                                holder.iv080.setOnClickListener(view -> {
                                    newHolder = holder.iv080;
                                    scheduleDismiss();
                                    message = holder.iv080.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "76":
                                holder.iv081.setVisibility(VISIBLE);
                                holder.iv081.setContentDescription(DA_NAME);
                                holder.iv081.setTag(IP_ID);

                                holder.iv081.setOnClickListener(view -> {
                                    newHolder = holder.iv081;
                                    scheduleDismiss();
                                    message = holder.iv081.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "77":
                                holder.iv082.setVisibility(VISIBLE);
                                holder.iv082.setContentDescription(DA_NAME);
                                holder.iv082.setTag(IP_ID);

                                holder.iv082.setOnClickListener(view -> {
                                    newHolder = holder.iv082;
                                    scheduleDismiss();
                                    message = holder.iv082.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "78":
                                holder.iv083.setVisibility(VISIBLE);
                                holder.iv083.setContentDescription(DA_NAME);
                                holder.iv083.setTag(IP_ID);

                                holder.iv083.setOnClickListener(view -> {
                                    newHolder = holder.iv083;
                                    scheduleDismiss();
                                    message = holder.iv083.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "79":
                                holder.iv084.setVisibility(VISIBLE);
                                holder.iv084.setContentDescription(DA_NAME);
                                holder.iv084.setTag(IP_ID);

                                holder.iv084.setOnClickListener(view -> {
                                    newHolder = holder.iv084;
                                    scheduleDismiss();
                                    message = holder.iv084.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "80":
                                holder.iv085.setVisibility(VISIBLE);
                                holder.iv085.setContentDescription(DA_NAME);
                                holder.iv085.setTag(IP_ID);

                                holder.iv085.setOnClickListener(view -> {
                                    newHolder = holder.iv085;
                                    scheduleDismiss();
                                    message = holder.iv085.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "81":
                                holder.iv086.setVisibility(VISIBLE);
                                holder.iv086.setContentDescription(DA_NAME);
                                holder.iv086.setTag(IP_ID);

                                holder.iv086.setOnClickListener(view -> {
                                    newHolder = holder.iv086;
                                    scheduleDismiss();
                                    message = holder.iv086.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "82":
                                holder.iv087.setVisibility(VISIBLE);
                                holder.iv087.setContentDescription(DA_NAME);
                                holder.iv087.setTag(IP_ID);

                                holder.iv087.setOnClickListener(view -> {
                                    newHolder = holder.iv087;
                                    scheduleDismiss();
                                    message = holder.iv087.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "83":
                                holder.iv088.setVisibility(VISIBLE);
                                holder.iv088.setContentDescription(DA_NAME);
                                holder.iv088.setTag(IP_ID);

                                holder.iv088.setOnClickListener(view -> {
                                    newHolder = holder.iv088;
                                    scheduleDismiss();
                                    message = holder.iv088.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "84":
                                holder.iv089.setVisibility(VISIBLE);
                                holder.iv089.setContentDescription(DA_NAME);
                                holder.iv089.setTag(IP_ID);

                                holder.iv089.setOnClickListener(view -> {
                                    newHolder = holder.iv089;
                                    scheduleDismiss();
                                    message = holder.iv089.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "85":
                                holder.ll19.setVisibility(VISIBLE);
                                holder.iv090.setVisibility(VISIBLE);
                                holder.iv090.setContentDescription(DA_NAME);
                                holder.iv090.setTag(IP_ID);

                                holder.iv091.setOnClickListener(view -> {
                                    newHolder = holder.iv091;
                                    scheduleDismiss();
                                    message = holder.iv091.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "86":
                                holder.iv092.setVisibility(VISIBLE);
                                holder.iv092.setContentDescription(DA_NAME);
                                holder.iv092.setTag(IP_ID);

                                holder.iv092.setOnClickListener(view -> {
                                    newHolder = holder.iv092;
                                    scheduleDismiss();
                                    message = holder.iv092.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "87":
                                holder.iv093.setVisibility(VISIBLE);
                                holder.iv093.setContentDescription(DA_NAME);
                                holder.iv093.setTag(IP_ID);

                                holder.iv093.setOnClickListener(view -> {
                                    newHolder = holder.iv093;
                                    scheduleDismiss();
                                    message = holder.iv093.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "88":
                                holder.iv094.setVisibility(VISIBLE);
                                holder.iv094.setContentDescription(DA_NAME);
                                holder.iv094.setTag(IP_ID);

                                holder.iv094.setOnClickListener(view -> {
                                    newHolder = holder.iv094;
                                    scheduleDismiss();
                                    message = holder.iv094.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "89":
                                holder.iv095.setVisibility(VISIBLE);
                                holder.iv095.setContentDescription(DA_NAME);
                                holder.iv095.setTag(IP_ID);

                                holder.iv095.setOnClickListener(view -> {
                                    newHolder = holder.iv095;
                                    scheduleDismiss();
                                    message = holder.iv095.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "90":
                                holder.iv096.setVisibility(VISIBLE);
                                holder.iv096.setContentDescription(DA_NAME);
                                holder.iv096.setTag(IP_ID);

                                holder.iv096.setOnClickListener(view -> {
                                    newHolder = holder.iv096;
                                    scheduleDismiss();
                                    message = holder.iv096.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "91":
                                holder.iv097.setVisibility(VISIBLE);
                                holder.iv097.setContentDescription(DA_NAME);
                                holder.iv097.setTag(IP_ID);

                                holder.iv097.setOnClickListener(view -> {
                                    newHolder = holder.iv097;
                                    scheduleDismiss();
                                    message = holder.iv097.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "92":
                                holder.iv098.setVisibility(VISIBLE);
                                holder.iv098.setContentDescription(DA_NAME);
                                holder.iv098.setTag(IP_ID);

                                holder.iv098.setOnClickListener(view -> {
                                    newHolder = holder.iv098;
                                    scheduleDismiss();
                                    message = holder.iv098.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "93":
                                holder.iv099.setVisibility(VISIBLE);
                                holder.iv099.setContentDescription(DA_NAME);
                                holder.iv099.setTag(IP_ID);

                                holder.iv099.setOnClickListener(view -> {
                                    newHolder = holder.iv099;
                                    scheduleDismiss();
                                    message = holder.iv099.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;
                            case "94":
                                holder.ll20.setVisibility(VISIBLE);
                                holder.iv100.setVisibility(VISIBLE);
                                holder.iv100.setContentDescription(DA_NAME);
                                holder.iv100.setTag(IP_ID);

                                holder.iv100.setOnClickListener(view -> {
                                    newHolder = holder.iv100;
                                    scheduleDismiss();
                                    message = holder.iv100.getContentDescription().toString();
                                    makeToast();
                                    //   fireClick = false;
                                    // return false
                                });
                                break;

                        }


                        DA_COUNT++;
                        DA_CNT = Integer.toString(DA_COUNT);
                    }
                }
            }
            if (DA_COUNT > 2) {
                holder.btnPassenger.setImageResource(R.drawable.ic_person_white_36dp);
            }
            if (!DA_CALLER.equals("LIST_INVOLVED_PARTY")) {
                List<InvolvedImageStore> involvedImageStoreList = new ArrayList<>();
                involvedImageStoreList = mInvolvedImageStoreDao.getAccPics(AID_ID, IV_ID);
                Boolean clickImage = true;
                fireClick = true;
                if (involvedImageStoreList.size() == 0) {
                    holder.btnGallery.setImageAlpha(alpha2);
                    clickImage = false;
                }
                if (1 == 2) {
                    if (clickImage) {
                        holder.btnGallery.setOnClickListener(view -> {
                            if (fireClick == true) {
                                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                                if (PERSIST_ACTION_IN_PROGRESS.equals("false")) {

                                    context = view.getContext();
                                    String DA_ID = rsIV_AID.get(position);
                                    mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                                    DA_ID = rsIV_ID.get(position);
                                    mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                    rotateAnimation.setRepeatCount(1);
                                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                                    rotateAnimation.setDuration(100);
                                    holder.btnGallery.startAnimation(rotateAnimation);
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
                }
                holder.btnMedia.setOnClickListener(view -> {
                    if (fireClick == true) {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                            context = view.getContext();
                            String DA_ID = rsIV_AID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                            DA_ID = rsIV_ID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                            mPersistenceObjDao.updateData("PERSIST_MULTI_MEDIA_CALLER", "LIST_INVOLVED_VEHICLE");
                            mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                            mPersistenceObjDao.updateData("PERSIST_TEMP_01", "");
                            mPersistenceObjDao.updateData("PERSIST_TEMP_02", "");
                            mPersistenceObjDao.updateData("PERSIST_TEMP_03", holder.tvIV_YEAR.getText().toString());
                            mPersistenceObjDao.updateData("PERSIST_TEMP_04", holder.tvIV_MAKE.getText().toString());
                            mPersistenceObjDao.updateData("PERSIST_TEMP_05", holder.tvIV_MODEL.getText().toString());

                            //   intent = new Intent(context, CameraActivity.class);
                            intent = new Intent(context, MultiMediaMenu.class);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnMedia.startAnimation(rotateAnimation);
                            scheduleDismissIntent();
                            // context.startActivity(intent);
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
                if (1 == 2) {
                    holder.btnCamera.setOnClickListener(view -> {
                        if (fireClick == true) {
                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                                context = view.getContext();
                                String DA_ID = rsIV_AID.get(position);
                                mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                                DA_ID = rsIV_ID.get(position);
                                mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                                intent = new Intent(context, CameraActivity.class);
                                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                rotateAnimation.setRepeatCount(1);
                                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                                rotateAnimation.setDuration(100);
                                holder.btnCamera.startAnimation(rotateAnimation);
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
                holder.btnPassenger.setOnClickListener(view -> {
                    if (fireClick == true) {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                            mPersistenceObjDao.updateData("PERSIST_IP_MODE", "SELECT_PROFILE");
                            mPersistenceObjDao.updateData("PERSIST_IP_CALLER", "LIST_INVOLVED_VEHICLE");
                            String DA_ID = rsIV_ID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                            context = view.getContext();
                            intent = new Intent(context, ListInvolvedParty.class);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnPassenger.startAnimation(rotateAnimation);
                            pos = position;
                            scheduleDismissIntent();
                        }
                     }
                    fireClick = true;
                    holder.btnPassenger.setImageAlpha(alpha1);
                });
                holder.btnPassenger.setOnLongClickListener(view -> {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                        holder.btnPassenger.setImageAlpha(alpha2);
                        context = view.getContext();
                        Resources res = context.getResources();
                        message = res.getString(R.string.tv0156);
                        makeToast();
                        fireClick = false;
                    }
                    return false;

                });
                holder.ll01.setOnClickListener(view -> fireClick = false);
                holder.rl02.setOnClickListener(view -> fireClick = false);
                holder.ll03.setOnClickListener(view -> fireClick = false);

                holder.ll05.setOnClickListener(view -> fireClick = false);
                holder.ll11.setOnClickListener(view -> fireClick = false);
                holder.ll12.setOnClickListener(view -> fireClick = false);
                holder.ll13.setOnClickListener(view -> fireClick = false);
                holder.ll14.setOnClickListener(view -> fireClick = false);
                holder.ll15.setOnClickListener(view -> fireClick = false);
                holder.ll16.setOnClickListener(view -> fireClick = false);
                holder.ll17.setOnClickListener(view -> fireClick = false);
            } else {
                // DA_HOLDER = LIST_INVOLVED_PARTY
              //  holder.btnGallery.setVisibility(View.INVISIBLE);
                holder.btnDelete2.show();
                holder.btnDelete2.setImageAlpha(alpha2);
                holder.btnMedia.hide();
              //  holder.btnCamera.hide();
              //  holder.btnNote.hide();
                holder.btnPolicy.hide();
                holder.hasPolicy.setVisibility(View.INVISIBLE);
                holder.btnPassenger.hide();
              //  holder.ll05.setVisibility(View.GONE);
            }
        } else {
          //  holder.btnGallery.hide();
            holder.btnMedia.hide();
         //   holder.btnCamera.hide();
          //  holder.btnNote.hide();
            holder.btnPolicy.hide();
            holder.hasPolicy.setVisibility(View.INVISIBLE);
            holder.btnPassenger.hide();
            holder.ll05.setVisibility(View.GONE);
        }
        if (DA_CALLER.equals("LIST_INVOLVED_PARTY") && personinvehicleBool) {
            personinvehicleBool = false;
            holder.btnDelete2.setImageAlpha(alpha1);
            holder.btnDelete2.setOnClickListener(view -> {
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                        PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                        if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                            holder.btnDelete2.setImageAlpha(alpha2);
                            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setRepeatCount(1);
                            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                            rotateAnimation.setDuration(100);
                            holder.btnDelete2.startAnimation(rotateAnimation);
                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
                            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                            if (isNumber(DA_ID_STR)) {
                                VMIP_ID = Integer.parseInt(DA_ID_STR);
                            } else {
                                VMIP_ID = 0;
                            }

                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                            if (isNumber(DA_ID_STR)) {
                                VMAID_ID = Integer.parseInt(DA_ID_STR);
                            } else {
                                VMAID_ID = 0;
                            }

                            DA_ID = rsIV_ID.get(position);
                            if (isNumber(DA_ID)) {
                                VMIV_ID = Integer.parseInt(DA_ID);
                            } else {
                                VMIV_ID = 0;
                            }

                            mVehicleManifestDao.deleteVMIP_ID(VMIP_ID);
                            mVehicleManifestDao.deleteVMIV_IDWZ(VMIV_ID);
                            intent = new Intent(context, ListInvolvedVehicle.class);
                            ActionInProgress = true;
                            scheduleDismissIntent();
                        }
            });
        }
        rowView.setOnClickListener(view -> {
            if (fireClick == true) {
                if (ActionInProgress == false) {
                    persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                    PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                    if (PERSIST_ACTION_IN_PROGRESS.equals("false")) {
                        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setRepeatCount(1);
                        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                        rotateAnimation.setDuration(100);
                        rowView.startAnimation(rotateAnimation);
                        pos = position;
                        if (DA_CALLER.equals("LIST_INVOLVED_PARTY")) {
                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
                            DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                            if (isNumber(DA_ID)) {
                                VMIP_ID = Integer.parseInt(DA_ID);
                            } else {
                                VMIP_ID = 0;
                            }

                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                            DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                            if (isNumber(DA_ID)) {
                                VMAID_ID = Integer.parseInt(DA_ID);
                            } else {
                                VMAID_ID = 0;
                            }

                            DA_ID = rsIV_ID.get(position);
                            if (isNumber(DA_ID)) {
                                VMIV_ID = Integer.parseInt(DA_ID);
                            } else {
                                VMIV_ID = 0;
                            }
                            mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);


                            // mVehicleManifestDao.deleteVMIP_IDWZ(VMIP_ID);
                            mVehicleManifestDao.deleteVMIP_ID(VMIP_ID);
                            mVehicleManifestDao.deleteVMIV_IDWZ(VMIV_ID);
                            AddData(VMAID_ID, VMIV_ID, VMIP_ID);
                            intent = new Intent(context, ListInvolvedVehicle.class);
                            ActionInProgress = true;
                            scheduleDismissIntent();
                        }
                        if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {
                            String DA_ID = rsIV_AID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                            DA_ID = rsIV_ID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                            mPersistenceObjDao.updateData("PERSIST_IP_ID", "0");
                            mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_INVOLVED_VEHICLE");
                        }
                        if (DA_CALLER.equals("LIST_INSURED_VEHICLES")) {
                            context = view.getContext();
                            mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "LIST_INVOLVED_MENU");
                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                            String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                            if (isNumber(DA_ID)) {
                                IPV_AID = Integer.parseInt(DA_ID);
                            } else {
                                IPV_AID = 0;
                            }

                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_ID");
                            DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                            if (isNumber(DA_ID)) {
                                IPV_POID = Integer.parseInt(DA_ID);
                            } else {
                                IPV_POID = 0;
                            }

                            DA_ID_STR = rsIV_ID.get(position);
                            if (isNumber(DA_ID_STR)) {
                                IPV_IVID = Integer.parseInt(DA_ID_STR);
                            } else {
                                IPV_IVID = 0;
                            }

                            String IPV_V1 = "";
                            String IPV_V2 = "";
                            String IPV_V3 = "";
                            String IPV_V4 = "";
                            String IPV_V5 = "";
                            String IPV_V6 = "";
                            String IPV_V7 = "";
                            String IPV_V8 = "";
                            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_HOLDER");
                            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                            if (isNumber(DA_ID_STR)) {
                                CURRENT_IPO_HOLDER = Integer.parseInt(DA_ID_STR);
                            } else {
                                CURRENT_IPO_HOLDER = 0;
                            }

                            IPV_HOLDER = CURRENT_IPO_HOLDER;
                            mInsurancePolicyVDao.addData(IPV_AID, IPV_POID,
                                    IPV_IVID, IPV_V1, IPV_V2, IPV_V3,
                                    IPV_V4, IPV_V5, IPV_V6, IPV_V7, IPV_V8, IPV_HOLDER);

                        }

                        if (!DA_CALLER.equals("SELECT_NOTE_ATTACHMENT") && !DA_CALLER.equals("LIST_INSURED_VEHICLES")) {
                            context = view.getContext();
                            String DA_ID = rsIV_AID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_AID_ID", DA_ID);
                            DA_ID = rsIV_ID.get(position);
                            mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);
                        }
                        if (ActionInProgress == false) {
                            scheduleDoListItem();
                        }
                    }
                }
            }
            fireClick = true;

        });
        return rowView;
    }
    private void AddData(Integer VMAID_ID, Integer VMIV_ID, Integer VMIP_ID) {
        long insertData = mVehicleManifestDao.addData(VMAID_ID, VMIV_ID, VMIP_ID);
        // Add Test Comment
        // Add Second Comment

    }
    private void scheduleDoListItem() {
        Handler handler = new Handler();
        handler.postDelayed(this::doListItem, 200);
    }
    private void doListItem() {
        if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {
            intent = new Intent(context, AddNotes.class);
            dismissIntent();
           // context.startActivity(intent);
        }
        if (DA_CALLER.equals("LIST_INSURED_VEHICLES")) {

            intent = new Intent(context, ListVehicleCoverage.class);
            dismissIntent();
            //context.startActivity(intent);
        }

        if (!DA_CALLER.equals("SELECT_NOTE_ATTACHMENT") && !DA_CALLER.equals("LIST_INSURED_VEHICLES")) {
            intent = new Intent(context, UpdateInvolvedVehicle.class);
            dismissIntent();
           // context.startActivity(intent);
        }


    }

    private void scheduleDismiss() {
        Handler handler = new Handler();
        //hud.dismiss();
        newHolder.setImageAlpha(alpha2);
        handler.postDelayed(this::xHolder, 2000);
    }

    private void xHolder() {

        newHolder.setImageAlpha(alpha1);
    }

    private void makeToast() {
        int duration = 20;
        int type = 0;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);

        mdToast.show();

    }

    public class Holder {
        LinearLayout ll01, ll03, ll04, ll05, ll10, ll11, ll12, ll13, ll14, ll15, ll16, ll17, ll18, ll19, ll20;
        RelativeLayout rl02;
        FloatingActionButton btnGallery;
        FloatingActionButton btnMedia;
        FloatingActionButton btnCamera;
        FloatingActionButton btnPassenger;
        FloatingActionButton btnDelete2;
        ImageView iv004;
        ImageView iv005;
        ImageView iv006;
        ImageView iv007;
        ImageView iv008;
        ImageView iv009;
        ImageView iv010;
        ImageView iv011;
        ImageView iv012;
        ImageView iv013;
        ImageView iv014;
        ImageView iv015;
        ImageView iv016;
        ImageView iv017;
        ImageView iv018;
        ImageView iv019;
        ImageView iv020;
        ImageView iv021;
        ImageView iv022;
        ImageView iv023;
        ImageView iv024;
        ImageView iv025;
        ImageView iv026;
        ImageView iv027;
        ImageView iv028;
        ImageView iv029;
        ImageView iv030;
        ImageView iv031;
        ImageView iv032;
        ImageView iv033;
        ImageView iv034;
        ImageView iv035;
        ImageView iv036;
        ImageView iv037;
        ImageView iv038;
        ImageView iv039;
        ImageView iv040;
        ImageView iv041;
        ImageView iv042;
        ImageView iv043;
        ImageView iv044;
        ImageView iv045;
        ImageView iv046;
        ImageView iv047;
        ImageView iv048;
        ImageView iv049;
        ImageView iv050;
        ImageView iv051;
        ImageView iv052;
        ImageView iv053;
        ImageView iv054;
        ImageView iv055;
        ImageView iv056;
        ImageView iv057;
        ImageView iv058;
        ImageView iv059;
        ImageView iv060;
        ImageView iv061;
        ImageView iv062;
        ImageView iv063;
        ImageView iv064;
        ImageView iv065;
        ImageView iv066;
        ImageView iv067;
        ImageView iv068;
        ImageView iv069;
        ImageView iv070;
        ImageView iv071;
        ImageView iv072;
        ImageView iv073;
        ImageView iv074;
        ImageView iv075;
        ImageView iv076;
        ImageView iv077;
        ImageView iv078;
        ImageView iv079;
        ImageView iv080;
        ImageView iv081;
        ImageView iv082;
        ImageView iv083;
        ImageView iv084;
        ImageView iv085;
        ImageView iv086;
        ImageView iv087;
        ImageView iv088;
        ImageView iv089;
        ImageView iv090;
        ImageView iv091;
        ImageView iv092;
        ImageView iv093;
        ImageView iv094;
        ImageView iv095;
        ImageView iv096;
        ImageView iv097;
        ImageView iv098;
        ImageView iv099;
        ImageView iv100;
        FloatingActionButton btnNote;
        TextView tvIV_ID;
        TextView tvAID_ID;
        TextView tvIV_YEAR; 
        TextView tvIV_MAKE; 
        TextView tvIV_MODEL; 
        FloatingActionButton btnPolicy;
        TextView hasPolicy;

    }
    public void doClose() {
        mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        mAccidentNoteDao.closeAll();
        mInvolvedPartyDao.closeAll();
        //mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
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

}
