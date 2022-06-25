package dev.examsmanagement;

import dev.examsmanagement.model.CQSubmission;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CQSubmissionController extends StudentNavController{
    @FXML
    private Text questionTitle;
    @FXML
    private TextArea answer;
    @FXML
    private Button submitBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
        questionTitle.setText(Session.sessQuestion.getQuestion());
    }

    @FXML
    public void submitAnswer() throws IOException {
        CQSubmission cqSubmission = new CQSubmission(Session.getCurrentUser(), Session.sessTest, Session.sessQuestion, answer.getText());
        if(cqSubmission.saveSubmission()){
            Session.switchScene("StudentTestQuestionView.fxml");
        }
        else{
            submitBtn.setText("Try Again!");
        }
    }

}
