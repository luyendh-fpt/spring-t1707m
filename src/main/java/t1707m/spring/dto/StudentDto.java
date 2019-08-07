package t1707m.spring.dto;

import t1707m.spring.entity.Student;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentDto {

    private Long id;
    @NotEmpty
    @Size(min = 5, max = 10)
    private String name;
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min = 5, max = 10)
    private String password;

    private Long role;

    public StudentDto() {

    }

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}

