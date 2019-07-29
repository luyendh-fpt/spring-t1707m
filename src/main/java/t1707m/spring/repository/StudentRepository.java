package t1707m.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t1707m.spring.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
