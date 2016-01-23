package com.hh.ehh.ui.splash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hh.ehh.R;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.utils.FragmentStackManager;
import com.hh.ehh.utils.SharedPrefsConstants;

public class SplashActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private FragmentStackManager fragmentStackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fragmentStackManager = FragmentStackManager.getInstance(this);
        sharedPreferences = getSharedPreferences(SharedPrefsConstants.PREFS, Context.MODE_PRIVATE);
        boolean isRegistered = sharedPreferences.getBoolean(SharedPrefsConstants.REGISTERED, false);
        if (isRegistered) {
            SplashFragment fragment = new SplashFragment();
            fragmentStackManager.loadFragment(fragment, R.id.splash_frame);
        } else {
            RegisterFragment fragment = new RegisterFragment();
            fragmentStackManager.loadFragment(fragment, R.id.splash_frame);
        }
    }

    @Override
    public void onBackPressed() {
        if (!fragmentStackManager.popBackStatFragment()) {
            CustomDialogs.closeDialog(SplashActivity.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    ////////////////////////////////////// WEBSERVICE NEEDED ////////////////////////////////////////////////////////////
    /*
    TODO webservice which returns a responsible and an image (optional) from a given PHONE NUMBER and ID_DOC when working uncomment this
     */
//    private void setDatabase() {
//        dbHelper = DataBaseSQLiteHelper.newInstance(getApplicationContext());
//        database = dbHelper.getWritableDatabase();
//    }
//
//    private void updateDB() {
//        setDatabase();
//        ProfileManager profileManager = new ProfileManager(database, getApplicationContext(), new StateListener() {
//            @Override
//            public void onSuccess(Boolean state) {
//            }
//
//            @Override
//            public void onError(Boolean state) {
//            }
//        });
//
//        Intent intents = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intents);
//        finish();
//    }
}
