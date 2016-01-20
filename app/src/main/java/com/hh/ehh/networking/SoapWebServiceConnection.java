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
    private static SoapWebServiceConnection mConnection = null;
    private static String ADD_PATIENT_TO_RESPONSIBLE = "addPatientToResponsible";
    private static String ADD_RESPONSIBLE_TO_PATIENT = "addResponsibleToPatient";
    private static String CREATE_PATIENT = "createPatient";
    private static String CREATE_RESPONSIBLE = "createResponsible";
    private static String DELETE_PATIENT = "deletePatient";
    private static String DELETE_PATIENT_RESPONSIBLE = "deletePatientResponse";
    private static String DELETE_RESPONSIBLE = "deleteResponsible";
    private static String DELETE_RESPONSIBLE_FROM_PATIENT = "deleteResponsibleFromPatient";
    private static String GET_PATIENT_RESPONSIBLES = "getPatientResponsibles";
    private static String READ_PATIENT = "readPatient";
    private static String READ_RESPONSIBLE = "readResponsible";
    private static String UPDATE_PATIENT = "updatePatient";
    private static String UPDATE_RESPONSIBLE = "updateResponsible";
    private static String GET_RESPONSIBLE_PATIENTS = "getResponsiblePatients";
    private static String POST_NEW_PATIENT = "createPatient";
    private static String ADD_GEOFENCE = "addPatientGeofence";
    private static String GET_PATIENT_GEOFENCE = "getPatientGeofences";




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
//    public String getPatient(@NonNull String id) {
//        String action = SOAP_ACTION + "/" + READ_PATIENT;
//        SoapObject request = new SoapObject(NAMESPACE, READ_PATIENT);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.dotNet = true;
//        request.addProperty("patientId", Integer.valueOf(id));
//        envelope.setOutputSoapObject(request);
//        return GET(action, envelope);
//    }
    public String getResponsible(@NonNull String id) {
        String action = SOAP_ACTION + "/" + READ_RESPONSIBLE;
        SoapObject request = new SoapObject(NAMESPACE, READ_RESPONSIBLE);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("responsibleId", Integer.valueOf(id));
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    public String getResponsiblePatients(@NonNull String id) {
        String action = SOAP_ACTION + "/" + GET_RESPONSIBLE_PATIENTS;
        SoapObject request = new SoapObject(NAMESPACE, GET_RESPONSIBLE_PATIENTS);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("responsibleId", Integer.valueOf(id));
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    public String getPatientLocation(@NonNull String id) {
        String action = SOAP_ACTION + "/" + GET_RESPONSIBLE_PATIENTS;
        SoapObject request = new SoapObject(NAMESPACE, GET_RESPONSIBLE_PATIENTS);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("responsibleId", Integer.valueOf(id));
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
