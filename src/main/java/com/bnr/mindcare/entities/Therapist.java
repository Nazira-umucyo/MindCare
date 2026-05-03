package com.bnr.mindcare.entities;

import com.bnr.mindcare.exceptions.InvalidTherapistDataException;
import java.util.ArrayList;
import java.util.List;

public class Therapist extends Person {

    private String specialty;
    private boolean available;
    private List<String> bookedPatients;

    public Therapist(String name, int age, String email, String specialty) {
        super(name, age, email);
        if (name == null || name.isEmpty()) {
            throw new InvalidTherapistDataException("Therapist name cannot be empty");
        }
        if (specialty == null || specialty.isEmpty()) {
            throw new InvalidTherapistDataException("Specialty cannot be empty");
        }
        this.specialty = specialty;
        this.available = true;
        this.bookedPatients = new ArrayList<>();
    }

    @Override
    public String getRole() { return "Therapist"; }

    public String getSpecialty() { return specialty; }
    public boolean isAvailable() { return available; }
    public List<String> getBookedPatients() { return bookedPatients; }

    public void addBookedPatient(String patientName) {
        bookedPatients.add(patientName);
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}