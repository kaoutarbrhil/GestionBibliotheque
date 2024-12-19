package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;
import java.util.Optional;

public class BookService {
    private BookDAO bookDAO;

    // Constructeur qui initialise l'objet BookDAO
    public BookService() {
        this.bookDAO = new BookDAO();
    }

    // Ajouter un livre
    public void addBook(Book book) {
        bookDAO.add(book);
    }

    // Afficher tous les livres
    public void displayBooks() {
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Trouver un livre par ID
    public Optional<Book> findBookById(int id) {
        return bookDAO.getBookById(id);
    }

    // Supprimer un livre par ID
    public void deleteBook(int id) {
        bookDAO.delete(id);
    }

    // Mise à jour des informations d'un livre
    public void updateBook(Book book) {
        bookDAO.update(book);
    }
}
