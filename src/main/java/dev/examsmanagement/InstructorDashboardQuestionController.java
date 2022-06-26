package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
import dev.examsmanagement.model.CQSubmission;
import dev.examsmanagement.model.Question;
import dev.examsmanagement.model.Test;
import dev.examsmanagement.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class InstructorDashboardQuestionController extends InstructorNavController{
    @FXML
    private ListView<CQSubmission> evaluatedSubmission, unevaluatedSubmission;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT * FROM cqsubmissions WHERE evaluated=0 AND test=" + Session.sessTest.getId() + " ;";
        try{
            Statement splSt = conn.createStatement();
            ResultSet rs = splSt.executeQuery(sqlQ);
            while (rs.next()){
                String qUser = "SELECT * FROM users WHERE email=\'" + rs.getString("student") + "\';";
                String qQuestion = "SELECT * FROM questions WHERE id=" + rs.getInt("question") + ";";

                Statement stUser = conn.createStatement();
                ResultSet rUser = stUser.executeQuery(qUser);
                if(DBconnection.database == DBconnection.mysqlDB) { rUser.next();}

                Statement stQuestion = conn.createStatement();
                ResultSet rQuestion = stQuestion.executeQuery(qQuestion);
                if(DBconnection.database == DBconnection.mysqlDB) { rQuestion.next();}

                Test test = Session.sessTest;
                User student = new User(rUser.getString("name"), rUser.getString("email"), rUser.getInt("isinstructor"));
                Question question = new Question(rQuestion.getInt("id"), rQuestion.getString("title"), rQuestion.getInt("points"), test);

                CQSubmission cqSubmission = new CQSubmission(rs.getInt("id"), student, test, question, rs.getString("answer"));
                unevaluatedSubmission.getItems().add(cqSubmission);
            }

            sqlQ = "SELECT * FROM cqsubmissions WHERE evaluated=1 AND test=" + Session.sessTest.getId() + " ;";
            splSt = conn.createStatement();
            rs = splSt.executeQuery(sqlQ);
            while (rs.next()){
                String qUser = "SELECT * FROM users WHERE email=\'" + rs.getString("student") + "\';";
                String qQuestion = "SELECT * FROM questions WHERE id=" + rs.getInt("question") + ";";

                Statement stUser = conn.createStatement();
                ResultSet rUser = stUser.executeQuery(qUser);
                if(DBconnection.database == DBconnection.mysqlDB) { rUser.next();}

                Statement stQuestion = conn.createStatement();
                ResultSet rQuestion = stQuestion.executeQuery(qQuestion);
                if(DBconnection.database == DBconnection.mysqlDB) { rQuestion.next();}

                Test test = Session.sessTest;
                User student = new User(rUser.getString("name"), rUser.getString("email"), rUser.getInt("isinstructor"));
                Question question = new Question(rQuestion.getInt("id"), rQuestion.getString("title"), rQuestion.getInt("points"), test);

                CQSubmission cqSubmission = new CQSubmission(rs.getInt("id"), student, test, question, rs.getString("answer"));
                evaluatedSubmission.getItems().add(cqSubmission);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

//        -- unevaluated selection
        unevaluatedSubmission.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CQSubmission>() {
            @Override
            public void changed(ObservableValue<? extends CQSubmission> observableValue, CQSubmission cqSubmission, CQSubmission t1) {
                Session.sessCQSubmission = unevaluatedSubmission.getSelectionModel().getSelectedItem();
                try{
                    Session.switchScene("InstructorCQEvaluationView.fxml");
                }
                catch (IOException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @FXML
    public void publishResult() throws IOException {
        Session.sessTest.publishResult();
        Session.switchScene("InstructorDashboardView.fxml");
    }
}
