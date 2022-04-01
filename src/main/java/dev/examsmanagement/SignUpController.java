package dev.examsmanagement;

import dev.examsmanagement.modal.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpController {
    @FXML
    public TextField signUpEmail;
    public PasswordField signUpPassword;

    @FXML
    protected void signUpAsStudent(){
        User newUser = new User(signUpEmail.getText(), signUpPassword.getText(), 0);
    }

    @FXML
    protected void signUpAsInstructor(){
        User newUser = new User(signUpEmail.getText(), signUpPassword.getText(), 1);
    }

    @FXML
    protected void login(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginView.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
