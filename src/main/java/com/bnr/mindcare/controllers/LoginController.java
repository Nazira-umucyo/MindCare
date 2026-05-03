package com.bnr.mindcare.controllers;

import com.bnr.mindcare.services.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label messageLabel;

    private AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Admin", "Patient", "Therapist");
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Please fill in all fields");
            return;
        }

        String role = authService.login(email, password);

        if (role == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Invalid email or password");
            return;
        }

        try {
            String fxmlFile = switch (role) {
                case "Admin" -> "/com/bnr/mindcare/admin-view.fxml";
                case "Patient" -> "/com/bnr/mindcare/patient-view.fxml";
                case "Therapist" -> "/com/bnr/mindcare/therapist-view.fxml";
                default -> null;
            };

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();

            if (role.equals("Patient")) {
                PatientController controller = loader.getController();
                controller.setCurrentPatient(email);
            } else if (role.equals("Therapist")) {
                TherapistController controller = loader.getController();
                controller.setCurrentTherapist(email);
            }

        } catch (Exception e) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Error loading screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (email.isEmpty() || password.isEmpty() || role == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Please fill in all fields and select a role");
            return;
        }

        if (role.equals("Admin")) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Admin cannot register. Please login.");
            return;
        }

        String existingRole = authService.checkIfCreatedByAdmin(email, role);
        if (existingRole == null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("You must be added by admin first");
            return;
        }

        authService.registerUser(role, email, email, password);
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Registered successfully! You can now login.");
    }
}