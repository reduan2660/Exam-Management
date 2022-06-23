package dev.examsmanagement;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MCQEditcontroller extends QuestionEditController{

    public TextField option1, option2, option3, option4;
    public CheckBox option1Check, option2Check, option3Check, option4Check;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
        questionPoint.setText(Integer.toString(Session.sessMCQ.getPoints()));
        questionTitle.setText(Session.sessMCQ.getQuestion());

        String[] options = Session.sessMCQ.getOptions();
        option1.setText(options[0]);
        option2.setText(options[1]);
        option3.setText(options[2]);
        option4.setText(options[3]);

        option1Check.setSelected(false);
        option2Check.setSelected(false);
        option3Check.setSelected(false);
        option4Check.setSelected(false);

        if(Session.sessMCQ.getCorrectOptionIndex() == 1){
            option1Check.setSelected(true);
        }
        else if(Session.sessMCQ.getCorrectOptionIndex() == 2){
            option2Check.setSelected(true);
        }
        else if(Session.sessMCQ.getCorrectOptionIndex() == 3){
            option3Check.setSelected(true);
        }
        else if(Session.sessMCQ.getCorrectOptionIndex() == 4){
            option4Check.setSelected(true);
        }

    }

    @Override
    protected void updateQuestion() throws SQLException, IOException {

        Session.sessMCQ.setOptions(option1.getText(), option2.getText(), option3.getText(), option4.getText());
        int correctIndex = 1;
        if(option1Check.isSelected()){
            correctIndex = 1;
        }
        else if (option2Check.isSelected()){
            correctIndex = 2;
        }
        else if(option3Check.isSelected()){
            correctIndex = 3;
        }
        else if(option4Check.isSelected()){
            correctIndex = 4;
        }

        Session.sessMCQ.setCorrectOptionIndex(correctIndex);
        Session.sessMCQ.setQuestion(questionTitle.getText());
        Session.sessMCQ.setPoints(Integer.parseInt(questionPoint.getText()));
        Session.sessMCQ.updateQuestion();

        Session.switchScene("TestEditView.fxml");
    }
}
