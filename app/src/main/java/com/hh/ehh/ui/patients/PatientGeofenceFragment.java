package com.hh.ehh.ui.patients;

import android.graphics.Color;
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
import com.hh.ehh.model.Patient;
import com.hh.ehh.utils.FragmentStackManager;

/**
 * Created by Ivan on 12/01/2016.
 */
public class PatientGeofenceFragment extends Fragment implements GoogleMap.OnMarkerDragListener {

    private FragmentStackManager fragmentStackManager;
    public static final String ARG_PATIENT_NUMBER = "patient_number";

    public GoogleMap googleMap;
    private int distance = 1000;
    MapView mMapView;
    double patientLatitude = 41.6081194;
    double patientLongitude = 0.6235039;
    double geofenceLatitude = 41.6081194;
    double geofenceLongitude = 0.6235039;
    public int radius;


    public static PatientGeofenceFragment newInstance(Patient patient) {
        PatientGeofenceFragment patientGeofenceFragment = new PatientGeofenceFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PATIENT_NUMBER, patient);
        patientGeofenceFragment.setArguments(bundle);
        return patientGeofenceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentStackManager = FragmentStackManager.getInstance(getActivity());

         View v = inflater.inflate(R.layout.patient_geofence_map, container, false);


        // create an object of GoogleMap and get the reference of map from the
        // xml layout file
        mMapView = (MapView) v.findViewById(R.id.geofence_map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // set OnMarkerDrag Listener
        googleMap.setOnMarkerDragListener(PatientGeofenceFragment.this);


        // move camera at specific location.
        // current location latitude and longitude can be provided here
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(patientLatitude, patientLongitude), distance > 1000 ? 17 : 13));

        // createGeofence location latitude and longitude and shape
        createGeofence(geofenceLatitude, geofenceLongitude, distance, "CIRCLE", "HBGEOFENCE");

        // TODO:  A l'aplicació del pacient, s'hauria de ficar la detecció de la geofence i enviar notificacions en entrades/sortides d'aquesta

        return v;
    }

    private void createGeofence(double latitude, double longitude, int radius, String geofenceType, String title){
        Marker stopMarker = googleMap.addMarker(new MarkerOptions().draggable(true).position(new LatLng(latitude, longitude)).title(title).infoWindowAnchor(10, 10).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ehh)));
        googleMap.addCircle(new CircleOptions().center(new LatLng(latitude, longitude)).radius(radius).strokeColor(Color.parseColor("#ffff00")).fillColor(Color.TRANSPARENT));
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
       // fragment.setRadius(this.radius);

        fragment.show(getFragmentManager(),"radius");

        LatLng dragPosition = marker.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        googleMap.clear();
        createGeofence(dragLat, dragLong, distance, "CIRCLE", "GEOFENCE");
    }

    @Override
    public void onMarkerDragStart(Marker marker){}
}
