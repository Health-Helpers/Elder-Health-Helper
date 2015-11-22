package health.com.elderhealthhelper.patient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import health.com.elderhealthhelper.R;
import health.com.elderhealthhelper.responsible.MainActivity;
import health.com.elderhealthhelper.responsible.PatientFragment;
import health.com.elderhealthhelper.responsible.PatientSettingsFragment;

/**
 * Created by Carolina on 21/11/2015.
 */
public class PatientWelcome  extends Activity {

    private RadioButton rbPaciente;
    private RadioButton rbResponsable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_welcome);

        rbPaciente = (RadioButton)findViewById(R.id.rbPaciente);
        rbResponsable = (RadioButton)findViewById(R.id.rbResponsable);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Intent intents = null;

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbResponsable:
                if (checked)
                    intents = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intents);
                    finish();
                    break;
            case R.id.rbPaciente:
                if (checked)
                    intents = new Intent(getApplicationContext(), PatientMainActivity.class);
                    startActivity(intents);
                    finish();
                    break;
        }
    }

}

