package health.com.elderhealthhelper.patient;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import health.com.elderhealthhelper.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity_main);

        Button pairButton = (Button) findViewById(R.id.pairBtn);
        Button callButton = (Button) findViewById(R.id.callBtn);
        Button settingsButton = (Button) findViewById(R.id.settingsBtn);

        pairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context =  v.getContext();


                Toast.makeText(context,"Pair Pressed",Toast.LENGTH_SHORT).show();

            }
        });

    }



}
