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
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.models.InsurancePolicyDao;
import com.auto.accident.report.models.InsurancePolicyVDao;
import com.auto.accident.report.models.InvolvedPartyDao;
import com.auto.accident.report.models.InvolvedVehicleDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.InsurancePolicy;
import com.auto.accident.report.objects.InsurancePolicyV;
import com.auto.accident.report.objects.InvolvedParty;
import com.auto.accident.report.objects.InvolvedVehicle;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 6/9/2018.
 */

class ListVehicleCoverageAdapter extends BaseAdapter {
    private static final String REQ_CODE_INVOLVED_PARTY = "LIST_INVOLVED_PARTY";
    private static LayoutInflater inflater = null;
    //   String [] result1;

    private PersistenceObjDao mPersistenceObjDao;
    private InsurancePolicyVDao mInsurancePolicyVDao;
    private InsurancePolicyDao mInsurancePolicyDao;

    private final ArrayList<Integer> rsIPV_ID = new ArrayList<>();
    private final ArrayList<String> rsIPV_AID = new ArrayList<>();
    private final ArrayList<String> rsIPV_HOLDER = new ArrayList<>();
    private final ArrayList<String> rsPOLICY_HOLDER = new ArrayList<>();
    private final ArrayList<String> rsXX_CNAM = new ArrayList<>();
    private final ArrayList<String> rsXX_PNUM = new ArrayList<>();
    private final ArrayList<String> rsGENERIC_VALUE01 = new ArrayList<>();
    private int alpha = 50;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Boolean clickImage;
    private Boolean fireClick = true;
    private String message;
    private int pos;
    private final int CURRENT_AID;
    private int CURRENT_IP_ID;
    private int CURRENT_IPV_ID;
    private String DA_CALLER;
    private  InsurancePolicy insurancePolicy;
    private PersistenceObj persistenceObj;
    private InvolvedVehicleDao mInvolvedVehicleDao;
    private List<InsurancePolicyV> InsurancePolicyVList = new ArrayList<>();
    private InvolvedVehicle involvedVehicle;
    private InvolvedParty involvedParty;
    private Intent intent;
    private String DA_HORN;
    private  Context context;
    private int CURRENT_IV_ID;
    private String DA_ID_STR;
    private int CURRENT_IPO_HOLDER;
    private int CURRENT_IPO_ID;
    private RotateAnimation rotateAnimation;
    private String PERSIST_ACTION_IN_PROGRESS;
    public ListVehicleCoverageAdapter(ListVehicleCoverage ListVehicleCoverage) {
        // TODO Auto-generated constructor stub
        context = ListVehicleCoverage;


        mPersistenceObjDao = new PersistenceObjDao(context);
        persistenceObj  = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
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
        //     mPersistenceObjDao.updateData("PERSIST_INSURANCE_POLICY_VEHICLE_CALLER", "LIST_INVOLVED_VEHICLE");
        //     PERSIST_INSURANCE_POLICY_VEHICLE_CALLER
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_INSURANCE_POLICY_VEHICLE_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        mInsurancePolicyDao = new InsurancePolicyDao(context);

        mInvolvedVehicleDao = new InvolvedVehicleDao(context);

        switch (DA_CALLER) {
            case "LIST_INVOLVED_VEHICLE":
                mPersistenceObjDao.updateData("PERSIST_INSURANCE_POLICY_CALLER", "LIST_INSURED_VEHICLES");
                mInsurancePolicyVDao = new InsurancePolicyVDao(context);
                InsurancePolicyVList = mInsurancePolicyVDao.getInsuranceAllPolicyVs2(CURRENT_AID, CURRENT_IV_ID);
                for (InsurancePolicyV insurancePolicyV : InsurancePolicyVList) {
                // TODO INSURANCE POLICY
                    // IPV_POID -> IPOID
                    int IPV_POID = insurancePolicyV.getIPV_POID();

                    insurancePolicy = mInsurancePolicyDao.getInsurancePolicy(IPV_POID, CURRENT_AID);
                    rsIPV_HOLDER.add(Integer.toString(insurancePolicy.getIPO_HOLDER()));
                    int IPO_HOLDER = insurancePolicy.getIPO_HOLDER();
                    rsXX_CNAM.add(insurancePolicy.getIPO_CNAM());
                    rsXX_PNUM.add(insurancePolicy.getIPO_PNUM());

                    InvolvedPartyDao mInvolvedPartyDao = new InvolvedPartyDao(context);

                    involvedParty = mInvolvedPartyDao.getInvolvedParty(IPO_HOLDER, CURRENT_AID);
                    String IP_FNAME = involvedParty.getIP_FNAME();
                    rsIPV_ID.add(insurancePolicyV.getIPV_ID());
                    rsIPV_AID.add(Integer.toString(CURRENT_AID));
                    rsIPV_HOLDER.add(Integer.toString(IPV_POID));
                    rsPOLICY_HOLDER.add(IP_FNAME);
                    int IPV_IVID = insurancePolicyV.getIPV_IVID();
                    ArrayList<Integer> rsIPV_IVID = new ArrayList<>();
                    rsIPV_IVID.add(IPV_IVID);
                    involvedVehicle = mInvolvedVehicleDao.getInvolvedVehicle(IPV_IVID, CURRENT_AID);
                    rsGENERIC_VALUE01.add(involvedVehicle.getIV_YEAR() + ' ' + involvedVehicle.getIV_MAKE() + ' ' + involvedVehicle.getIV_MODEL());

                }
                break;
            default:
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    CURRENT_IP_ID = Integer.parseInt(DA_ID_STR);
                } else {
                    CURRENT_IP_ID = 0;
                }
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_HOLDER");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    CURRENT_IPO_HOLDER = Integer.parseInt(DA_ID_STR);
                } else {
                    CURRENT_IPO_HOLDER = 0;
                }


                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IPO_ID");
                DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
                if (isNumber(DA_ID_STR)) {
                    CURRENT_IPO_ID = Integer.parseInt(DA_ID_STR);
                } else {
                    CURRENT_IPO_ID = 0;
                }

                InvolvedPartyDao mInvolvedPartyDao = new InvolvedPartyDao(context);

                involvedParty = mInvolvedPartyDao.getInvolvedParty(CURRENT_IP_ID, CURRENT_AID);
                String IP_FNAME = involvedParty.getIP_FNAME();
                mInsurancePolicyVDao = new InsurancePolicyVDao(context);
                InsurancePolicyVList = mInsurancePolicyVDao.getInsurancePolicyVs(CURRENT_AID, CURRENT_IPO_ID);
                for (InsurancePolicyV insurancePolicyV : InsurancePolicyVList) {

                    rsIPV_ID.add(insurancePolicyV.getIPV_ID());
                    rsIPV_AID.add(Integer.toString(CURRENT_AID));
                    rsIPV_HOLDER.add(Integer.toString(CURRENT_IPO_HOLDER));
                    rsPOLICY_HOLDER.add(IP_FNAME);
                    int IPV_IVID = insurancePolicyV.getIPV_IVID();
                    ArrayList<Integer> rsIPV_IVID = new ArrayList<>();
                    rsIPV_IVID.add(IPV_IVID);
                    involvedVehicle = mInvolvedVehicleDao.getInvolvedVehicle(IPV_IVID, CURRENT_AID);
                    rsGENERIC_VALUE01.add(involvedVehicle.getIV_YEAR() + ' ' + involvedVehicle.getIV_MAKE() + ' ' + involvedVehicle.getIV_MODEL());

                    insurancePolicy = mInsurancePolicyDao.getInsurancePolicy(CURRENT_IPO_ID, CURRENT_AID);
                    rsXX_CNAM.add(insurancePolicy.getIPO_CNAM());
                    rsXX_PNUM.add(insurancePolicy.getIPO_PNUM());

                }

                break;
        }


        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsIPV_ID.size();

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
        rowView = inflater.inflate(R.layout.list_insured_people_adapter, null);
        holder.tvXX_GENERIC_VALUE01 = rowView.findViewById(R.id.tvXX_GENERIC_VALUE01);
        holder.entity_insurance_policy_ll04 = rowView.findViewById(R.id.entity_insurance_policy_ll04);
        holder.btnDelete = rowView.findViewById(R.id.btnDelete);
        holder.tvIPO_ID = rowView.findViewById(R.id.tvIPO_ID);
        holder.tvIPO_AID = rowView.findViewById(R.id.tvIPO_AID);
        holder.tvIPO_HOLDER = rowView.findViewById(R.id.tvIPO_HOLDER);
        holder.tvPOLICY_HOLDER = rowView.findViewById(R.id.tvPOLICY_HOLDER);
        holder.tvXX_CNAM = rowView.findViewById(R.id.tvXX_CNAM);
        holder.tvXX_PNUM = rowView.findViewById(R.id.tvXX_PNUM);
        holder.entity_insurance_policy_ll04.setVisibility(VISIBLE);

        holder.tvIPO_AID.setText(rsIPV_AID.get(position));
        holder.tvIPO_HOLDER.setText(rsIPV_HOLDER.get(position));
        holder.tvPOLICY_HOLDER.setText(rsPOLICY_HOLDER.get(position));
        holder.tvXX_CNAM.setText(rsXX_CNAM.get(position));
        holder.tvXX_PNUM.setText(rsXX_PNUM.get(position));
        holder.tvXX_GENERIC_VALUE01.setText(rsGENERIC_VALUE01.get(position));


        mPersistenceObjDao = new PersistenceObjDao(context);
        mInsurancePolicyVDao = new InsurancePolicyVDao(context);
        InsurancePolicyVList = mInsurancePolicyVDao.getInsurancePolicyVs(CURRENT_AID, CURRENT_IP_ID);

        holder.btnDelete.setOnClickListener(view -> {

            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    context = view.getContext();
                    pos = position;
                    DA_HORN = "btnDelete";
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    holder.btnDelete.startAnimation(rotateAnimation);
                    scheduleDoBullhorn();
                }

                           }

            holder.btnDelete.setImageAlpha(alpha1);

            fireClick = true;
        });
        holder.btnDelete.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {


                context = view.getContext();
                Resources res = context.getResources();

                message = res.getString(R.string.remove_from_policy);

                makeToast();
                holder.btnDelete.setImageAlpha(alpha2);
                fireClick = false;
            }
            return false;
        });


        rowView.setOnClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounceme3);
                BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(mBullHornBounceInterpolator);
                rowView.startAnimation(myAnim);
            }
        });
        return rowView;
    }

    private void makeToast() {
        int duration = 20;
        int type = 0;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);

        mdToast.show();

    }

    public class Holder {
        LinearLayout entity_insurance_policy_ll04;
        FloatingActionButton btnDelete;

        //  TextView hasPolicy;
        TextView tvIPO_ID;
        TextView tvIPO_AID;
        TextView tvIPO_HOLDER;
        TextView tvPOLICY_HOLDER;
        TextView tvXX_CNAM;
        TextView tvXX_PNUM;
        TextView tvXX_GENERIC_VALUE01;

    }
    public void doClose() {
        mPersistenceObjDao.closeAll();
     //   mVehicleManifestDao.closeAll();
    }
    private void scheduleDismissIntent() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissIntent, 1250);
    }
    private void dismissIntent() {
        doClose();
        context.startActivity(intent);
    }
    private void scheduleDoBullhorn() {
        Handler handler = new Handler();
        handler.postDelayed(this::doBullhorn, 250);
    }

    private void doBullhorn() {
        switch (DA_HORN) {
            case "btnDelete": {
                CURRENT_IPV_ID = rsIPV_ID.get(pos);
                mInsurancePolicyVDao.deleteIPV_ID(CURRENT_IPV_ID);
                intent = new Intent(context, ListVehicleCoverage.class);
                scheduleDismissIntent();
                break;
            }
        }
    }
}

