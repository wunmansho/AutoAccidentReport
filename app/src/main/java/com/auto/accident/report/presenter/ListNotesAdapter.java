package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.auto.accident.report.database.AccidentNoteDao;
import com.auto.accident.report.database.InvolvedImageStoreDao;
import com.auto.accident.report.database.InvolvedPartyDao;
import com.auto.accident.report.database.InvolvedVehicleDao;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.database.VehicleManifestDao;
import com.auto.accident.report.model.AccidentNote;
import com.auto.accident.report.model.InvolvedParty;
import com.auto.accident.report.model.InvolvedVehicle;
import com.auto.accident.report.model.PersistenceObj;

import java.util.ArrayList;
import java.util.List;

import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 4/16/2018.
 */

class ListNotesAdapter extends BaseAdapter {
    private static final String REQ_CODE_INVOLVED_PARTY = "LIST_INVOLVED_PARTY";
    private static final String REQ_CODE_INVOLVED_VEHICLE = "LIST_INVOLVED_VEHICLE";
    private static LayoutInflater inflater = null;
    //   String [] result1;
    private Context context;
    private PersistenceObjDao mPersistenceObjDao;
    //  String [] result0;
    private final ArrayList<String> rsAN_SUBJECT = new ArrayList<>();
    private final ArrayList<String> rsAN_NOTE = new ArrayList<>();
    private final ArrayList<String> rsIP_PTYPE = new ArrayList<>();
    ArrayList<String> rsIX_F_MANIFEST = new ArrayList<>();
    ArrayList<String> rsIX_M_MANIFEST = new ArrayList<>();
    ArrayList<String> rsIX_L_MANIFEST = new ArrayList<>();
    private final ArrayList<String> rsIV_YEAR = new ArrayList<>();
    private final ArrayList<String> rsIV_MAKE = new ArrayList<>();
    private final ArrayList<String> rsIV_MODEL = new ArrayList<>();
    private final ArrayList<String> rsIP_FNAME = new ArrayList<>();
    private final ArrayList<String> rsAN_CDATE = new ArrayList<>();
    private final ArrayList<String> rsAN_UDATE = new ArrayList<>();
    private final ArrayList<String> rsAN_ID = new ArrayList<>();
    private final ArrayList<String> rsAN_IPID = new ArrayList<>();
    private final ArrayList<String> rsAN_IVID = new ArrayList<>();
    private int alpha = 50;
    private int alpha1 = 255;
    private int alpha2 = 50;
    private Boolean clickImage;
    private Boolean fireClick = true;
    private String message;
    private int pos;

    private String DA_CALLER;
    private PersistenceObj persistenceObj;
    private AccidentNoteDao mAccidentNoteDao;
    private InvolvedPartyDao mInvolvedPartyDao;
    private InvolvedParty involvedParty;
    private InvolvedVehicle involvedVehicle;
    private Intent intent;
    private RotateAnimation rotateAnimation;
    private int AN_AID;
    private int AN_ID;
    private int AN_IPID;
    private int AN_IVID;
    private String DA_ID_STR;
    private int CURRENT_IP_ID;
    private int CURRENT_IPO_HOLDER;
    private int CURRENT_IPO_ID;
    private int CURRENT_IV_ID;
    private String PERSIST_ACTION_IN_PROGRESS;
    public ListNotesAdapter(ListNotes ListNotes) {
        // TODO Auto-generated constructor stub
        context = ListNotes;
        VehicleManifestDao mVehicleManifestDao = new VehicleManifestDao(context);
        InvolvedVehicleDao mInvolvedVehicleDao = new InvolvedVehicleDao(context);
        InvolvedImageStoreDao mInvolvedImageStoreDao = new InvolvedImageStoreDao(context);
        mPersistenceObjDao = new PersistenceObjDao(context);
        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_NOTES");
        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "LIST_NOTES");
        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_NOTES");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        String DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID)) {
            AN_AID = Integer.parseInt(DA_ID);
        } else {
            AN_AID = 0;
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_LIST_NOTES_CALLER");
        DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            CURRENT_IP_ID = Integer.parseInt(DA_ID_STR);
        } else {
            CURRENT_IP_ID = 0;
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            CURRENT_IV_ID = Integer.parseInt(DA_ID_STR);
        } else {
            CURRENT_IV_ID = 0;
        }

        mInvolvedPartyDao = new InvolvedPartyDao(context);
        mAccidentNoteDao = new AccidentNoteDao(context);

        List<AccidentNote> accidentnoteList = new ArrayList<>();
        switch (DA_CALLER) {
            case REQ_CODE_INVOLVED_PARTY: {
                CURRENT_IV_ID = 0;
                mPersistenceObjDao.updateData("PERSIST_IV_ID", "0");
                accidentnoteList = mAccidentNoteDao.getIPAccidentNotes(AN_AID, CURRENT_IP_ID);

                break;
            }
            case REQ_CODE_INVOLVED_VEHICLE: {
                CURRENT_IP_ID = 0;
                mPersistenceObjDao.updateData("PERSIST_IP_ID", "0");
                accidentnoteList = mAccidentNoteDao.getIVAccidentNotes(AN_AID, CURRENT_IV_ID);
                break;
            }
            default: {
                CURRENT_IV_ID = 0;
                mPersistenceObjDao.updateData("PERSIST_IV_ID", "0");
                CURRENT_IP_ID = 0;
                mPersistenceObjDao.updateData("PERSIST_IP_ID", "0");
                accidentnoteList = mAccidentNoteDao.getAllAccidentNotes(AN_AID);
            }
        }
        for (AccidentNote accidentNote : accidentnoteList) {
            rsAN_SUBJECT.add(accidentNote.getAN_SUBJECT());
            rsAN_NOTE.add(accidentNote.getAN_NOTE());
            rsAN_ID.add(Integer.toString(accidentNote.getAN_ID()));
            rsAN_IPID.add(Integer.toString(accidentNote.getAN_IPID()));
            int IP_ID = accidentNote.getAN_IPID();
            rsAN_IVID.add(Integer.toString(accidentNote.getAN_IVID()));
            int IV_ID = accidentNote.getAN_IVID();
            ArrayList<String> rsAN_APPATH = new ArrayList<>();
            rsAN_APPATH.add(accidentNote.getAN_APPATH());
            rsAN_CDATE.add(accidentNote.getAN_CDATE());
            rsAN_UDATE.add(accidentNote.getAN_UDATE());

            boolean NULLPOINTERIP_ID = false;
            if (IP_ID > 0) {
                //toDo get IP
                involvedParty = mInvolvedPartyDao.getInvolvedParty(IP_ID, AN_AID);
                try {
                    rsIP_PTYPE.add(involvedParty.getIP_PTYPE());
                } catch (NullPointerException e) {
                    NULLPOINTERIP_ID = true;
                    rsIP_PTYPE.add(" ");
                    rsIP_FNAME.add(" ");
                }
                if (!NULLPOINTERIP_ID) {
                    rsIP_FNAME.add(involvedParty.getIP_FNAME());
                }


            } else {
                rsIP_PTYPE.add(" ");
                rsIP_FNAME.add(" ");
            }
            boolean NULLPOINTERIV_ID = false;
            if (IV_ID > 0 && !NULLPOINTERIP_ID) {
                //toDo get IP
                involvedVehicle = mInvolvedVehicleDao.getInvolvedVehicle(IV_ID, AN_AID);
                try {
                    rsIV_YEAR.add(involvedVehicle.getIV_YEAR());
                } catch (NullPointerException e) {
                    NULLPOINTERIV_ID = true;
                    rsIV_YEAR.add(" ");
                    rsIV_MAKE.add(" ");
                    rsIV_MODEL.add(" ");
                }
                if (!NULLPOINTERIV_ID) {
                    rsIV_MAKE.add(involvedVehicle.getIV_MAKE());
                    rsIV_MODEL.add(involvedVehicle.getIV_MODEL());
                }

            } else {
                rsIV_YEAR.add(" ");
                rsIV_MAKE.add(" ");
                rsIV_MODEL.add(" ");
            }


            int noteCnt = 0;
            if (!NULLPOINTERIP_ID && !NULLPOINTERIV_ID) {
                noteCnt++;
            }
            if (NULLPOINTERIP_ID || NULLPOINTERIV_ID) {
                int AN_ID = accidentNote.getAN_ID();
                mAccidentNoteDao.deleteACCIDENT_NOTE(AN_AID, AN_ID);
                accidentnoteList.remove(noteCnt);
                rsAN_SUBJECT.remove(noteCnt);
                rsAN_NOTE.remove(noteCnt);
                rsAN_ID.remove(noteCnt);
                rsAN_IPID.remove(noteCnt);
                rsAN_IVID.remove(noteCnt);
                rsAN_APPATH.remove(noteCnt);
                rsIP_PTYPE.remove(noteCnt);
                rsAN_CDATE.remove(noteCnt);
                rsAN_UDATE.remove(noteCnt);
                rsIP_PTYPE.remove(noteCnt);
                rsIP_FNAME.remove(noteCnt);
                rsIP_PTYPE.remove(noteCnt);
                rsIV_YEAR.remove(noteCnt);
                rsIV_MAKE.remove(noteCnt);
                rsIV_MODEL.remove(noteCnt);
                NULLPOINTERIP_ID = false;
                NULLPOINTERIV_ID = false;
            }

        }


        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rsAN_ID.size();

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
        rowView = inflater.inflate(R.layout.list_notes_adapter, null);
        //  holder.tv0= rowView.findViewById(R.id.textView0);
        //  holder.tv1= rowView.findViewById(R.id.textView1);
        holder.rl01 = rowView.findViewById(R.id.rl01);
        //      holder.iv01 = (ImageView) rowView.findViewById(R.id.imageView01);
        //  holder.iv02 = (ImageView) rowView.findViewById(R.id.imageView02);
        holder.tv01 = rowView.findViewById(R.id.tvAN_SUBJECT);
        holder.tv02 = rowView.findViewById(R.id.tvIP_PTYPE);
        holder.tv03 = rowView.findViewById(R.id.tvIV_YEAR);
        holder.tv04 = rowView.findViewById(R.id.tvIV_MAKE);
        holder.tv05 = rowView.findViewById(R.id.tvIV_MODEL);
        holder.tv06 = rowView.findViewById(R.id.tvIP_FNAME);
        holder.tv09 = rowView.findViewById(R.id.tvAN_ID);
        holder.tv10 = rowView.findViewById(R.id.tvAN_IPID);
        holder.tv11 = rowView.findViewById(R.id.tvAN_AID);
        holder.tv12 = rowView.findViewById(R.id.tvAN_IVID);
        holder.tv13 = rowView.findViewById(R.id.tvAN_APPATH);
        holder.tv14 = rowView.findViewById(R.id.tvAN_CDATE);
        holder.tv15 = rowView.findViewById(R.id.tvAN_UDATE);
        holder.tv16 = rowView.findViewById(R.id.tvAN_NOTE);
        holder.tv17 = rowView.findViewById(R.id.tvACCIDENT);
        holder.ll01 = rowView.findViewById(R.id.ll01);
        holder.tv01.setText(rsAN_SUBJECT.get(position));
        holder.tv02.setText(rsIP_PTYPE.get(position));
        holder.tv03.setText(rsIV_YEAR.get(position));
        holder.tv04.setText(rsIV_MAKE.get(position));
        holder.tv05.setText(rsIV_MODEL.get(position));
        holder.tv06.setText(rsIP_FNAME.get(position));
        holder.tv09.setText(rsAN_ID.get(position));
        holder.tv10.setText(rsAN_IPID.get(position));
        holder.tv11.setText(Integer.toString(AN_AID));
        holder.tv12.setText(rsAN_IVID.get(position));
        holder.tv14.setText(rsAN_CDATE.get(position));
        holder.tv15.setText(rsAN_UDATE.get(position));
        holder.tv16.setText(rsAN_NOTE.get(position));
        //  holder.tv13.setText(rsAN_APPATH.get(position));
        //       String DA_PATH = rsAN_APPATH.get(position);
        String AN_IPID = rsAN_IPID.get(position);
        String AN_IVID = rsAN_IVID.get(position);
        if (!AN_IPID.equals("0") && !AN_IPID.equals("0")) {
            holder.tv06.setVisibility(View.VISIBLE);
        }
        if (!AN_IVID.equals("0") && !AN_IVID.equals("0")) {
            holder.tv03.setVisibility(View.VISIBLE);
            holder.tv04.setVisibility(View.VISIBLE);
            holder.tv05.setVisibility(View.VISIBLE);

        }

        if (AN_IVID.equals("0") || AN_IVID.equals("0") && AN_IPID.equals("0") || AN_IPID.equals("0")) {
        //    holder.tv17.setVisibility(View.VISIBLE);
        //    holder.tv11.setVisibility(View.VISIBLE);
        }




        rowView.setOnClickListener(view -> {
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_ACTION_IN_PROGRESS");
            PERSIST_ACTION_IN_PROGRESS = persistenceObj.getPERSISTENCE_VALUE();
            if (PERSIST_ACTION_IN_PROGRESS.equals("false") ) {
                intent = new Intent(context, UpdateNotes.class);
                String DA_ID = rsAN_ID.get(position);
                mPersistenceObjDao.updateData("PERSIST_AN_ID", DA_ID);
                mPersistenceObjDao.updateData("PERSIST_UPDATE_NOTES_CALLER", "LIST_NOTES");
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
    public void makeToast() {
        int duration = 20;
        int type = 0;
        MDToast mdToast = MDToast.makeText(context, message, duration, type);
        mdToast.setGravity(Gravity.TOP, 50, 200);

        mdToast.show();

    }

    public class Holder {
        RelativeLayout rl01;

        //      ImageView iv01;
        //    ImageView iv02;

        //TextView tv15;
        TextView tv01;
        TextView tv02;
        TextView tv03;
        TextView tv04;
        TextView tv05;
        TextView tv06;
        TextView tv07;
        TextView tv08;
        TextView tv09;
        TextView tv10;
        TextView tv11;
        TextView tv12;
        TextView tv13;
        TextView tv14;
        TextView tv15;
        TextView tv16;
        TextView tv17;
        RelativeLayout ll01;


    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
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
        //mVehicleManifestDao.closeAll();

    }
}
