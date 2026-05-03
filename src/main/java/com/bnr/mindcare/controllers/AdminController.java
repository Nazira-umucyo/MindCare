package com.bnr.mindcare.controllers;

import com.bnr.mindcare.entities.Patient;
import com.bnr.mindcare.entities.Therapist;
import com.bnr.mindcare.exceptions.InvalidPatientDataException;
import com.bnr.mindcare.exceptions.InvalidTherapistDataException;
import com.bnr.mindcare.services.AuthService;
import com.bnr.mindcare.services.ClinicService;
import com.bnr.mindcare.services.FileService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    @FXML private TextField searchPatientField;
    @FXML private VBox addPatientPane;
    @FXML private VBox patientsListPane;
    @FXML private VBox addTherapistPane;
    @FXML private VBox therapistsListPane;

    @FXML private TextField patientName, patientAge, patientEmail, patientDiagnosis;
    @FXML private Label patientMessage;

    @FXML private TextField therapistName, therapistAge, therapistEmail, therapistSpecialty;
    @FXML private Label therapistMessage;

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> patientNameCol;
    @FXML private TableColumn<Patient, String> patientEmailCol;
    @FXML private TableColumn<Patient, Integer> patientAgeCol;
    @FXML private TableColumn<Patient, String> patientDiagnosisCol;
    @FXML private TableColumn<Patient, String> patientTherapistCol;
    @FXML private Label patientsMessage;

    @FXML private TableView<Therapist> therapistsTable;
    @FXML private TableColumn<Therapist, String> therapistNameCol;
    @FXML private TableColumn<Therapist, String> therapistEmailCol;
    @FXML private TableColumn<Therapist, Integer> therapistAgeCol;
    @FXML private TableColumn<Therapist, String> therapistSpecialtyCol;
    @FXML private TableColumn<Therapist, String> therapistAvailableCol;
    @FXML private Label therapistsMessage;

    private ClinicService clinicService = new ClinicService();
    private FileService fileService = new FileService();
    private AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        patientNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        patientEmailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        patientAgeCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAge()).asObject());
        patientDiagnosisCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDiagnosis()));
        patientTherapistCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBookedTherapist()));

        therapistNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        therapistEmailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        therapistAgeCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAge()).asObject());
        therapistSpecialtyCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSpecialty()));
        therapistAvailableCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isAvailable() ? "Yes" : "No"));

        for (Patient p : fileService.loadPatients()) {
            clinicService.addPatient(p);
        }
        for (Therapist t : fileService.loadTherapists()) {
            clinicService.addTherapist(t);
        }
    }

    @FXML
    private void showAddPatient() {
        hideAll();
        addPatientPane.setVisible(true);
    }

    @FXML
    private void showPatients() {
        hideAll();
        patientsListPane.setVisible(true);
        refreshPatientsTable();
    }

    @FXML
    private void showAddTherapist() {
        hideAll();
        addTherapistPane.setVisible(true);
    }

    @FXML
    private void showTherapists() {
        hideAll();
        therapistsListPane.setVisible(true);
        refreshTherapistsTable();
    }

    private void hideAll() {
        addPatientPane.setVisible(false);
        patientsListPane.setVisible(false);
        addTherapistPane.setVisible(false);
        therapistsListPane.setVisible(false);
    }

    @FXML
    private void handleAddPatient() {
        try {
            String name = patientName.getText();
            int age = Integer.parseInt(patientAge.getText());
            String email = patientEmail.getText();
            String diagnosis = patientDiagnosis.getText();

            Patient patient = new Patient(name, age, email, diagnosis);
            clinicService.addPatient(patient);
            fileService.savePatient(patient);
            authService.saveEmailForRegistration("Patient", email);

            patientMessage.setStyle("-fx-text-fill: green;");
            patientMessage.setText("Patient added! They can register with: " + email);
            clearPatientFields();

        } catch (InvalidPatientDataException e) {
            patientMessage.setStyle("-fx-text-fill: red;");
            patientMessage.setText("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            patientMessage.setStyle("-fx-text-fill: red;");
            patientMessage.setText("Error: Age must be a number");
        }
    }

    @FXML
    private void handleRemovePatient() {
        Patient selected = patientsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            clinicService.removePatient(selected.getName());
            refreshPatientsTable();
            patientsMessage.setStyle("-fx-text-fill: green;");
            patientsMessage.setText("Patient removed successfully!");
        } else {
            patientsMessage.setStyle("-fx-text-fill: red;");
            patientsMessage.setText("Please select a patient to remove");
        }
    }

    @FXML
    private void handleAddTherapist() {
        try {
            String name = therapistName.getText();
            int age = Integer.parseInt(therapistAge.getText());
            String email = therapistEmail.getText();
            String specialty = therapistSpecialty.getText();

            Therapist therapist = new Therapist(name, age, email, specialty);
            clinicService.addTherapist(therapist);
            fileService.saveTherapist(therapist);
            authService.saveEmailForRegistration("Therapist", email);

            therapistMessage.setStyle("-fx-text-fill: green;");
            therapistMessage.setText("Therapist added! They can register with: " + email);
            clearTherapistFields();

        } catch (InvalidTherapistDataException e) {
            therapistMessage.setStyle("-fx-text-fill: red;");
            therapistMessage.setText("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            therapistMessage.setStyle("-fx-text-fill: red;");
            therapistMessage.setText("Error: Age must be a number");
        }
    }

    @FXML
    private void handleRemoveTherapist() {
        Therapist selected = therapistsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            clinicService.removeTherapist(selected.getName());
            refreshTherapistsTable();
            therapistsMessage.setStyle("-fx-text-fill: green;");
            therapistsMessage.setText("Therapist removed successfully!");
        } else {
            therapistsMessage.setStyle("-fx-text-fill: red;");
            therapistsMessage.setText("Please select a therapist to remove");
        }
    }

    private void refreshPatientsTable() {
        patientsTable.getItems().clear();
        patientsTable.getItems().addAll(clinicService.getAllPatients());
    }

    private void refreshTherapistsTable() {
        therapistsTable.getItems().clear();
        therapistsTable.getItems().addAll(clinicService.getAllTherapists());
    }

    private void clearPatientFields() {
        patientName.clear();
        patientAge.clear();
        patientEmail.clear();
        patientDiagnosis.clear();
    }

    private void clearTherapistFields() {
        therapistName.clear();
        therapistAge.clear();
        therapistEmail.clear();
        therapistSpecialty.clear();
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bnr/mindcare/login-view.fxml"));
            Stage stage = (Stage) addPatientPane.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 500));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error logging out: " + e.getMessage());
        }
    }
    @FXML
    private void handleSearchPatient() {
        String keyword = searchPatientField.getText().toLowerCase();
        patientsTable.getItems().clear();
        for (Patient p : clinicService.getAllPatients()) {
            if (p.getName().toLowerCase().contains(keyword)) {
                patientsTable.getItems().add(p);
            }
        }
    }

    @FXML
    private void handleSortPatients() {
        List<Patient> sorted = new ArrayList<>(clinicService.getAllPatients());
        sorted.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        patientsTable.getItems().clear();
        patientsTable.getItems().addAll(sorted);
    }
}