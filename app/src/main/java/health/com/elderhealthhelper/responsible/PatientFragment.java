package health.com.elderhealthhelper.responsible;

/**
 * Created by ivanjosa on 20/11/15.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import health.com.elderhealthhelper.R;
import health.com.elderhealthhelper.utils.FragmentStackManager;

/**
 * Fragment that appears in the "content_frame", shows a patient
 */
public class PatientFragment extends Fragment {
    public static final String ARG_PATIENT_NUMBER = "patient_number";
    private FragmentStackManager fragmentStackManager;

    public PatientFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.responsible_patient_fragment, container, false);
        int i = getArguments().getInt(ARG_PATIENT_NUMBER);
        String patient = getResources().getStringArray(R.array.patients_array)[i];


        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentStackManager = FragmentStackManager.getInstance(getActivity());

        Button locatePatientBtn = (Button) rootView.findViewById(R.id.localizePatientBtn);
        locatePatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                Fragment fragment = new PatientMapFragment();
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);

            }
        });

        Button callPatientBtn = (Button) rootView.findViewById(R.id.responsibleCallBtn);
        callPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Context context = v.getContext();

               /*  Fragment fragment = new PatientMapFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("responsible.PatientMainActivity").commit();
*/
                Toast.makeText(context, "Ring Ring!!", Toast.LENGTH_SHORT).show();
            }
        });

        Button patientSettingsBtn = (Button) rootView.findViewById(R.id.patientSettingsBtn);
        patientSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Context context = v.getContext();

                Fragment fragment = new PatientSettingsFragment();
                fragmentStackManager.loadFragment(fragment, R.id.responsiblePatientFrame);

            }
        });

        getActivity().setTitle(patient);
        return rootView;
    }
}