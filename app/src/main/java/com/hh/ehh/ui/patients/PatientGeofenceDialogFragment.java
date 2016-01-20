package com.hh.ehh.ui.patients;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hh.ehh.R;
import com.hh.ehh.model.Geofence;
import com.hh.ehh.model.Patient;
import com.hh.ehh.networking.SoapWebServiceConnection;

import java.util.Date;

/**
 * Created by ivanjosa on 15/1/16.
 */
public class PatientGeofenceDialogFragment extends DialogFragment {


    public static final String ARG_PATIENT_NUMBER = "patient_number";
    public double latitude;

    public double longitude;
    private EditText radiusInput;
    public Patient patient;


    public static PatientGeofenceDialogFragment newInstance(Patient patient) {
        PatientGeofenceDialogFragment patientGeofenceDialogFragment = new PatientGeofenceDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PATIENT_NUMBER, patient);
        patientGeofenceDialogFragment.setArguments(bundle);
        return patientGeofenceDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //TODO: Read existent geofences


        final Patient patient = getArguments().getParcelable(ARG_PATIENT_NUMBER);
        this.patient = patient;
        View rootView = inflater.inflate(R.layout.dialog_geofence_radius, null);
        radiusInput = (EditText) rootView.findViewById(R.id.geofence_radius);

        builder.setView(rootView)
                .setPositiveButton("Guardar", new DialogPositiveClickListener(latitude, longitude, patient))
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PatientGeofenceDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatidude(double latidude) {
        this.latitude = latidude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    private class DialogPositiveClickListener implements DialogInterface.OnClickListener{

        int radius;
        double longitude;
        double latitude;
        Patient patient;

        public DialogPositiveClickListener(double latitude, double longitude,Patient patient) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.patient = patient;
        }


        @Override
        public void onClick(DialogInterface dialog, int id) {
            int radius = Integer.parseInt(radiusInput.getText().toString());
            this.radius = radius;
            Log.d("Log",String.valueOf(this.radius));
            Log.d("Log",String.valueOf(this.longitude));
            Log.d("Log",String.valueOf(this.latitude));
            Log.d("Log",String.valueOf(this.patient.getId()));

            Geofence geofenceObject = new Geofence(this.patient.getId(),String.valueOf(id),String.valueOf(this.radius),String.valueOf(this.latitude),String.valueOf(this.longitude));

            //Send data to webservices
            sendGeofenceDataToWebsite(geofenceObject);
        }
    }

    private void sendGeofenceDataToWebsite(Geofence geofence) {
        if (SoapWebServiceConnection.checkInternetConnection(getActivity())) {
            SoapWebServiceConnection soapWebServiceConnection = SoapWebServiceConnection.getInstance(getActivity());

            SaveGeofence postGeofence = new SaveGeofence(soapWebServiceConnection,geofence,this.patient);
            postGeofence.execute(geofence);
         }
    }

    private class SaveGeofence extends AsyncTask<Geofence, Void, Void> {

        private SoapWebServiceConnection soapWebServiceConnection;
        private Geofence geofence;
        private ProgressDialog dialog;
        Patient patient;


        public SaveGeofence(SoapWebServiceConnection soapWebServiceConnection, Geofence geofence,Patient patient) {
            this.soapWebServiceConnection = soapWebServiceConnection;
            this.geofence = geofence;
            this.patient = patient;
        }

        @Override
        protected Void doInBackground(Geofence... params) {
            Geofence geofence = params[0];
            soapWebServiceConnection.addPatientGeofence(geofence,patient);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog != null)
                dialog.cancel();
        }
    }
}
