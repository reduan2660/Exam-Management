module dev.examsmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens dev.examsmanagement to javafx.fxml;
    exports dev.examsmanagement;
    exports dev.examsmanagement.model;
    opens dev.examsmanagement.model to javafx.fxml;
}