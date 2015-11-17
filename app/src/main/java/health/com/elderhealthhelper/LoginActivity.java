package health.com.elderhealthhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginStatus = (TextView) findViewById(R.id.login_status);
        String phoneNumber = checkPhoneNumber();
        String smsCode = checkSmsCode();
    }

    private String checkSmsCode() {
        return null;
    }

    private String checkPhoneNumber() {

        return null;
    }

    private interface processListenner{
        public Object onSuccess (Object object);
        public Object onFailure (Object object);
    }

}
