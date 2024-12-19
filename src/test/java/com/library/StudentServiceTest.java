package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    private StudentDAO studentDAO;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        // Initialisation manuelle des DAO et services
        studentDAO = new StudentDAO();
        studentService = new StudentService(studentDAO);
    }

    @Test
    void testAddStudent() {
        // Ajouter un étudiant et vérifier que l'opération est correcte
        studentService.addStudent(new Student(1, "Alice"));
        assertEquals(1, studentDAO.getAllStudents().size());

        Optional<Student> studentOpt = studentDAO.getStudentById(1);
        assertTrue(studentOpt.isPresent()); // Vérifie que l'étudiant existe
        assertEquals("Alice", studentOpt.get().getName()); // Vérifie le nom de l'étudiant
    }

    @Test
    void testGetAllStudents() {
        // Ajouter plusieurs étudiants et vérifier le nombre total
        studentService.addStudent(new Student(2, "Hatim"));
        studentService.addStudent(new Student(3, "Bob"));

        assertEquals(3, studentDAO.getAllStudents().size());
    }
}
