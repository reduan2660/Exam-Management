package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    public TextField loginEmail;
    public PasswordField loginPassword;
    public Label loginValidation;

    @FXML
    protected void login(ActionEvent event) throws IOException, SQLException {

        loginValidation.setText("Logging in. Please Wait.");

//        Connection conn = DBconnection.DBconnect();
        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT * FROM users WHERE email=\'" + loginEmail.getText() + "\' AND password=\'" + loginPassword.getText() + "\' LIMIT 1;";


        try{
//            --- Run query to check if user exists ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

//            --- Mysql Config ---
            if(DBconnection.database == DBconnection.mysqlDB) {
                rs.next();
            }

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

//        conn.close();
    }

    @FXML
    protected void navSignUp(ActionEvent event) throws IOException {
        Session.switchScene("SignUpView.fxml", event);
    }
}
