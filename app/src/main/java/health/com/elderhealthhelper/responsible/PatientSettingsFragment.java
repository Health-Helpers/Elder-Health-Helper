package health.com.elderhealthhelper.responsible;

/**
 * Created by ivanjosa on 20/11/15.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import health.com.elderhealthhelper.R;

/**
 * Fragment that appears in the "content_frame", shows a patient
 */
public class PatientSettingsFragment extends Fragment {

    public static FragmentManager fragmentManager;

    public PatientSettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.responsible_patient_settings_fragment, container, false);

        return rootView;
    }
}