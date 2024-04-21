package com.example.leddit.Controller;

import com.example.leddit.Model.User;
import com.example.leddit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestParam String username, @RequestParam String password) {
        boolean isValid = userService.validateLogin(username, password);
        if (isValid) {
            // Successful login, generate token
            String token = userService.generateToken();
            User user = userService.findByUsername(username).orElse(null);

            if (user != null || user.getActive() != 0) {
                // Store token in the database
                userService.storeToken(user.getId(), token);

                // Return the token in the response body
                return ResponseEntity.ok(token);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(@ModelAttribute User user, Model model) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already taken");
            return "register";
        }
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already in use");
            return "register";
        }

        userService.registerUser(user);
        model.addAttribute("success", "Registration successful! Please log in.");
        return "login";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        // Invalidate the session to log out the user
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        // Retrieve all users from the database
        List<User> users = userService.getAllUsers();

        // Add the list of users to the model
        model.addAttribute("users", users);

        // Return the name of the Thymeleaf template to render
        return "view_users";
    }
}