package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.Course;
import dev.examsmanagement.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentController extends StudentNavController {
    public ListView<Course> courseList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        profileBtn.setText(Session.getCurrentUser().getName());

        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT course FROM course_student WHERE student=\'" + Session.getCurrentUser().getEmail() + "\' ORDER BY id DESC;";
        try {
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()){
                String sqlQ2 = "SELECT * FROM courses WHERE id=" + rs.getInt("course") + ";";
                Statement sqlSt2 = conn.createStatement();
                ResultSet rs2 = sqlSt2.executeQuery(sqlQ2);

                while (rs2.next()){

                    String sqlQIns = "SELECT name, email FROM users WHERE email=\'" + rs2.getString("instructor") + "\';";
                    Statement sqlStIns = conn.createStatement();
                    ResultSet rsIns = sqlStIns.executeQuery(sqlQIns);

                    if(DBconnection.database == DBconnection.mysqlDB) { rsIns.next();}
                    User CourseInstructor = new User(rsIns.getString("name"), rsIns.getString("email"), 1);

                    Course course = new Course(rs2.getInt("id"), rs2.getString("title"), rs2.getString("description"), CourseInstructor);
                    courseList.getItems().add(course);

                    System.out.println(course);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }


//      Course is selected
        courseList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {
            @Override
            public void changed(ObservableValue<? extends Course> observableValue, Course course, Course t1) {
                Session.sessCourse = courseList.getSelectionModel().getSelectedItem();

                try {
                    Session.switchScene("StudentTestView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
