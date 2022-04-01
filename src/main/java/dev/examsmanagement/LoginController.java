package dev.examsmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField signUpEmail;
    public PasswordField signUpPassword;

    @FXML
    protected void signup(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpView.fxml"));

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignUpView.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
}
