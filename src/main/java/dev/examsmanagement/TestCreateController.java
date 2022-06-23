package dev.examsmanagement;

import dev.examsmanagement.model.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class TestCreateController extends InstructorNavController {
    public TextField testTitle;
    public TextArea testInstruction;
    public DatePicker testTime;
    public ChoiceBox testHour, testMinute, testAMPM;
    public CheckBox testLateSubmission;
    public CheckBox testRandomQuestion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

        int[] hours = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] minutes = {0, 15, 30, 45};
        String[] ampm = {"AM", "PM"};

        for(int i=0;i<hours.length;i++){
            testHour.getItems().add(hours[i]);
        }
        for(int i=0;i<minutes.length;i++){
            testMinute.getItems().add(minutes[i]);
        }
        testAMPM.getItems().addAll(ampm);

        testLateSubmission.setSelected(true);
        testRandomQuestion.setSelected(true);
    }

    protected void addTest() throws SQLException, IOException{

//        --- Process time input ---
        LocalDate date = testTime.getValue();
        LocalDateTime time = Test.toTime(date.toString(), Integer.parseInt(testHour.getValue().toString()), Integer.parseInt(testMinute.getValue().toString()), testAMPM.getValue().toString());

//        --- Store the test ---
        Test newTest = new Test(testTitle.getText(), testInstruction.getText(), time, testLateSubmission.isSelected(), testRandomQuestion.isSelected(), Session.sessCourse);
        newTest.saveTest();

        Session.sessTest = newTest;  //   Save to session
    }

    @FXML
    protected void addMCQquestion(ActionEvent event) throws SQLException, IOException {
        addTest();
        Session.switchScene("MCQCreateView.fxml", event);
    }

    @FXML
    protected void addCQquestion(ActionEvent event) throws SQLException, IOException {
        addTest();
        Session.switchScene("QuestionCreateView.fxml", event);
    }

}
