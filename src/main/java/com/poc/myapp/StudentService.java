package com.poc.myapp;

import com.poc.myapp.entity.Student;
import com.poc.myapp.mapper.StudentMapper;
import com.poc.myapp.model.StudentDTO;
import com.poc.myapp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private StudentMapper studentMapper;

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentDTO getStudent(final Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return studentMapper.toStudentDTO(student.get());
    }
}
