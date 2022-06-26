package dev.examsmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InstructorCQEvaluationController extends InstructorNavController{

    @FXML
    private Text question, answer, points;
    @FXML
    private TextField evaluationPoint;
    @FXML
    private Button evaluationBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

        question.setText(Session.sessCQSubmission.getQuestion().getQuestion());
        answer.setText(Session.sessCQSubmission.getAnswer());
        points.setText(String.valueOf(Session.sessCQSubmission.getQuestion().getPoints()));
    }

    @FXML
    public void evaluate(){
        Session.sessCQSubmission.evaluate(Integer.parseInt(evaluationPoint.getText()));
        evaluationBtn.setText("Evaluated");
    }

    @FXML
    public void goback() throws IOException {
        Session.switchScene("InstructorDashboardQuestionView.fxml");
    }
}
