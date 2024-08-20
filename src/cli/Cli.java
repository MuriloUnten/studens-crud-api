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
    private Scanner stdin;

    public Cli() {
        stdin = new Scanner(System.in);
        connectToDB();
        createMainMenu();
        createStudentMenu();
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
            System.out.print("Escolha o aluno a ser selecionado: ");

            int option = Menu.entrySelector(students.size() + 1);
            if (option == 0) {
                System.out.println("Voltando...");
                return;
            }
            // TODO run the other menu
        });
    }

    private void createStudentMenu() {
        // TODO implement
    }

    private void printStudents(ArrayList<Student> students) {
        int i = 1;
        for (Student s : students) {
            System.out.println(i++ + ". " + s.name);
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
