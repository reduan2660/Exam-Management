package dev.examsmanagement.modal;

import dev.examsmanagement.Log;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.*;

public class User {
    private int id;
    private String email, password;
    private int isInstructor;
    private String validationMessage;

    public User(String _email, String _password, int _instrctor){
        email = _email;
        password = _password;
        isInstructor = _instrctor;
    }

    public User(int _id, String _email, String _password, int _instrctor){
        id = _id;
        email = _email;
        password = _password;
        isInstructor = _instrctor;
    }

    public void printUser(){
        System.out.println("ID: " + id + " Email: " + email + " Password: " + password + " isinstrctor: " + isInstructor);
    }

    public int getIsInstructor() {
        return isInstructor;
    }

    //  Sign Up Methods
    public boolean newUserValidation() throws SQLException {
        boolean returnFlag = true;
// -- Check For Same Email --
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "SELECT COUNT(*) FROM users WHERE email=\'" + email + "\';";
        try{
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            int userWithEmail = rs.getInt(1);
            if(userWithEmail > 0){
                validationMessage = "Email already exists.";
                returnFlag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        Password validation
        if(password.length() < 4){
            validationMessage = "Password must be 4 digits long.";
            returnFlag = false;
        }

        conn.close();
        return returnFlag;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public boolean saveUser() throws IOException, SQLException {
        Log logs = new Log();

        Connection conn = DBconnection.DBconnect();
        String sqlQ = "CREATE Table users (id INT AUTO_INCREMENT PRIMARY KEY, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, isinstructor INT NOT NULL);";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            logs.info("USERS table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO users(email, password, isinstructor) VALUES(?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, email);
            sqlSt.setString(2, password);
            sqlSt.setInt(3, isInstructor);
            sqlSt.executeUpdate();
            logs.info("New user created");
            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            logs.warning("New user creation failed");
            conn.close();
            return false;
        }
    }

}
