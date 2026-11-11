
package com.academicguide;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final StudentRepository studentRepository;

    public RegistrationController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        name = name.trim();
        email = email.trim();

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }

        if (password.length() < 8) {
            model.addAttribute("error",
                    "Password must contain at least 8 characters!");
            return "register";
        }

        if (studentRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        Student student = new Student(name, email, hashedPassword);

        studentRepository.save(student);

        return "redirect:/login?registered";
    }
}
