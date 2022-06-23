package dev.examsmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddStudentController extends InstructorNavController {
    public TextArea studentString;

    @FXML
    protected void addStudentsToCourse(){
        String[] studentList = studentString.getText().split(";", -1);
        Session.sessCourse.addStudents(studentList);
    }

    @FXML
    protected void createCourseWithoutTest(ActionEvent event) throws IOException {
        addStudentsToCourse();
        Session.switchScene("InstructorView.fxml", event);
    }

    @FXML
    protected void createCourseAndAddTest(ActionEvent event) throws IOException {
        addStudentsToCourse();
        Session.switchScene("TestCreateView.fxml", event);
    }

}
