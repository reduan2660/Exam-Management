package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.modal.Course;
import dev.examsmanagement.modal.MCQquestion;
import dev.examsmanagement.modal.Question;
import dev.examsmanagement.modal.Test;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TestEditController implements Initializable {
    public Button profileBtn;
    public TextField testTitle, testTime;
    public TextArea testInstructions;

    public ListView<Question> questionList;
    public ListView<MCQquestion> mcqQuestionList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //        --- Set up top profile name ---
        profileBtn.setText(Session.getCurrentUser().getName());
        testTitle.setText(Session.sessTest.getTitle());
        testTime.setText(Session.sessTest.getTime().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yy")));
        testInstructions.setText(Session.sessTest.getInstructions());

//        --- Get all questions of this course ---
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "SELECT * FROM questions WHERE test=" + Session.sessTest.getId() + " ORDER BY id DESC;";

        try {
//            --- Run query to bring all courses ---
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while (rs.next()) {
                Question question = new Question(rs.getInt("id"),rs.getString("title"), rs.getInt("points"), Session.sessTest);
                questionList.getItems().add(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        --- Get all mcq of this course ---
        sqlQ = "SELECT * FROM mcqquestions WHERE test=" + Session.sessTest.getId() + " ORDER BY id DESC;";

        try {
//            --- Run query to bring all courses ---
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

//        --- Close connection ---
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    protected void addQuestion() throws IOException {
        Session.switchScene("QuestionCreateView.fxml");
    }

    @FXML
    protected void addMCQ() throws IOException {
        Session.switchScene("MCQCreateView.fxml");
    }

    @FXML
    protected void changeTime() throws IOException {
//        Session.switchScene("TestCreateView.fxml");
    }

    @FXML
    protected void addStudents() throws IOException {
        System.out.println("Add students");
//        Session.switchScene("AddStudentToCourseView.fxml");
    }
//    --- Navigation ---

    @FXML
    protected void createCourse() throws IOException {
        Session.switchScene("CourseCreateView.fxml"); // Switch scene to CourseCreateView
    }

    @FXML
    protected void allCourse() throws IOException {
        Session.switchScene("InstructorView.fxml");
    }

    @FXML
    protected void logout() throws IOException {
        Session.logout();
        Session.switchScene("LoginView.fxml"); // Switch scene to login
    }
}
