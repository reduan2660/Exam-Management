package dev.examsmanagement;

import dev.examsmanagement.modal.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateCourseController implements Initializable {
    @FXML
    public TextField newcourseTitle;
    public TextArea newcourseDesc;
    public Button profileBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
    }

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

//    --- Navigation ---
    @FXML
    protected void allCourse(ActionEvent event) throws IOException {
        Session.switchScene("InstructorView.fxml", event);
    }

    @FXML
    protected void logout(ActionEvent event) throws IOException {
        Session.logout();
        Session.switchScene("LoginView.fxml", event);
    }

}
