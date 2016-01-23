package com.hh.ehh.ui.patients;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hh.ehh.R;
import com.hh.ehh.model.Geofence;
import com.hh.ehh.model.Patient;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.utils.xml.XMLHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ivan on 12/01/2016.
 */
public class PatientGeofenceFragment extends Fragment implements GoogleMap.OnMarkerDragListener {

    public static final String ARG_PATIENT_NUMBER = "patient_number";
    public GoogleMap googleMap;
    public int radius;
    MapView mMapView;
    double patientLatitude = 41.6081194;
    double patientLongitude = 0.6235039;
    Patient patient;
    private ReadGeofence readGeofences;
    private int distance = 1000;

    public static PatientGeofenceFragment newInstance(Patient patient) {
        PatientGeofenceFragment patientGeofenceFragment = new PatientGeofenceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PATIENT_NUMBER, patient);
        patientGeofenceFragment.setArguments(bundle);
        return patientGeofenceFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.patient_geofence_map, container, false);

        final Patient patient = getArguments().getParcelable(ARG_PATIENT_NUMBER);
        this.patient = patient;

        readPatientGeofences(patient);
        mMapView = (MapView) v.findViewById(R.id.geofence_map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.setOnMarkerDragListener(PatientGeofenceFragment.this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(patientLatitude, patientLongitude), distance > 1000 ? 17 : 13));

        //FIXME: Si el paciente tiene más de una zona suscrita, mostrar el marcador
        addMarker();


        return v;
    }


    private void addMarker(){
        LatLng geofenceCenter = new LatLng(patientLatitude, patientLongitude);
        Marker stopMarker = googleMap.addMarker(new MarkerOptions().draggable(true).position(geofenceCenter).title("Actual").infoWindowAnchor(10, 10).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ehh)));

    }

    private void readPatientGeofences(Patient patient){
        readGeofences = new ReadGeofence();
        try {
            readGeofences.execute(patient).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void createGeofence(double latitude, double longitude, int radius, String geofenceType, String title){


        LatLng geofenceCenter = new LatLng(latitude, longitude);

        Marker stopMarker = googleMap.addMarker(new MarkerOptions().draggable(true).position(geofenceCenter).title(title).infoWindowAnchor(10, 10).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ehh)));


        googleMap.addCircle(new CircleOptions().center(geofenceCenter).radius(radius).strokeColor(Color.parseColor("#ffff00")).fillColor(Color.TRANSPARENT));
        this.radius = radius;
    }

    @Override
    public void onMarkerDrag(Marker marker){}

    @Override
    public void onMarkerDragEnd(Marker marker){

        final Patient patient = getArguments().getParcelable(ARG_PATIENT_NUMBER);
        PatientGeofenceDialogFragment fragment = PatientGeofenceDialogFragment.newInstance(patient);
        fragment.setLatidude(marker.getPosition().latitude);
        fragment.setLongitude(marker.getPosition().longitude);

        fragment.show(getFragmentManager(),"radius");

        LatLng dragPosition = marker.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        googleMap.clear();
        createGeofence(dragLat, dragLong, distance, "CIRCLE", "GEOFENCE");
    }

    @Override
    public void onMarkerDragStart(Marker marker){}

    private class ReadGeofence extends AsyncTask<Patient, Void, List<Geofence>> {

        Patient patient;
        private SoapWebServiceConnection soapWebServiceConnection;
        private Geofence geofence;
        private ProgressDialog dialog;


        public ReadGeofence(){}

        public ReadGeofence(SoapWebServiceConnection soapWebServiceConnection,Patient patient) {
            this.soapWebServiceConnection = soapWebServiceConnection;
            this.patient = patient;
        }

        @Override
        protected List<Geofence> doInBackground(Patient... params) {
            Patient patient = params[0];
            SoapWebServiceConnection connection = SoapWebServiceConnection.getInstance(getActivity());
            String rawXML = connection.getPatientGeofence(patient);
            List<Geofence> geofences = null;
            try {
                if (rawXML != null)
                    geofences = XMLHandler.getPatientGeofences(rawXML);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return geofences;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage(getActivity().getResources().getString(R.string.loading));
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    readGeofences.cancel(true);
                }
            });
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<Geofence> geofences) {
            super.onPostExecute(geofences);
            if(geofences!=null) {
                for (Geofence geofence : geofences) {
                    createGeofence(Double.parseDouble(geofence.getLatitude()), Double.parseDouble(geofence.getLongitude()),Integer.parseInt(geofence.getRadius()), "CIRCLE", geofence.getGeofenceId());
                }
            }

            if (dialog != null)
                dialog.cancel();
        }
    }
}
