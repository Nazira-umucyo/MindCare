package com.bnr.mindcare.controllers;

import com.bnr.mindcare.entities.Patient;
import com.bnr.mindcare.entities.Therapist;
import com.bnr.mindcare.services.ClinicService;
import com.bnr.mindcare.services.FileService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientController {

    @FXML private VBox profilePane;
    @FXML private VBox bookingPane;
    @FXML private VBox historyPane;

    @FXML private Label profileName, profileAge, profileEmail;
    @FXML private Label profileDiagnosis, profileTherapist, profileAppointment;

    @FXML private ListView<String> therapistsList;
    @FXML private Label bookingMessage;

    @FXML private Label historyTherapist, historyDate, historyDiagnosis;

    private ClinicService clinicService = new ClinicService();
    private FileService fileService = new FileService();
    private Patient currentPatient;
    private String currentEmail;

    @FXML
    public void initialize() {
        reloadData();
    }

    private void reloadData() {
        clinicService = new ClinicService();
        for (Patient p : fileService.loadPatients()) {
            clinicService.addPatient(p);
        }
        for (Therapist t : fileService.loadTherapists()) {
            clinicService.addTherapist(t);
        }
        if (currentEmail != null) {
            currentPatient = clinicService.findPatientByEmail(currentEmail);
        }
    }

    public void setCurrentPatient(String email) {
        this.currentEmail = email;
        currentPatient = clinicService.findPatientByEmail(email);
        if (currentPatient != null) {
            showProfile();
        }
    }

    @FXML
    private void showProfile() {
        reloadData();
        hideAll();
        profilePane.setVisible(true);
        if (currentPatient != null) {
            profileName.setText("Name: " + currentPatient.getName());
            profileAge.setText("Age: " + currentPatient.getAge());
            profileEmail.setText("Email: " + currentPatient.getEmail());
            profileDiagnosis.setText("Diagnosis: " + currentPatient.getDiagnosis());
            profileTherapist.setText("Booked Therapist: " + currentPatient.getBookedTherapist());
            profileAppointment.setText("Appointment: " + currentPatient.getAppointmentDate());
        }
    }

    @FXML
    private void showBooking() {
        reloadData();
        hideAll();
        bookingPane.setVisible(true);
        therapistsList.getItems().clear();
        for (Therapist t : clinicService.getAvailableTherapists()) {
            therapistsList.getItems().add(t.getName() + " - " + t.getSpecialty());
        }
    }

    @FXML
    private void showHistory() {
        reloadData();
        hideAll();
        historyPane.setVisible(true);
        if (currentPatient != null) {
            if (currentPatient.getAppointmentDate().equals("Not set")) {
                historyTherapist.setText("No session booked yet");
                historyDate.setText("");
                historyDiagnosis.setText("");
            } else {
                historyTherapist.setText("Therapist: " + currentPatient.getBookedTherapist());
                historyDate.setText("Appointment Date: " + currentPatient.getAppointmentDate());
                historyDiagnosis.setText("Diagnosis: " + currentPatient.getDiagnosis());
            }
        }
    }

    @FXML
    private void handleBookTherapist() {
        String selected = therapistsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            bookingMessage.setStyle("-fx-text-fill: red;");
            bookingMessage.setText("Please select a therapist");
            return;
        }

        String therapistName = selected.split(" - ")[0];
        for (Therapist t : clinicService.getAllTherapists()) {
            if (t.getName().equals(therapistName)) {
                clinicService.bookTherapist(currentPatient, t);
                fileService.updatePatient(currentPatient);
                bookingMessage.setStyle("-fx-text-fill: green;");
                bookingMessage.setText("Therapist booked successfully!");
                return;
            }
        }
    }

    private void hideAll() {
        profilePane.setVisible(false);
        bookingPane.setVisible(false);
        historyPane.setVisible(false);
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bnr/mindcare/login-view.fxml"));
            Stage stage = (Stage) profilePane.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error logging out: " + e.getMessage());
        }
    }
}