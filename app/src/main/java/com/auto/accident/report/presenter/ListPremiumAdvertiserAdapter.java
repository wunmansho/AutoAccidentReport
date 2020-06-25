package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.auto.accident.report.R;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.database.PremiumAdvertiserDao;
import com.auto.accident.report.model.PersistenceObj;
import com.auto.accident.report.model.PremiumAdvertiser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: myron
 * Created on 7/9/2018
 */
public class ListPremiumAdvertiserAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    //   String [] result1;
    private Context context;


    private int alpha = 50;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private int alpha3 = 150;
    private int alphaCompare;
    private Boolean fireClick = true;
    private String message;
    private int pos;

    private int duration;
    private int type;
    private String PERSIST_ACTION_IN_PROGRESS;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;

    private final ArrayList<String> rsPA_ID = new ArrayList<>();
    private final ArrayList<String> rsPA_MENU = new ArrayList<>();
    private final ArrayList<String> rsPA_ADVERTISER = new ArrayList<>();
    private final ArrayList<String> rsPA_PHONE = new ArrayList<>();
    private final ArrayList<String> rsPA_URL = new ArrayList<>();
    private final ArrayList<String> rsPA_PATH = new ArrayList<>();
    private final ArrayList<String> rsPA_FILENAME = new ArrayList<>();


    public ListPremiumAdvertiserAdapter(ListPremiumAdvertiser ListPremiumAdvertiser) {
        // TODO Auto-generated constructor stub
        context = ListPremiumAdvertiser;


        PremiumAdvertiserDao mPremiumAdvertiserDao = new PremiumAdvertiserDao(context);
        List<PremiumAdvertiser> premiumadvertiserList = new ArrayList<>();
        String DA_MENU = "ATTORNEYS";
        premiumadvertiserList = mPremiumAdvertiserDao.getPAByMenu(DA_MENU);
        for (PremiumAdvertiser premiumAdvertiser : premiumadvertiserList) {
            rsPA_ID.add(Integer.toString(premiumAdvertiser.getPA_ID()));
            rsPA_MENU.add(premiumAdvertiser.getPA_MENU());
            rsPA_ADVERTISER.add(premiumAdvertiser.getPA_ADVERTISER());
            rsPA_PHONE.add(premiumAdvertiser.getPA_PHONE());
            rsPA_URL.add(premiumAdvertiser.getPA_URL());
            rsPA_PATH.add(premiumAdvertiser.getPA_PATH());
            rsPA_FILENAME.add(premiumAdvertiser.getPA_FILENAME());

        }

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsPA_ID.size();

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
        rowView = inflater.inflate(R.layout.list_premium_advertiser_adapter, null);
        holder.tvPA_ID = rowView.findViewById(R.id.tvPA_ID);
        holder.tvPA_MENU = rowView.findViewById(R.id.tvPA_MENU);
        holder.tvPA_ADVERTISER = rowView.findViewById(R.id.tvPA_ADVERTISER);
        holder.tvPA_PHONE = rowView.findViewById(R.id.tvPA_PHONE);
        holder.tvPA_URL = rowView.findViewById(R.id.tvPA_URL);
        holder.tvPA_PATH = rowView.findViewById(R.id.tvPA_PATH);
        holder.tvPA_FILENAME = rowView.findViewById(R.id.tvPA_FILENAME);
        holder.DA_BANNER = rowView.findViewById(R.id.DA_BANNER);
        holder.tvPA_ID.setText(rsPA_ID.get(position));
        holder.tvPA_MENU.setText(rsPA_MENU.get(position));
        holder.tvPA_ADVERTISER.setText(rsPA_ADVERTISER.get(position));
        holder.tvPA_PHONE.setText(rsPA_PHONE.get(position));
        holder.tvPA_URL.setText(rsPA_URL.get(position));
        holder.tvPA_PATH.setText(rsPA_PATH.get(position));
        holder.tvPA_FILENAME.setText(rsPA_FILENAME.get(position));

        String image = rsPA_PATH.get(position);
        Glide.with(context)
                .load(image)
                .into(holder.DA_BANNER);

        rowView.setOnClickListener(view -> {
            context = view.getContext();

            if (fireClick == true) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
                PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
                if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                    final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounceme3);

                    BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(mBullHornBounceInterpolator);

                    rowView.startAnimation(myAnim);
                    String PA_PHONE = rsPA_PHONE.get(position);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + PA_PHONE));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    }
                }
            }
            fireClick = true;
            holder.DA_BANNER.setImageAlpha(alpha1);

        });

        rowView.setOnLongClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {

                holder.DA_BANNER.setImageAlpha(alpha2);
                context = view.getContext();
                Resources res = view.getResources();
                String Advertiser = rsPA_ADVERTISER.get(position);
                message = res.getString(R.string.call) + ' ' + Advertiser;
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
            }
            return false;
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

        ImageView DA_BANNER;
        TextView tvPA_ID;
        TextView tvPA_MENU;
        TextView tvPA_ADVERTISER;
        TextView tvPA_PHONE;
        TextView tvPA_URL;
        TextView tvPA_PATH;
        TextView tvPA_FILENAME;
    }
    public void doClose() {

    //    mVehicleManifestDao.closeAll();
    }
}
