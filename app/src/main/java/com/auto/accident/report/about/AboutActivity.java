package com.auto.accident.report.about;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.mikepenz.aboutlibraries.LibTaskCallback;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.LibsConfiguration;
import com.mikepenz.aboutlibraries.entity.Library;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.auto.accident.report.R;
import com.auto.accident.report.anim.BullHornBounceInterpolator;
import com.auto.accident.report.presenter.AccidentMenu;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

/**
 * Created by mikepenz on 04.06.14.
 */
public class AboutActivity extends AppCompatActivity {
    private static final String TAG = "AboutActivity";
    private Toolbar toolbar;
    private Resources res;
    private FloatingActionButton btnHelp;
    private RecyclerView mRecyclerView;
    private RotateAnimation rotateAnimation;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_fragment);

        btnHelp = findViewById(R.id.btnHelp);
        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
        toolbar = findViewById(R.id.my_toolbar);
        res = getResources();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(300);
            View btnBack = toolbar.getChildAt(2);
            scheduleDismissToolbar();
            btnBack.startAnimation(rotateAnimation);

        });


        /*
        //NOTE: This is how you can modify a specific library definition during runtime
        HashMap<String, HashMap<String, String>> libsModification = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> modifyAboutLibraries = new HashMap<String, String>();
        modifyAboutLibraries.put("name", "_AboutLibraries");
        libsModification.put("aboutlibraries", modifyAboutLibraries);
        .withLibraryModification(libsModification);
        */

        LibsSupportFragment fragment = new LibsBuilder()
                .withVersionShown(true)
                .withLicenseShown(true)
                .withLibraryModification("aboutlibraries", Libs.LibraryFields.LIBRARY_NAME, "_AboutLibraries")
                .withFields(R.string.class.getFields())
                .withAutoDetect(true)
                .supportFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

        btnHelp.setOnClickListener(view -> {
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounceme);

            // Use bounce interpolator with amplitude 0.2 and frequency 20
            BullHornBounceInterpolator mBullHornBounceInterpolator = new BullHornBounceInterpolator(0.2, 20);
            myAnim.setInterpolator(mBullHornBounceInterpolator);

            btnHelp.startAnimation(myAnim);
            scheduleDoHelp();

           // showSequence0(view);


        });

    }

    LibTaskCallback libTaskCallback = new LibTaskCallback() {
        @Override
        public void onLibTaskStarted() {
            Log.e("AboutLibraries", "started");
        }

        @Override
        public void onLibTaskFinished(ItemAdapter fastItemAdapter) {
            Log.e("AboutLibraries", "finished");
        }
    };

    LibsConfiguration.LibsUIListener libsUIListener = new LibsConfiguration.LibsUIListener() {
        @Override
        public View preOnCreateView(View view) {
            return view;
        }

        @Override
        public View postOnCreateView(View view) {
            return view;
        }
    };

    LibsConfiguration.LibsListener libsListener = new LibsConfiguration.LibsListener() {
        @Override
        public void onIconClicked(View v) {
            Toast.makeText(v.getContext(), "We are able to track this now ;)", Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean onLibraryAuthorClicked(View v, Library library) {
            return false;
        }

        @Override
        public boolean onLibraryContentClicked(View v, Library library) {
            return false;
        }

        @Override
        public boolean onLibraryBottomClicked(View v, Library library) {
            return false;
        }

        @Override
        public boolean onExtraClicked(View v, Libs.SpecialButton specialButton) {
            return false;
        }

        @Override
        public boolean onIconLongClicked(View v) {
            return false;
        }

        @Override
        public boolean onLibraryAuthorLongClicked(View v, Library library) {
            return false;
        }

        @Override
        public boolean onLibraryContentLongClicked(View v, Library library) {
            return false;
        }

        @Override
        public boolean onLibraryBottomLongClicked(View v, Library library) {
            return false;
        }
    };

    public void disableButtons() {


        btnHelp.setEnabled(false);


    }
    public void enableButtons() {


        btnHelp.setEnabled(true);



    }


    public void showSequence0() {
        final Toolbar tb = this.findViewById(R.id.my_toolbar);
        mRecyclerView = findViewById(R.id.cardListView);
        int toolBarColorValue = Color.parseColor("#FF0288D1");
        //
        disableButtons();
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(AboutActivity.this)
                        .setTarget(tb.getChildAt(2))


                        .setPrimaryText(res.getString(R.string.shield_icon2))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                )

                .addPrompt(new MaterialTapTargetPrompt.Builder(AboutActivity.this)
                        .setTarget(mRecyclerView.getChildAt(0))

                        .setPrimaryText(res.getString(R.string.about_libs_help))
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setPromptFocal(new RectanglePromptFocal())
                )
                .addPrompt(new MaterialTapTargetPrompt.Builder(AboutActivity.this)
                        .setTarget(btnHelp)
                        .setPrimaryText(res.getString(R.string.btn_help) + TAG)
                        .setSecondaryText(res.getString(R.string.got_it))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator()))


                .show();
    }
    private void scheduleDismissToolbar() {
        Handler handler = new Handler();
        //hud.dismiss();
        handler.postDelayed(this::dismissActivity, 250);

    }
    private void dismissActivity() {
        Intent intent = new Intent(this, AccidentMenu.class);
        startActivity(intent);
    }
    private void scheduleDoHelp() {
        Handler handler = new Handler();
        handler.postDelayed(this::doHelp, 250);
    }

    private void doHelp() {
        showSequence0();
    }

}
