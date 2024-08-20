package cli;

import storage.Storage;
import storage.Student;
import storage.Grade;
import api.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Optional;

public class Cli {
    private Menu mainMenu;
    private StudentsAPI api;
    private Scanner stdin;

    public Cli() {
        stdin = new Scanner(System.in);
        connectToDB();
        mainMenu = new Menu();

        mainMenu.createEntry("Sair", () -> {});

        mainMenu.createEntry("Listar alunos", () -> {
            System.out.println("\nLista de alunos");
            System.out.println("====================");

            ArrayList<Student> students = api.listStudents();
            if (students.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                return;
            }
            printStudents(students);
            System.out.println("====================");
        });

        mainMenu.createEntry("Adicionar aluno", () -> {
            System.out.println("Selecionado: Adicionar aluno.");
            System.out.print("Qual o nome do aluno? ");
            String name = stdin.nextLine();
            api.createStudent(name);
        });

        mainMenu.createEntry("Deletar aluno", () -> {
            System.out.println("\nLista de alunos");
            System.out.println("====================");

            ArrayList<Student> students = api.listStudents();
            if (students.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                return;
            }
            printStudents(students);
            System.out.println("====================");
            System.out.println("Escolha o aluno a ser deletado: ");

            // TODO fix: possible bad input
            int n = stdin.nextInt() - 1;
            int id = students.get(n).id;
            api.deleteStudent(id);
        });

        mainMenu.createEntry("Selecionar aluno", () -> {
            System.out.println("\nLista de alunos");
            System.out.println("====================");

            ArrayList<Student> students = api.listStudents();
            if (students.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                return;
            }
            printStudents(students);
            System.out.println("====================");
            System.out.println("Escolha o aluno a ser selecionado: ");

            int n = stdin.nextInt() - 1;
            int id = students.get(n).id;
            // TODO run the other menu
        });
    }

    public void run() {
        mainMenu.run();

        stdin.close();
    }

    private void printStudents(ArrayList<Student> students) {
        int i = 1;
        for (Student s : students) {
            System.out.println(i++ + ". " + s.name);
        }
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
