package com.hh.ehh.responsible;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;
import com.hh.ehh.utils.FragmentStackManager;


public class ResponsibleSettingsFragment extends Fragment{

    protected FragmentStackManager fragmentStackManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.responsible_settings_fragment, container,false);

        Button btnNewPatient = (Button) view.findViewById(R.id.btnNewPatient);
        btnNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });


        Button btnChangeLocale = (Button) view.findViewById(R.id.btnChangeIdiom);
        btnChangeLocale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnChange = (Button) view.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);
//        updateUI();
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());

    }



}
