package com.hh.ehh.responsible;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hh.ehh.R;
import com.hh.ehh.utils.FragmentStackManager;


public class ResponsibleSettingsFragment extends Fragment{

    protected FragmentStackManager fragmentStackManager;
    private Button btnNewPatient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.responsible_settings_fragment, container,false);
        btnNewPatient = (Button) view.findViewById(R.id.btnNewPatient);
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
