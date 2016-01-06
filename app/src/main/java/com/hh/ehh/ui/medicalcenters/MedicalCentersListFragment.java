package com.hh.ehh.ui.medicalcenters;

import android.app.ProgressDialog;
import android.os.Bundle;
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

import com.android.volley.VolleyError;
import com.hh.ehh.R;
import com.hh.ehh.model.MedicalCenter;
import com.hh.ehh.networking.RestWebServiceConnection;
import com.hh.ehh.utils.FragmentStackManager;
import com.hh.ehh.utils.json.JSONParser;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import org.json.JSONException;

import java.util.List;

/**
 * Created by ivanjosa on 23/11/15.
 */
public class MedicalCentersListFragment extends Fragment {

    protected FragmentStackManager fragmentStackManager;
    private ProgressDialog pDialog;
    private RecyclerView rv = null;
    private ParallaxRecyclerAdapter<MedicalCenter> parallaxRecyclerAdapter;
    private SwipeRefreshLayout refreshLayout;

    public MedicalCentersListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medical_centers_list_fragment, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(getActivity().getResources().getString(R.string.app_name));
        populateUI();
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        populateUI();
                    }
                }
        );
    }

    private void fillAdapter(final List<MedicalCenter> medicalCenters) {
        parallaxRecyclerAdapter = new ParallaxRecyclerAdapter<MedicalCenter>(medicalCenters) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<MedicalCenter> parallaxRecyclerAdapter, int i) {
                MedicalCenter medicalCenter = parallaxRecyclerAdapter.getData().get(i);
                MedicalCenterHolder patientHolder = (MedicalCenterHolder) viewHolder;
                patientHolder.medicalCenterName.setText(medicalCenter.getName());
                patientHolder.medicalCenterPhoto.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.hospital_icon));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<MedicalCenter> parallaxRecyclerAdapter, int i) {
                return new MedicalCenterHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medical_center_list_item, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<MedicalCenter> parallaxRecyclerAdapter) {
                return medicalCenters.size();
            }
        };
        parallaxRecyclerAdapter.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.medical_center_list_header_layout, rv, false), rv);
        parallaxRecyclerAdapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View view, int position) {
                MedicalCenter medicalCenter = parallaxRecyclerAdapter.getData().get(position);
                MedicalCenterFragment fragment = MedicalCenterFragment.newInstance(medicalCenter);
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);
            }
        });
        rv.setAdapter(parallaxRecyclerAdapter);    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.update_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                populateUI();
                break;
            default:
                break;
        }
        return true;
    }

    private void populateUI() {
        RestWebServiceConnection connection = RestWebServiceConnection.getInstance(getActivity());
        connection.getMedicalCentersList(new RestWebServiceConnection.CustomListener<String>() {
            @Override
            public void onSucces(String response) {
                List<MedicalCenter> medicalCenters = null;
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage(getActivity().getResources().getString(R.string.loading));
                pDialog.show();
                try {
                    medicalCenters = JSONParser.getAllMedicalCentersFromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fillAdapter(medicalCenters);
                if (pDialog != null)
                    pDialog.dismiss();
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private class MedicalCenterHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView medicalCenterName;
        ImageView medicalCenterPhoto;

        public MedicalCenterHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            medicalCenterName = (TextView) itemView.findViewById(R.id.medical_center_name);
            medicalCenterPhoto = (ImageView) itemView.findViewById(R.id.medical_center_image);
        }
    }
}
