package com.hh.ehh.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.hh.ehh.R;
import com.hh.ehh.bluetooth.BluetoothSPP;
import com.hh.ehh.responsible.MainActivity;


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

