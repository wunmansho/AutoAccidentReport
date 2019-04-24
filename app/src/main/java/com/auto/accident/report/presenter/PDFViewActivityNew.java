package com.auto.accident.report.presenter;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.auto.accident.report.R;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.firebase.FirebaseCloudUpload;
import com.auto.accident.report.models.PersistenceObjDao;
import com.auto.accident.report.objects.PersistenceObj;
import com.auto.accident.report.util.KeyboardUtils;
import com.shockwave.pdfium.PdfDocument;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.util.List;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static android.view.View.VISIBLE;
import static com.auto.accident.report.util.AdvancedSend.advancedSend;
import static com.auto.accident.report.util.utils.isNumber;

/**
 * Created by myron on 2/12/2019.
 */

public class PDFViewActivityNew extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {

    public static final int PERMISSION_CODE = 42042;
    private static final String SAMPLE_FILE = "en/involvedparty.pdf";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String TAG = PDFViewActivityNew.class.getSimpleName();
    private final static int REQUEST_CODE = 42;

    private PDFView pdfView;
    private Toolbar my_toolbar;

    private ImageView btnRight;
    private ImageView btnLeft;
    private ImageView btnCloud;
    private ImageView btnHelp;
    private ImageView btnShare;
    private TextView tvPage;
    private LinearLayout ll01;
    private TextView noData;
    private Uri uri;
    private Integer pageNumber = 0;
    private String pdfFileName;
    private PDDocument document;
    private String AID;
    private String albumName;
    // private TextView tvPage;
    private int page = 1;
    private String pageS;
    private String pageSL;
    private int TotalPages;
    //  private ImageButton btnRight;
    //  private ImageButton btnLeft;
    //  private FloatingActionButton btnHelp;
    // private ImageView btnShare;
    // private ImageView btnCloud;

    private Resources res;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private RotateAnimation rotateAnimation;
    private String FIREBASE_CLOUD_UPLOAD_IN_PROGRESS;
    private PersistenceObjDao mPersistenceObjDao;
    private PersistenceObj persistenceObj;
    private String DA_ID_STR;
    //   private Toolbar toolbar;
    private View view;
    private  Context context;
    private File root;
    private AssetManager assetManager;
    private Bitmap pageImage;
    private TextView tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view_activity);
        my_toolbar = findViewById(R.id.my_toolbar);
        btnCloud = findViewById(R.id.btnCloud);
        pdfView = findViewById(R.id.pdfView);
        btnRight = findViewById(R.id.btnRight);
        btnLeft = findViewById(R.id.btnLeft);
        btnHelp = findViewById(R.id.btnHelp );
        btnShare = findViewById(R.id.btnShare );
        tvPage = findViewById(R.id.tvPage );
       // tv = findViewById(R.id.tv);
        ll01 = findViewById(R.id.ll01 );
        noData = findViewById(R.id.noData );
        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_AID_ID");
        AID = persistenceObj.getPERSISTENCE_VALUE();
        String PERSISTANCE_KEY = "PERSIST_PDF_PAGE_COUNT" + AID;
        persistenceObj = mPersistenceObjDao.getPersistence(PERSISTANCE_KEY);

        DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
        if (isNumber(DA_ID_STR)) {
            TotalPages = Integer.parseInt(DA_ID_STR);
        } else {
            TotalPages = 0;
        }

        res = getResources();
        my_toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(100);
            rotateAnimation.setRepeatCount(1);
            rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
            View btnBack = my_toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);
        });

        if (TotalPages != 0){
            if (page < 10) {
                pageS = "00" + Integer.toString(page);
            }
            if (page > 9 && page < 100) {
                pageS = "0" + Integer.toString(page);
            }
            if (page > 99) {
                pageS = Integer.toString(page);
            }

            pageSL = Integer.toString(page) + "/" + Integer.toString(TotalPages);
            tvPage.setText(pageSL);

            btnHelp.setOnClickListener(view -> {
                rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(1);
                rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
                rotateAnimation.setDuration(100);
                btnHelp.startAnimation(rotateAnimation);
                scheduleDoHelp();

            });
            btnShare.setOnClickListener(view -> {
                if (fireClick) {
                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(Animation.INFINITE);
                    rotateAnimation.setRepeatMode(Animation.RESTART);
                    rotateAnimation.setDuration(1000);
                    btnShare = findViewById(R.id.btnShare);
                    btnShare.startAnimation(rotateAnimation);
                    //  Intent intent = new Intent(this, AdvancedSend.class);
                    //  this.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    boolean sendresult = advancedSend(this, res);
                    if (sendresult == true) {
                        scheduleDismissShareAnimation();
                    }
                }
                fireClick = true;
                btnShare.setImageAlpha(alpha1);
            });

            btnShare.setOnLongClickListener(view -> {
                btnShare.setImageAlpha(alpha2);
                context = view.getContext();
                Resources res = getResources();
                message = res.getString(R.string.share_report);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
                return false;
            });
            btnCloud.setOnClickListener(view -> {
                if (fireClick) {
                    mPersistenceObjDao.updateData("FIREBASE_CLOUD_UPLOAD_IN_PROGRESS", "true");

                    rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setRepeatCount(Animation.INFINITE);
                    rotateAnimation.setRepeatMode(Animation.RESTART);
                    rotateAnimation.setDuration(1000);
                    btnCloud = findViewById(R.id.btnCloud);
                    btnCloud.startAnimation(rotateAnimation);
                    animateCloud();
                    context = view.getContext();

                    Intent intent = new Intent(context, FirebaseCloudUpload.class);
                    context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


                }
                fireClick = true;
                btnCloud.setImageAlpha(alpha1);
            });

            btnCloud.setOnLongClickListener(view -> {
                btnCloud.setImageAlpha(alpha2);
                context = view.getContext();
                Resources res = getResources();
                message = res.getString(R.string.cloud_upload);
                duration = 20;
                type = 0;
                MDToast mdToast = MDToast.makeText(context, message, duration, type);
                mdToast.setGravity(Gravity.TOP, 50, 200);
                mdToast.show();
                fireClick = false;
                return false;
            });
            btnRight.setOnClickListener(view -> {
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

                // Use bounce interpolator with amplitude 0.2 and frequency 20
                BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(mBullHornBounceInterpolator);

                btnRight.startAnimation(myAnim);
                page++;
                if (page > TotalPages) {
                    page = 1;
                }

                if (page < 10) {
                    pageS = "00" + Integer.toString(page);
                }
                if (page > 9 && page < 100) {
                    pageS = "0" + Integer.toString(page);
                }
                if (page > 99) {
                    pageS = Integer.toString(page);
                }

                pageSL = Integer.toString(page) + "/" + Integer.toString(TotalPages);
                tvPage.setText(pageSL);
                albumName = "AccidentReport/AccidentReport000" + AID + pageS + ".pdf";

                File mStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), albumName);
                uri = Uri.fromFile(mStorageDirectory);


                pdfView.setBackgroundColor(Color.LTGRAY);

                displayFromUri(uri);


            });
            btnLeft.setOnClickListener(view -> {
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

                // Use bounce interpolator with amplitude 0.2 and frequency 20
                BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(mBullHornBounceInterpolator);

                btnLeft.startAnimation(myAnim);
                page--;
                if (page == 0) {
                    page = TotalPages;
                }
                if (page < 10) {
                    pageS = "00" + Integer.toString(page);
                }
                if (page > 9 && page < 100) {
                    pageS = "0" + Integer.toString(page);
                }
                if (page > 99) {
                    pageS = Integer.toString(page);
                }

                pageSL = Integer.toString(page) + "/" + Integer.toString(TotalPages);
                tvPage.setText(pageSL);
                albumName = "AccidentReport/AccidentReport000" + AID + pageS + ".pdf";

                File mStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), albumName);
                uri = Uri.fromFile(mStorageDirectory);


                pdfView.setBackgroundColor(Color.LTGRAY);

                displayFromUri(uri);
            });

            albumName = "AccidentReport/AccidentReport000" + AID + "001.pdf";

            File mStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), albumName);
            uri = Uri.fromFile(mStorageDirectory);


            pdfView.setBackgroundColor(Color.LTGRAY);

            displayFromUri(uri);
        }
        if (TotalPages == 0){
            pdfView.setVisibility(View.GONE);
            ll01.setVisibility(View.GONE);
            btnHelp.setVisibility(View.GONE);
            noData.setVisibility(VISIBLE);


        }


    }

    private void scheduleDismissShareAnimation() {
        Handler handler = new Handler();
        //hud.dismiss();
        handler.postDelayed(this::animateShare, 10000);

    }
    private void animateShare() {
        btnShare.clearAnimation();
    }
    private void scheduleDismissCloudAnimation() {
        Handler handler = new Handler();
        handler.postDelayed(this::animateCloud, 200);

    }
    private void animateCloud() {

        persistenceObj = mPersistenceObjDao.getPersistence("FIREBASE_CLOUD_UPLOAD_IN_PROGRESS");
        FIREBASE_CLOUD_UPLOAD_IN_PROGRESS  = persistenceObj.getPERSISTENCE_VALUE();
        if(!FIREBASE_CLOUD_UPLOAD_IN_PROGRESS.equals("false")) {
            scheduleDismissCloudAnimation();
        } else {
            btnCloud.clearAnimation();
        }
    }
    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();


    }

    private void displayFromUri(Uri uri) {
        pdfFileName = getFileName(uri);

        pdfView.fromUri(uri)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

 //   @OnActivityResult(REQUEST_CODE)  todo
    public void onResult(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            uri = intent.getData();
            displayFromUri(uri);
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        //    pageNumber = page;
        //   setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    private void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }

    public void disableButtons() {
        btnHelp.setEnabled(false);
        btnRight.setEnabled(false);
        btnLeft.setEnabled(false);
        btnShare.setEnabled(false);


    }
    public void enableButtons() {
        btnHelp.setEnabled(true);
        btnRight.setEnabled(true);
        btnLeft.setEnabled(true);
        btnShare.setEnabled(true);

    }


    public void showSequence0(View view) {
        KeyboardUtils.hideKeyboard(PDFViewActivityNew.this);

        disableButtons();
        final Toolbar tb = this.findViewById(R.id.my_toolbar);

        //int toolBarColorValue = Color.parseColor("#FF0288D1");
        //

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(PDFViewActivityNew.this)
                        .setTarget(tb.getChildAt(2))



                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator()))

                //  .addPrompt(new MaterialTapTargetPrompt.Builder(PDFViewActivityNew.this)
                //     .setTarget(btnShare)

                //
                //    .setPrimaryText(res.getString(R.string.btn_drive))
                //   .setSecondaryText(res.getString(R.string.got_it))
                //   .setAnimationInterpolator(new LinearOutSlowInInterpolator()))
                // .addPrompt(new MaterialTapTargetPrompt.Builder(PDFViewActivityNew.this)
                //         .setTarget(btnCloud)
                //
                //         .setPrimaryText(res.getString(R.string.btn_cloud))
                //        .setSecondaryText(res.getString(R.string.got_it))
                //        .setAnimationInterpolator(new LinearOutSlowInInterpolator()))

                .addPrompt(new MaterialTapTargetPrompt.Builder(PDFViewActivityNew.this)
                        .setTarget(btnLeft)


                        .setPrimaryText(res.getString(R.string.btn_left))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator()))
                .addPrompt(new MaterialTapTargetPrompt.Builder(PDFViewActivityNew.this)
                        .setTarget(btnRight)


                        .setPrimaryText(res.getString(R.string.btn_right))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator()))



                .addPrompt(new MaterialTapTargetPrompt.Builder(PDFViewActivityNew.this)
                        .setTarget(btnHelp)

                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                        {
                            @Override
                            public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                            {
                                if (state != MaterialTapTargetPrompt.STATE_REVEALING && state != MaterialTapTargetPrompt.STATE_REVEALED  && state != MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)

                                {
                                    enableButtons();
                                    //makeTieFocusableTrue();
                                }
                            }
                        })
                )


                .show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        View btnBack = my_toolbar.getChildAt(2);
        btnBack.clearAnimation();
    }
    private void scheduleDismissToolbar() {

        Handler handler = new Handler();
        //hud.dismiss();
        handler.postDelayed(this::dismissActivity, 200);

    }
    private void dismissActivity() {
        doClose();
        View btnBack = my_toolbar.getChildAt(2);
        btnBack.clearAnimation();
        Intent intent = new Intent(this, PdfPrint.class);
        startActivity(intent);


    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //   mInvolvedPartyDao.closeAll();
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
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }
    private void doHelp() {
        showSequence0(view);
    }

    public void onBackPressed() {
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setRepeatMode(Animation.RELATIVE_TO_SELF);
        View btnBack = my_toolbar.getChildAt(2);
        scheduleDismissToolbar();
        btnBack.startAnimation(rotateAnimation);

    }






}
