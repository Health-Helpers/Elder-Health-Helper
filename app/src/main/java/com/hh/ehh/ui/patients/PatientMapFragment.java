package com.hh.ehh.ui.patients;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hh.ehh.R;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.Position;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.utils.xml.XMLHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class PatientMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String ARG_PATIENT = "PATIENT";
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private GetPatientLocation getPatientLocation;
    private Patient patient;
    private SoapWebServiceConnection soapWebServiceConnection;

    public static PatientMapFragment newInstance(Patient patient) {
        PatientMapFragment patientMapFragment = new PatientMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PATIENT, patient);
        patientMapFragment.setArguments(bundle);
        return patientMapFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.patient_map, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        patient = getArguments().getParcelable(ARG_PATIENT);
        soapWebServiceConnection = SoapWebServiceConnection.getInstance(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        if (SoapWebServiceConnection.checkInternetConnection(getActivity())) {
            getPatientLocation = new GetPatientLocation(googleMap, patient, soapWebServiceConnection);
            getPatientLocation.execute();
        } else {
            CustomDialogs.noInternetConnection(getActivity()).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class GetPatientLocation extends AsyncTask<Void, Void, Position> {
        private GoogleMap map;
        private ProgressDialog dialog;
        private Patient patient;
        private SoapWebServiceConnection soapWebServiceConnection;

        public GetPatientLocation(GoogleMap map, Patient patient, SoapWebServiceConnection soapWebServiceConnection) {
            this.map = map;
            this.patient = patient;
            this.soapWebServiceConnection = soapWebServiceConnection;
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
                    getPatientLocation.cancel(true);
                }
            });
            dialog.show();
        }

        @Override
        protected Position doInBackground(Void... params) {
            String rawResponse = soapWebServiceConnection.getPatientLocation(patient.getId());
            Position location = null;
            try {
                if (rawResponse != null)
                    location = XMLHandler.getPatientLocation(rawResponse);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return location;
        }

        @Override
        protected void onPostExecute(Position location) {
            super.onPostExecute(location);
            if (location != null) {
                String description = getActivity().getResources().getString(R.string.last_seen);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location.getLatLng(), 17));
                MarkerOptions marker = new MarkerOptions().position(
                        location.getLatLng()).title(patient.getFullName()).snippet(description + location.getDate());
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                map.addMarker(marker);
            }
            if (dialog != null) {
                dialog.dismiss();
            }
        }

        @Override
        protected void onCancelled(Position location) {
            super.onCancelled(location);
        }
    }
}