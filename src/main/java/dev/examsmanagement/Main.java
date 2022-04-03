package dev.examsmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Log logs = new Log();
        Session.primaryStage = stage;

//        Database Connection
        Connection conn = dev.examsmanagement.db.DBconnection.DBconnect();
        if(conn != null){
            logs.fine("Database Connection Successful");
            conn.close();
        }else{
            logs.warning("Database Connection Failed");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Exams");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}