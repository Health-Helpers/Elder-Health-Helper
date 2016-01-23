package com.hh.ehh.ui.splash;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.database.DataBaseSQLiteHelper;
import com.hh.ehh.database.managers.ProfileDatabaseManager;
import com.hh.ehh.model.Profile;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.ui.main.MainActivity;
import com.hh.ehh.utils.xml.XMLHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by mpifa on 17/1/16.
 */
public class SplashFragment extends Fragment {
    private TextView loginStatus;
    private ImageView logo;
    private DataBaseSQLiteHelper dbHelper = null;
    private SQLiteDatabase database = null;
    private ProfileTask profileTask;
    private SoapWebServiceConnection soapWebServiceConnection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_fragment, container, false);
        loginStatus = (TextView) view.findViewById(R.id.login_status);
        logo = (ImageView) view.findViewById(R.id.logo);
        loginStatus.setText(getResources().getString(R.string.check_data));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (SoapWebServiceConnection.checkInternetConnection(getActivity())) {
            soapWebServiceConnection = SoapWebServiceConnection.getInstance(getActivity());
            profileTask = new ProfileTask(soapWebServiceConnection, "");
            profileTask.execute();
        } else {
            CustomDialogs.noInternetConnection(getActivity()).show();
        }
    }

    private void updateProfile(Profile profile) {
        dbHelper = DataBaseSQLiteHelper.newInstance(getActivity().getApplicationContext());
        database = dbHelper.getWritableDatabase();
        ProfileDatabaseManager profileDatabaseManager = new ProfileDatabaseManager();
        profileDatabaseManager.updateProfile(profile, database);
    }

    private class ProfileTask extends AsyncTask<Void, Void, Profile> {

        private SoapWebServiceConnection soapWebServiceConnection;
        private String id;


        public ProfileTask(SoapWebServiceConnection soapWebServiceConnection, String id) {
            this.soapWebServiceConnection = soapWebServiceConnection;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Profile doInBackground(Void... params) {
            Profile profile = null;
            String rawXML = soapWebServiceConnection.getResponsible(id);
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
            if (profile != null) {
                updateProfile(profile);
            }
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
