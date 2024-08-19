package src.storage;

import java.sql.*;
import java.util.Properties;
import java.util.ArrayList;


public class Storage {
    private static final int DB_PORT = 5432;
    private static final String DB_ADDRESS = "127.0.0.1";
    private static final String DB_NAME = "boletim";

    private Connection connection;
    private String url;
    private Properties properties;

    Storage(String user, String password) {
        url = "jdbc:postgresql://" + DB_ADDRESS +  ":" + DB_PORT + "/" + DB_NAME;
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        connection = DriverManager.getConnection(url, properties);
    }

    public void createStudent(String name) {

    }

    public ArrayList<String> getStudents() {

    }

    public Student getStudentByName(String name) {
        
    }

    public void deleteStudent(String name) {

    }


    public void createGrade(int studentId, Float grade) {

    }

    public ArrayList<Grade> getGrades(int studentId) {

    }

    public void updateGrade(int id, Float newGrade) {
        
    }

    public void deleteGrade(int id) {
        
    }
}
