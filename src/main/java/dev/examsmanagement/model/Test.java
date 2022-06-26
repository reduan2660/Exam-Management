package dev.examsmanagement.model;

import dev.examsmanagement.Log;
import dev.examsmanagement.Session;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Test {
    private int id;
    private String title, instructions;
    private LocalDateTime time;
    private int duration;
    private boolean allowLateSubmission, randomQuestions;
    private Course course;
    private int resultPublished = 0;

    public Test(String _title,
                String _instructions,
                LocalDateTime _time,
                int _duration,
                boolean _allowLateSubmission,
                boolean _randomQuestions,
                Course _course)
    {
        title = _title;
        instructions = _instructions;
        time = _time;
        duration = _duration;
        allowLateSubmission = _allowLateSubmission;
        randomQuestions = _randomQuestions;
        course = _course;
    }

    public Test(int _id,
                String _title,
                String _instructions,
                LocalDateTime _time,
                int _duration,
                boolean _allowLateSubmission,
                boolean _randomQuestions,
                Course _course)
    {
        id = _id;
        title = _title;
        instructions = _instructions;
        time = _time;
        duration = _duration;
        allowLateSubmission = _allowLateSubmission;
        randomQuestions = _randomQuestions;
        course = _course;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean setTime(LocalDateTime time) {
        this.time = time;

        Connection conn = DBconnection.conn;
        String sqlQ = "UPDATE tests " +
                "SET time=\'" + this.time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\'" +
                " WHERE id = " + this.id + " ;";

        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("Test time Updated");
            return true;
        } catch (SQLException e) {
            Log.info("Test time Update Failed");
            e.printStackTrace();
            return false;
        }
    }

    public int getDuration() { return duration; }

    public Course getCourse() {
        return course;
    }

    public boolean publishResult(){
        resultPublished = 1;
        Connection conn = DBconnection.conn;
        String sqlQ = "UPDATE tests " +
                "SET resultPublished=\'" + resultPublished + "\'" +
                " WHERE id = " + id + " ;";

        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("Test Published");
            return true;
        } catch (SQLException e) {
            Log.info("Test Publish Failed");
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public String toString() {
        return title + " at " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(time);
    }

    public static LocalDateTime toTime(String date, int hour, int minute, String ampm){
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));

//        -- 24 hour format
        if (ampm=="PM"){
            if(hour>0){
                hour += 12;
            }
        }

        LocalDateTime t = LocalDateTime.of(year,month,day,hour,minute);
        return t;
    }

    public static LocalDateTime toTime(String dateDatabaseFormat){
        int year = Integer.parseInt(dateDatabaseFormat.substring(0,4));
        int month = Integer.parseInt(dateDatabaseFormat.substring(5,7));
        int day = Integer.parseInt(dateDatabaseFormat.substring(8,10));
        int hour = Integer.parseInt(dateDatabaseFormat.substring(11,13));
        int minute = Integer.parseInt(dateDatabaseFormat.substring(14,16));

        LocalDateTime t = LocalDateTime.of(year,month,day,hour,minute);
        return t;
    }

    public boolean saveTest() throws IOException, SQLException {
        Connection conn = DBconnection.conn;
        String sqlQ = null;
        if(DBconnection.database == DBconnection.mysqlDB) {
            sqlQ = "CREATE Table tests (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "title VARCHAR(255) NOT NULL," +
                    "instructions VARCHAR(255)," +
                    "time VARCHAR(255) NOT NULL," +
                    "duration INTEGER NOT NULL," +
                    "course INTEGER NOT NULL," +
                    "allowLateSubmission INTEGER NOT NULL," +
                    "randomQuestions INTEGER NOT NULL," +
                    "resultPublished INTEGER NOT NULL" +
                    ");";
        }
        else if(DBconnection.database == DBconnection.sqliteDB) {
            sqlQ = "CREATE Table tests (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title VARCHAR(255) NOT NULL," +
                    "instructions VARCHAR(255)," +
                    "time VARCHAR(255) NOT NULL," +
                    "duration INTEGER NOT NULL," +
                    "course INTEGER NOT NULL," +
                    "allowLateSubmission INTEGER NOT NULL," +
                    "randomQuestions INTEGER NOT NULL," +
                    "resultPublished INTEGER NOT NULL" +
                    ");";
        }
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("tests table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO tests(title, instructions, time, duration, course, allowLateSubmission, randomQuestions, resultPublished) VALUES(?,?,?,?,?,?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, title);
            sqlSt.setString(2, instructions);
            sqlSt.setString(3, time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            sqlSt.setInt(4, duration);
            sqlSt.setInt(5, course.getId());
            if(allowLateSubmission){
                sqlSt.setInt(6, 1);
            }
            else{
                sqlSt.setInt(6, 0);
            }

            if(randomQuestions){
                sqlSt.setInt(7, 1);
            }
            else{
                sqlSt.setInt(7, 0);
            }
            sqlSt.setInt(8, resultPublished);

            sqlSt.executeUpdate();

            Log.info("New test added");

//          -- Set up id --
            sqlQ = "SELECT * FROM tests ORDER BY id DESC LIMIT 1";

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

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            Log.warning("New test creation failed");
            return false;
        }
    }

    public boolean update(){
        Connection conn = DBconnection.conn;
        String sqlQ = "UPDATE tests " +
                "SET title=\'" + title + "\'," +
                " instructions=\'" + instructions + "\'" +
                " WHERE id = " + id + " ;";

        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("Test Updated");
            return true;
        } catch (SQLException e) {
            Log.info("Test Update Failed");
            e.printStackTrace();
            return false;
        }

    }
}
