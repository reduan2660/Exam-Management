package dev.examsmanagement;

import dev.examsmanagement.modal.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SignUpController {
    @FXML
    public TextField signUpName;
    public TextField signUpEmail;
    public PasswordField signUpPassword;
    public Label signupValidation;

    @FXML
    protected void signup(User newuser, ActionEvent event) throws IOException, SQLException {
        if(newuser.newUserValidation()){
            newuser.saveUser();

            login(event); // Switch view to Login -- (Alternate) -- Direct to dashboard --
        }
        else {
            signupValidation.setText(newuser.getValidationMessage());
        }
    }

    @FXML
    protected void signUpAsStudent(ActionEvent event) throws IOException, SQLException {
        User newUser = new User(signUpName.getText(),signUpEmail.getText(), signUpPassword.getText(), 0);
        signup(newUser, event);
    }

    @FXML
    protected void signUpAsInstructor(ActionEvent event) throws IOException, SQLException {
        User newUser = new User(signUpName.getText(),signUpEmail.getText(), signUpPassword.getText(), 1);
        signup(newUser, event);
    }

    @FXML
    protected void login(ActionEvent event) throws IOException {
        Session.switchScene("LoginView.fxml", event);
    }
}
