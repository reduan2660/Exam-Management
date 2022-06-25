package dev.examsmanagement;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class StudentTestClosedController extends StudentNavController{
    @FXML
    public void goback() throws IOException {
        Session.switchScene("StudentTestView.fxml");
    }
}
