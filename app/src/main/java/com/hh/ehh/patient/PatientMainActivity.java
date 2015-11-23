package com.hh.ehh.patient;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;
import com.hh.ehh.bluetooth.Bluetooth;

import java.util.ArrayList;
import java.util.List;

public class PatientMainActivity extends AppCompatActivity implements Bluetooth.BluetoothListener{

    private static final String TAG = "FindBluetooth";
    private Bluetooth bluetooth;
    private ArrayList<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity_main);

        try {
            bluetooth = Bluetooth.startFindDevices(this, this);
        } catch (Exception e) {
            Log.e(TAG, "Error: ", e);
        }

        arrayAdapter = new ArrayList<String>();

        Button btnBluetooth = (Button) findViewById(R.id.btnBluetooth);

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                for (String name: arrayAdapter) {
                    Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void action(String action) {

        if (action.compareTo(ACTION_DISCOVERY_STARTED) == 0) {
            arrayAdapter.add("-Started Find");
        } else if (action.compareTo(ACTION_DISCOVERY_FINISHED) == 0) {
            preencherLista();
            arrayAdapter.add("-Finished Find");
        }
    }

    private void preencherLista() {
        for (BluetoothDevice device : bluetooth.getDispositivos()) {
            arrayAdapter.add(device.getName() + "\n" + device.getAddress());
        }
    }
}
