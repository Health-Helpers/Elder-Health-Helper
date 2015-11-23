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


public class WebServiceConnection {

    private static WebServiceConnection mConnection = null;
    private RequestQueue mRequestQueue;
    private Context context;



    private WebServiceConnection(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static WebServiceConnection getInstance(Context context) {
        if (mConnection == null) {
            mConnection = new WebServiceConnection(context);
        }
        return mConnection;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public interface CustomListener<T> extends EventListener {
        void onSucces(T response);
        void onError(VolleyError error);
    }

    /***********************************************************
     *                              GET
     ***********************************************************/

    public void phoneNumberValidation(String number, final CustomListener<String> listener) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        String url = "ADD_URL_OF_TLF" + "/" + number;
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
}
