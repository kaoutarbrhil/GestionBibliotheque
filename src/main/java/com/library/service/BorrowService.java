
package com.library.service;

import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

public class BorrowService {

    private BorrowDAO borrowDAO;

    // Constructeur avec BorrowDAO
    public BorrowService(BorrowDAO borrowDAO) {
        this.borrowDAO = borrowDAO;
    }

    // Méthode pour emprunter un livre
    public String borrowBook(Borrow borrow) {
        try {
            borrowDAO.addBorrow(borrow);
            return "Livre emprunté avec succès.";
        } catch (Exception e) {
            return "Erreur lors de l'emprunt du livre : " + e.getMessage();
        }
    }


    // Afficher les emprunts (méthode fictive, à adapter)
    public void displayBorrows() {
        System.out.println("Liste des emprunts...");
        // Afficher les emprunts enregistrés (adapté selon votre DAO)
    }
}
