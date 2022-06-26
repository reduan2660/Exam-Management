package dev.examsmanagement;

import dev.examsmanagement.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Session {
    private static User currentUser = null;
    public static User sessStudent = null;
    public static Course sessCourse = null;
    public static Test sessTest = null;
    public static Question sessQuestion = null;
    public static MCQquestion sessMCQ = null;
    public static CQSubmission sessCQSubmission = null;
    public static Stage primaryStage = null;

    public static void setCurrentUser(User _currentUser) {
        currentUser = _currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() { currentUser = null; }

    public static void switchScene(String _viewName, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Session.class.getResource(_viewName)));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(String _viewName) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Session.class.getResource(_viewName)));
        Stage stage = primaryStage;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
