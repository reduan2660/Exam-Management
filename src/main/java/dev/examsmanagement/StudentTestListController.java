package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.Test;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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

                Test test = new Test(rs.getInt("id"), rs.getString("title"), rs.getString("instructions"), t, allowLateSubmission, randomQuestions, Session.sessCourse);
                testList.getItems().add(test);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
