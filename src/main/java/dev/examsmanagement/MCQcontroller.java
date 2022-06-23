package dev.examsmanagement;

import dev.examsmanagement.model.MCQquestion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class MCQcontroller extends QuestionController{

    public TextField option1, option2, option3, option4;
    public CheckBox option1Check, option2Check, option3Check, option4Check;

    @Override
    protected void saveQuestion() throws SQLException, IOException {
        String[] options = new String[4];
        options[0] = option1.getText();
        options[1] = option2.getText();
        options[2] = option3.getText();
        options[3] = option4.getText();

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

        MCQquestion newMCQ = new MCQquestion(questionTitle.getText(), Integer.parseInt(questionPoint.getText()),options,correctIndex, Session.sessTest);
        newMCQ.saveQuestion();
    }
}
