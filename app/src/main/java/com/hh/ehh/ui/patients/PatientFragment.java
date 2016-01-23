package com.hh.ehh.ui.patients;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.model.Patient;
import com.hh.ehh.utils.FragmentStackManager;

public class PatientFragment extends Fragment {
    public static final String ARG_PATIENT_NUMBER = "patient_number";
    private FragmentStackManager fragmentStackManager;
    private Button locatePatientBtn;
    private Button callPatientBtn;
    private Button patientSettingsBtn;
    private TextView nameHeader, name, birthdateHeader, birthdate, phoneHeader, phone, addressHeader, address;

    public static PatientFragment newInstance(Patient patient) {
        PatientFragment patientFragment = new PatientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PATIENT_NUMBER, patient);
        patientFragment.setArguments(bundle);
        return patientFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_fragment, container, false);
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        locatePatientBtn = (Button) rootView.findViewById(R.id.localizePatientBtn);
        callPatientBtn = (Button) rootView.findViewById(R.id.responsibleCallBtn);
        patientSettingsBtn = (Button) rootView.findViewById(R.id.patientSettingsBtn);
        nameHeader = (TextView) rootView.findViewById(R.id.name_header);
        name = (TextView) rootView.findViewById(R.id.name_value);
        birthdateHeader = (TextView) rootView.findViewById(R.id.birthdate_header);
        birthdate = (TextView) rootView.findViewById(R.id.birthdate_value);
        phoneHeader = (TextView) rootView.findViewById(R.id.phone_header);
        phone = (TextView) rootView.findViewById(R.id.phone_value);
        addressHeader = (TextView) rootView.findViewById(R.id.address_header);
        address = (TextView) rootView.findViewById(R.id.address_value);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Patient patient = getArguments().getParcelable(ARG_PATIENT_NUMBER);
        Resources res = getActivity().getResources();
        nameHeader.setText(res.getString(R.string.name_header));
        birthdateHeader.setText(res.getString(R.string.birthdate_header));
        phoneHeader.setText(res.getString(R.string.phone_header));
        addressHeader.setText(res.getString(R.string.address_header));
        if (patient != null) {
            updateUI(patient);
        }
        patientSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = PatientSettingsFragment.newInstance(patient);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);

            }
        });
        callPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patient != null && patient.getPhone() != null) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + patient.getPhone()));
                    startActivity(intent);
                }
            }
        });
        locatePatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientMapFragment fragment = PatientMapFragment.newInstance(patient);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
            }
        });

    }

    private void updateUI(Patient patient) {
        name.setText(patient.getFullName());
        birthdate.setText(patient.getBirthDate());
        phone.setText(patient.getPhone());
        address.setText(patient.getAddress());
        getActivity().setTitle(patient.getName());
    }
}