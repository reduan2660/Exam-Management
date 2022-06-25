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

    @Override
    protected void allCourse() throws IOException {
        if(Session.getCurrentUser().getIsInstructor() == 1) {
            Session.switchScene("InstructorView.fxml");
        }
        else{
            Session.switchScene("StudentView.fxml");
        }
    }

}
