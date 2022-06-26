package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.Course;
import dev.examsmanagement.model.Test;
import dev.examsmanagement.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class StudentDashboardController extends StudentNavController{

    @FXML
    private ListView<Test> testList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

        Connection conn = DBconnection.conn;
        String qCourses = "SELECT * FROM course_student WHERE student=\'" + Session.getCurrentUser().getEmail() + "\';";
        try {
            Statement stCourses = conn.createStatement();
            ResultSet rsCourses = stCourses.executeQuery(qCourses);

            while (rsCourses.next()){
                String qCourse = "SELECT * FROM courses WHERE id=" + rsCourses.getInt("course") + ";";
                Statement stCourse = conn.createStatement();
                ResultSet rsCourse = stCourse.executeQuery(qCourse);

                String qInstructor = "SELECT * FROM users WHERE email=\'" + rsCourse.getString("instructor") +"\';";
                Statement stInstructor = conn.createStatement();
                ResultSet rsInstructor = stInstructor.executeQuery(qInstructor);

                User instructor = new User(rsInstructor.getString("name"), rsInstructor.getString("email"), rsInstructor.getInt("isinstructor"));
                Course course = new Course(rsCourse.getInt("id"), rsCourse.getString("title"), rsCourse.getString("description"), instructor);

                String qTest = "SELECT * FROM tests WHERE course=" + rsCourses.getInt("course") + ";";
                Statement stTest = conn.createStatement();
                ResultSet rsTest = stTest.executeQuery(qTest);

                while(rsTest.next()){

                    LocalDateTime t = Test.toTime(rsTest.getString("time"));
                    boolean allowLateSubmission, randomQuestions;
                    if(rsTest.getInt("allowLateSubmission") == 1){
                        allowLateSubmission = true;
                    }
                    else{
                        allowLateSubmission = false;
                    }

                    if(rsTest.getInt("randomQuestions") == 1){
                        randomQuestions = true;
                    }
                    else{
                        randomQuestions = false;
                    }

                    Test test = new Test(rsTest.getInt("id"), rsTest.getString("title"), rsTest.getString("instructions"), t, rsTest.getInt("duration"), allowLateSubmission, randomQuestions, course);
                    testList.getItems().add(test);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //        --- If test selected then save test to session and forward to test edit view
        testList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Test>() {
            @Override
            public void changed(ObservableValue<? extends Test> observableValue, Test test, Test t1) {
                Session.sessTest = testList.getSelectionModel().getSelectedItem();
                try {
                    Session.switchScene("StudentResultView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
