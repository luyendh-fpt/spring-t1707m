package t1707m.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import t1707m.spring.entity.Student;
import t1707m.spring.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyUserDetailService implements UserDetailsService {

    private static Logger LOGGER = Logger.getLogger(MyUserDetailService.class.getSimpleName());

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.log(Level.INFO, String.format("Try to login with username: %s", username));
        Optional<Student> optionalStudent = studentRepository.findFirstByUsername(username);
        if (optionalStudent.isPresent()) {
            LOGGER.log(Level.INFO, String.format("Okie, %s is pressent", username));
            Student student = optionalStudent.get();
            UserDetails userDetails =
                    User.builder()
                            .username(student.getUsername())
                            .password(student.getPassword())
                            .roles(student.getRole())
                            .build();
            return userDetails;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
