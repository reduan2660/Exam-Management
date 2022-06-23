package dev.examsmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends InstructorNavController {
    public Text name, email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
        name.setText(Session.getCurrentUser().getName());
        email.setText(Session.getCurrentUser().getEmail());
    }

    @FXML
    protected void createCourse(ActionEvent event) throws IOException {
        Session.switchScene("CourseCreateView.fxml", event); // Switch scene to CourseCreateView
    }

    @FXML
    protected void logout(ActionEvent event) throws IOException {
        Session.logout();
        Session.switchScene("LoginView.fxml", event); // Switch scene to login
    }
}
