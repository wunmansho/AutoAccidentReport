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
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.auto.accident.report.R;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.PersistenceObj;

import java.util.ArrayList;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 1/19/2018.
 */

class ListVehicleTypeAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    //  String [] result0;
    private ArrayList<String> rsVT_ID = new ArrayList<>();
    private ArrayList<String> rsVT_TYPE = new ArrayList<>();
    private String message;
    private int duration;
    private int type;
    private Context context;
    private int posx;
    private RotateAnimation rotateAnimation;
    private int DA_ID;
    private String DA_ID_STR;
    private String PERSIST_ACTION_IN_PROGRESS;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;

    public ListVehicleTypeAdapter(ListVehicleType ListVehicleType,
                                  ArrayList<String> alVT_ID,
                                  ArrayList<String> alVT_TYPE) {
        // TODO Auto-generated constructor stub

        context = ListVehicleType;
        rsVT_ID = alVT_ID;
        rsVT_TYPE = alVT_TYPE;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsVT_ID.size();

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
        rowView = inflater.inflate(R.layout.list_vehicle_type_adapter, null);
        holder.tv01 = rowView.findViewById(R.id.tvVT_ID);
        holder.tv02 = rowView.findViewById(R.id.tvVT_TYPE);
        holder.tv01.setText(rsVT_ID.get(position));
        holder.tv02.setText(rsVT_TYPE.get(position));



        rowView.setOnClickListener(view -> {
                    context = view.getContext();

                    mPersistenceObjDao = new PersistenceObjDao(context);

            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                // TODO Auto-generated method stub

                DA_ID_STR = rsVT_ID.get(position);
                if (isNumber(DA_ID_STR)) {
                    DA_ID = Integer.parseInt(DA_ID_STR);
                } else {
                    DA_ID = 0;
                }
                if (DA_ID > 21) {
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(1);
                    rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                    rotateAnimation.setDuration(100);
                    rowView.startAnimation(rotateAnimation);

                    posx = position;
                    scheduleDoListItem();

                } else {
                    final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounceme3);

                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);

                    rowView.startAnimation(myAnim);
                    Resources res = context.getResources();
                    message = res.getString(R.string.default_types);
                    duration = 20;
                    type = 0;
                    MDToast mdToast = MDToast.makeText(context, message, duration, type);
                    mdToast.setGravity(Gravity.TOP, 50, 200);
                    mdToast.show();
                }
            }
            //     Toast.makeText(context, "You Clicked "+rsIP_ID.get(position), Toast.LENGTH_LONG).show();
        });
        return rowView;
    }
    private void scheduleDoListItem() {
       
        Handler handler = new Handler();
        handler.postDelayed(this::doListItem, 200);
    }
    private void doListItem() {
        Intent intent = new Intent(context, UpdateVehicleType.class);
        intent.putExtra("VT_ID", rsVT_ID.get(posx));
        intent.putExtra("VT_TYPE", rsVT_TYPE.get(posx));
		 doClose();
        context.startActivity(intent);
    }
    public class Holder {
        TextView tv01;
        TextView tv02;

    }
    public void doClose() {

    //    mVehicleManifestDao.closeAll();
    }
}
