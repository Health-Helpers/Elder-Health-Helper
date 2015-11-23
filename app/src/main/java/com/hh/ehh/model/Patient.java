package com.hh.ehh.model;


public class Patient {
    private String id;
    private String nickName;
    private String name;
    private String surname;
    private String birthDate;
    private String phone;
    private String address;
    private String language;

    public Patient(String id, String nickName, String name, String surname, String birthDate,
                   String phone, String address, String language) {
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return (id != null ? id.equals(patient.id) : patient.id == null)
           &&  (nickName != null ? nickName.equals(patient.nickName) : patient.nickName == null)
           &&  (name != null ? name.equals(patient.name) : patient.name == null)
           &&  (surname != null ? surname.equals(patient.surname) : patient.surname == null)
           &&  (birthDate != null ? birthDate.equals(patient.birthDate) : patient.birthDate == null)
           &&  (phone != null ? phone.equals(patient.phone) : patient.phone == null)
           &&  (address != null ? address.equals(patient.address) : patient.address == null)
           &&  (language != null ? language.equals(patient.language) : patient.language == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
