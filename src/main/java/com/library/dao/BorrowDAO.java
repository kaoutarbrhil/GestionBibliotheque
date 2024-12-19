package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowDAO {
    private final StudentService studentService;
    private final BookService bookService;

    public BorrowDAO(StudentService studentService, BookService bookService) {
        this.studentService = studentService;
        this.bookService = bookService;
    }

    public void addBorrow(Borrow borrow) {
        String query = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, borrow.getReturnDate() != null ? new java.sql.Date(borrow.getReturnDate().getTime()) : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Récupérer les informations depuis la base de données
                int borrowId = rs.getInt("id");
                int studentId = rs.getInt("student_id"); // Colonne contenant le nom du membre
                int bookId = rs.getInt("book_id");    // Colonne contenant le titre du livre
                Date borrowDate = rs.getDate("borrow_date");
                Date returnDate = rs.getDate("return_date");

                // Récupérer l'objet Student à partir de l'ID
                Optional<Student> student = studentService.findStudentById(studentId);
                if (!student.isPresent()) {
                    continue; // Si l'étudiant n'existe pas, passez à la prochaine ligne
                }

                // Récupérer l'objet Book à partir de l'ID
                Optional<Book> book = bookService.findBookById(bookId);
                if (!book.isPresent()) {
                    continue; // Si le livre n'existe pas, passez à la prochaine ligne
                }

                // Créer l'objet Borrow
                Borrow borrow = new Borrow(borrowId, student.get(), book.get(), borrowDate, returnDate);
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }
}
