package health.com.elderhealthhelper.responsible;

/**
 * Created by ivanjosa on 20/11/15.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import health.com.elderhealthhelper.R;

/**
 * Fragment that appears in the "content_frame", shows a patient
 */
public class PatientFragment extends Fragment {
    public static final String ARG_PATIENT_NUMBER = "patient_number";
    public static FragmentManager fragmentManager;

    public PatientFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.responsible_patient_fragment, container, false);
        int i = getArguments().getInt(ARG_PATIENT_NUMBER);
        String patient = getResources().getStringArray(R.array.patients_array)[i];


        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getFragmentManager();

        Button locatePatientBtn = (Button) rootView.findViewById(R.id.localizePatientBtn);
        locatePatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                Fragment fragment = new PatientMapFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                Toast.makeText(context, "Locate Pressed", Toast.LENGTH_SHORT).show();

            }
        });


        getActivity().setTitle(patient);
        return rootView;
    }
}