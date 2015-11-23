package com.hh.ehh.patient;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;
import com.hh.ehh.bluetooth.Bluetooth;

import java.util.ArrayList;

/**
 * Created by mpifa on 23/11/15.
 */
public class PatientHomeFragment extends Fragment implements Bluetooth.BluetoothListener{

    private Button btnBluetooth;

    private static final String TAG = "FindBluetooth";
    private Bluetooth bluetooth;
    private ArrayList<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_activity_main, container, false);
        btnBluetooth = (Button) view.findViewById(R.id.btnBluetooth);

        try {
            bluetooth = Bluetooth.startFindDevices(getActivity(), this);
        } catch (Exception e) {
            Log.e(TAG, "Error: ", e);
        }

        arrayAdapter = new ArrayList<String>();



        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                for (String name : arrayAdapter) {
                    Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
