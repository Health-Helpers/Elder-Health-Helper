package com.hh.ehh.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;


public class Profile implements Parcelable {
    public static final Creator CREATOR =
            new Creator() {
                public Profile createFromParcel(Parcel in) {
                    return new Profile(in);
                }

                public Profile[] newArray(int size) {
                    return new Profile[size];
                }
            };
    private String id;
    private String idDoc;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String imagePath;
    private String phone;

    public Profile(String id, String idDoc, String name, String surname, String email, String address, String imagePath, String phone) {
        this.id = id;
        this.idDoc = idDoc;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.imagePath = imagePath;
        this.phone = phone;
    }

    public Profile(Parcel in) {
        id = in.readString();
        idDoc = in.readString();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        address = in.readString();
        imagePath = in.readString();
        phone = in.readString();
    }

    public Drawable getImageAsDrawable() {
        return Drawable.createFromPath(imagePath);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return getName() + " " + getSurname();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idDoc);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(imagePath);
        dest.writeString(phone);
    }


}
