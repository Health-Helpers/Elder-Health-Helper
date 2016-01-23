package com.hh.ehh.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.hh.ehh.model.Geofence;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.Profile;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by mpifa on 19/12/15.
 */
public class SoapWebServiceConnection {
    private static String ADD_GEOFENCE = "addPatientGeofence";
    private static String GET_PATIENT_GEOFENCE = "getPatientGeofences";

    private static SoapWebServiceConnection mConnection = null;
    private static String REGISTER_RESPONSIBLE = "registerUser";
    private static String GET_PATIENT_LOCATION = "getPatientLocation";
    private static String GET_RESPONSIBLE_PATIENTS = "getResponsiblePatients";
    private static String POST_NEW_PATIENT = "createPatient";
    private static String NAMESPACE = "http://ws.ehh.cat/";
    private static String SOAP_WS_CONTROLLER = "/SoapWSController";
    private static String SOAP_ACTION = NAMESPACE + SOAP_WS_CONTROLLER;
    private static String URL = "http://alumnes-grp04.udl.cat/EHHWeb" + SOAP_WS_CONTROLLER;
    private Context context;

    public SoapWebServiceConnection(Context context) {
        this.context = context;
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static SoapWebServiceConnection getInstance(Context context) {
        if (mConnection == null) {
            mConnection = new SoapWebServiceConnection(context);
        }
        return mConnection;
    }


    /***********************************************************
     * GET
     ***********************************************************/
    public String getResponsible(@NonNull String idDoc, @NonNull String phone, @NonNull String installationId) {
        String action = SOAP_ACTION + "/" + REGISTER_RESPONSIBLE;
        SoapObject request = new SoapObject(NAMESPACE, REGISTER_RESPONSIBLE);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("idDoc", idDoc);
        request.addProperty("phone", phone);
        request.addProperty("parseInstallationId", installationId);
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    public String getResponsible(@NonNull String id) {
        String action = SOAP_ACTION + "/" + REGISTER_RESPONSIBLE;
        SoapObject request = new SoapObject(NAMESPACE, REGISTER_RESPONSIBLE);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("responsibleId", id);
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    public String getResponsiblePatients(@NonNull String responsibleId) {
        String action = SOAP_ACTION + "/" + GET_RESPONSIBLE_PATIENTS;
        SoapObject request = new SoapObject(NAMESPACE, GET_RESPONSIBLE_PATIENTS);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("responsibleId", Integer.valueOf(responsibleId));
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    public String getPatientLocation(@NonNull String patientId) {
        String action = SOAP_ACTION + "/" + GET_PATIENT_LOCATION;
        SoapObject request = new SoapObject(NAMESPACE, GET_PATIENT_LOCATION);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("patientId", Integer.valueOf(patientId));
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    private String GET(String action, SoapSerializationEnvelope envelope) {
        HttpTransportSE transporte = new HttpTransportSE(URL);
        try {
            transporte.call(action, envelope);
            Object response = envelope.getResponse();
            return String.valueOf(response);
        } catch (Exception e) {
            return null;
        }
    }

    /***********************************************************
     * POST
     ***********************************************************/
    public void postPatient(Profile profile, Patient patient) {
        String action = SOAP_ACTION + "/" + POST_NEW_PATIENT;
        SoapObject request = new SoapObject(NAMESPACE, POST_NEW_PATIENT);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("name", patient.getName());
        request.addProperty("surname", patient.getSurname());
        request.addProperty("idDoc", patient.getIdDoc());
        request.addProperty("phone  ", patient.getPhone());
        request.addProperty("birthdate", patient.getBirthDate());
        request.addProperty("address", patient.getAddress());
        request.addProperty("disease", patient.getDiseases());
        request.addProperty("dependencyGrade", patient.getDependencyGrade());
        request.addProperty("responsibleId", Integer.valueOf(profile.getId()));
        envelope.setOutputSoapObject(request);
        GET(action, envelope);
    }

    /***********************************************************
     * GEOFENCES
     ***********************************************************/
    public void addPatientGeofence(Geofence geofence, Patient patient) {
        String action = SOAP_ACTION + "/" + ADD_GEOFENCE;
        SoapObject request = new SoapObject(NAMESPACE, ADD_GEOFENCE);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("patientId", patient.getId());
        request.addProperty("radius", geofence.getRadius());
        request.addProperty("latitude", geofence.getLatitude());
        request.addProperty("longitude", geofence.getLongitude());

        envelope.setOutputSoapObject(request);
        GET(action, envelope);
    }

    public String getPatientGeofence(Patient patient) {
        String action = SOAP_ACTION + "/" + GET_PATIENT_GEOFENCE;
        SoapObject request = new SoapObject(NAMESPACE, GET_PATIENT_GEOFENCE);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("patientId", patient.getId());

        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }
}
