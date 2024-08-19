package api;

import storage.*;

import java.util.ArrayList;
import java.util.Optional;
import java.security.cert.TrustAnchor;
import java.sql.SQLException;

public class StudentsAPI {
    private Storage db;

    public StudentsAPI(String user, String password) throws SQLException {
        db = new Storage(user, password);
    }

    public Optional<ArrayList<Student>> listStudents() {
        Optional<ArrayList<Student>> output;
        try {
            ArrayList<Student> students = db.getStudents();
            output = Optional.ofNullable(students);
        }
        catch (Exception e) {
            output = Optional.ofNullable(null);
        }

        return output;
    }

    public void createStudent(String name) {
        try {
            db.createStudent(name);
            System.out.println("Aluno criado com sucesso.");
        }
        catch (Exception e) {
            System.out.println("Não foi possível criar o aluno. Por favor tente novamente.");
        }
    }

    public void deleteStudent(int id) {
        try {
            db.deleteStudent(id);
        }
        catch (Exception e) {
            System.out.println("Não foi possível deletar o aluno. Por favor tente novamente.");
        }
    }
}
