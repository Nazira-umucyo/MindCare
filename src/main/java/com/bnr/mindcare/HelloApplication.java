package com.bnr.mindcare;

import com.bnr.mindcare.services.AuthService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new AuthService().createAdminIfNotExists();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bnr/mindcare/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 500);
        stage.setTitle("MindCare — Youth Mental Health System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}