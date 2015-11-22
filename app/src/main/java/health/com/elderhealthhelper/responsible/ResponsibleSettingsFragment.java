package health.com.elderhealthhelper.responsible;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import health.com.elderhealthhelper.R;
import health.com.elderhealthhelper.adapters.PatientAdapter;
import health.com.elderhealthhelper.model.Patient;
import health.com.elderhealthhelper.utils.FragmentStackManager;


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
