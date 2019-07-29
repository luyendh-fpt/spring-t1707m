package t1707m.spring.entity;

import org.springframework.beans.factory.annotation.Autowired;
import t1707m.spring.repository.StudentRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Student {

    @Id
    private long id;
    private String name;

    public Student() {

    }

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
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
