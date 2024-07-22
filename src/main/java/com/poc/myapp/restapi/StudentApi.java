package com.poc.myapp.restapi;

import com.poc.myapp.StudentService;
import com.poc.myapp.entity.Student;
import com.poc.myapp.model.StudentRequest;
import com.poc.myapp.model.StudentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<StudentResponse> getStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(studentService.getStudent(id));
    }

    @PostMapping("/student")
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok()
                .body(studentService.createStudent(studentRequest));
    }
}
