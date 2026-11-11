
package com.academicguide;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final StudentRepository studentRepository;

    public LoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginStudent(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Student student = studentRepository.findByEmail(email.trim());

        if (student == null ||
                !PasswordUtil.verifyPassword(
                        password, student.getPassword())) {

            model.addAttribute("error",
                    "Invalid email or password!");

            return "login";
        }

        session.setAttribute("studentId", student.getId());
        session.setAttribute("studentName", student.getName());

        return "redirect:/";
    }
    
    @PostMapping("/logout")
    public String logoutStudent(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }

}
