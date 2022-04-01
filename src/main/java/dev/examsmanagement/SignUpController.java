package dev.examsmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {
    @FXML
    public TextField signUpEmail;
    public PasswordField signUpPassword;

    @FXML
    protected void signUp(){
        System.out.println(signUpEmail.getText());
        System.out.println(signUpPassword.getText());
    }
}
