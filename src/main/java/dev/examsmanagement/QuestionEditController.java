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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class QuestionEditController extends InstructorNavController {

    public TextField questionPoint;
    public TextArea questionTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
        questionPoint.setText(Integer.toString(Session.sessQuestion.getPoints()));
        questionTitle.setText(Session.sessQuestion.getQuestion());
    }
    @FXML
    protected void updateQuestion() throws SQLException, IOException {
        Session.sessQuestion.setQuestion(questionTitle.getText());
        Session.sessQuestion.setPoints(Integer.parseInt(questionPoint.getText()));
        Session.sessQuestion.updateQuestion();

        Session.switchScene("TestEditView.fxml");
    }
}
