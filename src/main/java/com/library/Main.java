package com.library;

import com.library.service.BorrowService;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.dao.BorrowDAO;
import com.library.util.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection;
        try {
            connection = DbConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
            return;
        }

        // Création des services
        BookService bookService = new BookService();
        StudentService studentService = new StudentService();
        BorrowDAO borrowDAO = new BorrowDAO(studentService, bookService);  // Création de BorrowDAO avec deux paramètres
        BorrowService borrowService = new BorrowService(borrowDAO);  // Passer BorrowDAO au constructeur de BorrowService

        boolean running = true;

        while (running) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher les livres");
            System.out.println("3. Ajouter un étudiant");
            System.out.println("4. Afficher les étudiants");
            System.out.println("5. Emprunter un livre");
            System.out.println("6. Afficher les emprunts");
            System.out.println("7. Quitter");

            System.out.print("Choisir une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consommer la ligne restante après l'entier

            switch (choice) {
                case 1:
                    System.out.print("Entrez le titre du livre: ");
                    String title = scanner.nextLine();
                    System.out.print("Entrez l'auteur du livre: ");
                    String author = scanner.nextLine();
                    Book newBook = new Book(title, author, "defaultPublisher", 0, "defaultISBN", 0); // Utiliser les constructeurs avec tous les paramètres
                    bookService.addBook(newBook);
                    break;

                case 2:
                    bookService.displayBooks();
                    break;

                case 3:
                    System.out.print("Entrez le nom de l'étudiant: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Entrez le Id de l'étudiant: ");
                    int student_Id = scanner.nextInt();
                    Student newStudent = new Student(student_Id, studentName);
                    studentService.addStudent(newStudent);
                    break;

                case 4:
                    studentService.displayStudents();
                    break;

                case 5:
                    System.out.print("Entrez l'ID de l'étudiant: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Entrez l'ID du livre: ");
                    int bookId = scanner.nextInt();

                    // Récupérer l'étudiant et le livre
                    Optional<Student> studentForBorrow = studentService.findStudentById(studentId);
                    Optional<Book> bookForBorrow = bookService.findBookById(bookId);

                    if (studentForBorrow.isPresent() && bookForBorrow.isPresent()) {
                        Borrow newBorrow = new Borrow(
                                studentForBorrow.get().getId(),
                                studentForBorrow.get(),
                                bookForBorrow.get(),
                                new Date(),
                                null
                        );
                        borrowService.borrowBook(newBorrow);
                        System.out.println("Livre emprunté avec succès.");
                    } else {
                        System.out.println("Étudiant ou livre introuvable.");
                    }


                case 6:
                    borrowService.displayBorrows();
                    break;

                case 7:
                    running = false;
                    System.out.println("Au revoir!");
                    break;

                default:
                    System.out.println("Option invalide.");
            }
        }

        scanner.close();
    }
}
