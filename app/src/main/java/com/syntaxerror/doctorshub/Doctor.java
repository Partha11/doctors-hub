package com.syntaxerror.doctorshub;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {

    private int doctorId;

    private String doctorName;
    private String doctorNumber;
    private String doctorDetails;
    private String doctorAppointment;
    private String doctorEmail;

    public Doctor() {

    }

    public Doctor(String doctorName, String doctorNumber, String doctorDetails, String doctorAppointment, String doctorEmail) {

        this.doctorName = doctorName;
        this.doctorNumber = doctorNumber;
        this.doctorDetails = doctorDetails;
        this.doctorAppointment = doctorAppointment;
        this.doctorEmail = doctorEmail;
    }

    public Doctor(int doctorId, String doctorName, String doctorNumber, String doctorDetails, String doctorAppointment, String doctorEmail) {

        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorNumber = doctorNumber;
        this.doctorDetails = doctorDetails;
        this.doctorAppointment = doctorAppointment;
        this.doctorEmail = doctorEmail;
    }

    public int getDoctorId() {

        return doctorId;
    }

    public String getDoctorName() {

        return doctorName;
    }

    public String getDoctorNumber() {

        return doctorNumber;
    }

    public String getDoctorDetails() {

        return doctorDetails;
    }

    public String getDoctorAppointment() {

        return doctorAppointment;
    }

    public String getDoctorEmail() {

        return doctorEmail;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
