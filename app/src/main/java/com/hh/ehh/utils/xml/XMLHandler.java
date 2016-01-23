package com.hh.ehh.utils.xml;

import android.util.Xml;

import com.google.android.gms.maps.model.LatLng;
import com.hh.ehh.model.Geofence;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.Position;
import com.hh.ehh.model.Profile;
import com.hh.ehh.model.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpifa on 20/12/15.
 */
public class XMLHandler {
    private static final String ID_PATIENT = "patientId";
    private static final String ID_DOC = "idDoc";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIRTHDATE = "birthdate";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String DISEASE = "disease";
    private static final String DEPGRADE = "depGrade";
    private static final String PATIENT = "patient";
    private static final String PATIENTS = "patients";
    private static final String PATIENT_HEAD = "getResponsiblePatientsResponse";
    private static final String LOCATION_HEAD = "getPatientLocationResponse";
    private static final String LOCATION = "location";
    private static final String PROFILE_HEAD = "createUserResponse";
    private static final String PROFILE = "user";
    private static final String USERID = "userId";
    private static final String DATE = "date";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private static final String GEOFENCE_RESPONSE_HEAD = "getPatientGeofenceResponse";
    private static final String GEOFENCES = "geofences";
    private static final String GEOFENCE = "geofence";
    private static final String GEOFENCE_ID = "id";
    private static final String GEOFENCE_LATITUDE = "latitude";
    private static final String GEOFENCE_LONGITUDE = "longitude";
    private static final String GEOFENCE_RADIUS = "radius";


    private static final String ENCODING = "UTF-8";

    private static XmlPullParserFactory xmlFactoryObject;

    /********************************************************************************
     * Geofences
     *******************************************************************************/

    public static List<Geofence> getPatientGeofences(String stringXML) throws XmlPullParserException, IOException {
        InputStream stream = new ByteArrayInputStream(stringXML.getBytes(ENCODING));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(stream, null);
            parser.nextTag();
            return parseGeofences(parser);
        } finally {
            stream.close();
        }
    }
    private static List<Geofence> parseGeofences(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        final String ns = "";
        List<Geofence> geofenceList = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, GEOFENCE_RESPONSE_HEAD);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tagName = parser.getName();
            if (tagName.equals(GEOFENCES)) {
                readGeofences(parser,geofenceList);

            } else {
                skipTag(parser);
            }
        }

        return geofenceList;
    }

    private static void readGeofences(XmlPullParser parser, List<Geofence> geofenceList) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(GEOFENCE)) {
                geofenceList.add(readGeofence(parser));
            } else {
                skipTag(parser);
            }
        }
    }


    private static Geofence readGeofence(XmlPullParser parser) throws IOException, XmlPullParserException {


        final String ns = "";
        Geofence geofence = new Geofence();

        parser.require(XmlPullParser.START_TAG, ns, GEOFENCE);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case GEOFENCE_ID:
                    geofence.setGeofenceId(readString(parser, GEOFENCE_ID));
                    break;
                case GEOFENCE_RADIUS:
                    geofence.setRadius(readString(parser, GEOFENCE_RADIUS));
                    break;
                case GEOFENCE_LATITUDE:
                    geofence.setLatitude(readString(parser, GEOFENCE_LATITUDE));
                    break;
                case GEOFENCE_LONGITUDE:
                    geofence.setLongitude(readString(parser, GEOFENCE_LONGITUDE));
                    break;
                default:
                    skipTag(parser);
                    break;
            }
        }

        return geofence;
    }

    /********************************************************************************
     * PATIENTS
     *******************************************************************************/
    public static List<Patient> getResponsiblePatients(String stringXML) throws XmlPullParserException, IOException {
        InputStream stream = new ByteArrayInputStream(stringXML.getBytes(ENCODING));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(stream, null);
            parser.nextTag();
            return parsePatients(parser);
        } finally {
            stream.close();
        }
    }

    private static List<Patient> parsePatients(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        final String ns = "";
        List<Patient> patientList = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, PATIENT_HEAD);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(PATIENTS)) {
                readPatients(parser, patientList);

            } else {
                skipTag(parser);
            }
        }
        return patientList;
    }

    private static void readPatients(XmlPullParser parser, List<Patient> patientList) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(PATIENT)) {
                patientList.add(readPatient(parser));

            } else {
                skipTag(parser);
            }
        }
    }

    private static Patient readPatient(XmlPullParser parser) throws IOException, XmlPullParserException {
        User.UserBuilder builder = new User.UserBuilder();
        String disease = null;
        String dependencyGrade = null;
        final String ns = "";

        parser.require(XmlPullParser.START_TAG, ns, PATIENT);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case ID_PATIENT:
                    builder.setId(readString(parser, ID_PATIENT));
                    break;
                case ID_DOC:
                    builder.setIdDoc(readString(parser, ID_DOC));
                    break;
                case NAME:
                    builder.setName(readString(parser, NAME));
                    break;
                case SURNAME:
                    builder.setSurname(readString(parser, SURNAME));
                    break;
                case BIRTHDATE:
                    builder.setBirthdate(readString(parser, BIRTHDATE));
                    break;
                case ADDRESS:
                    builder.setAddress(readString(parser, ADDRESS));
                    break;
                case PHONE:
                    builder.setPhone(readString(parser, PHONE));
                    break;
                case DISEASE:
                    disease = readString(parser, DISEASE);
                    break;
                case DEPGRADE:
                    dependencyGrade = readString(parser, DEPGRADE);
                    break;
                default:
                    skipTag(parser);
                    break;
            }
        }
        Patient patient = new Patient(builder.build());
        patient.setDiseases(disease);
        patient.setDependencyGrade(dependencyGrade);
        return patient;
    }

    private static String readString(XmlPullParser parser, String TAG) throws IOException, XmlPullParserException {
        final String ns = "";
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        String nombre = getText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG);
        return nombre;
    }

    private static String getText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String resultado = "";
        if (parser.next() == XmlPullParser.TEXT) {
            resultado = parser.getText();
            parser.nextTag();
        }
        return resultado;
    }

    private static void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    /********************************************************************************
     *
     *                          LOCATION
     *
     *******************************************************************************/
    public static Position getPatientLocation(String rawResponse) throws IOException, XmlPullParserException {
        InputStream stream = new ByteArrayInputStream(rawResponse.getBytes(ENCODING));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(stream, null);
            parser.nextTag();
            return parseLocation(parser);
        } finally {
            stream.close();
        }
    }

    private static Position parseLocation(XmlPullParser parser) throws IOException, XmlPullParserException {
        final String ns = "";
        Position position = null;
        parser.require(XmlPullParser.START_TAG, ns, LOCATION_HEAD);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(LOCATION)) {
                position = readLocation(parser);
                break;

            } else {
                skipTag(parser);
            }
        }
        return position;
    }

    private static Position readLocation(XmlPullParser parser) throws IOException, XmlPullParserException {
        final String ns = "";
        String latitude = null, longitude = null, date = null;
        parser.require(XmlPullParser.START_TAG, ns, LOCATION);
        int event;
        String text = null;
        try {
            event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case DATE:
                                date = text;
                                break;
                            case LATITUDE:
                                latitude = text;
                                break;
                            case LONGITUDE:
                                longitude = text;
                                break;
                            default:
                                break;
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        Position position = null;
        if (latitude != null && longitude != null && date != null) {
            position = new Position(new LatLng(
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude)
            ), date);
        }
        return position;
    }

    /********************************************************************************
     * PROFILE
     *******************************************************************************/

    public static Profile getProfile(String rawResponse) throws IOException, XmlPullParserException {
        InputStream stream = new ByteArrayInputStream(rawResponse.getBytes(ENCODING));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(stream, null);
            parser.nextTag();
            return parseProfile(parser);
        } finally {
            stream.close();
        }
    }

    private static Profile parseProfile(XmlPullParser parser) throws IOException, XmlPullParserException {
        final String ns = "";
        Profile profile = null;
        parser.require(XmlPullParser.START_TAG, ns, PROFILE_HEAD);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(PROFILE)) {
                profile = readProfile(parser);
                break;

            } else {
                skipTag(parser);
            }
        }
        return profile;
    }

    private static Profile readProfile(XmlPullParser parser) throws IOException, XmlPullParserException {
        final String ns = "";
        parser.require(XmlPullParser.START_TAG, ns, PROFILE);
        User.UserBuilder builder = new User.UserBuilder();
        int event;
        String text = null;
        try {
            event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case USERID:
                                builder.setId(text);
                                break;
                            case ID_DOC:
                                builder.setIdDoc(text);
                                break;
                            case NAME:
                                builder.setName(text);
                                break;
                            case SURNAME:
                                builder.setSurname(text);
                                break;
                            case BIRTHDATE:
                                builder.setBirthdate(text);
                                break;
                            case ADDRESS:
                                builder.setAddress(text);
                                break;
                            case PHONE:
                                builder.setPhone(text);
                                break;
                        }
                        break;
                }
                event = parser.next();
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        User user = builder.build();
        return new Profile(
                user.getId(),
                user.getIdDoc(),
                user.getName(),
                user.getSurname(),
                "marc@gmail.com",
                user.getAddress(),
                "",
                user.getPhone()
        );
    }
}

