package com.poc.myapp.restapi;

import com.poc.myapp.StudentService;
import com.poc.myapp.entity.Student;
import com.poc.myapp.model.StudentDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
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

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(studentService.getStudent(id));
    }
}
