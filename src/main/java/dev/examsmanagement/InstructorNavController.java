package dev.examsmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InstructorNavController implements Initializable {
    public Button profileBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
    }

    //    Navigation
    @FXML
    protected void allCourse() throws IOException {
        Session.switchScene("InstructorView.fxml");
    }

    @FXML
    protected void createCourse(ActionEvent event) throws IOException {
        Session.switchScene("CourseCreateView.fxml", event); // Switch scene to CourseCreateView
    }

    @FXML
    protected void viewProfile(ActionEvent event) throws IOException {
        Session.switchScene("ProfileView.fxml", event);
    }

    @FXML
    protected void logout(ActionEvent event) throws IOException {
        Session.logout();
        Session.switchScene("LoginView.fxml", event); // Switch scene to login
    }
}
