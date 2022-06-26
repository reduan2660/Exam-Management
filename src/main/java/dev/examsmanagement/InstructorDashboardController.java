package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.Course;
import dev.examsmanagement.model.Test;
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

public class InstructorDashboardController extends InstructorNavController{
    @FXML
    private ListView<Test> publishedTestList, unpublishedTestList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

//        -- Get all courses of this instructor
        Connection conn = DBconnection.conn;
        String sqlCourse = "SELECT * FROM courses WHERE instructor=\'" + Session.getCurrentUser().getEmail() + "\' ;";
        try {
            Statement sqlStCourse = conn.createStatement();
            ResultSet rsCourse = sqlStCourse.executeQuery(sqlCourse);

            while(rsCourse.next()){
                Course course = new Course(rsCourse.getInt("id"), rsCourse.getString("title"), rsCourse.getString("description"), Session.getCurrentUser());

//           --- Get all published tests of this course ---
                String sqlPublished = "SELECT * FROM tests WHERE resultPublished=1 AND course=" + course.getId() + " ORDER BY id DESC;";
                Statement sqlSt = conn.createStatement();
                ResultSet rsPublished = sqlSt.executeQuery(sqlPublished);

                while (rsPublished.next()) {
                    LocalDateTime t = Test.toTime(rsPublished.getString("time"));
                    boolean allowLateSubmission, randomQuestions;
                    if (rsPublished.getInt("allowLateSubmission") == 1) {
                        allowLateSubmission = true;
                    } else {
                        allowLateSubmission = false;
                    }

                    if (rsPublished.getInt("randomQuestions") == 1) {
                        randomQuestions = true;
                    } else {
                        randomQuestions = false;
                    }

                    Test test = new Test(rsPublished.getInt("id"), rsPublished.getString("title"), rsPublished.getString("instructions"), t, rsPublished.getInt("duration"), allowLateSubmission, randomQuestions, course);
                    publishedTestList.getItems().add(test);
                }

//           --- Get all unpublished tests of this course ---
                String sqlUnpublished = "SELECT * FROM tests WHERE resultPublished=0 AND course=" + course.getId() + " ORDER BY id DESC;";
                Statement sqlStUnpublished = conn.createStatement();
                ResultSet rsUnpublished = sqlStUnpublished.executeQuery(sqlUnpublished);

                while (rsUnpublished.next()) {
                    LocalDateTime t = Test.toTime(rsUnpublished.getString("time"));
                    boolean allowLateSubmission, randomQuestions;
                    if (rsUnpublished.getInt("allowLateSubmission") == 1) {
                        allowLateSubmission = true;
                    } else {
                        allowLateSubmission = false;
                    }

                    if (rsUnpublished.getInt("randomQuestions") == 1) {
                        randomQuestions = true;
                    } else {
                        randomQuestions = false;
                    }

                    Test test = new Test(rsUnpublished.getInt("id"), rsUnpublished.getString("title"), rsUnpublished.getString("instructions"), t, rsUnpublished.getInt("duration"), allowLateSubmission, randomQuestions, course);
                    unpublishedTestList.getItems().add(test);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //        --- If unpublished test is selected then save test to session and forward to dashboard question view
        unpublishedTestList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Test>() {
            @Override
            public void changed(ObservableValue<? extends Test> observableValue, Test test, Test t1) {
                Session.sessTest = unpublishedTestList.getSelectionModel().getSelectedItem();
                try {
                    Session.switchScene("InstructorDashboardQuestionView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //        --- If published test is selected then save test to session and forward to dashboard question view
        publishedTestList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Test>() {
            @Override
            public void changed(ObservableValue<? extends Test> observableValue, Test test, Test t1) {
                Session.sessTest = publishedTestList.getSelectionModel().getSelectedItem();
                try {
                    Session.switchScene("InstructorDashboardStudentView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
