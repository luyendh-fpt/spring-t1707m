package t1707m.spring.dto;

import t1707m.spring.entity.Student;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

public class StudentDto {

    private long id;
    private String name;

    public StudentDto() {

    }

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

