package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class InstructorStudentResultController extends InstructorNavController {

    @FXML
    private Text points;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

        Connection conn = DBconnection.conn;

        String qCQTotalPoints = "SELECT SUM(points) FROM questions WHERE test=" + Session.sessTest.getId() + ";";
        String qMCQTotalPoints = "SELECT SUM(points) FROM mcqquestions WHERE test=" + Session.sessTest.getId() + ";";

        String qCQGivenPoint = "SELECT SUM(givenpoints) FROM cqsubmissions WHERE student=\'" + Session.sessStudent.getEmail() + "\' AND test=" + Session.sessTest.getId() + ";";
        String qMCQGivenPoint = "SELECT SUM(givenpoints) FROM mcqsubmissions WHERE student=\'" + Session.sessStudent.getEmail() + "\' AND test=" + Session.sessTest.getId() + ";";

        try {
            Statement stCQTotalPoints = conn.createStatement();
            ResultSet rCQTotalPoints = stCQTotalPoints.executeQuery(qCQTotalPoints);

            Statement stMCQTotalPoints = conn.createStatement();
            ResultSet rMCQTotalPoints = stMCQTotalPoints.executeQuery(qMCQTotalPoints);

            Statement stCQGivenPoint = conn.createStatement();
            ResultSet rCQGivenPoint = stCQGivenPoint.executeQuery(qCQGivenPoint);

            Statement stMCQGivenPoint = conn.createStatement();
            ResultSet rMCQGivenPoint = stMCQGivenPoint.executeQuery(qMCQGivenPoint);

            if(DBconnection.database == DBconnection.mysqlDB) {
                rCQTotalPoints.next(); rMCQTotalPoints.next();
                rCQGivenPoint.next(); rMCQGivenPoint.next();
            }

            int totalPoint = rCQTotalPoints.getInt(1) + rMCQTotalPoints.getInt(1);
            int givenPoint = rCQGivenPoint.getInt(1) + rMCQGivenPoint.getInt(1);

            points.setText(String.valueOf(givenPoint) + " / " + String.valueOf(totalPoint));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void goback(ActionEvent event) throws IOException {
        Session.switchScene("InstructorDashboardStudentView.fxml", event);
    }
}
