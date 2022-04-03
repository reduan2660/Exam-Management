package dev.examsmanagement.modal;
import dev.examsmanagement.Log;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class MCQquestion extends Question {

    private String[] options = new String[4];
    private int correctOptionIndex;

    public MCQquestion(String _question, int _points, String[] _options, int _correctOptionIndex, Test _test){
        super(_question, _points, _test);
        options = _options;
        correctOptionIndex = _correctOptionIndex;
    }

    public MCQquestion(int _id,String _question,int _points, String[] _options, int _correctOptionIndex,Test _test){
        super(_id,_question, _points, _test);
        options = _options;
        correctOptionIndex = _correctOptionIndex;
    }

    @Override
    public String toString() {
        return "MCQquestion{" +
                "options=" + Arrays.toString(options) +
                ", correctOptionIndex=" + correctOptionIndex +
                ", id=" + id +
                ", question='" + question + '\'' +
                ", points=" + points +
                ", givenPoint=" + givenPoint +
                ", test=" + test +
                '}';
    }

    @Override
    public boolean saveQuestion() throws IOException, SQLException {
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "CREATE Table mcqquestions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "points INTEGER NOT NULL,"+
                "givenPoint INTEGER NOT NULL,"+
                "option1 VARCHAR(255),"+
                "option2 VARCHAR(255),"+
                "option3 VARCHAR(255),"+
                "option4 VARCHAR(255),"+
                "correctOption INTEGER NOT NULL,"+
                "test INTEGER NOT NULL"+
                ");";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("mcqquestions table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO mcqquestions (title, points, givenPoint, option1, option2, option3, option4, correctOption, test) VALUES(?,?,?,?,?,?,?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, question);
            sqlSt.setInt(2, points);
            sqlSt.setInt(3, givenPoint);
            sqlSt.setString(4, options[0]);
            sqlSt.setString(5, options[1]);
            sqlSt.setString(6, options[2]);
            sqlSt.setString(7, options[3]);
            sqlSt.setInt(8, correctOptionIndex);
            sqlSt.setInt(9, test.getId());

            sqlSt.executeUpdate();

            Log.info("New question added");

//          -- Set up id --
            sqlQ = "SELECT * FROM mcqquestions ORDER BY id DESC LIMIT 1";

            try{
//            --- Run query to bring all courses ---
                Statement sqlSt2 = conn.createStatement();
                ResultSet rs = sqlSt2.executeQuery(sqlQ);

                if (rs.next()){
                    id = rs.getInt("id");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            Log.warning("New mcqquestions creation failed");
            conn.close();
            return false;
        }
    }

}
