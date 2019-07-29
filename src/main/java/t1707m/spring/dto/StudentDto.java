package t1707m.spring.dto;

import javax.persistence.Id;
import java.util.Calendar;

public class StudentDto {

    private long id;
    private String name;

    public StudentDto() {

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

