package com.bnr.mindcare.services;

import com.bnr.mindcare.entities.Patient;
import com.bnr.mindcare.entities.Therapist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    private static final String PATIENTS_FILE = "patients.txt";
    private static final String THERAPISTS_FILE = "therapists.txt";

    public void savePatient(Patient patient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENTS_FILE, true))) {
            writer.write(patient.getName() + "," + patient.getAge() + "," +
                    patient.getEmail() + "," + patient.getDiagnosis() + "," +
                    patient.getBookedTherapist() + "," + patient.getAppointmentDate());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving patient: " + e.getMessage());
        }
    }

    public List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Patient p = new Patient(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]);
                p.setBookedTherapist(parts[4]);
                p.setAppointmentDate(parts[5]);
                patients.add(p);
            }
        } catch (IOException e) {
            System.out.println("No patients file found, starting fresh.");
        }
        return patients;
    }

    public void saveTherapist(Therapist therapist) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(THERAPISTS_FILE, true))) {
            writer.write(therapist.getName() + "," + therapist.getAge() + "," +
                    therapist.getEmail() + "," + therapist.getSpecialty() + "," +
                    therapist.isAvailable());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving therapist: " + e.getMessage());
        }
    }

    public List<Therapist> loadTherapists() {
        List<Therapist> therapists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(THERAPISTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Therapist t = new Therapist(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]);
                t.setAvailable(Boolean.parseBoolean(parts[4]));
                therapists.add(t);
            }
        } catch (IOException e) {
            System.out.println("No therapists file found, starting fresh.");
        }
        return therapists;
    }
    public void updatePatient(Patient patient) {
        List<Patient> allPatients = loadPatients();
        System.out.println("Updating patient: " + patient.getName() + " date: " + patient.getAppointmentDate());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENTS_FILE, false))) {
            for (Patient p : allPatients) {
                if (p.getEmail().equals(patient.getEmail())) {
                    writer.write(patient.getName() + "," + patient.getAge() + "," +
                            patient.getEmail() + "," + patient.getDiagnosis() + "," +
                            patient.getBookedTherapist() + "," + patient.getAppointmentDate());
                } else {
                    writer.write(p.getName() + "," + p.getAge() + "," +
                            p.getEmail() + "," + p.getDiagnosis() + "," +
                            p.getBookedTherapist() + "," + p.getAppointmentDate());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }
}