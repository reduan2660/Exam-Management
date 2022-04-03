package dev.examsmanagement.modal;

import dev.examsmanagement.Log;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class Question {
    protected int id;
    protected String question;
    protected int points=1, givenPoint = 0;
    protected Test test;

    public Question(String _question, int _points, Test _test){
        question = _question;
        points = _points;
        test = _test;
    }

    public Question(int _id, String _question, int _points, Test _test){
        id = _id;
        question = _question;
        points = _points;
        test = _test;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", points=" + points +
                ", givenPoint=" + givenPoint +
                ", test=" + test +
                '}';
    }

    public boolean saveQuestion() throws IOException, SQLException {
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "CREATE Table questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "points INTEGER NOT NULL,"+
                "givenPoint INTEGER NOT NULL,"+
                "test INTEGER NOT NULL"+
                ");";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("questions table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO questions (title, points, givenPoint, test) VALUES(?,?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, question);
            sqlSt.setInt(2, points);
            sqlSt.setInt(3, givenPoint);
            sqlSt.setInt(4, test.getId());

            sqlSt.executeUpdate();

            Log.info("New question added");

//          -- Set up id --
            sqlQ = "SELECT * FROM questions ORDER BY id DESC LIMIT 1";

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
            Log.warning("New question creation failed");
            conn.close();
            return false;
        }
    }

}
