package com.auto.accident.report.photos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.auto.accident.report.R;
import com.auto.accident.report.models.DeviceImageStoreDao;
import com.auto.accident.report.models.DeviceUserDao;
import com.auto.accident.report.models.InvolvedImageStoreDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.DeviceUser;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.utils;

import static com.auto.accident.report.util.utils.isNumber;

public class VideoPlayActivity extends AppCompatActivity {

    private static final String TAG = "VideoPlayActivity";

    private MediaPlayer mMediaPlayer;
    private Uri mVideoUri;
    private ImageButton mPlayPauseButton;
    private SurfaceView mSurfaceView;
     private String image;
     private Context context;
    private RotateAnimation rotateAnimation;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private DeviceUserDao mDeviceUserDao;
    private DeviceUser deviceUser;
    private Resources res;
    private MediaControllerCompat mController;
    private Toolbar toolbar;
    private String fileExt;
    private String rsMode;
    private String DA_RESULT2;
    private Drawer mDrawer;
    private PrimaryDrawerItem pitem1;
    private PrimaryDrawerItem pitem2;
    private PrimaryDrawerItem pitem3;
    private PrimaryDrawerItem pitem4;
    private InvolvedImageStoreDao mInvolvedImageStoreDao;
    private DeviceImageStoreDao mDeviceImageStoreDao;
     private Intent intent;
    private String DA_ID_STR;
    private int DUX_ID;
    private MediaControllerCompat.TransportControls mControllerTransportControls;
    private final MediaControllerCompat.Callback mControllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);

            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mPlayPauseButton.setImageResource(R.drawable.ic_pause_circle_filled_black_48dp);
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    mPlayPauseButton.setImageResource(R.drawable.ic_play_circle_filled_white_black_48dp);
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    mPlayPauseButton.setImageResource(R.drawable.ic_play_circle_filled_white_black_48dp);
                    break;
            }
        }
    };
    private PlaybackStateCompat.Builder mPBuilder;
    private MediaSessionCompat mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        toolbar = findViewById(R.id.my_toolbar);
        mPlayPauseButton = findViewById(R.id.videoPlayPauseButton);
        mSurfaceView = findViewById(R.id.videoSurfaceView);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mDeviceUserDao = new DeviceUserDao(this);
        res = getResources();
        toolbar.setNavigationOnClickListener(view -> {
            context = view.getContext();
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            mPersistenceObjDao.updateData("PERSIST_DU_MODE", "SELECT");
            mPersistenceObjDao.updateData("PERSIST_DU_CALLER", "ACCIDENT_MENU");
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = toolbar.getChildAt(2);
            btnBack.startAnimation(rotateAnimation);
            scheduleDismissToolbar();

        });
        Intent callingIntent = this.getIntent();
        if (callingIntent != null) {
            image = callingIntent.getStringExtra("DA_IMAGE");
            mVideoUri = Uri.parse(image);
            fileExt = utils.splitFileExt(image);
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_GALLERY_CALLER");
            rsMode = persistenceObj.getPERSISTENCE_VALUE();
           // if (rsMode.equals("LIST_INVOLVED_PARTY") || rsMode.equals("LIST_ACCIDENT") || rsMode.equals("LIST_INVOLVED_VEHICLE")) {
                persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                DUX_ID = Integer.parseInt(DA_ID_STR);
            } else {
                DUX_ID = 0;
            }
                deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
                String DU_FNAME = deviceUser.getDU_FNAME();

                String[] splitString;
                int splitLength;

                splitString = DU_FNAME.split(" ");
                DA_RESULT2 = splitString[0];


                if (fileExt.equals("m4a")) {
                    toolbar.setSubtitle(getString(R.string.audio_player));
                } else {
                    toolbar.setSubtitle(getString(R.string.video_player)) ;
                }
          //  }


        }


        mSession = new MediaSessionCompat(this, TAG);
        mSession.setCallback(new MediaSessionCallback(this));
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mPBuilder = new PlaybackStateCompat.Builder();
        mController = new MediaControllerCompat(this, mSession);
        mControllerTransportControls = mController.getTransportControls();

        mDrawer = new DrawerBuilder()
                .withActivity(this)


                .withTranslucentStatusBar(true)
                .withTranslucentNavigationBar(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)

                .addDrawerItems(

                        new DividerDrawerItem(),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.app_name),
                        new DividerDrawerItem(),
                        new DividerDrawerItem(),
                        pitem1 = new PrimaryDrawerItem().withName(R.string.gallery_return).withIcon(FontAwesome.Icon.faw_shield_alt).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1),

                        new DividerDrawerItem(),

                        pitem2 = new PrimaryDrawerItem().withName(R.string.delete_photo).withIcon(FontAwesome.Icon.faw_trash).withIconColor(0xFFFFFFFF).withSelectedIconColor(0xFF0288D1)



                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Context context = view.getContext();
                        //  Toast.makeText(AccidentMenu.this, ((Nameable) drawerItem).getName().getText(AccidentMenu.this), Toast.LENGTH_SHORT).show();
                        switch (position) {
                            case 5: {
                                doClose();
                                finish();
                                intent = new Intent(context, PhotoGalleryActivity.class);
                                context.startActivity(intent);

                                break;
                            }
                            case 7: {
                                if (rsMode.equals("LIST_INVOLVED_PARTY") || rsMode.equals("LIST_ACCIDENT") || rsMode.equals("LIST_INVOLVED_VEHICLE")) {
                                    mInvolvedImageStoreDao = new InvolvedImageStoreDao(context);
                                    mInvolvedImageStoreDao.deletePIC(image);

                                }
                                if (rsMode.equals("LIST_DEVICE_USER") || rsMode.equals("LIST_DEVICE_VEHICLE")) {
                                    mDeviceImageStoreDao = new DeviceImageStoreDao(context);
                                    mDeviceImageStoreDao.deletePIC(image);

                                }
                                doClose();
                                finish();
                                intent = new Intent(context, PhotoGalleryActivity.class);
                                startActivity(intent);

                                break;
                            }
                        }


                        //we do not consume the event and want the Drawer to continue with the event chain
                        return false;
                    }
                })

                .withSavedInstance(savedInstanceState)
                .withCloseOnClick(false)
                .build();


scheduleDoOpen();

    }

    public void playPauseClick(View view) {
        if (mController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
            mControllerTransportControls.pause();
        } else if (mController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PAUSED ||
                mController.getPlaybackState().getState() == PlaybackStateCompat.STATE_STOPPED ||
                mController.getPlaybackState().getState() == PlaybackStateCompat.STATE_NONE) {
            mControllerTransportControls.play();
        }

    }

    @Override
    protected void onStop() {

        mController.unregisterCallback(mControllerCallback);
        if (mController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING ||
                mController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PAUSED) {
            mControllerTransportControls.stop();
        }

        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mController.registerCallback(mControllerCallback);
        mPBuilder.setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mSession.setPlaybackState(mPBuilder.build());
    }

    @Override
    protected void onPause() {

        if (mController.getPlaybackState().getState() == PlaybackStateCompat.STATE_PLAYING) {
            mControllerTransportControls.pause();
        }
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSession.release();
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener,
            AudioManager.OnAudioFocusChangeListener {

        private final Context mContext;
        private final AudioManager mAudioManager;
        private final IntentFilter mNoisyIntentFilter;
        private final AudioBecommingNoisy mAudioBecommingNoisy;

        public MediaSessionCallback(Context context) {
            super();

            mContext = context;
            mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            mAudioBecommingNoisy = new AudioBecommingNoisy();
            mNoisyIntentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
            mSurfaceView.getHolder().addCallback(this);
        }

        @Override
        public void onPlay() {
            super.onPlay();

            mediaPlay();
        }

        @Override
        public void onPause() {
            super.onPause();

            mediaPause();
        }

        @Override
        public void onStop() {
            super.onStop();

            releaseResources();
        }

        private void releaseResources() {
            mSession.setActive(false);
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }

        private void mediaPlay() {
            registerReceiver(mAudioBecommingNoisy, mNoisyIntentFilter);
            int requestAudioFocusResult = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (requestAudioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mSession.setActive(true);
                mPBuilder.setActions(PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_STOP);
                if (mMediaPlayer != null) {
                    mPBuilder.setState(PlaybackStateCompat.STATE_PLAYING,

                            mMediaPlayer.getCurrentPosition(), 1.0f, SystemClock.elapsedRealtime());
                } else {
                    mPBuilder.setState(PlaybackStateCompat.STATE_PLAYING,

                            0, 1.0f, SystemClock.elapsedRealtime());
                }
                mSession.setPlaybackState(mPBuilder.build());
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }
            }
        }

        private void mediaPause() {
            if (mMediaPlayer != null) {
                mMediaPlayer.pause();
                mPBuilder.setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_STOP);
                mPBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                        mMediaPlayer.getCurrentPosition(), 1.0f, SystemClock.elapsedRealtime());
                mSession.setPlaybackState(mPBuilder.build());
                mAudioManager.abandonAudioFocus(this);
                unregisterReceiver(mAudioBecommingNoisy);
            }
            }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            mMediaPlayer = MediaPlayer.create(mContext, mVideoUri, surfaceHolder);
            mMediaPlayer.setOnCompletionListener(this);
            schedulePlayVideo();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mPBuilder.setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_STOP);
            mPBuilder.setState(PlaybackStateCompat.STATE_STOPPED,
                    mMediaPlayer.getCurrentPosition(), 1.0f, SystemClock.elapsedRealtime());
            mSession.setPlaybackState(mPBuilder.build());
        }

        @Override
        public void onAudioFocusChange(int audioFocusChanged) {
            switch (audioFocusChanged) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mediaPause();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlay();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    mediaPause();
                    break;
            }
        }

        private class AudioBecommingNoisy extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
              mediaPause();
            }
        }
    }
    private void schedulePlayVideo() {
        Handler handler = new Handler();
        handler.postDelayed(this::playVideo, 200);
    }
    private void playVideo() {
      //  mPlayPauseButton.performClick();
    }
    private void scheduleDismissToolbar() {
        Handler handler = new Handler();
        handler.postDelayed(this::dismissActivity, 200);
    }
    private void dismissActivity() {
        doClose();
        View btnBack = toolbar.getChildAt(2);
        btnBack.clearAnimation();
        finish();
        intent = new Intent(this, PhotoGalleryActivity.class);
        startActivity(intent);
    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
       // mDeviceUserDao.closeAll();
        //mInsurancePolicyPDao.closeAll();
        //mInvolvedVehicleDao.closeAll();
     //   mAccidentIdDao.closeAll();
        mPersistenceObjDao.closeAll();
        mSession.setActive(false);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        //mPremiumAdvertiserDao.closeAll();
        //mInsurancePolicyDao.closeAll();
        //mDeviceVehicleDao.closeAll();
        //mVehicleManifestDao.closeAll();

    }
    private void scheduleDoOpen() {
        Handler handler = new Handler();
        handler.postDelayed(this::openIt, 500);
    }
    private void openIt() {
        mDrawer.openDrawer();
        scheduleDoClose();
    }
    private void scheduleDoClose() {
        Handler handler = new Handler();
        handler.postDelayed(this::closeIt, 2000);
    }
    private void closeIt() {
        mDrawer.closeDrawer();
    }
}
