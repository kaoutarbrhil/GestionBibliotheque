package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }


    // Constructeur
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    

    // Ajouter un étudiant
    public void addStudent(Student student) {
            studentDAO.addStudent(student);
    }

    // Afficher tous les étudiants
    public void displayStudents() {
            List<Student> students = studentDAO.getAllStudents();
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + " | Nom: " + student.getName());
            }
    }


    // Dans StudentService
    public Optional<Student> findStudentById(int id) {
        return studentDAO.findById(id);
    }

}
