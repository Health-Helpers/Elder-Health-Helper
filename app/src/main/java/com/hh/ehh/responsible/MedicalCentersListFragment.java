package com.hh.ehh.responsible;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.hh.ehh.R;
import com.hh.ehh.adapters.MedicalCenterAdapter;
import com.hh.ehh.model.MedicalCenter;
import com.hh.ehh.networking.RestWebServiceConnection;
import com.hh.ehh.utils.FragmentStackManager;
import com.hh.ehh.utils.json.JSONParser;

import org.json.JSONException;

import java.util.List;

/**
 * Created by ivanjosa on 23/11/15.
 */
public class MedicalCentersListFragment extends Fragment {

    protected FragmentStackManager fragmentStackManager;
    private ListView medicalCentersList;
    private ProgressDialog pDialog;

    public MedicalCentersListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.responsible_medical_centers_list_fragment, container, false);
        medicalCentersList = (ListView) view.findViewById(R.id.medicalCentersList);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        populateUI();
        medicalCentersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicalCenter medicalCenter = (MedicalCenter) medicalCentersList.getItemAtPosition(position);
                MedicalCenterFragment fragment = MedicalCenterFragment.newInstance(medicalCenter);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
            }
        });
    }

    private void fillAdapter(List<MedicalCenter> medicalCenters) {
        medicalCentersList.setAdapter(new MedicalCenterAdapter(getActivity(), medicalCenters));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.update_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                populateUI();
                break;
            default:
                break;
        }
        return true;
    }

    private void populateUI() {
        RestWebServiceConnection connection = RestWebServiceConnection.getInstance(getActivity());
        connection.getMedicalCentersList(new RestWebServiceConnection.CustomListener<String>() {
            @Override
            public void onSucces(String response) {
                List<MedicalCenter> medicalCenters = null;
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                pDialog.show();
                try {
                    medicalCenters = JSONParser.getAllMedicalCentersFromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (medicalCenters != null && !medicalCenters.isEmpty())
                    fillAdapter(medicalCenters);
                if(pDialog!=null)
                    pDialog.dismiss();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

}
