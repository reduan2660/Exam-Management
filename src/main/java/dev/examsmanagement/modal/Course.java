package dev.examsmanagement.modal;

import dev.examsmanagement.Log;
import dev.examsmanagement.Session;
import dev.examsmanagement.db.DBconnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Course {
    private int id;
    private String title, description;
    private User instrcutor;

    private static int studentCap = 20, totalStudent=0;
    private User[] students = new User[studentCap];
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

    public void printCourse(){
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Instructor: " + instrcutor.getEmail());

    }

    public void addStudent(User _student){
//        --- Append to array ---
        if(totalStudent == studentCap){
            User[] studentsExtended = new User[studentCap * 2];

            for(int i=0;i<totalStudent;i++){
                studentsExtended[i] = students[i];
            }
            studentsExtended[totalStudent] = _student;

            students = studentsExtended;
        }
        else{
            students[totalStudent] = _student;
            totalStudent += 1;
        }

//        --- Save to Database ---
    }

    public void addTest(Test _test){
//        --- Save to class variable
        test = _test;
//        --- Save to database

    }

    public boolean saveCourse() throws IOException, SQLException {


        Connection conn = DBconnection.DBconnect();
        String sqlQ = "CREATE Table courses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "description VARCHAR(255) NOT NULL," +
                "instructor VARCHAR(255) NOT NULL"+
                ");";
        try{
            Statement sqlSt = conn.createStatement();
            sqlSt.execute(sqlQ);
            Log.info("Courses table created");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        sqlQ = "INSERT INTO courses(title, description, instructor) VALUES(?,?,?);";
        try{
            PreparedStatement sqlSt = conn.prepareStatement(sqlQ);
            sqlSt.setString(1, title);
            sqlSt.setString(2, description);
            sqlSt.setString(3, instrcutor.getEmail());
            int a = sqlSt.executeUpdate();
            System.out.println(a);

            Log.info("New course added");
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


}
