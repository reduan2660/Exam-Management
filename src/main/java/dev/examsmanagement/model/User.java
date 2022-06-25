package dev.examsmanagement.model;

import dev.examsmanagement.Log;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.*;

public class User {
    private String name, email, password;
    private int isInstructor;
    private String validationMessage;

    public User(String _name,String _email, String _password, int _instrctor){
        name = _name;
        email = _email;
        password = _password;
        isInstructor = _instrctor;
    }

    public User(String _name, String _email, int _instrctor){
        name = _name;
        email = _email;
        isInstructor = _instrctor;
    }

    public void printUser(){
        System.out.println(" Email: " + email + " Password: " + password + " isinstrctor: " + isInstructor);
    }

    public int getIsInstructor() {
        return isInstructor;
    }
    public String getName(){
        return name;
    }
    public String getEmail() {
        return email;
    }

    //  Sign Up Methods
    public boolean newUserValidation() throws SQLException {
        boolean returnFlag = true;
// -- Check For Same Email --
        Connection conn = DBconnection.conn;
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

        return returnFlag;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public boolean saveUser() throws IOException, SQLException {
        Connection conn = DBconnection.conn;
        String sqlQ = "CREATE Table users (email VARCHAR(255) NOT NULL PRIMARY KEY, name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, isinstructor INT NOT NULL);";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("Users table created");
        } catch (SQLException e) {
//            e.printStackTrace();
            Log.warning("Users table creation failed");
        }

        sqlQ = "INSERT INTO users(name, email, password, isinstructor) VALUES(?,?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, name);
            sqlSt.setString(2, email);
            sqlSt.setString(3, password);
            sqlSt.setInt(4, isInstructor);
            sqlSt.executeUpdate();
            Log.info("New user added");

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            Log.warning("New user creation failed");
            return false;
        }
    }

}
