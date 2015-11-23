package com.hh.ehh.patient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;

/**
 * Created by mpifa on 23/11/15.
 */
public class PatientHomeFragment extends Fragment {

    private Button pairButton;
    private Button callButton;
    private Button settingsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_activity_main, container, false);
        pairButton = (Button) view.findViewById(R.id.btnNuevoResponsable);
        callButton = (Button) view.findViewById(R.id.callBtn);
        settingsButton = (Button) view.findViewById(R.id.settingsBtn);

        pairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, "Pair Pressed", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
