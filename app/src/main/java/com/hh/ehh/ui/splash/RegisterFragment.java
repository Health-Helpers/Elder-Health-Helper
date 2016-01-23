package com.hh.ehh.ui.splash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hh.ehh.R;
import com.hh.ehh.database.DataBaseSQLiteHelper;
import com.hh.ehh.database.managers.ProfileDatabaseManager;
import com.hh.ehh.model.Profile;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.utils.SharedPrefsConstants;
import com.hh.ehh.utils.Validators;
import com.hh.ehh.utils.xml.XMLHandler;
import com.parse.ParseInstallation;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by mpifa on 17/1/16.
 */
public class RegisterFragment extends Fragment {
    private EditText dni, phone;
    private TextInputLayout layout_dni, layout_phone;
    private Button login, contact;
    private LoginTask loginTask;
    private DataBaseSQLiteHelper dbHelper = null;
    private SQLiteDatabase database = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_responsible_fragment, container, false);
        dni = (EditText) rootView.findViewById(R.id.input_dni);
        phone = (EditText) rootView.findViewById(R.id.input_phone);
        login = (Button) rootView.findViewById(R.id.login);
        contact = (Button) rootView.findViewById(R.id.contact);
        layout_dni = (TextInputLayout) rootView.findViewById(R.id.input_layout_dni);
        layout_phone = (TextInputLayout) rootView.findViewById(R.id.input_layout_phone);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getActivity().getResources().getString(R.string.phone_num)));
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getActivity().getResources();
                if (!Validators.dniValidator(dni.getText().toString())) {
                    layout_dni.setError(res.getString(R.string.wrong_dni));
                } else if (phone.getText().toString().length() < 9) {
                    layout_phone.setError(res.getString(R.string.wrong_phone));
                } else {
                    layout_dni.setErrorEnabled(false);
                    layout_phone.setErrorEnabled(false);
                    if (SoapWebServiceConnection.checkInternetConnection(getActivity())) {
                        SoapWebServiceConnection soapWebServiceConnection = SoapWebServiceConnection.getInstance(getActivity());
                        loginTask = new LoginTask(soapWebServiceConnection,
                                phone.getText().toString(),
                                dni.getText().toString(),
                                ParseInstallation.getCurrentInstallation().getInstallationId());
                        loginTask.execute();
                    }
                }
            }
        });
    }

    private void createProfile(Profile profile) {
        dbHelper = DataBaseSQLiteHelper.newInstance(getActivity().getApplicationContext());
        database = dbHelper.getWritableDatabase();
        ProfileDatabaseManager.updateProfile(profile, database);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefsConstants.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SharedPrefsConstants.REGISTERED, true);
        editor.apply();
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private class LoginTask extends AsyncTask<Void, Void, Profile> {

        private SoapWebServiceConnection soapWebServiceConnection;
        private ProgressDialog dialog;
        private String installationId;
        private String dni, phone;


        public LoginTask(SoapWebServiceConnection soapWebServiceConnection, String phone, String dni, String installationId) {
            this.soapWebServiceConnection = soapWebServiceConnection;
            this.installationId = installationId;
            this.dni = dni;
            this.phone = phone;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getActivity().getResources().getString(R.string.loading));
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    loginTask.cancel(true);
                }
            });
            dialog.show();

        }


        @Override
        protected Profile doInBackground(Void... params) {
            Profile profile = null;
            String rawXML = soapWebServiceConnection.getResponsible(dni, phone, installationId);
            try {
                if (rawXML != null)
                    profile = XMLHandler.getProfile(rawXML);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return profile;
        }


        @Override
        protected void onPostExecute(Profile profile) {
            super.onPostExecute(profile);
            if (dialog != null)
                dialog.cancel();
            if (profile == null) {
                CustomDialogs.registrationError(getActivity()).show();
            } else {
                createProfile(profile);
            }
        }
    }
}