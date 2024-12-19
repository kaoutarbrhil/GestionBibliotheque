package com.library;

import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
    }

    @Test
    public void testAddBook() {
        Book book = new Book("Test Title", "Test Author", "Test Publisher", 2023, "1234567890", 2023);
        bookService.addBook(book);

        // Vérifier que le livre a été ajouté en récupérant le livre par ISBN
        Optional<Book> retrievedBook = bookService.findBookById(book.getId());
        assertTrue(retrievedBook.isPresent());
        assertEquals("Test Title", retrievedBook.get().getTitle());
        assertEquals("Test Author", retrievedBook.get().getAuthor());
    }

    @Test
    public void testDisplayBooks() {
        // Ajouter des livres
        Book book1 = new Book("Title1", "Author1", "Publisher1", 2020, "ISBN1", 2020);
        Book book2 = new Book("Title2", "Author2", "Publisher2", 2021, "ISBN2", 2021);
        bookService.addBook(book1);
        bookService.addBook(book2);

        // Afficher les livres
        bookService.displayBooks();
    }

    @Test
    public void testFindBookById() {
        Book book = new Book("Title", "Author", "Publisher", 2020, "ISBN3", 2020);
        bookService.addBook(book);

        Optional<Book> retrievedBook = bookService.findBookById(book.getId());
        assertTrue(retrievedBook.isPresent());
        assertEquals(book.getTitle(), retrievedBook.get().getTitle());
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book("ToDelete", "Author", "Publisher", 2020, "ISBN4", 2020);
        bookService.addBook(book);

        // Supprimer le livre
        bookService.deleteBook(book.getId());

        // Vérifier que le livre a été supprimé
        Optional<Book> retrievedBook = bookService.findBookById(book.getId());
        assertFalse(retrievedBook.isPresent());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book("Old Title", "Author", "Publisher", 2020, "ISBN5", 2020);
        bookService.addBook(book);

        // Mise à jour des informations du livre
        book.setTitle("New Title");
        bookService.updateBook(book);

        // Vérifier que le livre a été mis à jour
        Optional<Book> retrievedBook = bookService.findBookById(book.getId());
        assertTrue(retrievedBook.isPresent());
        assertEquals("New Title", retrievedBook.get().getTitle());
    }
}
