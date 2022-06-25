package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.Test;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ResourceBundle;

public class StudentTestListController extends StudentNavController{
    public ListView<Test> testList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        profileBtn.setText(Session.getCurrentUser().getName());

        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT * FROM tests WHERE course=" + Session.sessCourse.getId() + " ORDER BY id DESC;";

        try {
//            --- Run query to bring all tests ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()) {
                LocalDateTime t = Test.toTime(rs.getString("time"));
                boolean allowLateSubmission, randomQuestions;
                if(rs.getInt("allowLateSubmission") == 1){
                    allowLateSubmission = true;
                }
                else{
                    allowLateSubmission = false;
                }

                if(rs.getInt("randomQuestions") == 1){
                    randomQuestions = true;
                }
                else{
                    randomQuestions = false;
                }

                Test test = new Test(rs.getInt("id"), rs.getString("title"), rs.getString("instructions"), t, rs.getInt("duration"), allowLateSubmission, randomQuestions, Session.sessCourse);
                testList.getItems().add(test);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //        --- If test selected then save test to session and forward to test edit view
        testList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Test>() {
            @Override
            public void changed(ObservableValue<? extends Test> observableValue, Test test, Test t1) {
                Session.sessTest = testList.getSelectionModel().getSelectedItem();

                LocalDateTime testStartTime = Session.sessTest.getTime();
                LocalDateTime testEndTime = testStartTime.plusMinutes(Session.sessTest.getDuration());
                LocalDateTime now = LocalDateTime.now();

                Duration diffStart = Duration.between(testStartTime, now);
                Duration diffEnd = Duration.between(testEndTime, now);

                try {
                    if(diffStart.isNegative()){
                        Session.switchScene("TestNotOpenView.fxml");
                    }
                    else{
                        if(diffEnd.isNegative()){
                            Session.switchScene("StudentTestQuestionView.fxml");
                        }
                        else{
                            Session.switchScene("TestClosedView.fxml");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
