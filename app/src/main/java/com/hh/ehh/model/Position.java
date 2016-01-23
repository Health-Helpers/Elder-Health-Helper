package com.hh.ehh.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mpifa on 15/1/16.
 */
public class Position {
    private LatLng latLng;
    private String date;

    public Position(LatLng latLng, String date) {
        this.latLng = latLng;
        this.date = date;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Position{" +
                "latLng=" + latLng +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (latLng != null ? !latLng.equals(position.latLng) : position.latLng != null)
            return false;
        return !(date != null ? !date.equals(position.date) : position.date != null);

    }

    @Override
    public int hashCode() {
        int result = latLng != null ? latLng.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
