package cli;

import storage.Student;
import storage.Grade;
import api.*;

import java.util.Scanner;
import java.util.ArrayList;

public class Cli {
    private Menu mainMenu;
    private Menu studentMenu;
    private StudentsAPI api;

    private Student selectedStudent;

    private Scanner stdin;

    public Cli() {
        stdin = new Scanner(System.in);
        connectToDB();
        createMainMenu();
        createStudentMenu();
        selectedStudent = null;
    }

    public void run() {
        mainMenu.run();

        stdin.close();
    }

    private void createMainMenu() {
        mainMenu = new Menu();

        mainMenu.createEntry("Sair", () -> {});

        mainMenu.createEntry("Listar alunos", () -> {
            Cli.clearScreen();
            System.out.println("\nLista de alunos");
            System.out.println("====================");

            ArrayList<Student> students = api.listStudents();
            if (students.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                System.out.println("====================");
                return;
            }
            printStudents(students);
            System.out.println("====================");
        });

        mainMenu.createEntry("Adicionar aluno", () -> {
            Cli.clearScreen();
            System.out.println("Selecionado: Adicionar aluno.");
            System.out.print("Qual o nome do aluno? ");
            String name = stdin.nextLine();
            api.createStudent(name);
        });

        mainMenu.createEntry("Deletar aluno", () -> {
            Cli.clearScreen();
            System.out.println("\nLista de alunos");
            System.out.println("====================");

            ArrayList<Student> students = api.listStudents();
            if (students.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                System.out.println("====================");
                return;
            }

            System.out.println("0. Voltar");
            printStudents(students);
            System.out.println("====================");
            System.out.print("Escolha o aluno a ser deletado: ");

            int option = Menu.entrySelector(students.size() + 1);
            if (option == 0) {
                System.out.println("Voltando...");
                return;
            }
            int id = students.get(option - 1).id;
            api.deleteStudent(id);
        });

        mainMenu.createEntry("Selecionar aluno", () -> {
            Cli.clearScreen();
            System.out.println("\nLista de alunos");
            System.out.println("====================");

            ArrayList<Student> students = api.listStudents();
            if (students.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                System.out.println("====================");
                return;
            }

            System.out.println("0. Voltar");
            printStudents(students);
            System.out.println("====================");
            System.out.print("Escolha um aluno: ");

            int option = Menu.entrySelector(students.size() + 1);
            if (option == 0) {
                System.out.println("Voltando...");
                return;
            }

            selectedStudent = students.get(option - 1);
            studentMenu.run();
        });
    }

    private void createStudentMenu() {
        studentMenu = new Menu();

        studentMenu.createEntry("Voltar", () -> {});

        studentMenu.createEntry("Listar notas", () -> {
            Cli.clearScreen();
            System.out.println("\nNotas de " + selectedStudent.name);
            System.out.println("====================");
            ArrayList<Grade> grades = api.listGrades(selectedStudent.id);
            if (grades.isEmpty()) {
                System.out.println("Nenhuma nota cadastrada.");
                System.out.println("====================");
                return;
            }
            printGrades(grades);
            System.out.println("====================");
        });

        studentMenu.createEntry("Adicionar nota", () -> {
            Cli.clearScreen();
            System.out.println("Selecionado: Adicionar nota.");
            System.out.print("Nota: ");
            float grade = Menu.getFloatInput();
            
            api.createGrade(selectedStudent.id, grade);
        });

        studentMenu.createEntry("Editar nota", () -> {
            Cli.clearScreen();
            System.out.println("\nNotas de " + selectedStudent.name);
            System.out.println("====================");
            ArrayList<Grade> grades = api.listGrades(selectedStudent.id);
            if (grades.isEmpty()) {
                System.out.println("Nenhuma nota cadastrada.");
                System.out.println("====================");
                return;
            }

            System.out.println("0. Voltar");
            printGrades(grades);
            System.out.println("====================");
            System.out.print("Selecione a nota que deseja alterar: ");

            int option = Menu.entrySelector(grades.size() + 1);
            if (option == 0) {
                System.out.println("Voltando...");
                return;
            }
            
            System.out.print("Digite a nova nota: ");
            float newGrade = Menu.getFloatInput();
            api.updateGrade(grades.get(option - 1).id, newGrade);
        });

        studentMenu.createEntry("Deletar nota", () -> {
            Cli.clearScreen();
            System.out.println("\nNotas de " + selectedStudent.name);
            System.out.println("====================");
            ArrayList<Grade> grades = api.listGrades(selectedStudent.id);
            if (grades.isEmpty()) {
                System.out.println("Nenhuma nota cadastrada.");
                System.out.println("====================");
                return;
            }

            System.out.println("0. Voltar");
            printGrades(grades);
            System.out.println("====================");
            System.out.print("Selecione a nota que deseja deletar: ");

            int option = Menu.entrySelector(grades.size() + 1);
            if (option == 0) {
                System.out.println("Voltando...");
                return;
            }
            
            api.deleteGrade(grades.get(option - 1).id);
        });
    }

    private void printStudents(ArrayList<Student> students) {
        int i = 1;
        for (Student s : students) {
            System.out.println(i++ + ". " + s.name);
        }
    }

    private void printGrades(ArrayList<Grade> grades) {
        int i = 1;
        for (Grade g : grades) {
            System.out.println(i++ + ". " + g.value);
        }
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    private String getUser() {
        System.out.print("Digite seu nome de usuário: ");
        String user = stdin.nextLine();
        System.out.println("");
        return user;
    }

    private String getPassword() {
        System.out.print("Digite sua senha: ");
        String password = stdin.nextLine();
        System.out.println("");
        return password;
    }

    private void connectToDB() {
        boolean authenticated = false;
        while (!authenticated) {
            try {
                String user = getUser();
                String password = getPassword();
                api = new StudentsAPI(user, password);
                authenticated = true;
            }
            catch (Exception e) {
                System.out.println("Failed to connect to the database. Please try again.");
                authenticated = false;
            }
        }

    }
}
