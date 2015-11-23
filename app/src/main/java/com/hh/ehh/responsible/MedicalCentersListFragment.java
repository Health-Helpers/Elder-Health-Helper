package com.hh.ehh.responsible;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hh.ehh.R;
import com.hh.ehh.adapters.MedicalCenterAdapter;
import com.hh.ehh.utils.FragmentStackManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanjosa on 23/11/15.
 */
public class MedicalCentersListFragment extends Fragment {

    protected FragmentStackManager fragmentStackManager;
    private ListView medicalCentersList;

    public MedicalCentersListFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.responsible_medical_centers_list_fragment, container,false);
        medicalCentersList = (ListView) view.findViewById(R.id.medicalCentersList);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);
//        updateUI();
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        medicalCentersList.setAdapter(fillAdapter());
       /* medicalCentersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = (Patient) medicalCentersList.getItemAtPosition(position);
                PatientFragment fragment = new PatientFragment();
                Bundle args = new Bundle();
                args.putString(PatientFragment.ARG_PATIENT_NUMBER, patient.getNickName());
                fragment.setArguments(args);
                fragmentStackManager.loadFragment(fragment,R.id.responsiblePatientFrame);
            }
        });*/
    }

    private ListAdapter fillAdapter() {
        List<String> medicalCenters = new ArrayList<>();
        medicalCenters.add("Medical Center 1");
        medicalCenters.add("Medical Center 2");
        medicalCenters.add("Medical Center 3");
        medicalCenters.add("Medical Center 4");
        medicalCenters.add("Medical Center 5");
         return new MedicalCenterAdapter(getActivity(),medicalCenters);
    }

}
