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
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;

import java.util.ArrayList;


/**
 * Created by myron on 1/19/2018.
 */

class ListPartyTypeAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    //  String [] result0;
    private ArrayList<String> rsPT_ID = new ArrayList<>();
    private ArrayList<String> rsPT_TYPE = new ArrayList<>();
    private ArrayList<String> rsPT_DESC = new ArrayList<>();
    private PersistenceObjDao mPersistenceObjDao;
    private String message;
    private int duration;
    private int type;
    private Context context;
    private String rsMode;
    private int posx;
    private int DA_ID;
    private PersistenceObj persistenceObj;
    private Intent intent;
    private RotateAnimation rotateAnimation;
    private int pos;
    private String PERSIST_ACTION_IN_PROGRESS;
    public ListPartyTypeAdapter(ListPartyType ListPartyType,
                                ArrayList<String> alPT_ID,
                                ArrayList<String> alPT_TYPE,
                                ArrayList<String> alPT_DESC) {
        // TODO Auto-generated constructor stub

        context = ListPartyType;
        rsPT_ID = alPT_ID;
        rsPT_TYPE = alPT_TYPE;
        rsPT_DESC = alPT_DESC;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsPT_ID.size();

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
        rowView = inflater.inflate(R.layout.list_party_type_adapter, null);
        holder.tv01 = rowView.findViewById(R.id.tvPT_ID);
        holder.tv02 = rowView.findViewById(R.id.tvPT_TYPE);
        holder.tv03 = rowView.findViewById(R.id.tvPT_DESC);
        holder.tv01.setText(rsPT_ID.get(position));
        holder.tv02.setText(rsPT_TYPE.get(position));
        holder.tv03.setText(rsPT_DESC.get(position));

        rowView.setOnClickListener(view -> {
            mPersistenceObjDao = new PersistenceObjDao(context);
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {


                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PT_CALLER");
                rsMode = persistenceObj.getPERSISTENCE_VALUE();
                switch (rsMode) {
                    case "ADD_INVOLVED_PARTY": {
                        String DA_PTYPE = rsPT_TYPE.get(position) + " - " + rsPT_DESC.get(position);
                        mPersistenceObjDao.updateData("PERSIST_PT_TYPE", DA_PTYPE);
                        break;
                    }
                    case "LIST_INVOLVED_PARTY": {

                        String DA_PTYPE = rsPT_TYPE.get(position) + " - " + rsPT_DESC.get(position);
                        mPersistenceObjDao.updateData("PERSIST_PT_TYPE", DA_PTYPE);
                        mPersistenceObjDao.updateData("PERSIST_DU_MODE", "SELECT_PROFILE");
                        mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "LIST_INVOLVED_PARTY");
                        break;
                    }
                    default: {
                        DA_ID = Integer.parseInt(rsPT_ID.get(position));

                    }
                }
                posx = position;
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                rowView.startAnimation(rotateAnimation);
                scheduleDoListItem();
            }
        });
        return rowView;
    }
    private void scheduleDoListItem() {
       
        Handler handler = new Handler();
        handler.postDelayed(this::doListItem, 600);
    }
    private void doListItem() {
		 doClose();
        switch (rsMode) {
            case "ADD_INVOLVED_PARTY": {
                Intent intent = new Intent(context, AddInvolvedParty.class);
                context.startActivity(intent);
                break;
            }
            case "LIST_INVOLVED_PARTY": {
                Intent intent = new Intent(context, ListDeviceUser.class);
                context.startActivity(intent);
                break;
            }
            default: {

                if (DA_ID > 17) {
                    Intent intent = new Intent(context, UpdatePartyType.class);
                    intent.putExtra("PT_ID", rsPT_ID.get(posx));
                    intent.putExtra("PT_TYPE", rsPT_TYPE.get(posx));
                    intent.putExtra("PT_DESC", rsPT_DESC.get(posx));
                    context.startActivity(intent);
                } else {
                    Resources res = context.getResources();
                    message = res.getString(R.string.default_types);
                    duration = 20;
                    type = 0;
                    MDToast mdToast = MDToast.makeText(context, message, duration, type);
                    mdToast.setGravity(Gravity.TOP, 50, 200);
                    mdToast.show();
                }
            }


        }
    }
        public class Holder {
        TextView tv01;
        TextView tv02;
        TextView tv03;
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
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
}
