package dev.examsmanagement.modal;

import dev.examsmanagement.db.DBconnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class User {
    private int id;
    private String email, password;
    private int instrctor;

    public User(String _email, String _password, int _instrctor){
        email = _email;
        password = _password;
        instrctor = _instrctor;

        saveUser();
    }

    boolean saveUser(){
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "CREATE Table users (id int, email varchar(255), password varchar(255), isinstrctor int);";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            System.out.println("User table created");
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("User table already exists!");
        }

        sqlQ = "INSERT INTO users(email, password, isinstrctor) VALUES(?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, email);
            sqlSt.setString(2, password);
            sqlSt.setInt(3, instrctor);
            sqlSt.executeUpdate();
            System.out.println("User entried");
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Not entried");
            return false;
        }
    }
}
