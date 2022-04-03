package dev.examsmanagement.modal;

import dev.examsmanagement.Log;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class Test {
    private int id;
    private String title, instructions;
    private LocalDateTime time;
    private boolean allowLateSubmission, randomQuestions;
    private Course course;

    public Test(String _title,
                String _instructions,
                LocalDateTime _time,
                boolean _allowLateSubmission,
                boolean _randomQuestions,
                Course _course)
    {
        title = _title;
        instructions = _instructions;
        time = _time;
        allowLateSubmission = _allowLateSubmission;
        randomQuestions = _randomQuestions;
        course = _course;
    }

    Test(int _id,
         String _title,
         String _instructions,
         LocalDateTime _time,
         boolean _allowLateSubmission,
         boolean _randomQuestions,
         Course _course)
    {
        id = _id;
        title = _title;
        instructions = _instructions;
        time = _time;
        allowLateSubmission = _allowLateSubmission;
        randomQuestions = _randomQuestions;
        course = _course;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", instructions='" + instructions + '\'' +
                ", time=" + time +
                ", allowLateSubmission=" + allowLateSubmission +
                ", randomQuestions=" + randomQuestions +
                ", course=" + course +
                '}';
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

    public boolean saveTest() throws IOException, SQLException {
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "CREATE Table tests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "instructions VARCHAR(255)," +
                "time VARCHAR(255) NOT NULL,"+
                "course INTEGER NOT NULL,"+
                "allowLateSubmission INTEGER NOT NULL,"+
                "randomQuestions INTEGER NOT NULL"+
                ");";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("tests table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO tests(title, instructions, time, course, allowLateSubmission, randomQuestions) VALUES(?,?,?,?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, title);
            sqlSt.setString(2, instructions);
            sqlSt.setString(3, time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            sqlSt.setInt(4, course.getId());
            if(allowLateSubmission){
                sqlSt.setInt(5, 1);
            }
            else{
                sqlSt.setInt(5, 0);
            }

            if(randomQuestions){
                sqlSt.setInt(6, 1);
            }
            else{
                sqlSt.setInt(6, 0);
            }

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

            conn.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            Log.warning("New test creation failed");
            conn.close();
            return false;
        }
    }

}
