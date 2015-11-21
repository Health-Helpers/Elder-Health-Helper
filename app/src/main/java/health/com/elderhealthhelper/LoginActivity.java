package health.com.elderhealthhelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import health.com.elderhealthhelper.responsible.MainActivity;
import health.com.elderhealthhelper.sms.SMSReceiver;
import health.com.elderhealthhelper.utils.SharedPrefsConstants;

public class LoginActivity extends AppCompatActivity {

    private TextView loginStatus;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginStatus = (TextView) findViewById(R.id.login_status);

        Intent intents = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intents);
        finish();
//      REMOVE WHEN EVERYTHING WORKS AS EXPECTED

//        sharedPreferences = getSharedPreferences(SharedPrefsConstants.PREFS, Context.MODE_PRIVATE);
//
//        boolean isRegistered = sharedPreferences.getBoolean(SharedPrefsConstants.REGISTERED, false);
//        String profile = sharedPreferences.getString(SharedPrefsConstants.PROFILE, null);
//        if(isRegistered && profile != null && profile.equals(SharedPrefsConstants.PATIENT)){
//            Intent intent = new Intent(getApplicationContext(), PatientMainActivity.class);
//            startActivity(intent);
//            finish();
//        } else if(isRegistered && profile != null && profile.equals(SharedPrefsConstants.RESPONSIBLE)){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            String phoneNumber = checkPhoneNumber();
//            WebServiceConnection webServiceConnection = WebServiceConnection.getInstance(getApplicationContext());
//            webServiceConnection.phoneNumberValidation(phoneNumber, new WebServiceConnection.CustomListener<String>(){
//
//                @Override
//                public void onSucces(String response) {
//
//                }
//
//                @Override
//                public void onError(VolleyError error) {
//
//                }
//            });
//        }
    }



    private String checkSmsCode(String sms) {
        if(sms != null){
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
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        return null;
    }
}
