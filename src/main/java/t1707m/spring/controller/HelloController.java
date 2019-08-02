package t1707m.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import t1707m.spring.entity.Student;
import t1707m.spring.repository.StudentRepository;

@Controller
public class HelloController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "/")
    @ResponseBody
    public String index() {
        return "Okie, home";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String sayHello(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Student sayGoodbye(
            @ModelAttribute Student student) {
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return student;
    }

}
