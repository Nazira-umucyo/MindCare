package com.bnr.mindcare.services;

import com.bnr.mindcare.entities.Patient;
import com.bnr.mindcare.entities.Therapist;
import com.bnr.mindcare.generics.Repository;

import java.util.List;

public class ClinicService {

    private Repository<Patient> patientRepository = new Repository<>();
    private Repository<Therapist> therapistRepository = new Repository<>();

    public void addPatient(Patient patient) {
        patientRepository.add(patient);
    }

    public void removePatient(String name) {
        patientRepository.getAll().removeIf(p -> p.getName().equals(name));
    }

    public List<Patient> getAllPatients() {
        return patientRepository.getAll();
    }

    public Patient findPatientByEmail(String email) {
        for (Patient p : patientRepository.getAll()) {
            if (p.getEmail().equals(email)) {
                return p;
            }
        }
        return null;
    }

    public void addTherapist(Therapist therapist) {
        therapistRepository.add(therapist);
    }

    public void removeTherapist(String name) {
        therapistRepository.getAll().removeIf(t -> t.getName().equals(name));
    }

    public List<Therapist> getAllTherapists() {
        return therapistRepository.getAll();
    }

    public List<Therapist> getAvailableTherapists() {
        List<Therapist> available = new java.util.ArrayList<>();
        for (Therapist t : therapistRepository.getAll()) {
            if (t.isAvailable()) {
                available.add(t);
            }
        }
        return available;
    }

    public void bookTherapist(Patient patient, Therapist therapist) {
        patient.setBookedTherapist(therapist.getName());
        therapist.addBookedPatient(patient.getName());
        therapist.setAvailable(false);
    }
}