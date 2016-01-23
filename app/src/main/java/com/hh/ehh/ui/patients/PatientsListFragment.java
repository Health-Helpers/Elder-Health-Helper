package com.hh.ehh.ui.patients;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.Profile;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.utils.FragmentStackManager;
import com.hh.ehh.utils.xml.XMLHandler;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;


public class PatientsListFragment extends Fragment {

    public static final String ARG_PROFILE = "PROFILE";
    protected FragmentStackManager fragmentStackManager;
    private GetAllPatients getAllPatients;
    private Profile profile;
    private RecyclerView rv = null;
    private ParallaxRecyclerAdapter<Patient> parallaxRecyclerAdapter;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fab;


    public static PatientsListFragment newInstance(Profile profile) {
        PatientsListFragment patientsListFragment = new PatientsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PROFILE, profile);
        patientsListFragment.setArguments(bundle);
        return patientsListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_list_fragment, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        fab = (FloatingActionButton) view.findViewById(R.id.add_patient);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profile = getArguments().getParcelable(ARG_PROFILE);
        setHasOptionsMenu(true);
        getActivity().setTitle(getActivity().getResources().getString(R.string.app_name));
        populateUI(profile);
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        populateUI(profile);
                    }
                }
        );
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientRegister patientRegister = PatientRegister.newInstance(profile);
                fragmentStackManager.loadFragment(patientRegister, R.id.responsiblePatientFrame);
            }
        });
    }

    private void populateUI(Profile profile) {
        if (SoapWebServiceConnection.checkInternetConnection(getActivity())) {
            getAllPatients = new GetAllPatients();
            getAllPatients.execute(profile.getId());
        } else {
            CustomDialogs.noInternetConnection(getActivity()).show();
        }
    }

    private void fillAdapter(final List<Patient> patients) {
        parallaxRecyclerAdapter = new ParallaxRecyclerAdapter<Patient>(patients) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<Patient> parallaxRecyclerAdapter, int i) {
                Patient patient = parallaxRecyclerAdapter.getData().get(i);
                PatientHolder patientHolder = (PatientHolder) viewHolder;
                patientHolder.personName.setText(patient.getFullName());
                patientHolder.personDisease.setText(patient.getDiseases());
                patientHolder.personPhoto.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.profile));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<Patient> parallaxRecyclerAdapter, int i) {
                return new PatientHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_list_item_card, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<Patient> parallaxRecyclerAdapter) {
                return patients.size();
            }
        };
        parallaxRecyclerAdapter.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.patient_list_header_layout, rv, false), rv);
        parallaxRecyclerAdapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View view, int position) {
                Patient patient = parallaxRecyclerAdapter.getData().get(position);
                PatientFragment fragment = PatientFragment.newInstance(patient);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
            }
        });
        rv.setAdapter(parallaxRecyclerAdapter);
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
                populateUI(profile);
                break;
            default:
                break;
        }
        return true;
    }

    private class PatientHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personDisease;
        ImageView personPhoto;

        public PatientHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personDisease = (TextView) itemView.findViewById(R.id.person_disease);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }
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
            if (dialog != null)
                dialog.cancel();
            refreshLayout.setRefreshing(false);
        }
    }

}
