package dev.examsmanagement;

import dev.examsmanagement.db.DBconnection;
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

public class InstuctorDashboardStudentController extends InstructorNavController{
    @FXML
    private ListView<User> studentList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileBtn.setText(Session.getCurrentUser().getName());

        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT * FROM course_student WHERE course=" + Session.sessTest.getCourse().getId() + ";";

        try{
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            while(rs.next()){

                String qUser = "SELECT * FROM users WHERE email=\'" + rs.getString("student") + "\';";
                Statement stUser = conn.createStatement();
                ResultSet rUser = stUser.executeQuery(qUser);

                User student = new User(rUser.getString("name"), rUser.getString("email"), rUser.getInt("isinstructor"));
                studentList.getItems().add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        If selected
        studentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
                Session.sessStudent = studentList.getSelectionModel().getSelectedItem();
                try{
                    Session.switchScene("InstructorStudentResultView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

