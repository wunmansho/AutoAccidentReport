package com.auto.accident.report.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
import com.auto.accident.report.presenter.AddNotes;

import static com.auto.accident.report.util.utils.isNumber;


public class FullScreenImageActivity extends AppCompatActivity implements
        View.OnLongClickListener {
    private PersistenceObjDao mPersistenceObjDao;
    private InvolvedImageStoreDao mInvolvedImageStoreDao;
    private DeviceImageStoreDao mDeviceImageStoreDao;
    private String image;
  //  private ImageButton btnDelete;
    private Boolean fireClick = true;
    private String message;
    private int duration;
    private int type;
    private int pos;
    private final int alpha1 = 255;
    private final int alpha2 = 50;
    private Drawer mDrawer;
    private PrimaryDrawerItem pitem1;
    private PrimaryDrawerItem pitem2;
    private PrimaryDrawerItem pitem3;
    private PrimaryDrawerItem pitem4;
    private String DA_ID_STR;
    private int DUX_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        DeviceUserDao mDeviceUserDao = new DeviceUserDao(this);
        ImageView fullScreenImageView = findViewById(R.id.fullScreenImageView);
      //  btnDelete = findViewById(R.id.btnDelete);
   //     Toolbar toolbar = findViewById(R.id.image_gallery_toolbar);


        mPersistenceObjDao = new PersistenceObjDao(this);
        PersistenceObj persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_GALLERY_CALLER");
        String DA_CALLER = persistenceObj.getPERSISTENCE_VALUE();
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_PIC_MODE");
        String picMode = persistenceObj.getPERSISTENCE_VALUE();

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
                            case 5:
                                Intent intent = new Intent(FullScreenImageActivity.this, PhotoGalleryActivity.class);
                                context.startActivity(intent);
                                break;
                            case 7:

                                if (DA_CALLER.equals("LIST_INVOLVED_PARTY") || DA_CALLER.equals("LIST_ACCIDENT") || DA_CALLER.equals("LIST_INVOLVED_VEHICLE")) {
                                    mInvolvedImageStoreDao = new InvolvedImageStoreDao(context);
                                    mInvolvedImageStoreDao.deletePIC(image);

                                }
                                if (DA_CALLER.equals("LIST_DEVICE_USER") || DA_CALLER.equals("LIST_DEVICE_VEHICLE")) {
                                    mDeviceImageStoreDao = new DeviceImageStoreDao(context);
                                    mDeviceImageStoreDao.deletePIC(image);

                                }
                                intent = new Intent(context, PhotoGalleryActivity.class);
                                startActivity(intent);
                                break;
                        }


                        //we do not consume the event and want the Drawer to continue with the event chain
                        return false;
                    }
                })

                .withSavedInstance(savedInstanceState)
                .withCloseOnClick(false)
                .build();
      //  mDrawer.openDrawer();
        scheduleDismiss();
        if (DA_CALLER.equals("SELECT_NOTE_ATTACHMENT")) {
         //   btnDelete.setVisibility(GONE);
            persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_DU_ID");
            DA_ID_STR = persistenceObj.getPERSISTENCE_VALUE();
            if (isNumber(DA_ID_STR)) {
                DUX_ID = Integer.parseInt(DA_ID_STR);
            } else {
                DUX_ID = 0;
            }
            DeviceUser deviceUser = mDeviceUserDao.getDeviceUser(DUX_ID);
            String DU_FNAME = deviceUser.getDU_FNAME();
            String[] splitString;
            int splitLength;
            String DA_RESULT2;
            splitString = DU_FNAME.split(" ");
            DA_RESULT2 = splitString[0];

         //   toolbar.setSubtitle(getString(R.string.welcome) + " " + DA_RESULT2 + " - " + getString(R.string.can));
        }

      //  toolbar.setNavigationOnClickListener(v -> {
      //      Intent intent = new Intent(FullScreenImageActivity.this, PhotoGalleryActivity.class);
       //     startActivity(intent);


     //   });
        fullScreenImageView.setOnClickListener(view -> {
            Context context = view.getContext();
            mPersistenceObjDao = new PersistenceObjDao(context);
            PersistenceObj persistenceObj1 = mPersistenceObjDao.getPersistence("PERSIST_GALLERY_CALLER");
            String rsMode = persistenceObj1.getPERSISTENCE_VALUE();
            if (rsMode.equals("SELECT_NOTE_ATTACHMENT")) {
                mPersistenceObjDao.updateData("PERSIST_AP_ID", image);
                mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "PHOTO_GALLERY");
                mPersistenceObjDao.updateData("PERSIST_ADD_NOTES_CALLER", "PHOTO_GALLERY");
                Intent intent = new Intent(FullScreenImageActivity.this, AddNotes.class);

                startActivity(intent);
            }
        });


     //   btnDelete.setOnClickListener(view -> {
     //       if (fireClick == true) {
     //           Context context = view.getContext();
    //            mInvolvedImageStoreDao = new InvolvedImageStoreDao(context);
    //            mInvolvedImageStoreDao.deletePIC(image);
    //            mDeviceImageStoreDao = new DeviceImageStoreDao(context);
    //            mDeviceImageStoreDao.deletePIC(image);
    // //           Intent intent = new Intent(FullScreenImageActivity.this, PhotoGalleryActivity.class);
    //            startActivity(intent);
      //      }
     //       fireClick = true;
      //      btnDelete.setImageAlpha(alpha1);
    //    });
   //     btnDelete.setOnLongClickListener(view -> {
    //        btnDelete.setImageAlpha(alpha2);
   //         Context context = view.getContext();
   //         Resources res = getResources();
   //         message = res.getString(R.string.delete_photo);
   //         duration = 20;
  //          type = 0;
  //          MDToast mdToast = MDToast.makeText(context, message, duration, type);
 //           mdToast.setGravity(Gravity.TOP, 50, 200);
  //          mdToast.show();
  //          fireClick = false;
  //          return false;
   //     });


        fullScreenImageView.setOnLongClickListener(this);

        Intent callingActivityIntent = getIntent();
        if (callingActivityIntent != null) {
            image = callingActivityIntent.getStringExtra("DA_IMAGE");

            if (image != null) {
                Glide.with(this)
                        .load(image)
                        .into(fullScreenImageView);
            }
        }
    }
    private void scheduleDismiss() {
        Handler handler = new Handler();
        //hud.dismiss();
        handler.postDelayed(this::openIt, 1000);
    }
    private void openIt() {

        mDrawer.openDrawer();
        scheduleDismiss2();
    }
    private void scheduleDismiss2() {
        Handler handler = new Handler();
        //hud.dismiss();
        handler.postDelayed(this::closeIt, 1000);
    }

    private void closeIt() {

        mDrawer.closeDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.full_image_share, menu);

        MenuItem menuItem = menu.findItem(R.id.image_share_menu);
        //     ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        //     shareActionProvider.setShareIntent(createShareIntent());
        return true;
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem carousel_item) {
        switch (carousel_item.getItemId()) {
            case R.id.image_share_menu:
                Toast.makeText(this, "share image button selected!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                super.onOptionsItemSelected(carousel_item);
        }
        
        return true;
    }
*/

    //  private Intent createShareIntent() {
    //     Intent shareIntent = new Intent(Intent.ACTION_SEND);
    //     shareIntent.setType("image/*");
    //    shareIntent.putExtra(Intent.EXTRA_STREAM, image);
    //     return shareIntent;
    //   }

    @Override
    public boolean onLongClick(View v) {

        //     Intent shareIntent = createShareIntent();
        //    startActivity(Intent.createChooser(shareIntent, "send to"));
        return true;
    }
}
