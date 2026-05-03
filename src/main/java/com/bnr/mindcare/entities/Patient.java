package com.bnr.mindcare.entities;

import com.bnr.mindcare.exceptions.InvalidPatientDataException;

public class Patient extends Person {

    private String diagnosis;
    private String bookedTherapist;
    private String appointmentDate;

    public Patient(String name, int age, String email, String diagnosis) {
        super(name, age, email);
        if (name == null || name.isEmpty()) {
            throw new InvalidPatientDataException("Patient name cannot be empty");
        }
        if (age < 0 || age > 120) {
            throw new InvalidPatientDataException("Patient age is not valid");
        }
        if (diagnosis == null || diagnosis.isEmpty()) {
            throw new InvalidPatientDataException("Diagnosis cannot be empty");
        }
        this.diagnosis = diagnosis;
        this.bookedTherapist = "None";
        this.appointmentDate = "Not set";
    }

    @Override
    public String getRole() { return "Patient"; }

    public String getDiagnosis() { return diagnosis; }
    public String getBookedTherapist() { return bookedTherapist; }
    public String getAppointmentDate() { return appointmentDate; }

    public void setBookedTherapist(String therapistName) {
        this.bookedTherapist = therapistName;
    }
    public void setAppointmentDate(String date) {
        this.appointmentDate = date;
    }
}