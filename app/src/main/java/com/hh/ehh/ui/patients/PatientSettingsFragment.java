package com.hh.ehh.ui.patients;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;
import com.hh.ehh.model.Patient;
import com.hh.ehh.utils.FragmentStackManager;

/**
 * Fragment that appears in the "content_frame", shows a patient
 */
public class PatientSettingsFragment extends Fragment {

    public static final String ARG_PATIENT_NUMBER = "patient_number";
    private FragmentStackManager fragmentStackManager;
    private Button patientAlertsBtn,secureZoneBtn;


    public static PatientSettingsFragment newInstance(Patient patient) {
        PatientSettingsFragment patientSettingsFragment = new PatientSettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PATIENT_NUMBER, patient);
        patientSettingsFragment.setArguments(bundle);
        return patientSettingsFragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Patient patient = getArguments().getParcelable(ARG_PATIENT_NUMBER);

        patientAlertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });

        secureZoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = PatientGeofenceFragment.newInstance(patient);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.patient_settings_fragment, container, false);

        fragmentStackManager = FragmentStackManager.getInstance(getActivity());

        patientAlertsBtn = (Button) v.findViewById(R.id.patientAlertsBtn);
        secureZoneBtn = (Button) v.findViewById(R.id.secureZoneBtn);

        return v;
    }
}