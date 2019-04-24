package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.InsurancePolicyDao;
import com.auto.accident.report.models.InsurancePolicyPDao;
import com.auto.accident.report.models.InsurancePolicyVDao;
import com.auto.accident.report.models.InvolvedPartyDao;
import com.auto.accident.report.models.InvolvedVehicleDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.InsurancePolicy;
import com.auto.accident.report.objects.InsurancePolicyP;
import com.auto.accident.report.objects.InsurancePolicyV;
import com.auto.accident.report.objects.InvolvedParty;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 6/7/2018.
 */

class ListInsurancePolicyAdapter extends BaseAdapter {
    private static final String REQ_CODE_INVOLVED_PARTY = "LIST_INVOLVED_PARTY";
    private static final String REQ_CODE_INVOLVED_VEHICLE = "LIST_INVOLVED_VEHICLE";
    private static LayoutInflater inflater = null;
    //   String [] result1;
    private Context context;
    private PersistenceObjDao mPersistenceObjDao;
    private final ArrayList<String> rsIPO_ID = new ArrayList<>();
    private final ArrayList<String> rsIPO_AID = new ArrayList<>();
    private final ArrayList<String> rsIPO_HOLDER = new ArrayList<>();
    private final ArrayList<String> rsPOLICY_HOLDER = new ArrayList<>();
    private final ArrayList<String> rsXX_CNAM = new ArrayList<>();
    private final ArrayList<String> rsXX_PNUM = new ArrayList<>();
    private int alpha = 50;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Boolean clickImage;
    private Boolean fireClick = true;
    private String message;
    private int CURRENT_AID;
    int CURRENT_IP_ID;
    private int CURRENT_IPO_ID;
    private String DA_CALLER;
    private List<InsurancePolicyV> InsurancePolicyVList = new ArrayList<>();
    private List<InsurancePolicy> insurancePolicyList;
    private InsurancePolicyDao mInsurancePolicyDao;
    private InsurancePolicyPDao mInsurancePolicyPDao;
    private InvolvedVehicleDao mInvolvedVehicleDao;
    private PersistenceObj persistenceObj;
    private InvolvedPartyDao mInvolvedPartyDao;
    private InsurancePolicyVDao mInsurancePolicyVDao;
    private InsurancePolicyV insurancePolicyV;
    private InvolvedParty involvedParty;
    private Intent intent;
    private RotateAnimation rotateAnimation;
    private int pos;
    private int CURRENT_IV_ID;
    private int CURRENT_IPO_HOLDER;
    private int IPV_AID;
    private int IPV_IVID;
    private int IPV_POID;
    private int IPV_HOLDER;
    private String DA_ID;
    private String DA_ID_STR;
    private String PERSIST_ACTION_IN_PROGRESS;

    public ListInsurancePolicyAdapter(ListInsurancePolicy ListInsurancePolicy) {
        // TODO Auto-generated constructor stub
        context = ListInsurancePolicy;
        mInsurancePolicyVDao = new InsurancePolicyVDao(context);
        mInsurancePolicyPDao = new InsurancePolicyPDao(context);
        mInvolvedVehicleDao = new InvolvedVehicleDao(context);
        mInvolvedPartyDao = new InvolvedPartyDao(context);
        mPersistenceObjDao = new PersistenceObjDao(context);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            CURRENT_AID = Integer.parseInt(DA_ID_STR);
        } else {
            CURRENT_AID = 0;
        }


        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            CURRENT_IV_ID = Integer.parseInt(DA_ID_STR);
        } else {
            CURRENT_IV_ID = 0;
        }





        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_LIST_INSURANCE_POLICY_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        mInsurancePolicyDao = new InsurancePolicyDao(context);
        switch (DA_CALLER) {
            case "LIST_INVOLVED_VEHICLES": {
                insurancePolicyList = mInsurancePolicyDao.getAccidentInsurancePolicys(CURRENT_AID);

                for (InsurancePolicy insurancePolicy : insurancePolicyList) {

                    rsIPO_ID.add(Integer.toString(insurancePolicy.getIPO_ID()));
                    rsIPO_AID.add(Integer.toString(CURRENT_AID));
                    rsIPO_HOLDER.add(Integer.toString(insurancePolicy.getIPO_HOLDER()));
                    int IPO_HOLDER = insurancePolicy.getIPO_HOLDER();
                    rsXX_CNAM.add(insurancePolicy.getIPO_CNAM());
                    rsXX_PNUM.add(insurancePolicy.getIPO_PNUM());
                    involvedParty = mInvolvedPartyDao.getInvolvedParty(IPO_HOLDER, CURRENT_AID);

                    rsPOLICY_HOLDER.add(involvedParty.getIP_FNAME());

                    rsXX_CNAM.add(insurancePolicy.getIPO_CNAM());
                    rsXX_PNUM.add(insurancePolicy.getIPO_PNUM());

                }

                break;
            }
            case "LIST_INSURED_VEHICLES": {
                insurancePolicyList = mInsurancePolicyDao.getAccidentInsurancePolicys(CURRENT_AID);

                for (InsurancePolicy insurancePolicy : insurancePolicyList) {

                    rsIPO_ID.add(Integer.toString(insurancePolicy.getIPO_ID()));
                    rsIPO_AID.add(Integer.toString(CURRENT_AID));
                    rsIPO_HOLDER.add(Integer.toString(insurancePolicy.getIPO_HOLDER()));
                    int IPO_HOLDER = insurancePolicy.getIPO_HOLDER();
                    rsXX_CNAM.add(insurancePolicy.getIPO_CNAM());
                    rsXX_PNUM.add(insurancePolicy.getIPO_PNUM());
                    involvedParty = mInvolvedPartyDao.getInvolvedParty(IPO_HOLDER, CURRENT_AID);

                    rsPOLICY_HOLDER.add(involvedParty.getIP_FNAME());

                    rsXX_CNAM.add(insurancePolicy.getIPO_CNAM());
                    rsXX_PNUM.add(insurancePolicy.getIPO_PNUM());

                }

                break;
            }
            default: {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_HOLDER");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    CURRENT_IPO_HOLDER = Integer.parseInt(DA_ID_STR);
                } else {
                    CURRENT_IPO_HOLDER = 0;
                }


                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    CURRENT_IP_ID = Integer.parseInt(DA_ID_STR);
                } else {
                    CURRENT_IP_ID = 0;
                }

                involvedParty = mInvolvedPartyDao.getInvolvedParty(CURRENT_IPO_HOLDER, CURRENT_AID);
                String IP_FNAME = involvedParty.getIP_FNAME();
                insurancePolicyList = mInsurancePolicyDao.getHolderInsurancePolicys(CURRENT_AID, CURRENT_IPO_HOLDER);

                for (InsurancePolicy insurancePolicy : insurancePolicyList) {

                    rsIPO_ID.add(Integer.toString(insurancePolicy.getIPO_ID()));
                    rsIPO_AID.add(Integer.toString(CURRENT_AID));
                    rsIPO_HOLDER.add(Integer.toString(CURRENT_IPO_HOLDER));
                    rsPOLICY_HOLDER.add(IP_FNAME);
                    rsXX_CNAM.add(insurancePolicy.getIPO_CNAM());
                    rsXX_PNUM.add(insurancePolicy.getIPO_PNUM());

                }
                break;
            }

        }


        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsIPO_ID.size();

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
        rowView = inflater.inflate(R.layout.list_insurance_policy_adapter, null);

        holder.btnCoveredVehicles = rowView.findViewById(R.id.btnCoveredVehicles);
        holder.btnCoveredPeople = rowView.findViewById(R.id.btnCoveredPeople);
        holder.tvIPO_ID = rowView.findViewById(R.id.tvIPO_ID);
        holder.tvIPO_AID = rowView.findViewById(R.id.tvIPO_AID);
        holder.tvIPO_HOLDER = rowView.findViewById(R.id.tvIPO_HOLDER);
        holder.tvPOLICY_HOLDER = rowView.findViewById(R.id.tvPOLICY_HOLDER);
        holder.tvXX_CNAM = rowView.findViewById(R.id.tvXX_CNAM);
        holder.tvXX_PNUM = rowView.findViewById(R.id.tvXX_PNUM);
        holder.ll01 = rowView.findViewById(R.id.ll01);

        holder.tvIPO_ID.setText(rsIPO_ID.get(position));
        holder.tvIPO_AID.setText(rsIPO_AID.get(position));
        holder.tvIPO_HOLDER.setText(rsIPO_HOLDER.get(position));

        holder.tvPOLICY_HOLDER.setText(rsPOLICY_HOLDER.get(position));
        holder.tvXX_CNAM.setText(rsXX_CNAM.get(position));
        holder.tvXX_PNUM.setText(rsXX_PNUM.get(position));
        switch (DA_CALLER) {
            case "LIST_INSURED_VEHICLES":
                holder.btnCoveredVehicles.setVisibility(INVISIBLE);
                holder.btnCoveredPeople.setVisibility(INVISIBLE);
            default:
        }



        List<InsurancePolicyP> insurancePolicyPList = mInsurancePolicyPDao.getInsurancePolicyPs(CURRENT_AID, CURRENT_IP_ID);

        //   InsurancePolicyVList = mInsurancePolicyVDao.getInsurancePolicyV(CURRENT_AID, CURRENT_IV_ID);
        holder.btnCoveredPeople.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                    context = view.getContext();
                    String DA_ID = rsIPO_ID.get(position);
                    mPersistenceObjDao.updateData("PERSIST_IPO_ID", DA_ID);
                    intent = new Intent(context, ListInsuredPeople.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    holder.btnCoveredPeople.startAnimation(rotateAnimation);
                    pos = position;
                    scheduleDismissIntent();
                }
            }
            holder.btnCoveredPeople.setImageAlpha(alpha1);
            fireClick = true;
        });
        holder.btnCoveredPeople.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                context = view.getContext();
                Resources res = context.getResources();
                holder.btnCoveredPeople.setImageAlpha(alpha2);
                message = res.getString(R.string.insured_people);
                makeToast();
                fireClick = false;
            }
            return false;
        });


        holder.btnCoveredVehicles.setOnClickListener(view -> {
            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                    context = view.getContext();
                    String DA_ID = rsIPO_ID.get(position);
                    mPersistenceObjDao.updateData("PERSIST_IPO_ID", DA_ID);
                    intent = new Intent(context, ListVehicleCoverage.class);
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    holder.btnCoveredVehicles.startAnimation(rotateAnimation);
                    scheduleDismissIntent();
                }
             }
            holder.btnCoveredVehicles.setImageAlpha(alpha1);
            fireClick = true;
        });
        holder.btnCoveredVehicles.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                context = view.getContext();
                Resources res = context.getResources();
                holder.btnCoveredVehicles.setImageAlpha(alpha2);
                message = res.getString(R.string.insured_vehicles);
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
                switch (DA_CALLER) {
                    case "LIST_INSURED_VEHICLES": {
                        mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "");
                        mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "LIST_INVOLVED_MENU");
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID_STR)) {
                            IPV_AID = Integer.parseInt(DA_ID);
                        } else {
                            IPV_AID = 0;
                        }

                        DA_ID_STR = rsIPO_ID.get(position);
                        if (isNumber(DA_ID_STR)) {
                            IPV_POID = Integer.parseInt(DA_ID_STR);
                        } else {
                            IPV_POID = 0;
                        }
                        DA_ID_STR = rsIPO_HOLDER.get(position);
                        if (isNumber(DA_ID_STR)) {
                            IPV_HOLDER = Integer.parseInt(DA_ID_STR);
                        } else {
                            IPV_HOLDER = 0;
                        }

                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            IPV_IVID = Integer.parseInt(DA_ID);
                        } else {
                            IPV_IVID = 0;
                        }

                        insurancePolicyV = mInsurancePolicyVDao.getInsuredVehicle(IPV_AID, IPV_POID, IPV_IVID);
                        if (insurancePolicyV == null) {
                            String IPV_V1 = "";
                            String IPV_V2 = "";
                            String IPV_V3 = "";
                            String IPV_V4 = "";
                            String IPV_V5 = "";
                            String IPV_V6 = "";
                            String IPV_V7 = "";
                            String IPV_V8 = "";


                            mInsurancePolicyVDao.addData(IPV_AID, IPV_POID,
                                    IPV_IVID, IPV_V1, IPV_V2, IPV_V3,
                                    IPV_V4, IPV_V5, IPV_V6, IPV_V7, IPV_V8, IPV_HOLDER);
                        }

                        break;
                    }
                    case "LIST_INVOLVED_VEHICLES": {
                        mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "");
                        mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "LIST_INVOLVED_MENU");
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            IPV_AID = Integer.parseInt(DA_ID);
                        } else {
                            IPV_AID = 0;
                        }
                        DA_ID_STR = rsIPO_ID.get(position);
                        if (isNumber(DA_ID_STR)) {
                            IPV_POID = Integer.parseInt(DA_ID_STR);
                        } else {
                            IPV_POID = 0;
                        }
                        DA_ID_STR = rsIPO_HOLDER.get(position);
                        if (isNumber(DA_ID_STR)) {
                            IPV_HOLDER = Integer.parseInt(DA_ID_STR);
                        } else {
                            IPV_HOLDER = 0;
                        }

                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            IPV_IVID = Integer.parseInt(DA_ID);
                        } else {
                            IPV_IVID = 0;
                        }

                        insurancePolicyV = mInsurancePolicyVDao.getInsuredVehicle(IPV_AID, IPV_POID, IPV_IVID);
                        if (insurancePolicyV == null) {
                            String IPV_V1 = "";
                            String IPV_V2 = "";
                            String IPV_V3 = "";
                            String IPV_V4 = "";
                            String IPV_V5 = "";
                            String IPV_V6 = "";
                            String IPV_V7 = "";
                            String IPV_V8 = "";


                            mInsurancePolicyVDao.addData(IPV_AID, IPV_POID,
                                    IPV_IVID, IPV_V1, IPV_V2, IPV_V3,
                                    IPV_V4, IPV_V5, IPV_V6, IPV_V7, IPV_V8, IPV_HOLDER);
                        }

                        break;
                    }
                    case "LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY_LIST_INVOLVED_PARTY": {
                        mPersistenceObjDao.updateData("PERSIST_LIST_INSURANCE_POLICY_CALLER", "");
                        mPersistenceObjDao.updateData("PERSIST_IV_CALLER", "LIST_INVOLVED_MENU");
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            IPV_AID = Integer.parseInt(DA_ID);
                        } else {
                            IPV_AID = 0;
                        }
                        DA_ID_STR = rsIPO_ID.get(position);
                        if (isNumber(DA_ID_STR)) {
                            IPV_POID = Integer.parseInt(DA_ID_STR);
                        } else {
                            IPV_POID = 0;
                        }
                        DA_ID_STR = rsIPO_HOLDER.get(position);
                        if (isNumber(DA_ID_STR)) {
                            IPV_HOLDER = Integer.parseInt(DA_ID_STR);
                        } else {
                            IPV_HOLDER = 0;
                        }
                        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
                        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
                        if (isNumber(DA_ID)) {
                            IPV_IVID = Integer.parseInt(DA_ID);
                        } else {
                            IPV_IVID = 0;
                        }
                        insurancePolicyV = mInsurancePolicyVDao.getInsuredVehicle(IPV_AID, IPV_POID, IPV_IVID);
                        if (insurancePolicyV == null) {
                            String IPV_V1 = "";
                            String IPV_V2 = "";
                            String IPV_V3 = "";
                            String IPV_V4 = "";
                            String IPV_V5 = "";
                            String IPV_V6 = "";
                            String IPV_V7 = "";
                            String IPV_V8 = "";


                            mInsurancePolicyVDao.addData(IPV_AID, IPV_POID,
                                    IPV_IVID, IPV_V1, IPV_V2, IPV_V3,
                                    IPV_V4, IPV_V5, IPV_V6, IPV_V7, IPV_V8, IPV_HOLDER);
                        }

                        break;
                    }
                    default: {
                        String DA_ID = rsIPO_ID.get(position);
                        mPersistenceObjDao.updateData("PERSIST_IPO_ID", DA_ID);

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

        Handler handler = new Handler();
        handler.postDelayed(this::doListItem, 200);
    }
    private void doListItem() {
        doClose();
        switch (DA_CALLER) {
            case "LIST_INSURED_VEHICLES": {

                Intent intent = new Intent(context, ListVehicleCoverage.class);
                context.startActivity(intent);
                break;
            }
            case "LIST_INVOLVED_VEHICLES": {

                Intent intent = new Intent(context, ListVehicleCoverage.class);
                context.startActivity(intent);
                break;
            }
            case "LIST_INVOLVED_VEHICLE_LIST_INSURANCE_POLICY_LIST_INVOLVED_PARTY": {

                Intent intent = new Intent(context, ListVehicleCoverage.class);
                context.startActivity(intent);
                break;
            }
            default: {
                Intent intent = new Intent(context, UpdateInsurancePolicy.class);
                context.startActivity(intent);
            }
        }


    }
    private void makeToast() {
        int duration = 20;
        int type = 0;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);

        mdToast.show();

    }

    public class Holder {
        LinearLayout ll01;
        ImageView btnCoveredVehicles;
        ImageView btnCoveredPeople;
        //  TextView hasPolicy;
        TextView tvIPO_ID;
        TextView tvIPO_AID;
        TextView tvIPO_HOLDER;
        TextView tvPOLICY_HOLDER;
        TextView tvXX_CNAM;
        TextView tvXX_PNUM;


    }
    public void doClose() {
        mInsurancePolicyVDao.closeAll();
       // mDeviceImageStoreDao.closeAll();
      //  mPartyTypeDao.closeAll();
      //  mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
       // mAccidentNoteDao.closeAll();
        mInvolvedPartyDao.closeAll();
        //mDeviceUserDao.closeAll();
        mInsurancePolicyPDao.closeAll();
        mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        mInsurancePolicyDao.closeAll();
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

