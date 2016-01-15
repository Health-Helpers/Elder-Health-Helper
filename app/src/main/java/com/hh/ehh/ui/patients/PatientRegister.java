package com.hh.ehh.ui.patients;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hh.ehh.R;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.Profile;
import com.hh.ehh.model.User;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.utils.Validators;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mpifa on 15/1/16.
 */
public class PatientRegister extends Fragment {
    public static final String ARG_PROFILE_NUMBER = "profile";
    private EditText dni, name, surnames, phone, address, diseases;
    private EditText birthday;
    private TextInputLayout layout_dni, layout_name, layout_surnames, layout_phone, layout_address, layout_diseases, layout_birthday;
    private PostPatient postPatient;
    private RadioGroup dependencyGrade;
    private RadioButton radioButton;
    private Button sendPatient;

    public static PatientRegister newInstance(Profile profile) {
        PatientRegister patientRegister = new PatientRegister();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PROFILE_NUMBER, profile);
        patientRegister.setArguments(bundle);
        return patientRegister;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_patient, container, false);
        dni = (EditText) rootView.findViewById(R.id.input_dni);
        name = (EditText) rootView.findViewById(R.id.input_name);
        surnames = (EditText) rootView.findViewById(R.id.input_surname);
        phone = (EditText) rootView.findViewById(R.id.input_phone);
        address = (EditText) rootView.findViewById(R.id.input_address);
        diseases = (EditText) rootView.findViewById(R.id.input_disease);
        dependencyGrade = (RadioGroup) rootView.findViewById(R.id.dependency_grade);
        birthday = (EditText) rootView.findViewById(R.id.input_birthday);
        sendPatient = (Button) rootView.findViewById(R.id.btn_add);

        layout_dni = (TextInputLayout) rootView.findViewById(R.id.input_layout_dni);
        layout_name = (TextInputLayout) rootView.findViewById(R.id.input_layout_name);
        layout_surnames = (TextInputLayout) rootView.findViewById(R.id.input_layout_surname);
        layout_phone = (TextInputLayout) rootView.findViewById(R.id.input_layout_phone);
        layout_address = (TextInputLayout) rootView.findViewById(R.id.input_layout_address);
        layout_diseases = (TextInputLayout) rootView.findViewById(R.id.input_layout_disease);
        layout_birthday = (TextInputLayout) rootView.findViewById(R.id.input_layout_birthday);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Profile profile = getArguments().getParcelable(ARG_PROFILE_NUMBER);
        dependencyGrade.check(R.id.radio_low);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(birthday);
            }
        });
        sendPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getActivity().getResources();
                if (!Validators.dniValidator(dni.getText().toString())) {
                    layout_dni.setError(res.getString(R.string.wrong_dni));
                } else if (name.getText().toString().length() == 0) {
                    layout_name.setError(res.getString(R.string.wrong_name));
                } else if (surnames.getText().toString().length() == 0) {
                    layout_surnames.setError(res.getString(R.string.wrong_surnames));
                } else if (birthday.getText().toString().length() == 0) {
                    layout_birthday.setError(res.getString(R.string.wrong_birthday));
                } else if (phone.getText().toString().length() < 9) {
                    layout_phone.setError(res.getString(R.string.wrong_phone));
                } else if (address.getText().toString().length() == 0) {
                    layout_address.setError(res.getString(R.string.wrong_address));
                } else if (diseases.getText().toString().length() == 0) {
                    layout_diseases.setError(res.getString(R.string.wrong_disease));
                } else {
                    layout_dni.setErrorEnabled(false);
                    layout_name.setErrorEnabled(false);
                    layout_surnames.setErrorEnabled(false);
                    layout_phone.setErrorEnabled(false);
                    layout_address.setErrorEnabled(false);
                    layout_diseases.setErrorEnabled(false);
                    layout_birthday.setErrorEnabled(false);
                    Patient patient = new Patient(new User.UserBuilder()
                            .setIdDoc(dni.getText().toString())
                            .setName(name.getText().toString())
                            .setSurname(surnames.getText().toString())
                            .setPhone(phone.getText().toString())
                            .setAddress(address.getText().toString())
                            .setBirthdate(birthday.getText().toString())
                            .build());
                    patient.setDiseases(diseases.getText().toString());
                    switch (dependencyGrade.getCheckedRadioButtonId()) {
                        case R.id.radio_low:
                            patient.setDependencyGrade("1");
                            break;
                        case R.id.radio_medium:
                            patient.setDependencyGrade("2");
                            break;
                        case R.id.radio_high:
                            patient.setDependencyGrade("3");
                            break;
                    }
                    if (SoapWebServiceConnection.checkInternetConnection(getActivity())) {
                        SoapWebServiceConnection soapWebServiceConnection = SoapWebServiceConnection.getInstance(getActivity());
                        postPatient = new PostPatient(soapWebServiceConnection, profile);
                        postPatient.execute(patient);
                    }
                }
            }
        });


    }

    private void showCalendar(final EditText birthday) {
        final Calendar c = Calendar.getInstance(Locale.ENGLISH);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String date = Integer.toString(dayOfMonth) + "/" + Integer.toString(monthOfYear + 1) + "/" + Integer.toString(year);
                int yearNow = c.get(Calendar.YEAR);
                int monthNow = c.get(Calendar.MONTH);
                int dayNow = c.get(Calendar.DAY_OF_MONTH);
                if (year > yearNow || monthOfYear > monthNow || dayOfMonth > dayNow) {
                    CustomDialogs.wrongDateDialog(getActivity()).show();
                } else {
                    birthday.setText(date);
                }
            }
        }, year, month, day).show();
    }

    private class PostPatient extends AsyncTask<Patient, Void, Void> {

        private SoapWebServiceConnection soapWebServiceConnection;
        private Profile profile;
        private ProgressDialog dialog;


        public PostPatient(SoapWebServiceConnection soapWebServiceConnection, Profile profile) {
            this.soapWebServiceConnection = soapWebServiceConnection;
            this.profile = profile;
        }

        @Override
        protected Void doInBackground(Patient... params) {
            Patient patient = params[0];
            soapWebServiceConnection.postPatient(profile, patient);
            return null;
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
                    postPatient.cancel(true);
                }
            });
            dialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog != null)
                dialog.cancel();
        }
    }
}