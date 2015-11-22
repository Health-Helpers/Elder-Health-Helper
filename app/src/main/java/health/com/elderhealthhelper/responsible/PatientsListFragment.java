package health.com.elderhealthhelper.responsible;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import health.com.elderhealthhelper.R;
import health.com.elderhealthhelper.adapters.PatientAdapter;
import health.com.elderhealthhelper.model.Patient;
import health.com.elderhealthhelper.utils.FragmentStackManager;

/**
 * Created by mpifa on 22/11/15.
 */
public class PatientsListFragment extends Fragment{

    protected FragmentStackManager fragmentStackManager;
    private ListView patientsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.responsible_patient_list_fragment, container,false);
        patientsList = (ListView) view.findViewById(R.id.patientsList);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);
//        updateUI();
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        patientsList.setAdapter(fillAdapter());
        patientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = (Patient) patientsList.getItemAtPosition(position);
                PatientFragment fragment = new PatientFragment();
                Bundle args = new Bundle();
                args.putString(PatientFragment.ARG_PATIENT_NUMBER, patient.getNickName());
                fragment.setArguments(args);
                fragmentStackManager.loadFragment(fragment,R.id.responsiblePatientFrame);
            }
        });
    }

    private ListAdapter fillAdapter() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("1","John",null,null,null,null,null,null));
        patients.add(new Patient("2","Patient",null,null,null,null,null,null));
        patients.add(new Patient("3","Someone",null,null,null,null,null,null));
        patients.add(new Patient("4","Somebody",null,null,null,null,null,null));
        patients.add(new Patient("5","Nobody",null,null,null,null,null,null));
        patients.add(new Patient("6","Me :(",null,null,null,null,null,null));
        PatientAdapter patientAdapter = new PatientAdapter(getActivity(),patients);
        return patientAdapter;
    }

}
