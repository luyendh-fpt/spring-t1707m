package t1707m.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import t1707m.spring.dto.RestResponse;
import t1707m.spring.dto.StudentDto;
import t1707m.spring.entity.Student;
import t1707m.spring.repository.StudentRepository;
import t1707m.spring.rest.RESTResponse;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success!")
                .setData(studentRepository.findAll().stream().map(x -> new StudentDto(x)).collect(Collectors.toList()))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getById(@PathVariable long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Student is not found or has been deleted!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        Student student = optionalStudent.get();
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success!")
                .setData(new StudentDto(student))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody StudentDto studentDto) {
        Student student = new Student(studentDto);
        studentRepository.save(student);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("Success!")
                .setData(new StudentDto(student))
                .build(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody StudentDto studentDto, @PathVariable long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Student is not found or has been deleted!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        Student existStudent = optionalStudent.get();
        existStudent.setName(studentDto.getName());
        studentRepository.save(existStudent);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success!")
                .setData(new StudentDto(existStudent))
                .build(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.SimpleError()
                    .setCode(HttpStatus.NOT_FOUND.value())
                    .setMessage("Student is not found or has been deleted!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        Student existStudent = optionalStudent.get();
        studentRepository.delete(existStudent);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success!")
                .build(), HttpStatus.OK);
    }
}
