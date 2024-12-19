package com.library;

import com.library.dao.BorrowDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowServiceTest {

    private BorrowDAO borrowDAO;
    private BorrowService borrowService;
    private StudentService studentService;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        // Initialisation des services
        studentService = new StudentService(); // Assurez-vous d'initialiser correctement les services
        bookService = new BookService();
        borrowDAO = new BorrowDAO(studentService, bookService); // Passer les services aux DAO
        borrowService = new BorrowService(borrowDAO);

        // Exécuter le testAddBorrow avant testGetAllBorrows
        testAddBorrow();
    }

    @Test
    public void testAddBorrow() {
        // Récupérer un étudiant existant dans la base de données
        Student student = studentService.findStudentById(1).orElseThrow(() ->
                new IllegalStateException("Aucun étudiant avec l'ID 1 dans la base de données"));

        // Récupérer un livre existant dans la base de données
        Book book = bookService.findBookById(1).orElseThrow(() ->
                new IllegalStateException("Aucun livre avec l'ID 1 dans la base de données"));

        // Créer un emprunt basé sur ces données
        Borrow borrow = new Borrow(1, student, book, new Date(), null);

        // Ajouter l'emprunt via le service
        String result = borrowService.borrowBook(borrow);

        // Vérifier que l'ajout a réussi
        assertEquals("Livre emprunté avec succès.", result);

        // Attendre un instant pour donner le temps à l'emprunt d'être enregistré
        List<Borrow> borrows = borrowDAO.getAllBorrows();
        assertFalse(borrows.isEmpty(), "La liste des emprunts ne doit pas être vide");

        // Vérifications supplémentaires
        Borrow savedBorrow = borrows.get(borrows.size() - 1);
        assertEquals(student.getId(), savedBorrow.getStudent().getId(),
                "Le id de l'étudiant ne correspond pas");
        assertEquals(book.getId(), savedBorrow.getBook().getId(),
                "Le Id du livre ne correspond pas");
    }

    @Test
    void testGetAllBorrows() {
        // Récupérer un étudiant existant dans la base de données
        Student student = studentService.findStudentById(2).orElseThrow(() ->
                new IllegalStateException("Aucun étudiant avec l'ID 2 dans la base de données"));

        // Récupérer un livre existant dans la base de données
        Book book = bookService.findBookById(2).orElseThrow(() ->
                new IllegalStateException("Aucun livre avec l'ID 2 dans la base de données"));

        // Créer un emprunt basé sur ces données
        Borrow borrow = new Borrow(2, student, book, new Date(), null);
        borrowService.borrowBook(borrow);

        // Attendre un instant pour que l'emprunt soit correctement ajouté
        List<Borrow> borrows = borrowDAO.getAllBorrows();
        assertEquals(2, borrows.size(), "La taille de la liste des emprunts ne correspond pas");

        // Vérifications supplémentaires
        Borrow savedBorrow = borrows.get(borrows.size() - 1);
        assertEquals(student.getId(), savedBorrow.getStudent().getId(),
                "Le id de l'étudiant ne correspond pas");
        assertEquals(book.getId(), savedBorrow.getBook().getId(),
                "Le Id du livre ne correspond pas");
    }
}
