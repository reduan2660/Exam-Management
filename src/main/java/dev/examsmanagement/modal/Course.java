package dev.examsmanagement.modal;

import dev.examsmanagement.Log;
import dev.examsmanagement.Session;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.*;

public class Course {
    private int id;
    private String title, description;
    private User instrcutor;

    private static int studentCap = 20, totalStudent=0;
    private String[] students = new String[studentCap];
    private Test test;

    public Course(String _title, String _description, User _instrcutor){
        title = _title;
        description = _description;
        instrcutor = _instrcutor;
    }

    public Course(int _id, String _title, String _description, User _instrcutor){
        id = _id;
        title = _title;
        description = _description;
        instrcutor = _instrcutor;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void addTest(Test _test){
//        --- Save to class variable
        test = _test;
//        --- Save to database

    }

    public boolean saveCourse() throws IOException, SQLException {
        Connection conn = DBconnection.DBconnect();
        String sqlQ = null;
        if(DBconnection.database == DBconnection.mysqlDB) {
            sqlQ = "CREATE Table courses (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "title VARCHAR(255) NOT NULL," +
                    "description VARCHAR(255) NOT NULL," +
                    "instructor VARCHAR(255) NOT NULL" +
                    ");";
        }
        else if(DBconnection.database == DBconnection.sqliteDB){
            sqlQ = "CREATE Table courses (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title VARCHAR(255) NOT NULL," +
                    "description VARCHAR(255) NOT NULL," +
                    "instructor VARCHAR(255) NOT NULL" +
                    ");";
        }

        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("Courses table created");
        } catch (SQLException e) {
            Log.info("Courses table creation failed");
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO courses(title, description, instructor) VALUES(?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, title);
            sqlSt.setString(2, description);
            sqlSt.setString(3, instrcutor.getEmail());
            sqlSt.executeUpdate();

            Log.info("New course added");

//          -- Set up id --
            sqlQ = "SELECT * FROM courses ORDER BY id DESC LIMIT 1";

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
            Log.warning("New user creation failed");
            conn.close();
            return false;
        }
    }

    public boolean addStudents(String[] students){
        Connection conn = DBconnection.DBconnect();
        String sqlQ = "CREATE Table course_student (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "course INTEGER NOT NULL," +
                "student VARCHAR(255) NOT NULL"+
                ");";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("course_student table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO course_student (course, student) VALUES(?,?);";
        boolean returnFlag = true;
        for (int i=0; i<students.length;i++){
            try {
                PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
                sqlSt.setInt(1, id);
                sqlSt.setString(2, students[i]);
                sqlSt.executeUpdate();
                Log.info("Course Student added");
            }
            catch (SQLException e){
                e.printStackTrace();
                Log.warning("Course-Student creation failed");
                returnFlag = false;
                break;
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnFlag;
    }
}
