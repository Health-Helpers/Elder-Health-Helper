package com.hh.ehh.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hh.ehh.R;

import java.util.ArrayList;
import java.util.List;


public class MedicalCenterAdapter extends BaseAdapter {

    private List<String> patients;
    private Context context;

    public MedicalCenterAdapter(Context context) {
        this.context = context;
        this.patients = new ArrayList<>();
    }

    public MedicalCenterAdapter(Context context, List<String> list) {
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
        MedicalCenterHolder holder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.responsible_medical_center_list_item, parent, false);
            holder = new MedicalCenterHolder();
            holder.name = (TextView) view.findViewById(R.id.text_view_medical_center);
            view.setTag(holder);
        }else {
            holder = (MedicalCenterHolder) view.getTag();
        }

        String medicalCenter = (String) this.getItem(position);

        holder.name.setText(medicalCenter);
        return view;
    }

    private static class MedicalCenterHolder{
        TextView name;
    }
}