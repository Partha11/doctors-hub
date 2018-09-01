package com.syntaxerror.doctorshub;

public class MedicalHistory {

    private int id;
    private String doctorName;
    private String doctorDetails;
    private String appointmentDate;
    private String prescription;

    public MedicalHistory(int id, String doctorName, String doctorDetails, String appointmentDate, String prescription) {

        this.id = id;
        this.doctorName = doctorName;
        this.doctorDetails = doctorDetails;
        this.appointmentDate = appointmentDate;
        this.prescription = prescription;
    }

    public MedicalHistory(String doctorName, String doctorDetails, String appointmentDate, String prescription) {

        this.doctorName = doctorName;
        this.doctorDetails = doctorDetails;
        this.appointmentDate = appointmentDate;
        this.prescription = prescription;
    }

    public int getId() {

        return id;
    }

    public String getDoctorName() {

        return doctorName;
    }

    public String getDoctorDetails() {

        return doctorDetails;
    }

    public String getAppointmentDate() {

        return appointmentDate;
    }

    public String getPrescription() {

        return prescription;
    }
}
