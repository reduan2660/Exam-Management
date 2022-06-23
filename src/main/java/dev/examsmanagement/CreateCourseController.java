package dev.examsmanagement;

import dev.examsmanagement.model.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateCourseController extends InstructorNavController {
    @FXML
    public TextField newcourseTitle;
    public TextArea newcourseDesc;

    @FXML
    protected void createCourseWithoutStudent(ActionEvent event) throws SQLException, IOException {
        Course newcourse = new Course(newcourseTitle.getText(), newcourseDesc.getText(), Session.getCurrentUser());
        newcourse.saveCourse();

        Session.switchScene("InstructorView.fxml", event);  // Switch scene to add student
    }

    @FXML
    protected void createCourseAndThenNavToStudent(ActionEvent event) throws  SQLException, IOException {
//        -- Create new course and save
        Course newcourse = new Course(newcourseTitle.getText(), newcourseDesc.getText(), Session.getCurrentUser());
        newcourse.saveCourse();

        Session.sessCourse = newcourse;     //  Save Course to session
        Session.switchScene("AddStudentToCourseView.fxml", event);  // Switch scene to add student
    }

}
