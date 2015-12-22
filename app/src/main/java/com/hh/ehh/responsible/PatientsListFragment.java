package com.hh.ehh.responsible;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hh.ehh.R;
import com.hh.ehh.adapters.PatientAdapter;
import com.hh.ehh.model.Patient;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.utils.FragmentStackManager;
import com.hh.ehh.utils.xml.XMLHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;


public class PatientsListFragment extends Fragment {

    protected FragmentStackManager fragmentStackManager;
    private ListView patientsList;
    private GetAllPatients getAllPatients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.responsible_patient_list_fragment, container, false);
        patientsList = (ListView) view.findViewById(R.id.patientsList);
        getActivity().setTitle(getResources().getString(R.string.patientsList));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);
        getActivity().setTitle(getActivity().getResources().getString(R.string.app_name));
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        getAllPatients = new GetAllPatients();
        getAllPatients.execute("1");
        patientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient patient = (Patient) patientsList.getItemAtPosition(position);
                PatientFragment fragment = PatientFragment.newInstance(patient);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
            }
        });
    }

    private void fillAdapter(List<Patient> patients) {
        patientsList.setAdapter(new PatientAdapter(getActivity(), patients));
    }


    private class GetAllPatients extends AsyncTask<String, Void, List<Patient>> {
        private ProgressDialog dialog;


        public GetAllPatients() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getActivity().getResources().getString(R.string.loading));
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getAllPatients.cancel(true);
                }
            });
            dialog.show();

        }

        @Override
        protected List<Patient> doInBackground(String[] params) {
            SoapWebServiceConnection connection = SoapWebServiceConnection.getInstance(getActivity());
            String rawXML = connection.getResponsiblePatients(params[0]);
            List<Patient> patientList = null;
            try {
                if (rawXML != null)
                    patientList = XMLHandler.getResponsiblePatients(rawXML);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return patientList;
        }

        @Override
        protected void onPostExecute(List<Patient> patientList) {
            super.onPostExecute(patientList);
            if (patientList != null)
                fillAdapter(patientList);
            dialog.cancel();
        }
    }

}
