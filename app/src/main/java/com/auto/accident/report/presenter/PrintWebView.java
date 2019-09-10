package com.auto.accident.report.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.auto.accident.report.R;
import com.auto.accident.report.database.PersistenceObjDao;
import com.auto.accident.report.model.PersistenceObj;

public class PrintWebView extends Activity {
private PersistenceObjDao mPersistenceObjDao;
private PersistenceObj persistenceObj;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_web_view);
        final WebView webView = findViewById(R.id.webView);

        mPersistenceObjDao = new PersistenceObjDao(this);
        persistenceObj = mPersistenceObjDao.getPersistence("PERSIST_WEB_VIEW_DATA");
        String DA_DATA = persistenceObj.getPERSISTENCE_VALUE();

        //  webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadData(DA_DATA, "text/html; charset=utf-8", "UTF-8");

    }
    public void doClose() {
        //mInsurancePolicyVDao.closeAll();
        //mDeviceImageStoreDao.closeAll();
        //mPartyTypeDao.closeAll();
        //mInvolvedImageStoreDao.closeAll();
        //mVehicleTypeDao.closeAll();
        //mAccidentNoteDao.closeAll();
        //mInvolvedPartyDao.closeAll();
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