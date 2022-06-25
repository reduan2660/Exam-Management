package dev.examsmanagement;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class StudentTestNotOpenController extends StudentTestClosedController{
    @FXML
    private Text testTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());
        testTime.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(Session.sessTest.getTime()));
    }
}
