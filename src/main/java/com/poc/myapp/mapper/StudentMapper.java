package com.poc.myapp.mapper;

import com.poc.myapp.entity.Student;
import com.poc.myapp.model.StudentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toStudentDTO(Student student);
}
