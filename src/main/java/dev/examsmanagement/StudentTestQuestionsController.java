package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.MCQquestion;
import dev.examsmanagement.model.Question;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentTestQuestionsController extends StudentNavController{
    public TextArea testInstructions;
    public ListView<Question> questionList;
    public ListView<MCQquestion> mcqQuestionList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

        testInstructions.setText(Session.sessTest.getInstructions());
        //        --- Get all questions of this test ---
        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT * FROM questions WHERE test=" + Session.sessTest.getId() + " ORDER BY id DESC;";

        try {
//            --- Run query to bring all questions ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()) {
                Question question = new Question(rs.getInt("id"),rs.getString("title"), rs.getInt("points"), Session.sessTest);
                questionList.getItems().add(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        --- Get all mcq of this test ---
        sqlQ = "SELECT * FROM mcqquestions WHERE test=" + Session.sessTest.getId() + " ORDER BY id DESC;";

        try {
//            --- Run query to bring all mcq ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()) {
                String options[] = new String[4];
                options[0] = rs.getString("option1");
                options[1] = rs.getString("option2");
                options[2] = rs.getString("option3");
                options[3] = rs.getString("option4");

                MCQquestion mcqQuestion = new MCQquestion(rs.getInt("id"),rs.getString("title"), rs.getInt("points"), options, rs.getInt("correctOption"), Session.sessTest);
                mcqQuestionList.getItems().add(mcqQuestion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //   --- CQ Selection
        questionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Question>() {
            @Override
            public void changed(ObservableValue<? extends Question> observableValue, Question question, Question t1) {
                Session.sessQuestion = questionList.getSelectionModel().getSelectedItem();
                try {
                    Session.switchScene("CQSubmissionView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @FXML
    public void submitTest() throws IOException {
        Session.switchScene("StudentView.fxml");
    }
}
