package dev.examsmanagement;

import dev.examsmanagement.modal.Question;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {
    public Button profileBtn;
    public TextField questionPoint;
    public TextArea questionTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

    }

    protected void saveQuestion() throws SQLException, IOException {
        Question newQuestion = new Question(questionTitle.getText(), Integer.parseInt(questionPoint.getText()), Session.sessTest);
        System.out.println(newQuestion.toString());
        newQuestion.saveQuestion();
    }

    @FXML
    protected void addQuestion(ActionEvent event) throws IOException, SQLException {
        saveQuestion();
        Session.switchScene("QuestionCreateView.fxml", event);
    }

    @FXML
    protected void addMCQ(ActionEvent event) throws IOException, SQLException {
        saveQuestion();
        Session.switchScene("MCQCreateView.fxml", event);
    }

    @FXML
    protected void createTest(ActionEvent event) throws IOException, SQLException {
        saveQuestion();
        Session.switchScene("InstructorView.fxml", event);
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
