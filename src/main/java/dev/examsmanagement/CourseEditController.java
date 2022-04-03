package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.modal.Course;
import dev.examsmanagement.modal.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CourseEditController implements Initializable {
    public Button profileBtn;
    public TextField courseTitle;
    public TextArea courseDesc;

    public ListView<Test> testList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //        --- Set up top profile name ---
        profileBtn.setText(Session.getCurrentUser().getName());
        courseTitle.setText(Session.sessCourse.getTitle());
        courseDesc.setText(Session.sessCourse.getDescription());

//        --- Get all tests of this course ---
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "SELECT * FROM tests WHERE course=" + Session.sessCourse.getId() + " ORDER BY id DESC;";

        try {
//            --- Run query to bring all courses ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()) {
                LocalDateTime t = Test.toTime(rs.getString("time"));
                boolean allowLateSubmission, randomQuestions;
                if(rs.getInt("allowLateSubmission") == 1){
                    allowLateSubmission = true;
                }
                else{
                    allowLateSubmission = false;
                }

                if(rs.getInt("randomQuestions") == 1){
                    randomQuestions = true;
                }
                else{
                    randomQuestions = false;
                }

                Test test = new Test(rs.getInt("id"), rs.getString("title"), rs.getString("instructions"), t, allowLateSubmission, randomQuestions, Session.sessCourse);
                testList.getItems().add(test);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    protected void createTest() throws IOException {
        Session.switchScene("TestCreateView.fxml");
    }

    @FXML
    protected void addStudents() throws IOException {
        System.out.println("Add students");
//        Session.switchScene("AddStudentToCourseView.fxml");
    }
//    --- Navigation ---

    @FXML
    protected void createCourse() throws IOException {
        Session.switchScene("CourseCreateView.fxml"); // Switch scene to CourseCreateView
    }

    @FXML
    protected void allCourse() throws IOException {
        Session.switchScene("InstructorView.fxml");
    }

    @FXML
    protected void logout() throws IOException {
        Session.logout();
        Session.switchScene("LoginView.fxml"); // Switch scene to login
    }
}
