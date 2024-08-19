package storage;

import java.sql.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Optional;


public class Storage {
    private static final int DB_PORT = 5432;
    private static final String DB_ADDRESS = "127.0.0.1";
    private static final String DB_NAME = "boletim";

    private Connection connection;
    private String url;
    private Properties properties;

    public Storage(String user, String password) throws SQLException {
        url = "jdbc:postgresql://" + DB_ADDRESS +  ":" + DB_PORT + "/" + DB_NAME;

        properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection(url, properties);
    }

    public boolean createStudent(String name) throws SQLException {
        String query = "INSERT INTO aluno(nome) VALUES(?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, name);
        
        int rowsAffected = pstmt.executeUpdate();

        pstmt.close();
        return rowsAffected == 1;
    }

    public ArrayList<Student> getStudents() throws SQLException {
        String query = "SELECT id, nome FROM aluno";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ArrayList<Student> students = new ArrayList<Student>();

        while (rs.next()) {
            Student s = new Student();
            s.id = rs.getInt(1);
            s.name = rs.getString(2);
            students.add(s);
        }

        stmt.close();
        rs.close();
        return students;
    }

    public Optional<Student> getStudentByName(String name) throws SQLException {
        String query = "SELECT id FROM aluno WHERE nome = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();

        Optional<Student> student;
        if (!rs.next()) {
            student = Optional.ofNullable(null);
            return student;
        }

        Student s = new Student();
        s.name = name;
        s.id = rs.getInt(1);
        student = Optional.ofNullable(s);

        return student;
    }

    public void deleteStudent(String name) throws SQLException {

    }


    public void createGrade(int studentId, Float grade) throws SQLException {

    }

    public ArrayList<Grade> getGrades(int studentId) throws SQLException {

        return null;
    }

    public void updateGrade(int id, Float newGrade) throws SQLException {
        
    }

    public void deleteGrade(int id) throws SQLException {
        
    }
}
