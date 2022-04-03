package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.modal.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    public Label loginValidation;

    @FXML
    protected void login(ActionEvent event) throws IOException, SQLException {

        Connection conn = DBconnection.DBconnect();
        String sqlQ = "SELECT * FROM users WHERE email=\'" + loginEmail.getText() + "\' AND password=\'" + loginPassword.getText() + "\';";


        try{
//            --- Run query to check if user exists ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

//            --- Save User to Session ---
            User loggedUser = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getInt("isinstructor"));
            Session.setCurrentUser(loggedUser);

//            --- Switch Views ---
            Parent root;
            if(loggedUser.getIsInstructor() == 1){
                Session.switchScene("InstructorView.fxml", event);
            }
            else{
                Session.switchScene("StudentView.fxml", event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.info("Wrong Credentials or Database Error");
            loginValidation.setText("Wrong Credentials.");
        }

        conn.close();
    }

    @FXML
    protected void navSignUp(ActionEvent event) throws IOException {
        Session.switchScene("SignUpView.fxml", event);
    }
}
