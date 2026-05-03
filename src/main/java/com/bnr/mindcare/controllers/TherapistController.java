package com.bnr.mindcare.controllers;

import com.bnr.mindcare.entities.Patient;
import com.bnr.mindcare.entities.Therapist;
import com.bnr.mindcare.services.ClinicService;
import com.bnr.mindcare.services.FileService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TherapistController {

    @FXML private VBox patientsPane;
    @FXML private VBox appointmentPane;

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> patientNameCol;
    @FXML private TableColumn<Patient, String> patientDiagnosisCol;
    @FXML private TableColumn<Patient, String> patientAppointmentCol;

    @FXML private ComboBox<String> patientComboBox;
    @FXML private TextField appointmentDate;
    @FXML private Label appointmentMessage;

    private ClinicService clinicService = new ClinicService();
    private FileService fileService = new FileService();
    private Therapist currentTherapist;
    private String currentEmail;

    @FXML
    public void initialize() {
        patientNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        patientDiagnosisCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDiagnosis()));
        patientAppointmentCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAppointmentDate()));
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
            for (Therapist t : clinicService.getAllTherapists()) {
                if (t.getEmail().equals(currentEmail)) {
                    currentTherapist = t;
                    break;
                }
            }
        }
    }

    public void setCurrentTherapist(String email) {
        this.currentEmail = email;
        reloadData();
        if (currentTherapist != null) {
            for (Patient p : clinicService.getAllPatients()) {
                if (p.getBookedTherapist().equals(currentTherapist.getName())) {
                    currentTherapist.addBookedPatient(p.getName());
                }
            }
        }
        showPatients();
    }

    @FXML
    private void showPatients() {
        reloadData();
        if (currentTherapist != null) {
            for (Patient p : clinicService.getAllPatients()) {
                if (p.getBookedTherapist().equals(currentTherapist.getName())) {
                    if (!currentTherapist.getBookedPatients().contains(p.getName())) {
                        currentTherapist.addBookedPatient(p.getName());
                    }
                }
            }
        }
        hideAll();
        patientsPane.setVisible(true);
        refreshPatientsTable();
    }

    @FXML
    private void showAppointment() {
        reloadData();
        hideAll();
        appointmentPane.setVisible(true);
        patientComboBox.getItems().clear();
        if (currentTherapist != null) {
            for (Patient p : clinicService.getAllPatients()) {
                if (p.getBookedTherapist().equals(currentTherapist.getName())) {
                    patientComboBox.getItems().add(p.getName());
                }
            }
        }
    }

    @FXML
    private void handleSetAppointment() {
        String selectedPatient = patientComboBox.getValue();
        String date = appointmentDate.getText();

        if (selectedPatient == null || date.isEmpty()) {
            appointmentMessage.setStyle("-fx-text-fill: red;");
            appointmentMessage.setText("Please select a patient and enter a date");
            return;
        }

        for (Patient p : clinicService.getAllPatients()) {
            if (p.getName().equals(selectedPatient)) {
                p.setAppointmentDate(date);
                fileService.updatePatient(p);
                appointmentMessage.setStyle("-fx-text-fill: green;");
                appointmentMessage.setText("Appointment set for " + selectedPatient + " on " + date);
                refreshPatientsTable();
                return;
            }
        }
        appointmentMessage.setStyle("-fx-text-fill: red;");
        appointmentMessage.setText("Patient not found");
    }

    private void refreshPatientsTable() {
        patientsTable.getItems().clear();
        if (currentTherapist != null) {
            for (Patient p : clinicService.getAllPatients()) {
                if (p.getBookedTherapist().equals(currentTherapist.getName())) {
                    patientsTable.getItems().add(p);
                }
            }
        }
    }

    private void hideAll() {
        patientsPane.setVisible(false);
        appointmentPane.setVisible(false);
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bnr/mindcare/login-view.fxml"));
            Stage stage = (Stage) patientsPane.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error logging out: " + e.getMessage());
        }
    }
}