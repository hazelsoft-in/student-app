package com.poc.myapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.myapp.entity.Student;
import com.poc.myapp.kafka.StudentProducer;
import com.poc.myapp.mapper.StudentMapper;
import com.poc.myapp.model.Department;
import com.poc.myapp.model.StudentRequest;
import com.poc.myapp.model.StudentResponse;
import com.poc.myapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private StudentMapper studentMapper;

    private StudentRepository studentRepository;

    private StudentProducer studentProducer;

    private RestTemplate restTemplate;

    @Value("${topic.name:student}")
    private String topicName;

    @Value("${department.service.url:department}")
    private String departmentUrl;

    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper,
                          StudentProducer studentProducer,
                          RestTemplate restTemplate) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.studentProducer = studentProducer;
        this.restTemplate = restTemplate;
    }

    public StudentResponse getStudent(final Long id) {
        Optional<Student> student = studentRepository.findById(id);
        Department department = getDepartment(student.get().getDeptId());
        return studentMapper.toStudentDTO(student.get(), department.getName());
    }

    public StudentResponse createStudent(final StudentRequest studentRequest) {
        final Student student = studentMapper.toStudentEntity(studentRequest);
        final Student student1 = studentRepository.save(student);
        final StudentResponse studentResponse = studentMapper
                .toStudentDTO(student1, null);

        String topicKey = new StringBuilder()
                .append(studentResponse.getId())
                .append("-")
                .append(studentResponse.getFirstName()).toString();
                try {
                    studentProducer.sendMessageAsync(topicName, topicKey, OBJECT_MAPPER
                            .writeValueAsString(studentResponse));
                } catch (JsonProcessingException jpe) {
                    // log error
                }
        return studentResponse;
    }

    private Department getDepartment(Long deptId) {
        Map<String, String> params = new HashMap<>();
        params.put("deptId", deptId.toString());
        return restTemplate
                .getForObject(departmentUrl+"/department/{deptId}",
                        Department.class, params);
    }
}
