package dev.examsmanagement;

import dev.examsmanagement.model.CQSubmission;
import dev.examsmanagement.model.MCQSubmission;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MCQSubmissionController extends StudentNavController{
    @FXML
    private Text questionTitle, questionPoint;
    @FXML
    public Text option1, option2, option3, option4;
    @FXML
    public CheckBox option1Check, option2Check, option3Check, option4Check;
    @FXML
    private Button submitBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
        questionTitle.setText(Session.sessMCQ.getQuestion());
        questionPoint.setText(String.valueOf(Session.sessMCQ.getPoints()));
        option1.setText(Session.sessMCQ.getOptions()[0]);
        option2.setText(Session.sessMCQ.getOptions()[1]);
        option3.setText(Session.sessMCQ.getOptions()[2]);
        option4.setText(Session.sessMCQ.getOptions()[3]);
    }

    @FXML
    public void submitAnswer() {

        int answer = 0, givenpoint=0;
        if(option1Check.isSelected()){ answer=1; }
        if(option2Check.isSelected()){ answer=2; }
        if(option3Check.isSelected()){ answer=3; }
        if(option4Check.isSelected()){ answer=4; }

        if(answer == Session.sessMCQ.getCorrectOptionIndex()){
            givenpoint = Session.sessMCQ.getPoints();
        }

        MCQSubmission mcqSubmission = new MCQSubmission(Session.getCurrentUser(), Session.sessTest, Session.sessMCQ, answer, givenpoint);

        if(mcqSubmission.checkSubmission()){
            if(mcqSubmission.saveSubmission()){
                submitBtn.setText("Submitted.");
            }
            else{
                submitBtn.setText("Try Again!");
            }
        }
        else{
            submitBtn.setText("Already Submitted!");
        }
    }

    @FXML
    public void goback() throws IOException {
        Session.switchScene("StudentTestQuestionView.fxml");
    }
}
