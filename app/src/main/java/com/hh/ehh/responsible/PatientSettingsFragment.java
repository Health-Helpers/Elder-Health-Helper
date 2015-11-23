package com.hh.ehh.responsible;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return inflater.inflate(R.layout.responsible_patient_settings_fragment, container, false);
    }
}