package health.com.elderhealthhelper.sms;

/**
 * Created by mpifa on 18/11/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import health.com.elderhealthhelper.LoginActivity;
import health.com.elderhealthhelper.R;
public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS = "SMS";

    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;

        if (myBundle != null) {
            try {
                Object[] pdus = (Object[]) myBundle.get("pdus");
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    if (messages[i].getDisplayOriginatingAddress().equals(context.getResources().getString(R.string.phone_number))) {
                        Intent smsIntent = new Intent(context, LoginActivity.class);
                        smsIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        smsIntent.putExtra(SMS, messages[i].getMessageBody());
                        context.startActivity(smsIntent);
}
                }
            } catch (NullPointerException e) {

            }
//                strMessage += "SMS From: " + messages[i].getOriginatingAddress();
//                strMessage += " : ";
//                strMessage += messages[i].getMessageBody();
//                strMessage += "\n";
        }
    }
}

