package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.database.DeviceUserDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.PersistenceObj;

import java.util.ArrayList;

/**
 * Author: myron
 * Created on 12/3/2018
 */
public class ListInvolvedMenuAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    //  String [] result0;
    private ArrayList<String> rsIM_ID = new ArrayList<>();
    private ArrayList<String> rsIM_TYPE = new ArrayList<>();
    private ArrayList<String> rsIM_DESC = new ArrayList<>();
    private ArrayList<String> rsIM_PHON1 = new ArrayList<>();
    private ArrayList<String> rsIM_EMAIL = new ArrayList<>();
    private ArrayList<String> rsIM_URL = new ArrayList<>();
    private ArrayList<String> rsIM_ICONURL = new ArrayList<>();
    private ArrayList<String> rsIM_CONFURL = new ArrayList<>();
    private Context context;
    private int posx;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;

  //  private InvolvedMenuDao mInvolvedMenuDao;



    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;

    private Resources res;
    private String did_play_InvolvedMenu;
    private DeviceUserDao mDeviceUserDao;
    private String rsMode;

    private boolean loaded;
  //  private DeviceUser deviceUser;
//    private  DeviceUserDao mDeviceUserDao;
    private Intent intent;
    private RotateAnimation rotateAnimation;
    private View rowView;
    private View currentItem;
    private String PERSIST_ACTION_IN_PROGRESS;
    public ListInvolvedMenuAdapter(ListInvolvedMenu ListInvolvedMenu,
                                   ArrayList<String> alIM_ID,
                                   ArrayList<String> alIM_TYPE,
                                   ArrayList<String> alIM_DESC,
                                   ArrayList<String> alIM_PHON1,
                                   ArrayList<String> alIM_EMAIL,
                                   ArrayList<String> alIM_URL,
                                   ArrayList<String> alIM_ICONURL,
                                   ArrayList<String> alIM_CONFURL
    ) {
        // TODO Auto-generated constructor stub

        context = ListInvolvedMenu;
        rsIM_ID = alIM_ID;
        rsIM_TYPE = alIM_TYPE;
        rsIM_DESC = alIM_DESC;
        rsIM_PHON1 = alIM_PHON1;
        rsIM_EMAIL = alIM_EMAIL;
        rsIM_URL = alIM_URL;
        rsIM_ICONURL  = alIM_ICONURL;
        rsIM_CONFURL = alIM_CONFURL;
        mPersistenceObjDao = new PersistenceObjDao(context);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IM_CALLER");
        rsMode = persistenceObj.getPERSISTENCE_VALUE();
        mDeviceUserDao = new DeviceUserDao(context);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsIM_ID.size();

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
         Holder holder = new  Holder();

        rowView = inflater.inflate(R.layout.list_involved_menu_adapter, null);

        holder.tvIM_ID = rowView.findViewById(R.id.tvIM_ID);
        holder.tvIM_TYPE = rowView.findViewById(R.id.tvIM_TYPE);
        holder.tvIM_DESC = rowView.findViewById(R.id.tvIM_DESC);
        holder.tvIM_PHON1 = rowView.findViewById(R.id.tvIM_PHON1);
        holder.tvIM_EMAIL = rowView.findViewById(R.id.tvIM_EMAIL);
        holder.tvIM_URL = rowView.findViewById(R.id.tvIM_URL);
        holder.tvIM_ICONURL = rowView.findViewById(R.id.tvIM_ICONURL);
        holder.tvIM_CONFURL = rowView.findViewById(R.id.tvIM_CONFURL);

        holder.tvIM_ID.setText(rsIM_ID.get(position));
        holder.tvIM_TYPE.setText(rsIM_TYPE.get(position));
        holder.tvIM_DESC.setText(rsIM_DESC.get(position));
        holder.tvIM_PHON1.setText(rsIM_PHON1.get(position));
        holder.tvIM_EMAIL.setText(rsIM_EMAIL.get(position));
        holder.tvIM_URL.setText(rsIM_URL.get(position));
        holder.tvIM_ICONURL.setText(rsIM_ICONURL.get(position));
        holder.tvIM_CONFURL.setText(rsIM_CONFURL.get(position));

        rowView.setOnClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                // TODO Auto-generated method stub
                context = view.getContext();
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                currentItem = parent.getChildAt(position);
                currentItem.startAnimation(rotateAnimation);

                pos = position;
                scheduleDismissMenu();
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
       // Intent intent = new Intent(context, Update ListInvolvedMenu.class);

        context.startActivity(intent);
    }

    public class Holder {
        TextView tvIM_ID;
        TextView tvIM_TYPE;
        TextView tvIM_DESC;
        TextView tvIM_PHON1;
        TextView tvIM_EMAIL;
        TextView tvIM_URL;
        TextView tvIM_ICONURL;
        TextView tvIM_CONFURL;

    }

    public void doClose() {
         mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //    mVehicleManifestDao.closeAll();
    }
    private void scheduleDismissMenu() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissItem, 250);
    }
    private void dismissItem() {
        String DA_TYPE = rsIM_TYPE.get(pos);


        switch (DA_TYPE) {
            case "IPM": {
                doClose();
                intent = new Intent(context, ListInvolvedParty.class);
                context.startActivity(intent);

                break;
            }

            case "IVM": {
                doClose();
                intent = new Intent(context, ListInvolvedVehicle.class);
                context.startActivity(intent);
                break;
            }
            case "BCM": {
              //  doClose();
             //   intent = new Intent(context, InitActivity.class);
              //  context.startActivity(intent);
                break;
            }
            case "RPM": {
                doClose();
                intent = new Intent(context, PdfPrint.class);
                context.startActivity(intent);
                break;
            }

        }



    }
    
    
}
