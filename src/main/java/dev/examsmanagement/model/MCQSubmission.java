package dev.examsmanagement.model;

import dev.examsmanagement.Log;
import dev.examsmanagement.db.DBconnection;

import java.sql.*;

public class MCQSubmission {
    protected int id;
    protected User student;
    protected Test test;
    protected MCQquestion mcqQuestion;
    protected int answer;
    protected int evaluated=0, givenpoints=0;

    public MCQSubmission(User student, Test test, MCQquestion mcqQuestion, int answer, int givenpoints) {
        this.student = student;
        this.test = test;
        this.mcqQuestion = mcqQuestion;
        this.answer = answer;
        this.evaluated = 1;
        this.givenpoints = givenpoints;
    }

    public MCQSubmission(User student, Test test, MCQquestion mcqQuestion, int answer) {
        this.student = student;
        this.test = test;
        this.mcqQuestion = mcqQuestion;
        this.answer = answer;
    }

    public User getStudent() { return student; }

    public Test getTest() { return test; }

    public MCQquestion getMcqQuestion() {
        return mcqQuestion;
    }

    public int getAnswer() { return answer; }

    public int getGivenpoints() { return givenpoints; }

    public void setAnswer(int answer) { this.answer = answer; }

    public boolean evaluate(int givenpoints){
        this.givenpoints = givenpoints;
        this.evaluated = 1;

        Connection conn = DBconnection.conn;
        String sqlQ = "UPDATE mcqsubmissions " +
                "SET givenpoints=\'" + this.givenpoints + "\'," +
                " evaluated=\'" + this.evaluated + "\'" +
                " WHERE id = " + this.id + " ;";

        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("MCQSubmission Updated");
            return true;
        } catch (SQLException e) {
            Log.info("MCQSubmission Update Failed");
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkSubmission(){
        boolean returnFlag = true;

        Connection conn = DBconnection.conn;
        String sqlQ = "SELECT COUNT(*) FROM mcqsubmissions WHERE" +
                " student=\'" + student.getEmail() + "\' AND " +
                " test=" + test.getId() + " AND " +
                " mcqquestion=" + mcqQuestion.getId() + " ;";
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
        }
        return returnFlag;
    }

    public boolean saveSubmission(){
        Connection conn = DBconnection.conn;
        String sqlQ = null;
        if(DBconnection.database == DBconnection.mysqlDB) {
            sqlQ = "CREATE Table mcqsubmissions (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "student VARCHAR(255) NOT NULL," +
                    "test INTEGER NOT NULL," +
                    "mcqquestion INTEGER NOT NULL," +
                    "answer VARCHAR(255) NOT NULL," +
                    "evaluated INTEGER NOT NULL," +
                    "givenpoints INTEGER NOT NULL" +
                    ");";
        }
        else if(DBconnection.database == DBconnection.sqliteDB) {
            sqlQ = "CREATE Table alvealve (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "student VARCHAR(255) NOT NULL," +
                    "test INTEGER NOT NULL," +
                    "mcqquestion INTEGER NOT NULL," +
                    "answer VARCHAR(255) NOT NULL," +
                    "evaluated INTEGER NOT NULL," +
                    "givenpoints INTEGER NOT NULL" +
                    ");";
        }
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("mcqsubmissions table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO mcqsubmissions (student, test, mcqquestion, answer, evaluated, givenpoints) VALUES(?,?,?,?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, student.getEmail());
            sqlSt.setInt(2, test.getId());
            sqlSt.setInt(3, mcqQuestion.getId());
            sqlSt.setInt(4, answer);
            sqlSt.setInt(5, evaluated);
            sqlSt.setInt(6, givenpoints);

            sqlSt.executeUpdate();
            Log.info("New mcqsubmission added");

//          -- Set up id --
            sqlQ = "SELECT * FROM mcqsubmissions ORDER BY id DESC LIMIT 1";

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
            Log.warning("New mcqsubmission creation failed");
            return false;
        }
    }
}
