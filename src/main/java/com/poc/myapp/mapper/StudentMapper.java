package com.poc.myapp.mapper;

import com.poc.myapp.entity.Student;
import com.poc.myapp.model.StudentRequest;
import com.poc.myapp.model.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {


    StudentResponse toStudentDTO(Student student, String deptName);
    Student toStudentEntity(StudentRequest studentRequest);
}
