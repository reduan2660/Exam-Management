package dev.examsmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {


//        Database Connection
        if(dev.examsmanagement.db.DBconnection.DBconnect() != null){
            System.out.println("Database Connection Successful");
        }else{
            System.out.println("Database Connection Failed");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignUpView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Exams");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}