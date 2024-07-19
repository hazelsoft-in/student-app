package com.poc.myapp.restapi;

import com.poc.myapp.StudentService;
import com.poc.myapp.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StudentApi {

    private StudentService studentService;

    public StudentApi(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/hello")
    public String helloStudent() {
        return "Hello Student!";
    }

    @GetMapping("/student")
    public Optional<Student> getStudent(Long id) {
        return studentService.getStudent(id);
    }
}
