package com.poc.myapp;

import com.poc.myapp.entity.Student;
import com.poc.myapp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getStudent(final Long id) {
        return studentRepository.findById(id);
    }
}
