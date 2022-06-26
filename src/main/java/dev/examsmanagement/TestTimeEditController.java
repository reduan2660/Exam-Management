package dev.examsmanagement;

import dev.examsmanagement.model.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class TestTimeEditController extends InstructorNavController {
    public TextField testDuration;
    public DatePicker testTime;
    public ChoiceBox testHour, testMinute, testAMPM;
    public Button updateBtn;

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
    }


    @FXML
    public void update() throws IOException {
        LocalDate date = testTime.getValue();
        LocalDateTime time = Test.toTime(date.toString(), Integer.parseInt(testHour.getValue().toString()), Integer.parseInt(testMinute.getValue().toString()), testAMPM.getValue().toString());
        Session.sessTest.setTime(time);

        Session.switchScene("TestEditView.fxml");
    }

}
