package com.hh.ehh.ui.medicalcenters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hh.ehh.R;
import com.hh.ehh.model.MedicalCenter;

/**
 * Created by mpifa on 21/12/15.
 */
public class MedicalCenterFragment extends Fragment {
    public static final String MEDICAL_CENTER_KEY = "MEDICAL_CENTER_KEY";
    private MapView mapView;
    private GoogleMap map;
    private MedicalCenter medicalCenter;
    private TextView name, phone, address,
            name_header, phone_header, address_header;

    public static MedicalCenterFragment newInstance(MedicalCenter medicalCenter) {
        MedicalCenterFragment medicalCenterFragment = new MedicalCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MEDICAL_CENTER_KEY, medicalCenter);
        medicalCenterFragment.setArguments(bundle);
        return medicalCenterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medical_center_fragment, container, false);
        name_header = (TextView) view.findViewById(R.id.name_header);
        name = (TextView) view.findViewById(R.id.name_value);
        phone_header = (TextView) view.findViewById(R.id.phone_header);
        phone = (TextView) view.findViewById(R.id.phone_value);
        address_header = (TextView) view.findViewById(R.id.address_header);
        address = (TextView) view.findViewById(R.id.address_value);
        if (mapView == null) {
            mapView = (MapView) view.findViewById(R.id.mapview);
            if (mapView == null) {
                Toast.makeText(getActivity(),
                        "Error creating map", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        medicalCenter = getArguments().getParcelable(MEDICAL_CENTER_KEY);
        name_header.setText(getActivity().getResources().getString(R.string.name_header));
        phone_header.setText(getActivity().getResources().getString(R.string.phone_header));
        address_header.setText(getActivity().getResources().getString(R.string.address_header));

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            map = mapView.getMap();
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            MapsInitializer.initialize(this.getActivity());
            if (medicalCenter != null && medicalCenter.getLatitude() != null && medicalCenter.getLongitude() != null &&
                    !medicalCenter.getLatitude().equals("null") && !medicalCenter.getLatitude().equals("null")) {
                map.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(Double.parseDouble(medicalCenter.getLatitude()),
                                        Double.parseDouble(medicalCenter.getLongitude())))
                        .title(medicalCenter.getName()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(
                        Double.parseDouble(medicalCenter.getLatitude()),
                        Double.parseDouble(medicalCenter.getLongitude())
                ), 13);
                map.animateCamera(cameraUpdate);
                name.setText(medicalCenter.getName());
                phone.setText(medicalCenter.getPhone());
                address.setText(medicalCenter.getAddress());
            }
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}

