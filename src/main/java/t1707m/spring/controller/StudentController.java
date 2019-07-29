package t1707m.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import t1707m.spring.dto.RestResponse;
import t1707m.spring.dto.StudentDto;
import t1707m.spring.entity.Student;
import t1707m.spring.rest.RESTResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/students")
public class StudentController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getListStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(Calendar.getInstance().getTimeInMillis());
        studentDto.setName("Xuan Hung");

        List<StudentDto> list = new ArrayList<>();
        list.add(studentDto);

        studentDto = new StudentDto();
        studentDto.setId(Calendar.getInstance().getTimeInMillis());
        studentDto.setName("Hong Luyen");
        list.add(studentDto);

        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Success!")
                .addData(studentDto)
                .build(), HttpStatus.OK);
    }
}
