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
        if (rs.next()) {
            Student s = new Student();
            s.name = name;
            s.id = rs.getInt(1);
            student = Optional.ofNullable(s);
        }
        else {
            student = Optional.ofNullable(null);
        }

        rs.close();
        pstmt.close();
        return student;
    }

    public boolean deleteStudent(String name) throws SQLException {
        String query = "DELETE FROM aluno WHERE nome = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, name);
        
        int rowsAffected = pstmt.executeUpdate();

        pstmt.close();
        return rowsAffected == 1;
    }

    public boolean deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM aluno WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        
        int rowsAffected = pstmt.executeUpdate();

        pstmt.close();
        return rowsAffected == 1;
    }

    public boolean createGrade(int studentId, Float grade) throws SQLException {
        String query = "INSERT INTO nota(id_aluno, nota) VALUES(?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, studentId);
        pstmt.setFloat(2, grade);

        int rowsAffected = pstmt.executeUpdate();

        pstmt.close();
        return rowsAffected == 1;
    }

    public ArrayList<Grade> getGrades(int studentId) throws SQLException {
        String query = "SELECT id_nota, nota FROM nota WHERE id_aluno = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, studentId);
        ResultSet rs = pstmt.executeQuery();

        ArrayList<Grade> grades = new ArrayList<Grade>();
        while (rs.next()) {
            Grade g = new Grade();
            g.id = rs.getInt(1);
            g.value = rs.getFloat(2);
            grades.add(g);
        }

        rs.close();
        pstmt.close();
        return grades;
    }

    public boolean updateGrade(int id, Float newGrade) throws SQLException {
        String query = "UPDATE nota SET nota = ? WHERE id_nota = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setFloat(1, newGrade);
        pstmt.setInt(2, id);

        int rowsAffected = pstmt.executeUpdate();

        pstmt.close();
        return rowsAffected == 1;
    }

    public boolean deleteGrade(int id) throws SQLException {
        String query = "DELETE FROM nota WHERE id_nota = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);

        int rowsAffected = pstmt.executeUpdate();

        pstmt.close();
        return rowsAffected == 1;
    }
}
