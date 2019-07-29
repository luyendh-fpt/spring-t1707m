package t1707m.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t1707m.spring.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository repository;
}
