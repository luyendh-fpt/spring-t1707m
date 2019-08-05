package t1707m.spring.entity;

import org.springframework.beans.factory.annotation.Autowired;
import t1707m.spring.dto.StudentDto;
import t1707m.spring.repository.StudentRepository;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "students")
    private Set<Role> roles;

    public Student() {

    }

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(StudentDto studentDto) {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.name = studentDto.getName();
    }

    public Set<Role> getRoles() {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        role.addStudent(this);
        this.roles.add(role);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
