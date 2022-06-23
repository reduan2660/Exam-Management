package dev.examsmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dev.examsmanagement.Log;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

public class DBconnection  {
    public static String[] databases = {"mysql", "sqlite"};
    public static String mysqlDB = databases[0];
    public static String sqliteDB = databases[1];

    public static String database = sqliteDB;

    public static Connection conn = null;

    public static Connection DBconnect(){
        Log.info("Creating New " + database + " Database Connection " );
//        Connection conn = null;
//        --- Database config ---
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();

        try {
            if(database == sqliteDB) {
                conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
                return conn;
            }
            else if(database == mysqlDB) {
                String serverName = dotenv.get("mysql_host");
                String mydatabase = dotenv.get("mysql_db");
                String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

                String username = dotenv.get("mysql_username");
                String password = dotenv.get("mysql_password");
                Connection connection = DriverManager.getConnection(url, username, password);
                return connection;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}