package com.auto.accident.report.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.auto.accident.report.R;
import com.auto.accident.report.audio.RecordingActivity;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.photos.CameraActivity;
import com.auto.accident.report.photos.PhotoGalleryActivity;
import com.auto.accident.report.photos.VideoActivity;
import com.auto.accident.report.util.CrossfadeWrapper;

import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

//import com.auto.accident.report.conference.ConferenceActivity;

public class MultiMediaMenu extends AppCompatActivity {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    //   private AccountHeader headerResult = null;
    private Drawer mDrawer = null;
    private MiniDrawer miniResult = null;
    private Crossfader crossFader;
    private TextView tvIP_ID;
    private TextView tvAID_ID;
    private TextView tvIP_FNAME;
    private TextView tvIP_PTYPE;
    private TextView tvIV_ID;
    private TextView tvIV_YEAR;
    private TextView tvIV_MAKE;
    private TextView tvIV_MODEL;
    private TextView tvXX_COMP;
    private PersistenceObj persistenceObj;
    private PersistenceObjDao mPersistenceObjDao;
    private String MULTI_MEDIA_MENU_CALLER;
    private String PERSIST_COMPANY_NAME;
    private Intent intent;
    private Context context;
    private String FNAME;
    private Resources res;
    private View view;
    private String DA_ID;
    private String REMOTE_CONFIG_SCAN;
    private String REMOTE_CONFIG_VIDEO_CALL;
    private String REMOTE_CONFIG_KEY;
    private RecyclerView mRecyclerView;
    private static final String configLocale = Locale.getDefault().getCountry().toLowerCase();
    private static final String TAG = "MultiMediaMenu";
    private boolean ActionInProgress;
    private String PERSIST_ACTION_IN_PROGRESS;
    private boolean CrossFade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_media_menu);
        tvIP_PTYPE = findViewById(R.id.tvIP_PTYPE);
        tvIP_ID = findViewById(R.id.tvIP_ID);
        tvAID_ID = findViewById(R.id.tvAID_ID);
        tvIP_FNAME = findViewById(R.id.tvIP_FNAME);
        tvIV_ID = findViewById(R.id.tvIV_ID);
        tvIV_YEAR = findViewById(R.id.tvIV_YEAR);
        tvIV_MAKE = findViewById(R.id.tvIV_MAKE);
        tvIV_MODEL = findViewById(R.id.tvIV_MODEL);
        tvXX_COMP = findViewById(R.id.tvXX_COMP);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        // Handle Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        res = getResources();
        context = this;
        ActionInProgress = false;
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mPersistenceObjDao.updateData("PERSIST_ACTION_IN_PROGRESS", "false");
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        DA_ID = persistenceObj.getPERSISTENCE_VALUE();
        if (DA_ID.equals("") || DA_ID.equals("-1") || DA_ID.equals("0")) {
            tvAID_ID.setText("");
        } else {
            tvAID_ID.setText(res.getString(R.string.accident_number) + " " + persistenceObj.getPERSISTENCE_VALUE());
        }
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IP_ID");
        tvIP_ID.setText(persistenceObj.getPERSISTENCE_VALUE());
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_IV_ID");
        tvIV_ID.setText(persistenceObj.getPERSISTENCE_VALUE());
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_TEMP_01");
        tvIP_FNAME.setText(persistenceObj.getPERSISTENCE_VALUE());
        FNAME = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_TEMP_02");
        tvIP_PTYPE.setText(persistenceObj.getPERSISTENCE_VALUE());
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_TEMP_03");
        tvIV_YEAR.setText(persistenceObj.getPERSISTENCE_VALUE());
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_TEMP_04");
        tvIV_MAKE.setText(persistenceObj.getPERSISTENCE_VALUE());
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_TEMP_05");
        tvIV_MODEL.setText(persistenceObj.getPERSISTENCE_VALUE());
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_MULTI_MEDIA_CALLER");
        MULTI_MEDIA_MENU_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        getSupportActionBar().setTitle(R.string.multi_media_menu);
        REMOTE_CONFIG_KEY = "REMOTE_CONFIG_VIDEO_CALL";
        REMOTE_CONFIG_VIDEO_CALL = getPersistantRemoteConfig(REMOTE_CONFIG_KEY);
        REMOTE_CONFIG_KEY = "REMOTE_CONFIG_SCAN";
        REMOTE_CONFIG_SCAN = getPersistantRemoteConfig(REMOTE_CONFIG_KEY);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_COMPANY_NAME");
        PERSIST_COMPANY_NAME = persistenceObj.getPERSISTENCE_VALUE();
        tvXX_COMP.setText(PERSIST_COMPANY_NAME);
        if (REMOTE_CONFIG_VIDEO_CALL.equals("true")  && MULTI_MEDIA_MENU_CALLER.equals("LIST_INVOLVED_PARTY")) {
            if (REMOTE_CONFIG_SCAN.equals("true")) {
                mDrawer = new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar)
                        .withTranslucentStatusBar(false)
                        .withInnerShadow(true)
                        .withSliderBackgroundColor(0x000000)
                        .addDrawerItems(
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.app_name),
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.video_conference_call_label).withDescription(res.getString(R.string.expert_window1) + " " + FNAME).withIcon(R.drawable.ic_video_call_white_36dp).withIdentifier(5).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.scan_accident_info).withIcon(R.drawable.ic_scanner_white_36dp).withIdentifier(6).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)
                        ) // add the items we want to use with our Drawer


                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                //    context = view.getContext();
                                //    mPersistenceObjDao = new PersistenceObjDao(context);
                                mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_PARTY");
                                mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_PARTY");
                                mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_PARTY");
                                mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_PARTY");
                                switch (position) {
                                    case 5: {
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, RecordingActivity.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                        }
                                            break;

                                    }

                                    case 6: {
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, PhotoGalleryActivity.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                        }
                                        //    context.startActivity(intent);
                                        break;
                                    }

                                    case 7: {
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, CameraActivity.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                            //    context.startActivity(intent);
                                            // photo
                                        }
                                        break;
                                    }
                                    case 8: {
                                        // video
                                        intent = new Intent(context, VideoActivity.class);
                                        doClose();
                                        context.startActivity(intent);
                                        finish();
                                        //  context.startActivity(intent);
                                        break;
                                    }
                                    case 9: {
                                        // video conference
                                        //    intent = new Intent(context, ConferenceActivity.class);
                                        //    doClose();
                                        //   context.startActivity(intent);
                                        //   finish();
                                        //     Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case 10: {
                                        // scan documents
                                        if (ActionInProgress == false) {
                                            Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                        }
                                        break;
                                    }
                                    case 11: {
                                        //  Take Notes
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, ListNotes.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                        }
                                        // context.startActivity(intent);
                                        break;
                                    }
                                    case 12: {
                                        // help
                                        if (ActionInProgress == false) {
                                            ActionInProgress = true;
                                            showNavSequenceVideoConferencePerson(viewGroup);
                                        }

                                        break;
                                    }

                                    default: {

                                        break;
                                    }
                                }
                                return false;
                            }
                        })
                        .withGenerateMiniDrawer(true)
                        .withSavedInstance(savedInstanceState)
                        // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                        .buildView();
                //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                miniResult = mDrawer.getMiniDrawer();

                //get the widths in px for the first and second panel
                int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                crossFader = new Crossfader()
                        .withContent(findViewById(R.id.crossfade_content))
                        .withFirst(mDrawer.getSlider(), firstWidth)
                        .withSecond(miniResult.build(this), secondWidth)
                        .withSavedInstance(savedInstanceState)
                        .build();

                //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                scheduleCrossfade();
            }
            if (REMOTE_CONFIG_SCAN.equals("false")) {
                mDrawer = new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar)
                        .withTranslucentStatusBar(false)
                        .withInnerShadow(true)
                        .withSliderBackgroundColor(0x000000)
                        .addDrawerItems(
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.app_name),
                                new DividerDrawerItem(),
                                new DividerDrawerItem(),
                                new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.video_conference_call_label).withDescription(res.getString(R.string.expert_window1) + " " + FNAME).withIcon(R.drawable.ic_video_call_white_36dp).withIdentifier(5).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)
                        ) // add the items we want to use with our Drawer
                         .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                //    context = view.getContext();
                                //    mPersistenceObjDao = new PersistenceObjDao(context);
                                mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_PARTY");
                                mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_PARTY");
                                mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_PARTY");
                                mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_PARTY");
                                switch (position) {
                                    case 5: {
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, RecordingActivity.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                        }
                                        break;
                                    }

                                    case 6: {
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, PhotoGalleryActivity.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                        }
                                        //    context.startActivity(intent);
                                        break;
                                    }

                                    case 7: {
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, CameraActivity.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                            //    context.startActivity(intent);
                                            // photo
                                        }
                                        break;
                                    }
                                    case 8: {
                                        // video
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, VideoActivity.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                            //  context.startActivity(intent);
                                        }
                                        break;
                                    }
                                    case 9: {
                                        // video conference
                                        //    intent = new Intent(context, ConferenceActivity.class);
                                        //    doClose();
                                        //   context.startActivity(intent);
                                        //   finish();
                                        //     Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                        break;
                                    }

                                    case 10: {
                                        //  Take Notes
                                        if (ActionInProgress == false) {
                                            intent = new Intent(context, ListNotes.class);
                                            doClose();
                                            context.startActivity(intent);
                                            finish();
                                        }
                                        // context.startActivity(intent);
                                        break;
                                    }
                                    case 11: {
                                        // help
                                        if (ActionInProgress == false) {
                                            ActionInProgress = true;
                                            showNavSequenceVideoConferencePersonNoScan(viewGroup);
                                        }

                                        break;
                                    }

                                    default: {

                                        break;
                                    }
                                }
                                return false;
                            }
                        })
                        .withGenerateMiniDrawer(true)
                        .withSavedInstance(savedInstanceState)
                        // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                        .buildView();
                //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                miniResult = mDrawer.getMiniDrawer();

                //get the widths in px for the first and second panel
                int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                crossFader = new Crossfader()
                        .withContent(findViewById(R.id.crossfade_content))
                        .withFirst(mDrawer.getSlider(), firstWidth)
                        .withSecond(miniResult.build(this), secondWidth)
                        .withSavedInstance(savedInstanceState)
                        .build();

                //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                scheduleCrossfade();
            }
        } else {
            switch (MULTI_MEDIA_MENU_CALLER) {

                case "LIST_INVOLVED_PARTY": {
                    if (REMOTE_CONFIG_SCAN.equals("true")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)
                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.scan_accident_info).withIcon(R.drawable.ic_scanner_white_36dp).withIdentifier(6).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)
                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_PARTY");
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_PARTY");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_PARTY");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_PARTY");
                                        switch (position) {
                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, RecordingActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                            }

                                            case 6: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                break;
                                            }

                                            case 7: {
                                                intent = new Intent(context, CameraActivity.class);
                                                doClose();
                                                context.startActivity(intent);
                                                finish();
                                                //    context.startActivity(intent);
                                                // photo
                                                break;
                                            }
                                            case 8: {
                                                // video
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                    //  context.startActivity(intent);
                                                }
                                                break;
                                            }

                                            case 9: {
                                                // scan documents
                                                if (ActionInProgress == false) {
                                                    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            }
                                            case 10: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                // context.startActivity(intent);
                                                break;
                                            }
                                            case 11: {
                                                // help
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceNoVideoConferencePerson(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }
                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();

                    }
                    if (REMOTE_CONFIG_SCAN.equals("false")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)
                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)
                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_PARTY");
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_PARTY");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_PARTY");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_PARTY");
                                        switch (position) {
                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, RecordingActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                            }

                                            case 6: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                    //    context.startActivity(intent);
                                                }
                                                break;
                                            }

                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                // photo
                                                break;
                                            }
                                            case 8: {
                                                // video
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //  context.startActivity(intent);
                                                break;
                                            }


                                            case 9: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                // context.startActivity(intent);
                                                break;
                                            }
                                            case 10: {
                                                // help
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceNoVideoConferencePersonNoScan(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }
                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();

                    }
                    break;
                }

                case "LIST_INVOLVED_VEHICLE": {
                    if (REMOTE_CONFIG_SCAN.equals("true")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)
                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.scan_accident_info).withIcon(R.drawable.ic_scanner_white_36dp).withIdentifier(6).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_DV_MODE", "SELECTPROFILE");
                                        mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_INVOLVED_VEHICLE");

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                    //  context.startActivity(intent);
                                                }
                                                break;
                                            }

                                            case 6: {
                                                //    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                intent = new Intent(context, CameraActivity.class);
                                                doClose();
                                                context.startActivity(intent);
                                                finish();
                                                //   context.startActivity(intent);

                                                break;
                                            }
                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //   context.startActivity(intent);
                                                break;
                                            }
                                            case 8: {
                                                if (ActionInProgress == false) {
                                                    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            }
                                            case 9: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                                //  context.startActivity(intent);
                                            }
                                            case 10: {
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceVehicle(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();

                    }
                    if (REMOTE_CONFIG_SCAN.equals("false")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)
                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_DV_MODE", "SELECTPROFILE");
                                        mPersistenceObjDao.updateData("PERSIST_DV_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_INVOLVED_VEHICLE");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_INVOLVED_VEHICLE");

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                    //  context.startActivity(intent);
                                                }
                                                break;
                                            }

                                            case 6: {
                                                //    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                    //   context.startActivity(intent);
                                                }
                                                break;
                                            }
                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //   context.startActivity(intent);
                                                break;
                                            }

                                            case 8: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                                //  context.startActivity(intent);
                                            }
                                            case 9: {
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceVehicleNoScan(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();

                    }
                    break;
                }
                case "LIST_ACCIDENT": {
                    if (REMOTE_CONFIG_SCAN.equals("true")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)

                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.scan_accident_info).withIcon(R.drawable.ic_scanner_white_36dp).withIdentifier(6).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_ACCIDENT");
                                        DA_ID = "0";
                                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, RecordingActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                            }

                                            case 6: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                break;
                                            }

                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                // photo
                                                break;
                                            }
                                            case 8: {
                                                // video
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //  context.startActivity(intent);
                                                break;
                                            }

                                            case 9: {
                                                // scan documents
                                                if (ActionInProgress == false) {
                                                    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            }
                                            case 10: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                // context.startActivity(intent);
                                                break;
                                            }
                                            case 11: {
                                                // help
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceNoVideoConferencePerson(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();

                    }
                    if (REMOTE_CONFIG_SCAN.equals("false")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)

                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_ACCIDENT");
                                        DA_ID = "0";
                                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, RecordingActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                            }

                                            case 6: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                break;
                                            }

                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                // photo
                                                break;
                                            }
                                            case 8: {
                                                // video
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //  context.startActivity(intent);
                                                break;
                                            }


                                            case 9: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                // context.startActivity(intent);
                                                break;
                                            }
                                            case 10: {
                                                // help
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceNoVideoConferencePersonNoScan(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();

                    }
                    break;
                }
                case "LIST_DEVICE_USER": {
                    if (REMOTE_CONFIG_SCAN.equals("true")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)

                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.scan_accident_info).withIcon(R.drawable.ic_scanner_white_36dp).withIdentifier(6).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                  .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_ACCIDENT");
                                        DA_ID = "0";
                                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, RecordingActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                            }

                                            case 6: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                break;
                                            }

                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);
                                                // photo
                                                break;
                                            }
                                            case 8: {
                                                // video
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //  context.startActivity(intent);
                                                break;
                                            }

                                            case 9: {
                                                // scan documents
                                                if (ActionInProgress == false) {
                                                    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            }
                                            case 10: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                // context.startActivity(intent);
                                                break;
                                            }
                                            case 11: {
                                                // help
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceNoVideoConferencePerson(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();

                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();
                    }
                    if (REMOTE_CONFIG_SCAN.equals("false")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)

                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.record_statements).withIcon(FontAwesome.Icon.faw_microphone_slash).withIdentifier(1).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_ACCIDENT");
                                        DA_ID = "0";
                                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, RecordingActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                break;
                                            }

                                            case 6: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                    //    context.startActivity(intent);
                                                }
                                                break;
                                            }

                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                    //    context.startActivity(intent);
                                                    // photo
                                                }
                                                break;
                                            }
                                            case 8: {
                                                // video
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //  context.startActivity(intent);
                                                break;
                                            }


                                            case 9: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                // context.startActivity(intent);
                                                break;
                                            }
                                            case 10: {
                                                // help
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceNoVideoConferencePersonNoScan(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();

                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();
                    }
                    break;
                }
                case "LIST_DEVICE_VEHICLE": {
                    if (REMOTE_CONFIG_SCAN.equals("true")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)

                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.scan_accident_info).withIcon(R.drawable.ic_scanner_white_36dp).withIdentifier(6).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_ACCIDENT");
                                        DA_ID = "0";
                                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();

                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //   context.startActivity(intent);

                                                break;
                                            }

                                            case 6: {
                                                //    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();

                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);

                                                break;
                                            }
                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //   context.startActivity(intent);
                                                break;
                                            }
                                            case 8: {
                                                Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            case 9: {
                                                //  Take Notes
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, ListNotes.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //  context.startActivity(intent);
                                                break;
                                            }
                                            case 10: {
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceVehicle(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();
                    }
                    if (REMOTE_CONFIG_SCAN.equals("false")) {
                        mDrawer = new DrawerBuilder()
                                .withActivity(this)
                                .withToolbar(toolbar)
                                .withTranslucentStatusBar(false)

                                .withInnerShadow(true)
                                .withSliderBackgroundColor(0x000000)
                                .addDrawerItems(
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.app_name),
                                        new DividerDrawerItem(),
                                        new DividerDrawerItem(),
                                        new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                        new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                                ) // add the items we want to use with our Drawer
                                 .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        //    context = view.getContext();
                                        //    mPersistenceObjDao = new PersistenceObjDao(context);
                                        mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                                        mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_ACCIDENT");
                                        DA_ID = "0";
                                        mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                        mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);

                                        switch (position) {

                                            case 5: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, PhotoGalleryActivity.class);
                                                    doClose();

                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //   context.startActivity(intent);

                                                break;
                                            }

                                            case 6: {
                                                //    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, CameraActivity.class);
                                                    doClose();

                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //    context.startActivity(intent);

                                                break;
                                            }
                                            case 7: {
                                                if (ActionInProgress == false) {
                                                    intent = new Intent(context, VideoActivity.class);
                                                    doClose();
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                                //   context.startActivity(intent);
                                                break;
                                            }
                                          case 8: {
                                                //  Take Notes
                                              if (ActionInProgress == false) {
                                                  intent = new Intent(context, ListNotes.class);
                                                  doClose();
                                                  context.startActivity(intent);
                                                  finish();
                                              }
                                                //  context.startActivity(intent);
                                                break;
                                            }
                                            case 9: {
                                                if (ActionInProgress == false) {
                                                    ActionInProgress = true;
                                                    showNavSequenceVehicleNoScan(viewGroup);
                                                }
                                                break;
                                            }

                                            default: {

                                                break;
                                            }
                                        }

                                        return false;
                                    }
                                })
                                .withGenerateMiniDrawer(true)
                                .withSavedInstance(savedInstanceState)
                                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                                .buildView();
                        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                        miniResult = mDrawer.getMiniDrawer();

                        //get the widths in px for the first and second panel
                        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                        crossFader = new Crossfader()
                                .withContent(findViewById(R.id.crossfade_content))
                                .withFirst(mDrawer.getSlider(), firstWidth)
                                .withSecond(miniResult.build(this), secondWidth)
                                .withSavedInstance(savedInstanceState)
                                .build();

                        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                        scheduleCrossfade();
                    }
                    break;
                }

                default: {
                    mDrawer = new DrawerBuilder()
                            .withActivity(this)
                            .withToolbar(toolbar)
                            .withTranslucentStatusBar(false)

                            .withInnerShadow(true)
                            .withSliderBackgroundColor(0x000000)
                            .addDrawerItems(
                                    new DividerDrawerItem(),
                                    new DividerDrawerItem(),
                                    new PrimaryDrawerItem().withName(R.string.app_name),
                                    new DividerDrawerItem(),
                                    new DividerDrawerItem(),
                                    new PrimaryDrawerItem().withName(R.string.media_gallery).withIcon(FontAwesome.Icon.faw_images).withIdentifier(2).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                    new PrimaryDrawerItem().withName(R.string.take_pictures).withIcon(FontAwesome.Icon.faw_camera).withIdentifier(3).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                    new PrimaryDrawerItem().withName(R.string.take_video).withIcon(FontAwesome.Icon.faw_video).withIdentifier(4).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                    new PrimaryDrawerItem().withName(R.string.take_notes).withIcon(FontAwesome.Icon.faw_sticky_note).withIdentifier(7).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000),
                                    new PrimaryDrawerItem().withName(R.string.get_help).withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(8).withIconColor(0xFFFFFFFF).withSelectedIconColor(0x00000000)

                            ) // add the items we want to use with our Drawer
                            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                @Override
                                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                    //    context = view.getContext();
                                    //    mPersistenceObjDao = new PersistenceObjDao(context);
                                    mPersistenceObjDao.updateData("PERSIST_CAMERA_CALLER", "LIST_ACCIDENT");
                                    mPersistenceObjDao.updateData("PERSIST_PIC_MODE", "ACCIDENT");
                                    mPersistenceObjDao.updateData("PERSIST_GALLERY_CALLER", "LIST_ACCIDENT");
                                    mPersistenceObjDao.updateData("PERSIST_LIST_NOTES_CALLER", "LIST_ACCIDENT");
                                    mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "LIST_ACCIDENT");
                                    DA_ID = "0";
                                    mPersistenceObjDao.updateData("PERSIST_IP_ID", DA_ID);
                                    mPersistenceObjDao.updateData("PERSIST_IV_ID", DA_ID);

                                    switch (position) {

                                        case 5: {
                                            if (ActionInProgress == false) {
                                                intent = new Intent(context, PhotoGalleryActivity.class);
                                                doClose();

                                                context.startActivity(intent);
                                                finish();
                                                //   context.startActivity(intent);
                                            }
                                            break;
                                        }

                                        case 6: {
                                            //    Toast.makeText(MultiMediaMenu.this, ((Nameable) drawerItem).getName().getText(MultiMediaMenu.this), Toast.LENGTH_SHORT).show();
                                            if (ActionInProgress == false) {
                                                intent = new Intent(context, CameraActivity.class);
                                                doClose();

                                                context.startActivity(intent);
                                                finish();
                                                //    context.startActivity(intent);
                                            }
                                            break;
                                        }
                                        case 7: {
                                            if (ActionInProgress == false) {
                                                intent = new Intent(context, VideoActivity.class);
                                                doClose();
                                                context.startActivity(intent);
                                                finish();
                                            }
                                            //   context.startActivity(intent);
                                            break;
                                        }
                                       case 8: {
                                            //  Take Notes
                                           if (ActionInProgress == false) {
                                               intent = new Intent(context, ListNotes.class);
                                               doClose();
                                               context.startActivity(intent);
                                               finish();
                                           }
                                            //  context.startActivity(intent);
                                            break;
                                        }
                                        case 9: {
                                            if (ActionInProgress == false) {
                                                ActionInProgress = true;
                                                showNavSequenceNoVideoConferencePersonNoScan(viewGroup);
                                            }
                                            break;
                                        }

                                        default: {

                                            break;
                                        }
                                    }

                                    return false;
                                }
                            })
                            .withGenerateMiniDrawer(true)
                            .withSavedInstance(savedInstanceState)
                            // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                            .buildView();
                    //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
                    miniResult = mDrawer.getMiniDrawer();

                    //get the widths in px for the first and second panel
                    int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
                    int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

                    //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
                    //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
                    crossFader = new Crossfader()
                            .withContent(findViewById(R.id.crossfade_content))
                            .withFirst(mDrawer.getSlider(), firstWidth)
                            .withSecond(miniResult.build(this), secondWidth)
                            .withSavedInstance(savedInstanceState)
                            .build();

                    //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                    miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

                    //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
                    crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

                    scheduleCrossfade();
                    break;


                }
            }
    }
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = mDrawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        //add the values which need to be saved from the crossFader to the bundle
        outState = crossFader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //  if (SystemUtils.getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
        //       inflater.inflate(R.menu.embedded, menu);
        //      menu.findItem(R.id.menu_1).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_sort).color(Color.WHITE).actionBar());
        //   }
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader != null && crossFader.isCrossFaded()) {
            crossFader.crossFade();

        } else {
            switch (MULTI_MEDIA_MENU_CALLER) {
                case "LIST_INVOLVED_PARTY": {
                    intent = new Intent(this, ListInvolvedParty.class);
                   doClose();
                    startActivity(intent);
                    //   context
                 break;
             }

             case "LIST_INVOLVED_VEHICLE": {
                 intent = new Intent(this, ListInvolvedVehicle.class);
                doClose();
                 startActivity(intent);
                 break;
             }
                case "LIST_ACCIDENT": {
                    intent = new Intent(this, ListAccident.class);
                   doClose();
                    startActivity(intent);
                    break;
                }
                case "LIST_DEVICE_USER": {
                    intent = new Intent(this, ListDeviceUser.class);
                   doClose();
                    startActivity(intent);
                    break;
                }
                case "LIST_DEVICE_VEHICLE": {
                    intent = new Intent(this, ListDeviceVehicle.class);
                   doClose();
                    startActivity(intent);
                    break;
                }
 			default: {
                super.onBackPressed();
 			break;
 			}
 			}

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case R.id.menu_1:
                crossFader.crossFade();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void scheduleCrossfade() {
        Handler handler = new Handler();
        handler.postDelayed(this::doCrossfade, 2000);
    }
    private void scheduleCrossfadeB() {
        Handler handler = new Handler();
        handler.postDelayed(this::doCrossfade, 1);
    }

    private void doCrossfade() {

        crossFader.crossFade();
    }
    public void doClose() {
        // mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
      //  mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();

      //  mDeviceUserDao.closeAll();

        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
        //mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }


    public void showNavSequenceVideoConferencePerson(View view) {
          mRecyclerView = mDrawer.getRecyclerView();
        float texSize = 50;

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();
       // crossFader.crossFade();

        int xx = cc;
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.audio_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.wipp))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_REVEALING )
                                    scheduleCrossfadeB();
                                {

                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.tap))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setPrimaryTextSize(texSize)
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_conference_call_help))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.scan_accident_info))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(11))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.subject_notes))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(12))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                   
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }
    public void showNavSequenceVideoConferencePersonNoScan(View view) {
        
        mRecyclerView = mDrawer.getRecyclerView();
        float texSize = 50;

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();
        crossFader.crossFade();
       //  drawerLayout = mDrawer.getDrawerLayout();
       //  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        int xx = cc;
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.audio_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_REVEALING )
                                    scheduleCrossfadeB();
                                {

                                }
                            }
                        })
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.wipp))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.tap))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setPrimaryTextSize(texSize)
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_conference_call_help))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.subject_notes))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(11))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }

    public void showNavSequenceNoVideoConferencePerson(View view) {
        crossFader.getCrossFadeSlidingPaneLayout().setClickable(false);
        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();
       //  drawerLayout = mDrawer.getDrawerLayout();
       //  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        int xx = cc;
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.audio_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_REVEALING )
                                    scheduleCrossfadeB();
                                {

                                }
                            }
                        })
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.wipp))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.tap))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.scan_accident_info))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.subject_notes))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(11))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }
    public void showNavSequenceNoVideoConferencePersonNoScan(View view) {
        crossFader.getCrossFadeSlidingPaneLayout().setClickable(false);
        mRecyclerView = mDrawer.getRecyclerView();

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();

       //  drawerLayout = mDrawer.getDrawerLayout();
       //  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
   
        int xx = cc;
        new MaterialTapTargetSequence()

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.audio_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_REVEALING )
                                    scheduleCrossfadeB();
                                {

                                }
                            }
                        })
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.wipp))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.tap))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.subject_notes))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {

                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }

    public void showNavSequenceVehicle(View view) {
        crossFader.getCrossFadeSlidingPaneLayout().setClickable(false);
        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();

       //  drawerLayout = mDrawer.getDrawerLayout();
       //  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        int xx = cc;
        new MaterialTapTargetSequence()



                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.wipp))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_REVEALING )
                                    scheduleCrossfadeB();
                                {

                                }
                            }
                        })
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.tap))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))

                        .setPrimaryText(res.getString(R.string.scan_accident_info))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.subject_notes))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(10))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }
    public void showNavSequenceVehicleNoScan(View view) {
        crossFader.getCrossFadeSlidingPaneLayout().setClickable(false);
        mRecyclerView = mDrawer.getRecyclerView();


        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        int cc = mRecyclerView.getChildCount();

       //  drawerLayout = mDrawer.getDrawerLayout();
       //  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        int xx = cc;
        new MaterialTapTargetSequence()



                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(5))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.wipp))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_REVEALING )
                                    scheduleCrossfadeB();
                                {

                                }
                            }
                        })
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(6))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.tap))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(7))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.video_menu_item))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )


                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(8))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.subject_notes))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                CrossFade = crossFader.isCrossFaded();
                                if (!CrossFade) {
                                    crossFader.crossFade();
                                }
                            }
                        })

                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(MultiMediaMenu.this)
                        .setTarget(mRecyclerView.getChildAt(9))
                        .setFocalColour(0x00000000)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG + " " + res.getString(R.string.drawer_label))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptFocal(new RectanglePromptFocal())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state == MaterialTapTargetPrompt.STATE_DISMISSED || state == MaterialTapTargetPrompt.STATE_FINISHING || state == MaterialTapTargetPrompt.STATE_FINISHED || state == MaterialTapTargetPrompt.STATE_DISMISSING)
                                {
                                    
                                    ActionInProgress = false;
                                }
                            }
                        }))


                .show();
    }
    public String getPersistantRemoteConfig(String PERSISTENCE_KEY) {

        persistenceObj = mPersistenceObjDao.getPersistence(PERSISTENCE_KEY);
        String PERSISTENCE_VALUE = persistenceObj.getPERSISTENCE_VALUE();
        return PERSISTENCE_VALUE;
    }



}
