package com.hh.ehh.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.model.Patient;

import java.util.ArrayList;
import java.util.List;


public class PatientAdapter extends BaseAdapter {

    private List<Patient> patients;
    private Context context;

    public PatientAdapter(Context context) {
        this.context = context;
        this.patients = new ArrayList<>();
    }

    public PatientAdapter(Context context, List<Patient> list) {
        this.context = context;
        this.patients = list;
    }


    @Override
    public int getCount() {
        return patients.size();
    }

    @Override
    public Object getItem(int position) {
        return patients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.responsible_patient_list_item, parent, false);
            holder = new Holder();
            holder.name = (TextView) view.findViewById(R.id.patient_name);
            holder.avatar = (ImageView) view.findViewById(R.id.avatar);
            holder.disease = (TextView) view.findViewById(R.id.disease);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        Patient patient = (Patient) this.getItem(position);
        holder.name.setText(patient.getName().concat(" ").concat(patient.getSurname()));
        holder.disease.setText(patient.getDiseases());
        holder.avatar.setImageDrawable((patient.getImageAsDrawable() == null ?
                ContextCompat.getDrawable(context, R.drawable.profile) : patient.getImageAsDrawable()));
        return view;
    }

    private static class Holder {
        TextView name,disease;
        ImageView avatar;
    }
}