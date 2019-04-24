package com.auto.accident.report.audio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.auto.accident.report.models.InvolvedImageStoreDao;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.presenter.MultiMediaMenu;
import com.auto.accident.report.util.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.auto.accident.report.R.id;
import static com.auto.accident.report.R.layout;

/**
 * @author Chathura Wijesinghe <cdanasiri@gmail.com> on 9/9/15.
 */
public class RecordingActivity extends AppCompatActivity implements View.OnClickListener {
     private Intent intent;
    private TextView mTimerTextView;

    private ImageButton mStopButton;

    private MediaRecorder mRecorder;
    private long mStartTime = 0;

    private int[] amplitudes = new int[100];
    private int i = 0;

    private Handler mHandler = new Handler();
    Context context;
    private PersistenceObj persistenceObj;
    private PersistenceObjDao mPersistenceObjDao;
    private InvolvedImageStoreDao mInvolvedImageStoreDao;

    private String mImageFileLocation = "";

    private String imageFileName;



    private Runnable mTickExecutor = new Runnable() {
        @Override
        public void run() {
            tick();
            mHandler.postDelayed(mTickExecutor,100);
        }
    };
    private File mOutputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_recording);
        this.mTimerTextView =  this.findViewById(id.timer);
        this.mStopButton =  this.findViewById(id.stop_button);
        this.mStopButton.setOnClickListener(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mPersistenceObjDao = new PersistenceObjDao(this);
        mInvolvedImageStoreDao = new InvolvedImageStoreDao(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("VoiceRecorder", "output: " + getOutputFile());
        startRecording();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRecorder != null) {
            stopRecording(false);
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
            mRecorder.setAudioEncodingBitRate(48000);
        } else {
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setAudioEncodingBitRate(64000);
        }
        mRecorder.setAudioSamplingRate(16000);
        mOutputFile = getOutputFile();
        mOutputFile.getParentFile().mkdirs();
        mRecorder.setOutputFile(mOutputFile.getAbsolutePath());

        try {
            mRecorder.prepare();
            mRecorder.start();
            mStartTime = SystemClock.elapsedRealtime();
            mHandler.postDelayed(mTickExecutor, 100);
            Log.d("VoiceRecorder","started recording to "+mOutputFile.getAbsolutePath());
        } catch (IOException e) {
            Log.e("VoiceRecorder", "prepare() failed "+e.getMessage());
        }
    }

    protected  void stopRecording(boolean saveFile) {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        mStartTime = 0;
        mHandler.removeCallbacks(mTickExecutor);
        if (!saveFile && mOutputFile != null) {
            mOutputFile.delete();
        }

        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        String rsMode = persistenceObj.getPERSISTENCE_VALUE();
        mImageFileLocation = mOutputFile.getAbsolutePath();
        imageFileName = utils.getPictureName(mImageFileLocation);
        if (rsMode.equals("ACCIDENT") || rsMode.equals("INVOLVED_PARTY") || rsMode.equals("INVOLVED_VEHICLE")) {
            long imageAdded = mInvolvedImageStoreDao.addData(mImageFileLocation, imageFileName);
        }
    }

    private File getOutputFile() {
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US);
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)
                + "/AccidentReport"
                + "/VoiceRecorder/RECORDING_"
                + dateFormat.format(new Date())
                + ".m4a");

    }

    private void tick() {
        long time = (mStartTime < 0) ? 0 : (SystemClock.elapsedRealtime() - mStartTime);
        int minutes = (int) (time / 60000);
        int seconds = (int) (time / 1000) % 60;
        int milliseconds = (int) (time / 100) % 10;
        mTimerTextView.setText(minutes+":"+(seconds < 10 ? "0"+seconds : seconds)+"."+milliseconds);
        if (mRecorder != null) {
            amplitudes[i] = mRecorder.getMaxAmplitude();
            //Log.d("Voice Recorder","amplitude: "+(amplitudes[i] * 100 / 32767));
            if (i >= amplitudes.length -1) {
                i = 0;
            } else {
                ++i;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case id.stop_button:
                stopRecording(true);

                Uri uri = Uri.parse("file://" + mOutputFile.getAbsolutePath());
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(uri);
                sendBroadcast(scanIntent);
                setResult(Activity.RESULT_OK, new Intent().setData(uri));
                intent = new Intent(this, MultiMediaMenu.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}