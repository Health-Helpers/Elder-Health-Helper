package com.hh.ehh.ui.patients;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.Profile;
import com.hh.ehh.model.User;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.utils.FragmentStackManager;
import com.hh.ehh.utils.Validators;
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
                newPatientDialog(profile).show();
            }
        });
    }

    private void populateUI(Profile profile) {
        getAllPatients = new GetAllPatients();
        getAllPatients.execute(profile.getId());
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

    private Dialog newPatientDialog(final Profile profile) {
        Button addPatient;
        final Dialog patientDialog = new Dialog(getActivity());
        patientDialog.setTitle(getActivity().getResources().getString(R.string.add_patient));
        patientDialog.setContentView(R.layout.add_new_patient);
        addPatient = (Button) patientDialog.findViewById(R.id.btn_add);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields(patientDialog, getActivity(), profile);
            }
        });
        return patientDialog;
    }

    private void checkFields(Dialog view, Context context, Profile profile) {
        EditText dni, name, surnames, phone, address;
        TextInputLayout layout_dni, layout_name, layout_surnames, layout_phone, layout_address;
        dni = (EditText) view.findViewById(R.id.input_dni);
        name = (EditText) view.findViewById(R.id.input_name);
        surnames = (EditText) view.findViewById(R.id.input_surname);
        phone = (EditText) view.findViewById(R.id.input_phone);
        address = (EditText) view.findViewById(R.id.input_address);
        layout_dni = (TextInputLayout) view.findViewById(R.id.input_layout_dni);
        layout_name = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        layout_surnames = (TextInputLayout) view.findViewById(R.id.input_layout_surname);
        layout_phone = (TextInputLayout) view.findViewById(R.id.input_layout_phone);
        layout_address = (TextInputLayout) view.findViewById(R.id.input_layout_address);
        if (!Validators.dniValidator(dni.getText().toString())) {
            layout_dni.setError(context.getResources().getString(R.string.wrong_dni));
        } else if (name.getText().toString().length() == 0) {
            layout_name.setError(context.getResources().getString(R.string.wrong_name));
        } else if (surnames.getText().toString().length() == 0) {
            layout_surnames.setError(context.getResources().getString(R.string.wrong_surnames));
        } else if (phone.getText().toString().length() < 9) {
            layout_phone.setError(context.getResources().getString(R.string.wrong_phone));
        } else if (address.getText().toString().length() == 0) {
            layout_address.setError(context.getResources().getString(R.string.wrong_address));
        } else {
            layout_dni.setErrorEnabled(false);
            layout_name.setErrorEnabled(false);
            layout_surnames.setErrorEnabled(false);
            layout_phone.setErrorEnabled(false);
            layout_address.setErrorEnabled(false);
            User user = new User.UserBuilder()
                    .setId(dni.getText().toString())
                    .setName(name.getText().toString())
                    .setSurname(surnames.getText().toString())
                    .setPhone(phone.getText().toString())
                    .setAddress(address.getText().toString())
                    .build();
            SoapWebServiceConnection soapWebServiceConnection = SoapWebServiceConnection.getInstance(context);
            if (SoapWebServiceConnection.checkInternetConnection(getActivity()))
                soapWebServiceConnection.postPattient(profile, user);
        }
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
//          DELETE FOLLOWING MOCK LINES
//            List<Patient> patients = patientList;
//            patients.addAll(patientList);
//            patients.addAll(patientList);
            if (patientList != null)
                fillAdapter(patientList);
            dialog.cancel();
            refreshLayout.setRefreshing(false);
        }
    }

}
