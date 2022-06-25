package dev.examsmanagement.model;

import dev.examsmanagement.Log;
import dev.examsmanagement.db.DBconnection;

import java.sql.*;

public class CQSubmission {
    protected int id;
    protected User student;
    protected Test test;
    protected Question question;
    protected String answer;
    protected int evaluated=0, givenpoints=0;

    public CQSubmission(int id, User student, Test test, Question question, String answer, int evaluated, int givenpoints) {
        this.id = id;
        this.student = student;
        this.test = test;
        this.question = question;
        this.answer = answer;
        this.evaluated = evaluated;
        this.givenpoints = givenpoints;
    }

    public CQSubmission(User student, Test test, Question question, String answer) {
        this.student = student;
        this.test = test;
        this.question = question;
        this.answer = answer;
    }

    public User getStudent() { return student; }

    public Test getTest() { return test; }

    public Question getQuestion() { return question; }

    public String getAnswer() { return answer; }

    public int getGivenpoints() { return givenpoints; }

    public void setAnswer(String answer) { this.answer = answer; }

    public boolean evaluate(int givenpoints){
        this.givenpoints = givenpoints;
        this.evaluated = 1;

        Connection conn = DBconnection.conn;
        String sqlQ = "UPDATE cqsubmissions " +
                "SET givenpoints=\'" + this.givenpoints + "\'," +
                " evaluated=\'" + this.evaluated + "\'" +
                " WHERE id = " + this.id + " ;";

        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("CQSubmission Updated");
            return true;
        } catch (SQLException e) {
            Log.info("CQSubmission Update Failed");
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkSubmission(){
        boolean returnFlag = true;

        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT COUNT(*) FROM cqsubmissions WHERE" +
                " student=\'" + student.getEmail() + "\' AND " +
                " test=" + test.getId() + " AND " +
                " question=" + question.getId() + " ;";
        System.out.println(sqlQ);
        try{
            Statement sqlSt = conn.createStatement();
            ResultSet rs = sqlSt.executeQuery(sqlQ);

            int submission = rs.getInt(1);
            if(submission > 0){
                returnFlag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            returnFlag = false;
        }
        return returnFlag;
    }

    public boolean saveSubmission(){
        Connection conn = DBconnection.conn;
        String sqlQ = null;
        if(DBconnection.database == DBconnection.mysqlDB) {
            sqlQ = "CREATE Table cqsubmissions (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "student VARCHAR(255) NOT NULL," +
                    "test INTEGER NOT NULL," +
                    "question INTEGER NOT NULL," +
                    "answer VARCHAR(255) NOT NULL," +
                    "evaluated INTEGER NOT NULL," +
                    "givenpoints INTEGER NOT NULL" +
                    ");";
        }
        else if(DBconnection.database == DBconnection.sqliteDB) {
            sqlQ = "CREATE Table cqsubmissions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "student VARCHAR(255) NOT NULL," +
                    "test INTEGER NOT NULL," +
                    "question INTEGER NOT NULL," +
                    "answer VARCHAR(255) NOT NULL," +
                    "evaluated INTEGER NOT NULL," +
                    "givenpoints INTEGER NOT NULL" +
                    ");";
        }
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("cqsubmissions table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO cqsubmissions (student, test, question, answer, evaluated, givenpoints) VALUES(?,?,?,?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, student.getEmail());
            sqlSt.setInt(2, test.getId());
            sqlSt.setInt(3, question.getId());
            sqlSt.setString(4, answer);
            sqlSt.setInt(5, evaluated);
            sqlSt.setInt(6, givenpoints);

            sqlSt.executeUpdate();
            Log.info("New cqsubmission added");

//          -- Set up id --
            sqlQ = "SELECT * FROM cqsubmissions ORDER BY id DESC LIMIT 1";

            try{
//            --- Run query to bring latest cqquestions ---
                Statement sqlSt2 = conn.createStatement();
                ResultSet rs = sqlSt2.executeQuery(sqlQ);

                if (rs.next()){
                    id = rs.getInt("id");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            Log.warning("New cqsubmission creation failed");
            return false;
        }
    }
}
