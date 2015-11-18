package health.com.elderhealthhelper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;


import com.android.volley.VolleyError;

import health.com.elderhealthhelper.networking.WebServiceConnection;
import health.com.elderhealthhelper.sms.SmSReceiver;

public class LoginActivity extends AppCompatActivity {

    private TextView loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginStatus = (TextView) findViewById(R.id.login_status);
        String phoneNumber = checkPhoneNumber();
        WebServiceConnection webServiceConnection = WebServiceConnection.getInstance(getApplicationContext());
        webServiceConnection.phoneNumberValidation(phoneNumber, new WebServiceConnection.CustomListener<String>(){

            @Override
            public void onSucces(String response) {

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }



    private String checkSmsCode(String sms) {
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String sms = extras.getString(SmSReceiver.SMS);
        checkSmsCode(sms);
    }

    private String checkPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        return null;
    }
}
