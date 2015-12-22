package com.hh.ehh.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.model.MedicalCenter;

import java.util.ArrayList;
import java.util.List;


public class MedicalCenterAdapter extends BaseAdapter {

    private List<MedicalCenter> medicalCenters;
    private Context context;

    public MedicalCenterAdapter(Context context) {
        this.context = context;
        this.medicalCenters = new ArrayList<>();
    }

    public MedicalCenterAdapter(Context context, List<MedicalCenter> list) {
        this.context = context;
        this.medicalCenters = list;
    }


    @Override
    public int getCount() {
        return medicalCenters.size();
    }

    @Override
    public Object getItem(int position) {
        return medicalCenters.get(position);
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

        MedicalCenter medicalCenter = (MedicalCenter) this.getItem(position);

        holder.name.setText(medicalCenter.getName());
        return view;
    }

    private static class MedicalCenterHolder{
        TextView name;
    }
}