package api;

import storage.*;

import java.util.ArrayList;
import java.sql.SQLException;

public class StudentsAPI {
    private Storage db;

    public StudentsAPI(String user, String password) throws SQLException {
        db = new Storage(user, password);
    }

    public ArrayList<Student> listStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            students = db.getStudents();
        }
        catch (Exception e) {
            System.out.println("Algo de errado aconteceu. Não foi possível listar os estudantes. Por favor tente novamente.");
        }

        return students;
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
