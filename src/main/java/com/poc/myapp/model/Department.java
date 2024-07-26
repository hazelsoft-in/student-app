package com.poc.myapp.model;

import lombok.Data;

@Data
public class Department {

    private Long id;
    private String name;
    private Integer numFaculty;
    private Long deptId;
}
