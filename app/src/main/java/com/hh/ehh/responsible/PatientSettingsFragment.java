package com.hh.ehh.responsible;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;

/**
 * Fragment that appears in the "content_frame", shows a patient
 */
public class PatientSettingsFragment extends Fragment {

    public PatientSettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.responsible_patient_settings_fragment, container, false);

        Button patientAlertsBtn = (Button) v.findViewById(R.id.patientAlertsBtn);
        patientAlertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });

        Button secureZoneBtn = (Button) v.findViewById(R.id.secureZoneBtn);
        secureZoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });

        Button patientSettingsNameBtn = (Button) v.findViewById(R.id.patientSettingsNameBtn);
        patientSettingsNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}