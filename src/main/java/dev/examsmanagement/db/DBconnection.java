package dev.examsmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection  {
    public static Connection DBconnect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
//        finally {
//            if(conn != null){
//                try{
//                    conn.close();
//                }
//                catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}