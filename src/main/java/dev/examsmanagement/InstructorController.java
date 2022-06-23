package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.Course;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class InstructorController extends InstructorNavController {
//    public Button profileBtn;
    public ListView<Course> courseList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        --- Set up top profile name ---
        profileBtn.setText(Session.getCurrentUser().getName());

//        --- Get all courses of this instructor ---
        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT * FROM courses WHERE instructor=\'" + Session.getCurrentUser().getEmail() + "\'  ORDER BY id DESC;";

        try {
//            --- Run query to bring all courses ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()) {
                Course course = new Course(rs.getInt("id"), rs.getString("title"), rs.getString("description"), Session.getCurrentUser());
                courseList.getItems().add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        --- If course selected then save course to session and forward to course edit view
        courseList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {
            @Override
            public void changed(ObservableValue<? extends Course> observableValue, Course course, Course t1) {
                Session.sessCourse = courseList.getSelectionModel().getSelectedItem();

                try {
                    Session.switchScene("CourseEditView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
