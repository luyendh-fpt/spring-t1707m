package t1707m.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1707m.spring.entity.Role;
import t1707m.spring.entity.Student;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
