package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class LoginController {
    @FXML
    public TextField loginEmail;
    public PasswordField loginPassword;

    @FXML
    protected void login(){

        Connection conn = DBconnection.DBconnect();
        String sqlQ = "SELECT * FROM users WHERE email=\'" + loginEmail.getText() + "\';";
        try{
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()){
                System.out.println("Email: " + rs.getString("email") + " Password: " + rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void navSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpView.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
