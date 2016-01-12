package com.hh.ehh.ui.settings;

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
        View view = inflater.inflate(R.layout.settings_fragment, container,false);


        android.content.res.Resources res = view.getContext().getResources();
        String configStr = res.getString(R.string.configuration);
        getActivity().setTitle(configStr);

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
