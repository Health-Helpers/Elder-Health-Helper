package health.com.elderhealthhelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import health.com.elderhealthhelper.R;
import health.com.elderhealthhelper.model.Patient;

/**
 * Created by mpifa on 22/11/15.
 */
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
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.responsible_patient_list_item, parent, false);
            holder = new Holder();
            holder.name = (TextView) view.findViewById(R.id.simple_text_view);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }

        Patient patient = (Patient) this.getItem(position);

        holder.name.setText(patient.getNickName());
        return view;
    }

    private static class Holder{
        TextView name;
    }
}