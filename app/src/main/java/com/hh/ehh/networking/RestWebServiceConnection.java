package com.hh.ehh.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.EventListener;


public class RestWebServiceConnection {

    private final static String BASE_URL = "http://alumnes-grp04.udl.cat";
    private final static String EHH_WEB = "/EHHWeb";
    private final static String REST = "/RestWSController";
    private final static String CENTERS = "/getMedicalCentersList";
    private final static String CENTER = "/getMedicalCenter/";
    private static RestWebServiceConnection mConnection = null;
    private RequestQueue mRequestQueue;
    private Context context;


    private RestWebServiceConnection(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static RestWebServiceConnection getInstance(Context context) {
        if (mConnection == null) {
            mConnection = new RestWebServiceConnection(context);
        }
        return mConnection;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /***********************************************************
     * GET
     ***********************************************************/


    public void getMedicalCentersList(final CustomListener<String> listener) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        String url = BASE_URL + EHH_WEB + REST + CENTERS;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSucces(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        mRequestQueue.add(request);
    }

    public interface CustomListener<T> extends EventListener {
        void onSucces(T response);

        void onError(VolleyError error);
    }
}
