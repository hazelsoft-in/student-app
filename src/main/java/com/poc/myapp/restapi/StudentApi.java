package com.poc.myapp.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentApi {

    @GetMapping("/student")
    public String helloStudent() {
        return "Hello Student!";
    }
}
