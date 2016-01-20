package com.hh.ehh.ui.patients;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hh.ehh.R;
import com.hh.ehh.model.Patient;

/**
 * Created by ivanjosa on 15/1/16.
 */
public class PatientGeofenceDialogFragment extends DialogFragment {


    public static final String ARG_PATIENT_NUMBER = "patient_number";
    public double latidude;

    public double longitude;
    private EditText radiusInput;
    public int patientId;


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
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final Patient patient = getArguments().getParcelable(ARG_PATIENT_NUMBER);
        View rootView = inflater.inflate(R.layout.dialog_geofence_radius, null);
        radiusInput = (EditText) rootView.findViewById(R.id.geofence_radius);



        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                .setPositiveButton("Guardar", new DialogPositiveClickListener(latidude, longitude, patient))
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PatientGeofenceDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public double getLatidude() {
        return latidude;
    }

    public void setLatidude(double latidude) {
        this.latidude = latidude;
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

        public DialogPositiveClickListener(double longitude, double latitude,Patient patient) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.patient = patient;
        }


        @Override
        public void onClick(DialogInterface dialog, int id) {
            int radius = Integer.parseInt(radiusInput.getText().toString());
            Log.d("Log",String.valueOf(this.radius));
            Log.d("Log",String.valueOf(this.longitude));
            Log.d("Log",String.valueOf(this.latitude));
            Log.d("Log",String.valueOf(this.patient.getId()));

            //TODO: create webservice
        }
    }
}
