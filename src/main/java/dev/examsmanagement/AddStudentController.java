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

public class AddStudentController implements Initializable {
    public Button profileBtn;
    public TextArea studentString;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
    }

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


//    --- Navigation ---
    @FXML
    protected void allCourse(ActionEvent event) throws IOException {
        Session.switchScene("InstructorView.fxml", event);
    }

    @FXML
    protected void logout(ActionEvent event) throws IOException {
        Session.logout();

//        -- switch scene to login --
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginView.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
