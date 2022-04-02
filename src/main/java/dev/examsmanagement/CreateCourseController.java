package dev.examsmanagement;

import dev.examsmanagement.modal.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class CreateCourseController {
    @FXML
    public TextField newcourseTitle;
    public TextArea newcourseDesc;
    public Button profileBtn;

//    --- Functionalities ---
    @FXML
    protected void createCourseWithoutStudent(ActionEvent event) throws SQLException, IOException {
        Course newcourse = new Course(newcourseTitle.getText(), newcourseDesc.getText(), Session.getCurrentUser());
        newcourse.saveCourse();

//        -- switch scene to all courses --
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InstructorView.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    --- Navigation ---
    @FXML
    protected void allCourse(ActionEvent event) throws IOException {
//        -- switch scene to InstructorView --
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("InstructorView.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
//    For now set for on mouse enter, before a better solution is found
    protected void updateProfileBtn(){
        profileBtn.setText(Session.getCurrentUser().getName());
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
