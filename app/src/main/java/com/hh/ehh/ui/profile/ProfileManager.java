package com.hh.ehh.ui.profile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hh.ehh.networking.RestWebServiceConnection;
import com.hh.ehh.utils.StateListener;

/**
 * Created by mcpe on 23/12/2015.
 */
public class ProfileManager {

    private final StateListener stateListenner;
    private SQLiteDatabase database;
    private Context context;

//    TODO add profile image management.

    public ProfileManager(SQLiteDatabase database, Context context,StateListener listenner) {
        this.database = database;
        this.context = context;
        this.stateListenner = listenner;
    }

    public void buildProfile(){
        if (RestWebServiceConnection.checkInternetConnection(context)) {
            updateOrCreateProfile();
        }
    }

    private void updateOrCreateProfileImage(final String profile) {
        RestWebServiceConnection connections = RestWebServiceConnection.getInstance(context);
//        connections.getProfileImageBitmap(uuid, new RestWebServiceConnection.CustomListener<Bitmap>() {
//            @Override
//            public void onSucces(Bitmap response) {
//                try {
//                    FileHelper fileHelper = new FileHelper();
//                    File file = fileHelper.checkExsistFile(fileHelper.DEFAULT_STORAGE, response);
//                    Profile profile1 = JSONParser.getProfileFromJSON(profile, file.getPath());
//                    ProfileDatabaseManager.updateProfile(profile1,database);
//                    if(stateListenner!=null)
//                        stateListenner.onSuccess();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    stateListenner.onError();
//                }
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                if(stateListenner!=null)
//                    stateListenner.onError();
//            }
//        });
    }

    private void updateOrCreateProfile() {
        RestWebServiceConnection connections = RestWebServiceConnection.getInstance(context);
//        connections.getProfileInfo(uuid, new RestWebServiceConnection.CustomListener<String>() {
//            @Override
//            public void onSucces(String response) {
//                updateOrCreateProfileImage(response);
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//                if(stateListenner!=null)
//                    stateListenner.onError();
//            }
//        });
    }
}
