package com.hh.ehh.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.database.DataBaseSQLiteHelper;
import com.hh.ehh.sms.SMSReceiver;
import com.hh.ehh.ui.profile.ProfileManager;
import com.hh.ehh.utils.SharedPrefsConstants;
import com.hh.ehh.utils.StateListener;

public class LoginActivity extends AppCompatActivity {

    private TextView loginStatus;
    private ImageView logo;
    private SharedPreferences sharedPreferences;
    private DataBaseSQLiteHelper dbHelper = null;
    private SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        loginStatus = (TextView) findViewById(R.id.login_status);
        logo = (ImageView) findViewById(R.id.logo);
        loginStatus.setText(getResources().getString(R.string.please_wait));
        sharedPreferences = getSharedPreferences(SharedPrefsConstants.PREFS, Context.MODE_PRIVATE);
        boolean isRegistered = sharedPreferences.getBoolean(SharedPrefsConstants.REGISTERED, false);
        if (isRegistered) {
            updateDB();
        } else {
            String phoneNumber = checkPhoneNumber();
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                //REGISTER
            }
        }
        Intent intents = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intents);
        finish();
    }

    ////////////////////////////////////// WEBSERVICE NEEDED ////////////////////////////////////////////////////////////
    /*
    TODO webservice which returns a responsible and an image (optional) from a given PHONE NUMBER and ID_DOC
     */
    private void setDatabase() {
        dbHelper = DataBaseSQLiteHelper.newInstance(getApplicationContext());
        database = dbHelper.getWritableDatabase();
    }

    private void updateDB() {
        setDatabase();
        ProfileManager profileManager = new ProfileManager(database, getApplicationContext(), new StateListener() {
            @Override
            public void onSuccess(Boolean state) {
            }

            @Override
            public void onError(Boolean state) {
            }
        });

        Intent intents = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intents);
        finish();
    }

    private String checkSmsCode(String sms) {
        if (sms != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SharedPrefsConstants.REGISTERED, true);
            editor.apply();
        }
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String sms = extras.getString(SMSReceiver.SMS);
        checkSmsCode(sms);
    }

    private String checkPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }
}
