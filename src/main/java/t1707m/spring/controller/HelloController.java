package t1707m.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import t1707m.spring.entity.Student;
import t1707m.spring.repository.StudentRepository;

import java.util.Calendar;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    StudentRepository repository;

    @GetMapping(value = "/say")
    public String sayHello(Model model) {
        Student student = new Student(Calendar.getInstance().getTimeInMillis(), "Hung");
        repository.save(student);
        model.addAttribute("name", "Hung");
        System.out.println("Say hello");
        return "hello";
    }

    @RequestMapping(value = "/goodbye/{var}", method = RequestMethod.POST)
    public String sayGoodbye(@RequestParam(name = "username") String name, @PathVariable String var) {
        System.out.println("Say Goodbye " + name);
        System.out.println("Say Var " + var);
        return "goodbye";
    }

}
