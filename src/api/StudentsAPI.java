package api;

import storage.*;

import java.util.ArrayList;
import java.sql.SQLException;

public class StudentsAPI {
    private static final int STUDENT_NAME_MAX_CHARS = 127;
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
        if (name.length() > STUDENT_NAME_MAX_CHARS) {
            System.out.println("Nome inválido. Tamanho máximo: " + STUDENT_NAME_MAX_CHARS + " caracteres.");
            return;
        }

        try {
            db.createStudent(name);
            System.out.println("Aluno criado com sucesso.");
        }
        catch (Exception e) {
            System.out.println("Não foi possível criar o aluno. Por favor tente novamente.");
        }
    }

    public void updateStudent(int id, String newName) {
        if (newName.length() > STUDENT_NAME_MAX_CHARS) {
            System.out.println("Nome inválido. Tamanho máximo: " + STUDENT_NAME_MAX_CHARS + " caracteres.");
            return;
        }

        try {
            db.updateStudent(id, newName);
            System.out.println("Aluno editado com sucesso.");
        }
        catch (Exception e) {
            System.out.println("Não foi possível editar o aluno. Por favor tente novamente.");
        }
    }

    public void deleteStudent(int id) {
        try {
            db.deleteStudent(id);
            System.out.println("Aluno deletado com sucesso.");
        }
        catch (Exception e) {
            System.out.println("Não foi possível deletar o aluno. Por favor tente novamente.");
        }
    }
    
    public ArrayList<Grade> listGrades(int studentId) {
        ArrayList<Grade> grades = new ArrayList<Grade>();
        try {
            grades = db.getGrades(studentId);
        }
        catch (Exception e) {
            System.out.println("Algo de errado aconteceu. Não foi possível listar as notas. Por favor tente novamente.");
        }

        return grades;
    }

    public void createGrade(int studentId, float grade) {
        float roundedGrade = round(grade, 2);
        if (roundedGrade < 0 || roundedGrade > 10) {
            System.out.println("Nota inválida. A nota deve estar entre 0 e 10.");
            return;
        }

        try {
            db.createGrade(studentId, grade);
            System.out.println("Nota criada com sucesso.");
        }
        catch (Exception e) {
            System.out.println("Não foi possível criar a nota. Por favor tente novamente.");
        }
    }

    public void updateGrade(int id, float newGrade) {
        float roundedGrade = round(newGrade, 2);
        if (roundedGrade < 0 || roundedGrade > 10) {
            System.out.println("Nota inválida. A nota deve estar entre 0 e 10.");
            return;
        }

        try {
            db.updateGrade(id, newGrade);
            System.out.println("Nota editada com sucesso.");
        }
        catch (Exception e) {
            System.out.println("Não foi possível editar a nota. Por favor tente novamente.");
        }
    }

    public void deleteGrade(int id) {
        try {
            db.deleteGrade(id);
            System.out.println("Nota deletada com sucesso.");
        }
        catch (Exception e) {
            System.out.println("Não foi possível deletar a nota. Por favor tente novamente.");
        }
    }

    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }
}
