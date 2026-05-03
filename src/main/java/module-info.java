module com.bnr.mindcare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.bnr.mindcare to javafx.fxml;
    opens com.bnr.mindcare.controllers to javafx.fxml;
    opens com.bnr.mindcare.entities to javafx.fxml;
    opens com.bnr.mindcare.services to javafx.fxml;
    opens com.bnr.mindcare.exceptions to javafx.fxml;
    opens com.bnr.mindcare.generics to javafx.fxml;

    exports com.bnr.mindcare.generics;
    exports com.bnr.mindcare;
    exports com.bnr.mindcare.controllers;
    exports com.bnr.mindcare.entities;
    exports com.bnr.mindcare.services;
    exports com.bnr.mindcare.exceptions;
}