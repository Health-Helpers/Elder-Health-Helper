package com.hh.ehh.ui.patients;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;
import com.hh.ehh.utils.FragmentStackManager;

/**
 * Fragment that appears in the "content_frame", shows a patient
 */
public class PatientSettingsFragment extends Fragment {

    private FragmentStackManager fragmentStackManager;

    public PatientSettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        View v = inflater.inflate(R.layout.patient_settings_fragment, container, false);

        Button patientAlertsBtn = (Button) v.findViewById(R.id.patientAlertsBtn);
        //TODO: Obrir un formulari per especificar els metres de radi de la geofence
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
                Fragment fragment = new PatientGeofenceFragment();
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
            }
        });


        return v;
    }
}