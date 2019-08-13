package t1707m.spring.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import t1707m.spring.dto.StudentDto;
import t1707m.spring.entity.Role;
import t1707m.spring.entity.Student;
import t1707m.spring.repository.RoleRepository;
import t1707m.spring.repository.StudentRepository;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;

@Controller
public class HelloController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MessageSource messageSource;

    @GetMapping(value = "/")
    @ResponseBody
    public String index() {
        return "Okie, home";
    }


    @GetMapping(value = "/employee")
//    @RolesAllowed("EMPLOYEE")
//    @Secured("EMPLOYEE")
    @ResponseBody
    public String employee() {
        return "Okie, employee";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/custom-error")
    public String error() {
        return "custom-error";
    }

    @GetMapping(value = "/demo-upload")
    public String demoUpload() {
        return "demo-upload";
    }

    @RequestMapping(value = "/de-upload", method = RequestMethod.POST)
    @ResponseBody
    public String deUpload(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        return "de-uploaed";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("student", new StudentDto());
        model.addAttribute("roles", roleRepository.findAll());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String signup(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("student") @Valid StudentDto studentDto,
            BindingResult bindingResult,
            Model model) {
        if (file != null && !file.isEmpty()) {
            System.out.println("Uploading file");
            long currentTime = System.currentTimeMillis();
            String saveName = currentTime + file.getOriginalFilename();
            try {
                File image = new File("upload/" + saveName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                System.out.println(String.format("Error when upload file.", saveName, e.getMessage()));
                e.printStackTrace();
            }
        }
        System.out.println("Binding error.");
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("student", studentDto);
            return "register";
        }
        System.out.println(studentDto.getRole());
        Role role = roleRepository.findById(studentDto.getRole()).orElse(null);
        if (role == null) {
            bindingResult.addError(new ObjectError("role", "Invalid role"));
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("student", studentDto);
            return "register";
        }
        Student student = Student.Builder.aStudent()
                .withName(studentDto.getName())
                .withUsername(studentDto.getUsername())
                .withPassword(bCryptPasswordEncoder.encode(studentDto.getPassword()))
                .withRole(role)
                .build();
//        role.addStudent(student);
        studentRepository.save(student);
        return "register-success";
    }

}
